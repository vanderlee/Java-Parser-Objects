/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package pobs.parser;

/**
 * Terminal parser which creates a set of characters to match from an encoded
 * input "rangestring".
 * Matches any single character specified in the rangestring.
 * Uses case sensitive directive.
 * This is a terminal parser for which no BNF equivalent exists.
 * 
 * The rangestring is encoded as a simple sequences of characters and markers.
 * A marker is a specific, predefined character placed between individual
 * characters in the rangestring to specify that each character in between
 * those two characters must also be matched by this parser.
 *	e.g.: <code>"a-d"</code> will match 'a', 'b', 'c' and 'd'
 * Placing the marker character at the beginning or end of the rangestring will
 * include the marker itself in the set.
 *	e.g.: <code>"a-c-"</code> will match 'a', 'b', 'c' and '-'
 * Individual characters can be included by simply omitting the range marker.
 *	e.g.: <code>"ae-h"</code> will match 'a', 'e', 'f', 'g', 'h' and 'z'
 * The default marker is the hyphen ('-') but you can specify an alternative
 * marker in the constructor, this may be useful if you want the hyphen to be
 * included in the set but do not want to specify it as either the first or last
 * character in the rangestring.
 * 
 * Characters in the range need not be in any specific order, the current
 * implementation can handle both incremental and decremental order or ranges.
 * Please note that this may change prior to a v1.0 release of POBS due to
 * performance reasons.
 * @author	Martijn W. van der Lee
 */
public class PRange extends pobs.parser.PSet {
    /**
     * Constructor.
     * @param range		string containing individual characters and ranges
     * @param marker	character used to mark ranges
     */
    public PRange(java.lang.String range, char marker) {
        super(PRange.decode(range, marker));
        setErrorInfo("a character from '" + range + "'");
    }

	/**
	 * Stub constructor which assumes the hyphen (-) character as the marker.
	 * @param range		string containing individual characters and ranges
	 */
	public PRange(java.lang.String range) {
		this(range, '-');
	}

    /**
     * Decodes a rangestring containing both individual characters as ranges
     * into a string which contains all individual characters implied in the
     * rangestring. 
     * @param range		string containing individual characters and ranges
     * @param marker	character used to mark ranges
     * @return			set of all characters decoded from the rangestring
     */
    public static java.lang.String decode(
        java.lang.String range,
        char marker) {

        java.lang.String set = new java.lang.String();

        for (int x = 0; x < range.length(); ++x) {

            char c = range.charAt(x);

            if (c == marker && x > 0 && ++x < range.length()) {

                char begin = set.charAt(set.length() - 1);
                char end = range.charAt(x);

                if (begin <= end) {
                    for (++begin; begin <= end; ++begin) {
                        set += begin;
                    }
                } else {
                    for (--begin; begin >= end; --begin) {
                        set += begin;
                    }
                }
            } else {
                set += c;
            }
        }

        return set;
    }
}
