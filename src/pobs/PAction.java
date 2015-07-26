/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package pobs;

/**
 * Interface which allows semantic actions to be linked to parsers. <p/>In
 * order to use actions in a thread-safe web of parsers they have to be
 * stateless. They should only manipulate the {@link PTarget}provided.
 * 
 * @author Martijn W. van der Lee
 * @author Franz-Josef Elmer
 */
public interface PAction {
    /**
     * Performs an action on the specified target using the specified data.
     * 
     * @param target
     *            Target object manipulated by the action. Can be
     *            <code>null</code>. In this case stateless actions will do
     *            nothing.
     * @param data
     *            the matched string for the parser the action was linked to. If
     *            this action was called upon a mismatch, this is the part
     *            matched upto but excluding the point of failure.
     */
    public void perform(PTarget target, String data);
}