/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package pobs.scanner;

import pobs.PScanner;

/**
 * A {@link PTextScanner}decorator which returns line and column numbers of a
 * texts with newline and tab characters.
 * 
 * @author Franz-Josef Elmer
 */
public class PTextScanner implements PScanner {
    private final PScanner scanner;

    private final int tabStep;

    private long previousIndex;

    private int previousLine;

    private int previousColumn;

    /**
     * Creates an instance for the specified scanner. Short cut of
     * <code>new PTextScanner(scanner, 8)</code>.
     * 
     * @param scanner
     *            Wrapped scanner instance. <code>null</code> is not allowed.
     */
    public PTextScanner(PScanner scanner) {
        this(scanner, 8);
    }

    /**
     * Creates an instance for the specified scanner and tabulator step.
     * 
     * @param scanner
     *            Wrapped scanner instance. <code>null</code> is not allowed.
     * @param tabStep
     *            Tabulator step used to calculate the column number when an
     *            ASCII TAB character occurs. Must be a positive number.
     * @throws IllegalArgumentException
     *             if one of the two conditions at the arguments are violated.
     */
    public PTextScanner(PScanner scanner, int tabStep) {
        if (scanner == null) {
            throw new IllegalArgumentException("Undefined PScanner");
        }
        this.scanner = scanner;
        if (tabStep < 1) {
            throw new IllegalArgumentException(
                    "Tab step is not a positive number: " + tabStep);
        }
        this.tabStep = tabStep;

        previousIndex = 0;
        previousLine = 0;
        previousColumn = 0;
    }

    public char charAt(long index) throws IndexOutOfBoundsException {
        return scanner.charAt(index);
    }

    public long length() {
        return scanner.length();
    }

    public String substring(long beginIndex, long endIndex) {
        return scanner.substring(beginIndex, endIndex);
    }

    /**
     * Returns the number of lines and columns between from the start of the
     * input upto the specified index.
     * 
     * @param index
     *            the index upto which is counted
     */
    public PPosition getPosition(long index) {
        if (index > previousIndex) {
            PPosition newPosition = getRelativePosition(previousIndex, index);

            if (newPosition.getLine() >= 1) {
                previousLine += newPosition.getLine();
                previousColumn = newPosition.getColumn();
            } else {
                previousColumn += newPosition.getColumn();
            }
            previousIndex = index;

            return new PPosition(previousLine, previousColumn);
        } else if (index < previousIndex) {
            PPosition newPosition = getRelativePosition(0, index);

            previousLine += newPosition.getLine();
            previousColumn = newPosition.getColumn();
            previousIndex = index;

            return newPosition;
        } else {
            return new PPosition(previousLine, previousColumn);
        }
    }

    /**
     * Returns the number of lines and columns between the from and to indexes.
     * 
     * @param from
     *            the base index from where to start counting
     * @param to
     *            the index upto which is counted
     */
    public PPosition getRelativePosition(long from, long to) {
        int line = 0;
        int column = 0;
        long n = Math.min(scanner.length(), to);
        char c = 0;
        for (long i = from; i < n; i++) {
            char lastC = c;
            c = scanner.charAt(i);
            if (c == '\n' || c == '\r') {
                column = 0;
                if (c == '\r' || lastC != '\r') {
                    line++;
                }
            } else if (c == '\t') {
                column = ((column / tabStep) + 1) * tabStep;
            } else {
                column++;
            }
        }
        return new PPosition(line, column);
    }
}