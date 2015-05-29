package com.trazere.core.util;

import com.trazere.core.lang.HashCode;
import java.io.Serializable;

/**
 * The {@link Value} class implements a basic data type that wraps single values.
 * <p>
 * This class aims at being subclassed in order to strengthen code typing (instead of using and reusing simple types all over place).
 * 
 * @param <T> Type of the wrapped value.
 */
public class Value<T>
implements Field<T>, Serializable {
	private static final long serialVersionUID = 1L;
	
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
	@Override
	public T get() {
		return _value;
	}
	
	// TODO: add getValueFunction in ValueFunctions
	
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
		return String.valueOf(_value);
	}
}
