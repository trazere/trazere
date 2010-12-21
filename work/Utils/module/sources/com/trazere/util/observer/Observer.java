package com.trazere.util.observer;

import java.util.Observable;

/**
 * The {@link Observer} interface defines observers of events raised by {@link Observable observable sources}.
 * 
 * @param <T> Type of the observed event values.
 * @see Observable
 */
public interface Observer<T> {
	/**
	 * Notifies the receiver observer with the given event.
	 * 
	 * @param value The event value. May be <code>null</code>.
	 */
	public void notify(final T value);
}
