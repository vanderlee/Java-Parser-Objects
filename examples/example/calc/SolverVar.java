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
public class SolverVar {
    // Non-operands
    private final static byte VALUE = 0;
    private final static byte VARIABLE = 1;
    // Binary operands
    public final byte PLUS = 20;
    public final byte MINUS = 21;
    public final byte MULTIPLY = 22;
    public final byte DIVIDE = 23;
    public final byte MODULO = 24;
    public final byte POWER = 25;
    public final byte ASSIGN = 26;
    public final byte MAX = 27;
    public final byte MIN = 28;
    // Unary operands
    public final byte ABSOLUTE = 50;

    java.util.Vector stack;
    private java.util.Map variables;

    private class StackNode {
        byte operand;
        double value;
        String name;

        public StackNode(byte operand, double value, String name) {
            this.operand = operand;
            this.value = value;
            this.name = name;
        }

        public void setValue(double value) {
            this.value = value;
        }

        public void print() {
            System.out.println(
                "Operand:" + operand + ", Value:" + value + ", Name:" + name);
        }
    }

    class PushVariableAction implements pobs.PAction {
        public void perform(pobs.PTarget target, java.lang.String string) {
            stack.add(new StackNode(VARIABLE, 0, string));
        }
    }
    public pobs.PAction variable = new PushVariableAction();

    private class PushValueAction implements pobs.PAction {
        public void perform(pobs.PTarget target, java.lang.String string) {
            stack.add(
                new StackNode(
                    VALUE,
                    java.lang.Double.parseDouble(string),
                    null));
        }
    }
    public pobs.PAction value = new PushValueAction();

    public class PushOperandAction implements pobs.PAction {
        private byte operation;
        public PushOperandAction(byte operation) {
            this.operation = operation;
        }
        public void perform(pobs.PTarget target, java.lang.String string) {
            stack.add(new StackNode(operation, 0, null));
        };
    }
    public boolean debug = false;
    /**
     * Solver constructor comment.
     */
    public SolverVar() {
        this(false);
    }
    /**
     * Solver constructor comment.
     */
    public SolverVar(boolean debug) {
        super();

        this.debug = debug;

        stack = new java.util.Vector();

        variables = new java.util.HashMap();

        variables.put("PI", new Double(java.lang.Math.PI)); //$NON-NLS-1$
    }
    private double getValue(int index) {
        return ((StackNode) stack.get(index)).value;
    }
    /**
     * Insert the method's description here.
     * 
     * @return boolean
     */
    public boolean isDebug() {
        return debug;
    }
    public static void main(java.lang.String[] args) {
        SolverVar s = new SolverVar();
        PushOperandAction plus = s.operand(s.PLUS);

        s.value.perform(null, "2"); //$NON-NLS-1$
        s.value.perform(null, "3"); //$NON-NLS-1$
        plus.perform(null, null);

        System.out.print(s.solve());
    }

    public PushOperandAction operand(byte operation) {
        return new PushOperandAction(operation);
    }

    public void print() {
        for (int x = 0; x < stack.size(); ++x) {
            ((StackNode) stack.get(x)).print();
        }
    }

    public void setDebug(boolean newDebug) {
        debug = newDebug;
    }

    private void setStackValue(int index, double value) {
        StackNode s = (StackNode) stack.get(index);
        s.setValue(value);
        stack.set(index, s);
    }

    public void setVariable(String name, double value) {
        variables.put(name, new Double(value));
    }

    public double solve() {
        if (stack.size() <= 0) {
            return 0;
        }

        int x = 0;
        do {
            switch (((StackNode) stack.get(x)).operand) {

                case PLUS :
                    setStackValue(x, getValue(x - 2) + getValue(x - 1));
                    x -= 2;
                    stack.remove(x);
                    stack.remove(x);
                    break;

                case MINUS :
                    setStackValue(x, getValue(x - 2) - getValue(x - 1));
                    x -= 2;
                    stack.remove(x);
                    stack.remove(x);
                    break;

                case MULTIPLY :
                    setStackValue(x, getValue(x - 2) * getValue(x - 1));
                    x -= 2;
                    stack.remove(x);
                    stack.remove(x);
                    break;

                case DIVIDE :
                    setStackValue(x, getValue(x - 2) / getValue(x - 1));
                    x -= 2;
                    stack.remove(x);
                    stack.remove(x);
                    break;

                case MODULO :
                    setStackValue(x, getValue(x - 2) % getValue(x - 1));
                    x -= 2;
                    stack.remove(x);
                    stack.remove(x);
                    break;

                case POWER :
                    setStackValue(
                        x,
                        java.lang.Math.pow(getValue(x - 2), getValue(x - 1)));
                    x -= 2;
                    stack.remove(x);
                    stack.remove(x);
                    break;

                case MAX :
                    setStackValue(
                        x,
                        java.lang.Math.max(getValue(x - 2), getValue(x - 1)));
                    x -= 2;
                    stack.remove(x);
                    stack.remove(x);
                    break;

                case MIN :
                    setStackValue(
                        x,
                        java.lang.Math.min(getValue(x - 2), getValue(x - 1)));
                    x -= 2;
                    stack.remove(x);
                    stack.remove(x);
                    break;

                case ASSIGN :
                    {
                        double value = getValue(x - 1);
                        stack.remove(x - 1);
                        stack.remove(x - 1);
                        x -= 2;
                        setStackValue(x, getValue(x + 1));
                        variables.put(
                            ((StackNode) stack.get(x)).name,
                            new Double(value));
                        break;
                    }

                case ABSOLUTE :
                    setStackValue(x, java.lang.Math.abs(getValue(x - 1)));
                    x -= 1;
                    stack.remove(x);
                    break;

                case VARIABLE :
                    String name = ((StackNode) stack.get(x)).name;
                    if (variables.containsKey(name)) {
                        setStackValue(
                            x,
                            ((Double) variables.get(name)).doubleValue());
                    }
                    break;

                default :
                    // VALUE
            }
            if (debug) {
                System.out.println("X:" + x);
                print();
                System.out.println("--------------------");
            }
        } while (++x < stack.size());

        return getValue(x - 1);
    }
}
