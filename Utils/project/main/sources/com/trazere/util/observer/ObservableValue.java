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
package com.trazere.util.observer;

import com.trazere.util.function.Predicate1;
import com.trazere.util.type.Tuple2;

/**
 * The {@link ObservableValue} interface defines observable values.
 * <p>
 * Every event corresponds to a new value and is raised when the observed values changes.
 * 
 * @param <T> Type of the values.
 * @deprecated Use {@link com.trazere.core.reactive.ObservableValue}.
 */
@Deprecated
public interface ObservableValue<T>
extends Observable<T> {
	/**
	 * Subcribes the given observer to the receiver observable value for all events and immediately notifies the given observer with the current value.
	 * 
	 * @param observer The observer.
	 * @return The corresponding subcription.
	 * @deprecated Use {@link com.trazere.core.reactive.ObservableValue#subscribeAndNotify(com.trazere.core.reactive.Observer)}.
	 */
	@Deprecated
	public ObserverSubscription subscribeAndNotify(Observer<? super T> observer);
	
	/**
	 * Subcribes the given observer to the receiver observable value for all events.
	 * 
	 * @param observer The observer.
	 * @return The current value and corresponding subcription.
	 * @deprecated Use {@link com.trazere.core.reactive.ObservableValue#subscribeToValue(com.trazere.core.reactive.Observer)}.
	 */
	@Deprecated
	public Tuple2<T, ObserverSubscription> subscribeToValue(final Observer<? super T> observer);
	
	/**
	 * Subscribes the given observer to the receiver observable value for a single event.
	 * 
	 * @param observer The observer.
	 * @return The current value and the corresponding subcription.
	 * @deprecated Use {@link com.trazere.core.reactive.ObservableValue#subscribeToValue(com.trazere.core.reactive.Observer)} and
	 *             {@link com.trazere.core.reactive.ObserverUtils#once(com.trazere.core.reactive.Observer)}.
	 */
	@Deprecated
	public Tuple2<T, ObserverSubscription> subscribeOnceToValue(final Observer<? super T> observer);
	
	/**
	 * Subscribes the given observer to the receiver observable value for the events as long as the given condition holds.
	 * 
	 * @param observer The observer.
	 * @param condition The condition.
	 * @return The current value and the corresponding subcription.
	 * @deprecated Use {@link com.trazere.core.reactive.ObservableValue#subscribeToValue(com.trazere.core.reactive.Observer)} and
	 *             {@link com.trazere.core.reactive.ObserverUtils#while_(com.trazere.core.reactive.Observer, com.trazere.core.functional.Predicate)}.
	 */
	@Deprecated
	public Tuple2<T, ObserverSubscription> subscribeWhileToValue(final Observer<? super T> observer, final Predicate1<? super T, RuntimeException> condition);
}
