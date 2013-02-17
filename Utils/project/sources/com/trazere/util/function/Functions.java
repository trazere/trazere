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

import com.trazere.util.collection.CollectionUtils;
import com.trazere.util.collection.Multimap;
import com.trazere.util.lang.ThrowableFactory;
import com.trazere.util.record.Record;
import com.trazere.util.record.RecordException;
import com.trazere.util.type.Maybe;
import java.util.Collection;
import java.util.Map;

/**
 * The {@link Functions} class provides various common functions.
 * 
 * @see Function1
 * @see Function2
 */
public class Functions {
	/**
	 * Builds an identity function.
	 * 
	 * @param <T> Type of the argument and result values.
	 * @param <X> Type of the exceptions.
	 * @return The built function.
	 */
	@SuppressWarnings("unchecked")
	public static <T, X extends Exception> Function1<T, T, X> identity() {
		return (Function1<T, T, X>) _IDENTITY;
	}
	
	private static final Function1<?, ?, ?> _IDENTITY = new Function1<Object, Object, RuntimeException>() {
		@Override
		public Object evaluate(final Object value) {
			return value;
		}
	};
	
	/**
	 * Builds a function corresponding to the composition of the given functions (g . f).
	 * 
	 * @param <T1> Type of the argument values of the outer function.
	 * @param <T2> Type of the results.
	 * @param <X> Type of the exceptions.
	 * @param g The outer function.
	 * @param f The inner function.
	 * @return The built function.
	 */
	public static <T1, T2, X extends Exception> Function0<T2, X> compose(final Function1<? super T1, ? extends T2, ? extends X> g, final Function0<? extends T1, ? extends X> f) {
		assert null != g;
		assert null != f;
		
		return new Function0<T2, X>() {
			@Override
			public T2 evaluate()
			throws X {
				return g.evaluate(f.evaluate());
			}
		};
	}
	
	/**
	 * Builds a function corresponding to the composition of the given functions (g . f).
	 * 
	 * @param <T1> Type of the argument values of the inner function.
	 * @param <T2> Type of the argument values of the outer function.
	 * @param <T3> Type of the result values.
	 * @param <X> Type of the exceptions.
	 * @param g The outer function.
	 * @param f The inner function.
	 * @return The built function.
	 */
	public static <T1, T2, T3, X extends Exception> Function1<T1, T3, X> compose(final Function1<? super T2, ? extends T3, ? extends X> g, final Function1<? super T1, ? extends T2, ? extends X> f) {
		assert null != g;
		assert null != f;
		
		return new Function1<T1, T3, X>() {
			@Override
			public T3 evaluate(final T1 value)
			throws X {
				return g.evaluate(f.evaluate(value));
			}
		};
	}
	
	/**
	 * Builds a zero arguments function which always evaluates to the given value.
	 * 
	 * @param <R> Type of the result values.
	 * @param <X> Type of the exceptions.
	 * @param result The result value. May be <code>null</code>.
	 * @return The built function.
	 */
	public static <R, X extends Exception> Function0<R, X> constant0(final R result) {
		return new Function0<R, X>() {
			@Override
			public R evaluate() {
				return result;
			}
		};
	}
	
	/**
	 * Builds a one argument function which always evaluates to the given value.
	 * 
	 * @param <T> Type of the argument values.
	 * @param <R> Type of the result value.
	 * @param <X> Type of the exceptions.
	 * @param result The result value. May be <code>null</code>.
	 * @return The built function.
	 */
	public static <T, R, X extends Exception> Function1<T, R, X> constant1(final R result) {
		return new Function1<T, R, X>() {
			@Override
			public R evaluate(final T value) {
				return result;
			}
		};
	}
	
