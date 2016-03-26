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
import java.util.Observable;

/**
 * The {@link Observer} interface defines observers of events raised by {@link Observable observables}.
 * 
 * @param <E> Type of the observed events.
 * @see Observable
 * @since 2.0
 */
public interface Observer<E> {
	/**
	 * Notifies this observer that the given event has been raised.
	 * 
	 * @param event Raised event.
	 * @return <code>true</code> to hold the subscription corresponding to this notification, <code>false</code> to cancel it.
	 * @since 2.0
	 */
	boolean onEvent(final E event);
	
	/**
	 * Builds a view of this observer that handles a single event and cancels the subscription.
	 * 
	 * @return The built view.
	 * @since 2.0
	 */
	default Observer<E> once() {
		final Observer<E> self = this;
		return new Observer<E>() {
			@Override
			public boolean onEvent(final E event) {
				self.onEvent(event);
				return false;
			}
		};
	}
	
	/**
	 * Builds a view of this observer that handles the events as long as the given condition holds.
	 * 
	 * @param condition Predicate representing the condition.
	 * @return The built view.
	 * @since 2.0
	 */
	default Observer<E> while_(final Predicate<? super E> condition) {
		assert null != condition;
		
		final Observer<E> self = this;
		return new Observer<E>() {
			@Override
			public boolean onEvent(final E event) {
				if (condition.evaluate(event)) {
					return self.onEvent(event);
				} else {
					return false;
				}
			}
		};
	}
	
	/**
	 * Builds a view of this observer that filters the handled events.
	 * <p>
	 * The subscription is hold when ignoring events.
	 * 
	 * @param filter Predicate to use to filter of the events.
	 * @return The built view.
	 * @since 2.0
	 */
	default Observer<E> filter(final Predicate<? super E> filter) {
		assert null != filter;
		
		final Observer<E> self = this;
		return new Observer<E>() {
			@Override
			public boolean onEvent(final E event) {
				if (filter.evaluate(event)) {
					return self.onEvent(event);
				} else {
					return true;
				}
			}
		};
	}
}
