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

import com.trazere.util.function.FunctionUtils;
import com.trazere.util.function.Predicate1;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@link BaseObservable} class provides a skeleton implementation of {@link Observable observable event sources}.
 * 
 * @param <T> Type of the event values.
 * @deprecated Use {@link com.trazere.core.reactive.BaseObservable}.
 */
@Deprecated
public abstract class BaseObservable<T>
implements Observable<T> {
	/**
	 * The {@link BaseObservable.LiveObserver} class represents the observers subscribed to the observables.
	 * <p>
	 * The instances keep a weak reference to the real observer in order to allow and handle their garbage collection. They also provide control over the life
	 * span of the subscription.
	 * 
	 * @deprecated Use {@link com.trazere.core.reactive.BaseObservable.ObserverRef}.
	 */
	@Deprecated
	@SuppressWarnings("javadoc")
	protected abstract class LiveObserver {
		public LiveObserver(final Observer<? super T> observer) {
			assert null != observer;
			
			// Initialization.
			_observer = new WeakReference<>(observer);
		}
		
		// Observer.
		
		protected final WeakReference<Observer<? super T>> _observer;
		
		public boolean isAlive() {
			return null != _observer.get();
		}
		
		public boolean notify(final T value) {
			final Observer<? super T> observer = _observer.get();
			if (null != observer) {
				return notify(observer, value);
			} else {
				return false;
			}
		}
		
		protected abstract boolean notify(final Observer<? super T> observer, final T value);
	}
	
	/** Subscribed observers. */
	protected final List<LiveObserver> _observers = new ArrayList<>();
	
	/**
	 * Indicates whether the receiver observable is being observed.
	 * 
	 * @return <code>true</code> when the observable is observed by at least one observer, <code>false</code> otherwise.
	 * @deprecated Use {@link com.trazere.core.reactive.BaseObservable#isObserved()}.
	 */
	@Deprecated
	public boolean isObserved() {
		return FunctionUtils.isAny(_LIVE_OBSERVER_FILTER, _observers);
	}
	
	private static final Predicate1<BaseObservable<?>.LiveObserver, RuntimeException> _LIVE_OBSERVER_FILTER = new Predicate1<BaseObservable<?>.LiveObserver, RuntimeException>() {
		@Override
		public boolean evaluate(final BaseObservable<?>.LiveObserver observer) {
			return observer.isAlive();
		}
	};
	
	/**
	 * Subscribes the given observer to the receiver observable.
	 * <p>
	 * This method is called every time a observer is being subscribed.
	 * 
	 * @param observer The observer.
	 * @deprecated Use {@link com.trazere.core.reactive.BaseObservable#subscribe(com.trazere.core.reactive.BaseObservable.ObserverRef)}.
	 */
	@Deprecated
	@SuppressWarnings("javadoc")
	protected void subscribe(final LiveObserver observer) {
		assert null != observer;
		
		_observers.add(observer);
	}
	
	/**
	 * Unsubscribes the given observer from the receiver observable.
	 * <p>
	 * This method is called every time a observer is being unsubscribed (either implicitely or explicitely).
	 * 
	 * @param observer The observer.
	 * @deprecated Use {@link com.trazere.core.reactive.BaseObservable#unsubscribe(com.trazere.core.reactive.BaseObservable.ObserverRef)}.
	 */
	@Deprecated
	@SuppressWarnings("javadoc")
	protected void unsubscribe(final LiveObserver observer) {
		assert null != observer;
		
		_observers.remove(observer);
	}
	
	/**
	 * Unsubscribes all observers at once.
	 * <p>
	 * Note: the {@link #unsubscribe(LiveObserver)} method is not called when calling this method.
	 * 
	 * @deprecated Use {@link com.trazere.core.reactive.BaseObservable#unsubscribeAll()}.
	 */
	@Deprecated
	@SuppressWarnings("javadoc")
	protected void unsubscribeAll() {
		_observers.clear();
	}
	
	/**
	 * The {@link Subscription} class represents the subscriptions of the observers.
	 * 
	 * @deprecated Use {@link com.trazere.core.reactive.BaseObservable.ObserverSubscriptionImpl}.
	 */
	@Deprecated
	@SuppressWarnings("javadoc")
	protected class Subscription
	extends BaseObserverSubscription {
		/**
		 * Instantiates a new subscription for the given observer and preservation condition.
		 * 
		 * @param observer The observer.
		 * @param liveObserver The subscribed observer.
		 * @deprecated Use
		 *             {@link com.trazere.core.reactive.BaseObservable.ObserverSubscriptionImpl#ObserverSubscriptionImpl(com.trazere.core.reactive.Observer, com.trazere.core.reactive.BaseObservable.ObserverRef)}
		 *             .
		 */
		@Deprecated
		public Subscription(final Observer<? super T> observer, final LiveObserver liveObserver) {
			super(observer);
			
			// Checks.
			assert null != liveObserver;
			
			// Initialization.
			_subscribedObserver = liveObserver;
		}
		
		// Subscription.
		
		/** Subscribed observer. */
		protected final LiveObserver _subscribedObserver;
		
		@Override
		public void unsubscribe() {
			BaseObservable.this.unsubscribe(_subscribedObserver);
		}
	}
	
	protected ObserverSubscription subscribe(final Observer<? super T> observer, final LiveObserver liveObserver) {
		assert null != observer;
		assert null != liveObserver;
		
		// Subscribe.
		subscribe(liveObserver);
		
		// Build the subscription.
		return new Subscription(observer, liveObserver);
	}
	
	@Override
	public ObserverSubscription subscribe(final Observer<? super T> observer) {
		assert null != observer;
		
		return subscribe(observer, new LiveObserver(observer) {
			@Override
			protected boolean notify(final Observer<? super T> observer_, final T value) {
				observer_.notify(value);
				return true;
			}
		});
	}
	
	@Override
	public ObserverSubscription subscribeOnce(final Observer<? super T> observer) {
		assert null != observer;
		
		return subscribe(observer, new LiveObserver(observer) {
			@Override
			protected boolean notify(final Observer<? super T> observer_, final T value) {
				observer_.notify(value);
				return false;
			}
		});
	}
	
	@Override
	public ObserverSubscription subscribeWhile(final Observer<? super T> observer, final Predicate1<? super T, RuntimeException> condition) {
		assert null != observer;
		assert null != condition;
		
		return subscribe(observer, new LiveObserver(observer) {
			@Override
			protected boolean notify(final Observer<? super T> observer_, final T value) {
				if (condition.evaluate(value)) {
					observer_.notify(value);
					return true;
				} else {
					return false;
				}
			}
		});
	}
	
	/**
	 * Notifies the observers of the receiver observable with the given event.
	 * 
	 * @param value The event value. May be <code>null</code>.
	 * @deprecated Use {@link com.trazere.core.reactive.BaseObservable#notify(Object)}.
	 */
	@Deprecated
	@SuppressWarnings("javadoc")
	protected void notify(final T value) {
		// Note: copy the observers to prevent concurrent modifications.
		for (final LiveObserver observer : new ArrayList<>(_observers)) {
			if (!observer.notify(value)) {
				unsubscribe(observer);
			}
		}
	}
}
