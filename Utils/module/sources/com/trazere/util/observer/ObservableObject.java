package com.trazere.util.observer;

import com.trazere.util.lang.MutableObject;
import com.trazere.util.text.Describable;
import com.trazere.util.text.Description;
import com.trazere.util.text.TextUtils;

/**
 * The {@link ObservableObject} class represents mutable object values whose changes can be observed.
 * 
 * @param <T> Type of the value.
 */
public class ObservableObject<T>
extends AbstractObservable<T>
implements Describable {
	/**
	 * Instantiates a new observable object with the given initial value.
	 * 
	 * @param value The value. May be <code>null</code>.
	 */
	public ObservableObject(final T value) {
		// Initialization.
		_value = new MutableObject<T>(value);
	}
	
	// Value.
	
	/** Value. */
	protected final MutableObject<T> _value;
	
	/**
	 * Gets the value of the receiver observable object.
	 * 
	 * @return The value. May be <code>null</code>.
	 */
	public T get() {
		return _value.get();
	}
	
	/**
	 * Sets the value of the receiver observable object and notify the observers.
	 * 
	 * @param value The new value. May be <code>null</code>.
	 */
	public void set(final T value) {
		// Set.
		_value.set(value);
		
		// Notify.
		raise(value);
	}
	
	// Object.
	
	@Override
	public final String toString() {
		return TextUtils.computeDescription(this);
	}
	
	public void fillDescription(final Description description) {
		description.append("Value", _value.get());
	}
}
