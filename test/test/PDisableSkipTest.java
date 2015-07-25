/*
 * Created on 23.07.2004
 */
package test;

import pobs.PParser;
import pobs.control.PDisableSkip;


/**
 * @author Franz-Josef Elmer
 */
public class PDisableSkipTest extends PEnableSkipTest {

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
		PParser parser = mock.addControl(new PDisableSkip());
		check(stateBefore, expectedStateInMock, mock, parser);
	}
}
