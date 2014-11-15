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

/**
 * The {@link ResultUtils} class provides various utilities regarding {@link Result}s.
 */
public class ResultUtils {
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
