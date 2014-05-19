package com.trazere.util.lang;

import com.trazere.util.function.Predicate1;

/**
 * The {@link LangPredicates} class provides various factories of predicates related to the language.
 */
public class LangPredicates {
	/**
	 * Builds a predicate that test the membership for the given type.
	 * 
	 * @param <T> Type of the argument values.
	 * @param <X> Type of the exceptions.
	 * @param type The type to match.
	 * @return The built predicate.
	 */
	public static <T, X extends Exception> Predicate1<T, X> isInstanceOf(final Class<? extends T> type) {
		assert null != type;
		
		return new Predicate1<T, X>() {
			@Override
			public boolean evaluate(final T value) {
				return null != value && type.isInstance(value);
			}
		};
	}
	
	private LangPredicates() {
		// Prevents instantiation.
	}
}
