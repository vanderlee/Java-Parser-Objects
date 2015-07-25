/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package test;

/**
 * @author	Martijn W. van der Lee 
 */
public class PTokensTest extends TestCaseParser {
    public PTokensTest(String arg0) {
        super(arg0);
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(PTokensTest.class);
    }

    public void testAlternative() {
        pobs.PDirective dir = new pobs.PDirective();
        pobs.PObject parser = new pobs.parser.PTokens("abc", "abcd", "ab");
        pobs.PScanner iter = new pobs.scanner.PStringScanner("abcd");

        dir.setAlternatives(pobs.PDirective.FIRST);
        assertMatch(
            "First",
            parser.process(iter, 0, new pobs.PContext(dir)),
            3);

        dir.setAlternatives(pobs.PDirective.LONGEST);
        assertMatch(
            "Longest",
            parser.process(iter, 0, new pobs.PContext(dir)),
            4);

        dir.setAlternatives(pobs.PDirective.SHORTEST);
        assertMatch(
            "Shortest",
            parser.process(iter, 0, new pobs.PContext(dir)),
            2);
    }

    public void testToken() {
        pobs.PObject parser = new pobs.parser.PTokens("yodel", "hiti");

		assertMismatch("Zero-length input", parse(parser, ""), 0);
		assertMatch("First token", parse(parser, "yodelhiti"), 5);
        assertMatch("Second token", parse(parser, "hitiho"), 4);
        assertMismatch("No token", parse(parser, "yo, dude"), 2);
        assertMatch("Non-zero begin", parse(parser, "yoyodelho", 2), 5);
    }
    
    public void testFailLength() {
        pobs.PObject parser = new pobs.parser.PTokens("aaaz", "aaz");

        assertMismatch("Mismatch longest", parse(parser, "aaaaaa"), 3);
    }
}
