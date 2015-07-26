/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package pobs;

/**
 * Interface of all error handlers. An error handler is called if a parser found
 * a mismatch.
 * 
 * @author Franz-Josef Elmer
 */
public interface PErrorHandler {
	/**
	 * Notifies the error handler that a syntax error occured for 
	 * the specified parser at the specified parsing position. 
	 * This method might be called several
	 * times even if complete parsing is successful. 
	 * <p>
	 * From both types of notifiction
	 * events concrete implementations must find out where the actual error
	 * occured and what is its reason. For the later
	 * {@link PParser#getErrorInfo()} can be used.
	 * 
	 * @param position
	 *            Parsing position.
	 * @param parser
	 *            Parser who has detected a syntax error.
	 */
	public void notifySyntaxError(long position, PParser parser);
	
	/**
	 * Notifies the error handler that a semantic error occured during 
	 * execution of a {@link PAction} attached to the specified parser.
	 * This method might be called several
	 * times even if complete parsing is successful. 
	 * <p>
	 * From both types of notifiction
	 * events concrete implementations must find out where the actual error
	 * occured and what is its reason. For the later
	 * <code>exception.getMessage()</code> can be used.
	 * 
	 * @param position
	 *            Parsing position.
	 * @param exception
	 *            Exception thrown by the <code>PAction</code> instance.
	 */
	public void notifySemanticError(long position, RuntimeException exception);

	/**
	 * Gets the actual error position.
	 * 
	 * @return actual parsing position where the error occured.
	 */
	public long getErrorPosition();
	
	/**
	 * Returns <code>true</code> if at least one semantic error occured.
	 */
	public boolean semanticErrorOccured();

	/**
	 * Creates a human readable error message using the specified input to get
	 * reasonable error positions (like line number and column number).
	 * 
	 * @param input
	 *            Input source of parsing.
	 * @return error message.
	 */
	public String createErrorMessage(PScanner input);
}