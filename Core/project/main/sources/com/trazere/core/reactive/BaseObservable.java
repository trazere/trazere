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

import com.trazere.core.lang.IterableUtils;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@link BaseObservable} class provides a skeleton implementation of {@link Observable observables}.
 * 
 * @param <E> Type of the events.
 * @since 2.0
 */
public abstract class BaseObservable<E>
implements Observable<E> {
	// Observers.
	
	/**
	 * The {@link BaseObservable.ObserverRef} class represents references to subscribed observers.
	 * <p>
	 * The instances keep a weak reference to the observers in order to allow and handle their garbage collection.
	 * 
	 * @since 2.0
	 */
	protected class ObserverRef {
		/**
		 * Instantiates a new reference.
		 * 
		 * @param observer Observer to reference.
		 * @since 2.0
		 */
		public ObserverRef(final Observer<? super E> observer) {
			assert null != observer;
			
			// Initialization.
			_observer = new WeakReference<>(observer);
		}
		
		// Observer.
		
		/**
		 * Reference to the observer.
		 * 
		 * @since 2.0
		 */
		protected final WeakReference<Observer<? super E>> _observer;
		
		/**
		 * Indicates whether the referenced observer is alive or has been garbage collected.
		 * 
		 * @return <code>true</code> when the observer is alive, <code>false</code> when it has been garbage collected.
		 * @since 2.0
		 */
		public boolean isAlive() {
			return null != _observer.get();
		}
		
		/**
		 * Notifies the referenced observer that the given event has been raised.
		 * 
		 * @param event Raised event.
		 * @return <code>true</code> to hold the subscription corresponding to this notification, <code>false</code> to cancel it.
		 * @see Observer#onEvent(Object)
		 * @since 2.0
		 */
		public boolean notify(final E event) {
			final Observer<? super E> observer = _observer.get();
			if (null != observer) {
				return BaseObservable.this.notify(observer, event);
			} else {
				return false;
			}
		}
		
		@Override
		public String toString() {
			final Observer<? super E> observer = _observer.get();
			return null != observer ? observer.toString() : "*";
		}
	}
	
	/**
	 * References to the subscribed observers.
	 * 
	 * @since 2.0
	 */
	protected final List<ObserverRef> _observers = new ArrayList<>();
	
	/**
	 * Indicates whether this observable is currently being observed.
	 * 
	 * @return <code>true</code> when the observable is being observed by at least one live observer, <code>false</code> otherwise.
	 * @since 2.0
	 */
	public synchronized boolean isObserved() {
		return IterableUtils.isAny(_observers, ObserverRef::isAlive);
	}
	
	/**
	 * Subscribes the given observer to this observable.
	 * <p>
	 * This method is called every time an observer is being subscribed.
	 * 
	 * @param observer Observer to subscribe.
	 * @since 2.0
	 */
	protected synchronized void subscribe(final ObserverRef observer) {
		assert null != observer;
		
		_observers.add(observer);
	}
	
	/**
	 * Unsubscribes the given observer from this observable.
	 * <p>
	 * This method is called every time an observer is being unsubscribed (either implicitely or explicitely), unless all observers are unsubscribes at once
	 * (@see {@link #unsubscribeAll()}).
	 * 
	 * @param observer Observer to unsubscribe.
	 * @since 2.0
	 */
	protected synchronized void unsubscribe(final ObserverRef observer) {
		assert null != observer;
		
		_observers.remove(observer);
	}
	
	/**
	 * Unsubscribes all observers from this observable at once.
	 * <p>
	 * This method is called every time all observers are unsubscribed at once.
	 * 
	 * @since 2.0
	 */
	protected synchronized void unsubscribeAll() {
		_observers.clear();
	}
	
	/**
	 * The {@link ObserverSubscriptionImpl} class implements the subscriptions of the observers.
	 * 
	 * @since 2.0
	 */
	protected class ObserverSubscriptionImpl
	extends BaseObserverSubscription {
		/**
		 * Instantiates a new subscription.
		 * 
		 * @param observer Subscribed observer.
		 * @param reference Reference to the subscribed observer.
		 * @since 2.0
		 */
		public ObserverSubscriptionImpl(final Observer<? super E> observer, final ObserverRef reference) {
			super(observer);
			
			// Checks.
			assert null != reference;
			
			// Initialization.
			_reference = reference;
		}
		
		// Subscription.
		
		/**
		 * Observer reference.
		 * 
		 * @since 2.0
		 */
		protected final ObserverRef _reference;
		
		@Override
		public void unsubscribe() {
			BaseObservable.this.unsubscribe(_reference);
		}
	}
	
	@Override
	public ObserverSubscription subscribe(final Observer<? super E> observer) {
		assert null != observer;
		
		// Build the reference.
		final ObserverRef reference = new ObserverRef(observer);
		
		// Subscribe.
		subscribe(reference);
		
		// Build the subscription.
		return new ObserverSubscriptionImpl(observer, reference);
	}
	
	/**
	 * Notifies the observers subscribed to this observable that the given event has been raised.
	 *
	 * @param event Raised event.
	 * @since 2.0
	 */
	protected void notify(final E event) {
		// Note: copy the observers to prevent concurrent modifications and for thread safety.
		final List<ObserverRef> observers;
		synchronized (this) {
			observers = new ArrayList<>(_observers);
		}
		
		// Note: avoid holding the lock while notifying to prevent possible deadlocks.
		for (final ObserverRef observer : observers) {
			if (!observer.notify(event)) {
				unsubscribe(observer);
			}
		}
	}
	
	/**
	 * Notifies the given observer with the value of the future.
	 * <p>
	 * The default implementation protects the caller against observer failures. The subscription is cancelled when the observer fail.
	 * 
	 * @param observer Observer to notify.
	 * @param event Value of the future.
	 * @return <code>true</code> to hold the subscription corresponding to this notification, <code>false</code> to cancel it.
	 * @since 2.0
	 */
	protected boolean notify(final Observer<? super E> observer, final E event) {
		return ObservableUtils.notify(observer, event);
	}
}
