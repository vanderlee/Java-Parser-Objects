/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package example.nanoc;

import java.util.Enumeration;
import java.util.Hashtable;

/**
 * @author Franz-Josef Elmer
 */
public class FunctionRepository {
	private final Hashtable functions = new Hashtable();
	
	public void addFunction(Function function) {
		String name = function.getName();
		if (hasFunction(name)) {
			throw new IllegalArgumentException(
						"There exists already a function '" + name + "'.");
		}
		functions.put(name, function);
	}
	
	public Function getFunction(String name) {
		return (Function) functions.get(name);
	}
	
	public boolean hasFunction(String name) {
		return functions.containsKey(name);
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		Enumeration elements = functions.elements();
		while (elements.hasMoreElements()) {
			buffer.append(elements.nextElement()).append('\n');
		}
		return new String(buffer);
	}
}
