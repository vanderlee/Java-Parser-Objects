/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package test;

/**
 * @author	Martijn W. van der Lee 
 */
public class PTokenTest extends TestCaseParser {
	public PTokenTest(String arg0) {
		super(arg0);
	}

	public static void main(String[] args) {
		junit.textui.TestRunner.run(PTokenTest.class);
	}

	public void testInputLimits() {
		pobs.PObject parser = new pobs.parser.PToken("foo");

		assertMismatch("Zero-length input", parse(parser, ""), 0);
		assertMatch("First iteration only", parse(parser, "foofoofoo"), 3);
		assertMatch("Begin not at zero", parse(parser, "afoofy", 1), 3);
	}

	public void testUnicode() {
		pobs.PObject parser = new pobs.parser.PToken("\u3334");

		assertMatch("Unicode match", parse(parser, "\u3334"), 1);
		assertMismatch("Unicode mismatch", parse(parser, "\u3336"), 0);
	}

	public void testCaseSensitive() {
		pobs.PObject parser = new pobs.parser.PToken("fOo");

		assertMismatch("Case sensitive", parse(parser, "Foo"), 0);

		pobs.PContext context = new pobs.PContext();
		context.getDirective().setCaseSensitive(false);

		assertMatch(
			"Case insensitive",
			parser.process(new pobs.scanner.PStringScanner("Foo"), 0, context),
			3);
	}
}