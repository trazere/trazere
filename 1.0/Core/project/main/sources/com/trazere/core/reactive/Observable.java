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
 * @since 1.0
 */
public interface Observable<E> {
	/**
	 * Subscribes the given observer to this observable.
	 * <p>
	 * The observer will be notified with the events raised by this observable as long as the subscription holds.
	 * 
	 * @param observer Observer to subscribe.
	 * @return The resulting subcription.
	 * @since 1.0
	 */
	ObserverSubscription subscribe(Observer<? super E> observer);
}
