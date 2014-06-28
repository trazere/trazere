package com.trazere.core.lang;

import com.trazere.core.functional.Function;
import com.trazere.core.util.Maybe;

/**
 * The {@link ObjectExtractors} class provides various extractors related to {@link Object}s.
 */
public class ObjectExtractors {
	/**
	 * Builds an extractor that matches objects against the given type.
	 * 
	 * @param <T> Type of the argument values.
	 * @param <R> Type of the result values.
	 * @param <X> Type of the exceptions.
	 * @param type The type.
	 * @return The built extractor.
	 * @see ObjectUtils#match(Object, Class)
	 */
	public static <T, R extends T, X extends Exception> Function<T, Maybe<R>> match(final Class<R> type) {
		assert null != type;
		
		return new Function<T, Maybe<R>>() {
			@Override
			public Maybe<R> evaluate(final Object object) {
				return ObjectUtils.match(object, type);
			}
		};
	}
	
	private ObjectExtractors() {
		// Prevents instantiation.
	}
}
