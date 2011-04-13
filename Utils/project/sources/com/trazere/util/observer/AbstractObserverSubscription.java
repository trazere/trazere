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

/**
 * The {@link AbstractObserverSubscription} abstract class provides a skeleton implementation of {@link ObserverSubscription observer subcriptions}.
 * <p>
 * This class keeps a strong reference to the observer corresponding to the subscription. That way, the subscribers which keep a reference to their
 * subscriptions don't have keep another reference to the observers in order to prevent their garbage collection.
 */
public abstract class AbstractObserverSubscription
implements ObserverSubscription {
	/**
	 * Instantiates a new subscription for the given observer.
	 * 
	 * @param observer The observer.
	 */
	public AbstractObserverSubscription(final Observer<?> observer) {
		assert null != observer;
		
		// Initialization.
		_observer = observer;
	}
	
	// Observer.
	
	/** Observer. */
	protected final Observer<?> _observer;
	
	/**
	 * Gets the observer corresponding to the receiver subscription.
	 * 
	 * @return The observer.
	 */
	public Observer<?> getObserver() {
		return _observer;
	}
}
