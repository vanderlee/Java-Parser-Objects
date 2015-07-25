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
 * Pointer to a single parser object. 
 * It is used to implement recursive parsers more easily
 * by creating the pointers prior to assigning them their parser.
 * Does nothing but pass through the parse call to the specified parser object.
 * 
 * @author Martijn W. van der Lee
 */
public class PParserPointer extends PParser {
    private PObject pObject;

    /**
     * Sets the parser to which this pointer references.
     * @param pObject	the references parser
     */
    public void set(PObject pObject) {
        this.pObject = pObject;
    }

    /**
     * Delegates call to the wrapped parser object.
     * @throws IllegalStateException if no parser object has been set by
     * 		   {@link #set(PObject)}.
     */
    public PMatch parse(PScanner input, long begin, PContext context) {
    	if (pObject == null) {
            throw new IllegalStateException(
            					"Wrapped parser object has not been set.");
        }

        return pObject.process(input, begin, context);
    }
    
	public PMatch process(PScanner input, long begin, PContext context) {
		return this.parse(input, begin, context);
	}
}
