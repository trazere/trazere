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
import com.trazere.core.util.Tuple2;

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
	
	/**
	 * Curries the given function which takes pairs of elements into a function that takes two arguments.
	 *
	 * @param <A1> Type of the first element of the argument pairs.
	 * @param <A2> Type of the second element of the argument pairs.
	 * @param <R> Type of the results.
	 * @param function Function to curry.
	 * @return The built function.
	 */
	public static <A1, A2, R> Function2<A1, A2, R> curry(final Function<? super Tuple2<A1, A2>, ? extends R> function) {
		assert null != function;
		
		return (arg1, arg2) -> function.evaluate(new Tuple2<>(arg1, arg2));
	}
	
	/**
	 * Uncurries the given function which takes two arguments into a function that takes pairs of elements.
	 *
	 * @param <A1> Type of the first arguments.
	 * @param <A2> Type of the second arguments.
	 * @param <R> Type of the results.
	 * @param function Function to uncurry.
	 * @return The built function.
	 */
	public static <A1, A2, R> Function<Tuple2<A1, A2>, R> uncurry(final Function2<? super A1, ? super A2, ? extends R> function) {
		assert null != function;
		
		return (arg) -> function.evaluate(arg.get1(), arg.get2());
	}
	
	private Functions() {
		// Prevents instantiation.
	}
}
