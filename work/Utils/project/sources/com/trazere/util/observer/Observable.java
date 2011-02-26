/*
 *  Copyright 2006-2011 Julien Dufour
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
package com.trazere.util.observer;

import com.trazere.util.function.Predicate1;

/**
 * The {@link Observable} interface defines event sources which can be observed.
 * <p>
 * {@link Observer Observers} can be subscribed to observables in order to listen to the raised events.
 * <p>
 * <b>Important:</b> In order to simplify their life cycle, the subscriptions are automatically canceled when the observers are garbaged collected. As the
 * observables do not keep strong references to the observers, it is the responsability of the caller to reference them (or their corresponding
 * {@link ObserverSubscription subscription}) as long as necessary.
 * 
 * @param <T> Type of the event values.
 * @see Observer
 * @see ObserverSubscription
 */
public interface Observable<T> {
	/**
	 * Subscribes the given observer to the receiver observable.
	 * 
	 * @param observer The observer.
	 * @return The corresponding subcription.
	 */
	public ObserverSubscription subscribe(final LiveObserver<? super T> observer);
	
	/**
	 * Subcribes the given observer to the receiver observable for all events.
	 * 
	 * @param observer The observer.
	 * @return The corresponding subcription.
	 */
	public ObserverSubscription subscribe(final Observer<? super T> observer);
	
	/**
	 * Subscribes the given observer to the receiver observable for a single event.
	 * 
	 * @param observer The observer.
	 * @return The corresponding subcription.
	 */
	public ObserverSubscription subscribeOnce(final Observer<? super T> observer);
	
	/**
	 * Subscribes the given observer to the receiver observable for the events as long as the given condition holds.
	 * 
	 * @param observer The observer.
	 * @param condition The condition.
	 * @return The corresponding subcription.
	 */
	public ObserverSubscription subscribeWhile(final Observer<? super T> observer, final Predicate1<? super T, RuntimeException> condition);
}
