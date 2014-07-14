/*
 *  Copyright 2006-2013 Julien Dufour
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
package com.trazere.core.functional;

import com.trazere.core.imperative.Procedure;
import com.trazere.core.imperative.Procedure2;
import com.trazere.core.imperative.Procedure3;
import com.trazere.core.lang.ThrowableFactory;

/**
 * The {@link Functions} class provides various factories of functions.
 * 
 * @see Function
 */
public class Functions {
	/**
	 * Builds an identity function.
	 * 
	 * @param <T> Type of the arguments and results.
	 * @return The built function.
	 */
	@SuppressWarnings("unchecked")
	public static <T> Function<T, T> identity() {
		return (Function<T, T>) IDENTITY;
	}
	
	private static final Function<?, ?> IDENTITY = arg -> arg;
	
	/**
	 * Builds a function corresponding to the composition of the given functions (g . f).
	 *
	 * @param <A1> Type of the arguments of the inner function.
	 * @param <A2> Type of the arguments of the outer function.
	 * @param <R> Type of the results.
	 * @param g Outer function.
	 * @param f Inner function.
	 * @return The built function.
	 */
	public static <A1, A2, R> Function<A1, R> compose(final Function<? super A2, ? extends R> g, final Function<? super A1, ? extends A2> f) {
		return FunctionUtils.map(f, g);
	}
	
	/**
	 * Builds a function that always evaluates to the given result.
	 *
	 * @param <A> Type of the arguments.
	 * @param <R> Type of the results.
	 * @param result Result of the function.
	 * @return The built function.
	 */
	// TODO: rename to fromValue ?
	public static <A, R> Function<A, R> constant(final R result) {
		return arg -> result;
	}
	
	/**
	 * Builds a function that throws the given exception.
	 *
	 * @param <A> Type of the arguments.
	 * @param <R> Type of the results.
	 * @param exception Exception to throw.
	 * @return The built function.
	 */
	public static <A, R> Function<A, R> failure(final RuntimeException exception) {
		assert null != exception;
		
		return arg -> {
			throw exception;
		};
	}
	
	/**
	 * Builds a function that throws an exception.
	 *
	 * @param <A> Type of the arguments.
	 * @param <R> Type of the results.
	 * @param throwableFactory Throwable factory to use.
	 * @return The built function.
	 */
	public static <A, R> Function<A, R> failure(final ThrowableFactory<? extends RuntimeException> throwableFactory) {
		assert null != throwableFactory;
		
		return arg -> {
			throw throwableFactory.build("Failed argument \"" + arg + "\"");
		};
	}
	
	/**
	 * Builds a function that lifts the given thunk.
	 *
	 * @param <A> Type of the arguments.
	 * @param <R> Type of the results.
	 * @param thunk Thunk to lift.
	 * @return The built function.
	 */
	public static <A, R> Function<A, R> fromThunk(final Thunk<? extends R> thunk) {
		assert null != thunk;
		
		return arg -> thunk.evaluate();
	}
	
	/**
	 * Builds a function that lifts the given predicate.
	 *
	 * @param <A> Type of the arguments.
	 * @param predicate Predicate to lift.
	 * @return The built function.
	 */
	public static final <A> Function<A, Boolean> fromPredicate(final Predicate<? super A> predicate) {
		assert null != predicate;
		
		return arg -> predicate.evaluate(arg);
	}
	
	/**
	 * Builds a function that lifts the given procedure.
	 *
	 * @param <A> Type of the arguments.
	 * @param procedure Procedure to lift.
	 * @return The built function.
	 */
	public static <A> Function<A, Void> fromProcedure(final Procedure<? super A> procedure) {
		return fromProcedure(procedure, (Void) null);
	}
	
	/**
	 * Builds a function that lifts the given procedure and evaluates to the given result.
	 *
	 * @param <A> Type of the arguments.
	 * @param <R> Type of the results.
	 * @param procedure Procedure to lift.
	 * @param result Result of the function.
	 * @return The built function.
	 */
	public static <A, R> Function<A, R> fromProcedure(final Procedure<? super A> procedure, final R result) {
		assert null != procedure;
		
		return arg -> {
			procedure.execute(arg);
			return result;
		};
	}
	
