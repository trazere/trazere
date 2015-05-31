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
package com.trazere.util.closure;

/**
 * The {@link ClosureUtils} class provides helpers regarding the closures.
 * 
 * @deprecated Use core.
 */
@Deprecated
public class ClosureUtils {
	/**
	 * Evaluates the given closure in a thread safe way.
	 * 
	 * @param <T> Type of the value.
	 * @param <X> Type of the exceptions.
	 * @param closure The closure.
	 * @return The value of the closure.
	 * @throws X When the evaluation of the closure fails.
	 * @deprecated Use {@link com.trazere.core.functional.ThunkUtils#synchronizedEvaluate(com.trazere.core.functional.Thunk)}.
	 */
	@Deprecated
	public static <T, X extends Exception> T synchronizedEvaluate(final Closure<T, ? extends X> closure)
	throws X {
		assert null != closure;
		
		synchronized (closure) {
			return closure.evaluate();
		}
	}
	
	/**
	 * Resets the given closure in a thread safe way.
	 * 
	 * @param closure The closure.
	 * @deprecated Use {@link com.trazere.core.functional.ThunkUtils#synchronizedReset(com.trazere.core.functional.ResettableThunk)}.
	 */
	@Deprecated
	public static void synchronizedReset(final ResettableClosure<?, ?> closure) {
		assert null != closure;
		
		synchronized (closure) {
			closure.reset();
		}
	}
	
	private ClosureUtils() {
		// Prevents instantiation.
	}
}
