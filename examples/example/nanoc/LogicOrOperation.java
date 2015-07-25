/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package example.nanoc;

/**
 * @author Franz-Josef Elmer
 */
public class LogicOrOperation implements Operation {
	private Operation leftOperand;
	private Operation rightOperand;
	
	public LogicOrOperation(Operation leftOperand, Operation rightOperand) {
		this.leftOperand = leftOperand;
		this.rightOperand = rightOperand;
	}

	public Object execute(Block block) {
		if (!Block.NULL.equals(leftOperand.execute(block)) 
				|| !Block.NULL.equals(rightOperand.execute(block))) {
			return new Integer(1);
		} else {
			return Block.NULL;
		}
	}
}
