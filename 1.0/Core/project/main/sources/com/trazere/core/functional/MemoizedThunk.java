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
 * The {@link MemoizedThunk} interface defines abstract, memoized computations of some value.
 * <p>
 * After it has been computed, the value of the memoized thunks is stored so that it can be returned by subsequent evaluations without additional computations.
 * Memoized thunks simulate the call-by-need evaluation strategy whereas non-memoized thunks simulate the call-by-name evaluation strategy.
 * 
 * @param <T> Type of the value.
 * @since 1.0
 */
public interface MemoizedThunk<T>
extends Thunk<T> {
	/**
	 * Indicates whether the value of this thunk has already been evaluated and memoized.
	 * 
	 * @return <code>true</code> when the value has been evaluated and memoized, <code>false</code> otherwise.
	 * @since 1.0
	 */
	boolean isMemoized();
	
	/**
	 * Gets the memoized value of this thunk without evaluation.
	 * 
	 * @return The memoized value, or nothing when the value has not been evaluated and memoized yet.
	 * @since 1.0
	 */
	Maybe<T> get();
}
