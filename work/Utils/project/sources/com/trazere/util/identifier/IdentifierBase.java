/*
 *  Copyright 2006-2011 Julien Dufour
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

import com.trazere.util.collection.LazyMap;
import com.trazere.util.function.Function1;

/**
 * The {@link IdentifierBase} abstract class represents repositories of identifiers which help to build them from their values.
 * <p>
 * The built identifiers are normalized according to the egality of their values so that they can be compared physically.
 * 
 * @param <V> Type of the values of the identifier.
 * @param <I> Type of the identifiers.
 */
public abstract class IdentifierBase<V, I extends Identifier<V>> {
	/** Identifiers identified by their values. */
	private final LazyMap<V, I, RuntimeException> _identifiers = new LazyMap<V, I, RuntimeException>() {
		@Override
		protected I compute(final V value) {
			return IdentifierBase.this.build(value);
		}
	};
	
	/**
	 * Builds the identifier with the given value.
	 * 
	 * @param value The value.
	 * @return The built identifier.
	 */
	protected abstract I build(final V value);
	
	/**
	 * Gets the identifier corresponding to the given value.
	 * 
	 * @param value The value.
	 * @return The identifier.
	 */
	public final I fromValue(final V value) {
		assert null != value;
		
		return _identifiers.get(value);
	}
	
	/**
	 * Gets a function which gets the identifiers of the receiver base from their values.
	 * 
	 * @param <X> Type of the exceptions.
	 * @return The built function.
	 */
	public <X extends Exception> Function1<V, I, X> fromValueFunction() {
		return new Function1<V, I, X>() {
			public I evaluate(final V value) {
				return fromValue(value);
			}
		};
	}
}
