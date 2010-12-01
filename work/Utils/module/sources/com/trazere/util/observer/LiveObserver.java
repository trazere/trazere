package com.trazere.util.observer;

import java.util.Observable;

/**
 * The {@link LiveObserver} interface defines observers of events raised by {@link Observable observable sources} which control the life span of the
 * subscriptions.
 * 
 * @param <T> Type of the observed event values.
 * @see Observable
 */
public interface LiveObserver<T> {
	/**
	 * Notifies the receiver observer of an event.
	 * 
	 * @param value The value corresponding to the observed event.
	 * @return <code>true</code> to preserve the subscription, <code>false</code> to unsubscribe the observer.
	 */
	public boolean process(final T value);
}
