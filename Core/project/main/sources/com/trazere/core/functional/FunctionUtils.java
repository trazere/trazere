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

import com.trazere.core.util.Maybe;
import com.trazere.core.util.Tuple2;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

/**
 * The {@link FunctionUtils} class provides various utilities regarding {@link Function functions}.
 * 
 * @see Function
 * @see ResettableFunction
 * @see Function2
 * @see Function3
 * @since 1.0
 */
public class FunctionUtils {
	/**
	 * Builds a function corresponding to the composition of the given functions (g . f).
	 *
	 * @param <A> Type of the arguments.
	 * @param <I> Type of the intermediate values
	 * @param <R> Type of the results.
	 * @param g Outer function.
	 * @param f Inner function.
	 * @return The built function.
	 * @since 1.0
	 */
	public static <A, I, R> Function<A, R> compose(final Function<? super I, ? extends R> g, final Function<? super A, ? extends I> f) {
		assert null != f;
		assert null != g;
		
		return arg -> g.evaluate(f.evaluate(arg));
	}
	
	/**
	 * Filters the given function using the given filter.
	 * 
	 * @param <A> Type of the arguments.
	 * @param <R> Type of the results.
	 * @param function Function to filter.
	 * @param filter Predicate to use to filter the results.
	 * @return The built function.
	 * @since 1.0
	 */
	public static <A, R> Function<A, Maybe<R>> filter(final Function<? super A, ? extends R> function, final Predicate<? super R> filter) {
		assert null != function;
		assert null != filter;
		
		return arg -> Maybe.<R>some(function.evaluate(arg)).filter(filter);
	}
	
	/**
	 * Transforms the given function using the given function.
	 * <p>
	 * This method is equivalent to function composition.
	 *
	 * @param <A> Type of the arguments.
	 * @param <R> Type of the results.
	 * @param
	 * 		<TR>
	 *        Type of the transformed results.
	 * @param function Function to transform.
	 * @param mapFunction Function to use to transform the results.
	 * @return The built function.
	 * @since 1.0
	 */
	public static <A, R, TR> Function<A, TR> map(final Function<? super A, ? extends R> function, final Function<? super R, ? extends TR> mapFunction) {
		return compose(mapFunction, function);
	}
	
	/**
	 * Builds a memoized view of the the given function.
	 * 
	 * @param <A> Type of the arguments.
	 * @param <R> Type of the results.
	 * @param function Function to memoize.
	 * @return The built thunk.
	 * @since 1.0
	 */
	public static <A, R> MemoizedFunction<A, R> memoized(final Function<? super A, ? extends R> function) {
		assert null != function;
		
		return new BaseMemoizedFunction<A, R>() {
			@Override
			protected R compute(final A arg) {
				return function.evaluate(arg);
			}
		};
	}
	
	/**
	 * Builds a function that memoizes the evaluations of the given function and can be reset.
	 * 
	 * @param <A> Type of the arguments.
	 * @param <R> Type of the results.
	 * @param function Function to memoize.
	 * @return The build function.
	 * @since 1.0
	 */
	public static <A, R> ResettableFunction<A, R> resettable(final Function<? super A, ? extends R> function) {
		assert null != function;
		
		return new ResettableFunction<A, R>() {
			@Override
			protected R compute(final A arg) {
				return function.evaluate(arg);
			}
		};
	}
	
	/**
	 * Builds a function that evaluates to the given function in a thread safe way.
	 * 
	 * @param <A> Type of the arguments.
	 * @param <R> Type of the results.
	 * @param function Function to synchronize.
	 * @return The built function.
	 * @since 1.0
	 */
	public static <A, R> Function<A, R> synchronized_(final Function<? super A, ? extends R> function) {
		assert null != function;
		
		return arg -> synchronizedEvaluate(function, arg);
	}
	
	/**
	 * Curries the given function which takes pairs of elements into a function that takes two arguments.
	 *
	 * @param <A1> Type of the first element of the argument pairs.
	 * @param <A2> Type of the second element of the argument pairs.
	 * @param <R> Type of the results.
	 * @param function Function to curry.
	 * @return The built function.
	 * @since 1.0
	 */
	public static <A1, A2, R> Function2<A1, A2, R> curry(final Function<? super Tuple2<A1, A2>, ? extends R> function) {
		assert null != function;
		
		return (arg1, arg2) -> function.evaluate(new Tuple2<>(arg1, arg2));
	}
	
	// TODO: add curry for Tuple3 ?
	
	/**
	 * Builds a Java 8 function that lifts the given function.
	 * 
	 * @param <A> Type of the arguments.
	 * @param <R> Type of the results.
	 * @param function Function to lift.
	 * @return The built Java 8 function.
	 * @since 1.0
	 */
	public static <A, R> java.util.function.Function<A, R> toFunction(final Function<? super A, ? extends R> function) {
		assert null != function;
		
		return t -> function.evaluate(t);
	}
	
	/**
	 * Builds an unary operator that lifts the given function.
	 * 
	 * @param <V> Type of the arguments and results.
	 * @param function Function to lift.
	 * @return The built unary operator.
	 * @since 1.0
	 */
	public static <V> UnaryOperator<V> toUnaryOperator(final Function<? super V, ? extends V> function) {
		assert null != function;
		
		return t -> function.evaluate(t);
	}
	
	/**
	 * Evaluates the given function in a thread safe way.
	 * 
	 * @param <A> Type of the arguments.
	 * @param <R> Type of the results.
	 * @param function Thunk to evaluate.
	 * @param arg Argument to evaluate the function with.
	 * @return The result of the function evaluation.
	 * @since 1.0
	 */
	public static <A, R> R synchronizedEvaluate(final Function<? super A, ? extends R> function, final A arg) {
		synchronized (function) {
			return function.evaluate(arg);
		}
	}
	
