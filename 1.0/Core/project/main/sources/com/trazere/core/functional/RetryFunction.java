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

import com.trazere.core.imperative.IntCounter;
import com.trazere.core.lang.ThrowableFactory;
import com.trazere.core.util.FailureHandler;
import com.trazere.core.util.Maybe;
import com.trazere.core.util.Result;
import com.trazere.core.util.ResultUtils;

/**
 * The {@link RetryFunction} interface defines one argument functions whose evaluation is attempted multiple times in case of failure.
 * 
 * @param <A> Type of the arguments.
 * @param <R> Type of the results.
 * @see Function
 * @since 1.0
 */
public abstract class RetryFunction<A, R>
implements Function<A, Result<R>>, FailureHandler<R> {
	@Override
	public Result<R> evaluate(final A value) {
		final IntCounter failureCounter = new IntCounter();
		while (true) {
			// Try.
			final Result<R> result = attemptEvaluate(value, failureCounter.get());
			if (result.isSuccess()) {
				// Success.
				return result;
			} else {
				// Failure.
				final Maybe<? extends Result<R>> failureHandling = handleFailure(result.asFailure().getCause(), failureCounter.inc());
				if (failureHandling.isSome()) {
					return failureHandling.asSome().getValue();
				}
			}
		}
	}
	
	/**
	 * Attempts to evaluate this function.
	 * 
	 * @param arg Argument to evaluate the function with.
	 * @param failureCount Number of failures.
	 * @return The result of the evaluation attempt.
	 * @since 1.0
	 */
	protected abstract Result<R> attemptEvaluate(A arg, int failureCount);
	
	/**
	 * Evaluates the given retry function.
	 * 
	 * @param <A> Type of the arguments.
	 * @param <R> Type of the results.
	 * @param function Function to evaluate.
	 * @param arg Argument to evaluate the function with.
	 * @param failureFactory Factory of the failure.
	 * @return The result.
	 * @throws RuntimeException When the evaluation fails repeatedly.
	 * @since 1.0
	 */
	public static <A, R> R evaluate(final RetryFunction<A, R> function, final A arg, final ThrowableFactory<? extends RuntimeException> failureFactory) {
		return ResultUtils.get(function.evaluate(arg), failureFactory);
	}
}
