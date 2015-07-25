/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package example.nanoc;

/**
 * @author Franz-Josef Elmer
 */
public class NOP implements Operation {
	public static final NOP NOP = new NOP();

	private NOP() {}

	public Object execute(Block block) {
		return Block.NULL;
	}

}
