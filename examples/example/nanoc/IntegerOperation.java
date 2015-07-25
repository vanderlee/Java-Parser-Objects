/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package example.nanoc;

/**
 * @author Franz-Josef Elmer
 */
public abstract class IntegerOperation extends BinaryOperation {
	public IntegerOperation(Operation leftOperand, Operation rightOperand) {
		super(leftOperand, rightOperand);
	}

	protected Object operation(Object leftValue, Object rightValue) {
		if (leftValue instanceof Number && rightValue instanceof Number) {
			int l = ((Number) leftValue).intValue();
			int r = ((Number) rightValue).intValue();
			return new Integer(operation(l, r));
		}
		return null;
	}
	
	protected abstract int operation(int leftValue, int rightValue);
}
