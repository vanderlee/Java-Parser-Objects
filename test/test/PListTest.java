/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package test;

import pobs.parser.PList;

/**
 * @author Martijn W. van der Lee
 */
public class PListTest extends TestCaseParser {
    public PListTest(String arg0) {
        super(arg0);
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(PListTest.class);
    }

    public void testOptional() {
        pobs.PObject digit = new pobs.parser.PDigit();
        pobs.PObject delimiter = new pobs.parser.PChar(',');
        pobs.PObject parser = new pobs.parser.PList(digit, delimiter, 0, 1);

        assertMatch("Zero-length input", parse(parser, ""), 0);
        assertMatch("All input", parse(parser, "1"), 1);
        assertMatch("First input only", parse(parser, "1,2"), 1);
        assertMatch("Non-zero beginning", parse(parser, "x1,2x", 1), 1);
    }

    public void testMultiple() {
        pobs.PObject digit = new pobs.parser.PDigit();
        pobs.PObject delimiter = new pobs.parser.PChar(',');
        pobs.PObject parser = new pobs.parser.PList(digit, delimiter);

        assertMismatch("Zero-length input", parse(parser, ""), 0);
        assertMatch("All input", parse(parser, "1,2,3"), 5);
        assertMatch("Broken list, first two only", parse(parser, "1,2,"), 3);
        assertMatch("Multiple", parse(parser, "1,2,3x"), 5);
        assertMatch("Non-zero beginning", parse(parser, "x1,2,3x", 1), 5);
    }

    public void testKleene() {
        pobs.PObject digit = new pobs.parser.PDigit();
        pobs.PObject delimiter = new pobs.parser.PChar(',');
        pobs.PObject parser = new pobs.parser.PList(digit, delimiter, 0, 99);

        assertMatch("Zero-length input", parse(parser, ""), 0);
        assertMatch("All input", parse(parser, "1,2,3"), 5);
        assertMatch("Broken list, first two only", parse(parser, "1,2,"), 3);
        assertMatch("Multiple", parse(parser, "1,2,3x"), 5);
        assertMatch("Non-zero beginning", parse(parser, "x1,2,3x", 1), 5);
        assertMatch("Non-zero beginning, no match", parse(parser, "xx", 1), 0);
    }

    public void testExact() {
        pobs.PObject digit = new pobs.parser.PDigit();
        pobs.PObject delimiter = new pobs.parser.PChar(',');
        pobs.PObject parser = new pobs.parser.PList(digit, delimiter, 2);

        assertMismatch("Zero-length input", parse(parser, ""), 0);
        assertMismatch("Not enough input", parse(parser, "1,"), 1);
        assertMatch("All input", parse(parser, "1,2,"), 3);
        assertMatch("First two only", parse(parser, "1,2,3"), 3);
        assertMatch("Non-zero, first two", parse(parser, "x1,2,3x", 1), 3);
        assertMismatch("Non-zero, not enough input", parse(parser, "x1,x", 1),
                1);
    }

    public void testSkipper() {
        pobs.PObject digit = new pobs.parser.PDigit();
        pobs.PObject delimiter = new pobs.parser.PChar(',');
        pobs.PObject parser = new pobs.parser.PList(digit, delimiter);
        pobs.PDirective dir = new pobs.PDirective(new pobs.parser.PWhitespace());

        assertMismatch("Zero-length input", parse(parser, "", dir), 0);
        assertMismatch("No data", parse(parser, " \n\t\r ", dir), 5);
        assertMatch("Only match at start", parse(parser, " 1 ,  2 , ", dir), 7);
    }
    
    public void testSkipperKleene() {
        pobs.PObject digit = new pobs.parser.PDigit();
        pobs.PObject delimiter = new pobs.parser.PChar(',');
        pobs.PObject parser = new pobs.parser.PList(digit, delimiter, 0, PList.INFINITE);
        pobs.PDirective dir = new pobs.PDirective(new pobs.parser.PWhitespace());

        assertMatch("Zero-length input", parse(parser, "", dir), 0);
        assertMatch("No data", parse(parser, " \n\t\r ", dir), 0);
        assertMatch("Only match at start", parse(parser, " 1 ,  2 , ", dir), 7);
    }
}