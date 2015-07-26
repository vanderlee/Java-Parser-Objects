/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package pobs.parser;

import pobs.PContext;
import pobs.PMatch;
import pobs.PScanner;

/**
 * Tries a number of parsers specified until one of them matches. The order of
 * the parsers determines the order of parsing. Only the parsers upto the first
 * which matches the input are parsed so be sure to put the longest of two
 * parses which share the same beginning before the other. To clarify,
 * <code>rule := "a" | "ab"</code> will match "a" even when input is "abc"
 * because the second parser is never evaluated if the first one matched. Uses
 * alternatives directive.
 * BNF: <code>this := parser[0] | .. | parser[..]</code>
 * (any number of parsers can be specified).
 * 
 * @author Martijn W. van der Lee
 */
public class POr extends pobs.PParser {
    private pobs.PObject[] parsers;

    /**
     * Constructor taking an array of any number of parsers.
     * 
     * @param parsers
     *            array of parsers
     * @throws IllegalArgumentException
     *             if either no array is specified or array is empty.
     */
    public POr(pobs.PObject[] parsers) {
        this.parsers = parsers;
        if (parsers == null || parsers.length == 0) {
            throw new IllegalArgumentException("Undefined parsers.");
        }
    }

    /**
     * Stub constructor taking two parsers.
     */
    public POr(pobs.PObject parser1, pobs.PObject parser2) {
        super();

        this.parsers = new pobs.PObject[] { parser1, parser2 };
    }

    /**
     * Stub constructor taking three parsers.
     */
    public POr(pobs.PObject parser1, pobs.PObject parser2, pobs.PObject parser3) {

        super();

        this.parsers = new pobs.PObject[] { parser1, parser2, parser3 };
    }

    /**
     * Stub constructor taking four parsers.
     */
    public POr(pobs.PObject parser1, pobs.PObject parser2,
            pobs.PObject parser3, pobs.PObject parser4) {

        super();

        this.parsers = new pobs.PObject[] { parser1, parser2, parser3, parser4 };
    }

    /**
     * Stub constructor taking five parsers.
     */
    public POr(pobs.PObject parser1, pobs.PObject parser2,
            pobs.PObject parser3, pobs.PObject parser4, pobs.PObject parser5) {

        super();

        this.parsers = new pobs.PObject[] { parser1, parser2, parser3, parser4,
                parser5 };
    }

    public PMatch parse(PScanner input, long begin, PContext context) {
        PMatch match = PMatch.FALSE;

        if (context.getDirective().getAlternatives() == pobs.PDirective.FIRST) {
            for (int p = 0; p < this.parsers.length; ++p) {
                pobs.PMatch m = this.parsers[p].process(input, begin, context);
                if (m.isMatch()) {
                    return m;
                }
                if (m.getLength() > match.getLength()) {
                    match = m;
                }
            }
        } else {
            for (int p = 0; p < this.parsers.length; ++p) {
                match = context.getDirective().alternative(match,
                        this.parsers[p].process(input, begin, context));
            }
        }

        return match;
    }
}