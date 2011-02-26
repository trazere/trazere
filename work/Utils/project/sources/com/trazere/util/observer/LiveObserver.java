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
	 * Notifies the receiver observer with the given event.
	 * 
	 * @param value The event value. May be <code>null</code>.
	 * @return <code>true</code> to preserve the subscription, <code>false</code> to cancel it.
	 */
	public boolean notify(final T value);
}
