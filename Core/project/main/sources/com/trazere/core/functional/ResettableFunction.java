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

import com.trazere.core.lang.Releasable;
import com.trazere.core.util.Maybe;
import java.util.Set;

/**
 * The {@link ResettableFunction} interface defines memoized functions that can be re-evaluated.
 * 
 * @param <A> Type of the arguments.
 * @param <R> Type of the results.
 * @since 2.0
 */
public interface ResettableFunction<A, R>
extends MemoizedFunction<A, R>, Releasable {
	/**
	 * Gets the arguments whose evaluation has been memoized.
	 * 
	 * @return An unmodifiable set of the arguments.
	 * @since 2.0
	 */
	Set<A> memoizedArgs();
	
	/**
	 * Resets the evaluation of this function with the given argument, discarding the possibly memoized result.
	 * <p>
	 * The result will be computed (again) the next time this function is evaluated with the given argument.
	 * 
	 * @param arg Argument whose evaluation should be reset.
	 * @since 2.0
	 */
	void reset(final A arg);
	
	/**
	 * Resets the evaluation of this function with the given argument in a thread safe way, discarding the possibly memoized result.
	 * 
	 * @param arg Argument corresponding to the evaluation to reset.
	 * @see #reset(Object)
	 * @since 2.0
	 */
	default void synchronizedReset(final A arg) {
		synchronized (this) {
			reset(arg);
		}
	}
	
	/**
	 * Resets the evaluations of this function with the arguments accepted by the given filter, discarding the possibly memoized results.
	 * <p>
	 * The results will be computed (again) the next time this function is evaluated with the accepted arguments.
	 * 
	 * @param filter Predicate to use to filter the arguments whose evaluation should be reset.
	 * @since 2.0
	 */
	void reset(Predicate<? super A> filter);
	
	/**
	 * Resets all evaluations of this function, discarding the possibly memoized results.
	 * <p>
	 * The results will be computed (again) the next time this function is evaluated.
	 * 
	 * @since 2.0
	 */
	void resetAll();
	
	@Override
	default ResettableFunction<A, R> synchronized_() {
		final ResettableFunction<A, R> self = this;
		return new ResettableFunction<A, R>() {
			@Override
			public R evaluate(final A arg) {
				return self.synchronizedEvaluate(arg);
			}
			
			@Override
			public boolean isMemoized(final A arg) {
				return self.isMemoized(arg);
			}
			
			@Override
			public Maybe<R> probe(final A arg) {
				return self.probe(arg);
			}
			
			@Override
			public Set<A> memoizedArgs() {
				return self.memoizedArgs();
			}
			
			@Override
			public void reset(final A arg) {
				self.synchronizedReset(arg);
			}
			
			@Override
			public void reset(final Predicate<? super A> filter) {
				synchronized (self) {
					self.reset(filter);
				}
			}
			
			@Override
			public void resetAll() {
				synchronized (self) {
					self.resetAll();
				}
			}
		};
	}
	
	// Releasable.
	
	@Override
	default void release() {
		resetAll();
	}
}
