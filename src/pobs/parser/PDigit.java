/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package pobs.parser;

import pobs.PContext;
import pobs.PMatch;
import pobs.PScanner;

/**
 * Matches any digit character ranging as defined by
 * java.lang.Character.isDigit.
 * This is a terminal parser for which no BNF equivalent exists.
 * @author	Martijn W. van der Lee
 */
public class PDigit extends pobs.PParser {
    /**
     * Checks if there is any input left and matches any single character as
     * defined by java.lang.Character.isDigit.
     */
    public PMatch parse(PScanner input, long begin, PContext context) {

        if (begin < input.length()
            && java.lang.Character.isDigit(input.charAt(begin))) {
            return PMatch.TRUE;
        }

        return PMatch.FALSE;
    }
}
