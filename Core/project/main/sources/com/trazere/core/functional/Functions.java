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
package com.trazere.core.functional;

import com.trazere.core.lang.ThrowableFactory;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

/**
 * The {@link Functions} class provides various factories of {@link Function functions}.
 * 
 * @see Function
 * @see Function2
 * @see Function3
 * @since 2.0
 */
public class Functions {
	/**
	 * Builds an identity function.
	 * 
	 * @param <T> Type of the values.
	 * @return The built function.
	 * @since 2.0
	 */
	@SuppressWarnings("unchecked")
	public static <T> Function<T, T> identity() {
		return (Function<T, T>) IDENTITY;
	}
	
	private static final Function<?, ?> IDENTITY = arg -> arg;
	
	/**
	 * Builds a function that evaluates to the given result for all arguments.
	 *
	 * @param <A> Type of the arguments.
	 * @param <R> Type of the results.
	 * @param result Result of the function.
	 * @return The built function.
	 * @since 2.0
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
	 * @since 2.0
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
	 * @param failureFactory Factory of the exceptions for the failures.
	 * @return The built function.
	 * @since 2.0
	 */
	public static <A, R> Function<A, R> failure(final ThrowableFactory<? extends RuntimeException> failureFactory) {
		assert null != failureFactory;
		
		return arg -> {
			throw failureFactory.build("Failed argument \"" + arg + "\"");
		};
	}
	
	/**
	 * Builds a function that throws an exception.
	 * 
	 * @param <A> Type of the arguments.
	 * @param <R> Type of the results.
	 * @param failureFactory Factory of the exceptions for the failures.
	 * @param message Message of the throwable.
	 * @return The built function.
	 * @since 2.0
	 */
	public static <A, R> Function<A, R> failure(final ThrowableFactory<? extends RuntimeException> failureFactory, final String message) {
		assert null != failureFactory;
		
		return arg -> {
			throw failureFactory.build(message);
		};
	}
	
	/**
	 * Builds a function that lifts the given Java 8 function.
	 * 
	 * @param <A> Type of the arguments.
	 * @param <R> Type of the results.
	 * @param function Java 8 function to lift.
	 * @return The built function.
	 * @since 2.0
	 */
	public static <A, R> Function<A, R> fromFunction(final java.util.function.Function<? super A, ? extends R> function) {
		assert null != function;
		
		return function::apply;
	}
	
	/**
	 * Builds a function that lifts the given unary operator.
	 * 
	 * @param <V> Type of the arguments and results.
	 * @param operator Unary operator to lift.
	 * @return The built function.
	 * @since 2.0
	 */
	public static <V> Function<V, V> fromUnaryOperator(final UnaryOperator<V> operator) {
		assert null != operator;
		
		return operator::apply;
	}
	
	/**
	 * Builds a function that lifts the given Java unary
	 */
	/**
	 * Builds a function that lifts the given thunk.
	 *
	 * @param <A> Type of the arguments.
	 * @param <R> Type of the results.
	 * @param thunk Thunk to lift.
	 * @return The built function.
	 * @since 2.0
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
	 * @since 2.0
	 */
	public static <A> Function<A, Boolean> fromPredicate(final Predicate<? super A> predicate) {
		assert null != predicate;
		
		return predicate::evaluate;
	}
	
	/**
	 * Builds a function that evaluates the argument functions with the given argument.
	 *
	 * @param <A> Type of the argument.
	 * @param <R> Type of the results.
	 * @param arg Argument to use to evaluate the functions.
	 * @return The built function.
	 * @since 2.0
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
	 * @since 2.0
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
	 * @since 2.0
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
	 * @since 2.0
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
	 * @since 2.0
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
	 * @since 2.0
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
	 * @since 2.0
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
	 * @param failureFactory Factory of the exceptions for the failures.
	 * @return The built function.
	 * @since 2.0
	 */
	public static <A1, A2, R> Function2<A1, A2, R> failure2(final ThrowableFactory<? extends RuntimeException> failureFactory) {
		assert null != failureFactory;
		
		return (arg1, arg2) -> {
			throw failureFactory.build();
		};
	}
	
