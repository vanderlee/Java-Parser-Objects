/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package test;

/**
 * @author Martijn W. van der Lee
 */
public class PSequenceTest extends TestCaseParser {
    public PSequenceTest(String arg0) {
        super(arg0);
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(PSequenceTest.class);
    }

    public void testConstructor2() {
        pobs.PParser a = new pobs.parser.PChar('a');
        pobs.PParser parser = new pobs.parser.PSequence(a, a);

        assertMismatch("Zero-length input", parse(parser, ""), 0);
        assertMismatch("Mismatch on second", parse(parser, "ab"), 1);
        assertMatch("Match all", parse(parser, "aab"), 2);
        assertMatch("Non-zero beginning", parse(parser, "baad", 1), 2);
    }

    public void testConstructor3() {
        pobs.PParser a = new pobs.parser.PChar('a');
        pobs.PParser parser = new pobs.parser.PSequence(a, a, a);

        assertMismatch("Zero-length input", parse(parser, ""), 0);
        assertMismatch("Mismatch on second", parse(parser, "ab"), 1);
        assertMismatch("Mismatch on third", parse(parser, "aab"), 2);
        assertMatch("Match all", parse(parser, "aaab"), 3);
        assertMatch("Non-zero beginning", parse(parser, "baaad", 1), 3);
    }

    public void testConstructor4() {
        pobs.PParser a = new pobs.parser.PChar('a');
        pobs.PParser parser = new pobs.parser.PSequence(a, a, a, a);

        assertMismatch("Zero-length input", parse(parser, ""), 0);
        assertMismatch("Mismatch on second", parse(parser, "ab"), 1);
        assertMismatch("Mismatch on third", parse(parser, "aab"), 2);
        assertMismatch("Mismatch on fourth", parse(parser, "aaab"), 3);
        assertMatch("Match all", parse(parser, "aaaab"), 4);
        assertMatch("Non-zero beginning", parse(parser, "baaaad", 1), 4);
    }

    public void testConstructor5() {
        pobs.PParser a = new pobs.parser.PChar('a');
        pobs.PParser parser = new pobs.parser.PSequence(a, a, a, a, a);

        assertMismatch("Zero-length input", parse(parser, ""), 0);
        assertMismatch("Mismatch on second", parse(parser, "ab"), 1);
        assertMismatch("Mismatch on third", parse(parser, "aab"), 2);
        assertMismatch("Mismatch on fourth", parse(parser, "aaab"), 3);
        assertMismatch("Mismatch on fifth", parse(parser, "aaaab"), 4);
        assertMatch("Match all", parse(parser, "aaaaab"), 5);
        assertMatch("Non-zero beginning", parse(parser, "baaaaad", 1), 5);
    }

    public void testConstructorArray() {
        pobs.PParser a = new pobs.parser.PChar('a');
        pobs.PParser parser = new pobs.parser.PSequence(new pobs.PObject[] { a,
                a, a, a, a, a });

        assertMismatch("Zero-length input", parse(parser, ""), 0);
        assertMismatch("Mismatch on second", parse(parser, "ab"), 1);
        assertMismatch("Mismatch on third", parse(parser, "aab"), 2);
        assertMismatch("Mismatch on fourth", parse(parser, "aaab"), 3);
        assertMismatch("Mismatch on fifth", parse(parser, "aaaab"), 4);
        assertMismatch("Mismatch on sixth", parse(parser, "aaaaab"), 5);
        assertMatch("Match all", parse(parser, "aaaaaab"), 6);
        assertMatch("Non-zero beginning", parse(parser, "baaaaaad", 1), 6);
    }

    public void testSkipper() {
        pobs.PParser a = new pobs.parser.PChar('a');
        pobs.PParser parser = new pobs.parser.PSequence(a, a, a);
        pobs.PDirective dir = new pobs.PDirective(new pobs.parser.PWhitespace());

        assertMismatch("Zero-length input", parse(parser, "\n \t\r", dir), 4);
        assertMismatch("Mismatch on second", parse(parser, "a\rb", dir), 2);
        assertMismatch("Mismatch on third", parse(parser, "a   a b", dir), 6);
        assertMatch("Match all", parse(parser, " aa  ab", dir), 6);
        assertMatch("Match only whitespaces at start", parse(parser, " aaa ",
                dir), 4);
    }

    public void testConstructorNull() {
        try {
            pobs.PParser parser = new pobs.parser.PSequence(null);
            fail("Null parser array, no IllegalArgumentException thrown");
        } catch (IllegalArgumentException e) {} catch (Throwable e) {}
    }

    public void testConstructorEmpty() {
        try {
            pobs.PParser parser = new pobs.parser.PSequence(
                    new pobs.PObject[] {});
            fail("Empty parser array, no IllegalArgumentException thrown");
        } catch (IllegalArgumentException e) {}
    }
}