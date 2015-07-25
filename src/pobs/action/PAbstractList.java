/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package pobs.action;

import java.util.Iterator;

/**
 * @author Martijn W. van der Lee
 */
public abstract class PAbstractList extends PAbstractCollection implements
        PList {

    public pobs.PAction addAction(final int i) {
        return new pobs.PAction() {
            final int index = i;

            public void perform(pobs.PTarget target, String data) {
                addToContainer(index, data);
            };
        };
    }

    public pobs.PAction addAction(final int i, final Object o) {
        return new pobs.PAction() {
            final Object object = o;

            final int index = i;

            public void perform(pobs.PTarget target, String data) {
                addToContainer(index, object);
            };
        };
    }

    public pobs.PAction addAction(final PNumber i) {
        return new pobs.PAction() {
            final PNumber index = i;

            public void perform(pobs.PTarget target, String data) {
                addToContainer(index.intValue(), data);
            };
        };
    }

    public pobs.PAction addAction(final PNumber i, final Object o) {
        return new pobs.PAction() {
            final Object object = o;

            final PNumber index = i;

            public void perform(pobs.PTarget target, String data) {
                addToContainer(index.intValue(), object);
            };
        };
    }

    public pobs.PAction addAllAction(final int i, final PCollection c) {
        return new pobs.PAction() {
            final int index = i;

            final PCollection collection = c;

            public void perform(pobs.PTarget target, String data) {
                int pos = index;
                Iterator i = collection.iterator();

                while (i.hasNext()) {
                    addToContainer(pos++, i.next());
                }
            };
        };
    }

    public pobs.PAction addAllAction(final PNumber i, final PCollection c) {
        return new pobs.PAction() {
            final PNumber index = i;

            final PCollection collection = c;

            public void perform(pobs.PTarget target, String data) {
                int pos = index.intValue();
                Iterator i = collection.iterator();

                while (i.hasNext()) {
                    addToContainer(pos++, i.next());
                }
            };
        };
    }

    /**
     * @see pobs.action.PCollection
     */
    protected abstract void addToContainer(int index, Object object);

    /**
     * @see pobs.action.PCollection
     */
    public pobs.PAction removeAction(final int i) {
        return new pobs.PAction() {
            final int index = i;

            public void perform(pobs.PTarget target, String data) {
                Iterator i = iterator();

                int j = 1;

                while (i.hasNext()) {
                    i.next();

                    if (j == index) {
                        i.remove();
                        break;
                    }

                    ++j;
                }
            };
        };
    }

    /**
     * @see pobs.action.PCollection
     */
    public pobs.PAction removeAction(final PNumber i) {
        return new pobs.PAction() {
            final PNumber index = i;

            public void perform(pobs.PTarget target, String data) {
                Iterator i = iterator();

                int j = 1;

                while (i.hasNext()) {
                    i.next();

                    if (j == index.intValue()) {
                        i.remove();
                        break;
                    }

                    ++j;
                }
            };
        };
    }

    /**
     * @see pobs.action.PList
     */
    public pobs.PAction setAction(final int i, final Object o) {
        return new pobs.PAction() {
            final int index = i;

            final Object object = o;

            public void perform(pobs.PTarget target, String data) {
                Iterator i = iterator();

                int j = 1;

                while (i.hasNext()) {
                    i.next();

                    if (j == index) {
                        i.remove();
                        addToContainer(j, object);
                        break;
                    }

                    ++j;
                }
            };
        };
    }

    /**
     * @see pobs.action.PList
     */
    public pobs.PAction setAction(final PNumber i, final Object o) {
        return new pobs.PAction() {
            final PNumber index = i;

            final Object object = o;

            public void perform(pobs.PTarget target, String data) {
                Iterator i = iterator();

                int j = 1;

                while (i.hasNext()) {
                    i.next();

                    if (j == index.intValue()) {
                        i.remove();
                        addToContainer(j, object);
                        break;
                    }

                    ++j;
                }
            };
        };
    }
}