/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package test;

/**
 * @author Martijn W. van der Lee
 */
public class POptionalTest extends TestCaseParser {
    java.lang.String log;

    public POptionalTest(String arg0) {
        super(arg0);
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(POptionalTest.class);
    }

    public void testOptional() {
        pobs.PObject parser = new pobs.parser.POptional(new pobs.parser.PChar('a'));

        assertMatch("Zero-length input", parse(parser, ""), 0);
        assertMatch("First input only", parse(parser, "aaaa"), 1);
        assertMatch("Option not found", parse(parser, "b", 0), 0);
        assertMatch("Non-zero beginning", parse(parser, "bad", 1), 1);      
    }
}