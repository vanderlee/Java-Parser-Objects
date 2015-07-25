/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package pobs.parser;

import pobs.PContext;
import pobs.PMatch;
import pobs.PScanner;

/**
 * Matches successfull when no more input is available, always returns a length
 * of zero.
 * @author	Martijn W. van der Lee
 */
public class PEnd extends pobs.PParser {

    public PMatch parse(PScanner input, long begin, PContext context) {
    	return begin >= input.length() ? new PMatch(true, 0) : PMatch.FALSE;
    }
}
