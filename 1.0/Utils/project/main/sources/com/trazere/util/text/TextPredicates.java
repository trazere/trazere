package com.trazere.util.text;

import com.trazere.util.function.Predicate1;

/**
 * The {@link TextPredicates} class provides various factories of predicates related to text.
 * 
 * @see Predicate1
 * @deprecated Use core.
 */
@Deprecated
public class TextPredicates {
	/**
	 * Builds a predicate that evaluates to <code>true</code> for empty strings.
	 * 
	 * @param <X> Type of the exceptions.
	 * @return The built predicate.
	 * @deprecated Use {@link com.trazere.core.text.TextPredicates#isEmpty()}.
	 */
	@Deprecated
	@SuppressWarnings("unchecked")
	public static <X extends Exception> Predicate1<String, X> isEmptyString() {
		return (Predicate1<String, X>) _IS_EMPTY_STRING;
	}
	
	private static Predicate1<String, ?> _IS_EMPTY_STRING = new Predicate1<String, RuntimeException>() {
		@Override
		public boolean evaluate(final String value) {
			assert null != value;
			
			return 0 == value.length();
		}
	};
	
	private TextPredicates() {
		// Prevents instantiation.
	}
}
