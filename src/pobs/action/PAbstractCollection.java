/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package pobs.action;

import java.util.Iterator;
import java.util.AbstractCollection;

/**
 * @author Martijn W. van der Lee
 */
public abstract class PAbstractCollection extends AbstractCollection implements
        PCollection {

    /**
     * @see pobs.action.PCollection
     */
    public pobs.PAction addAction() {
        return new pobs.PAction() {
            public void perform(pobs.PTarget target, String data) {
                add(data);
            };
        };
    }

    public pobs.PAction addAction(final Object o) {
        return new pobs.PAction() {
            final Object object = o;

            public void perform(pobs.PTarget target, String data) {
                add(object);
            };
        };
    }

    public pobs.PAction addAllAction(final PCollection c) {
        return new pobs.PAction() {
            final PCollection collection = c;

            public void perform(pobs.PTarget target, String data) {
                Iterator i = collection.iterator();

                while (i.hasNext()) {
                    add(i.next());
                }
            };
        };
    }

    /**
     * @see pobs.action.PCollection
     */
    public pobs.PAction clearAction() {
        return new pobs.PAction() {
            public void perform(pobs.PTarget target, String data) {
                Iterator i = iterator();

                while (i.hasNext()) {
                    i.next();
                    i.remove();
                }
            };
        };
    }

    /**
     * @see pobs.action.PCollection
     */
    public pobs.PAction removeAction() {
        return new pobs.PAction() {
            public void perform(pobs.PTarget target, String data) {
                Iterator i = iterator();

                while (i.hasNext()) {
                    if (i.next().equals(data)) {
                        i.remove();
                        break;
                    }
                }
            };
        };
    }

    /**
     * @see pobs.action.PCollection
     */
    public pobs.PAction removeAction(final Object o) {
        return new pobs.PAction() {
            final Object object = o;

            public void perform(pobs.PTarget target, String data) {
                Iterator i = iterator();

                while (i.hasNext()) {
                    if (i.next().equals(object)) {
                        i.remove();
                        break;
                    }
                }
            };
        };
    }

    /**
     * @see pobs.action.PCollection
     */
    public pobs.PAction removeAllAction(final PCollection c) {
        return new pobs.PAction() {
            final PCollection collection = c;

            public void perform(pobs.PTarget target, String data) {
                Iterator i = collection.iterator();

                while (i.hasNext()) {
                    if (contains(i.next())) {
                        i.remove();
                    }
                }
            };
        };
    }

    /**
     * @see pobs.action.PCollection
     */
    public pobs.PAction retainAllAction(final PCollection c) {
        return new pobs.PAction() {
            final PCollection collection = c;

            public void perform(pobs.PTarget target, String data) {
                Iterator i = collection.iterator();

                while (i.hasNext()) {
                    if (!contains(i.next())) {
                        i.remove();
                    }
                }
            };
        };
    }
}