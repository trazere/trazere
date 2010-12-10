package com.trazere.util.observer;

/**
 * The {@link SimpleObservable} class represents observable sources whose events can be raised externally.
 * 
 * @param <T> Type of the event values.
 */
public class SimpleObservable<T>
extends AbstractObservable<T> {
	@Override
	public boolean isObserved() {
		return super.isObserved();
	}
	
	@Override
	public void raise(final T value) {
		super.raise(value);
	}
}
