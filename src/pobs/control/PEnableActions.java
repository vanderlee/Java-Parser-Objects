/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package pobs.control;

import pobs.PControl;
import pobs.PDirective;

/**
 * Enables the actions directive.
 * 
 * @author	Martijn W. van der Lee
 * @author Franz-Josef Elmer
 */
public class PEnableActions implements PControl {
	public Object modifyState(PDirective directive) {
		Boolean result = directive.isActions() ? Boolean.TRUE : Boolean.FALSE;
		directive.setActions(true);
		return result;
	}

	public void reestablishPreviousState(PDirective directive,
										 Object previousState) {
		directive.setActions(previousState == Boolean.TRUE);
	}
}
