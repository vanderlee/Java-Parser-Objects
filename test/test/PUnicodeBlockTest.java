/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package test;

import java.lang.Character.UnicodeBlock;

/**
 * @author Martijn W. van der Lee
 */
public class PUnicodeBlockTest extends TestCaseParser {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(PUnicodeBlockTest.class);
    }

    public PUnicodeBlockTest(String arg0) {
        super(arg0);
    }

    public void testCurrencySymbols() {
        pobs.PObject parser = new pobs.parser.PUnicodeBlock(
                UnicodeBlock.CURRENCY_SYMBOLS);

        assertMismatch("Zero-length input", parse(parser, ""), 0);
        assertMatch("Currency Symbols", parse(parser, "\u20AC"), 1); // Euro
        assertMismatch("Dingbats", parse(parser, "\u2710"), 0); // Crayon
        assertMismatch("General Punctuation", parse(parser, "\u2030"), 0); // Promille
        assertMismatch("Undefined", parse(parser, "0x0FFFF"), 0);
        assertMatch("Match beyond begin", parse(parser, "_\u20AC_", 1), 1);
    }

    public void testDingbats() {
        pobs.PObject parser = new pobs.parser.PUnicodeBlock(
                UnicodeBlock.DINGBATS);

        assertMismatch("Zero-length input", parse(parser, ""), 0);
        assertMismatch("Currency Symbols", parse(parser, "\u20AC"), 0); // Euro
        assertMatch("Dingbats", parse(parser, "\u2710"), 1); // Crayon
        assertMismatch("General Punctuation", parse(parser, "\u2030"), 0); // Promille
        assertMismatch("Undefined", parse(parser, "0xFFFF"), 0);
        assertMatch("Match beyond begin", parse(parser, "_\u2710_", 1), 1);
    }

    public void testGeneralPunctuation() {
        pobs.PObject parser = new pobs.parser.PUnicodeBlock(
                UnicodeBlock.GENERAL_PUNCTUATION);

        assertMismatch("Zero-length input", parse(parser, ""), 0);
        assertMismatch("Currency Symbols", parse(parser, "\u20AC"), 0); // Euro
        assertMismatch("Dingbats", parse(parser, "\u2710"), 0); // Crayon
        assertMatch("General Punctuation", parse(parser, "\u2030"), 1); // Promille
        assertMismatch("Undefined", parse(parser, "0x0FFFF"), 0);
        assertMatch("Match beyond begin", parse(parser, "_\u2030_", 1), 1);
    }
}