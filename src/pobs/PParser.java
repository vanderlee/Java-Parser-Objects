/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package pobs;

import java.util.Vector;

/**
 * Abstract base class of all parsers. It handles directives and optional
 * actions. Concrete subclasses must implement
 * {@link #parse(PScanner, long, PContext)}.<p/>In order to make parsers thread
 * safe the following points must be fulfilled: <ul><li>
 * {@link #setErrorInfo(String)},{@link #addControl(PControl)},
 * {@link #setMatchAction(PAction)}, and {@link #setMismatchAction(PAction)}
 * must be called <em>before</em> the first invocation of
 * {@link #process(PScanner, long, PContext)}.</li><li>These added
 * {@link PControl PControls}and {@link PAction PActions}must be stateless
 * objects.</li><li>The implementation of
 * {@link #parse(PScanner, long, PContext)}never changes the internal state of a
 * <code>PParser</code> object.</li></ul>
 * 
 * @author Martijn W. van der Lee
 * @author Franz-Josef Elmer
 */
public abstract class PParser implements PObject {
    private Vector controls = new Vector(1);

    private String errorInfo;

    private PAction matchAction;

    private PAction mismatchAction;

    /**
     * Adds a control. Controls are executed in inverse order. That is, the last
     * added control will be executed first.
     * 
     * @return this instance.
     */
    public final PParser addControl(PControl control) {
        controls.addElement(control);
        return this;
    }

    /**
     * Returns the error information.
     */
    public String getErrorInfo() {
        return errorInfo;
    }

    /**
     * Parses the specified input starting at <code>begin</code>. Usually
     * only the {@link PDirective}of the specified {@link PContext}will be
     * used. The complete context is necessary if parser has to call a wrapped
     * parser.
     * 
     * @param input
     *            Input to be parsed.
     * @param begin
     *            Index of first character of <code>input</code> to be parsed.
     * @param context
     *            Parsing context.
     * @return Parsing result.
     */
    protected abstract PMatch parse(PScanner input, long begin, PContext context);

    /**
     * Manipulates the directives before actual parsing by using the added
     * {@link PControl PControls}. This method is called recursively in inverse
     * order of control list.
     */
    private PMatch parse(PScanner input, long begin, PContext context, int level) {
        PMatch result;
        if (level == 0) {
            result = parse(input, begin, context);
        } else {
            --level;
            PControl control = (PControl) controls.elementAt(level);
            PDirective directive = context.getDirective();
            Object currentState = control.modifyState(directive);
            result = parse(input, begin, context, level);
            control.reestablishPreviousState(directive, currentState);
        }
        return result;
    }

    /**
     * Parses specified input and invokes actions if defined. First,
     * {@link #parse(PScanner, long, PContext)}is invoked. Depending on the
     * result the matching or missmatching action is invoked with the
     * {@link PTarget}of <code>context</code>.
     */
    public PMatch process(PScanner input, long begin, PContext context) {
        PMatch result = parse(input, begin, context, controls.size());

        try {
	        boolean match = result.isMatch();
	        if (match && matchAction != null) {
	            matchAction.perform(context.getTarget(), input.substring(begin,
	                    begin + result.getLength()));
	        } else if (!match && mismatchAction != null) {
	            mismatchAction.perform(context.getTarget(), input.substring(begin,
	                    begin + result.getLength()));
	        }
	        if (!match) {
	            PErrorHandler errorHandler = context.getErrorHandler();
	            if (errorHandler != null) {
	                errorHandler.notifySyntaxError(begin, this);
	            }
	        }
        } catch (RuntimeException e) {
            PErrorHandler errorHandler = context.getErrorHandler();
            if (errorHandler != null) {
                errorHandler.notifySemanticError(begin, e);
            }
        }

        return result;
    }

    /**
     * Sets an error info.
     * 
     * @param errorInfo
     *            Error info which will be used by {@link PErrorHandler}. Can
     *            be <code>null</code>.
     * @return this instance.
     */
    public PParser setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
        return this;
    }

    /**
     * Defines the action used in the case of matching.
     * 
     * @param matchAction
     *            Action invoked in case of a successful match. Can be
     *            <code>null</code>.
     * @return this instance.
     */
    public final PParser setMatchAction(PAction matchAction) {
        this.matchAction = matchAction;
        return this;
    }

    /**
     * Defines the action used in the case of missmatching.
     * 
     * @param mismatchAction
     *            Action invoked in case of a unsuccessful match. Can be
     *            <code>null</code>.
     * @return this instance.
     */
    public final PParser setMismatchAction(PAction mismatchAction) {
        this.mismatchAction = mismatchAction;
        return this;
    }
}