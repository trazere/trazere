package com.trazere.core.text;

/**
 * The {@link DescriptionFormats} class provides various factories of {@link DescriptionFormat description formats}.
 * 
 * @since 1.0
 */
public class DescriptionFormats {
	/**
	 * Basic description format.
	 * 
	 * @since 1.0
	 */
	public static final DescriptionFormat BASIC = simple("[", "]", " - ", " = ");
	
	/**
	 * Builds a simple description format.
	 * 
	 * @param opening Opening part of the description.
	 * @param closing Closing part of the description.
	 * @param delimiter Delimiter part of the description.
	 * @param assignment Assigment part of the description.
	 * @return The built format.
	 * @since 1.0
	 */
	public static DescriptionFormat simple(final CharSequence opening, final CharSequence closing, final CharSequence delimiter, final CharSequence assignment) {
		assert null != opening;
		assert null != closing;
		assert null != delimiter;
		assert null != assignment;
		
		return new DescriptionFormat() {
			@Override
			public CharSequence getOpening() {
				return opening;
			}
			
			@Override
			public CharSequence getClosing() {
				return closing;
			}
			
			@Override
			public CharSequence getDelimiter() {
				return delimiter;
			}
			
			@Override
			public CharSequence getAssigment() {
				return assignment;
			}
		};
	}
	
	private DescriptionFormats() {
		// Prevent instantiation.
	}
}
