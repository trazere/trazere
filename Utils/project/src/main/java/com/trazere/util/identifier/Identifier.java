/*
 *  Copyright 2006-2013 Julien Dufour
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

/**
 * The {@link Identifier} class represents typed identification values.
 * <p>
 * Usually, basic types like strings and integers are used as identifiers because they can be easily instanciated. This class aims at providing a typed
 * alternative so that identifiers of different classes cannot be mixed with each other.
 * <p>
 * Identifiers rely on physically egality, the underlying values are used for informational purpose.
 * 
 * @param <V> Type of the underlying values.
 * @see IdentifierBase
 */
public class Identifier<V> {
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
	 */
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
	 */
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
	public String toString() {
		return _value.toString();
	}
}
