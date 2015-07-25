/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package pobs.action;

/**
 * @author Martijn W. van der Lee
 */
abstract public class PNumber implements Comparable {
    protected Number value;

    /**
     * PBoolean constructor comment.
     */
    public PNumber() {
        super();

        value = new Integer(0);
    }

    public byte byteValue() {
        return value.byteValue();
    }

    public int compareTo(Object o) {
        if (o instanceof PNumber) {
            return (this.value.intValue() - ((PNumber) o).intValue());
        } else if (o instanceof Number) {
            return (this.value.intValue() - ((Number) o).intValue());
        } else {
            throw new ClassCastException();
        }
    }

    public double doubleValue() {
        return value.doubleValue();
    }

    public float floatValue() {
        return value.floatValue();
    }

    /**
     * Insert the method's description here.
     * 
     * @return boolean
     */
    public Number getNumber() {
        return value;
    }

    /**
     * Insert the method's description here.
     * 
     * @return boolean
     */
    public int intValue() {
        return value.intValue();
    }

    public long longValue() {
        return value.longValue();
    }

    public pobs.PAction setAction() {
        return new pobs.PAction() {
            public void perform(pobs.PTarget target, String data) {
                value = new Integer(data);
            }
        };
    }

    public pobs.PAction setAction(final PNumber c) {
        return new pobs.PAction() {
            final PNumber constant = c;

            public void perform(pobs.PTarget target, String data) {
                value = getNumber();
            }
        };
    }

    public short shortValue() {
        return value.shortValue();
    }

    public String toString() {
        return value.toString();
    }

    abstract protected Number numberFromDouble(double d);

    public pobs.PAction addAction(final PNumber n) {
        return new pobs.PAction() {
            final PNumber number = n;

            public void perform(pobs.PTarget target, String data) {
                value = numberFromDouble(value.doubleValue()
                        + number.doubleValue());
            }
        };
    }
    
    public pobs.PAction addAction() {
        return new pobs.PAction() {
            public void perform(pobs.PTarget target, String data) {
                value = numberFromDouble(value.doubleValue()
                        + Double.parseDouble(data));
            }
        };
    }

    public pobs.PAction subtractAction(final PNumber n) {
        return new pobs.PAction() {
            final Number number = n.value;

            public void perform(pobs.PTarget target, String data) {
                value = numberFromDouble(value.doubleValue()
                        - number.doubleValue());
            }
        };
    }

    public pobs.PAction multiplyAction(final PNumber n) {
        return new pobs.PAction() {
            final Number number = n.value;

            public void perform(pobs.PTarget target, String data) {
                value = numberFromDouble(value.doubleValue()
                        * number.doubleValue());
            }
        };
    }

    public pobs.PAction divideAction(final PNumber n) {
        return new pobs.PAction() {
            final Number number = n.value;

            public void perform(pobs.PTarget target, String data) {
                value = numberFromDouble(value.doubleValue()
                        / number.doubleValue());
            }
        };
    }

    public pobs.PAction moduloAction(final PNumber n) {
        return new pobs.PAction() {
            final Number number = n.value;

            public void perform(pobs.PTarget target, String data) {
                value = numberFromDouble(value.doubleValue()
                        % number.doubleValue());
            }
        };
    }

    public pobs.PAction maxAction(final PNumber n) {
        return new pobs.PAction() {
            final Number number = n.value;

            public void perform(pobs.PTarget target, String data) {
                if (value.doubleValue() < number.doubleValue()) {
                    value = numberFromDouble(number.doubleValue());
                }
            }
        };
    }

    public pobs.PAction minAction(final PNumber n) {
        return new pobs.PAction() {
            final Number number = n.value;

            public void perform(pobs.PTarget target, String data) {
                if (value.doubleValue() > number.doubleValue()) {
                    value = numberFromDouble(number.doubleValue());
                }
            }
        };
    }
}