	/**
	 * Builds a two arguments function which always evaluates to the given value.
	 * 
	 * @param <T1> Type of the first argument values.
	 * @param <T2> Type of the second argument values.
	 * @param <R> Type of the result value.
	 * @param <X> Type of the exceptions.
	 * @param result The result value. May be <code>null</code>.
	 * @return The built function.
	 */
	public static <T1, T2, R, X extends Exception> Function2<T1, T2, R, X> constant2(final R result) {
		return new Function2<T1, T2, R, X>() {
			@Override
			public R evaluate(final T1 value1, final T2 value2) {
				return result;
			}
		};
	}
	
	/**
	 * Builds a three arguments function which always evaluates to the given value.
	 * 
	 * @param <T1> Type of the first argument values.
	 * @param <T2> Type of the second argument values.
	 * @param <T3> Type of the third argument values.
	 * @param <R> Type of the result value.
	 * @param <X> Type of the exceptions.
	 * @param result The result value. May be <code>null</code>.
	 * @return The built function.
	 */
	public static <T1, T2, T3, R, X extends Exception> Function3<T1, T2, T3, R, X> constant3(final R result) {
		return new Function3<T1, T2, T3, R, X>() {
			@Override
			public R evaluate(final T1 value1, final T2 value2, final T3 value3) {
				return result;
			}
		};
	}
	
	/**
	 * Builds a one argument function which lifts the given zero arguments function.
	 * 
	 * @param <T> Type of the argument values.
	 * @param <R> Type of the result values.
	 * @param <X> Type of the exceptions.
	 * @param function The lifted function.
	 * @return The built function.
	 */
	public static <T, R, X extends Exception> Function1<T, R, X> lift1(final Function0<? extends R, ? extends X> function) {
		assert null != function;
		
		return new Function1<T, R, X>() {
			@Override
			public R evaluate(final T value)
			throws X {
				return function.evaluate();
			}
		};
	}
	
	/**
	 * Build a one argument function which lifts the given one argument predicate.
	 * 
	 * @param <T> Type of the values.
	 * @param <X> Type of the exceptions.
	 * @param predicate The lifted predicate.
	 * @return The built function.
	 */
	public static final <T, X extends Exception> Function1<T, Boolean, X> lift1(final Predicate1<? super T, ? extends X> predicate) {
		assert null != predicate;
		
		return new Function1<T, Boolean, X>() {
			@Override
			public Boolean evaluate(final T value)
			throws X {
				return predicate.evaluate(value);
			}
		};
	}
	
	/**
	 * Builds a two arguments function which lifts the given zero arguments function.
	 * 
	 * @param <T1> Type of the first argument values.
	 * @param <T2> Type of the second argument values.
	 * @param <R> Type of the result values.
	 * @param <X> Type of the exceptions.
	 * @param function The lifted function.
	 * @return The built function.
	 */
	public static <T1, T2, R, X extends Exception> Function2<T1, T2, R, X> lift2(final Function0<? extends R, ? extends X> function) {
		assert null != function;
		
		return new Function2<T1, T2, R, X>() {
			@Override
			public R evaluate(final T1 value1, final T2 value2)
			throws X {
				return function.evaluate();
			}
		};
	}
	
	/**
	 * Builds a two arguments function which lifts the given one argument function.
	 * 
	 * @param <T1> Type of the first argument values.
	 * @param <T2> Type of the second argument values.
	 * @param <R> Type of the result values.
	 * @param <X> Type of the exceptions.
	 * @param function The lifted function.
	 * @return The built function.
	 */
	public static <T1, T2, R, X extends Exception> Function2<T1, T2, R, X> lift2(final Function1<? super T1, ? extends R, ? extends X> function) {
		assert null != function;
		
		return new Function2<T1, T2, R, X>() {
			@Override
			public R evaluate(final T1 value1, final T2 value2)
			throws X {
				return function.evaluate(value1);
			}
		};
	}
	
