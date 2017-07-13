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

import com.trazere.core.util.Tuple2;
import java.util.function.Function;

/**
 * The {@link FunctionUtils} class provides various utilities regarding {@link Function functions}.
 * 
 * @see Function
 * @see ResettableFunction
 * @see Function2
 * @see Function3
 * @since 2.0
 */
public class FunctionUtils {
	/**
	 * Curries the given function which takes pairs of elements into a function that takes two arguments.
	 *
	 * @param <A1> Type of the first element of the argument pairs.
	 * @param <A2> Type of the second element of the argument pairs.
	 * @param <R> Type of the results.
	 * @param function Function to curry.
	 * @return The built function.
	 * @since 2.0
	 */
	public static <A1, A2, R> Function2<A1, A2, R> curry2(final Function<? super Tuple2<A1, A2>, ? extends R> function) {
		assert null != function;
		
		return (arg1, arg2) -> function.evaluate(new Tuple2<>(arg1, arg2));
	}
	
	private FunctionUtils() {
		// Prevent instantiation.
	}
}
