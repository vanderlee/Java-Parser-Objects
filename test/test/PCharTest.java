/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package test;

/**
 * @author	Martijn W. van der Lee 
 */
public class PCharTest extends TestCaseParser {
	public PCharTest(String arg0) {
		super(arg0);
	}

	public static void main(String[] args) {
		junit.textui.TestRunner.run(PCharTest.class);
	}

	public void testInputLimits() {
		pobs.PObject parser = new pobs.parser.PChar('a');

		assertMismatch("Zero-length input", parse(parser, ""), 0);
		assertMatch("First input only", parse(parser, "aaaa"), 1);
		assertMatch("Non-zero beginning", parse(parser, "bad", 1), 1);
	}

	public void testUnicode() {
		pobs.PObject parser = new pobs.parser.PChar('\u3334');

		assertMatch("Match", parse(parser, "\u3334"), 1);
		assertMismatch("Match", parse(parser, "\u3336"), 0);
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