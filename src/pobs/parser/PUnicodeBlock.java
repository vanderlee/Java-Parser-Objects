/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package pobs.parser;

import pobs.PContext;
import pobs.PMatch;
import pobs.PScanner;

/**
 * Matches any single character which is part of the specified UnicodeBlock.
 * Note that this class explicitely does not use the case sensitive directive.
 * <p>
 * Also note, that this class is not JDK 1.1 compliant. At least JDK 1.2 is
 * needed.
 * 
 * @author Martijn W. van der Lee
 */
public class PUnicodeBlock extends pobs.PParser {
    public Character.UnicodeBlock unicodeBlock;

    /**
     * Sole constructor taking the UnicodeBlock to match.
     * 
     * @param unicodeBlock
     *            the UnicodeBlock to match
     */
    public PUnicodeBlock(Character.UnicodeBlock unicodeBlock) {
        this.unicodeBlock = unicodeBlock;
    }

    public PMatch parse(PScanner input, long begin, PContext context) {

        if (begin < input.length()
                && Character.UnicodeBlock.of(input.charAt(begin)) == unicodeBlock) {
            return PMatch.TRUE;
        }

        return PMatch.FALSE;
    }
}