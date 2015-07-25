/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package pobs.control;

import pobs.PControl;
import pobs.PDirective;

/**
 * Enables the case-sensitive directive.
 * 
 * @author	Martijn W. van der Lee
 * @author Franz-Josef Elmer
 */
public class PEnableCase implements PControl {
	public Object modifyState(PDirective directive) {
		Boolean result = directive.isCaseSensitive() ? Boolean.TRUE 
													 : Boolean.FALSE;
		directive.setCaseSensitive(true);
		return result;
	}

	public void reestablishPreviousState(PDirective directive,
										 Object previousState) {
		directive.setCaseSensitive(previousState == Boolean.TRUE);
	}
}
