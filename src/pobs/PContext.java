/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package pobs;


/**
 * Immutable class containg a {@link PDirective} instance and optional a
 * {@link PTarget} and a {@link PErrorHandler}.
 * For threadsafe usage, a PTarget <u>must</u> be specified.
 * 
 * @author Martijn W. van der Lee
 * @author Franz-Josef Elmer
 */
public class PContext {
	private final PDirective directive;

	private final PTarget target;
	
	private final PErrorHandler errorHandler;

	/**
	 * Creates an instance with a default directive, no target 
	 * and no error handler.
	 */
	public PContext() {
		this(new PDirective(), null, null);
	}

	/**
	 * Creates an instance with the specified directive, no target 
	 * and no error handler.
	 * 
	 * @param directive
	 *            Parser directive. <code>null</code> is not allowed.
	 * @throws IllegalArgumentException
	 *             if <code>directive == null</code>.
	 */
	public PContext(PDirective directive) {
		this(directive, null, null);
	}

	/**
	 * Creates an instance with the specified directive and target.
	 * 
	 * @param directive
	 *            Parser directive. <code>null</code> is not allowed.
	 * @param target
	 *            Action target. Can be <code>null</code>.
	 * @throws IllegalArgumentException
	 *             if <code>directive == null</code>.
	 */
	public PContext(PDirective directive, PTarget target, 
					PErrorHandler errorHandler) {
		if (directive == null) {
			throw new IllegalArgumentException("directive missing");
		}
		this.directive = directive;
		this.target = target;
		this.errorHandler = errorHandler;
	}

	/**
	 * Returns parsing directive.
	 */
	public PDirective getDirective() {
		return directive;
	}

	/**
	 * Returns action target.
	 * 
	 * @return <code>null</code> if undefined.
	 */
	public PTarget getTarget() {
		return target;
	}
	
	/**
	 * Returns error handler.
	 * 
	 * @return <code>null</code> if undefined.
	 */
	public PErrorHandler getErrorHandler() {
		return errorHandler;
	}
}