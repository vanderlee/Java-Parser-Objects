/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package pobs.control;

import pobs.PControl;

/**
 * Various {@link PControl} constants.
 * 
 * @author Franz-Josef Elmer
 */
public class PC {
	/** Instance of {@link PDisableActions}. */
	public static final PControl DISABLE_ACTIONS = new PDisableActions();

	/** Instance of {@link PEnableActions}. */
	public static final PControl ENABLE_ACTIONS = new PEnableActions();

	/** Instance of {@link PDisableCase}. */
	public static final PControl DISABLE_CASE = new PDisableCase();

	/** Instance of {@link PEnableCase}. */
	public static final PControl ENABLE_CASE = new PEnableCase();

	/** Instance of {@link PDisableSkip}. */
	public static final PControl DISABLE_SKIP = new PDisableSkip();

	/** Instance of {@link PEnableSkip}. */
	public static final PControl ENABLE_SKIP = new PEnableSkip();

	/** Instance of {@link PFirstAlternative}. */
	public static final PControl FIRST_ALTERNATIVE = new PFirstAlternative();

	/** Instance of {@link PShortestAlternative}. */
	public static final PControl SHORTEST_ALTERNATIVE = new PShortestAlternative();

	/** Instance of {@link PLongestAlternative}. */
	public static final PControl LONGEST_ALTERNATIVE = new PLongestAlternative();
}