	//	/**
	//	 * Builds a function corresponding to the given multimap.
	//	 *
	//	 * @param <K> Type of the arguments (the keys of the multimap).
	//	 * @param <C> Type of the results (the collections of the multimap).
	//	 * @param <X> Type of the exceptions.
	//	 * @param map The multimap.
	//	 * @return The built function.
	//	 */
	//	public static <K, C extends Collection<?>, X extends Exception> Function1<K, C, X> fromMultimap(final Multimap<? super K, ?, ? extends C> map) {
	//		assert null != map;
	//
	//		return new Function1<K, C, X>() {
	//			@Override
	//			public C evaluate(final K key) {
	//				return map.get(key);
	//			}
	//		};
	//	}
	
	//	/**
	//	 * Builds a function corresponding to the given record.
	//	 * <p>
	//	 * The built function evaluates to the values associated to the keys in the record and fails for the other keys.
	//	 *
	//	 * @param <K> Type of the argument (the keys of the record).
	//	 * @param <V> Type of the results (the values of the record).
	//	 * @param record The record.
	//	 * @return The built function.
	//	 */
	//	public static <K, V> Function1<K, V, RecordException> fromRecord(final Record<? super K, ? extends V> record) {
	//		assert null != record;
	//
	//		return new Function1<K, V, RecordException>() {
	//			@Override
	//			public V evaluate(final K key)
	//			throws RecordException {
	//				return record.get(key);
	//			}
	//		};
	//	}
	//
	//	/**
	//	 * Builds a function corresponding to the given record.
	//	 * <p>
	//	 * The built function evaluates to the values associated to the keys in the record and to the default value for the other keys.
	//	 *
	//	 * @param <K> Type of the argument (the keys of the record).
	//	 * @param <V> Type of the results.
	//	 * @param <RV> Type of the values of the record.
	//	 * @param record The record.
	//	 * @param defaultValue The default value. May be <code>null</code>.
	//	 * @return The built function.
	//	 */
	//	public static <K, V, RV extends V> Function1<K, V, RecordException> fromRecord(final Record<? super K, RV> record, final RV defaultValue) {
	//		assert null != record;
	//
	//		return new Function1<K, V, RecordException>() {
	//			@Override
	//			public V evaluate(final K key)
	//			throws RecordException {
	//				return record.get(key, defaultValue);
	//			}
	//		};
	//	}
	//
	//	/**
	//	 * Builds a function corresponding to the given record.
	//	 * <p>
	//	 * The built function evaluates to the values associated to the keys in the record and throws an exception for the other keys.
	//	 *
	//	 * @param <K> Type of the argument (the keys of the map).
	//	 * @param <V> Type of the results (the values values).
	//	 * @param <X> Type of the exceptions.
	//	 * @param record The record.
	//	 * @param throwableFactory The throwable factory.
	//	 * @return The built function.
	//	 */
	//	public static <K, V, X extends Exception> Function1<K, V, X> fromRecord(final Record<? super K, ? extends V> record, final ThrowableFactory<X> throwableFactory) {
	//		assert null != record;
	//		assert null != throwableFactory;
	//
	//		return new Function1<K, V, X>() {
	//			@Override
	//			public V evaluate(final K key)
	//			throws X {
	//				try {
	//					return record.get(key);
	//				} catch (final RecordException exception) {
	//					throw throwableFactory.build(exception);
	//				}
	//			}
	//		};
	//	}
	
