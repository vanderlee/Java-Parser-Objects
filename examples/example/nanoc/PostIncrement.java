/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package example.nanoc;

/**
 * @author Franz-Josef Elmer
 */
public class PostIncrement implements Operation {
	private final Variable variable;
	private final int amount;
	
	public PostIncrement(final Variable variable, int amount) {
		super();
		this.variable = variable;
		this.amount = amount;
	}

	public Object execute(Block block) {
		Object result = variable.getValue();
		if (result instanceof Number) {
			variable.setValue(new Integer(((Number) result).intValue() + amount));
		}
		return result;
	}

}
