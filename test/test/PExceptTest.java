/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package test;

/**
 * @author	Martijn W. van der Lee 
 */
public class PExceptTest extends TestCaseParser {
	public PExceptTest(String arg0) {
		super(arg0);
	}

	public static void main(String[] args) {
		junit.textui.TestRunner.run(PExceptTest.class);
	}

	public void testInputLimits() {
		pobs.PObject parser =
			new pobs.parser.PExcept(
				new pobs.parser.PAny(),
				new pobs.parser.PChar('x'));

		assertMismatch("Zero-length input", parse(parser, ""), 0);
		assertMatch("First input only", parse(parser, "aaaa"), 1);
		assertMatch("Non-zero beginning", parse(parser, "xax", 1), 1);
	}

	public void testException() {
		pobs.PObject parser =
			new pobs.parser.PExcept(
				new pobs.parser.PAny(),
				new pobs.parser.PChar('x'));

		assertMatch("Match", parse(parser, "a"), 1);
		assertMismatch("Mismatch", parse(parser, "x"), 0);
	}

	public void testOptional() {
		pobs.PObject parser =
			new pobs.parser.PExcept(
				new pobs.parser.POptional(new pobs.parser.PAny()),
				new pobs.parser.PChar('x'));

		assertMatch("Match none", parse(parser, ""), 0);
		assertMismatch("Mismatch", parse(parser, "x"), 0);
	}

	public void testKleene() {
		pobs.PObject parser =
			new pobs.parser.PExcept(
				new pobs.parser.PKleene(new pobs.parser.PAny()),
				new pobs.parser.PChar('x'));

		assertMatch("Match none", parse(parser, ""), 0);
		assertMismatch("Mismatch", parse(parser, "xaa"), 0);
		assertMatch("Match including x", parse(parser, "aaax"), 4);
		assertMatch("Match beyond x", parse(parser, "axa"), 3);
	}
}