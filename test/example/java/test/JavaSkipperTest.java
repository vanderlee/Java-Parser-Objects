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
public class JavaSkipperTest extends TestCaseParser {
	public JavaSkipperTest(String arg0) {
		super(arg0);
	}

	public static void main(String[] args) {
		junit.textui.TestRunner.run(JavaSkipperTest.class);
	}

	public void testJavaSkipper() {
		PObject p = new example.java.JavaSkipper();	
		
		assertMatch("Zero-length input", parse(p, ""), 0);	
		assertMatch("None", parse(p, "a"), 0);	
		assertMatch("Spaces", parse(p, "   a"), 3);	
		assertMatch("Newlined", parse(p, "/*\n*/a"), 5);	
		assertMatch("C style", parse(p, "/* blah */a"), 10);	
		assertMatch("C++ style", parse(p, "// blah\na"), 8);	
	}
	
	final public pobs.PMatch parseJava(pobs.PObject parser, java.lang.String input) {
		PObject s = new example.java.JavaSkipper();	
		
		return super.parse(parser, input, new PDirective(s));
	}	
}