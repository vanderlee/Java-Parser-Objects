/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package test;

/**
 * @author	Martijn W. van der Lee 
 */
public class PLimitTest extends test.TestCaseParser {
	public PLimitTest(String arg0) {
        super(arg0);
    }
    
	public static void main(String[] args) {
		junit.textui.TestRunner.run(PLimitTest.class);
	}
	
	public void testPositiveRanges() {
		pobs.PObject parser = new pobs.parser.PLimit(pobs.utility.POBS.UNSIGNED_INT, 100, 2000);
						
		assertMismatch("Zero (below range)", parse(parser, "0"), 1); 		
		assertMatch("In range", parse(parser, "123"), 3);
		assertMatch("Low boundary", parse(parser, "100"), 3);
		assertMatch("High boundary", parse(parser, "2000"), 4);
		assertMismatch("Below range", parse(parser, "12"), 2);
		assertMismatch("Above range", parse(parser, "10123"), 5);
	}
	
	public void testSignedRanges() {
		pobs.PObject parser = new pobs.parser.PLimit(pobs.utility.POBS.SIGNED_INT, -100, 100);
						
		assertMatch("Zero (match)", parse(parser, "0"), 1);
		assertMatch("Positive in range", parse(parser, "23"), 2);
		assertMatch("Negative in range", parse(parser, "-23"), 3);
		assertMatch("Low bound (negative)", parse(parser, "-100"), 4);
		assertMatch("High bound (positive)", parse(parser, "100"), 3);
		assertMismatch("Below range", parse(parser, "-200"), 4);
		assertMismatch("Above range", parse(parser, "1000"), 4);
	}	
}
