/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package example.nanoc;

/**
 * @author Franz-Josef Elmer
 */
public class Constant implements Operation {
	private final Object value;
	
	public Constant(Object value) {
		this.value = value;
	}

	public Object execute(Block block) {
		return value;
	}

}
