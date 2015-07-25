package example.calc;

import pobs.*;
import pobs.scanner.*;
import pobs.utility.POBS;
import pobs.control.*;
import pobs.parser.*;

/**
 * Insert the type's description here.
 * 
 * @author: Martijn W. van der Lee
 */
public class SimpleCalc {

    private pobs.PObject parser;
    private SolverVar solver;

    /**
     * SimpleCalc constructor comment.
     */
    public SimpleCalc() {
        super();

        solver = new SolverVar();

        /*
           	number		= sdouble | sint
        	variable	= lexeme(alpha+)       	
        	args		= expr (',' expr)*
        	function	= lexeme(alpha+) '(' args ')'
        	expr		= term ( ('+' term) | ('-' term) )*
            term		= factor ( ('*' factor) | ('/' factor) )*
            factor		= number | function | variable | '(' expr ')'            	
         */

        PObjectPointer p_term = new PObjectPointer();
        PObjectPointer p_factor = new PObjectPointer();

        // terminals
        PParser number = new POr(POBS.SIGNED_FLOAT, POBS.SIGNED_INT);
        number.setMatchAction(solver.value);

        PParser var = new PMultiple(POBS.alpha()).addControl(new PDisableSkip());
        var.setMatchAction(solver.variable);

        // non-terminals
        PParser expr_plus = new PSequence(new PChar('+'), p_factor);
        expr_plus.setMatchAction(solver.operand(solver.PLUS));

        PParser expr_minus = new PSequence(new PChar('-'), p_factor);
        expr_plus.setMatchAction(solver.operand(solver.MINUS));

        PObject expr =
            new PSequence(p_term, new PKleene(new POr(expr_plus, expr_minus)));

        PParser term_multiply = new PSequence(new PChar('*'), p_factor);
        term_multiply.setMatchAction(solver.operand(solver.MULTIPLY));

        PParser term_divide = new PSequence(new PChar('/'), p_factor);
        term_divide.setMatchAction(solver.operand(solver.DIVIDE));

        PObject term =
            new PSequence(
                p_factor,
                new PKleene(new POr(term_multiply, term_divide)));
        p_term.set(term);

        PObject args =
            new PSequence(
                new PChar('('),
                new PList(expr, new PChar(',')),
                new PChar(')'));

        PParser function_max = new PSequence(new PToken("max"), args);
        function_max.setMatchAction(solver.operand(solver.MAX));

		PParser function_min = new PSequence(new PToken("min"), args);
		function_min.setMatchAction(solver.operand(solver.MIN));
        
        PObject function = new POr(function_min, function_max);

        PObject factor =
            new POr(
                number,
                function,
                var,
                new PSequence(new PChar('('), expr, new PChar(')')));
        p_factor.set(factor);

        parser = factor;
    }
    /**
     * Starts the application.
     * @param args an array of command-line arguments
     */
    public static void main(java.lang.String[] args) {
        SimpleCalc c = new SimpleCalc();

        //                                   1         2         3         4
        //                          1234567890123456789012345678901234567890
        System.out.println(c.parse("max (3, PI + ( 4 * 5.5 ) )")); //$NON-NLS-1$
        c.solver.print();
        System.out.println(c.solver.solve());
    }
    public long parse(String input) {
        return parser
            .process(
                new PStringScanner(input),
                0,
                new PContext(new PDirective(new PWhitespace())))
            .getLength();
    }
}
