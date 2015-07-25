/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package example.nanoc;

/**
 * @author Franz-Josef Elmer
 * @author Martijn W. van der Lee
 */
public class PreIncrement implements Operation {
	private final Variable variable;
	private final int amount;
	
	public PreIncrement(final Variable variable, int amount) {
		super();
		this.variable = variable;
		this.amount = amount;
	}

	public Object execute(Block block) {
		Object result = variable.getValue();
		if (result instanceof Number) {
			Integer value = new Integer(((Number) result).intValue() + amount);
			variable.setValue(value);
			return value;
		}
		return result;
	}

}
