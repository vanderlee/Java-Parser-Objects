/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package example.nanoc;

/**
 * @author Franz-Josef Elmer
 */
public class SimpleStatement extends Statement {
	private final Operation expression;
	
	public SimpleStatement(Block parent, Operation expression) {
		super(parent);
		this.expression = expression;
	}

	public Object execute(Block block) {
		return expression.execute(block);
	}

}
