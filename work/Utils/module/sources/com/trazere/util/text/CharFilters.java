package com.trazere.util.text;

/**
 * The <code>CharFilters</code> class provides various standard character filters.
 */
public class CharFilters {
	/**
	 * Filter accepting the digit characters.
	 * 
	 * @see Character#isDigit(char)
	 */
	public static final CharFilter DIGIT = new CharFilter() {
		public boolean filter(final char c) {
			return Character.isDigit(c);
		}
	};

	/**
	 * Filter accepting the letter characters.
	 * 
	 * @see Character#isLetter(char)
	 */
	public static final CharFilter LETTER = new CharFilter() {
		public boolean filter(final char c) {
			return Character.isLetter(c);
		}
	};

	/**
	 * Filter accepting the alphanumeric characters.
	 * 
	 * @see Character#isLetterOrDigit(char)
	 */
	public static final CharFilter ALPHANUMERIC = new CharFilter() {
		public boolean filter(final char c) {
			return Character.isLetterOrDigit(c) || '_' == c;
		}
	};

	private CharFilters() {
		// Prevent instanciation.
	}
}
