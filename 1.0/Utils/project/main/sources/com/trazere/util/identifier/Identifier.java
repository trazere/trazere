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
package com.trazere.util.identifier;

import com.trazere.util.function.Function1;
import com.trazere.util.lang.HashCode;
import java.io.Serializable;

// TODO: kill this, merge/replace by ValueWrapper

/**
 * The {@link Identifier} class represents typed identification values.
 * <p>
 * Usually, basic types like strings and integers are used as identifiers because they can be easily instanciated. This class aims at providing a typed
 * alternative so that identifiers of different classes cannot be mixed with each other.
 * <p>
 * Equality of identifiers relies on their type and the logical equality of their underlying values.
 * 
 * @param <V> Type of the underlying values.
 * @deprecated Use {@link com.trazere.core.util.Value}.
 */
@Deprecated
public abstract class Identifier<V>
implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Instantiates an identifier with the given value.
	 * 
	 * @param value The value.
	 */
	protected Identifier(final V value) {
		assert null != value;
		
		// Initialization.
		_value = value;
	}
	
	// Value.
	
	/** Value. */
	protected final V _value;
	
	/**
	 * Gets the value of the receiver identifier.
	 * 
	 * @return The value.
	 * @deprecated Use {@link com.trazere.core.util.Value#get()}.
	 */
	@Deprecated
	public V getValue() {
		return _value;
	}
	
	/**
	 * Builds a function which gets the value of identifiers.
	 * 
	 * @param <I> Type of the identifiers.
	 * @param <V> Type of the values.
	 * @param <X> Type of the exceptions.
	 * @return The built function.
	 * @deprecated To be removed.
	 */
	@Deprecated
	@SuppressWarnings("unchecked")
	public static <I extends Identifier<V>, V, X extends Exception> Function1<I, V, X> getValueFunction() {
		return (Function1<I, V, X>) _GET_VALUE_FUNCTION;
	}
	
	private static final Function1<? extends Identifier<?>, ?, ?> _GET_VALUE_FUNCTION = new Function1<Identifier<Object>, Object, RuntimeException>() {
		@Override
		public Object evaluate(final Identifier<Object> identifier) {
			assert null != identifier;
			
			return identifier.getValue();
		}
	};
	
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
			final Identifier<?> key = (Identifier<?>) object;
			return _value.equals(key._value);
		} else {
			return false;
		}
	}
	
	@Override
	public String toString() {
		return _value.toString();
	}
}
