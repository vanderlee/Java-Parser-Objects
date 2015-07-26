/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package pobs.parser;

import pobs.PContext;
import pobs.PMatch;
import pobs.PScanner;

/**
 * Matches any of the tokens specified similar to an {@link pobs.parser.POr
 * POr}'ed list of [@linke pobs.parser.PToken PToken} objects. Please note that,
 * by default, this class return upon the first token in the list found.
 * Therefore you should take care in correctly ordering the tokens as supplied
 * to this class, eg. "Applepie" before "Apple". Uses case sensitivity and
 * alternatives directive. BNF: <code>parser := "aa" | "bb" | .. | "zz" </code>
 * (where any number of strings can be specified)
 * 
 * @author Martijn W. van der Lee
 */
public class PTokens extends pobs.PParser {
    private String[] tokens;

    /**
     * Sole constructor.
     * 
     * @param tokens
     *            sorted array of strings
     * @throws IllegalArgumentException
     *             if either no array is specified or array is empty.
     */
    public PTokens(String[] tokens) {
        this.tokens = tokens;
        if (tokens == null || tokens.length == 0) {
            throw new IllegalArgumentException("Undefined parsers.");
        }
    }

    public PTokens(String token1, String token2) {
        this(new String[] { token1, token2 });
    }

    public PTokens(String token1, String token2, String token3) {
        this(new String[] { token1, token2, token3 });
    }

    public PTokens(String token1, String token2, String token3, String token4) {
        this(new String[] { token1, token2, token3, token4 });
    }

    public PTokens(String token1, String token2, String token3, String token4,
            String token5) {
        this(new String[] { token1, token2, token3, token4, token5 });
    }

    public PMatch parse(PScanner input, long begin, PContext context) {
    	if (input.length() - begin <= 0) {
    		return pobs.PMatch.FALSE;
    	}   	
    	
        if (context.getDirective().getAlternatives() == pobs.PDirective.FIRST) {
            int max = 0;

            token: for (int t = 0; t < this.tokens.length; ++t) {
                int c = 0;
                for (; c < this.tokens[t].length()
                        && c < input.length() - begin; ++c) {
                    if (context.getDirective()
                            .convert(this.tokens[t].charAt(c)) != context
                            .getDirective().convert(input.charAt(begin + c))) {
                        if (c > max) {
                            max = c;
                        }
                        continue token;
                    }
                }

                return new pobs.PMatch(true, this.tokens[t].length());
            }

            return new pobs.PMatch(false, max);

        } else {
            pobs.PMatch match = PMatch.FALSE;

            token: for (int t = 0; t < this.tokens.length; ++t) {
                int c = 0;
                for (; c < this.tokens[t].length()
                        && c < input.length() - begin; ++c) {
                    if (context.getDirective()
                            .convert(this.tokens[t].charAt(c)) != context
                            .getDirective().convert(input.charAt(begin + c))) {
                        match = context.getDirective().alternative(match,
                                new pobs.PMatch(false, c));
                        continue token;
                    }
                }
                match = context.getDirective().alternative(match,
                        new pobs.PMatch(true, c));
            }

            return match;
        }
    }
}