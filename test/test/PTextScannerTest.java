/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package test;

import pobs.scanner.PPosition;
import pobs.scanner.PStringScanner;
import pobs.scanner.PTextScanner;
import junit.framework.TestCase;

/**
 * @author Franz-Josef Elmer
 */
public class PTextScannerTest extends TestCase {
	public PTextScannerTest(String arg0) {
		super(arg0);
	}
	
	private void check(int line, int column, int index, String text) {
		PTextScanner scanner = new PTextScanner(new PStringScanner(text));
		PPosition position = scanner.getPosition(index);
		assertEquals("line number", line, position.getLine());
		assertEquals("column number", column, position.getColumn());
	}
	
	private void check(PPosition[] positions, int[] indices, String text) {
		PTextScanner scanner = new PTextScanner(new PStringScanner(text));
		for (int i = 0; i < positions.length; i++) {
			PPosition position = scanner.getPosition(indices[i]);
			assertEquals("line number for " + i, positions[i].getLine(), 
					     position.getLine());
			assertEquals("column number for " + i, positions[i].getColumn(), 
						 position.getColumn());
		}
	}
	
	public void testSimple() {
		check(0, 0, 0, "");
		check(0, 0, 0, "123456789");
		check(0, 5, 5, "123456789");
	}
	
	public void testUnixNewLine() {
		check(0, 2,  2, "hello\nworld\n\nhow are you?\n");
		check(0, 5,  5, "hello\nworld\n\nhow are you?\n");
		check(1, 0,  6, "hello\nworld\n\nhow are you?\n");
		check(1, 1,  7, "hello\nworld\n\nhow are you?\n");
		//               012345 678901 2 3456789012345 67890
		check(1, 4, 10, "hello\nworld\n\nhow are you?\n");
		check(1, 5, 11, "hello\nworld\n\nhow are you?\n");
		check(2, 0, 12, "hello\nworld\n\nhow are you?\n");
		check(3, 0, 13, "hello\nworld\n\nhow are you?\n");
		check(3, 1, 14, "hello\nworld\n\nhow are you?\n");
		check(3,12, 25, "hello\nworld\n\nhow are you?\n");
		check(4, 0, 26, "hello\nworld\n\nhow are you?\n");
		check(4, 0, 27, "hello\nworld\n\nhow are you?\n");
		check(4, 0, 28, "hello\nworld\n\nhow are you?\n");
	}
	
	public void testMacNewLine() {
		check(0, 2,  2, "hello\rworld\r\rhow are you?\r");
		check(0, 5,  5, "hello\rworld\r\rhow are you?\r");
		check(1, 0,  6, "hello\rworld\r\rhow are you?\r");
		check(1, 1,  7, "hello\rworld\r\rhow are you?\r");
		//               012345 678901 2 3456789012345 67890
		check(1, 4, 10, "hello\rworld\r\rhow are you?\r");
		check(1, 5, 11, "hello\rworld\r\rhow are you?\r");
		check(2, 0, 12, "hello\rworld\r\rhow are you?\r");
		check(3, 0, 13, "hello\rworld\r\rhow are you?\r");
		check(3, 1, 14, "hello\rworld\r\rhow are you?\r");
		check(3,12, 25, "hello\rworld\r\rhow are you?\r");
		check(4, 0, 26, "hello\rworld\r\rhow are you?\r");
		check(4, 0, 27, "hello\rworld\r\rhow are you?\r");
		check(4, 0, 28, "hello\rworld\r\rhow are you?\r");
	}
	
	public void testWinNewLine() {
		check(0, 2,  2, "hello\r\nworld\r\n\r\nhow are you?\r\n");
		check(0, 5,  5, "hello\r\nworld\r\n\r\nhow are you?\r\n");
		check(1, 0,  6, "hello\r\nworld\r\n\r\nhow are you?\r\n");
		check(1, 0,  7, "hello\r\nworld\r\n\r\nhow are you?\r\n");
		check(1, 1,  8, "hello\r\nworld\r\n\r\nhow are you?\r\n");
		//               012345 6 789012 3 4 5 6789012345678 9 0
		check(1, 4, 11, "hello\r\nworld\r\n\r\nhow are you?\r\n");
		check(1, 5, 12, "hello\r\nworld\r\n\r\nhow are you?\r\n");
		check(2, 0, 13, "hello\r\nworld\r\n\r\nhow are you?\r\n");
		check(2, 0, 14, "hello\r\nworld\r\n\r\nhow are you?\r\n");
		check(3, 0, 15, "hello\r\nworld\r\n\r\nhow are you?\r\n");
		check(3, 0, 16, "hello\r\nworld\r\n\r\nhow are you?\r\n");
		check(3, 1, 17, "hello\r\nworld\r\n\r\nhow are you?\r\n");
		check(3,12, 28, "hello\r\nworld\r\n\r\nhow are you?\r\n");
		check(4, 0, 29, "hello\r\nworld\r\n\r\nhow are you?\r\n");
		check(4, 0, 30, "hello\r\nworld\r\n\r\nhow are you?\r\n");
		check(4, 0, 31, "hello\r\nworld\r\n\r\nhow are you?\r\n");
		check(4, 0, 32, "hello\r\nworld\r\n\r\nhow are you?\r\n");
	}
	
	public void testTab() {
		check(0,  0, 0, "\t1\t12\t123");
		check(0,  8, 1, "\t1\t12\t123");
		check(0,  9, 2, "\t1\t12\t123");
		check(0, 16, 3, "\t1\t12\t123");
		check(0,  8, 8, "1234567\ta");
		check(0, 16, 9, "12345678\ta");
		
		check(1,  8, 4, "a\nb\tc");
	}
	
	public void testMultipleCalls() {
		check(new PPosition[] 
				{new PPosition(0,  0),
				 new PPosition(0, 16),
				 new PPosition(0,  8),
				 new PPosition(3,  0),
				 new PPosition(1,  8),
				 new PPosition(0,  9)}, new int[] {0, 3, 1, 18, 15, 2},
			  "\t1\t12\t123\nabcd\tg\n\nh");
		     //0 12 345 6789 01234 56 7 8
	}
}
