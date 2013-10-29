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
package com.trazere.util.function;

import com.trazere.util.lang.Counter;
import com.trazere.util.type.Either;
import com.trazere.util.type.Maybe;

/**
 * The {@link RetryFunction1} interface defines one argument functions that are attempted multiple times in case of failure.
 * 
 * @param <T> Type of the argument values.
 * @param <S> Type of the success values.
 * @param <F> Type of the failure values.
 * @param <X> Type of the exceptions.
 */
public abstract class RetryFunction1<T, S, F, X extends Exception>
implements Function1<T, Either<S, F>, X> {
	@Override
	public Either<S, F> evaluate(final T value)
	throws X {
		final Counter failureCounter = new Counter();
		while (true) {
			// Try.
			final Either<S, F> result = evaluateStep(value, failureCounter.get());
			if (result.isLeft()) {
				// Success.
				return result;
			} else {
				// Failure.
				final Maybe<F> failure = handleFailure(result.asRight().getRight(), failureCounter.inc());
				if (failure.isSome()) {
					return Either.right(failure.asSome().getValue());
				}
			}
		}
	}
	
	/**
	 * Attempts to evaluate the receiver function.
	 * 
	 * @param value The argument value.
	 * @param failureCount The number of failures.
	 * @return The succes or failure result.
	 * @throws X
	 */
	protected abstract Either<S, F> evaluateStep(final T value, final int failureCount)
	throws X;
	
	/**
	 * Handles a failed attempt at evaluating the receiver function.
	 * 
	 * @param failure The failure result.
	 * @param failureCount The number of failures.
	 * @return Some failure value to end the evaluation with, or nothing to perform another evaluation attempt.
	 * @throws X
	 */
	protected abstract Maybe<F> handleFailure(final F failure, final int failureCount)
	throws X;
	
	/**
	 * Evaluates the given retry function.
	 * 
	 * @param <T> Type of the argument values.
	 * @param <S> Type of the success values.
	 * @param <F> Type of the failure values.
	 * @param <X> Type of the exceptions.
	 * @param function The function.
	 * @param value The argument value.
	 * @return The result.
	 * @throws F When the evaluation fails repeatedly.
	 * @throws X
	 */
	public static <T, S, F extends Throwable, X extends Exception> S evaluate(final RetryFunction1<T, S, F, X> function, final T value)
	throws F, X {
		final Either<S, F> result = function.evaluate(value);
		if (result.isLeft()) {
			// Success.
			return result.asLeft().getLeft();
		} else {
			// Failure.
			throw result.asRight().getRight();
		}
	}
}
