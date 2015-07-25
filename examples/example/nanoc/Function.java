/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package example.nanoc;

import java.util.Hashtable;
import java.util.Vector;

/**
 * @author Franz-Josef Elmer
 */
public class Function extends Variable {
	private final Variable[] arguments;
	private Block block;
	
	public static Function createFunctionFromSignature(Vector nameAndArguments) {
		if (nameAndArguments.size() == 0) {
			throw new IllegalArgumentException(
							"Cannot create function with no name!");
		}
		String name = (String) nameAndArguments.elementAt(0);
		Variable[] args = new Variable[nameAndArguments.size() - 1];
		for (int i = 0; i < args.length; i++) {
			args[i] = new Variable((String) nameAndArguments.elementAt(i + 1));
		}
		return new Function(name, args);
	}
	
	public Function(String name, Variable[] arguments) {
		super(name);
		this.arguments = arguments;
		Hashtable argsHash = new Hashtable();
		for (int i = 0; i < arguments.length; i++) {
			String argName = arguments[i].getName();
			if (argsHash.containsKey(argName)) {
				throw new IllegalArgumentException("Argument '" + argName 
						+ "' occurs at least twice in the signature of "
						+ "function '" + name + "'.");
			} else {
				argsHash.put(argName, argName);
			}
		}
	}

	public Variable[] getArguments() {
		return arguments;
	}
	
	public void setFunctionBlock(Block block) {
		this.block = block;
		block.addFunctionArguments(this);
	}
	
	public Object call(Object[] argumentValues) {
		int n = Math.min(arguments.length, argumentValues.length);
		for (int i = 0; i < n; i++) {
			arguments[i].setValue(argumentValues[i]);
		}
		Object result = block.execute(block);
		if (result instanceof ReturnValue) {
			result = ((ReturnValue) result).getValue();
		}
		return result;
	}
	
	public String toString() {
		StringBuffer result = new StringBuffer(getName()).append('(');
		for (int i = 0; i < arguments.length; i++) {
			result.append(arguments[i].getName());
			if (i + 1 < arguments.length) {
				result.append(", ");
			}
		}
		result.append(")");
		if (block != null) {
			result.append(": ").append(block.getLocalVariables());
		}
		return new String(result);
	}
}
