/*
 * Created on 23.07.2004
 */
package test;

import junit.framework.TestCase;
import pobs.PContext;
import pobs.PMatch;
import pobs.PParser;
import pobs.PScanner;
import pobs.control.PLongestAlternative;
import pobs.scanner.PStringScanner;

/**
 * @author Franz-Josef Elmer
 * @author Martijn W. van der Lee
 */
public class PLongestAlternativeTest extends TestCase {
	static class MockParser extends PParser {
		public Object result;

		protected PMatch parse(PScanner input, long begin, PContext context) {
			result = context.getDirective().getAlternatives();
			return new PMatch(true, 1);
		}
	}

	public void testSettingFirstToLongestDirective() {
		Object before = pobs.PDirective.FIRST;
		Object mockState = pobs.PDirective.LONGEST;
		check(before, mockState);
	}

	public void testSettingShortestToLongestDirective() {
		Object before = pobs.PDirective.SHORTEST;
		Object mockState = pobs.PDirective.LONGEST;
		check(before, mockState);
	}

	public void testSettingLongestToLongestDirective() {
		Object before = pobs.PDirective.LONGEST;
		Object mockState = pobs.PDirective.LONGEST;
		check(before, mockState);
	}

	private void check(Object stateBefore, Object expectedStateInMock) {
		MockParser mock = new MockParser();
		PParser parser = mock.addControl(new PLongestAlternative());
		check(stateBefore, expectedStateInMock, mock, parser);
	}

	/**
	 * @param stateBefore
	 *            Case state before possible modification and expected state
	 *            after modification.
	 * @param expectedStateInMock
	 *            Expected case state during modification.
	 * @param mock
	 *            Mock parser.
	 * @param parser
	 *            Disabled/Enable wrapper around mock parser.
	 */
	protected void check(Object stateBefore, Object expectedStateInMock,
			MockParser mock, PParser parser) {
		PStringScanner input = new PStringScanner("a");
		PContext context = new PContext();

		context.getDirective().setAlternatives(stateBefore);
		parser.process(input, 0, context);

		assertEquals("mock", expectedStateInMock, mock.result);
		assertEquals("directive", stateBefore, context.getDirective()
				.getAlternatives());
	}

}