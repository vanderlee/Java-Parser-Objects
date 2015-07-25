/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package example.nanoc;

/**
 * @author Franz-Josef Elmer
 */
public class VariableReading implements Operation {
	private final Variable variable;
	
	public VariableReading(Variable variable) {
		this.variable = variable;
	}

	public Object execute(Block block) {
		return variable.getValue();
	}

}
