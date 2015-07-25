/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package example.nanoc;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.PrintWriter;
import java.io.StringWriter;

import pobs.PContext;
import pobs.PDirective;
import pobs.PErrorHandler;
import pobs.PMatch;
import pobs.PObject;
import pobs.PParser;
import pobs.PScanner;
import pobs.control.PC;
import pobs.errorhandler.PDefaultErrorHandler;
import pobs.parser.PAny;
import pobs.parser.PChar;
import pobs.parser.PEOL;
import pobs.parser.PExcept;
import pobs.parser.PKleene;
import pobs.parser.PList;
import pobs.parser.PMultiple;
import pobs.parser.POptional;
import pobs.parser.POr;
import pobs.parser.PParserPointer;
import pobs.parser.PSequence;
import pobs.parser.PSet;
import pobs.parser.PToken;
import pobs.parser.PTokens;
import pobs.parser.PWhitespace;
import pobs.parser.PWrapper;
import pobs.scanner.PStringScanner;
import pobs.scanner.PTextScanner;
import pobs.utility.POBS;

/**
 * @author Franz-Josef Elmer
 */
public class NanoC extends PParser {
	private static final String CHAR_LIT_C = "char lit c";
	private static final String CHAR_LIT_S = "char lit s";
	private static final String CHAR_CONSTANT = "char constant";
	private static final String STRING_CONSTANT = "string constant";
	private static final String CONSTANT = "constant";
	private static final String TERM = "term";
	private static final String ASGNOP = "asgnop";
	private static final String BINOP = "binop";
	private static final String IF = "if";
	private static final String WHILE = "while";
	private static final String FOR = "for";
	private static final String EXPRESSION = "expression";
	private static final String STATEMENT = "statement";
	private static final String IDENTIFIER = "identifier";
	private static final String BLOCK = "block";
	private static final String FUNC_DEC = "funcDec";
	private static final String FUNCTION_DEFINITION = "functionDefinition";
	private static final String PROGRAM = "program";
	
	private final ParserWeb web = new ParserWeb(false);
	
	private final PParser parser;
	
	public NanoC() {
		// 1 PROGRAM
		web.set(PROGRAM, new PMultiple(web.get(FUNCTION_DEFINITION)));
		
		// 6 FUNCTION DEFINITION
		web.set(FUNCTION_DEFINITION, 
				new PSequence(web.get(FUNC_DEC), web.get(BLOCK))
					.setMatchAction(NanoCC.FINISH_FUNC));
		
		// 10 FUNC DEC, 11 FUNC HEAD 
		web.set(FUNC_DEC, 
				new PSequence(web.get(IDENTIFIER), new PChar('('),
							  new PList(web.get(IDENTIFIER), new PChar(','), 
							  		    0, PList.INFINITE), 
							  new PChar(')')).setMatchAction(NanoCC.DEF_FUNC));
		
		// 13 BLOCK
		web.set(BLOCK, 
				new PSequence(new PChar('{').setMatchAction(NanoCC.OPEN_BLOCK),
							  new PKleene(web.get(STATEMENT)), 
							  new PChar('}').setMatchAction(NanoCC.CLOSE_BLOCK)));
		
		buildStatementParser();
		buildExpressionParser();
		
		// 38 CONSTANT
		PParser numberConstant 
				= POBS.unsignedInt().setMatchAction(NanoCC.NUMBER);
		web.set(CONSTANT, 
				new POr(web.get(STRING_CONSTANT), 
						web.get(CHAR_CONSTANT),
						numberConstant));
		
		// 40 CHAR CONSTANT
		web.set(CHAR_CONSTANT, 
				new PSequence(new PChar('\'').setMatchAction(NanoCC.CLEAR_BUFFER), 
						      web.get(CHAR_LIT_C), 
						      new PChar('\''))
					.setMatchAction(NanoCC.CHAR)
					.addControl(PC.DISABLE_SKIP));
				
		// 41 STRING CONSTANT
		web.set(STRING_CONSTANT, 
				new PSequence(new PChar('"').setMatchAction(NanoCC.CLEAR_BUFFER),
						      new PKleene(web.get(CHAR_LIT_S)), 
							  new PChar('"'))
					.setMatchAction(NanoCC.STRING)
					.addControl(PC.DISABLE_SKIP));
		
		// 57 CHAR LIT (for characters)
		PTokens escapeCharacters = new PTokens("\\n", "\\t", "\\'", "\\\"", "\\\\");
		web.set(CHAR_LIT_C, 
				new POr(new PExcept(new PAny(), new PChar('\'')),
						escapeCharacters)
					.setMatchAction(NanoCC.ADD_CHAR));
		
		// 57 CHAR LIT (for strings)
		web.set(CHAR_LIT_S, 
				new POr(escapeCharacters, new PExcept(new PAny(), new PChar('"')) 
						)
					.setMatchAction(NanoCC.ADD_CHAR));
		
		// 53 IDENTIFIER
		web.set(IDENTIFIER, 
				POBS.alphaAlphaNums().setMatchAction(NanoCC.PUSH));
		
		parser = (PParser) web.get(PROGRAM);
	}

