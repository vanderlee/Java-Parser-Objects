/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package pobs.parser;

/**
 * Matches the specified parser zero-or-more times.
 * This class acts as a facade for {@link pobs.parser.PRepeat PRepeat}.
 * Uses the skip directive.
 * BNF: <code>this := parser*</code>
 * @author	Martijn W. van der Lee
 */
public class PKleene extends PRepeat {
	
    /**
     * Sole constructor.
     */
    public PKleene(pobs.PObject parser) {
        super(parser, 0, pobs.parser.PRepeat.INFINITE);
    }
}
