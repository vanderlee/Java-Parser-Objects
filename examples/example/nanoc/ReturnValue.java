/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package example.nanoc;

/**
 * @author Franz-Josef Elmer
 */
public class ReturnValue {
	private final Object value;
	
	public ReturnValue(Object value) {
		this.value = value;
	}
	
	public Object getValue() {
		return value;
	}
}
