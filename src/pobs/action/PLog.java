/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package pobs.action;

/**
 * Outputs a specified string to stdout whenever this action is performed.
 * 
 * @author Martijn W. van der Lee
 */
public class PLog implements pobs.PAction {
    public java.lang.String string;

    /**
     * Sole constructor.
     * 
     * @param string
     *            the string to output on action.
     */
    public PLog(java.lang.String string) {
        super();

        this.string = string;
    }

    public void perform(pobs.PTarget target, java.lang.String data) {
        System.out.println(this.string);
    }
}