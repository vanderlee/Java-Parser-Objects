/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package test;

/**
 * @author	Martijn W. van der Lee 
 */
public class PLetterTest extends TestCaseParser {
	public PLetterTest(String arg0) {
		super(arg0);
	}

	public static void main(String[] args) {
		junit.textui.TestRunner.run(PLetterTest.class);
	}

	public void testInputLimits() {
		pobs.PObject parser = new pobs.parser.PLetter();

		assertMismatch("Zero-length input", parse(parser, ""), 0);
		assertMatch("First input only", parse(parser, "aaaa"), 1);
		assertMatch("Non-zero beginning", parse(parser, "3a3", 1), 1);
	}

	public void testLetter() {
		pobs.PObject parser = new pobs.parser.PLetter();

		assertMatch("Character", parse(parser, "a"), 1);
		assertMismatch("Digit", parse(parser, "123"), 0);
	}
}