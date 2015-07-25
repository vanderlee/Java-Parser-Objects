package test;

import junit.framework.TestCase;

/**
 * Provided specialized methods for testing POBS parsers.
 * 
 * @author Martijn W. van der Lee
 */
public abstract class TestCaseParser extends TestCase {
    public TestCaseParser() {
        super();
    }

    public TestCaseParser(String arg0) {
        super(arg0);
    }

    public void assertMatch(java.lang.String message, pobs.PMatch match,
            long length) {
        assertTrue(message + ", match expected", match.isMatch());
        assertEquals(message + ", length", length, match.getLength());
    }

    public void assertMismatch(java.lang.String message, pobs.PMatch match,
            long length) {
        assertFalse(message + ", mismatch expected", match.isMatch());
        assertEquals(message + ", length", length, match.getLength());
    }

    final public pobs.PMatch parse(pobs.PObject parser, java.lang.String input) {
        return parse(parser, input, 0);
    }

    final public pobs.PMatch parse(pobs.PObject parser, java.lang.String input,
            long begin) {
        return parser.process(new pobs.scanner.PStringScanner(input), begin,
                new pobs.PContext());
    }

    final public pobs.PMatch parse(pobs.PObject parser, java.lang.String input,
            pobs.PDirective directive) {
        return parser.process(new pobs.scanner.PStringScanner(input), 0,
                new pobs.PContext(directive));
    }
}