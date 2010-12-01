package com.trazere.util.observer;

/**
 * The {@link SimpleObservable} class represents observable sources whose event can be raised externally.
 * 
 * @param <T> Type of the event values.
 */
public class SimpleObservable<T>
extends AbstractObservable<T> {
	@Override
	public void raise(final T value) {
		super.raise(value);
	}
}
