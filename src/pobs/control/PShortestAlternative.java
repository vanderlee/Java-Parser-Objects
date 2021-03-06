/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package pobs.control;

import pobs.PControl;
import pobs.PDirective;

/**
 * Sets the alternatives directive to shortest mode, this will return the
 * shortest of all matching alternatives in encountered in parsers like
 * {@link pobs.parser.POr} and {@link pobs.parser.PTokens}.
 * 
 * @author Martijn W. van der Lee
 * @author Franz-Josef Elmer
 */
public class PShortestAlternative implements PControl {
	public Object modifyState(PDirective directive) {
		Object result = directive.getAlternatives();
		directive.setAlternatives(PDirective.SHORTEST);
		return result;
	}

	public void reestablishPreviousState(PDirective directive,
			Object previousState) {
		directive.setAlternatives(previousState);
	}
}