	private void buildStatementParser() {
		// 21 STATEMENT
		PChar semicolon = new PChar(';');
		PParser simpleStatement 
				= new PSequence(web.get(EXPRESSION), semicolon)
							.setMatchAction(NanoCC.SIMPLE_STATEMENT);
		PParser returnStatement 
				= new PSequence(new PToken("return").setMatchAction(NanoCC.RETURN), 
								new POptional(web.get(EXPRESSION)), 
								semicolon)
						.setMatchAction(NanoCC.RET);
		PParser breakStatement = new PSequence(new PToken("break"), semicolon)
										.setMatchAction(NanoCC.BREAK);
		PParserPointer ifStatementP = new PParserPointer();
		PParserPointer whileStatementP = new PParserPointer();
		PParserPointer forStatementP = new PParserPointer();
		web.set(STATEMENT, 
				new POr(new PObject[] {web.get(FOR), web.get(WHILE),
									   web.get(IF), returnStatement, 
									   breakStatement, web.get(BLOCK),
									   simpleStatement})
					.addControl(PC.FIRST_ALTERNATIVE));
		
		// 32 IF
		POptional elseClause 
				= new POptional(new PSequence(new PToken("else")
												.setMatchAction(NanoCC.ELSE), 
									          web.get(STATEMENT)));
		web.set(IF, 
				new PSequence(new PObject[] 
								{new PToken("if"), new PChar('('), 
						      	 web.get(EXPRESSION), new PChar(')'),
								 web.get(STATEMENT), elseClause})
						.setMatchAction(NanoCC.IF));
		
		// 35 WHILE
		web.set(WHILE, 
				new PSequence(new PToken("while"), 
							  new PChar('(').setMatchAction(NanoCC.FOR_START), 
							  web.get(EXPRESSION), 
							  new PChar(')').setMatchAction(NanoCC.FOR_CONDITION),
							  web.get(STATEMENT)).setMatchAction(NanoCC.FOR));
		
		// 37 FOR
		web.set(FOR,
				new PSequence(new PObject[] 
								{new PToken("for"), new PChar('('),
				         		 new POptional(web.get(EXPRESSION)), 
								 new PChar(';').setMatchAction(NanoCC.FOR_START),
								 new POptional(web.get(EXPRESSION)), 
								 new PChar(';').setMatchAction(NanoCC.FOR_CONDITION),
								 new POptional(web.get(EXPRESSION)), 
								 new PChar(')'), 
								 web.get(STATEMENT)}).setMatchAction(NanoCC.FOR));
	}

