package com.trazere.core.util;

import com.trazere.core.lang.HashCode;
import com.trazere.core.text.Describable;
import com.trazere.core.text.Description;
import com.trazere.core.text.TextUtils;

/**
 * The {@link Value} class implements a basic data type that wraps single values.
 * <p>
 * This class aims at being subclassed in order to strengthen code typing (instead of using and reusing simple types all over place).
 * 
 * @param <T> Type of the wrapped value.
 */
public class Value<T>
implements Describable {
	/**
	 * Instantiates a new value.
	 * 
	 * @param value Value to wrap.
	 */
	public Value(final T value) {
		_value = value;
	}
	
	// Value.
	
	/** Wrapped value. */
	protected final T _value;
	
	/**
	 * Gets the wrapped value.
	 * 
	 * @return The value.
	 */
	public T getValue() {
		return _value;
	}
	
	// TODO: add getValueFunction
	
	// Object.
	
	@Override
	public int hashCode() {
		final HashCode result = new HashCode(this);
		result.append(_value);
		return result.get();
	}
	
	@Override
	public boolean equals(final Object object) {
		if (this == object) {
			return true;
		} else if (null != object && getClass().equals(object.getClass())) {
			final Value<?> value = (Value<?>) object;
			return _value.equals(value._value);
		} else {
			return false;
		}
	}
	
	@Override
	public String toString() {
		return TextUtils.description(this);
	}
	
	@Override
	public void appendDescription(final Description description) {
		description.append("Value", _value);
	}
}
