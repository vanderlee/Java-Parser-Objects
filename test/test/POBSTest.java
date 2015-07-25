/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package test;

import java.math.BigInteger;

import junit.framework.TestCase;
import pobs.PAction;
import pobs.PContext;
import pobs.PDirective;
import pobs.PMatch;
import pobs.PParser;
import pobs.PTarget;
import pobs.scanner.PStringScanner;
import pobs.utility.POBS;

/**
 * @author Franz-Josef Elmer
 */
public class POBSTest extends TestCase {
	private static class MockAction implements PAction {
		public void perform(PTarget target, String data) {
			if (target instanceof MockTarget) {
				((MockTarget) target).data = data;
			}
		}
	}
	private static class MockTarget implements PTarget {
		String data;		
	}

	private void checkInvalid(PParser parser, String number) {
		MockTarget target = new MockTarget();
		PDirective directive = new PDirective();
		PContext context = new PContext(directive, target, null);
		PMatch result = parser.process(new PStringScanner(number), 0, context);
		if (result.isMatch()) {
			assertTrue(number.length() > result.getLength());
		}
	}

	private PDirective checkValid(PParser parser, String number) {
		MockTarget target = new MockTarget();
		PDirective directive = new PDirective();
		directive.setSkip(true);
		PContext context = new PContext(directive, target, null);
		assertTrue(parser.process(new PStringScanner(number), 0, context).isMatch());
		assertEquals(number, target.data);
		assertTrue(directive.isSkip());
		return directive;
	}
	
	private void checkValidFloat(PParser parser, String number) {
		PDirective directive = checkValid(parser, number);
		assertTrue(directive.isCaseSensitive());
		new Double(number); // throws exception if not a valid double
	}
	
	private void checkValidInt(PParser parser, String number) {
		PDirective directive = checkValid(parser, number);
		assertTrue(directive.isCaseSensitive());
		new BigInteger(number); // throws exception if not a valid number
	}

	public void testFloatNumber() {
		PParser p = POBS.floatNumber();
		PParser p2 = POBS.floatNumber();
		assertNotSame(p, p2);
		
		p.setMatchAction(new MockAction());
		checkValidFloat(p, "1");
		checkValidFloat(p, "12");
		checkValidFloat(p, "-1");
		checkValidFloat(p, "-.1");
		checkValidFloat(p, "+.1");
		checkValidFloat(p, "1.");
		checkValidFloat(p, "4.1");
		checkValidFloat(p, "+1.3");
		checkValidFloat(p, "-3.14159");
		checkValidFloat(p, "-3.14159e5");
		checkValidFloat(p, "9e+5");
		checkValidFloat(p, "9E-5");
		checkValidFloat(p, "9e-05");
		checkValidFloat(p, "1.e+4105");
		checkValidFloat(p, "-.1E+5");
	}
	
	public void testInvalidFloatNumber() {
		PParser p = POBS.floatNumber();
		checkInvalid(p, "");
		checkInvalid(p, ".");
		checkInvalid(p, "+.e7");
		checkInvalid(p, "- 78");
		checkInvalid(p, "e5");
		checkInvalid(p, "1e");
		checkInvalid(p, "1E-");
	}
	
	public void testInvalidSignedInt() {
		PParser p = POBS.signedInt();
		p.setMatchAction(new MockAction());
		checkInvalid(p, "");
		checkInvalid(p, "+1");
		checkInvalid(p, ".42");
		checkInvalid(p, "-");
		checkInvalid(p, "+");
		checkInvalid(p, "42+");
	}
	
	public void testSignedInt() {
		PParser p = POBS.signedInt();
		PParser p2 = POBS.signedInt();
		assertNotSame(p, p2);
		
		p.setMatchAction(new MockAction());
		checkValidInt(p, "1");
		checkValidInt(p, "42");
		checkValidInt(p, "-1");
		checkValidInt(p, "1234567890123456789");
		checkValidInt(p, "12345678901234567891234567890123456789");
	}

}
