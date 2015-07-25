/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package pobs.utility;

import pobs.PParser;
import pobs.control.PC;
import pobs.parser.PAny;
import pobs.parser.PChar;
import pobs.parser.PKleene;
import pobs.parser.PMultiple;
import pobs.parser.POptional;
import pobs.parser.POr;
import pobs.parser.PRange;
import pobs.parser.PSequence;
import pobs.parser.PSet;
import pobs.parser.PWrapper;

/**
 * Contains a number of convenient factories for commonly used parsers.
 * 
 * @author Martijn W. van der Lee
 * @author Franz-Josef Elmer
 */
public class POBS {

    private POBS() {}

    /**
     * Matches all ASCII alphabetical characters and digits, both lower- and
     * uppercase.
     */
    public static PParser alphaNum() {
		return new PRange("0-9a-zA-Z");
	}
    
    /**
     * Creates a parser who parses one or more alpha-numerical characters with
     * skipping disabled.
     * Short-cut of 
     * <pre>new PMultiple(POBS.alphaNum()).addControl(new PDisableSkip()</pre>
     */
    public static PParser alphaNums() {
    	return new PMultiple(alphaNum()).addControl(PC.DISABLE_SKIP);
    }

    /**
     * Matches all ASCII alphabetical characters, both lower- and uppercase.
     */
    public static PParser alpha() {
		return new PRange("a-zA-Z");
	}

    /**
     * Creates a parser who parses a sequence starting with a letter
     * followed by zero or more alpha-numerical characters.
     * Skipping disabled.
     * Short-cut of <pre>new PSequence(POBS.alpha(), new PKleene(POBS.alphaNum())).addControl(new PDisableSkip())</pre>
     * @return PParser
     */
    public static PParser alphaAlphaNums() {
    	return new PSequence(alpha(), new PKleene(alphaNum()))
						.addControl(PC.DISABLE_SKIP);
    }

    /**
     * Matches all ASCII lowercase alphabetical characthers.
     */
	public static PParser lowerCase() {
		return new PRange("a-z");
	}
	

    /**
     * Matches all ASCII uppercase alphabetical characters.
     */
 	public static PParser upperCase() {
		return new PRange("A-Z");
	}

    /**
     * Matches any single character.
     */
    public final static PParser ANY = new PAny();

    /**
     * Matches a single ASCII digits 0 to 9.
     */
    public static PParser digit() {
    	return new PRange("0-9");
    }

    /**
     * Matches a series ASCII digits 0 to 9.
     */
    public static PParser digits() {
    	return new PMultiple(digit());
    }

    /**
     * Matches all valid ASCII characters for the binary system, both lower- and
     * uppercase.
     */
    public static PParser binaryDigit() {
    	return new PSet("01");
    }

    /**
     * Matches all valid ASCII characters for the octal system, both lower- and
     * uppercase.
     */
    public static PParser octalDigit() {
    	return new PRange("0-7");
    }

    /**
     * Matches all valid ASCII characters for the hexadecimal system, both
     * lower- and uppercase.
     */
    public static PParser hexDigit() {
    	return new PRange("0-9a-fA-F");
    }

    /**
     * Matches all the ASCII characters for the positive and negative signs.
     */
    public static PParser sign() {
    	return new PSet("-+");
    }

    // Helpers for memory profile reasons
    private final static PParser DOT = new PChar('.');

    private final static PParser OPT_SIGN = new POptional(sign());

    private final static PParser UINT = new PMultiple(digit());

    /**
     * Matches an unsigned decimal integer of any length.
     */
    public final static PParser UNSIGNED_INT = new PWrapper(UINT)
            .addControl(PC.DISABLE_SKIP);
    
    /**
     * Creates a new instance of a parser for an unsigned integer.
     */
    public static PParser unsignedInt() {
    	return digits().addControl(PC.DISABLE_SKIP);
    }
    
    /**
     * Matches a signed decimal integer of any length.
     */
    public final static PParser SIGNED_INT = new PSequence(OPT_SIGN, UINT)
            .addControl(PC.DISABLE_SKIP);

    /**
     * Creates a new instance of a parser for optionally signed
     * integer numbers. Any successfully parsed string can be converted
     * into a <code>BigInteger</code> 
     * without throwing a <code>NumberFormatException</code>.
     */
    public static PParser signedInt() {
    	return new PSequence(new POptional(new PChar('-')), digits())
						.addControl(PC.DISABLE_SKIP);
    }

    /**
     * Matches an unsigned floating decimal of any length.
     */
    public final static PParser UNSIGNED_FLOAT = new PSequence(UINT, DOT, UINT)
            .addControl(PC.DISABLE_SKIP);

    /**
     * Matches a signed floating decimal of any length.
     */
    public final static PParser SIGNED_FLOAT = new PSequence(OPT_SIGN, UINT,
            DOT, UINT).addControl(PC.DISABLE_SKIP);

    /**
     * Creates a new instance of a parser for floating point numbers
     * with optional exponent. Any string successfully parsed by this
     * parser can be converted into a double without throwing a
     * <code>NumberFormatException</code>.
     */
    public static PParser floatNumber() {
		PChar dot = new PChar('.');
		PParser mantissa 
				= new POr(new PSequence(dot, digits()),
						  new PSequence(digits(),
						  				new POptional(
						  					new PSequence(dot, 
						  								  new PKleene(digit())))));
    	PParser e = new PChar('e').addControl(PC.DISABLE_CASE);
		POptional sign = new POptional(new PSet("-+"));
		POptional exponent 	= new POptional(new PSequence(e, sign, digits()));
		return new PSequence(new POptional(sign()), mantissa, exponent)
						.addControl(PC.DISABLE_SKIP);
    }
}