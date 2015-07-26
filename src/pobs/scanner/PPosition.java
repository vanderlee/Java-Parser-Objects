/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package pobs.scanner;

/**
 * Immutable class carrying a line number and a column number.
 * 
 * @author Franz-Josef Elmer
 */
public class PPosition {
	private final int line;
	private final int column;
	
	/**
	 * Creates an instance for the specified line number and colum number.
	 */
	public PPosition(int line, int column) {
		this.line = line;
		this.column = column;
	}
	
	public int getColumn() {
		return column;
	}
	
	public int getLine() {
		return line;
	}
	
	public String toString() {
		return "line " + line + " column " + column;
	}
}
