package com.trazere.util.text;

/**
 * The <code>CharFilter</code> interface defines character filters.
 */
public interface CharFilter {
	/**
	 * Filter the given character.
	 * 
	 * @param c Character to filter.
	 * @return <code>true</code> is the character is accepted, <code>false</code> otherwise.
	 */
	public boolean filter(final char c);
}
