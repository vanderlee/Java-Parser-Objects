/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package test;

/**
 * @author	Martijn W. van der Lee 
 */
public class PAnyTest extends TestCaseParser {
	public PAnyTest(String arg0) {
		super(arg0);
	}

	public static void main(String[] args) {
		junit.textui.TestRunner.run(PAnyTest.class);
	}

	public void testAny() {
		pobs.PObject parser = new pobs.parser.PAny();

		assertMismatch("Zero-length input", parse(parser, ""), 0);
		assertMatch("First input only", parse(parser, "aaaa"), 1);
		assertMatch("Unicode", parse(parser, "\u3928abc"), 1);
		assertMatch("Non-zero beginning", parse(parser, "abc", 2), 1);
	}
}