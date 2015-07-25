/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package example.nanoc;

/**
 * @author Franz-Josef Elmer
 */
public class MinusOperation implements Operation {
	private final Operation expression;
	
	public MinusOperation(Operation expression) {
		this.expression = expression;
	}

	public Object execute(Block block) {
		Object result = expression.execute(block);
		if (result instanceof Number == false) {
			throw new IllegalArgumentException(
					"'.' operation only allowed for numbers: " + result);
		}
		return new Integer(-((Number) result).intValue());
	}

}
