/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package pobs.parser;

/**
 * Semantic parser which verifies if a parsed numeric (double) value is within
 * specified, inclusive boundaries.
 * @author	Martijn W. van der Lee
 */
public class PLimit extends pobs.PParser {
    private double max;
    private double min;
    private pobs.PObject parser;

    /**
     * Sole constructor.
     * If the lower boundary is greater than the higher boundary, this parser
     * will never match; boundaries are not interchangeable. 
     * @param parser	the parser which will handle parsing the number
     * @param min		the lower boundary
     * @param max		the higher boundary
     */
    public PLimit(pobs.PObject parser, double min, double max) {
        super();

        this.parser = parser;
        this.min = min;
        this.max = max;
    }
    /**
     * Matches if the numerical (parsed as double) data parsed by the specified
     * parser is within the specified minimal and maximum boundaries, inclusive.
     */
    public pobs.PMatch parse(
        pobs.PScanner input,
        long begin,
        pobs.PContext context) {

        pobs.PMatch m = parser.process(input, begin, context);
        if (m.isMatch()) {
            double d = new Double(input.substring(begin, m.getLength()))
							.doubleValue();

            if (min <= d && d <= max) {
                return m;
            }
        }

        return new pobs.PMatch(false, m.getLength());
    }
}
