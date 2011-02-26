package com.trazere.util.observer;

/**
 * The {@link ObserverSubscription} interface defines subscriptions of observers to observables.
 * 
 * @see Observable
 * @see Observer
 */
public interface ObserverSubscription {
	/**
	 * Cancels the receiver subscription.
	 */
	public void unsubscribe();
}
