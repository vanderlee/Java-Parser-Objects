/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package pobs.parser;

import pobs.PContext;
import pobs.PMatch;
import pobs.PScanner;

/**
 * Matches any sequence of atleast one whitespace character, as defined by
 * Character.isWhitespace, from input.
 * @author	Martijn W. van der Lee
 */
public class PWhitespace extends pobs.PParser {
    
    public PMatch parse(PScanner input, long begin, PContext context) {
        	
        long position = begin;
        while (position < input.length()
            && Character.isWhitespace(input.charAt(position))) {
            ++position;
        }

        if (position > begin) {
            return new pobs.PMatch(true, position - begin);
        }

        return PMatch.FALSE;
    }
}
