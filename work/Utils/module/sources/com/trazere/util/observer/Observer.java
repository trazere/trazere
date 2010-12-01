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
	 * Notifies the receiver observer of an event.
	 * 
	 * @param value The value corresponding to the observed event.
	 */
	public void process(final T value);
}
