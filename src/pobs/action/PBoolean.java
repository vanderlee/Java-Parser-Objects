/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package pobs.action;

/**
 * @author Martijn W. van der Lee
 */
public class PBoolean {
    private Boolean value;

    /**
     * PBoolean constructor comment.
     */
    public PBoolean() {
        super();

        value = new Boolean(false);
    }

    /**
     * PBoolean constructor comment.
     */
    public PBoolean(boolean value) {
        super();

        this.value = new Boolean(value);
    }

    /**
     * Insert the method's description here.
     * 
     * @return boolean
     */
    public boolean booleanValue() {
        return value.booleanValue();
    }

    /**
     * Insert the method's description here.
     * 
     * @return boolean
     */
    public Boolean getBoolean() {
        return value;
    }

    public pobs.PAction setAction() {
        return new pobs.PAction() {
            public void perform(pobs.PTarget target, String data) {
                value = new Boolean(data);
            }
        };
    }

    public pobs.PAction setAction(final PBoolean c) {
        return new pobs.PAction() {
            PBoolean constant = c;

            public void perform(pobs.PTarget target, String data) {
                value = constant.getBoolean();
            }
        };
    }

    public pobs.PAction setAction(final boolean c) {
        return new pobs.PAction() {
            final Boolean constant = new Boolean(c);

            public void perform(pobs.PTarget target, String data) {
                value = constant;
            }
        };
    }

    public String toString() {
        return value.toString();
    }
}