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

import com.trazere.core.util.Maybe;

/**
 * The {@link FunctionUtils} class provides various utilities regarding functions.
 */
public class FunctionUtils {
	/**
	 * Filters the given function using the given filter.
	 * 
	 * @param <A> Type of the arguments.
	 * @param <R> Type of the results.
	 * @param function Function to filter.
	 * @param filter Predicate to use to filter the results.
	 * @return The built function.
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
	 * @param <TR> Type of the transformed results.
	 * @param function Function to transform.
	 * @param mapFunction Function to use to transform the results.
	 * @return The built function.
	 */
	public static <A, R, TR> Function<A, TR> map(final Function<? super A, ? extends R> function, final Function<? super R, ? extends TR> mapFunction) {
		assert null != function;
		assert null != mapFunction;
		
		return arg -> mapFunction.evaluate(function.evaluate(arg));
	}
	
	/**
	 * Builds a function that memoizes the evaluations of the given function.
	 * 
	 * @param <A> Type of the arguments.
	 * @param <R> Type of the results.
	 * @param function Function to memoize.
	 * @return The built thunk.
	 */
	public static <A, R> MemoizedFunction<A, R> memoize(final Function<? super A, ? extends R> function) {
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
	 * Builds a synchronized function that evaluates to the given function.
	 * 
	 * @param <A> Type of the arguments.
	 * @param <R> Type of the results.
	 * @param function Function to synchronize.
	 * @return The built function.
	 */
	public static <A, R> Function<A, R> synchronize(final Function<? super A, ? extends R> function) {
		assert null != function;
		
		return arg -> synchronizedEvaluate(function, arg);
	}
	
	/**
	 * Evaluates the given function in a thread safe way.
	 * 
	 * @param <A> Type of the arguments.
	 * @param <R> Type of the results.
	 * @param function Thunk to evaluate.
	 * @param arg Argument to evaluate the function with.
	 * @return The result of the function evaluation.
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
	 * @param <TR> Type of the transformed results.
	 * @param function Function to transform.
	 * @param mapFunction Function to use to transform the results.
	 * @return The built function.
	 */
	public static <A1, A2, R, TR> Function2<A1, A2, TR> map2(final Function2<? super A1, ? super A2, ? extends R> function, final Function<? super R, ? extends TR> mapFunction) {
		assert null != function;
		assert null != mapFunction;
		
		return (arg1, arg2) -> mapFunction.evaluate(function.evaluate(arg1, arg2));
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
	 * @param <TR> Type of the transformed results.
	 * @param function Function to transform.
	 * @param mapFunction Function to use to transform the results.
	 * @return The built function.
	 */
	public static <A1, A2, A3, R, TR> Function3<A1, A2, A3, TR> map3(final Function3<? super A1, ? super A2, ? super A3, ? extends R> function, final Function<? super R, ? extends TR> mapFunction) {
		assert null != function;
		assert null != mapFunction;
		
		return (arg1, arg2, arg3) -> mapFunction.evaluate(function.evaluate(arg1, arg2, arg3));
	}
	
	private FunctionUtils() {
		// Prevent instantiation.
	}
}
