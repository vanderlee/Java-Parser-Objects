/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package test;

import junit.framework.TestCase;

/**
 * @author	Martijn W. van der Lee 
 */
public class PDirectiveTest extends TestCase {
    public PDirectiveTest(String arg0) {
        super(arg0);
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(PDirectiveTest.class);
    }

    public void testAccess() {
        pobs.PDirective d = new pobs.PDirective();

        assertTrue("Default actions", d.isActions() == true);
        d.setActions(false);
        assertTrue("Set actions", d.isActions() == false);

        assertTrue(
            "Default alternatives",
            d.getAlternatives() == pobs.PDirective.FIRST);
        d.setAlternatives(pobs.PDirective.LONGEST);
        assertTrue(
            "Set alternatives",
            d.getAlternatives() == pobs.PDirective.LONGEST);

        assertTrue("Default caseSensitive", d.isCaseSensitive() == true);
        d.setCaseSensitive(false);
        assertTrue("Set caseSensitive", d.isCaseSensitive() == false);

        assertTrue("Default skipper", d.getSkipper() == null);
        pobs.PObject skipper = new pobs.parser.PWhitespace();
        d.setSkipper(skipper);
        assertTrue("Set skipper", d.getSkipper() == skipper);
        assertTrue("Set skipper, skip still false", d.isSkip() == false);

        assertTrue("Default skip", d.isSkip() == false);
        d.setSkip(true);
        assertTrue("Set skip", d.isSkip() == true);
        assertTrue("Set skip, skipper still set", d.getSkipper() == skipper);
        d.setSkip(false);
        assertTrue("Set skip to false", d.isSkip() == false);
        assertTrue(
            "Set skip to false, skipper still set",
            d.getSkipper() == skipper);

        d.setSkip(true);
        assertTrue("Set skip on", d.isSkip() == true);
        d.setSkipper(null);
        assertTrue("Set skipper null", d.getSkipper() == null);
        assertTrue("Skip automatically set off", d.isSkip() == false);
    }

    public void testAlternatives() {
        pobs.PMatch false_short = new pobs.PMatch(false, 0);
        pobs.PMatch false_long = new pobs.PMatch(false, 4);
        pobs.PMatch true_short = new pobs.PMatch(true, 2);
        pobs.PMatch true_long = new pobs.PMatch(true, 6);

        pobs.PDirective d = new pobs.PDirective();

        d.setAlternatives(pobs.PDirective.FIRST);
        assertTrue(
            "First 1",
            d.alternative(false_short, false_long) == false_long);
        assertTrue(
            "First 2",
            d.alternative(false_short, true_long) == true_long);
        assertTrue(
            "First 3",
            d.alternative(true_short, false_long) == true_short);
        assertTrue(
            "First 4",
            d.alternative(true_short, true_long) == true_short);
        assertTrue(
            "First 5",
            d.alternative(false_long, false_short) == false_long);
        assertTrue(
            "First 6",
            d.alternative(false_long, true_short) == true_short);
        assertTrue(
            "First 7",
            d.alternative(true_long, false_short) == true_long);
        assertTrue(
            "First 8",
            d.alternative(true_long, true_short) == true_long);

        d.setAlternatives(pobs.PDirective.LONGEST);
        assertTrue(
            "Longest 1",
            d.alternative(false_short, false_long) == false_long);
        assertTrue(
            "Longest 2",
            d.alternative(false_short, true_long) == true_long);
        assertTrue(
            "Longest 3",
            d.alternative(true_short, false_long) == true_short);
        assertTrue(
            "Longest 4",
            d.alternative(true_short, true_long) == true_long);
        assertTrue(
            "Longest 5",
            d.alternative(false_long, false_short) == false_long);
        assertTrue(
            "Longest 6",
            d.alternative(false_long, true_short) == true_short);
        assertTrue(
            "Longest 7",
            d.alternative(true_long, false_short) == true_long);
        assertTrue(
            "Longest 8",
            d.alternative(true_long, true_short) == true_long);

        d.setAlternatives(pobs.PDirective.SHORTEST);
        assertTrue(
            "Shortest 1",
            d.alternative(false_short, false_long) == false_long);
        assertTrue(
            "Shortest 2",
            d.alternative(false_short, true_long) == true_long);
        assertTrue(
            "Shortest 3",
            d.alternative(true_short, false_long) == true_short);
        assertTrue(
            "Shortest 4",
            d.alternative(true_short, true_long) == true_short);
        assertTrue(
            "Shortest 5",
            d.alternative(false_long, false_short) == false_long);
        assertTrue(
            "Shortest 6",
            d.alternative(false_long, true_short) == true_short);
        assertTrue(
            "Shortest 7",
            d.alternative(true_long, false_short) == true_long);
        assertTrue(
            "Shortest 8",
            d.alternative(true_long, true_short) == true_short);
    }

    public void testConvert() {
        pobs.PDirective d = new pobs.PDirective();

        assertTrue("Default caseSensitive", d.isCaseSensitive() == true);
        assertTrue("Convert sensitive, non-cap.", d.convert('a') == 'a');
        assertTrue("Convert sensitive, capital", d.convert('A') == 'A');
        d.setCaseSensitive(false);
        assertTrue("Convert insensitive, non-cap.", d.convert('a') == 'a');
        assertTrue("Convert insensitive, capital", d.convert('A') == 'a');
    }

    public void testSkip() {
        pobs.PDirective d = new pobs.PDirective();
        pobs.PObject skipper = new pobs.parser.PChar('x');
        pobs.PObject kleene = new pobs.parser.PKleene(skipper);
        pobs.PScanner iter = new pobs.scanner.PStringScanner("xxx");

        assertTrue("Default skipper", d.getSkipper() == null);
        assertTrue("Default skip", d.isSkip() == false);
        assertTrue("Default: no skipping", d.skip(iter, 0) == 0);

        d.setSkipper(skipper);
        assertTrue(
            "Simple skipper, no skip: no skipping",
            d.skip(iter, 0) == 0);

        d.setSkip(true);
        assertTrue("Simple skipper, skip: skip 1", d.skip(iter, 0) == 1);

        d.setSkipper(kleene);
        assertTrue("0-or-more skipper, skip: skip 3", d.skip(iter, 0) == 3);

        d.setSkipper(null);
        assertTrue(
            "no skipper, auto no skip: no skipping",
            d.skip(iter, 0) == 0);
    }
}
