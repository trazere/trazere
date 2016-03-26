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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@link ObservableUtils} class provides various helpers regarding {@link Observable observables}.
 * 
 * @see Observable
 * @since 2.0
 */
public class ObservableUtils {
	/**
	 * Logger.
	 * 
	 * @since 2.0
	 */
	public static final Logger LOGGER = LoggerFactory.getLogger(ObservableUtils.class.getPackage().getName());
	
	/**
	 * Notifies the given observer that the given event has been raised.
	 * <p>
	 * This method protects the caller against observer failures. The subscription is cancelled when the observer fails.
	 * 
	 * @param <E> Type of the event.
	 * @param observer Reference to the observer to notify.
	 * @param event Raised event.
	 * @param logger Logger to use.
	 * @return <code>true</code> to hold the subscription corresponding to this notification, <code>false</code> to cancel it.
	 * @since 2.0
	 */
	public static <E> boolean notify(final Observer<? super E> observer, final E event, final Logger logger) {
		try {
			return observer.onEvent(event);
		} catch (final Exception exception) {
			// Log.
			logger.warn("Failed notifying observer \"" + observer + "\" with raised event \"" + event + "\"");
			
			// Cancel the subscription.
			return false;
		}
	}
	
	private ObservableUtils() {
		// Prevents instantiation.
	}
}
