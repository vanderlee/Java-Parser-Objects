/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package example.nanoc;

/**
 * @author Franz-Josef Elmer
 */
public class IntDiffOperation extends IntegerOperation {
	public IntDiffOperation(Operation leftOperand, Operation rightOperand) {
		super(leftOperand, rightOperand);
	}

	protected int operation(int leftValue, int rightValue) {
		return leftValue - rightValue;
	}
}
