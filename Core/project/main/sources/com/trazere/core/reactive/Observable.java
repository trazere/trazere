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
import com.trazere.core.reference.MutableReference;
import com.trazere.core.util.Maybe;
import java.time.Duration;
import java.time.Instant;

/**
 * The {@link Observable} interface defines sources of events that can be observed.
 * <p>
 * {@link Observer Observers} can be subscribed to observables in order to listen to the raised events.
 * <p>
 * <b>Important:</b> In order to simplify their life cycle, the subscriptions are automatically cancelled when the observers are garbaged collected. As the
 * observables do not keep strong references to the observers, it is the responsability of the caller to reference them (or their corresponding
 * {@link ObserverSubscription subscription}) as long as necessary.
 * 
 * @param <E> Type of the events.
 * @see Observer
 * @see ObserverSubscription
 * @since 2.0
 */
public interface Observable<E> {
	/**
	 * Subscribes the given observer to this observable.
	 * <p>
	 * The observer will be notified with the events raised by this observable as long as the subscription holds.
	 * 
	 * @param observer Observer to subscribe.
	 * @return The resulting subcription.
	 * @since 2.0
	 */
	ObserverSubscription subscribe(Observer<? super E> observer);
	
	/**
	 * Waits for the next event raised by this observable.
	 * <p>
	 * This methods blocks until an event is raised by the observable.
	 *
	 * @return The raised event.
	 * @throws InterruptedException When the current thread is interrupted before or while waiting for an event. The <i>interrupted status</i> of the current
	 *         thread is cleared when this exception is thrown.
	 * @since 2.0
	 */
	default E waitEvent()
	throws InterruptedException {
		// Observe.
		final MutableReference<E> eventHolder = new MutableReference<>();
		final ObserverSubscription subscription = subscribe(new Observer<E>() {
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
						eventHolder.wait(10000L);
					}
				}
			}
		} finally {
			// Note: retains the subscription
			subscription.unsubscribe();
		}
	}
	
	/**
	 * Waits for the next event raised by this observable.
	 * <p>
	 * This methods blocks until an event is raised by the observable or the given timeout expires.
	 *
	 * @param timeout Delay during which the event should be waited for.
	 * @return The raised event, or nothing when no events are raised before the timeout.
	 * @throws InterruptedException When the current thread is interrupted before or while waiting for an event. The <i>interrupted status</i> of the current
	 *         thread is cleared when this exception is thrown.
	 * @since 2.0
	 */
	default Maybe<E> waitEvent(final Duration timeout)
	throws InterruptedException {
		final Instant deadline = Instant.now().plus(timeout);
		
		// Observe.
		final MutableReference<E> eventHolder = new MutableReference<>();
		final ObserverSubscription subscription = subscribe(new Observer<E>() {
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
					final Instant now = Instant.now();
					if (eventHolder.isSet() || now.compareTo(deadline) >= 0) {
						return eventHolder.asMaybe();
					} else {
						eventHolder.wait(Duration.between(now, deadline).toMillis());
					}
				}
			}
		} finally {
			// Note: retains the subscription
			subscription.unsubscribe();
		}
	}
	
	/**
	 * Builds a view of this observable that transforms the provided events using the given function.
	 *
	 * @param <TE> Type of the transformed events.
	 * @param function Function to use to transform the events.
	 * @return The built view.
	 * @since 2.0
	 */
	default <TE> Observable<TE> map(final Function<? super E, ? extends TE> function) {
		assert null != function;
		
		final Observable<E> self = this;
		return new Observable<TE>() {
			@Override
			public ObserverSubscription subscribe(final Observer<? super TE> observer) {
				return self.subscribe(value -> observer.onEvent(function.evaluate(value)));
			}
		};
	}
}
