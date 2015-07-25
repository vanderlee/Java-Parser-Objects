/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package example.nanoc;

/**
 * @author Franz-Josef Elmer
 */
public class IfStatement extends Statement {
	private final Operation condition;
	private final Statement thenClause;
	private final Statement elseClause;
	
	public IfStatement(Block parent, Operation condition, Statement thenClause,
					   Statement elseClause) {
		super(parent);
		this.condition = condition;
		this.thenClause = thenClause;
		this.elseClause = elseClause;
	}

	public Object execute(Block block) {
		if (Block.NULL.equals(condition.execute(block)) == false) {
			return thenClause.execute(block);
		} else if (elseClause != null) {
			return elseClause.execute(block);
		} else {
			return Block.NULL;
		}
	}

}
