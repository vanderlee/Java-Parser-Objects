/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package example.nanoc;

/**
 * @author Franz-Josef Elmer
 */
public class NegateOperation implements Operation {
	private static final Integer FALSE = new Integer(0);
	private static final Integer TRUE = new Integer(1);
	private final Operation expression;
	
	public NegateOperation(Operation expression) {
		this.expression = expression;
	}

	public Object execute(Block block) {
		Object result = expression.execute(block);
		if (result instanceof Number == false) {
			throw new IllegalArgumentException(
					"'!' operation only allowed for numbers: " + result);
		}
		return ((Number) result).intValue() == 0 ? TRUE : FALSE;
	}

}