	/**
	 * Builds a two arguments function which lifts the given two arguments predicate.
	 * 
	 * @param <T1> Type of the first argument values.
	 * @param <T2> Type of the second argument values.
	 * @param <X> Type of the exceptions.
	 * @param predicate The lifted predicate.
	 * @return The built function.
	 */
	public static final <T1, T2, X extends Exception> Function2<T1, T2, Boolean, X> lift2(final Predicate2<? super T1, ? super T2, ? extends X> predicate) {
		assert null != predicate;
		
		return new Function2<T1, T2, Boolean, X>() {
			@Override
			public Boolean evaluate(final T1 value1, final T2 value2)
			throws X {
				return predicate.evaluate(value1, value2);
			}
		};
	}
	
	/**
	 * Builds a three arguments function which lifts the given zero arguments function.
	 * 
	 * @param <T1> Type of the first argument values.
	 * @param <T2> Type of the second argument values.
	 * @param <T3> Type of the third argument values.
	 * @param <R> Type of the result values.
	 * @param <X> Type of the exceptions.
	 * @param function The lifted function.
	 * @return The built function.
	 */
	public static <T1, T2, T3, R, X extends Exception> Function3<T1, T2, T3, R, X> lift3(final Function0<? extends R, ? extends X> function) {
		assert null != function;
		
		return new Function3<T1, T2, T3, R, X>() {
			@Override
			public R evaluate(final T1 value1, final T2 value2, final T3 value3)
			throws X {
				return function.evaluate();
			}
		};
	}
	
	/**
	 * Builds a three arguments function which lifts the given one argument function.
	 * 
	 * @param <T1> Type of the first argument values.
	 * @param <T2> Type of the second argument values.
	 * @param <T3> Type of the third argument values.
	 * @param <R> Type of the result values.
	 * @param <X> Type of the exceptions.
	 * @param function The lifted function.
	 * @return The built function.
	 */
	public static <T1, T2, T3, R, X extends Exception> Function3<T1, T2, T3, R, X> lift3(final Function1<? super T1, ? extends R, ? extends X> function) {
		assert null != function;
		
		return new Function3<T1, T2, T3, R, X>() {
			@Override
			public R evaluate(final T1 value1, final T2 value2, final T3 value3)
			throws X {
				return function.evaluate(value1);
			}
		};
	}
	
	/**
	 * Builds a three argument function which lifts the given two arguments function.
	 * 
	 * @param <T1> Type of the first argument values.
	 * @param <T2> Type of the second argument values.
	 * @param <T3> Type of the third argument values.
	 * @param <R> Type of the result values.
	 * @param <X> Type of the exceptions.
	 * @param function The lifted function.
	 * @return The built function.
	 */
	public static <T1, T2, T3, R, X extends Exception> Function3<T1, T2, T3, R, X> lift3(final Function2<? super T1, ? super T2, ? extends R, ? extends X> function) {
		assert null != function;
		
		return new Function3<T1, T2, T3, R, X>() {
			@Override
			public R evaluate(final T1 value1, final T2 value2, final T3 value3)
			throws X {
				return function.evaluate(value1, value2);
			}
		};
	}
	
	/**
	 * Builds a three arguments function which lifts the given three arguments predicate.
	 * 
	 * @param <T1> Type of the first argument values.
	 * @param <T2> Type of the second argument values.
	 * @param <T3> Type of the third argument values.
	 * @param <X> Type of the exceptions.
	 * @param predicate The lifted predicate.
	 * @return The built function.
	 */
	public static final <T1, T2, T3, X extends Exception> Function3<T1, T2, T3, Boolean, X> lift3(final Predicate3<? super T1, ? super T2, ? super T3, ? extends X> predicate) {
		assert null != predicate;
		
		return new Function3<T1, T2, T3, Boolean, X>() {
			@Override
			public Boolean evaluate(final T1 value1, final T2 value2, final T3 value3)
			throws X {
				return predicate.evaluate(value1, value2, value3);
			}
		};
	}
	
