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

/**
 * The {@link Function} interface defines functions.
 * <p>
 * TODO: compare to mathematical functions (partial vs total, stable results...)
 * 
 * @param <A> Type of the arguments.
 * @param <R> Type of the results.
 * @since 2.0
 */
@FunctionalInterface
public interface Function<A, R> {
	/**
	 * Evaluates this function with the given argument.
	 * 
	 * @param arg Argument to evaluate the function with.
	 * @return The result of the function evaluation.
	 * @since 2.0
	 */
	R evaluate(A arg);
	
	/**
	 * Evaluates this function with the given argument in a thread safe way.
	 * 
	 * @param arg Argument to evaluate the function with.
	 * @return The result of the function evaluation.
	 * @see #evaluate(Object)
	 * @since 2.0
	 */
	default R synchronizedEvaluate(final A arg) {
		synchronized (this) {
			return evaluate(arg);
		}
	}
	
	/**
	 * Composes this function with the given function.
	 * <p>
	 * TODO: detail function composition
	 *
	 * @param <CA> Type of the composition arguments.
	 * @param function Function to use to transform the arguments.
	 * @return The built function.
	 * @since 2.0
	 */
	default <CA> Function<CA, R> compose(final Function<? super CA, ? extends A> function) {
		assert null != function;
		
		final Function<A, R> self = this;
		return arg -> self.evaluate(function.evaluate(arg));
	}
	
	/**
	 * Transforms the results of this function using the given function.
	 *
	 * @param <TR> Type of the transformed results.
	 * @param function Function to use to transform the results.
	 * @return A function evaluating to the transformed results.
	 * @since 2.0
	 */
	default <TR> Function<A, TR> map(final Function<? super R, ? extends TR> function) {
		assert null != function;
		
		final Function<A, R> self = this;
		return arg -> function.evaluate(self.evaluate(arg));
	}
	
	/**
	 * Builds a memoized view of this function.
	 * 
	 * @return A memoized view of this function, or this function when it is memoized.
	 * @see MemoizedFunction
	 * @since 2.0
	 */
	default MemoizedFunction<A, R> memoized() {
		if (this instanceof MemoizedFunction) {
			return (MemoizedFunction<A, R>) this;
		} else {
			final Function<A, R> self = this;
			return new BaseMemoizedFunction<A, R>() {
				@Override
				protected R compute(final A arg) {
					return self.evaluate(arg);
				}
			};
		}
	}
	
	/**
	 * Builds a memoized, resettable view of this function.
	 * 
	 * @return A resettable view of the function, or this function when it is resettable.
	 * @see ResettableFunction
	 * @since 2.0
	 */
	default ResettableFunction<A, R> resettable() {
		if (this instanceof ResettableFunction) {
			return (ResettableFunction<A, R>) this;
		} else {
			final Function<A, R> self = this;
			return new BaseResettableFunction<A, R>() {
				@Override
				protected R compute(final A arg) {
					return self.evaluate(arg);
				}
			};
		}
	}
	
	/**
	 * Builds a synchronized view of this function.
	 * 
	 * @return The built function.
	 * @see #synchronizedEvaluate(Object)
	 * @since 2.0
	 */
	default Function<A, R> synchronized_() {
		final Function<A, R> self = this;
		return arg -> self.synchronizedEvaluate(arg);
	}
	
	/**
	 * Lifts this function as a Java 8 function.
	 * 
	 * @return The built Java 8 function.
	 * @since 2.0
	 */
	default java.util.function.Function<A, R> toFunction() {
		final Function<A, R> self = this;
		return t -> self.evaluate(t);
	}
}
