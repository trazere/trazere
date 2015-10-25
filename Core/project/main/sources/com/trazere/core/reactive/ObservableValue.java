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

import com.trazere.core.util.Tuple2;

/**
 * The {@link ObservableValue} interface defines observable values.
 * <p>
 * Events are raised when the observed value changes, providing the new value.
 * 
 * @param <T> Type of the values.
 * @since 2.0
 */
public interface ObservableValue<T>
extends Observable<T> {
	/**
	 * Subscribes the given observer to this observable value for all events and immediately notifies the given observer with the current value.
	 * 
	 * @param observer The observer.
	 * @return The corresponding subcription.
	 * @since 2.0
	 */
	default ObserverSubscription subscribeAndNotify(final Observer<? super T> observer) {
		// Subscribe.
		final Tuple2<T, ObserverSubscription> subscription = subscribeToValue(observer);
		
		// Notify.
		ObservableUtils.notify(observer, subscription.get1());
		
		return subscription.get2();
	}
	
	/**
	 * Subscribes the given observer to this observable value for all events.
	 * 
	 * @param observer The observer.
	 * @return The current value and corresponding subcription.
	 * @since 2.0
	 */
	Tuple2<T, ObserverSubscription> subscribeToValue(Observer<? super T> observer);
}
