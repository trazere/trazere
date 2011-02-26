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

import com.trazere.util.function.FunctionUtils;
import com.trazere.util.function.Predicate1;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@link AbstractObservable} class provides a skeleton implementation of {@link Observable observable sources}.
 * 
 * @param <T> Type of the event values.
 */
public abstract class AbstractObservable<T>
implements Observable<T> {
	/**
	 * The {@link Subscription} class represents the subscriptions of the observers.
	 * <p>
	 * The instances provide a weak reference to the observer to be used by the outer observable, but also keep a strong reference to it in order to prevent its
	 * garbage collection when the subscriber only keeps a reference to the subscription.
	 */
	protected class Subscription
	extends AbstractObserverSubscription {
		/**
		 * Instantiates a new subscription for the given observer.
		 * 
		 * @param observer The observer.
		 */
		public Subscription(final LiveObserver<? super T> observer) {
			super(observer);
			
			// Initialization.
			_reference = new WeakReference<LiveObserver<? super T>>(observer);
		}
		
		// Reference.
		
		/** Weak reference to the observer. */
		protected final WeakReference<LiveObserver<? super T>> _reference;
		
		/**
		 * Get the weak reference to the observer of the receiver subscription.
		 * 
		 * @return The reference.
		 */
		public WeakReference<LiveObserver<? super T>> getReference() {
			return _reference;
		}
		
		public void unsubscribe() {
			AbstractObservable.this.unsubscribe(_reference);
		}
	}
	
	public ObserverSubscription subscribe(final LiveObserver<? super T> observer) {
		assert null != observer;
		
		final Subscription subscription = new Subscription(observer);
		subscribe(subscription.getReference());
		return subscription;
	}
	
	public ObserverSubscription subscribe(final Observer<? super T> observer) {
		assert null != observer;
		
		return subscribe(new LiveObserver<T>() {
			public boolean notify(final T value) {
				observer.notify(value);
				return true;
			}
		});
	}
	
	public ObserverSubscription subscribeOnce(final Observer<? super T> observer) {
		assert null != observer;
		
		return subscribe(new LiveObserver<T>() {
			public boolean notify(final T value) {
				observer.notify(value);
				return false;
			}
		});
	}
	
	public ObserverSubscription subscribeWhile(final Observer<? super T> observer, final Predicate1<? super T, RuntimeException> condition) {
		assert null != observer;
		assert null != condition;
		
		return subscribe(new LiveObserver<T>() {
			public boolean notify(final T value) {
				if (condition.evaluate(value)) {
					observer.notify(value);
					return true;
				} else {
					return false;
				}
			}
		});
	}
	
	/** Weak references to the observers. */
	protected final List<WeakReference<LiveObserver<? super T>>> _observers = new ArrayList<WeakReference<LiveObserver<? super T>>>();
	
	/**
	 * Indicates whether the receiver observable is being observed.
	 * 
	 * @return <code>true</code> when the observable is observed by at least one observer, <code>false</code> otherwise.
	 */
	public boolean isObserved() {
		return FunctionUtils.isAny(_LIVE_REF, _observers);
	}
	
	private static final Predicate1<Reference<?>, RuntimeException> _LIVE_REF = new Predicate1<Reference<?>, RuntimeException>() {
		public boolean evaluate(final Reference<?> reference) {
			return null != reference.get();
		}
	};
	
	/**
	 * Subscribes the observer corresponding to the given soft reference to the receiver observable.
	 * <p>
	 * This method is called every time a single observer is being subscribed.
	 * 
	 * @param observer The reference to the observer.
	 */
	protected void subscribe(final WeakReference<LiveObserver<? super T>> observer) {
		assert null != observer;
		
		_observers.add(observer);
	}
	
	/**
	 * Unsubscribes the observer corresponding to the given soft reference from the receiver observable.
	 * <p>
	 * This method is called every time a single observer is being unsubscribed (either implicitely or explicitely).
	 * 
	 * @param observer The reference to the observer.
	 */
	protected void unsubscribe(final WeakReference<LiveObserver<? super T>> observer) {
		assert null != observer;
		
		_observers.remove(observer);
	}
	
	/**
	 * Notifies the observers of the receiver observable with the given event.
	 * 
	 * @param value The event value. May be <code>null</code>.
	 */
	protected void notify(final T value) {
		// Note: copy the references to prevent concurrent modifications.
		for (final WeakReference<LiveObserver<? super T>> reference : new ArrayList<WeakReference<LiveObserver<? super T>>>(_observers)) {
			final LiveObserver<? super T> observer = reference.get();
			if (null != observer) {
				if (!observer.notify(value)) {
					unsubscribe(reference);
				}
			} else {
				unsubscribe(reference);
			}
		}
	}
}
