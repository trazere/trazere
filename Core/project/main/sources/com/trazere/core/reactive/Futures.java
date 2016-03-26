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

import com.trazere.core.util.Maybe;
import org.slf4j.Logger;

/**
 * The {@link Futures} class provides various factories of {@link Future futures}.
 * 
 * @see Future
 * @since 2.0
 */
public class Futures {
	/**
	 * Builds a future providing the given value.
	 * 
	 * @param <T> Type of the value.
	 * @param value Value of the future.
	 * @return The built future.
	 * @since 2.0
	 */
	public static <T> Future<T> simple(final T value) {
		return simple(value, ObservableUtils.LOGGER);
	}
	
	/**
	 * Builds a future providing the given value.
	 * 
	 * @param <T> Type of the value.
	 * @param value Value of the future.
	 * @param logger Logger to use.
	 * @return The built future.
	 * @since 2.0
	 */
	public static <T> Future<T> simple(final T value, final Logger logger) {
		assert null != logger;
		
		return new Future<T>() {
			@Override
			public ObserverSubscription subscribe(final Observer<? super T> observer) {
				// Notify immediately.
				ObservableUtils.notify(observer, value, logger);
				
				// Build an nop subscription.
				return ObserverSubscriptions.nop();
			}
			
			@Override
			public boolean isAvailable() {
				return true;
			}
			
			@Override
			public Maybe<T> get() {
				return Maybe.some(value);
			}
		};
	}
	
	private Futures() {
		// Prevents instantiation.
	}
}
