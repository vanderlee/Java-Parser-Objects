/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package example.nanoc;

import java.io.PrintWriter;
import java.util.Properties;
import java.util.Stack;
import java.util.Vector;

import pobs.PAction;
import pobs.PTarget;

/**
 * @author Franz-Josef Elmer
 */
public class NanoCC implements PTarget {
	public static final PAction POP1
			= new PAction() {
					public void perform(PTarget target, String data) {
						((NanoCC) target).popFromStack(1);
					}
				};
	
	public static final PAction PUSH 
			= new PAction() {
					public void perform(PTarget target, String data) {
						((NanoCC) target).push(data);
					}
				};
	
	public static final PAction DEF_FUNC
			= new PAction() {
					public void perform(PTarget target, String data) {
						((NanoCC) target).startFunctionDefinition();
					}
				}; 
	
	public static final PAction FINISH_FUNC
			= new PAction() {
					public void perform(PTarget target, String data) {
						((NanoCC) target).finishFunctionDefinition();
					}
				}; 
	
	public static final PAction OPEN_BLOCK
			= new PAction() {
					public void perform(PTarget target, String data) {
						((NanoCC) target).createBlock();
					}
				}; 
	
	public static final PAction CLOSE_BLOCK
			= new PAction() {
					public void perform(PTarget target, String data) {
						((NanoCC) target).closeBlock();
					}
				}; 

	public static final PAction ASSIGN
			= new PAction() {
					public void perform(PTarget target, String data) {
						((NanoCC) target).assign();
					}
				}; 

	public static final PAction SIMPLE_STATEMENT
			= new PAction() {
					public void perform(PTarget target, String data) {
						((NanoCC) target).makeSimpleStatement();
					}
				}; 

	public static final PAction NUMBER
			= new PAction() {
					public void perform(PTarget target, String data) {
						((NanoCC) target).makeNumberConstant(data);
					}
				}; 
				
	public static final PAction CLEAR_BUFFER
			= new PAction() {
					public void perform(PTarget target, String data) {
						((NanoCC) target).clearBuffer();
					}
				}; 
				
	public static final PAction ADD_CHAR
			= new PAction() {
					public void perform(PTarget target, String data) {
						((NanoCC) target).addCharacter(data);
					}
				}; 
				
	public static final PAction STRING
			= new PAction() {
					public void perform(PTarget target, String data) {
						((NanoCC) target).makeStringConstant();
					}
				}; 
				
	public static final PAction CHAR
			= new PAction() {
					public void perform(PTarget target, String data) {
						((NanoCC) target).makeCharacterConstant();
					}
				}; 
				
	public static final PAction OPEN
			= new PAction() {
					public void perform(PTarget target, String data) {
						((NanoCC) target).push(OPEN_BRACKET);
					}
				}; 
				
	public static final PAction CLOSE
			= new PAction() {
					public void perform(PTarget target, String data) {
						((NanoCC) target).push(CLOSE_BRACKET);
					}
				}; 
				
	public static final PAction RETURN
			= new PAction() {
					public void perform(PTarget target, String data) {
						((NanoCC) target).push(RETURN_TOKEN);
					}
				}; 
				
	public static final PAction RET
			= new PAction() {
					public void perform(PTarget target, String data) {
						((NanoCC) target).makeReturnStatement();
					}
				}; 
				
	public static final PAction IF
			= new PAction() {
					public void perform(PTarget target, String data) {
						((NanoCC) target).makeIfStatement();
					}
				}; 
				
	public static final PAction FOR
			= new PAction() {
					public void perform(PTarget target, String data) {
						((NanoCC) target).makeForStatement();
					}
				}; 
				
	public static final PAction TERM
			= new PAction() {
					public void perform(PTarget target, String data) {
						((NanoCC) target).makeTerm();
					}
				}; 

	public static final PAction POST_INC
			= new PAction() {
					public void perform(PTarget target, String data) {
						((NanoCC) target).postIncrement(1);
					}
				};

	public static final PAction POST_DEC
			= new PAction() {
					public void perform(PTarget target, String data) {
						((NanoCC) target).postIncrement(-1);
					}
				}; 

	public static final PAction PRE_INC
			= new PAction() {
					public void perform(PTarget target, String data) {
						((NanoCC) target).preIncrement(1);
					}
				};

	public static final PAction PRE_DEC
			= new PAction() {
					public void perform(PTarget target, String data) {
						((NanoCC) target).preIncrement(-1);
					}
				}; 

	public static final PAction MINUS
			= new PAction() {
					public void perform(PTarget target, String data) {
						NanoCC cc = (NanoCC) target;
						cc.push(NOP.NOP);
						cc.push(BinOp.SIGN);
					}
				}; 

