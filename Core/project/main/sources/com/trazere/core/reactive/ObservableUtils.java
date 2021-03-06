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

import com.trazere.core.lang.ThreadUtils;
import com.trazere.core.lang.ThrowableFactories;
import com.trazere.core.reference.MutableReference;
import com.trazere.core.util.Maybe;
import java.time.Duration;
import java.time.Instant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@link ObservableUtils} class provides various helpers regarding {@link Observable observables}.
 * 
 * @see Observable
 * @since 2.0
 */
public class ObservableUtils {
	/** Logger. */
	public static final Logger LOGGER = LoggerFactory.getLogger(ObservableUtils.class.getPackage().getName());
	
	// TODO: logger as arg
	/**
	 * Notifies the given observer that the given event has been raised.
	 * <p>
	 * This method protects the caller against observer failures. The subscription is cancelled when the observer fail.
	 * 
	 * @param <E> Type of the event.
	 * @param observer Reference to the observer to notify.
	 * @param event Raised event.
	 * @return <code>true</code> to hold the subscription corresponding to this notification, <code>false</code> to cancel it.
	 * @since 2.0
	 */
	public static <E> boolean notify(final Observer<? super E> observer, final E event) {
		try {
			return observer.onEvent(event);
		} catch (final Exception exception) {
			// Log.
			LOGGER.warn("Failed notifying observer \"" + observer + "\" with raised event \"" + event + "\"", exception);
			
			// Cancel the subscription.
			return false;
		}
	}
	
	/**
	 * Waits for the next event raised by the given observable.
	 * <p>
	 * This methods blocks until an event is raised by the observable.
	 *
	 * @param <E> Type of the event.
	 * @param observable Observable to observe.
	 * @return The raised event.
	 * @since 2.0
	 */
	public static <E> E wait(final Observable<? extends E> observable) {
		// Observe the future.
		final MutableReference<E> eventHolder = new MutableReference<>();
		final ObserverSubscription subscription = observable.subscribe(new Observer<E>() {
			@Override
			public boolean onEvent(final E event) {
				synchronized (eventHolder) {
					// Set the event.
					eventHolder.set(event);
					
					// Wake up the caller.
					eventHolder.notifyAll();
				}
				
				return false;
			}
		});
		try {
			// Wait for the event.
			while (true) {
				synchronized (eventHolder) {
					if (eventHolder.isSet()) {
						return eventHolder.get();
					} else {
						try {
							eventHolder.wait();
						} catch (final InterruptedException exception) {
							throw new RuntimeException(exception);
						}
					}
				}
			}
		} finally {
			// Note: avoid garbage collection and in case of error.
			subscription.unsubscribe();
		}
	}
	
	/**
	 * Waits for the next event raised by the given observable.
	 * <p>
	 * This methods blocks until an event is raised by the observable or the given timeout expires.
	 *
	 * @param <E> Type of the event.
	 * @param observable Observable to observe.
	 * @param timeout Delay during which the value should be waited for.
	 * @return The raised event, or nothing when no event is raised during the delay.
	 * @since 2.0
	 */
	public static <E> Maybe<E> wait(final Observable<? extends E> observable, final Duration timeout) {
		final Instant deadline = Instant.now().plus(timeout);
		
		// Observe the future.
		final MutableReference<E> eventHolder = new MutableReference<>();
		final ObserverSubscription subscription = observable.subscribe(new Observer<E>() {
			@Override
			public boolean onEvent(final E event) {
				synchronized (eventHolder) {
					// Set the value.
					eventHolder.set(event);
					
					// Wake up the caller.
					eventHolder.notifyAll();
				}
				
				return false;
			}
		});
		try {
			// Wait for the value.
			while (true) {
				synchronized (eventHolder) {
					final Instant now = Instant.now();
					if (eventHolder.isSet() || now.compareTo(deadline) >= 0) {
						return eventHolder.asMaybe();
					} else {
						ThreadUtils.wait(eventHolder, Duration.between(now, deadline), ThrowableFactories.RUNTIME_EXCEPTION);
					}
				}
			}
		} finally {
			// Note: avoid garbage collection and in case of error.
			subscription.unsubscribe();
		}
	}
	
	private ObservableUtils() {
		// Prevents instantiation.
	}
}
