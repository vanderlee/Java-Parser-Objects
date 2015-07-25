/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package example.nanoc;

/**
 * @author Franz-Josef Elmer
 */
public abstract class Statement implements Operation {
	private final Block parent;
	
	public Statement(Block parent) {
		this.parent = parent;
	}

	public Block getParent() {
		return parent;
	}

}
