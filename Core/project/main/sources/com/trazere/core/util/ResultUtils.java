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
package com.trazere.core.util;

import com.trazere.core.functional.Functions;
import com.trazere.core.lang.ThrowableFactory;

/**
 * The {@link ResultUtils} class provides various utilities regarding {@link Result}s.
 */
public class ResultUtils {
	/**
	 * Gets the success value of the given result, or throws an exception cause by its failure.
	 * 
	 * @param <T> Type of the success value.
	 * @param result Result instance to read.
	 * @param failureFactory Factory of the failure.
	 * @return The success value.
	 * @throws RuntimeException An exceptioned caused the given result when it is a failure.
	 */
	public static <T> T get(final Result<T> result, final ThrowableFactory<? extends RuntimeException> failureFactory) {
		if (result.isSuccess()) {
			return result.asSuccess().getValue();
		} else {
			throw failureFactory.build(result.asFailure().getCause());
		}
	}
	
	/**
	 * Flattens the success value of the {@link Result} instance contained in the given {@link Result} instance.
	 *
	 * @param <T> Type of the success value.
	 * @param result {@link Result} instance containing the {@link Result} instance to flatten.
	 * @return A {@link Result} instance containing the flatten success value.
	 */
	public static <T> Result<T> flatten(final Result<? extends Result<? extends T>> result) {
		return result.flatMap(Functions.<Result<? extends T>>identity());
	}
	
	private ResultUtils() {
		// Prevent instantiation.
	}
}
