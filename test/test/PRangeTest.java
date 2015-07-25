/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package test;

/**
 * @author	Martijn W. van der Lee 
 */
public class PRangeTest extends TestCaseParser {

	public PRangeTest(String arg0) {
		super(arg0);
	}

	public static void main(String[] args) {
		junit.textui.TestRunner.run(PRangeTest.class);
	}

	public void testInputLimits() {
		pobs.PObject parser = new pobs.parser.PRange("a");

		assertMismatch("Zero-length input", parse(parser, ""), 0);
		assertMatch("First input only", parse(parser, "aaaa"), 1);
	}

	public void testAsSet() {
		pobs.PObject parser = new pobs.parser.PRange("box");

		assertMatch("First in set", parse(parser, "b"), 1);
		assertMismatch("Some middle char", parse(parser, "n"), 0);
		assertMatch("Last in set", parse(parser, "x"), 1);
		assertMismatch("Before first", parse(parser, "a"), 0);
		assertMismatch("After last", parse(parser, "z"), 0);
	}

	public void testRange() {
		pobs.PObject parser = new pobs.parser.PRange("a-c");

		assertMatch("Char in set", parse(parser, "b"), 1);
		assertMismatch("Char not in set", parse(parser, "d"), 0);
	}

	public void testUnicodeRange() {
		pobs.PObject parser = new pobs.parser.PRange("\u3333-\u3335");

		assertMatch("Char in set", parse(parser, "\u3334"), 1);
		assertMismatch("Char not in set", parse(parser, "\u3336"), 0);
	}

	public void testCaseSensitive() {
		pobs.PObject parser = new pobs.parser.PRange("a-c");

		assertMismatch("Case sensitive", parse(parser, "B"), 0);

		pobs.PContext context = new pobs.PContext();
		context.getDirective().setCaseSensitive(false);
		assertMatch(
			"Case insensitive",
			parser.process(new pobs.scanner.PStringScanner("B"), 0, context),
			1);
	}

	public void testMultipleRanges() {
		pobs.PObject parser = new pobs.parser.PRange("abg-iln-pz");

		assertMatch("Lone", parse(parser, "b"), 1);
		assertMatch("In first set", parse(parser, "h"), 1);
		assertMismatch("Between first set and lone", parse(parser, "j"), 0);
		assertMatch("Lone inbetween sets", parse(parser, "l"), 1);
		assertMatch("In second set", parse(parser, "o"), 1);
		assertMatch("Lone after second set", parse(parser, "z"), 1);
	}

	public void testMarkerLeading() {
		pobs.PObject parser = new pobs.parser.PRange("-b-d");

		assertMatch("Char in set", parse(parser, "c"), 1);
		assertMatch("Marker in set", parse(parser, "-"), 1);
		assertMismatch("In set at marker side", parse(parser, "a"), 0);
	}

	public void testMarkerTrailing() {
		pobs.PObject parser = new pobs.parser.PRange("a-c-");

		assertMatch("Char in set", parse(parser, "b"), 1);
		assertMatch("Marker in set", parse(parser, "-"), 1);
		assertMismatch("In set at marker side", parse(parser, "d"), 0);
	}

	public void testMarkerCustom() {
		pobs.PObject parser = new pobs.parser.PRange("d:hk-t", ':');

		assertMatch("First char in set", parse(parser, "d"), 1);
		assertMatch("Old marker in set", parse(parser, "-"), 1);
		assertMatch("Char in set", parse(parser, "f"), 1);
		assertMismatch("New marker", parse(parser, ":"), 0);
		assertMatch("First char in non-set", parse(parser, "k"), 1);
		assertMatch("Last char in non-set", parse(parser, "t"), 1);
		assertMismatch("Char in non-set", parse(parser, "r"), 0);
	}
}
