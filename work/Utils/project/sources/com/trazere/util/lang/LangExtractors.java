package com.trazere.util.lang;

import com.trazere.util.function.Extractors;
import com.trazere.util.function.Function1;
import com.trazere.util.type.Maybe;

/**
 * The {@link Extractors} class provides various factories of extractors related to the language.
 * <p>
 * An extractor is function that combines a map and a filter operation.
 * 
 * @see Function1
 */
public class LangExtractors {
	/**
	 * Builds an extractor that matches the values according to the given type.
	 * 
	 * @param <T> Type of the argument values.
	 * @param <R> Type of the result values.
	 * @param <X> Type of the exceptions.
	 * @param type The type.
	 * @return The built extractor.
	 * @see LangUtils#match(Object, Class)
	 */
	public static <T, R extends T, X extends Exception> Function1<T, Maybe<R>, X> match(final Class<R> type) {
		assert null != type;
		
		return new Function1<T, Maybe<R>, X>() {
			@Override
			public Maybe<R> evaluate(final Object value) {
				return LangUtils.match(value, type);
			}
		};
	}
	
	private LangExtractors() {
		// Prevents instantiation.
	}
}
