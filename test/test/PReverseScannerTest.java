/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package test;

import pobs.scanner.PStringScanner;
import pobs.scanner.PReverseScanner;
import junit.framework.TestCase;

/**
 * @author Martijn W. van der Lee
 */
public class PReverseScannerTest extends TestCase {
	public PReverseScannerTest(String arg0) {
		super(arg0);
	}
	
	public void testSimple() {
		PReverseScanner scanner = new PReverseScanner(new PStringScanner("blah"));
		
		assertEquals("Index 0", 'h', scanner.charAt(0));
		assertEquals("Index 1", 'a', scanner.charAt(1));
		assertEquals("Index 2", 'l', scanner.charAt(2));
		assertEquals("Index 3", 'b', scanner.charAt(3));
	    	
		assertEquals("Substring middle 2", "al", scanner.substring(1, 3));
		assertEquals("Substring first 3", "hal", scanner.substring(0, 3));
		assertEquals("Substring last 3", "alb", scanner.substring(1, 4));
	}
}
