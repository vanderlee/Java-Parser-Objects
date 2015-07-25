/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package pobs.parser;

import pobs.PContext;
import pobs.PMatch;
import pobs.PScanner;

/**
 * Matches any letter character; any alphabetic character in any of the
 * international alphabets defined in UniCode. 
 * Uses case sensitive directive.
 * @author	Martijn W. van der Lee
 */
public class PLetter extends pobs.PParser {
    public PMatch parse(PScanner input, long begin, PContext context) {

        if (begin < input.length()
            && Character.isLetter(context.getDirective().convert(input.charAt(begin)))) {
            return PMatch.TRUE;
        }

        return PMatch.FALSE;
    }
}
