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

import com.trazere.util.lang.HashCode;

/**
 * The {@link LenientIdentifier} class represents identifiers which need not to be normalized according to their values.
 * <p>
 * Equality of lenient identifiers relies on the logical equality of their underlying values.
 * 
 * @param <V> Type of the underlying values.
 * @see IdentifierBase
 */
public class LenientIdentifier<V>
extends Identifier<V> {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Instantiates an identifier with the given value.
	 * 
	 * @param value The value.
	 */
	protected LenientIdentifier(final V value) {
		super(value);
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
			final LenientIdentifier<?> key = (LenientIdentifier<?>) object;
			return _value.equals(key._value);
		} else {
			return false;
		}
	}
}
