/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package test;

import pobs.action.PString;
import pobs.parser.PChar;
import pobs.PObject;

/**
 * @author	Martijn W. van der Lee 
 */
public class PStringTest extends TestCaseParser {
	public PStringTest(String arg0) {
		super(arg0);
	}

	public static void main(String[] args) {
		junit.textui.TestRunner.run(PStringTest.class);
	}

	public void testSet() {
	    PString string = new PString("a");
	    PObject parser = new PChar('b').setMatchAction(string.set());
	    
	    parse(parser, "bc");
	    
		assertEquals("Set from input", "b", string.toString());
	}
	
	public void testSetConstant() {
	    PString string = new PString("a");
	    PObject parser = new PChar('b').setMatchAction(string.set("c"));
	    
	    parse(parser, "bc");
	    
		assertEquals("Set constant", "c", string.toString());
	}
	
	public void testConcat() {
	    PString string = new PString("a");
	    PObject parser = new PChar('b').setMatchAction(string.concat());
	    
	    parse(parser, "bc");
	    
		assertEquals("Set from input", "ab", string.toString());
	}
	
	public void testConcatConstant() {
	    PString string = new PString("a");
	    PObject parser = new PChar('b').setMatchAction(string.concat("c"));
	   
	    parse(parser, "bc");
	    
		assertEquals("Set constant", "ac", string.toString());
	}
	
	public void testClear() {
	    PString string = new PString("a");
	    PObject parser = new PChar('b').setMatchAction(string.clear());
	   
	    parse(parser, "bc");
	    
		assertEquals("Set constant", "", string.toString());
	}
	
	public void testTrim() {
	    PString string = new PString(" a ");
	    PObject parser = new PChar('b').setMatchAction(string.trim());
	   
	    parse(parser, "bc");
	    
		assertEquals("Set constant", "a", string.toString());
	}
	
	public void testToLowerCase() {
	    PString string = new PString("aA");
	    PObject parser = new PChar('b').setMatchAction(string.toLowerCase());
	   
	    parse(parser, "bc");
	    
		assertEquals("Set constant", "aa", string.toString());
	}
	
	public void testToUpperCase() {
	    PString string = new PString("aA");
	    PObject parser = new PChar('b').setMatchAction(string.toUpperCase());
	   
	    parse(parser, "bc");
	    
		assertEquals("Set constant", "AA", string.toString());
	}
	
	//TODO: Test toUpper-/-LowerCase with Locale 
}