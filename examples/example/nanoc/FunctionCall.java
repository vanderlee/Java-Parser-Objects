/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package example.nanoc;

/**
 * @author Franz-Josef Elmer
 */
public class FunctionCall implements Operation {
	private final Function function;
	private final Operation[] arguments;
	
	public FunctionCall(Function function, Operation[] arguments) {
		this.function = function;
		this.arguments = arguments;
	}

	public Object execute(Block block) {
		Object[] argumentValues = new Object[arguments.length];
		for (int i = 0; i < argumentValues.length; i++) {
			argumentValues[i] = arguments[i].execute(block);
		}
		return function.call(argumentValues);
	}

}
