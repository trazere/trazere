package com.trazere.core.lang;

import com.trazere.core.functional.Predicate;

/**
 * The {@link ObjectPredicates} class provides various predicates related to {@link Object}s.
 */
public class ObjectPredicates {
	/**
	 * Builds a predicate that test the membership of the given type.
	 * 
	 * @param <T> Type of the values.
	 * @param type Type to test against.
	 * @return The built predicate.
	 */
	public static <T> Predicate<T> isInstanceOf(final Class<? extends T> type) {
		assert null != type;
		
		return new Predicate<T>() {
			@Override
			public boolean evaluate(final T object) {
				return null != object && type.isInstance(object);
			}
		};
	}
	
	private ObjectPredicates() {
		// Prevents instantiation.
	}
}
