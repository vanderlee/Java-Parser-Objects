/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package example.nanoc;

/**
 * @author Franz-Josef Elmer
 */
public class LessEqualOperation extends CompareOperation {
	public LessEqualOperation(Operation leftOperand, Operation rightOperand) {
		super(leftOperand, rightOperand);
	}

	protected boolean compare(int left, int right) {
		return left <= right;
	}
	
}
