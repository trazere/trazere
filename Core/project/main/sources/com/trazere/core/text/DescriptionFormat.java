package com.trazere.core.text;

/**
 * The {@link DescriptionFormat} interfaces defines formats for building descriptions.
 * 
 * @see DescriptionBuilder
 * @since 2.0
 */
public interface DescriptionFormat {
	/**
	 * Gets the opening part of the description.
	 * 
	 * @return The opening part.
	 * @since 2.0
	 */
	CharSequence getOpening();
	
	/**
	 * Gets the closing part of the description.
	 * 
	 * @return The closing part.
	 * @since 2.0
	 */
	CharSequence getClosing();
	
	/**
	 * Gets the delimiter part to insert between the properties of the description.
	 * 
	 * @return The delimiter part.
	 * @since 2.0
	 */
	CharSequence getDelimiter();
	
	/**
	 * Gets the assignment part to insert between the name and the value of the properties of the description.
	 * 
	 * @return The assignment part.
	 * @since 2.0
	 */
	CharSequence getAssigment();
}
