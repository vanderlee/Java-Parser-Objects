/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package pobs.parser;

import pobs.PContext;
import pobs.PMatch;
import pobs.PScanner;

/**
 * Matches The tokens specified similar character by character.
 * Uses case sensitivity directive.
 * BNF: <code>parser := "abc"</code>
 * @author	Martijn W. van der Lee
 */
public class PToken extends pobs.PParser {
    private String token;

    /**
     * Sole constructor.
     * @param	token	a string which is matched
     */
    public PToken(java.lang.String token) {
        this.token = token;
    }

    public PMatch parse(PScanner input, long begin, PContext context) {

        if (this.token.length() > input.length() - begin) {
            return PMatch.FALSE;
        }

        int c = 0;

        for (; c < this.token.length() && c < input.length() - begin; c++) {
            if (context.getDirective().convert(this.token.charAt(c))
                != context.getDirective().convert(input.charAt(begin + c))) {
                return new pobs.PMatch(false, c);
            }
        }

        return new pobs.PMatch(true, c);
    }
}
