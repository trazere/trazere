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
package com.trazere.core.util;

/**
 * The {@link FailureHandler} interface defines handlers for failures.
 * 
 * @param <T> Type of the success value.
 * @since 2.0
 */
@FunctionalInterface
public interface FailureHandler<T> {
	/**
	 * Handles the given failure.
	 * 
	 * @param failure Failure to handle.
	 * @param failureCount Number of failures.
	 * @return The handling result.
	 * @since 2.0
	 */
	public Maybe<? extends Result<T>> handleFailure(final Throwable failure, final int failureCount);
}
