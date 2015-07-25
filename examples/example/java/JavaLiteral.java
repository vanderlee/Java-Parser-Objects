/*
 * Copyright (c) 2006 Parser OBjectS group Released under ZLib/LibPNG license.
 * See "license.txt" for details.
 */
package example.java;

import pobs.PContext;
import pobs.PMatch;
import pobs.PObject;
import pobs.PParser;
import pobs.PScanner;
import pobs.control.PDisableSkip;
import pobs.parser.PAny;
import pobs.parser.PChar;
import pobs.parser.PExcept;
import pobs.parser.PMultiple;
import pobs.parser.POptional;
import pobs.parser.POr;
import pobs.parser.PRepeat;
import pobs.parser.PSequence;
import pobs.parser.PSet;
import pobs.parser.PToken;
import pobs.utility.POBS;

/**
 * Matches a Java literal value.
 * 
 * @author Martijn W. van der Lee
 */
public class JavaLiteral extends PParser {
	public PParser parser;
	
	public JavaLiteral() {
		super();
		
		PObject c_zero = new PChar('0');
		PObject c_dot = new PChar('.');
		PObject c_quote = new PChar('"');
		PObject c_apos = new PChar('\'');
		
		PObject input_character = new PAny();
        
		PObject escape_character = new PSequence(new PToken("\\" + "u"),
										new PRepeat(POBS.hexDigit(), 4, 4));
                
		PObject escape_sequence = escape_character;

		PObject non_zero_digit = new PSet("123456789");
	   
		PObject digit = POBS.digit();

		PObject digits = new PMultiple(digit);

		PObject null_literal = new PToken("null");

		PObject string_character = new POr(new PExcept(input_character,
					   new PSet("\"\\")), escape_character);
					   
		PObject string_characters = new PMultiple(string_character);

		PObject string_literal = new PSequence(c_quote, new POptional(
					   string_characters), c_quote);

		PObject single_character = new PExcept(input_character,
					   new PSet("\'\\"));

		PObject character_literal = new PSequence(c_apos, new POr(
					   single_character, escape_sequence), c_apos);

		PObject boolean_literal = new POr(new PToken("true"), new PToken(
					   "false"));

		PObject float_type_suffix = new PSet("fFdD");

		PObject sign = POBS.sign();

		PObject signed_integer = new PSequence(new POptional(sign), digits);

		PObject exponent_indicator = new PSet("eE");

		PObject exponent_part = new PSequence(exponent_indicator,
					   signed_integer);

		PObject floating_point_literal  = new POr(
				   new PSequence(
					   new POr(new PSequence(digits, c_dot, new POptional(digits)),
							   new PSequence(c_dot, digits)),	
					   new POptional(exponent_part),
					   new POptional(float_type_suffix)),			
				   new PSequence(digits, new POr(new PSequence(exponent_part, float_type_suffix),
												   exponent_part, float_type_suffix)));
								                   
		PObject octal_digit = POBS.octalDigit();

		PObject octal_numeral = new PSequence(c_zero,
					   new PMultiple(octal_digit));

		PObject hex_digit = POBS.hexDigit();

		PObject hex_numeral = new PSequence(c_zero, new PSet("xX"),
					   new PMultiple(hex_digit));

		PObject decimal_numeral = new POr(c_zero, new PSequence(non_zero_digit,
					   new POptional(digits)));

		PObject integer_type_suffix = new PSet("lL");

		PObject octal_integer_literal = new PSequence(octal_numeral,
					   new POptional(integer_type_suffix));

		PObject hex_integer_literal = new PSequence(hex_numeral, new POptional(
					   integer_type_suffix));

		PObject decimal_integer_literal = new PSequence(decimal_numeral,
					   new POptional(integer_type_suffix));

		PObject integer_literal = new POr(hex_integer_literal, octal_integer_literal,
					   decimal_integer_literal);

		parser = new POr(new PObject[] {floating_point_literal, integer_literal,
				   boolean_literal, character_literal, string_literal,
				   null_literal}).addControl(new PDisableSkip());
	}
	
	protected PMatch parse(PScanner input, long begin, PContext context) {
		return parser.process(input, begin, context);
	}
}