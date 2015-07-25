/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package example.nanoc;

import java.util.Hashtable;

/**
 * @author Franz-Josef Elmer
 */
public abstract class BinaryOperationFactory {
	private static final Hashtable REPOSITORY = new Hashtable();
	
	static {
		REPOSITORY.put(BinOp.SIGN,
			new BinaryOperationFactory(){
				public Operation create(Operation left, Operation right) {
					return new MinusOperation(right);
				}
			});
		REPOSITORY.put(BinOp.NEGATE,
			new BinaryOperationFactory(){
				public Operation create(Operation left, Operation right) {
					return new NegateOperation(right);
				}
			});
		REPOSITORY.put(BinOp.MUL,
			new BinaryOperationFactory(){
				public Operation create(Operation left, Operation right) {
					return new IntMulOperation(left, right);
				}
			});
		REPOSITORY.put(BinOp.DIV,
			new BinaryOperationFactory(){
				public Operation create(Operation left, Operation right) {
					return new IntDivOperation(left, right);
				}
			});
		REPOSITORY.put(BinOp.MOD,
			new BinaryOperationFactory(){
				public Operation create(Operation left, Operation right) {
					return new IntModOperation(left, right);
				}
			});
		REPOSITORY.put(BinOp.PLUS,
			new BinaryOperationFactory(){
				public Operation create(Operation left, Operation right) {
					return new IntAddOperation(left, right);
				}
			});
		REPOSITORY.put(BinOp.MINUS,
			new BinaryOperationFactory(){
				public Operation create(Operation left, Operation right) {
					return new IntDiffOperation(left, right);
				}
			});
		REPOSITORY.put(BinOp.LESS,
			new BinaryOperationFactory(){
				public Operation create(Operation left, Operation right) {
					return new LessOperation(left, right);
				}
			});
		REPOSITORY.put(BinOp.LESS_EQUAL,
			new BinaryOperationFactory(){
				public Operation create(Operation left, Operation right) {
					return new LessEqualOperation(left, right);
				}
			});
		REPOSITORY.put(BinOp.GREATER,
			new BinaryOperationFactory(){
				public Operation create(Operation left, Operation right) {
					return new GreaterOperation(left, right);
				}
			});
		REPOSITORY.put(BinOp.GREATER_EQUAL,
			new BinaryOperationFactory(){
				public Operation create(Operation left, Operation right) {
					return new GreaterEqualOperation(left, right);
				}
			});
		REPOSITORY.put(BinOp.EQUAL,
			new BinaryOperationFactory(){
				public Operation create(Operation left, Operation right) {
					return new EqualOperation(left, right);
				}
			});
		REPOSITORY.put(BinOp.NOT_EQUAL,
			new BinaryOperationFactory(){
				public Operation create(Operation left, Operation right) {
					return new NotEqualOperation(left, right);
				}
			});
		REPOSITORY.put(BinOp.LOGIC_AND,
			new BinaryOperationFactory(){
				public Operation create(Operation left, Operation right) {
					return new LogicAndOperation(left, right);
				}
			});
		REPOSITORY.put(BinOp.LOGIC_OR,
			new BinaryOperationFactory(){
				public Operation create(Operation left, Operation right) {
					return new LogicOrOperation(left, right);
				}
			});

	}
	
	
	public static Operation create(Operation left, BinOp op, 
										 Operation right) {
		BinaryOperationFactory factory 
				= (BinaryOperationFactory) REPOSITORY.get(op);
		if (factory == null) {
			throw new IllegalArgumentException(
						"No factory registered for binary operand " + op);
		}
		return factory.create(left, right);
	}
	
	public abstract Operation create(Operation leftOperand, 
									 Operation rightOperand);
}
