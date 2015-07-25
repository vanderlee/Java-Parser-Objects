/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package example.nanoc;

/**
 * @author Franz-Josef Elmer
 */
public class ReturnStatement extends Statement {
	private final Operation expression;
	
	public ReturnStatement(Block parent, Operation expression) {
		super(parent);
		this.expression = expression;
	}

	public Object execute(Block block) {
		return new ReturnValue(expression == null ? null 
												  : expression.execute(block));
	}

}
