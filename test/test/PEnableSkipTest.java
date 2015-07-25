/*
 * Created on 23.07.2004
 */
package test;

import junit.framework.TestCase;
import pobs.PContext;
import pobs.PMatch;
import pobs.PParser;
import pobs.PScanner;
import pobs.control.PEnableSkip;
import pobs.scanner.PStringScanner;

/**
 * @author Franz-Josef Elmer
 */
public class PEnableSkipTest extends TestCase {
	static class MockParser extends PParser {
		public boolean skip;
		protected PMatch parse(PScanner input, long begin, PContext context) {
			skip = context.getDirective().isSkip();
			return new PMatch(true, 1);
		}
	}
	
	public void testEnableADisabledDirective() {
		boolean before = false;
		boolean mockState = true;
		check(before, mockState);
	}

	public void testEnableAnEnabledDirective() {
		boolean before = true;
		boolean mockState = true;
		check(before, mockState);
	}

	private void check(boolean stateBefore, boolean expectedStateInMock) {
		MockParser mock = new MockParser();
		PParser parser = mock.addControl(new PEnableSkip());
		check(stateBefore, expectedStateInMock, mock, parser);
	}

	/**
	 * @param stateBefore			Skipping state before possible modification 
	 * 								and expected state after modification.
	 * @param expectedStateInMock	Expected skipping state during modification. 
	 * @param mock					Mock parser.
	 * @param parser				Disabled/Enable wrapper around mock parser.
	 */
	protected void check(boolean stateBefore, boolean expectedStateInMock, 
						 MockParser mock, PParser parser) {
		PStringScanner input = new PStringScanner("a");
		PContext context = new PContext();
		
		context.getDirective().setSkip(stateBefore);
		parser.process(input, 0, context);
		
		assertEquals("mock", expectedStateInMock, mock.skip);
		assertEquals("directive", stateBefore, context.getDirective().isSkip());
	}

}
