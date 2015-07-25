/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package pobs.parser;

/**
 * Non-terminal parser which matches the same parser multiple times, optionally
 * limited. Uses skip directive. BNF: <code>this := parser[min, max]</code>
 * (where <code>min</code> is the minimum number of repeats required and
 * <code>max</code> is the maximum number of repeats allowed)
 * 
 * @author Martijn W. van der Lee
 */
public class PRepeat extends pobs.PParser {

    /**
     * Repeat upto any number of times. Though this field specifies a fixed
     * number of repetitions, it is for all practical purposes infinite.
     */
    public final static long INFINITE = java.lang.Long.MAX_VALUE;

    private long max;

    private long min;

    private pobs.PObject parser;

    /**
     * Stub constructor specifying one-or-more repetitions.
     * 
     * @param parserthe
     *            parser to be repeated
     */
    public PRepeat(pobs.PObject parser) {
        this(parser, 1, pobs.parser.PRepeat.INFINITE);
    }

    /**
     * Stub constructor allow specification an exact number of repetitions.
     * 
     * @param parser
     *            the parser to be repeated
     * @param count
     *            the exact number of repetitions required.
     */
    public PRepeat(pobs.PObject parser, long count) {
        this(parser, count, count);
    }

    /**
     * Constructor allowing specification of a lower and upper boundary to the
     * number of repetitions.
     * 
     * @param parser
     *            the parser to be repeated
     * @param min
     *            the minimum number of repetitions required.
     * @param max
     *            the maximum number of repetitions required.
     */
    public PRepeat(pobs.PObject parser, long min, long max) {
        super();

        this.parser = parser;
        this.min = min;
        this.max = max;
    }

    public pobs.PMatch parse(pobs.PScanner input, long begin,
            pobs.PContext context) {

        int matches = 0;
        long skipLength = 0;
        long length = context.getDirective().skip(input, begin);
        pobs.PMatch match = this.parser.process(input, begin + length, context);

        while (matches < this.max && match.isMatch()) {
            length += match.getLength() + skipLength;

            if (matches < this.min && !match.isMatch()) {
                return new pobs.PMatch(false, length);
            }

            ++matches;

            skipLength = context.getDirective().skip(input, begin + length);
            match = this.parser.process(input, begin + length + skipLength,
                    context);
        }

        return new pobs.PMatch(matches >= this.min, length);
    }
}