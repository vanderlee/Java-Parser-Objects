/*
 * Copyright (c) 2004 Parser OBjectS group Released under ZLib/LibPNG license.
 * See "license.txt" for details.
 */
package pobs.action;

import java.util.Collection;

/**
 * @author Martijn W. van der Lee
 */
public interface PCollection<Object> extends Collection<Object> {
    /**
     * @see pobs.action.PCollection
     */
    public pobs.PAction addAction();
    /**
     * @see pobs.action.PCollection
     */
    public pobs.PAction addAction(final Object o);
    /**
     * @see pobs.action.PCollection
     */
    public pobs.PAction addAllAction(PCollection c);
    /**
     * @see pobs.action.PCollection
     */
    public pobs.PAction clearAction();
    /**
     * @see pobs.action.PCollection
     */
    public pobs.PAction removeAction();
    public pobs.PAction removeAction(final Object o);
    /**
     * @see pobs.action.PCollection
     */
    public pobs.PAction removeAllAction(PCollection c);
    /**
     * @see pobs.action.PCollection
     */
    public pobs.PAction retainAllAction(PCollection c);
}
