/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package pobs;

/**
 * Immutable class which contains information on success/failure and the
 * length/point-of-failure of a parse.
 * 
 * @author Martijn W. van der Lee
 */
public class PMatch {
	/** Convenient constant for single character parsers. */
	public static final PMatch TRUE = new PMatch(true, 1),
							   FALSE = new PMatch(false, 0);
	
    private final boolean match;

    private final long length;

    /**
     * Sole constructor.
     * 
     * @param match
     *            specifies whether the match is succesful or failed
     * @param length
     *            the length of either the success or exact point of failure. In
     *            case of a failure, the length needs not be the exact point but
     *            doing so will allow the client code to know exactly where a
     *            parser failed. POBS' own parsers are all guarenteed to return
     *            the exact point of failure.
     */
    public PMatch(boolean match, long length) {
        this.match = match;

        if (length < 0) {
            throw new IndexOutOfBoundsException();
        }
        this.length = length;
    }

    /**
     * Returns the length of the successfull match or the length of the input
     * which could be matched in case of failure.
     * 
     * @return length of the match or failure
     */
    public long getLength() {
        return length;
    }

    /**
     * Check whether the match was successful.
     * 
     * @return true only when entire match successful
     */
    public boolean isMatch() {
        return match;
    }
}