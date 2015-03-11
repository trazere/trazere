/*
 *  Copyright 2006-2013 Julien Dufour
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

// TODO: subclass Observable ?

/**
 * The {@link Broadcaster} class implements distribution points of events that can be observed.
 * 
 * @param <E> Type of the events.
 */
public class Broadcaster<E> {
	/**
	 * Instantiates a new broadcaster.
	 */
	public Broadcaster() {
		this(new BroadcasterObservable<>());
	}
	
	/**
	 * Instantiates a new broadcaster.
	 * 
	 * @param observable Observable of the fired events.
	 */
	protected Broadcaster(final BroadcasterObservable<E> observable) {
		assert null != observable;
		
		// Initialization.
		_observable = observable;
	}
	
	// Observable.
	
	/**
	 * The {@link Broadcaster.BroadcasterObservable} class implements observables of events of broadcasters.
	 * 
	 * @param <E> Type of the events.
	 */
	protected static class BroadcasterObservable<E>
	extends BaseObservable<E> {
		// Nothing to do.
	}
	
	/** Observable of the fired events. */
	protected final BroadcasterObservable<E> _observable;
	
	/**
	 * Gets the observable of the events fired by this broadcaster.
	 * 
	 * @return The observable.
	 */
	public Observable<E> getObservable() {
		return _observable;
	}
	
	/**
	 * Indicates whether this broadcaster is currently being observed.
	 * 
	 * @return <code>true</code> when the broadcaster is being observed by at least one live observer, <code>false</code> otherwise.
	 */
	public boolean isObserved() {
		return _observable.isObserved();
	}
	
	/**
	 * Unsubscribes all observers from this broadcaster at once.
	 */
	public void unsubscribeAll() {
		_observable.unsubscribeAll();
	}
	
	// Events.
	
	/**
	 * Fires the given event.
	 * 
	 * @param event Event to fire.
	 */
	public void fire(final E event) {
		_observable.notify(event);
	}
	
	/**
	 * Fires the events raised by the given observable.
	 * 
	 * @param observable Observable providing the events to fire.
	 * @return The subscription.
	 */
	public ObserverSubscription fire(final Observable<? extends E> observable) {
		return observable.subscribe((final E event) -> {
			fire(event);
			return true;
		});
	}
}