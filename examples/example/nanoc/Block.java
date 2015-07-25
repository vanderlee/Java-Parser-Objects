/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package example.nanoc;

import java.util.Hashtable;
import java.util.Stack;

/**
 * @author Franz-Josef Elmer
 */
public class Block extends Statement {
	private final Hashtable localVariables = new Hashtable();
	private final Stack statements = new Stack();
	public static final Integer NULL = new Integer(0);
	
	public Block(Block parent) {
		super(parent);
	}
	
	public void addFunctionArguments(Function function) {
		Variable[] arguments = function.getArguments();
		for (int i = 0; i < arguments.length; i++) {
			addVariable(arguments[i]);
		}
	}
	
	public void addVariable(Variable variable) {
		localVariables.put(variable.getName(), variable);
	}
	
	public Variable getVariable(String name) {
		Variable result = (Variable) localVariables.get(name);
		if (result == null) {
			Block parent = getParent();
			if (parent != null) {
				result = parent.getVariable(name);
			}
		}
		return result;
	}
	
	String getLocalVariables() {
		return localVariables.toString();
	}
	
	public void pushStatement(Statement statement) {
		statements.push(statement);
	}
	
	public Statement popStatement() {
		return (Statement) statements.pop();
	}
	
	public Object execute(Block block) {
		Object result = null;
		for (int i = 0, n = statements.size(); i < n; i++) {
			result = ((Statement) statements.elementAt(i)).execute(block);
			if (result == BreakStatement.BREAK 
					|| result instanceof ReturnValue) {
				break;
			}
		}
		return result;
	}


}
