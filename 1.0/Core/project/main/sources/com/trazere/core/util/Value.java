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
package com.trazere.core.util;

import com.trazere.core.lang.HashCode;
import java.io.Serializable;

/**
 * The {@link Value} class implements a basic data type that wraps single values.
 * <p>
 * This class aims at being subclassed in order to strengthen code typing (instead of using and reusing simple types all over place).
 * 
 * @param <T> Type of the wrapped value.
 * @since 1.0
 */
public class Value<T>
implements Field<T>, Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Instantiates a new value.
	 * 
	 * @param value Value to wrap.
	 * @since 1.0
	 */
	public Value(final T value) {
		_value = value;
	}
	
	// Value.
	
	/**
	 * Wrapped value.
	 * 
	 * @since 1.0
	 */
	protected final T _value;
	
	/**
	 * Gets the wrapped value.
	 * 
	 * @return The value.
	 * @since 1.0
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
