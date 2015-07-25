/*
 * Copyright (c) 2006 Parser OBjectS group Released under ZLib/LibPNG license.
 * See "license.txt" for details.
 */
package example.java;

import pobs.PContext;
import pobs.PMatch;
import pobs.PParser;
import pobs.PScanner;
import pobs.control.PDisableSkip;
import pobs.parser.PKleene;
import pobs.parser.PSequence;

/**
 * Java Identifier parser
 * 
 * @author Martijn W. van der Lee
 */
public class JavaIdentifier extends PParser {
	public PParser parser =	new PSequence(
			new JavaIdentifierStart(), new PKleene(new JavaIdentifierPart()))
			.addControl(new PDisableSkip());
		
	protected PMatch parse(PScanner input, long begin, PContext context) {
		return parser.process(input, begin, context);
	}
}