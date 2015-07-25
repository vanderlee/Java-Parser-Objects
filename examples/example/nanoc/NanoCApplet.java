/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package example.nanoc;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.io.StringWriter;

import pobs.PContext;
import pobs.PDirective;
import pobs.PErrorHandler;
import pobs.PMatch;
import pobs.errorhandler.PDefaultErrorHandler;
import pobs.scanner.PStringScanner;
import pobs.scanner.PTextScanner;

/**
 * @author Franz-Josef Elmer
 */
public class NanoCApplet extends Applet {
	private TextArea codeWindow;
	private TextArea resultWindow;
	private TextArea errorWindow;
	private NanoC parser;
	
	public void init() {
		parser = new NanoC();
		setLayout(new BorderLayout());
		codeWindow = new TextArea(40, 90);
		codeWindow.setFont(new Font("SansSerif", Font.BOLD, 12));
		String code = getParameter("program");
		if (code != null) {
			codeWindow.setText(code);
		}
		add(codeWindow, BorderLayout.CENTER);
		Panel panel = new Panel();
		panel.setLayout(new BorderLayout());
		add(panel, BorderLayout.SOUTH);
		resultWindow = new TextArea(5, 90);
		resultWindow.setEditable(false);
		panel.add(resultWindow, BorderLayout.CENTER);
		Panel panel2 = new Panel();
		panel2.setLayout(new BorderLayout());
		panel.add(panel2, BorderLayout.SOUTH);
		errorWindow = new TextArea(2, 80);
		errorWindow.setEditable(false);
		errorWindow.setForeground(Color.red);
		panel2.add(errorWindow, BorderLayout.CENTER);
		Button runButton = new Button("run");
		runButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					run();
				}
			});
		panel2.add(runButton, BorderLayout.EAST);
	}
	
	private void run() {
		StringWriter writer = new StringWriter();
		NanoCC cc = new NanoCC(new PrintWriter[] {new PrintWriter(writer)});
    	PStringScanner input = new PStringScanner(codeWindow.getText());
    	PTextScanner input2 = new PTextScanner(input);
    	PContext context = new PContext(new PDirective(), cc, 
    									new PDefaultErrorHandler(true));
    	context.getDirective().setSkipper(parser.getSkipper());
    	context.getDirective().setSkip(true);
    	context.getDirective().setAlternatives(PDirective.LONGEST);
    	PMatch match = parser.process(input2, 0, context);
		PErrorHandler errorHandler = context.getErrorHandler();
		if (match.isMatch()) {
    		FunctionRepository functionRepository = cc.getFunctionRepository();
			Function mainFunction = functionRepository.getFunction("main");
			if (mainFunction != null) {
				mainFunction.call(new Object[0]);
			}
			resultWindow.setText(writer.toString());
			errorWindow.setText("");
		} else {
			errorWindow.setText(errorHandler.createErrorMessage(input2)); 
		}
	}
}
