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
public class JavaLiteralTest extends TestCaseParser {
	public JavaLiteralTest(String arg0) {
		super(arg0);
	}

	public static void main(String[] args) {
		junit.textui.TestRunner.run(JavaLiteralTest.class);
	}

	public void testJavaLiteral() {
		PObject p = new example.java.JavaLiteral();	

		assertMismatch("Zero-length input", parseJava(p, ""), 0);
		assertMatch("Integer dec 0", parseJava(p, "0a"), 1);
		assertMatch("Integer dec", parseJava(p, "123a"), 3);
		assertMatch("Integer dec long", parseJava(p, "123La"), 4);
		assertMatch("Integer hex", parseJava(p, "0xFFX"), 4);
		assertMatch("Integer oct", parseJava(p, "0128"), 3);
		assertMatch("Float short form", parseJava(p, "1.a"), 2);
		assertMatch("Float short form 2", parseJava(p, ".1a"), 2);
		assertMatch("Float specified", parseJava(p, "2fa"), 2);
		assertMatch("Float", parseJava(p, "1.2a"), 3);
		assertMatch("Float E", parseJava(p, "1.2E-2a"), 6);
		assertMatch("Float Ef", parseJava(p, "1.2E-2fa"), 7);
		assertMatch("Null", parseJava(p, "null"), 4);
		assertMismatch("Null fail skip", parseJava(p, "nu ll"), 2);
		assertMatch("Boolean", parseJava(p, "true"), 4);
		assertMatch("String", parseJava(p, "\"Blah\"diblah"), 6);
		assertMismatch("String escaped", parseJava(p, "\"Blah\\\"diblah"), 5);
		assertMatch("String escape char", parseJava(p, "\"Blah\\u1234\"diblah"), 12);
		assertMatch("Char", parseJava(p, "'a'db"), 3);
		assertMismatch("Char too long", parseJava(p, "'ab'db"), 2);
		assertMismatch("Identifier", parseJava(p, "abc"), 0);
	}

	final public pobs.PMatch parseJava(pobs.PObject parser, java.lang.String input) {
		PObject s = new example.java.JavaSkipper();	
		
		return super.parse(parser, input, new PDirective(s));
	}	
}