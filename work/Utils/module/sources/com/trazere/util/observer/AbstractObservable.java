package com.trazere.util.observer;

import com.trazere.util.function.Predicate1;
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
	/** Weak references to the observers. */
	protected final List<WeakReference<LiveObserver<? super T>>> _observerReferences = new ArrayList<WeakReference<LiveObserver<? super T>>>();
	
	/**
	 * The {@link Subscription} class represents the subscriptions of the observers.
	 * <p>
	 * The instances provide a weak reference to the observer to be used by the outer observable, but also keep a strong reference to it in order to prevent its
	 * garbage collection when the subscriber only keeps a reference to the subscription.
	 */
	protected class Subscription
	implements ObserverSubscription {
		protected final LiveObserver<? super T> _observer;
		
		/**
		 * Instantiates a new subscription for the given observer.
		 * 
		 * @param observer The observer.
		 */
		public Subscription(final LiveObserver<? super T> observer) {
			assert null != observer;
			
			// Initialization.
			_observer = observer;
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
		_observerReferences.add(subscription.getReference());
		return subscription;
	}
	
	public ObserverSubscription subscribe(final Observer<? super T> observer) {
		assert null != observer;
		
		return subscribe(new LiveObserver<T>() {
			public boolean process(final T value) {
				observer.process(value);
				return true;
			}
		});
	}
	
	public ObserverSubscription subscribeOnce(final Observer<? super T> observer) {
		assert null != observer;
		
		return subscribe(new LiveObserver<T>() {
			public boolean process(final T value) {
				observer.process(value);
				return false;
			}
		});
	}
	
	public ObserverSubscription subscribeWhile(final Observer<? super T> observer, final Predicate1<? super T, RuntimeException> condition) {
		assert null != observer;
		assert null != condition;
		
		return subscribe(new LiveObserver<T>() {
			public boolean process(final T value) {
				if (condition.evaluate(value)) {
					observer.process(value);
					return true;
				} else {
					return false;
				}
			}
		});
	}
	
	/**
	 * Raises a event with the given value for the receiver observable.
	 * 
	 * @param value The value of the event. May be <code>null</code>.
	 */
	protected void raise(final T value) {
		// Note: copy the references to prevent concurrent modifications.
		for (final WeakReference<LiveObserver<? super T>> reference : new ArrayList<WeakReference<LiveObserver<? super T>>>(_observerReferences)) {
			final LiveObserver<? super T> observer = reference.get();
			if (null != observer) {
				if (!observer.process(value)) {
					unsubscribe(reference);
				}
			} else {
				unsubscribe(reference);
			}
		}
	}
	
	/**
	 * Unsubscribes the observer corresponding to the given soft reference.
	 * 
	 * @param reference The reference.
	 */
	protected void unsubscribe(final WeakReference<LiveObserver<? super T>> reference) {
		assert null != reference;
		
		_observerReferences.remove(reference);
	}
}