	/**
	 * Builds a function corresponding to the given map.
	 * <p>
	 * The built function evaluates to the values associated to the keys in the map and to <code>null</code> for the other keys.
	 * 
	 * @param <K> Type of the argument (the keys of the map).
	 * @param <V> Type of the results (the values values).
	 * @param <X> Type of the exceptions.
	 * @param map The map.
	 * @return The built function.
	 */
	public static <K, V, X extends Exception> Function1<K, V, X> map(final Map<? super K, ? extends V> map) {
		assert null != map;
		
		return new Function1<K, V, X>() {
			@Override
			public V evaluate(final K key) {
				return map.get(key);
			}
		};
	}
	
	/**
	 * Builds a function corresponding to the given map.
	 * <p>
	 * The built function evaluates to the values associated to the keys in the map and to the default value for the other keys.
	 * 
	 * @param <K> Type of the argument (the keys of the map).
	 * @param <V> Type of the results (the values values).
	 * @param <X> Type of the exceptions.
	 * @param map The map.
	 * @param defaultValue The default value. May be <code>null</code>.
	 * @return The built function.
	 */
	public static <K, V, X extends Exception> Function1<K, V, X> map(final Map<? super K, ? extends V> map, final V defaultValue) {
		assert null != map;
		
		return new Function1<K, V, X>() {
			@Override
			public V evaluate(final K key) {
				return CollectionUtils.get(map, key, defaultValue);
			}
		};
	}
	
	/**
	 * Builds a function corresponding to the given map.
	 * <p>
	 * The built function evaluates to the values associated to the keys in the map and throws an exception the other keys.
	 * 
	 * @param <K> Type of the argument (the keys of the map).
	 * @param <V> Type of the results (the values values).
	 * @param <X> Type of the exceptions.
	 * @param map The map.
	 * @param throwableFactory The throwable factory.
	 * @return The built function.
	 */
	public static <K, V, X extends Exception> Function1<K, V, X> map(final Map<? super K, ? extends V> map, final ThrowableFactory<X> throwableFactory) {
		assert null != map;
		assert null != throwableFactory;
		
		return new Function1<K, V, X>() {
			@Override
			public V evaluate(final K key)
			throws X {
				return CollectionUtils.get(map, key, throwableFactory);
			}
		};
	}
	
	/**
	 * Builds a function corresponding to the given map.
	 * <p>
	 * The built function evaluates to the values associated to the keys in the map wrapped into a {@link Maybe maybe} instance to reflect the domain of the
	 * map.
	 * 
	 * @param <K> Type of the argument (the keys of the map).
	 * @param <V> Type of the results (the values values).
	 * @param <X> Type of the exceptions.
	 * @param map The map.
	 * @return The built function.
	 */
	public static <K, V, X extends Exception> Function1<K, Maybe<V>, X> maybeMap(final Map<? super K, ? extends V> map) {
		assert null != map;
		
		return new Function1<K, Maybe<V>, X>() {
			@Override
			public Maybe<V> evaluate(final K key) {
				return CollectionUtils.get(map, key);
			}
		};
	}
	
	/**
	 * Builds a function corresponding to the given multimap.
	 * 
	 * @param <K> Type of the arguments (the keys of the multimap).
	 * @param <C> Type of the results (the collections of the multimap).
	 * @param <X> Type of the exceptions.
	 * @param map The multimap.
	 * @return The built function.
	 */
	public static <K, C extends Collection<?>, X extends Exception> Function1<K, C, X> multimap(final Multimap<? super K, ?, ? extends C> map) {
		assert null != map;
		
		return new Function1<K, C, X>() {
			@Override
			public C evaluate(final K key) {
				return map.get(key);
			}
		};
	}
	