	public static final PAction NEGATE
			= new PAction() {
					public void perform(PTarget target, String data) {
						NanoCC cc = (NanoCC) target;
						cc.push(NOP.NOP);
						cc.push(BinOp.NEGATE);
					}
				}; 

	public static final PAction PUSH_BIN_OP
			= new PAction() {
					public void perform(PTarget target, String data) {
						((NanoCC) target).push(BinOp.get(data));
					}
				}; 

	public static final PAction BIN_OP
			= new PAction() {
					public void perform(PTarget target, String data) {
						((NanoCC) target).binaryOperations();
					}
				}; 

	public static final PAction CLOSE_EXPR
			= new PAction() {
					public void perform(PTarget target, String data) {
						((NanoCC) target).closeBracketExpression();
					}
				}; 

	public static final PAction ELSE
			= new PAction() {
					public void perform(PTarget target, String data) {
						((NanoCC) target).push(ELSE_TOKEN);
					}
				}; 

	public static final PAction FOR_START
			= new PAction() {
					public void perform(PTarget target, String data) {
						((NanoCC) target).push(FOR_START_TOKEN);
					}
				}; 

	public static final PAction FOR_CONDITION
			= new PAction() {
					public void perform(PTarget target, String data) {
						((NanoCC) target).push(FOR_CONDITION_TOKEN);
					}
				}; 

	public static final PAction BREAK
			= new PAction() {
					public void perform(PTarget target, String data) {
						((NanoCC) target).makeBreakStatement();
					}
				}; 

	private static final Properties ESCAPE_SEQUENCES = new Properties();
	private static final Object OPEN_BRACKET = new Object();
	private static final Object CLOSE_BRACKET = new Object();
	private static final Object RETURN_TOKEN = new Object();
	private static final Object ELSE_TOKEN = new Object();
	private static final Object FOR_START_TOKEN = new Object();
	private static final Object FOR_CONDITION_TOKEN = new Object();
	
	static {
		ESCAPE_SEQUENCES.put("\\n", "\n");
		ESCAPE_SEQUENCES.put("\\t", "\t");
		ESCAPE_SEQUENCES.put("\\'", "\'");
		ESCAPE_SEQUENCES.put("\\\"", "\"");
		ESCAPE_SEQUENCES.put("\\\\", "\\");
	}

	private final FunctionRepository funcRepository = new FunctionRepository();
	private final Stack stack = new Stack();
	private final StringBuffer buffer = new StringBuffer();
	
	private Function currentFunction;
	private Block currentBlock;
	
	
	public NanoCC() {
		funcRepository.addFunction(new PrintFunction());
	}

	public NanoCC(PrintWriter[] writers) {
		funcRepository.addFunction(new PrintFunction(writers));
	}

	public FunctionRepository getFunctionRepository() {
		return funcRepository;
	}
	
	public void popFromStack(int n) {
		for (; n > 0; n--) {
			stack.pop();
		}
	}
	
	public void push(Object token) {
		stack.push(token);
	}
	
	public void startFunctionDefinition() {
		try {
			currentFunction = Function.createFunctionFromSignature(stack);
		} catch (RuntimeException e) {
			throw e;
		} finally {
			stack.setSize(0);
		}
	}
	
	public void createBlock() {
		Block block = new Block(currentBlock);
		if (currentBlock == null && currentFunction != null) {
			currentFunction.setFunctionBlock(block);
		}
		if (currentBlock != null) {
			currentBlock.pushStatement(block);
		}
		currentBlock = block;
	}
	
	public void closeBlock() {
		if (currentBlock != null) {
			currentBlock = currentBlock.getParent();
		}
	}
	
	public void finishFunctionDefinition() {
		try {
			if (currentFunction != null) {
				funcRepository.addFunction(currentFunction);
			}
		} catch (RuntimeException e) {
			throw e;
		} finally {
			currentFunction = null;
		}
	}
	
	public void assign() {
		Operation expression = (Operation) stack.pop();
		String name = (String) stack.pop();
		Variable variable = currentBlock.getVariable(name);
		if (variable == null) {
			variable = new Variable(name);
			currentBlock.addVariable(variable);
		}
		stack.push(new Assignment(variable, expression));
	}
	
	public void makeNumberConstant(String data) {
		stack.push(new Constant(new Integer(data)));
	}
	
	public void clearBuffer() {
		buffer.setLength(0);
	}
	
	public void addCharacter(String c) {
		String escapeSequence = ESCAPE_SEQUENCES.getProperty(c);
		buffer.append(escapeSequence == null ? c : escapeSequence);
	}
	
