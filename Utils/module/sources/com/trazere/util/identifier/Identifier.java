/*
 *  Copyright 2006-2009 Julien Dufour
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

/**
 * The {@link Identifier} class represents typed unique values.
 * <p>
 * Usually, basic types like strings and integers are used as identifiers because they can be easily instanciated. This class aims at providing a typed
 * alternative so that identifiers of different classes cannot be mixed with each other.
 * <p>
 * Moreover, the identifiers are designed to be compared physically, the underlying value is only used for rendering.
 * 
 * @param <T> Type of the underlying value.
 */
public class Identifier<T> {
	private final T _value;
	
	protected Identifier(final T value) {
		assert null != value;
		
		// Initialization.
		_value = value;
	}
	
	public T getValue() {
		return _value;
	}
	
	@Override
	public String toString() {
		return _value.toString();
	}
}
