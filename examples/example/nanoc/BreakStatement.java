/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package example.nanoc;

/**
 * @author Franz-Josef Elmer
 */
public class BreakStatement extends Statement {
	public static final Object BREAK = new Object();

	public BreakStatement(Block parent) {
		super(parent);
	}

	public Object execute(Block block) {
		return BREAK;
	}

}
