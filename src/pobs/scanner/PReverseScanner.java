/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package pobs.scanner;

/**
 * Reverses any specified scanner.
 * 
 * @author Martijn W. van der Lee
 */
public class PReverseScanner implements pobs.PScanner {
    private pobs.PScanner scanner;

    /**
     * Sole constructor, takes another scanner.
     * 
     * @param scanner
     *            the source string for this iterator
     */
    public PReverseScanner(pobs.PScanner scanner) {
        super();

        this.scanner = scanner;
    }

    public char charAt(long index) throws IndexOutOfBoundsException {
        return scanner.charAt(scanner.length() - ++index);
    }

    public long length() {
        return scanner.length();
    }

    public java.lang.String substring(long beginIndex, long endIndex) {
        return new StringBuffer(scanner.substring(scanner.length() - endIndex,
                scanner.length() - beginIndex)).reverse().toString();
    }
}