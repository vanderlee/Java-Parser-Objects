/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package pobs.parser;

/**
 * Matches a delimited list of items within the specified number of items. The
 * user is responsible for making sure the item parser does not swollow the
 * delimiter parser.
 * 
 * @author Martijn W. van der Lee
 */
public class PList extends pobs.PParser {
    public final static long INFINITE = java.lang.Long.MAX_VALUE;

    private pobs.PObject parser;

    /**
     * Stub constructor taking the item and delimiter parser and assuming one-
     * or-more iterations.
     * 
     * @param item
     *            parser which matches the items in the list
     * @param delimiter
     *            parser which matches the delimiters interleaving the items
     */
    public PList(pobs.PObject item, pobs.PObject delimiter) {
        this(item, delimiter, 1, pobs.parser.PList.INFINITE);
    }

    /**
     * Stub constructor taking the item and delimiter parser and the exact
     * number of items in the list.
     * 
     * @param item
     *            parser which matches the items in the list
     * @param delimiter
     *            parser which matches the delimiters interleaving the items
     * @param count
     *            exact number of items required
     */
    public PList(pobs.PObject item, pobs.PObject delimiter, long count) {
        this(item, delimiter, count, count);
    }

    /**
     * Constructor taking the item and delimiter parser and the minimum and
     * maximum number of items in the list. Current implementation constructs a
     * POBS parser, future implementations may use dedicated code for better
     * performance.
     * 
     * @param item
     *            parser which matches the items in the list
     * @param delimiter
     *            parser which matches the delimiters interleaving the items
     * @param min
     *            minimum number of items required
     * @param max
     *            maximum number of items parsed
     */
    public PList(pobs.PObject item, pobs.PObject delimiter, long min, long max) {

        super();

        if (max < pobs.parser.PList.INFINITE) {
            --max;
        }

        if (min >= 1) {
            this.parser = new pobs.parser.PSequence(item,
                    new pobs.parser.PRepeat(new pobs.parser.PSequence(
                            delimiter, item), min - 1, max));
        } else {
            this.parser = new pobs.parser.POptional(new pobs.parser.PSequence(
                    item, new pobs.parser.PRepeat(new pobs.parser.PSequence(
                            delimiter, item), 0, max)));
        }
    }

    /**
     * Matches the specified number of items in a delimited list. Each two items
     * in the list must be separated by exactly one delimiter unless items are
     * optional.
     */
    public pobs.PMatch parse(pobs.PScanner input, long begin,
            pobs.PContext context) {

        return parser.process(input, begin, context);
    }
}