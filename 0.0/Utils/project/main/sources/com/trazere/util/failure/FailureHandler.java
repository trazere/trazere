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
package com.trazere.util.failure;

import com.trazere.util.type.Maybe;

/**
 * The {@link FailureHandler} interface defines handlers for failures.
 * 
 * @param <F> Type of the failures.
 * @param <X> Type of the exceptions.
 */
public interface FailureHandler<F, X extends Exception> {
	/**
	 * Handles a failed attempt at evaluating the receiver function.
	 */
	/**
	 * Handles the given failure.
	 * 
	 * @param failure Failure to handle.
	 * @param failureCount Number of failures.
	 * @return The extracted failure.
	 * @throws X On failure.
	 */
	public Maybe<? extends F> handleFailure(final F failure, final int failureCount)
	throws X;
}
