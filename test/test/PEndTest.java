/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package test;

/**
 * @author	Martijn W. van der Lee 
 */
public class PEndTest extends TestCaseParser {

    public PEndTest(String arg0) {
        super(arg0);
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(PEndTest.class);
    }

    public void testAny() {
        pobs.PObject parser = new pobs.parser.PEnd();

		assertMatch("Zero-length input", parse(parser, ""), 0);
		assertMismatch("Any input", parse(parser, "a"), 0);
		assertMatch("Non-zero beginning", parse(parser, "abc", 3), 0);
		assertMismatch("Non-zero beginning, input", parse(parser, "abc", 2), 0);
   }
}