	/**
	 * Builds a function corresponding to the given record.
	 * <p>
	 * The built function evaluates to the values associated to the keys in the record and fails for the other keys.
	 * 
	 * @param <K> Type of the argument (the keys of the record).
	 * @param <V> Type of the results (the values record).
	 * @param record The record.
	 * @return The built function.
	 */
	public static <K, V> Function1<K, V, RecordException> record(final Record<? super K, ? extends V> record) {
		assert null != record;
		
		return new Function1<K, V, RecordException>() {
			@Override
			public V evaluate(final K key)
			throws RecordException {
				return record.get(key);
			}
		};
	}
	
	/**
	 * Builds a function corresponding to the given record.
	 * <p>
	 * The built function evaluates to the values associated to the keys in the record and to the default value for the other keys.
	 * 
	 * @param <K> Type of the argument (the keys of the record).
	 * @param <V> Type of the results (the values record).
	 * @param <V2> Type of the values of the record.
	 * @param record The record.
	 * @param defaultValue The default value. May be <code>null</code>.
	 * @return The built function.
	 */
	public static <K, V, V2 extends V> Function1<K, V, RecordException> record(final Record<? super K, V2> record, final V2 defaultValue) {
		assert null != record;
		
		return new Function1<K, V, RecordException>() {
			@Override
			public V evaluate(final K key)
			throws RecordException {
				return record.get(key, defaultValue);
			}
		};
	}
	
	// TODO: move to FunctionUtils ?
	/**
	 * Builds a function which evaluates to the result of the evaluation of its zero arguments function arguments.
	 * 
	 * @param <R> Type of the result values.
	 * @param <X> Type of the exceptions.
	 * @return The built function.
	 */
	@SuppressWarnings("unchecked")
	public static <R, X extends Exception> Function1<Function0<? extends R, ? extends X>, R, X> evaluate0() {
		return (Function1<Function0<? extends R, ? extends X>, R, X>) _EVALUATE0;
	}
	
	private static final Function1<? extends Function0<?, ?>, ?, ?> _EVALUATE0 = new Function1<Function0<Object, RuntimeException>, Object, RuntimeException>() {
		@Override
		public Object evaluate(final Function0<Object, RuntimeException> function) {
			return function.evaluate();
		}
	};
	
	// TODO: move to FunctionUtils ?
	/**
	 * Builds a function which evaluates to the result of the evaluation of its one argument function arguments with the given value.
	 * 
	 * @param <T> Type of the argument values.
	 * @param <R> Type of the result values.
	 * @param <X> Type of the exceptions.
	 * @param value The argument value. May be <code>null</code>.
	 * @return The built function.
	 */
	public static <T, R, X extends Exception> Function1<Function1<? super T, ? extends R, ? extends X>, R, X> evaluate1(final T value) {
		return new Function1<Function1<? super T, ? extends R, ? extends X>, R, X>() {
			@Override
			public R evaluate(final Function1<? super T, ? extends R, ? extends X> function)
			throws X {
				return function.evaluate(value);
			}
		};
	}
	
	// TODO: move to FunctionUtils ?
	/**
	 * Builds a function which evaluates to the result of the evaluation of its two arguments function arguments with the given values.
	 * 
	 * @param <T1> Type of the first argument values.
	 * @param <T2> Type of the second argument values.
	 * @param <R> Type of the result values.
	 * @param <X> Type of the exceptions.
	 * @param value1 The first argument value. May be <code>null</code>.
	 * @param value2 The second argument value. May be <code>null</code>.
	 * @return The built function.
	 */
	public static <T1, T2, R, X extends Exception> Function1<Function2<? super T1, ? super T2, ? extends R, ? extends X>, R, X> evaluate2(final T1 value1, final T2 value2) {
		return new Function1<Function2<? super T1, ? super T2, ? extends R, ? extends X>, R, X>() {
			@Override
			public R evaluate(final Function2<? super T1, ? super T2, ? extends R, ? extends X> function)
			throws X {
				return function.evaluate(value1, value2);
			}
		};
	}
	
	private Functions() {
		// Prevents instantiation.
	}
}
