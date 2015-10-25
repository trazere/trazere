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

import com.trazere.core.functional.Predicate;

/**
 * The {@link ObserverUtils} class provides various utilities regarding {@link Observer observers}.
 * 
 * @see Observer
 * @since 2.0
 */
public class ObserverUtils {
	/**
	 * Derives an observer that handles a single event and unsubscribes.
	 * 
	 * @param <E> Type of the events.
	 * @param observer Observer to derive.
	 * @return The built observer.
	 * @since 2.0
	 */
	public static <E> Observer<E> once(final Observer<? super E> observer) {
		assert null != observer;
		
		return new Observer<E>() {
			@Override
			public boolean onEvent(final E event) {
				observer.onEvent(event);
				return false;
			}
		};
	}
	
	/**
	 * Derives an observer that handles the events as long as the given condition holds.
	 * 
	 * @param <E> Type of the events.
	 * @param observer Observer to derive.
	 * @param condition Handling condition.
	 * @return The built observer.
	 * @since 2.0
	 */
	public static <E> Observer<E> while_(final Observer<? super E> observer, final Predicate<? super E> condition) {
		assert null != observer;
		assert null != condition;
		
		return new Observer<E>() {
			@Override
			public boolean onEvent(final E event) {
				if (condition.evaluate(event)) {
					return observer.onEvent(event);
				} else {
					return false;
				}
			}
		};
	}
	
	/**
	 * Derives an observer that filters the handled events.
	 * 
	 * @param <E> Type of the events.
	 * @param observer Observer to derive.
	 * @param filter Filter of the events to handle.
	 * @return The built observer.
	 * @since 2.0
	 */
	public static <E> Observer<E> filter(final Observer<? super E> observer, final Predicate<? super E> filter) {
		assert null != observer;
		assert null != filter;
		
		return new Observer<E>() {
			@Override
			public boolean onEvent(final E event) {
				if (filter.evaluate(event)) {
					return observer.onEvent(event);
				} else {
					return true;
				}
			}
		};
	}
	
	private ObserverUtils() {
		// Prevent instantiation.
	}
}
