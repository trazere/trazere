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

import com.trazere.core.imperative.IntCounter;
import com.trazere.core.lang.ThrowableFactory;
import com.trazere.core.util.FailureHandler;
import com.trazere.core.util.Maybe;
import com.trazere.core.util.Result;
import com.trazere.core.util.ResultUtils;

/**
 * The {@link RetryThunk} interface defines abstract computations of some value whose evaluation is attempted multiple times in case of failure.
 * 
 * @param <T> Type of the value.
 * @see Thunk
 */
public abstract class RetryThunk<T>
implements Thunk<Result<T>>, FailureHandler<T> {
	@Override
	public Result<T> evaluate() {
		final IntCounter failureCounter = new IntCounter();
		while (true) {
			// Try.
			final Result<T> result = attemptEvaluate(failureCounter.get());
			if (result.isSuccess()) {
				// Success.
				return result;
			} else {
				// Failure.
				final Maybe<? extends Result<T>> failureHandling = handleFailure(result.asFailure().getCause(), failureCounter.inc());
				if (failureHandling.isSome()) {
					return failureHandling.asSome().getValue();
				}
			}
		}
	}
	
	/**
	 * Attempts to evaluate the receiver thunk.
	 * 
	 * @param failureCount Number of failures.
	 * @return The result of the evaluation attempt.
	 */
	protected abstract Result<T> attemptEvaluate(int failureCount);
	
	/**
	 * Evaluates the given retry thunk.
	 * 
	 * @param <T> Type of the value.
	 * @param <X> Type of the failure exceptions.
	 * @param thunk Thunk to evaluate.
	 * @param failureFactory Factory of the failure.
	 * @return The value of the thunk.
	 * @throws X When the evaluation fails repeatedly.
	 */
	public static <T, X extends Exception> T evaluate(final RetryThunk<T> thunk, final ThrowableFactory<? extends X> failureFactory)
	throws X {
		return ResultUtils.get(thunk.evaluate(), failureFactory);
	}
}
