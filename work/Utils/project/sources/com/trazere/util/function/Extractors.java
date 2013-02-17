/*
 *  Copyright 2006-2012 Julien Dufour
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.trazere.util.function;

import com.trazere.util.lang.LangUtils;
import com.trazere.util.type.Maybe;

/**
 * The {@link Extractors} class provides various extractors.
 * <p>
 * An extractor is function which combine a map and a filter operation.
 */
public class Extractors {
	/**
	 * Builds an identity extractor.
	 * 
	 * @param <T> Type of the values.
	 * @param <X> Type of the exceptions.
	 * @return The built extractor.
	 */
	@SuppressWarnings("unchecked")
	public static <T, X extends Exception> Function1<T, Maybe<T>, X> identity() {
		return (Function1<T, Maybe<T>, X>) _IDENTITY;
	}
	
	private static final Function1<?, ?, ?> _IDENTITY = new Function1<Object, Object, RuntimeException>() {
		@Override
		public Object evaluate(final Object value) {
			return Maybe.some(value);
		}
	};
	
	/**
	 * Builds an extractor corresponding to the composition of the given extractors (g . f).
	 * 
	 * @param <T1> Type of the argument values of the inner extractor.
	 * @param <T2> Type of the argument values of the outer extractor.
	 * @param <T3> Type of the result values.
	 * @param <X> Type of the exceptions.
	 * @param g The outer extractor.
	 * @param f The inned extractor.
	 * @return The built extractor.
	 */
	public static <T1, T2, T3, X extends Exception> Function1<T1, Maybe<T3>, X> compose(final Function1<? super T2, ? extends Maybe<? extends T3>, ? extends X> g, final Function1<? super T1, ? extends Maybe<? extends T2>, X> f) {
		return Functions.compose(Maybe.mapFilterFunction(g), f);
	}
	
	/**
	 * Builds an extractor which matches the values according to the given type.
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
	
	/**
	 * Builds an extractor which filters the values according to the given filter predicate.
	 * 
	 * @param <T> Type of the argument values.
	 * @param <X> Type of the result values.
	 * @param filter The filter.
	 * @return The built extractor.
	 */
	public static <T, X extends Exception> Function1<T, Maybe<T>, X> predicate(final Predicate1<? super T, ? extends X> filter) {
		assert null != filter;
		
		return new Function1<T, Maybe<T>, X>() {
			@Override
			public Maybe<T> evaluate(final T value)
			throws X {
				return filter.evaluate(value) ? Maybe.some(value) : Maybe.<T>none();
			}
		};
	}
	
	private Extractors() {
		// Prevents instantiation.
	}
}
