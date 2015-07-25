/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package test;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author	Martijn W. van der Lee
 */
public class AllTests {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(AllTests.class);
    }

    public static Test suite() {
        TestSuite suite = new TestSuite("Test for Parser OBjectS");
        //$JUnit-BEGIN$
        suite.addTestSuite(PDisableSkipTest.class);
        suite.addTestSuite(POBSTest.class);
        suite.addTestSuite(PRangeTest.class);
        suite.addTestSuite(PDirectiveTest.class);
        suite.addTestSuite(POrTest.class);
        suite.addTestSuite(PFirstAlternativeTest.class);
        suite.addTestSuite(PSequenceTest.class);
        suite.addTestSuite(PEOLTest.class);
        suite.addTestSuite(PShortestAlternativeTest.class);
        suite.addTestSuite(PDisableActionsTest.class);
        suite.addTestSuite(PTextScannerTest.class);
        suite.addTestSuite(PLimitTest.class);
        suite.addTestSuite(PReverseScannerTest.class);
        suite.addTestSuite(PEndTest.class);
        suite.addTestSuite(PTokensTest.class);
        suite.addTestSuite(PMultipleTest.class);
        suite.addTestSuite(PEnableActionsTest.class);
        suite.addTestSuite(PDisableCaseTest.class);
        suite.addTestSuite(PObjectPointerTest.class);
        suite.addTestSuite(PParserPointerTest.class);
        suite.addTestSuite(PKleeneTest.class);
        suite.addTestSuite(PUnicodeBlockTest.class);
        suite.addTestSuite(PCharTest.class);
        suite.addTestSuite(PEnableCaseTest.class);
        suite.addTestSuite(PRepeatTest.class);
        suite.addTestSuite(PUnicodeTest.class);
        suite.addTestSuite(PEnableSkipTest.class);
        suite.addTestSuite(POptionalTest.class);
        suite.addTestSuite(PAnyTest.class);
        suite.addTestSuite(PLongestAlternativeTest.class);
        suite.addTestSuite(PSetTest.class);
        suite.addTestSuite(PLetterTest.class);
        suite.addTestSuite(PWhitespaceTest.class);
        suite.addTestSuite(PDigitTest.class);
        suite.addTestSuite(PListTest.class);
        suite.addTestSuite(PTokenTest.class);
        suite.addTestSuite(PExceptTest.class);
        suite.addTestSuite(PStringTest.class);
        //$JUnit-END$
        return suite;
    }
}