/*
 *  Copyright 2006-2010 Julien Dufour
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

import java.util.HashMap;
import java.util.Map;

/**
 * The {@link IdentifierBase} abstract class helps to build identifiers from their value.
 * <p>
 * This class normalizes the built identifiers according to their value so that they can be compared physically.
 * 
 * @param <T> Type of the values of the identifier.
 * @param <I> Type of the identifiers.
 */
public abstract class IdentifierBase<T, I extends Identifier<T>> {
	/** Identifiers identified by their values. */
	private final Map<T, I> _identifiers = new HashMap<T, I>();
	
	/**
	 * Get the identifier corresponding to the given value.
	 * 
	 * @param value Value of the identifier.
	 * @return The identifier.
	 */
	public final I fromValue(final T value) {
		assert null != value;
		
		// Look in the cache.
		if (_identifiers.containsKey(value)) {
			return _identifiers.get(value);
		}
		
		// Build the identifier.
		final I identifier = build(value);
		_identifiers.put(value, identifier);
		
		return identifier;
	}
	
	/**
	 * Build the identifier with the given value.
	 * 
	 * @param value The value.
	 * @return The built identifier.
	 */
	protected abstract I build(final T value);
}
