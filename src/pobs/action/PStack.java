/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package pobs.action;

/**
 * Copies the methods of the {@link java.util.Stack Stack}class and implements
 * a number of POBS actions allowing the stack to be manipulated by a parser.
 * 
 * @author Martijn W. van der Lee
 */
public class PStack extends PVector {
    public pobs.PAction popAction() {
        return new pobs.PAction() {
            public void perform(pobs.PTarget target, String data) {
                vector.remove(vector.size());
            }
        };
    }

    public pobs.PAction pushAction() {
        return new pobs.PAction() {
            public void perform(pobs.PTarget target, String data) {
                vector.add(data);
            }
        };
    }

    public pobs.PAction pushAction(final Object o) {
        return new pobs.PAction() {
            final Object object = o;

            public void perform(pobs.PTarget target, String data) {
                vector.add(o);
            }
        };
    }

    /**
     * Tests if this stack is empty.
     */
    public boolean empty() {
        return vector.isEmpty();
    }

    /**
     * Looks at the object at the top of this stack without removing it from the
     * stack.
     */
    public Object peek() {
        return vector.lastElement();
    }

    /**
     * Removes the object at the top of this stack and returns that object as
     * the value of this function.
     */
    public Object pop() {
        Object o = vector.lastElement();
        vector.remove(o);
        return o;
    }

    /**
     * Pushes an item onto the top of this stack.
     */
    public Object push(Object item) {
        vector.add(item);
        return item;
    }

    /**
     * Returns the 1-based position where an object is on this stack.
     */
    public int search(Object o) {
        return vector.indexOf(o);
    }
}