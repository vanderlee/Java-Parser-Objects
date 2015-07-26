/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package pobs.action;

import pobs.PAction;
import pobs.PTarget;
import java.lang.String;
import java.util.Locale;

/**
 * This class wraps the {@link java.lang.String String}object and offers a
 * number of methods which return {@link pobs.PAction PAction}objects which can
 * 
 * @author Martijn W. van der Lee
 */
public class PString {
    private String string;

    public PString() {
        super();
    }

    public PString(String string) {
        super();

        this.string = string;
    }

    public String get() {
        return string;
    }

    public String toString() {
        return string;
    }

    public PAction set() {
        return new PAction() {
            public void perform(PTarget target, String data) {
                string = data;
            };
        };
    }

    public PAction set(final String constant) {
        return new PAction() {
            public void perform(PTarget target, String data) {
                string = constant;
            };
        };
    }

    public PAction concat() {
        return new PAction() {
            public void perform(PTarget target, String data) {
                string += data;
            };
        };
    }

    public PAction concat(final String constant) {
        return new PAction() {
            public void perform(PTarget target, String data) {
                string += constant;
            };
        };
    }

    public PAction clear() {
        return new PAction() {
            public void perform(PTarget target, String data) {
                string = "";
            };
        };
    }

    public PAction trim() {
        return new PAction() {
            public void perform(PTarget target, String data) {
                string = string.trim();
            };
        };
    }

    public PAction toLowerCase() {
        return new PAction() {
            public void perform(PTarget target, String data) {
                string = string.toLowerCase();
            };
        };
    }

    public PAction toLowerCase(final Locale locale) {
        return new PAction() {
            public void perform(PTarget target, String data) {
                string = string.toLowerCase(locale);
            };
        };
    }

    public PAction toUpperCase() {
        return new PAction() {
            public void perform(PTarget target, String data) {
                string = string.toUpperCase();
            };
        };
    }

    public PAction toUpperCase(final Locale locale) {
        return new PAction() {
            public void perform(PTarget target, String data) {
                string = string.toUpperCase(locale);
            };
        };
    }
}