	public void makeStringConstant() {
		stack.push(new Constant(new String(buffer)));
	}
	
	public void makeCharacterConstant() {
		stack.push(new Constant(new Character(buffer.charAt(0))));
	}
	
	public void makeTerm() {
		Object object = stack.pop();
		if (object == CLOSE_BRACKET) {
			// Function call
			Vector arguments = new Vector();
			object = stack.pop();
			while (object != OPEN_BRACKET) {
				arguments.addElement(object);
				object = stack.pop();
			}
			int n = arguments.size();
			Operation[] argumentExpressions = new Operation[n];
			for (int i = 0; i < n; i++) {
				int ni = n - i - 1;
				Object ai = arguments.elementAt(ni);
				argumentExpressions[i] = (Operation) ai;
			}
			String functionName = (String) stack.pop();
			Function function = funcRepository.getFunction(functionName);
			if (function == null) {
				throw new RuntimeException("Undefined function '" 
										   + functionName + "'.");
			}
			stack.push(new FunctionCall(function, argumentExpressions));
		} else {
			// variable
			Variable v = getVariable(object);
			stack.push(new VariableReading(v));
		}
	}
	
	private Variable getVariable(Object object) {
		String variableName = (String) object;
		Variable v = (Variable) currentBlock.getVariable(variableName);
		if (v == null) {
			throw new RuntimeException("Undefined variable '" 
									   + variableName + "'.");
		}
		return v;
	}

	public void makeSimpleStatement() {
		Object s = stack.pop();
		currentBlock.pushStatement(new SimpleStatement(currentBlock, (Operation) s));
	}
	
	public void makeReturnStatement() {
		Object object = stack.pop();
		Operation expression = null;
		if (object instanceof Operation) {
			expression = (Operation) object;
			object = stack.pop();
		}
		currentBlock.pushStatement(new ReturnStatement(currentBlock, expression));
	}
	
	public void makeBreakStatement() {
		currentBlock.pushStatement(new BreakStatement(currentBlock));
	}
	
	public void makeIfStatement() {
		Statement elseClause = null;
		if (stack.peek() == ELSE_TOKEN) {
			stack.pop();
			elseClause = currentBlock.popStatement();
		}
		Operation condition = (Operation) stack.pop();
		currentBlock.pushStatement(new IfStatement(currentBlock, condition, 
												   currentBlock.popStatement(), 
												   elseClause));
	}
	
	public void makeForStatement() {
		Object object = stack.pop();
		Operation increment = null;
		if (object instanceof Operation) {
			increment = (Operation) object;
			object = stack.pop();
		}
		object = stack.pop();
		Operation condition = null;
		if (object instanceof Operation) {
			condition = (Operation) object;
			object = stack.pop();
		}
		Operation start = null;
		if (!stack.isEmpty() && stack.peek() instanceof Operation) {
			start = (Operation) stack.pop();
		}
		currentBlock.pushStatement(
				new ForStatement(currentBlock, start, condition, increment, 
								 currentBlock.popStatement()));
	}

	public void postIncrement(int amount) {
		Variable variable = getVariable(stack.pop());
		stack.push(new PostIncrement(variable, amount));
	}
		
	public void preIncrement(int amount) {
		Variable variable = getVariable(stack.pop());
		stack.push(new PreIncrement(variable, amount));
	}
		
	public void binaryOperations() {
		Vector v = new Vector();
		Object object = stack.pop();
		v.addElement(object);
		while (!stack.isEmpty() && stack.peek() instanceof BinOp) {
			v.addElement(stack.pop());
			v.addElement(stack.pop());
		}
		reduceBinOp(v);
		stack.push(v.elementAt(0));
	}
	
	private void reduceBinOp(Vector sequence) {
		int index = 1;
		while (index > 0) {
			index = -1;
			int minPrecedence = Integer.MAX_VALUE;
			for (int i = sequence.size() - 2; i >= 0; i -= 2) {
				int precedence = ((BinOp) sequence.elementAt(i)).getPrecedence();
				if (precedence < minPrecedence) {
					index = i;
					minPrecedence = precedence;
				}
			}
			if (index > 0) {
				Operation left = (Operation) sequence.elementAt(index + 1);
				BinOp op = (BinOp) sequence.elementAt(index);
				Operation right = (Operation) sequence.elementAt(index - 1);
				left = BinaryOperationFactory.create(left, op, right);
				sequence.setElementAt(left, index - 1);
				sequence.removeElementAt(index);
				sequence.removeElementAt(index);
			}
		}
	}
	
	public void closeBracketExpression() {
		Object expression = stack.pop();
		stack.pop();
		stack.push(expression);
	}
}
