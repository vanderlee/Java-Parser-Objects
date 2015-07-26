/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package pobs.parser;

import pobs.PContext;
import pobs.PMatch;
import pobs.PScanner;

/**
 * Matches only if the next character on input is the character specified.
 * Uses case sensitivity directive.
 * BNF: <code>parser := 'c'</code> (where <code>c</code> is the character
 * specified)
 * @author	Martijn W. van der Lee
 */
public class PChar extends pobs.PParser {
    private char character;

    /**
     * Sole constructor which allows specification of the character to be
     * matched.
     * @param	character	the character that will be matched
     */
    public PChar(char character) {
        this.character = character;
        setErrorInfo("'" + character + "'");
    }

    /**
     * Determines if there is any input left and returns a succesful match if
     * the next character on input matches the specified character.
     */
    public PMatch parse(PScanner input, long begin, PContext context) {

        if (begin < input.length()
            && context.getDirective().convert(input.charAt(begin))
                == context.getDirective().convert(this.character)) {
            return PMatch.TRUE;
        }

        return PMatch.FALSE;
    }
}
