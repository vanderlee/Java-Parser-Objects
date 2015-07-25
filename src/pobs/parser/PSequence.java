/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package pobs.parser;

import pobs.PContext;
import pobs.PMatch;
import pobs.PObject;
import pobs.PParser;
import pobs.PScanner;

/**
 * Matches any number of specified parsers in the given order, each one
 * following the previous in sequence. Uses the skip directive. BNF:
 * <code>this := parser[0] .. parser[..]</code> (any number of parsers can be
 * specified)
 * 
 * @author Martijn W. van der Lee
 */
public class PSequence extends PParser {
    private PObject[] parsers = null;

    /**
     * Constructor taking an array of {@link PObject parsers}.
     * 
     * @param parsers
     *            an array of parsers
     * @throws IllegalArgumentException
     *             if either no array is specified or array is empty.
     */
    public PSequence(PObject[] parsers) {
        this.parsers = parsers;
        if (parsers == null || parsers.length == 0) {
            throw new IllegalArgumentException("Undefined parsers.");
        }
    }

    /**
     * Stub constructor taking two parsers.
     */
    public PSequence(PObject parser1, PObject parser2) {
        this(new PObject[] { parser1, parser2 });
    }

    /**
     * Stub constructor taking three parsers.
     */
    public PSequence(PObject parser1, PObject parser2, PObject parser3) {
        this(new PObject[] { parser1, parser2, parser3 });
    }

    /**
     * Stub constructor taking four parsers.
     */
    public PSequence(PObject parser1, PObject parser2, PObject parser3,
            PObject parser4) {
        this(new PObject[] { parser1, parser2, parser3, parser4 });
    }

    /**
     * Stub constructor taking five parsers.
     */
    public PSequence(PObject parser1, PObject parser2, PObject parser3,
            PObject parser4, PObject parser5) {
        this(new PObject[] { parser1, parser2, parser3, parser4, parser5 });
    }

    public PMatch parse(PScanner input, long begin, PContext context) {
        int length = 0;
        long skipped = 0;
        PMatch match;

        for (int p = 0; p < this.parsers.length; ++p) {
            length += context.getDirective().skip(input, begin + length);

            match = this.parsers[p].process(input, begin + length, context);

            length += match.getLength();

            if (!match.isMatch()) {
                return new PMatch(false, length);
            }
        }

        return new PMatch(true, length);
    }
}