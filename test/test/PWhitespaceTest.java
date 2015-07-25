/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package test;

/**
 * @author	Martijn W. van der Lee 
 */
public class PWhitespaceTest extends TestCaseParser {
	public PWhitespaceTest(String arg0) {
		super(arg0);
	}

	public static void main(String[] args) {
		junit.textui.TestRunner.run(PWhitespaceTest.class);
	}

	public void testWhitespace() {
		pobs.PObject parser = new pobs.parser.PWhitespace();

		assertMismatch("Zero-length input", parse(parser, ""), 0);
		assertMatch("All input", parse(parser, "   "), 3);
		assertMatch("Except no-brake 1", parse(parser, " \u00A0"), 1);
		assertMatch("Except no-brake 2", parse(parser, " \uFEFF"), 1);
		assertMatch(
			"ASCII whitespace",
			parse(parser, "\t\n\u000B\u000C\r\u001C\u001D\u001E\u001Fx"),
			9);
	}
}
