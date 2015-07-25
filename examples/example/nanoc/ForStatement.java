/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package example.nanoc;

/**
 * @author Franz-Josef Elmer
 */
public class ForStatement extends Statement {
	private final Operation start;
	private final Operation condition;
	private final Operation increment;
	private final Statement loopStatement;

	public ForStatement(Block parent, Operation start, Operation condition,
						Operation increment, Statement loopStatement) {
		super(parent);
		this.start = start;
		this.condition = condition;
		this.increment = increment;
		this.loopStatement = loopStatement;
	}

	public Object execute(Block block) {
		Object result = null;
		for (init(block); condition(block); increment(block)) {
			if (loopStatement != null) {
				result = loopStatement.execute(block);
				if (result == BreakStatement.BREAK) {
					return Block.NULL;
				}
			}
		}
		return result;
	}

	private void init(Block block) {
		if (start != null) {
			start.execute(block);
		}
	}
	
	private boolean condition(Block block) {
		return Block.NULL.equals(condition == null 
				? Block.NULL : condition.execute(block)) == false;	
	}
	
	private void increment(Block block) {
		if (increment != null) {
			increment.execute(block);
		}
	}

}