	//	/**
	//	 * Builds a function that evaluates to the result of the evaluation of its zero arguments function arguments.
	//	 *
	//	 * @param <R> Type of the result values.
	//	 * @param <X> Type of the exceptions.
	//	 * @return The built function.
	//	 */
	//	@SuppressWarnings("unchecked")
	//	public static <R, X extends Exception> Function1<Function0<? extends R, ? extends X>, R, X> evaluate() {
	//		return (Function1<Function0<? extends R, ? extends X>, R, X>) _EVALUATE;
	//	}
	//
	//	private static final Function1<? extends Function0<?, ?>, ?, ?> _EVALUATE = new Function1<Function0<Object, RuntimeException>, Object, RuntimeException>() {
	//		@Override
	//		public Object evaluate(final Function0<Object, RuntimeException> function) {
	//			return function.evaluate();
	//		}
	//	};
	//
	//	/**
	//	 * Builds a function that evaluates to the result of the evaluation of its one argument function arguments with the given value.
	//	 *
	//	 * @param <T> Type of the argument values.
	//	 * @param <R> Type of the result values.
	//	 * @param <X> Type of the exceptions.
	//	 * @param value The argument value. May be <code>null</code>.
	//	 * @return The built function.
	//	 */
	//	public static <T, R, X extends Exception> Function1<Function1<? super T, ? extends R, ? extends X>, R, X> evaluate(final T value) {
	//		return new Function1<Function1<? super T, ? extends R, ? extends X>, R, X>() {
	//			@Override
	//			public R evaluate(final Function1<? super T, ? extends R, ? extends X> function)
	//			throws X {
	//				return function.evaluate(value);
	//			}
	//		};
	//	}
	//
	//	/**
	//	 * Builds a function that evaluates to the result of the evaluation of its two arguments function arguments with the given values.
	//	 *
	//	 * @param <T1> Type of the first argument values.
	//	 * @param <T2> Type of the second argument values.
	//	 * @param <R> Type of the result values.
	//	 * @param <X> Type of the exceptions.
	//	 * @param value1 The first argument value. May be <code>null</code>.
	//	 * @param value2 The second argument value. May be <code>null</code>.
	//	 * @return The built function.
	//	 */
	//	public static <T1, T2, R, X extends Exception> Function1<Function2<? super T1, ? super T2, ? extends R, ? extends X>, R, X> evaluate(final T1 value1, final T2 value2) {
	//		return new Function1<Function2<? super T1, ? super T2, ? extends R, ? extends X>, R, X>() {
	//			@Override
	//			public R evaluate(final Function2<? super T1, ? super T2, ? extends R, ? extends X> function)
	//			throws X {
	//				return function.evaluate(value1, value2);
	//			}
	//		};
	//	}
	
	/**
	 * Builds a two arguments function that always evaluates to the given result.
	 *
	 * @param <A1> Type of the first arguments.
	 * @param <A2> Type of the second arguments.
	 * @param <R> Type of the results.
	 * @param result Result of the function.
	 * @return The built function.
	 */
	public static <A1, A2, R> Function2<A1, A2, R> constant2(final R result) {
		return (arg1, arg2) -> result;
	}
	
	/**
	 * Builds a two arguments function that throws the given exception.
	 *
	 * @param <A1> Type of the first arguments.
	 * @param <A2> Type of the second arguments.
	 * @param <R> Type of the results.
	 * @param exception Exception to throw.
	 * @return The built function.
	 */
	public static <A1, A2, R> Function2<A1, A2, R> failure2(final RuntimeException exception) {
		assert null != exception;
		
		return (arg1, arg2) -> {
			throw exception;
		};
	}
	
	/**
	 * Builds a two arguments function that throws an exception.
	 *
	 * @param <A1> Type of the first arguments.
	 * @param <A2> Type of the second arguments.
	 * @param <R> Type of the results.
	 * @param throwableFactory Throwable factory to use.
	 * @return The built function.
	 */
	public static <A1, A2, R> Function2<A1, A2, R> failure2(final ThrowableFactory<? extends RuntimeException> throwableFactory) {
		assert null != throwableFactory;
		
		return (arg1, arg2) -> {
			throw throwableFactory.build();
		};
	}
	
	/**
	 * Builds a two arguments function that lifts the given thunk.
	 *
	 * @param <A1> Type of the first arguments.
	 * @param <A2> Type of the second arguments.
	 * @param <R> Type of the results.
	 * @param thunk Thunk to lift.
	 * @return The built function.
	 */
	public static <A1, A2, R> Function2<A1, A2, R> fromThunk2(final Thunk<? extends R> thunk) {
		assert null != thunk;
		
		return (arg1, arg2) -> thunk.evaluate();
	}
	
	/**
	 * Builds a two arguments function from the given two arguments predicate.
	 *
	 * @param <A1> Type of the first arguments.
	 * @param <A2> Type of the second arguments.
	 * @param predicate Predicate to lift.
	 * @return The built function.
	 */
	public static <A1, A2> Function2<A1, A2, Boolean> fromPredicate2(final Predicate2<? super A1, ? super A2> predicate) {
		assert null != predicate;
		
		return (arg1, arg2) -> predicate.evaluate(arg1, arg2);
	}
	
	/**
	 * Builds a two arguments function from the given two arguments procedure.
	 *
	 * @param <A1> Type of the first arguments.
	 * @param <A2> Type of the second arguments.
	 * @param procedure Procedure to lift.
	 * @return The built function.
	 */
	public static <A1, A2> Function2<A1, A2, Void> fromProcedure2(final Procedure2<? super A1, ? super A2> procedure) {
		return fromProcedure2(procedure, (Void) null);
	}
	
