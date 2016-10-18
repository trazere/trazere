/*
 *  Copyright 2006-2015 Julien Dufour
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

import com.trazere.util.collection.CollectionUtils;
import com.trazere.util.type.Maybe;
import java.util.Map;

/**
 * The {@link Extractors} class provides various factories of extractors.
 * <p>
 * An extractor is function that combines a map and a filter operation.
 * 
 * @see Function1
 * @deprecated Use {@link com.trazere.core}.
 */
@Deprecated
public class Extractors {
	/**
	 * Builds an identity extractor.
	 * 
	 * @param <T> Type of the values.
	 * @param <X> Type of the exceptions.
	 * @return The built extractor.
	 * @deprecated Use {@link com.trazere.core.functional.Extractors#identity()}.
	 */
	@Deprecated
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
	 * @deprecated Use {@link com.trazere.core.functional.ExtractorUtils#compose(com.trazere.core.functional.Function, com.trazere.core.functional.Function)}.
	 */
	@Deprecated
	public static <T1, T2, T3, X extends Exception> Function1<T1, Maybe<T3>, X> compose(final Function1<? super T2, ? extends Maybe<? extends T3>, ? extends X> g, final Function1<? super T1, ? extends Maybe<? extends T2>, ? extends X> f) {
		return Functions.compose(Maybe.extractFunction(g), f);
	}
	
	// TODO: add filter(Predicate1, Extractor): Extractor
	// TODO: add map(Function1, Extractor): Extractor
	
	/**
	 * Builds an identity extractor that filters the values according to the given filter predicate.
	 * 
	 * @param <T> Type of the argument values.
	 * @param <X> Type of the exceptions.
	 * @param filter The filter.
	 * @return The built extractor.
	 * @deprecated Use {@link com.trazere.core.functional.Extractors#fromPredicate(com.trazere.core.functional.Predicate)}.
	 */
	@Deprecated
	public static <T, X extends Exception> Function1<T, Maybe<T>, X> fromPredicate(final Predicate1<? super T, ? extends X> filter) {
		assert null != filter;
		
		return new Function1<T, Maybe<T>, X>() {
			@Override
			public Maybe<T> evaluate(final T value)
			throws X {
				return filter.evaluate(value) ? Maybe.some(value) : Maybe.<T>none();
			}
		};
	}
	
	/**
	 * Builds an extractor from the given function.
	 * 
	 * @param <V> Type of the argument values.
	 * @param <R> Type of the result values.
	 * @param <X> Type of the exceptions.
	 * @param function The function.
	 * @return The built extractor.
	 * @deprecated Use {@link com.trazere.core.functional.Extractors#fromFunction(com.trazere.core.functional.Function)}.
	 */
	@Deprecated
	public static <V, R, X extends Exception> Function1<V, Maybe<R>, X> fromFunction(final Function1<? super V, ? extends R, ? extends X> function) {
		assert null != function;
		
		return new Function1<V, Maybe<R>, X>() {
			@Override
			public Maybe<R> evaluate(final V value)
			throws X {
				return Maybe.<R>some(function.evaluate(value));
			}
		};
	}
	
	/**
	 * Builds an extractor corresponding to the given map.
	 * <p>
	 * The built function evaluates to the values associated to the keys in the map wrapped into a {@link Maybe maybe} instance to reflect the domain of the
	 * map.
	 * 
	 * @param <K> Type of the argument (the keys of the map).
	 * @param <V> Type of the results (the values values).
	 * @param <X> Type of the exceptions.
	 * @param map The map.
	 * @return The built function.
	 * @deprecated Use {@link com.trazere.core.collection.MapExtractors#optionalGet(Map)}.
	 */
	@Deprecated
	public static <K, V, X extends Exception> Function1<K, Maybe<V>, X> fromMap(final Map<? super K, ? extends V> map) {
		assert null != map;
		
		return new Function1<K, Maybe<V>, X>() {
			@Override
			public Maybe<V> evaluate(final K key) {
				return CollectionUtils.get(map, key);
			}
		};
	}
	
	private Extractors() {
		// Prevents instantiation.
	}
}
