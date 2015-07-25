/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package example.nanoc;


/**
 * @author Franz-Josef Elmer
 */
public class Variable {
	private final String name;
	private Object value;
	
	public Variable(String name) {
		this.name = name;
	}
		
	public Object getValue() {
		return value;
	}
	
	public void setValue(Object value) {
		this.value = value;
	}
	
	public String getName() {
		return name;
	}
	
	public String toString() {
		return value == null ? name : name + "=" + value;
	}
}