	/**
	 * Builds a two arguments function from the given two arguments procedure.
	 *
	 * @param <A1> Type of the first arguments.
	 * @param <A2> Type of the second arguments.
	 * @param <R> Type of the results.
	 * @param procedure Procedure to lift.
	 * @param result Result of the function.
	 * @return The built function.
	 */
	public static <A1, A2, R> Function2<A1, A2, R> fromProcedure2(final Procedure2<? super A1, ? super A2> procedure, final R result) {
		assert null != procedure;
		
		return (arg1, arg2) -> {
			procedure.execute(arg1, arg2);
			return result;
		};
	}
	
	//	/**
	//	 * Builds a two arguments function that lifts the given function.
	//	 * <p>
	//	 * The function is evaluated with the first argument.
	//	 *
	//	 * @param <A1> Type of the first arguments.
	//	 * @param <A2> Type of the second arguments.
	//	 * @param <R> Type of the results.
	//	 * @param function Function to lift.
	//	 * @return The built function.
	//	 */
	//	public static <A1, A2, R> Function2<A1, A2, R> lift2(final Function<? super A1, ? extends R> function) {
	//		assert null != function;
	//
	//		return (arg1, arg2) -> function.evaluate(arg1);
	//	}
	
	/**
	 * Builds a three arguments function that always evaluates to the given result.
	 *
	 * @param <A1> Type of the first arguments.
	 * @param <A2> Type of the second arguments.
	 * @param <A3> Type of the third arguments.
	 * @param <R> Type of the results.
	 * @param result Result of the function.
	 * @return The built function.
	 */
	public static <A1, A2, A3, R> Function3<A1, A2, A3, R> constant3(final R result) {
		return (arg1, arg2, arg3) -> result;
	}
	
	/**
	 * Builds a three arguments function that throws the given exception.
	 *
	 * @param <A1> Type of the first arguments.
	 * @param <A2> Type of the second arguments.
	 * @param <A3> Type of the third arguments.
	 * @param <R> Type of the results.
	 * @param exception Exception to throw.
	 * @return The built function.
	 */
	public static <A1, A2, A3, R> Function3<A1, A2, A3, R> failure3(final RuntimeException exception) {
		assert null != exception;
		
		return (arg1, arg2, arg3) -> {
			throw exception;
		};
	}
	
	/**
	 * Builds a three arguments function that throws an exception.
	 *
	 * @param <A1> Type of the first arguments.
	 * @param <A2> Type of the second arguments.
	 * @param <A3> Type of the third arguments.
	 * @param <R> Type of the results.
	 * @param throwableFactory Throwable factory to use.
	 * @return The built function.
	 */
	public static <A1, A2, A3, R> Function3<A1, A2, A3, R> failure3(final ThrowableFactory<? extends RuntimeException> throwableFactory) {
		assert null != throwableFactory;
		
		return (arg1, arg2, arg3) -> {
			throw throwableFactory.build();
		};
	}
	
	/**
	 * Builds a three arguments function that lifts the given thunk.
	 *
	 * @param <A1> Type of the first arguments.
	 * @param <A2> Type of the second arguments.
	 * @param <A3> Type of the third arguments.
	 * @param <R> Type of the results.
	 * @param thunk Thunk to lift.
	 * @return The built function.
	 */
	public static <A1, A2, A3, R> Function3<A1, A2, A3, R> fromThunk3(final Thunk<? extends R> thunk) {
		assert null != thunk;
		
		return (arg1, arg2, arg3) -> thunk.evaluate();
	}
	
	/**
	 * Builds a three arguments function from the given three arguments predicate.
	 *
	 * @param <A1> Type of the first arguments.
	 * @param <A2> Type of the second arguments.
	 * @param <A3> Type of the third arguments.
	 * @param predicate Predicate to lift.
	 * @return The built function.
	 */
	public static <A1, A2, A3> Function3<A1, A2, A3, Boolean> fromPredicate3(final Predicate3<? super A1, ? super A2, ? super A3> predicate) {
		assert null != predicate;
		
		return (arg1, arg2, arg3) -> predicate.evaluate(arg1, arg2, arg3);
	}
	
