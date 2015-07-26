/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package pobs.action;

/**
 * Outputs the parsed data which caused the triggering of this action to stdout.
 * 
 * @author Martijn W. van der Lee
 */
public class PPrint implements pobs.PAction {

    /**
     * Sole constructor.
     */
    public PPrint() {
        super();
    }

    public void perform(pobs.PTarget target, String data) {
        System.out.println(data);
    }
}