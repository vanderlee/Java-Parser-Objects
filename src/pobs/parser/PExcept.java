/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package pobs.parser;

import pobs.PContext;
import pobs.PMatch;
import pobs.PScanner;

/**
 * Matches if the parsed input matches the left-hand parser and not matches the
 * right-hand parser. This parser will also return a succesfull match if the
 * right-hand parser could not match as much of the input as the left-hand
 * parser.
 * BNF: <code>this := left - right</code>
 * @author	Martijn W. van der Lee
 */
public class PExcept extends pobs.PParser {
    private pobs.PObject parserLeft;
    private pobs.PObject parserRight;

    /**
     * Sole constructor taking a left-hand and right-hand pobs.PObject.
     * @param	parserLeft	the left-hand parser, which must match
     * @param	parserRight	the right-hand parser, which must not match 
     */
    public PExcept(pobs.PObject parserLeft, pobs.PObject parserRight) {
        super();

        this.parserLeft = parserLeft;
        this.parserRight = parserRight;
    }

    public PMatch parse(PScanner input, long begin, PContext context) {

        PMatch matchRight =
            this.parserRight.process(input, begin, context);
        if (matchRight.isMatch()) {
            return PMatch.FALSE;
        }

        return this.parserLeft.process(input, begin, context);

        /*
                pobs.PMatch matchLeft = this.parserLeft.process(input, begin, directive);
                if (matchLeft.isMatch()) {
                    pobs.PMatch matchRight =
                        this.parserRight.process(input, begin, directive);
                    if (matchRight.isMatch()
                        && matchLeft.getLength() <= matchRight.getLength()) {
                        matchLeft.setMatch(false);
                    }
                }
                return matchLeft;
        */
    }
}
