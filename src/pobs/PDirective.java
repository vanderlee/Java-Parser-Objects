/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package pobs;

/**
 * Maintains all state information for the POBS framework such as parser
 * policies. Directive also provides a number of support methods which helps
 * parsers implement the directives, it is ultimately up to the implementor of a
 * parser to handle the directives, this class cannot force parsers to abide by
 * the directives. All parsers supplied with POBS obey these directives fully.
 * 
 * @author Martijn W. van der Lee
 */
public class PDirective {
	private static final class Alternatives {}
	
	public static final Alternatives FIRST = new Alternatives();

	public static final Alternatives LONGEST = new Alternatives();

	public static final Alternatives SHORTEST = new Alternatives();

	private boolean actions = true;

	private Alternatives alternatives = FIRST;

	private boolean caseSensitive = true;

	private boolean skip = false;

	private pobs.PObject skipper;

	/**
	 * Empty constructor with all set to default.
	 */
	public PDirective() {
		super();
	}

	/**
	 * Construct a new object with a specified skipper. Enables skipping by
	 * default.
	 * 
	 * @param skipper
	 *            a {@link PObject}which specifies the input to skip.
	 */
	public PDirective(pobs.PObject skipper) {
		super();

		this.skipper = skipper;
		this.skip = true;
	}

	/**
	 * Determines the match of two matches based upon the alternatives
	 * directive.
	 * 
	 * @param a
	 *            the first alternative (leftmost in parser)
	 * @param b
	 *            the second alternative (rightmost in parser)
	 */
	public PMatch alternative(PMatch a, PMatch b) {
		if (alternatives == SHORTEST) {
			if (!b.isMatch() && a.getLength() > b.getLength() || a.isMatch()
					&& a.getLength() <= b.getLength()) {
				return a;
			}
		} else if (alternatives == LONGEST) {
			if ((a.isMatch() || !b.isMatch()) && a.getLength() >= b.getLength()
					|| a.isMatch() && !b.isMatch()) {
				return a;
			}
		} else {
			if (!b.isMatch() && a.getLength() >= b.getLength() || a.isMatch()) {
				return a;
			}
		}

		return b;
	}

	/**
	 * Converts a character based on whether the directive for case sensitivity
	 * is set. This method is ment for parser implementors only, to easy the
	 * support for the case sensitivity directive. An implementor just needs to
	 * pass every single character to this method and will hereby automatically
	 * gain support for this directive. If an implementor wishes to parse by any
	 * means other than character-by-character comparison, it is left to the
	 * implementor to handle case sensitivity. Note however that if you want to
	 * return the lengths of failed parses (which is highly recommended), a
	 * character-by-character comparison is likely the best way to do so.
	 * 
	 * @param character
	 *            the char to convert
	 * @return the converted character
	 */
	public char convert(char character) {
		if (!this.caseSensitive) {
			return java.lang.Character.toLowerCase(character);
		}

		return character;
	}

	/**
	 * gets the alternatives mode used in this directive.
	 * 
	 * @return the alternatives mode of this directive.
	 */
	public Alternatives getAlternatives() {
		return alternatives;
	}

	/**
	 * gets the parser used in this directive for skipping. Please note that
	 * this method may return a null value if no skipper is specified.
	 * 
	 * @return the skipper parser of this directive.
	 * @see pobs.PObject
	 */
	public PObject getSkipper() {
		return skipper;
	}

	/**
	 * Checks whether actions are enabled for this PContext.
	 * 
	 * @return indication of actions
	 */
	public boolean isActions() {
		return actions;
	}

	/**
	 * checks whether case sensitivity is enabled for this PContext.
	 * 
	 * @return indication of case sensitivity
	 */
	public boolean isCaseSensitive() {
		return caseSensitive;
	}

	/**
	 * Checks whether skipping is enabled for this PContext.
	 * 
	 * @return indication of skipping
	 */
	public boolean isSkip() {
		return skip;
	}

	/**
	 * Enables or disables actions for this PContext.
	 * 
	 * @param actions
	 *            true to enable actions.
	 */
	public void setActions(boolean actions) {
		this.actions = actions;
	}

	/**
	 * Sets the alternatives mode for this PContext.
	 * 
	 * @param alternatives
	 *            either FIRST, LONGEST or SHORTEST alternatives.
	 */
	public void setAlternatives(Object alternatives) {
		if (alternatives == PDirective.LONGEST
				|| alternatives == PDirective.SHORTEST)
			this.alternatives = (Alternatives) alternatives;
		else
			this.alternatives = PDirective.FIRST;

	}

	/**
	 * Enables or disables case sensitivity for this PContext.
	 * 
	 * @param caseSensitive
	 *            true to enable case sensitivity
	 */
	public void setCaseSensitive(boolean caseSensitive) {
		this.caseSensitive = caseSensitive;
	}

	/**
	 * Enables or disables skipping for this PContext.
	 * 
	 * @param skip
	 *            true to enable skipping
	 */
	public void setSkip(boolean skip) {
		this.skip = skip;
	}

	/**
	 * Sets the {@link pobs.PObject parser}to use for skipping. This does not
	 * enable skipping but merely specifies which skipper to use when skipping
	 * is enabled.
	 * 
	 * @param skipper
	 *            a parser to use for skipping
	 */
	public void setSkipper(pobs.PObject skipper) {
		this.skipper = skipper;

		if (this.skipper == null) {
			this.skip = false;
		}
	}

	/**
	 * Support method for skipping. This method is to be used by parser
	 * implementors to more easily use the skipping directive. The parser
	 * implementor must pass the input {@link pobs.PScanner iterator}and begin
	 * position then add the returned length to it's own internal position
	 * variable, there is no need for the parser implementer to check any of the
	 * directives, this method will always return the expected result. Contrary
	 * to instinct, skipping is <b>not </b> commonly implemented in terminal
	 * parsers. Since skipping occurs <i>between </i> terminals, it needs only
	 * to be implemented in those parsers which can parse sequences of (possibly
	 * terminal) parsers such as {@link pobs.parser.PSequence PSequence}and
	 * {@link pobs.parser.PRepeat PRepeat}.
	 * 
	 * @param input
	 *            iterator in which to skip
	 * @param begin
	 *            location from which to start skipping
	 * @return length of the skipped input area
	 */
	public long skip(pobs.PScanner input, long begin) {
		/*
		 * ???: Should we allow a default skipper policy? Could it itself
		 * contain another skipper?
		 */

		if (this.skip) {
			if (this.skipper == null) {
				throw new IllegalStateException("Undefined skipper.");
			}

			pobs.PMatch match = this.skipper.process(input, begin,
					new pobs.PContext());
			if (match.isMatch())
				return match.getLength();
		}

		return 0;
	}

}