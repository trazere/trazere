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

import com.trazere.core.lang.ThrowableFactory;

/**
 * The {@link Functions} class provides various factories of {@link Function functions}.
 * 
 * @see Function
 * @see Function2
 * @see Function3
 */
public class Functions {
	/**
	 * Builds an identity function.
	 * 
	 * @param <T> Type of the values.
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
	 * @param <A> Type of the arguments.
	 * @param <I> Type of the intermediate values
	 * @param <R> Type of the results.
	 * @param g Outer function.
	 * @param f Inner function.
	 * @return The built function.
	 */
	public static <A, I, R> Function<A, R> compose(final Function<? super I, ? extends R> g, final Function<? super A, ? extends I> f) {
		return FunctionUtils.map(f, g);
	}
	
	/**
	 * Builds a function that evaluates to the given result for all arguments.
	 *
	 * @param <A> Type of the arguments.
	 * @param <R> Type of the results.
	 * @param result Result of the function.
	 * @return The built function.
	 */
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
	 * Builds a function that evaluates the argument functions with the given argument.
	 *
	 * @param <A> Type of the argument.
	 * @param <R> Type of the results.
	 * @param arg Argument to use to evaluate the functions.
	 * @return The built function.
	 */
	public static <A, R> Function<Function<? super A, ? extends R>, R> evaluate(final A arg) {
		return function -> function.evaluate(arg);
	}
	
	/**
	 * Builds a function that evaluates the two arguments argument functions with the given arguments.
	 *
	 * @param <A1> Type of the first argument.
	 * @param <A2> Type of the second argument.
	 * @param <R> Type of the results.
	 * @param arg1 First argument to use to evaluate the functions.
	 * @param arg2 Second argument to use to evaluate the functions.
	 * @return The built function.
	 */
	public static <A1, A2, R> Function<Function2<? super A1, ? super A2, ? extends R>, R> evaluate(final A1 arg1, final A2 arg2) {
		return function -> function.evaluate(arg1, arg2);
	}
	
	/**
	 * Builds a function that evaluates the three arguments argument functions with the given arguments.
	 *
	 * @param <A1> Type of the first argument.
	 * @param <A2> Type of the second argument.
	 * @param <A3> Type of the third argument.
	 * @param <R> Type of the results.
	 * @param arg1 First argument to use to evaluate the functions.
	 * @param arg2 Second argument to use to evaluate the functions.
	 * @param arg3 Third argument to use to evaluate the functions.
	 * @return The built function.
	 */
	public static <A1, A2, A3, R> Function<Function3<? super A1, ? super A2, ? super A3, ? extends R>, R> evaluate(final A1 arg1, final A2 arg2, final A3 arg3) {
		return function -> function.evaluate(arg1, arg2, arg3);
	}
	
	/**
	 * Builds a function that evaluates the four arguments argument functions with the given arguments.
	 *
	 * @param <A1> Type of the first argument.
	 * @param <A2> Type of the second argument.
	 * @param <A3> Type of the third argument.
	 * @param <A4> Type of the fourth argument.
	 * @param <R> Type of the results.
	 * @param arg1 First argument to use to evaluate the functions.
	 * @param arg2 Second argument to use to evaluate the functions.
	 * @param arg3 Third argument to use to evaluate the functions.
	 * @param arg4 Fourth argument to use to evaluate the functions.
	 * @return The built function.
	 */
	public static <A1, A2, A3, A4, R> Function<Function4<? super A1, ? super A2, ? super A3, ? super A4, ? extends R>, R> evaluate(final A1 arg1, final A2 arg2, final A3 arg3, final A4 arg4) {
		return function -> function.evaluate(arg1, arg2, arg3, arg4);
	}
	
	/**
	 * Builds a function that evaluates the five arguments argument functions with the given arguments.
	 *
	 * @param <A1> Type of the first argument.
	 * @param <A2> Type of the second argument.
	 * @param <A3> Type of the third argument.
	 * @param <A4> Type of the fourth argument.
	 * @param <A5> Type of the fifth argument.
	 * @param <R> Type of the results.
	 * @param arg1 First argument to use to evaluate the functions.
	 * @param arg2 Second argument to use to evaluate the functions.
	 * @param arg3 Third argument to use to evaluate the functions.
	 * @param arg4 Fourth argument to use to evaluate the functions.
	 * @param arg5 Fifth argument to use to evaluate the functions.
	 * @return The built function.
	 */
	public static <A1, A2, A3, A4, A5, R> Function<Function5<? super A1, ? super A2, ? super A3, ? super A4, ? super A5, ? extends R>, R> evaluate(final A1 arg1, final A2 arg2, final A3 arg3, final A4 arg4, final A5 arg5) {
		return function -> function.evaluate(arg1, arg2, arg3, arg4, arg5);
	}
	
	// TODO: RecordFunctions
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
	// TODO: RecordFunctions
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
	// TODO: RecordFunctions
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
	
	/**
	 * Builds a two arguments function that evaluates to the given result for all arguments.
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
	 * Builds a three arguments function that evaluates to the given result for all arguments.
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
	
	private Functions() {
		// Prevents instantiation.
	}
}
