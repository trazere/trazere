package com.trazere.util.function;

import com.trazere.util.lang.Counter;
import com.trazere.util.type.Either;
import com.trazere.util.type.Maybe;

/**
 * The {@link RetryFunction0} interface defines zero arguments functions that are attempted multiple times in case of failure.
 * 
 * @param <S> Type of the success values.
 * @param <F> Type of the failure values.
 * @param <X> Type of the exceptions.
 */
public abstract class RetryFunction0<S, F, X extends Exception>
implements Function0<Either<S, F>, X> {
	@Override
	public Either<S, F> evaluate()
	throws X {
		final Counter failureCounter = new Counter();
		while (true) {
			// Try.
			final Either<S, F> result = evaluateStep(failureCounter.get());
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
	 * @param failureCount The number of failures.
	 * @return The succes or failure result.
	 * @throws X
	 */
	protected abstract Either<S, F> evaluateStep(final int failureCount)
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
	 * @param <S> Type of the success values.
	 * @param <F> Type of the failure values.
	 * @param <X> Type of the exceptions.
	 * @param function The function.
	 * @return The result.
	 * @throws F
	 * @throws X
	 */
	public static <S, F extends Exception, X extends Exception> S evaluate(final RetryFunction0<S, F, X> function)
	throws F, X {
		final Either<S, F> result = function.evaluate();
		if (result.isLeft()) {
			// Success.
			return result.asLeft().getLeft();
		} else {
			// Failure.
			throw result.asRight().getRight();
		}
	}
}
