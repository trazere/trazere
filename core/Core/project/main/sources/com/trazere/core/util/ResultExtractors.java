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
package com.trazere.core.util;

import com.trazere.core.functional.Function;

/**
 * The {@link ResultExtractors} class provides various extractors related to {@link Result}s.
 */
public class ResultExtractors {
	/**
	 * Builds an extractor of success values.
	 * 
	 * @param <T> Type of the success values.
	 * @return The built extractor.
	 */
	@SuppressWarnings("unchecked")
	public static <T> Function<Result<? extends T>, Maybe<T>> getSuccess() {
		return (Function<Result<? extends T>, Maybe<T>>) GET_SUCCESS;
	}
	
	private static Function<? extends Result<?>, ? extends Maybe<?>> GET_SUCCESS = Result::getSuccess;
	
	/**
	 * Builds an extractor of causes of failures.
	 * 
	 * @param <T> Type of the success values.
	 * @return The built extractor.
	 */
	@SuppressWarnings("unchecked")
	public static <T> Function<Result<? extends T>, Maybe<Throwable>> getFailure() {
		return (Function<Result<? extends T>, Maybe<Throwable>>) GET_FAILURE;
	}
	
	private static Function<? extends Result<?>, Maybe<Throwable>> GET_FAILURE = Result::getFailure;
	
	private ResultExtractors() {
		// Prevent instantiation.
	}
}
