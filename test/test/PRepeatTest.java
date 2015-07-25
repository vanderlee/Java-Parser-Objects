/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package test;

/**
 * @author Martijn W. van der Lee
 */
public class PRepeatTest extends TestCaseParser {
    public PRepeatTest(String arg0) {
        super(arg0);
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(PRepeatTest.class);
    }

    public void testLimits() {
        pobs.PObject parser = new pobs.parser.PRepeat(
                new pobs.parser.PChar('x'), 0, 2);

        assertMatch("Zero-length input", parse(parser, ""), 0);
        assertMatch("Input size 1", parse(parser, "x"), 1);
        assertMatch("Input size 2", parse(parser, "xx"), 2);
        assertMatch("Input size 3", parse(parser, "xxx"), 2);
        assertMatch("Non-zero beginning", parse(parser, "axxxa", 1), 2);
    }

    public void testExactConstructor() {
        pobs.PObject parser = new pobs.parser.PRepeat(
                new pobs.parser.PChar('x'), 2);

        assertMismatch("Zero-length input", parse(parser, ""), 0);
        assertMismatch("Input size 1", parse(parser, "x"), 1);
        assertMatch("Input size 2", parse(parser, "xx"), 2);
        assertMatch("Input size 3", parse(parser, "xxx"), 2);
        assertMatch("Non-zero beginning", parse(parser, "axxxa", 1), 2);
    }

    public void testSkipper() {
        pobs.PObject parser = new pobs.parser.PRepeat(
                new pobs.parser.PChar('x'), 1, 3);
        pobs.PDirective dir = new pobs.PDirective(new pobs.parser.PWhitespace());

        assertMatch("Input size 1, unskipped", parse(parser, "x", dir), 1);
        assertMatch("Input size 2, unskipped", parse(parser, "xx", dir), 2);
        assertMatch("Input size 3, unskipped", parse(parser, "xxx", dir), 3);
        assertMatch("Input size 1, skipped", parse(parser, "\n\n\nxa", dir), 4);
        assertMatch("Input size 2, skipped", parse(parser, " x \t x", dir), 6);
        assertMatch("Input size 3, skipped", parse(parser, "\rxx\rx", dir), 5);
        assertMatch("Only skip at start", parse(parser, " x ", dir), 2);
    }

    public void testMultipleConstructor() {
        pobs.PObject parser = new pobs.parser.PRepeat(
                new pobs.parser.PChar('x'));

        assertMismatch("Zero-length input", parse(parser, ""), 0);
        assertMatch("Input size 1", parse(parser, "x"), 1);
        assertMatch("Input size 2", parse(parser, "xx"), 2);
        assertMatch("Input size 3", parse(parser, "xxx"), 3);
        assertMatch("Non-zero beginning", parse(parser, "axxxa", 1), 3);
    }

    public void testConstructor() {
        pobs.PObject parser = new pobs.parser.PRepeat(null, 1, 2);

        try {
            parse(parser, "aaab");
            fail("2-limit constructor, null parser, no exception thrown");
        } catch (NullPointerException e) {} catch (Throwable e) {
            fail("2-limit constructor, null parser, wrong exception thrown");
        }
    }
}