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
package com.trazere.core.reactive;

import com.trazere.core.functional.Function;
import com.trazere.core.util.Maybe;

/**
 * The {@link FutureUtils} class provides various utilities regarding {@link Future futures}.
 * 
 * @see Future
 * @since 2.0
 */
public class FutureUtils {
	/**
	 * Transforms the value provided by the given future using the given function.
	 *
	 * @param <T> Type of the value.
	 * @param <RT> Type of the transformed value.
	 * @param future Future providing the elements to transform.
	 * @param function Function to use to transform the elements.
	 * @return An iterable providing the transformed elements.
	 * @since 1.0
	 */
	public static <T, RT> Future<RT> map(final Future<? extends T> future, final Function<? super T, ? extends RT> function) {
		assert null != future;
		assert null != function;
		
		return new Future<RT>() {
			@Override
			public ObserverSubscription subscribe(final Observer<? super RT> observer) {
				return future.subscribe((final T value) -> {
					return observer.onEvent(function.evaluate(value));
				});
			}
			
			@Override
			public boolean isAvailable() {
				return future.isAvailable();
			}
			
			@Override
			public Maybe<RT> get() {
				return future.get().map(function);
			}
		};
	}
	
	private FutureUtils() {
		// Prevents instantiation.
	}
}
