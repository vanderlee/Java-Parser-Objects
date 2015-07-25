/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package example.calc;

/**
 * Reverse Polish Notation solver.
 *
 * Reverse Polish Notation (RPN) is also known as Postfix notation, as
 * opposed to the more commonly used infix notation. The "fix" refers to
 * the position of the operators relative to their parameters. Whereas
 * in infix notation a plus appears in between it's parameters (<code>1
 * + 2</code>), in postfix it appears after it's parameters (<code>1 2
 * +</code>). This has the benefit of neither requiring parentheses nor
 * precedence; the placement of the operands explicitely specifies the order
 * of execution, which is always from left to right.
 * This feature of RPN means we need not recurse the input to attain a
 * solution to the expression. Another reason to use RPN is that it is very
 * easy to build parsers which produce RPN from infix input, easier so than
 * other notation methods.
 * There is also prefix notation, this is rarely used since it has no
 * benefits over either infix or RPN.
 *
 * Use the overloaded "push" method to add either a value (any double),
 * a variable (any string) or an operand (any byte) in a lineair fashion,
 * then call the solve method to return the result of the RPN expression
 * entered.
 * @author: Martijn W. van der Lee
 */
public class Solver {
    // Non-operands
    private final static byte VALUE = 0;
    // Binary operands
    public final byte PLUS = 20;
    public final byte MINUS = 21;
    public final byte MULTIPLY = 22;
    public final byte DIVIDE = 23;
    public final byte MODULO = 24;
    public final byte POWER = 25;
    // Unary operands
    public final byte ABSOLUTE = 50;

    java.util.Vector stack;

    private class StackNode {
        byte operand;
        double value;
        String variable;

        public StackNode(double value) {
            this.operand = VALUE;
            this.value = value;
        }
        public StackNode(byte operand) {
            this.operand = operand;
        }
        public StackNode(byte operand, double value) {
            this.operand = operand;
            this.value = value;
        }
    }

    private class PushValueAction implements pobs.PAction {
        public void perform(pobs.PTarget target, java.lang.String string) {
            stack.add(new StackNode(java.lang.Double.parseDouble(string)));
        }
    }
    public pobs.PAction value = new PushValueAction();

    public class pushOp_ implements pobs.PAction {
        private byte operation;
        public pushOp_(byte operation) {
            this.operation = operation;
        }
        public void perform(pobs.PTarget target, java.lang.String string) {
            stack.add(new StackNode(operation));
        };
    }
    public pushOp_ pushOp(byte operation) {
        return new pushOp_(operation);
    }

    public double getValue(int index) {
        return ((StackNode) stack.get(index)).value;
    }

    public void setValue(int index, double value) {
        StackNode s = (StackNode) stack.get(index);
        stack.set(index, new StackNode(s.operand, value));
    }

    /**
     * Solver constructor comment.
     */
    public Solver() {
        super();

        stack = new java.util.Vector();
    }
    /**
     * Starts the application.
     * @param args an array of command-line arguments
     */
    public static void main(java.lang.String[] args) {
        Solver s = new Solver();

        s.value.perform(null, "4"); //$NON-NLS-1$

        System.out.print(s.solve());
    }
       
    public double solve() {
		if (stack.size() <= 0) {
			return 0;
		}

        int x = 0;
        do {
            switch (((StackNode) stack.get(x)).operand) {

                case PLUS :
                    setValue(x, getValue(x - 2) + getValue(x - 1));
                    break;

                case MINUS :
                    setValue(x, getValue(x - 2) - getValue(x - 1));

                case MULTIPLY :
                    setValue(x, getValue(x - 2) * getValue(x - 1));
                    break;

                case DIVIDE :
                    setValue(x, getValue(x - 2) / getValue(x - 1));
                    break;

                case MODULO :
                    setValue(x, getValue(x - 2) % getValue(x - 1));
                    break;

                case POWER :
                    setValue(
                        x,
                        java.lang.Math.pow(getValue(x - 2), getValue(x - 1)));
                    break;

                case ABSOLUTE :
                    setValue(x, java.lang.Math.abs(getValue(x - 1)));
                    break;

                default :
                    }
		} while (x++ < stack.size());

        return getValue(x - 1);
    }
}
