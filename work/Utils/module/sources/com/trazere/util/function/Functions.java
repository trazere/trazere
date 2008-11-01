/*
 *  Copyright 2006-2008 Julien Dufour
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

import com.trazere.util.type.Maybe;
import com.trazere.util.type.Tuple2;
import java.util.Map;

/**
 * The {@link Functions} class provides various standard functions.
 * 
 * @see Function
 * @see Function2
 */
public class Functions {
	private static final Function<?, ?, ?> IDENTITY = new Function<Object, Object, RuntimeException>() {
		public Object evaluate(final Object value) {
			return value;
		}
	};
	
	/**
	 * Build an identity function.
	 * 
	 * @param <T> Type of the argument and result values.
	 * @param <X> Type of the exceptions.
	 * @return The built function.
	 */
	@SuppressWarnings("unchecked")
	public static <T, X extends Exception> Function<T, T, X> identity() {
		return (Function<T, T, X>) IDENTITY;
	}
	
	/**
	 * Build a function corresponding to the composition of the given functions (f . g).
	 * 
	 * @param <T1> Type of the argument values of the inner function.
	 * @param <T2> Type of the argument values of the outer function.
	 * @param <T3> Type of the results.
	 * @param <X> Type of the exceptions.
	 * @param f The outer function.
	 * @param g The inner function.
	 * @return The built function.
	 */
	public static <T1, T2, T3, X extends Exception> Function<T1, T3, X> compose(final Function<? super T2, T3, ? extends X> f, final Function<T1, ? extends T2, ? extends X> g) {
		assert null != f;
		assert null != g;
		
		// Build.
		return new Function<T1, T3, X>() {
			public T3 evaluate(final T1 value)
			throws X {
				return f.evaluate(g.evaluate(value));
			}
		};
	}
	
	/**
	 * Build a function which evaluates to the given value for all values.
	 * 
	 * @param <T> Type of the argument values.
	 * @param <R> Type of the result values.
	 * @param <X> Type of the exceptions.
	 * @param result Value value.
	 * @return The built function.
	 */
	public static <T, R, X extends Exception> Function<T, R, X> constant(final R result) {
		return new Function<T, R, X>() {
			public R evaluate(final T value) {
				return result;
			}
		};
	}
	
	/**
	 * Build a function corresponding to the given map.
	 * <p>
	 * The built function evaluates to the values associated to the keys in the map and to <code>null</code> for the other keys.
	 * 
	 * @param <K> Type of the keys of the map (the argument values).
	 * @param <V> Type of the values of the map (the result values).
	 * @param <X> Type of the exceptions.
	 * @param map Map defining the function to build.
	 * @return The built function.
	 */
	public static <K, V, X extends Exception> Function<K, V, X> map(final Map<K, V> map) {
		assert null != map;
		
		// Build the function.
		return new Function<K, V, X>() {
			public V evaluate(final K key) {
				return map.get(key);
			}
		};
	}
	
	/**
	 * Build a function corresponding to the given map.
	 * <p>
	 * The built function evaluates to the values associated to the keys in the map wrapped into a {@link Maybe maybe} instance to reflect the domain of the
	 * map.
	 * 
	 * @param <T> Type of the keys of the map (the argument values).
	 * @param <R> Type of the values of the map (the result values).
	 * @param <X> Type of the exceptions.
	 * @param map Map defining the function to build.
	 * @return The built function.
	 */
	public static <T, R, X extends Exception> Function<T, Maybe<R>, X> maybeMap(final Map<T, R> map) {
		assert null != map;
		
		// Build the function.
		return new Function<T, Maybe<R>, X>() {
			public Maybe<R> evaluate(final T key) {
				if (map.containsKey(key)) {
					return Maybe.some(map.get(key));
				} else {
					return Maybe.none();
				}
			}
		};
	}
	
	private static final Function<?, ?, RuntimeException> SOME = new Function<Object, Maybe<Object>, RuntimeException>() {
		public Maybe<Object> evaluate(final Object value) {
			return Maybe.some(value);
		}
	};
	
	/**
	 * Build a function wrapping the arguments in {@link Maybe.Some} instances.
	 * 
	 * @param <T> Type of the values.
	 * @param <X> Type of the exceptions.
	 * @return The built function.
	 */
	@SuppressWarnings("unchecked")
	public static <T, X extends Exception> Function<T, Maybe<T>, X> some() {
		return (Function<T, Maybe<T>, X>) SOME;
	}
	
	private static final Function<?, ?, RuntimeException> FIRST = new Function<Tuple2<Object, Object>, Object, RuntimeException>() {
		public Object evaluate(final Tuple2<Object, Object> value) {
			return value.getFirst();
		}
	};
	
	/**
	 * Build a function getting the first value of a tuple.
	 * 
	 * @param <T1> Type of the first values of the tuples.
	 * @param <T2> Type of the second values of the tuples.
	 * @param <X> The of the exceptions.
	 * @return The built function.
	 */
	@SuppressWarnings("unchecked")
	public static <T1, T2, X extends Exception> Function<Tuple2<T1, T2>, T1, X> first() {
		return (Function<Tuple2<T1, T2>, T1, X>) FIRST;
	}
	
	private static final Function<?, ?, RuntimeException> SECOND = new Function<Tuple2<Object, Object>, Object, RuntimeException>() {
		public Object evaluate(final Tuple2<Object, Object> value) {
			return value.getSecond();
		}
	};
	
	/**
	 * Build a function getting the second value of a tuple.
	 * 
	 * @param <T1> Type of the first values of the tuples.
	 * @param <T2> Type of the second values of the tuples.
	 * @param <X> The of the exceptions.
	 * @return The built function.
	 */
	@SuppressWarnings("unchecked")
	public static <T1, T2, X extends Exception> Function<Tuple2<T1, T2>, T2, X> second() {
		return (Function<Tuple2<T1, T2>, T2, X>) SECOND;
	}
	
	private Functions() {
		// Prevent instantiation.
	}
}
