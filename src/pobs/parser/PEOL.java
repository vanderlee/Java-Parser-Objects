/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package pobs.parser;

import pobs.PContext;
import pobs.PMatch;
import pobs.PScanner;

/**
 * Terminal parser which matches a line-ending.
 * @author	Martijn W. van der Lee
 */
public class PEOL extends pobs.PParser {
    /**
     * Sole constructor.
     */
    public PEOL() {
        super();
    }

    /**
     * Matches the Cariage Return and/or Line Feed characters in any known
     * order, only one of each may appear.
     */
    public PMatch parse(PScanner input, long begin, PContext context) {

        if (begin < input.length()) {
            if (input.charAt(begin) == '\r') {
                if (begin + 1 < input.length()
                    && input.charAt(begin + 1) == '\n') {
                    return new pobs.PMatch(true, 2);
                }
                return PMatch.TRUE;
            } else if (input.charAt(begin) == '\n') {
                return PMatch.TRUE;
            }
        }

        return PMatch.FALSE;
    }
}
