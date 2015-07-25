/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package example.nanoc;

/**
 * @author Franz-Josef Elmer
 */
public class GreaterEqualOperation extends CompareOperation {
	public GreaterEqualOperation(Operation leftOperand, Operation rightOperand) {
		super(leftOperand, rightOperand);
	}

	protected boolean compare(int left, int right) {
		return left >= right;
	}
	
}
