/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package pobs.parser;

/**
 * Matches succesful always, the length of the match is zero when the specified
 * parser fails or the length of the specified parsers' succesful match.
 * BNF: <code>this := parser?</code>
 * @author	Martijn W. van der Lee
 */
public class POptional extends pobs.PParser {
    private pobs.PObject parser;

    /**
     * Sole constructor.
     * @param	parser	a parser
     */
    public POptional(pobs.PObject parser) {
        super();

        this.parser = parser;
    }

    public pobs.PMatch parse(
        pobs.PScanner input,
        long begin,
        pobs.PContext context) {

        pobs.PMatch match = this.parser.process(input, begin, context);

        if (!match.isMatch()) {
            return new pobs.PMatch(true, 0);
        }

        return match;
    }
}