	private void buildExpressionParser() {
		// 22 EXPRESSION
		PParser bracketExpression 
				= new PSequence(new PChar('(').setMatchAction(NanoCC.OPEN), 
								web.get(EXPRESSION), 
								new PChar(')')).setMatchAction(NanoCC.CLOSE_EXPR);
		PParser minusExpression 
				= new PSequence(new PChar('-').setMatchAction(NanoCC.MINUS), 
								web.get(EXPRESSION))
										;
		PParser negateExpression 
				= new PSequence(new PChar('!').setMatchAction(NanoCC.NEGATE), 
								web.get(EXPRESSION))
										;
		PParser postIncrementExpression 
				= new PSequence(web.get(IDENTIFIER), 
								new PToken("++").setMismatchAction(NanoCC.POP1))
						.setMatchAction(NanoCC.POST_INC);
		PParser postDecrementExpression 
				= new PSequence(web.get(IDENTIFIER),
								new PToken("--").setMismatchAction(NanoCC.POP1))
						.setMatchAction(NanoCC.POST_DEC);
						
		PParser preIncrementExpression 
				= new PSequence(new PToken("++"),
								web.get(IDENTIFIER).setMismatchAction(NanoCC.POP1))
						.setMatchAction(NanoCC.PRE_INC);
		PParser preDecrementExpression 
				= new PSequence(new PToken("--"),
								web.get(IDENTIFIER).setMismatchAction(NanoCC.POP1))
						.setMatchAction(NanoCC.PRE_DEC);
						
		PParser exprWrapper = new PWrapper(web.get(EXPRESSION))
										.setMismatchAction(NanoCC.POP1);
		PParser assignExpression = new PSequence(web.get(IDENTIFIER), 
												 web.get(ASGNOP),
												 exprWrapper)
										.setMatchAction(NanoCC.ASSIGN);
		PParser expression = new POr(new PObject[] 
									  {bracketExpression, negateExpression, 
									   preIncrementExpression, preDecrementExpression,
									   postIncrementExpression, postDecrementExpression,
									   minusExpression,
									   assignExpression, web.get(TERM),
									   web.get(CONSTANT)})
							  .addControl(PC.FIRST_ALTERNATIVE);
		web.set(EXPRESSION, new PList(expression, web.get(BINOP))
								.setMatchAction(NanoCC.BIN_OP));
		
		// 23 TERM		
		PSequence arguments 
				= new PSequence(new PChar('(').setMatchAction(NanoCC.OPEN), 
								new PList(web.get(EXPRESSION), new PChar(','),
										  0, PList.INFINITE),
								new PChar(')').setMatchAction(NanoCC.CLOSE));
		web.set(TERM,
				new PSequence(web.get(IDENTIFIER), new POptional(arguments))
						.setMatchAction(NanoCC.TERM));
		
		// 25 BINOP		
		web.set(BINOP, new POr(new PObject[] 
								{new PSet("*/%"), new PSet("+-"),
								 new PTokens("&&", "||"),
								 new PTokens(new String[] {"<=", "<", ">=", ">", 
								 						   "==", "!="})})
						.setMatchAction(NanoCC.PUSH_BIN_OP));

		// 28 ASGNOP
		web.set(ASGNOP, new PChar('=').setMismatchAction(NanoCC.POP1));
	}
	
	
	protected PMatch parse(PScanner input, long begin, PContext context) {
		return parser.process(input, begin, context);
	}
	
	public void run(String code) {
    	PrintWriter[] writers = new PrintWriter[] {new PrintWriter(System.out)};
		run(code, writers);
	}

	public void run(String code, PrintWriter writer) {
    	PrintWriter[] writers = new PrintWriter[] {new PrintWriter(System.out), writer};
		run(code, writers);
	}
	
	public String exec(String code) {
		StringWriter writer = new StringWriter();
		run(code, new PrintWriter[] {new PrintWriter(writer)});
		return writer.toString();
	}
	

	/**
	 * @param code
	 * @param writers
	 */
	private void run(String code, PrintWriter[] writers) {
		NanoCC cc = new NanoCC(writers);
    	PStringScanner input = new PStringScanner(code);
    	PTextScanner input2 = new PTextScanner(input);
    	PContext context = new PContext(new PDirective(), cc, 
    									new PDefaultErrorHandler(true));
    	context.getDirective().setSkipper(getSkipper());
    	context.getDirective().setSkip(true);
    	context.getDirective().setAlternatives(PDirective.LONGEST);
    	PMatch match = parser.process(input2, 0, context);
		PErrorHandler errorHandler = context.getErrorHandler();
    	if (match.isMatch() && errorHandler.semanticErrorOccured() == false) {
    		System.out.println("ok");
    		FunctionRepository functionRepository = cc.getFunctionRepository();
//			System.out.println(functionRepository);
			Function mainFunction = functionRepository.getFunction("main");
			if (mainFunction != null) {
				mainFunction.call(new Object[0]);
			}
    	} else {
			System.out.println(errorHandler.createErrorMessage(input2));
    	}
	}

	PParser getSkipper() {
		PParser whitespaces = new PMultiple(new PWhitespace());
		PAny any = new PAny();
		PParser comment 
				= createComment(new PToken("/*"), any, new PToken("*/"));
		PParser inlineComment 
				= createComment(new PToken("//"), any, new PEOL());
		return new PKleene(new POr(whitespaces, comment, inlineComment))
					.addControl(PC.DISABLE_SKIP);
	}

	private PSequence createComment(PToken begin, PAny content, PParser end) {
		return new PSequence(begin, 
							 new PKleene(new PExcept(content, end)), 
							 end);
	}

	public static void main(String[] args) {
		final NanoC parser = new NanoC();
		Frame frame = new Frame();
	    frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent event) {
              System.exit(0);
            }
          }
        );
		Panel panel = new Panel();
		frame.add(panel);
		panel.setLayout(new BorderLayout());
		final TextArea editor = new TextArea(20, 60);
		panel.add(editor, BorderLayout.CENTER);
		Panel buttons = new Panel();
		panel.add(buttons, BorderLayout.SOUTH);
		Button runButton = new Button("run");
		runButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					parser.run(editor.getText());
				}
			});
		buttons.add(runButton);
		frame.pack();
		frame.setVisible(true);
	}
}
