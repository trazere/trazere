package com.trazere.util.value;

import com.trazere.util.lang.HashCode;
import com.trazere.util.text.Describable;
import com.trazere.util.text.Description;
import com.trazere.util.text.TextUtils;
import java.io.Serializable;

/**
 * The {@link ValueWrapper} class implements wrappers of single values.
 * 
 * @param <T> Type of the wrapped value.
 * @deprecated Use {@link com.trazere.core.util.Value}.
 */
@Deprecated
public class ValueWrapper<T>
implements Describable, Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Instantiates a new value.
	 * 
	 * @param value Wrapped value.
	 */
	public ValueWrapper(final T value) {
		assert null != value;
		
		// Initialization.
		_value = value;
	}
	
	// Value.
	
	/** Wrapped value. */
	protected final T _value;
	
	/**
	 * Gets the wrapped value.
	 * 
	 * @return The value.
	 * @deprecated Use {@link com.trazere.core.util.Value#get()}.
	 */
	@Deprecated
	public T getValue() {
		return _value;
	}
	
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
			final ValueWrapper<?> value = (ValueWrapper<?>) object;
			return _value.equals(value._value);
		} else {
			return false;
		}
	}
	
	@Override
	public String toString() {
		return TextUtils.computeDescription(this);
	}
	
	@Override
	public void fillDescription(final Description description) {
		description.append("Value", _value);
	}
}
