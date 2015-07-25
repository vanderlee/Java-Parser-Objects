/*
 * Copyright (c) 2006 Parser OBjectS group Released under ZLib/LibPNG license.
 * See "license.txt" for details.
 */
package example.java;

import pobs.PContext;
import pobs.PMatch;
import pobs.PScanner;
import pobs.parser.PToken;

/**
 * A Java token is a basic token delimited by something not a JavaIdentifier.
 * 
 * @author Martijn W. van der Lee
 */
public class JavaToken extends PToken {
	public JavaToken(String token) {
		super(token);
	}

	public PMatch parse(PScanner input, long begin, PContext context) {
		PMatch m = super.parse(input, begin, context);
		
		if (m.isMatch()
		 && (begin + m.getLength() < input.length())
		 && Character.isJavaIdentifierPart(input.charAt(begin + m.getLength()))) {
			return new PMatch(false, m.getLength());			
		} else {
			return m;
		}
	}
}