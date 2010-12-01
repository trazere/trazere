package com.trazere.util.observer;

/**
 * The {@link ObserverSubscription} interface defines subscriptions of observers to observables.
 * 
 * @see Observable
 * @see Observer
 */
public interface ObserverSubscription {
	/**
	 * Unsubscribes the observer from the observable corresponding to the receiver subscription.
	 */
	public void unsubscribe();
}
