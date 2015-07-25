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
public class JavaIdentifierTest extends TestCaseParser {
	public JavaIdentifierTest(String arg0) {
		super(arg0);
	}

	public static void main(String[] args) {
		junit.textui.TestRunner.run(JavaIdentifierTest.class);
	}

	public void testJavaIdentifier() {
		PObject p = new example.java.JavaIdentifier();	

		assertMismatch("Zero-length input", parseJava(p, ""), 0);
		assertMatch("Correct", parseJava(p, "ok123"), 5);
		assertMismatch("Numeric start", parseJava(p, "1ok"), 0);
		assertMatch("Incorrect characters", parseJava(p, "ok-dk"), 2);
		assertMatch("Correct", parseJava(p, "ok 123"), 2);
	}
	
	final public pobs.PMatch parseJava(pobs.PObject parser, java.lang.String input) {
		PObject s = new example.java.JavaSkipper();	
		
		return super.parse(parser, input, new PDirective(s));
	}	
}