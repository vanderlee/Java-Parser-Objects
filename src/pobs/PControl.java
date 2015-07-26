/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package pobs;


/**
 * Interface of all classes which manipulate {@link PDirective}.
 * 
 * @author Franz-Josef Elmer
 */
public interface PControl {
	/**
	 * Modifies a sub state of the specified directive.
	 * 
	 * @param directive 
	 * 				Directive to be modified.
	 * @return sub state before modification which allows reestablishing the
	 *         previous state.
	 */
	public Object modifyState(PDirective directive);
	
	/**
	 * Reestablishes the previous state before modification.
	 * 
	 * @param directive 
	 * 				Directive which has been modified.
	 * @param previousState 
	 * 				Previous state as returned by 
	 * 				{@link #modifyState(PDirective)}.
	 */
	public void reestablishPreviousState(PDirective directive, 
										 Object previousState);
}
