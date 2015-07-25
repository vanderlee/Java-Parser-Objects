/*
 * Copyright (c) 2006 Parser OBjectS group Released under ZLib/LibPNG license.
 * See "license.txt" for details.
 */
package example.java;

import pobs.PContext;
import pobs.PMatch;
import pobs.PScanner;
import pobs.parser.PTokens;

/**
 * A Java token is a basic token delimited by something not a JavaIdentifier.
 * 
 * @author Martijn W. van der Lee
 */
public class JavaTokens extends PTokens {
	public JavaTokens(String[] tokens) {
		super(tokens);
	}
	
	public JavaTokens(String token1, String token2) {
		this(new String[] { token1, token2 });
	}

	public JavaTokens(String token1, String token2, String token3) {
		this(new String[] { token1, token2, token3 });
	}

	public JavaTokens(String token1, String token2, String token3, String token4) {
		this(new String[] { token1, token2, token3, token4 });
	}

	public JavaTokens(String token1, String token2, String token3, String token4,
			String token5) {
		this(new String[] { token1, token2, token3, token4, token5 });
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