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

/**
 * The {@link MemoizedFunction} interface defines functions whose evaluations are memoized.
 * <p>
 * After they have been computed, the results of memoized function evaluations are stored so that they can by returned by subsequent evaluations without
 * additional computations. Memoized functions simulate the call-by-need evaluation strategy whereas non-memoized functions simulate the call-by-name evaluation
 * strategy.
 * 
 * @param <A> Type of the arguments.
 * @param <R> Type of the results.
 * @since 1.0
 */
public interface MemoizedFunction<A, R>
extends Function<A, R> {
	/**
	 * Indicates whether the evaluation of this function with the given argument is memoized.
	 * 
	 * @param arg Argument whose memoized evaluation should be tested.
	 * @return <code>true</code> when the evaluation is memoized, <code>false</code> otherwise.
	 * @since 1.0
	 */
	boolean isMemoized(A arg);
	
	/**
	 * Gets the memoized result of the evaluation of this function with the given argument.
	 * 
	 * @param arg Argument whose memoized evaluation should be read.
	 * @return The memoized evaluation result, or nothing when the evaluation is not memoized.
	 * @since 1.0
	 */
	Maybe<R> get(A arg);
}