	/**
	 * Builds a two arguments function that throws an exception.
	 *
	 * @param <A1> Type of the first arguments.
	 * @param <A2> Type of the second arguments.
	 * @param <R> Type of the results.
	 * @param failureFactory Factory of the exceptions for the failures.
	 * @param message Message of the throwable.
	 * @return The built function.
	 * @since 2.0
	 */
	public static <A1, A2, R> Function2<A1, A2, R> failure2(final ThrowableFactory<? extends RuntimeException> failureFactory, final String message) {
		assert null != failureFactory;
		
		return (arg1, arg2) -> {
			throw failureFactory.build(message);
		};
	}
	
	/**
	 * Builds a two arguments function that lifts the given bi-function.
	 * 
	 * @param <A1> Type of the first arguments.
	 * @param <A2> Type of the second arguments.
	 * @param <R> Type of the results.
	 * @param function Bi-function to lift.
	 * @return The built function.
	 * @since 2.0
	 */
	public static <A1, A2, R> Function2<A1, A2, R> fromBiFunction(final BiFunction<? super A1, ? super A2, ? extends R> function) {
		assert null != function;
		
		return function::apply;
	}
	
	/**
	 * Builds a two arguments function that lifts the given binary operator.
	 * 
	 * @param <V> Type of the arguments and results.
	 * @param operator Binary operator to lift.
	 * @return The built function.
	 * @since 2.0
	 */
	public static <V> Function2<V, V, V> fromBinaryOperator(final BinaryOperator<V> operator) {
		assert null != operator;
		
		return operator::apply;
	}
	
	/**
	 * Builds a two arguments function that lifts the given thunk.
	 *
	 * @param <A1> Type of the first arguments.
	 * @param <A2> Type of the second arguments.
	 * @param <R> Type of the results.
	 * @param thunk Thunk to lift.
	 * @return The built function.
	 * @since 2.0
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
	 * @since 2.0
	 */
	public static <A1, A2> Function2<A1, A2, Boolean> fromPredicate2(final Predicate2<? super A1, ? super A2> predicate) {
		assert null != predicate;
		
		return predicate::evaluate;
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
	 * @since 2.0
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
	 * @since 2.0
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
	 * @param failureFactory Factory of the exceptions for the failures.
	 * @return The built function.
	 * @since 2.0
	 */
	public static <A1, A2, A3, R> Function3<A1, A2, A3, R> failure3(final ThrowableFactory<? extends RuntimeException> failureFactory) {
		assert null != failureFactory;
		
		return (arg1, arg2, arg3) -> {
			throw failureFactory.build();
		};
	}
	
	/**
	 * Builds a three arguments function that throws an exception.
	 *
	 * @param <A1> Type of the first arguments.
	 * @param <A2> Type of the second arguments.
	 * @param <A3> Type of the third arguments.
	 * @param <R> Type of the results.
	 * @param failureFactory Factory of the exceptions for the failures.
	 * @param message Message of the throwable.
	 * @return The built function.
	 * @since 2.0
	 */
	public static <A1, A2, A3, R> Function3<A1, A2, A3, R> failure3(final ThrowableFactory<? extends RuntimeException> failureFactory, final String message) {
		assert null != failureFactory;
		
		return (arg1, arg2, arg3) -> {
			throw failureFactory.build(message);
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
	 * @since 2.0
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
	 * @since 2.0
	 */
	public static <A1, A2, A3> Function3<A1, A2, A3, Boolean> fromPredicate3(final Predicate3<? super A1, ? super A2, ? super A3> predicate) {
		assert null != predicate;
		
		return predicate::evaluate;
	}
	
	private Functions() {
		// Prevents instantiation.
	}
}
