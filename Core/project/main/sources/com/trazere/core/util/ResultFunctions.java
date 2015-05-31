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
package com.trazere.core.util;

import com.trazere.core.functional.Function;

/**
 * The {@link ResultFunctions} class provides various factories of {@link Function functions} related to {@link Result results}.
 * 
 * @see Function
 * @see Result
 * @since 1.0
 */
public class ResultFunctions {
	/**
	 * Builds a function that builds success.
	 * 
	 * @param <T> Type of the success values.
	 * @return The built function.
	 * @since 1.0
	 */
	@SuppressWarnings("unchecked")
	public static <T> Function<T, Result<T>> success() {
		return (Function<T, Result<T>>) SUCCESS;
	}
	
	private static final Function<?, ? extends Result<?>> SUCCESS = Result::success;
	
	/**
	 * Builds a function that builds failures.
	 * 
	 * @param <T> Type of the success values.
	 * @return The built function.
	 * @since 1.0
	 */
	@SuppressWarnings("unchecked")
	public static <T> Function<Throwable, Result<T>> failure() {
		return (Function<Throwable, Result<T>>) FAILURE;
	}
	
	private static final Function<Throwable, ? extends Result<?>> FAILURE = Result::failure;
	
	/**
	 * Builds a function that maps the success value of {@link Result} instances using the given function.
	 * 
	 * @param <T> Type of the success values.
	 * @param <R> Type of the mapped success values.
	 * @param function Function to use to transform the success values.
	 * @return The built function.
	 * @see Result#map(Function)
	 * @since 1.0
	 */
	public static <T, R> Function<Result<? extends T>, Result<R>> map(final Function<? super T, ? extends R> function) {
		assert null != function;
		
		return result -> result.map(function);
	}
	
	/**
	 * Builds a function that transforms anf flattens the success value of {@link Result} instances using the given function.
	 * 
	 * @param <T> Type of the success values.
	 * @param <R> Type of the mapped success values.
	 * @param function Function to use to transform the success values.
	 * @return The built function.
	 * @see Result#flatMap(Function)
	 * @since 1.0
	 */
	public static <T, R> Function<Result<? extends T>, Result<R>> flatMap(final Function<? super T, ? extends Result<? extends R>> function) {
		assert null != function;
		
		return result -> result.flatMap(function);
	}
	
	private ResultFunctions() {
		// Prevent instantiation.
	}
}
