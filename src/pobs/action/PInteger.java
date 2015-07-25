/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package pobs.action;

/**
 * @author Martijn W. van der Lee
 */
public class PInteger extends PNumber {
    /**
     * PInteger constructor comment.
     */
    public PInteger() {
        super();
    }

    /**
     * PInteger constructor comment.
     * 
     * @param value
     *            int
     */
    public PInteger(int value) {
        super();

        this.value = new Integer(value);
    }
    
    protected Number numberFromDouble(double d) {
        return new Integer((int) java.lang.Math.round(d));
    }
}