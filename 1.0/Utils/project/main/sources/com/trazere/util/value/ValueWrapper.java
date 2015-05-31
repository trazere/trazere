/*
 *  Copyright 2006-2015 Julien Dufour
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
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
