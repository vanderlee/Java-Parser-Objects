/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package example.nanoc;

/**
 * @author Franz-Josef Elmer
 */
public class Assignment implements Operation {
	private final Variable variable;
	private final Operation expression;

	public Assignment(Variable variable, Operation expression) {
		this.variable = variable;
		this.expression = expression;
	}

	public Object execute(Block block) {
		Object result = expression.execute(block);
		variable.setValue(result);
		return result;
	}

}
