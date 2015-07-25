/*
 * Copyright (c) 2006 Parser OBjectS group Released under ZLib/LibPNG license.
 * See "license.txt" for details.
 */
package example.java;

import pobs.PContext;
import pobs.PMatch;
import pobs.PParser;
import pobs.PScanner;

/**
 * Java Identifier Part parser
 * 
 * @author Martijn W. van der Lee
 */
public class JavaIdentifierPart extends PParser {
	public PMatch parse(PScanner input, long begin, PContext context) {
		if (begin < input.length()
			&& Character.isJavaIdentifierPart(input.charAt(begin))) {
			return PMatch.TRUE;
		}
			
		return PMatch.FALSE;
    }
}