/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package test;

/**
 * @author	Martijn W. van der Lee 
 */
public class PSetTest extends TestCaseParser {
	public PSetTest(String arg0) {
		super(arg0);
	}

	public static void main(String[] args) {
		junit.textui.TestRunner.run(PSetTest.class);
	}

	public void testInputLimits() {
		pobs.PObject parser = new pobs.parser.PChar('a');

		assertMismatch("Zero-length input", parse(parser, ""), 0);
		assertMatch("First input only", parse(parser, "aaaa"), 1);
		assertMatch("Non-zero beginning", parse(parser, "bad", 1), 1);
	}

	public void testChar() {
		pobs.PObject parser = new pobs.parser.PChar('b');

		assertMatch("Character", parse(parser, "b"), 1);
		assertMismatch("Mismatch character", parse(parser, "a"), 0);
	}

	public void testUnicode() {
		pobs.PObject parser = new pobs.parser.PChar('\u3334');

		assertMatch("Char in set", parse(parser, "\u3334"), 1);
		assertMismatch("Char not in set", parse(parser, "\u3336"), 0);
	}

	public void testCaseSensitive() {
		pobs.PObject parser = new pobs.parser.PChar('a');

		assertMismatch("Case sensitive", parse(parser, "A"), 0);

		pobs.PContext context = new pobs.PContext();
		context.getDirective().setCaseSensitive(false);
		assertMatch(
			"Case insensitive",
			parser.process(new pobs.scanner.PStringScanner("A"), 0, context),
			1);
	}
}
