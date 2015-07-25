/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package example.nanoc;

import java.util.Hashtable;

/**
 * @author Franz-Josef Elmer
 */
public class BinOp {
	private static final Hashtable REPOSITORY = new Hashtable();
	public static final BinOp SIGN = new BinOp("sign", 2),
							  NEGATE = new BinOp("!", 2),
							  MUL = new BinOp("*", 3),
							  DIV = new BinOp("/", 3),
							  MOD = new BinOp("%", 3),
							  PLUS = new BinOp("+", 4),
							  MINUS = new BinOp("-", 4),
							  LESS = new BinOp("<", 6),
							  LESS_EQUAL = new BinOp("<=", 6),
							  GREATER = new BinOp(">", 6),
							  GREATER_EQUAL = new BinOp(">=", 6),
							  EQUAL = new BinOp("==", 7),
							  NOT_EQUAL = new BinOp("!=", 7),
							  LOGIC_AND = new BinOp("&&", 11),
							  LOGIC_OR = new BinOp("||", 11);
							
	public static BinOp get(String literal) {
		return (BinOp) REPOSITORY.get(literal);
	}
	
	private final String literal;
	private final int precedence;
	
	public BinOp(String literal, int precedence) {
		this.literal = literal;
		this.precedence = precedence;
		REPOSITORY.put(literal, this);
	}
	
	public int getPrecedence() {
		return precedence;
	}
	
	public String toString() {
		return '(' + literal + ')' + precedence;
	}
}
