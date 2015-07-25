/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package test;

/**
 * @author	Martijn W. van der Lee 
 */
public class POrTest extends TestCaseParser {
    java.lang.String log;

    class Action implements pobs.PAction {
        java.lang.String string;
        public void perform(pobs.PTarget target, java.lang.String string) {
            log += string;
        }
    }

    public POrTest(String arg0) {
        super(arg0);
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(POrTest.class);
    }

    public void testAlternative() {
        pobs.PDirective dir = new pobs.PDirective();
        pobs.PObject parser =
            new pobs.parser.POr(
                new pobs.parser.PToken("abc"),
                new pobs.parser.PToken("abcd"),
                new pobs.parser.PToken("ab"));
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
        pobs.PObject parser =
            new pobs.parser.POr(
                new pobs.parser.PToken("yodel"),
                new pobs.parser.PToken("hiti"));

        assertMatch("First alternative", parse(parser, "yodelhiti"), 5);
        assertMatch("Second alternative", parse(parser, "hitiho"), 4);
        assertMismatch("No alternative", parse(parser, "yo, dude!"), 2);
        assertMatch("Non-zero beginning", parse(parser, "yoyodelho", 2), 5);
    }

    public void testAlternativesActions() {
        pobs.PObject parser =
            new pobs.parser.POr(
                new pobs.parser.PToken("x").setMatchAction(new Action()),
                new pobs.parser.PToken("xx").setMatchAction(new Action()));

        log = "";
        assertMatch("Actions", parse(parser, "xxx"), 1);
        assertTrue("Actions, log", log.equals("x"));
    }

    public void testAlternativesActionsForward() {
        pobs.PObject parser =
            new pobs.parser.POr(
                new pobs.parser.PToken("xx").setMatchAction(new Action()),
                new pobs.parser.PToken("x").setMatchAction(new Action()));

        log = "";
        assertMatch("Actions", parse(parser, "xxx"), 2);
        assertTrue("Actions, log", log.equals("xx"));
    }
}
