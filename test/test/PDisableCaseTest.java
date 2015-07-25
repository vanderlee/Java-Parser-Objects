/*
 * Created on 23.07.2004
 */
package test;

import java.util.Vector;

import pobs.PContext;
import pobs.PMatch;
import pobs.PParser;
import pobs.PScanner;
import pobs.control.PDisableCase;
import pobs.control.PEnableCase;
import pobs.parser.PSequence;
import pobs.parser.PSet;
import pobs.parser.PWrapper;
import pobs.scanner.PStringScanner;

/**
 * @author Franz-Josef Elmer
 */
public class PDisableCaseTest extends PEnableCaseTest {
	static class MockProxyParser extends PParser {
		public Vector flagRecorder = new Vector();
		private final PParser parser;
		public MockProxyParser(PParser parser) {
			this.parser = parser;
		}
		protected PMatch parse(PScanner input, long begin, PContext context) {
			flagRecorder.addElement(
					new Boolean(context.getDirective().isCaseSensitive()));
			return parser.process(input, begin, context);
		}
	}
	public void testDisableADisabledDirective() {
		boolean before = false;
		boolean mockState = false;
		check(before, mockState);
	}

	public void testDisableAnEnabledDirective() {
		boolean before = true;
		boolean mockState = false;
		check(before, mockState);
	}

	private void check(boolean stateBefore, boolean expectedStateInMock) {
		MockParser mock = new MockParser();
		PParser parser = mock.addControl(new PDisableCase());
		check(stateBefore, expectedStateInMock, mock, parser);
	}

	public void testNestedDisableSettings() {
		PParser lowerA = new PSet("a");
		PParser anyA = new PWrapper(lowerA).addControl(new PDisableCase());
		PParser sequence = new PSequence(lowerA, anyA, lowerA);
		PParser anySequence = new PWrapper(sequence).addControl(new PDisableCase());
		assertEquals(true, anySequence.process(new PStringScanner("AAA"), 0, 
				                               new PContext()).isMatch());
	}

	public void testNestedDisableSettingsWithFlagRecording() {
		MockProxyParser lowerA = new MockProxyParser(new PSet("a"));
		PParser anyA = new PWrapper(lowerA).addControl(new PDisableCase());
		PParser sequence = new PSequence(lowerA, anyA, lowerA);
		PParser anySequence = new PWrapper(sequence).addControl(new PDisableCase());

		assertEquals(true, anySequence.process(new PStringScanner("AAA"), 0, 
				                               new PContext()).isMatch());
		assertEquals("[false, false, false]", lowerA.flagRecorder.toString());
	}


	public void testNestedMixedSettingsWithFlagRecording() {
		MockProxyParser lowerA = new MockProxyParser(new PSet("a"));
		PParser lowerA2 = new PWrapper(lowerA).addControl(new PEnableCase());
		PParser sequence 
				= new PSequence(lowerA, lowerA2, lowerA).addControl(new PDisableCase());
		PParser sequence2 
				= new PSequence(lowerA2, lowerA, sequence).addControl(new PEnableCase());

		assertEquals(true, sequence2.process(new PStringScanner("aaAaA"), 0, 
				                               new PContext()).isMatch());
		assertEquals("[true, true, false, true, false]", 
				     lowerA.flagRecorder.toString());
	}

}
