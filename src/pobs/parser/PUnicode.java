package pobs.parser;

import pobs.PContext;
import pobs.PMatch;
import pobs.PScanner;

/**
 * Matches any single character as long as it is defined in Unicode.
 * This is a terminal parser for which no BNF equivalent exists.
 * @author	Martijn W. van der Lee
 */
public class PUnicode extends pobs.PParser {
    public PMatch parse(PScanner input, long begin, PContext context) {
            
        if (begin < input.length()
            && Character.isDefined(input.charAt(begin))) {
            return PMatch.TRUE;
        }

        return PMatch.FALSE;
    }
}
