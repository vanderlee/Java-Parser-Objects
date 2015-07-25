/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package test;

/**
 * @author	Martijn W. van der Lee 
 */
public class PDigitTest extends TestCaseParser {
	public PDigitTest(String arg0) {
		super(arg0);
	}

	public static void main(String[] args) {
		junit.textui.TestRunner.run(PDigitTest.class);
	}

	public void testInputLimits() {
		pobs.PObject parser = new pobs.parser.PDigit();

		assertMismatch("Zero-length input", parse(parser, ""), 0);
		assertMatch("First input only", parse(parser, "1234"), 1);
		assertMatch("Non-zero beginning", parse(parser, "u5a", 1), 1);
	}

	public void testDecimal() {
		pobs.PObject parser = new pobs.parser.PDigit();

		assertMatch("Decimal", parse(parser, "0"), 1);
		assertMismatch("Alphabetical", parse(parser, "a"), 0);
	}

	public void testUnicode() {
		pobs.PObject parser = new pobs.parser.PDigit();

		assertMatch("Bengali", parse(parser, "\u09E6"), 1);
		assertMatch("Fullwidth", parse(parser, "\uFF10"), 1);
	}
}