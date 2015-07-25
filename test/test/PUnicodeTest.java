/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package test;

/**
 * @author	Martijn W. van der Lee 
 */
public class PUnicodeTest extends TestCaseParser {
	public PUnicodeTest(String arg0) {
		super(arg0);
	}

	public static void main(String[] args) {
		junit.textui.TestRunner.run(PUnicodeTest.class);
	}

	public void testUnicode() {
		pobs.PObject parser = new pobs.parser.PUnicode();

		assertMismatch("Zero-length input", parse(parser, ""), 0);
		assertMatch("Match one character max.", parse(parser, "aaaa"), 1);
		assertMatch("Unicode", parse(parser, "\u0636"), 1);
		assertMismatch("Undefined", parse(parser, "\uffff"), 0);
		assertMatch("Match beyond begin", parse(parser, "\uffffa\uffff", 1), 1);
	}
}