	/**
	 * Builds a three arguments function from the given three arguments procedure.
	 *
	 * @param <A1> Type of the first arguments.
	 * @param <A2> Type of the second arguments.
	 * @param <A3> Type of the third arguments.
	 * @param procedure Procedure to lift.
	 * @return The built function.
	 */
	public static <A1, A2, A3> Function3<A1, A2, A3, Void> fromProcedure3(final Procedure3<? super A1, ? super A2, ? super A3> procedure) {
		return fromProcedure3(procedure, (Void) null);
	}
	
	/**
	 * Builds a three arguments function from the given three arguments procedure.
	 *
	 * @param <A1> Type of the first arguments.
	 * @param <A2> Type of the second arguments.
	 * @param <A3> Type of the third arguments.
	 * @param <R> Type of the results.
	 * @param procedure Procedure to lift.
	 * @param result Result of the function.
	 * @return The built function.
	 */
	public static <A1, A2, A3, R> Function3<A1, A2, A3, R> fromProcedure3(final Procedure3<? super A1, ? super A2, ? super A3> procedure, final R result) {
		assert null != procedure;
		
		return (arg1, arg2, arg3) -> {
			procedure.execute(arg1, arg2, arg3);
			return result;
		};
	}
	
	//	/**
	//	 * Builds a three arguments function that lifts the given one argument function.
	//	 *
	//	 * @param <T1> Type of the first argument values.
	//	 * @param <T2> Type of the second argument values.
	//	 * @param <T3> Type of the third argument values.
	//	 * @param <R> Type of the result values.
	//	 * @param <X> Type of the exceptions.
	//	 * @param function The lifted function.
	//	 * @return The built function.
	//	 */
	//	public static <T1, T2, T3, R, X extends Exception> Function3<T1, T2, T3, R, X> lift3(final Function1<? super T1, ? extends R, ? extends X> function) {
	//		assert null != function;
	//
	//		return new Function3<T1, T2, T3, R, X>() {
	//			@Override
	//			public R evaluate(final T1 value1, final T2 value2, final T3 value3)
	//			throws X {
	//				return function.evaluate(value1);
	//			}
	//		};
	//	}
	//
	//	/**
	//	 * Builds a three argument function that lifts the given two arguments function.
	//	 *
	//	 * @param <T1> Type of the first argument values.
	//	 * @param <T2> Type of the second argument values.
	//	 * @param <T3> Type of the third argument values.
	//	 * @param <R> Type of the result values.
	//	 * @param <X> Type of the exceptions.
	//	 * @param function The lifted function.
	//	 * @return The built function.
	//	 */
	//	public static <T1, T2, T3, R, X extends Exception> Function3<T1, T2, T3, R, X> lift3(final Function2<? super T1, ? super T2, ? extends R, ? extends X> function) {
	//		assert null != function;
	//
	//		return new Function3<T1, T2, T3, R, X>() {
	//			@Override
	//			public R evaluate(final T1 value1, final T2 value2, final T3 value3)
	//			throws X {
	//				return function.evaluate(value1, value2);
	//			}
	//		};
	//	}
	
	//
	
	//	public static <T, X extends Exception> Predicate1<T, X> normalizer() {
	//		return new Predicate1<T, X>() {
	//			private final Set<T> _visitedValues = new HashSet<T>();
	//
	//			@Override
	//			public boolean evaluate(final T value)
	//			throws X {
	//				return _visitedValues.add(value);
	//			}
	//		};
	//	}
	//
	//	/**
	//	 * Builds a function that normalizes values according to the given hash function.
	//	 * <p>
	//	 * The built function always returns the same value for a given hash. The returned value corresponds to the first value that produced the corresponding
	//	 * hash.
	//	 *
	//	 * @param <T> Type of the values.
	//	 * @param <H> Type of the hash values.
	//	 * @param <X> Type of the exceptions.
	//	 * @param hash Function that hash the values.
	//	 * @return The built predicate.
	//	 */
	//	public static <T, H, X extends Exception> Function1<T, T, X> normalizer(final Function1<? super T, H, ? extends X> hash) {
	//		assert null != hash;
	//
	//		return new Function1<T, T, X>() {
	//			private final Map<H, T> _values = new HashMap<H, T>();
	//
	//			@Override
	//			public T evaluate(final T value)
	//			throws X {
	//				final H hashValue = hash.evaluate(value);
	//				if (_values.containsKey(hashValue)) {
	//					return _values.get(hashValue);
	//				} else {
	//					_values.put(hashValue, value);
	//					return value;
	//				}
	//			}
	//		};
	//	}
	
	private Functions() {
		// Prevents instantiation.
	}
}
