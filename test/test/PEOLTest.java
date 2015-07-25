/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package test;

/**
 * @author	Martijn W. van der Lee 
 */
public class PEOLTest extends TestCaseParser {
	public PEOLTest(String arg0) {
		super(arg0);
	}

	public static void main(String[] args) {
		junit.textui.TestRunner.run(PEOLTest.class);
	}

	public void testEOL() {
		pobs.PObject parser = new pobs.parser.PEOL();

		assertMismatch("Zero-length input", parse(parser, ""), 0);
		assertMatch("LF, no CR", parse(parser, "\n\r"), 1);
		assertMatch("LF, no LF", parse(parser, "\n\n"), 1);
		assertMatch("CR, no CR", parse(parser, "\r\r"), 1);
		assertMatch("CR/LF", parse(parser, "\r\n"), 2);
		assertMatch("CR/LF, no CR", parse(parser, "\r\n\r"), 2);
		assertMatch("CR/LF, no LF", parse(parser, "\r\n\n"), 2);
		assertMismatch("Non EOL", parse(parser, "a\r\n"), 0);
		assertMatch("Non-zero beginning", parse(parser, "a\r\na", 2), 1);
	}
}