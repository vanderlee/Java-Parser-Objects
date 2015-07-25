/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package pobs.parser;

/**
 * Matches the specified parser one-or-more times.
 * This class acts as a facade for {@link pobs.parser.PRepeat PRepeat}.
 * Uses the skip directive.
 * BNF: <code>this := parser+</code>
 * @author	Martijn W. van der Lee
 */
public class PMultiple extends pobs.parser.PRepeat {
	
    /**
     * Sole constructor.
     * @param	parser	a parser 
     */
    public PMultiple(pobs.PObject parser) {
        super(parser, 1, pobs.parser.PRepeat.INFINITE);
    }
}
