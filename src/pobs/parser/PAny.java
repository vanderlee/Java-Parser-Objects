/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package pobs.parser;

import pobs.PContext;
import pobs.PMatch;
import pobs.PScanner;

/**
 * Matches any single character if there is any left on the input.
 * This is a terminal parser for which no BNF equivalent exists.
 * @author	Martijn W. van der Lee
 */
public class PAny extends pobs.PParser {

    public PMatch parse(PScanner input, long begin, PContext context) {
    	return begin < input.length() ? PMatch.TRUE : PMatch.FALSE;
    }
}
