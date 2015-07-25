/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package test;

/**
 * @author	Martijn W. van der Lee 
 */
public class PParserPointerTest extends TestCaseParser {
	public PParserPointerTest(String arg0) {
		super(arg0);
	}

	public static void main(String[] args) {
		junit.textui.TestRunner.run(PParserPointerTest.class);
	}

	public void testPointer() {
		pobs.parser.PParserPointer parser = new pobs.parser.PParserPointer();

		//	not yet assigned
		try {
			parse(parser, "a");
			fail("IllegalStateException expected");
		} catch (IllegalStateException e) {
			// okay
		}
		
		pobs.PParser any = new pobs.parser.PAny();
		parser.set(any);

		assertMatch("First input", parse(parser, "aaaa"), 1);
	}
}