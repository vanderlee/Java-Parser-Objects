/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package example.java.test;

import test.TestCaseParser;
import pobs.PDirective;
import pobs.PObject;

/**
 * @author	Martijn W. van der Lee 
 */
public class JavaTokenTest extends TestCaseParser {
	public JavaTokenTest(String arg0) {
		super(arg0);
	}

	public static void main(String[] args) {
		junit.textui.TestRunner.run(JavaTokenTest.class);
	}

	public void testJavaIdentifier() {
		PObject p = new example.java.JavaToken("token");	

		assertMismatch("Zero-length input", parseJava(p, ""), 0);
		assertMatch("Perfect match", parseJava(p, "token"), 5);
		assertMatch("Match + non-char", parseJava(p, "token("), 5);
		assertMismatch("Match + char", parseJava(p, "tokens"), 5);
		assertMismatch("Too short", parseJava(p, "toke"), 0);
	}
	
	final public pobs.PMatch parseJava(pobs.PObject parser, java.lang.String input) {
		PObject s = new example.java.JavaSkipper();	
		
		return super.parse(parser, input, new PDirective(s));
	}	
}