	/**
	 * Resets the given function in a thread safe way.
	 * 
	 * @param <A> Type of the arguments.
	 * @param arg Argument corresponding to the evaluation to reset.
	 * @param function Function to reset.
	 * @since 1.0
	 */
	public static <A> void synchronizedReset(final ResettableFunction<? super A, ?> function, final A arg) {
		synchronized (function) {
			function.reset(arg);
		}
	}
	
	/**
	 * Filters the given two arguments function using the given filter.
	 * 
	 * @param <A1> Type of the first arguments.
	 * @param <A2> Type of the second arguments.
	 * @param <R> Type of the results.
	 * @param function Function to filter.
	 * @param filter Predicate to use to filter the results.
	 * @return The built function.
	 * @since 1.0
	 */
	public static <A1, A2, R> Function2<A1, A2, Maybe<R>> filter2(final Function2<? super A1, ? super A2, ? extends R> function, final Predicate<? super R> filter) {
		assert null != function;
		assert null != filter;
		
		return (arg1, arg2) -> Maybe.<R>some(function.evaluate(arg1, arg2)).filter(filter);
	}
	
	/**
	 * Transforms the given two arguments function using the given function.
	 * <p>
	 * This method is equivalent to function composition.
	 *
	 * @param <A1> Type of the first arguments.
	 * @param <A2> Type of the second arguments.
	 * @param <R> Type of the results.
	 * @param
	 * 		<TR>
	 *        Type of the transformed results.
	 * @param function Function to transform.
	 * @param mapFunction Function to use to transform the results.
	 * @return The built function.
	 * @since 1.0
	 */
	public static <A1, A2, R, TR> Function2<A1, A2, TR> map2(final Function2<? super A1, ? super A2, ? extends R> function, final Function<? super R, ? extends TR> mapFunction) {
		assert null != function;
		assert null != mapFunction;
		
		return (arg1, arg2) -> mapFunction.evaluate(function.evaluate(arg1, arg2));
	}
	
	/**
	 * Uncurries the given function which takes two arguments into a function that takes pairs of elements.
	 *
	 * @param <A1> Type of the first arguments.
	 * @param <A2> Type of the second arguments.
	 * @param <R> Type of the results.
	 * @param function Function to uncurry.
	 * @return The built function.
	 * @since 1.0
	 */
	public static <A1, A2, R> Function<Tuple2<A1, A2>, R> uncurry(final Function2<? super A1, ? super A2, ? extends R> function) {
		assert null != function;
		
		return (arg) -> function.evaluate(arg.get1(), arg.get2());
	}
	
	/**
	 * Builds a bi-function that lifts the given two arguments function.
	 * 
	 * @param <A1> Type of the first arguments.
	 * @param <A2> Type of the second arguments.
	 * @param <R> Type of the results.
	 * @param function Function to lift.
	 * @return The built bi-function.
	 * @since 1.0
	 */
	public static <A1, A2, R> BiFunction<A1, A2, R> toBiFunction(final Function2<? super A1, ? super A2, ? extends R> function) {
		assert null != function;
		
		return (t, u) -> function.evaluate(t, u);
	}
	
	/**
	 * Builds a binary operator that lifts the given two arguments function.
	 * 
	 * @param <V> Type of the arguments and results.
	 * @param function Function to lift.
	 * @return The built binary operator.
	 * @since 1.0
	 */
	public static <V> BinaryOperator<V> toBinaryOperator(final Function2<? super V, ? super V, ? extends V> function) {
		assert null != function;
		
		return (t, u) -> function.evaluate(t, u);
	}
	
	/**
	 * Filters the given three arguments function using the given filter.
	 * 
	 * @param <A1> Type of the first arguments.
	 * @param <A2> Type of the second arguments.
	 * @param <A3> Type of the third arguments.
	 * @param <R> Type of the results.
	 * @param function Function to filter.
	 * @param filter Predicate to use to filter the results.
	 * @return The built function.
	 * @since 1.0
	 */
	public static <A1, A2, A3, R> Function3<A1, A2, A3, Maybe<R>> filter3(final Function3<? super A1, ? super A2, ? super A3, ? extends R> function, final Predicate<? super R> filter) {
		assert null != function;
		assert null != filter;
		
		return (arg1, arg2, arg3) -> Maybe.<R>some(function.evaluate(arg1, arg2, arg3)).filter(filter);
	}
	
	/**
	 * Transforms the given three arguments function using the given function.
	 * <p>
	 * This method is equivalent to function composition.
	 *
	 * @param <A1> Type of the first arguments.
	 * @param <A2> Type of the second arguments.
	 * @param <A3> Type of the third arguments.
	 * @param <R> Type of the results.
	 * @param
	 * 		<TR>
	 *        Type of the transformed results.
	 * @param function Function to transform.
	 * @param mapFunction Function to use to transform the results.
	 * @return The built function.
	 * @since 1.0
	 */
	public static <A1, A2, A3, R, TR> Function3<A1, A2, A3, TR> map3(final Function3<? super A1, ? super A2, ? super A3, ? extends R> function, final Function<? super R, ? extends TR> mapFunction) {
		assert null != function;
		assert null != mapFunction;
		
		return (arg1, arg2, arg3) -> mapFunction.evaluate(function.evaluate(arg1, arg2, arg3));
	}
	
	// TODO: add uncurry for Function3 ?
	
	private FunctionUtils() {
		// Prevent instantiation.
	}
}
