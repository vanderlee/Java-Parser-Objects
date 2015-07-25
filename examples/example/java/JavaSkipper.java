/*
 * Copyright (c) 2006 Parser OBjectS group Released under ZLib/LibPNG license.
 * See "license.txt" for details.
 */
package example.java;

import pobs.PContext;
import pobs.PMatch;
import pobs.PParser;
import pobs.PScanner;
import pobs.parser.PToken;
import pobs.parser.PExcept;
import pobs.parser.PSequence;
import pobs.parser.PAny;
import pobs.parser.PKleene;
import pobs.parser.PEOL;
import pobs.parser.POr;
import pobs.parser.PWhitespace;

/**
 * A parser that matches any whitespace and/or C or C++ style comments.
 * 
 * @author Martijn W. van der Lee
 */
public class JavaSkipper extends PParser {
	public PParser parser;
	
	public JavaSkipper() {
		super();

		PParser comment_cpp = new PSequence(new PToken("//"),
								 new PKleene(new PExcept(new PAny(), new PEOL())),
								 new PEOL());	
								 
		PParser comment_c = new PSequence(new PToken("/*"),
								new PKleene(new PExcept(new PAny(), new PToken("*/"))),
								new PToken("*/")); 
								       
		parser = new PKleene(new POr(new PWhitespace(), comment_cpp, comment_c));
		
	}
	
	protected PMatch parse(PScanner input, long begin, PContext context) {
		return parser.process(input, begin, context);
	}
}