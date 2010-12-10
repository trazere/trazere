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
	public AbstractObserverSubscription(final LiveObserver<?> observer) {
		assert null != observer;
		
		// Initialization.
		_observer = observer;
	}
	
	// Observer.
	
	/** Observer. */
	protected final LiveObserver<?> _observer;
	
	/**
	 * Gets the observer corresponding to the receiver subscription.
	 * 
	 * @return The observer.
	 */
	public LiveObserver<?> getObserver() {
		return _observer;
	}
}
