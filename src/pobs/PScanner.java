/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package pobs;

/**
 * Provides the standard interface to which all iterators must conform. The use
 * of a generic iterator allows any parser to be linked to any source of input
 * and enables the iterator implementor to optimize where required. The PScanner
 * defines only the output of an iterator to be in the form of a standard Java
 * Character or String, it makes no demands as to the input. It is upto the
 * class implementing this interface to make any necessary conversions to
 * support these output formats. <p/>Note, that during parsing the index for
 * {@link #charAt(long)}might not increase monotonically!
 * 
 * @author Martijn W. van der Lee
 */
public interface PScanner {
    /**
     * Returns the character at the specified location.
     * 
     * @param index
     *            position of the character to return
     * @return character at the specified index
     * @throws IndexOutOfBoundsException
     *             when the specified index is either less than zero or equal or
     *             more than the length
     */
    public char charAt(long index) throws IndexOutOfBoundsException;

    /**
     * Gives the length of this scanners' content. That is; the last position
     * which can be indexed.
     * 
     * @return the length of this iterator.
     */
    public long length();

    /**
     * Returns the part of the input from the beginning upto the ending index
     * locations.
     * 
     * @param beginIndex
     *            starting offset from which to extract the substring
     * @param endIndex
     *            ending offset at which the substring ends
     * @return the substring
     * @see java.lang.String
     */
    public java.lang.String substring(long beginIndex, long endIndex);
}