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
package com.trazere.util.collection;

import com.trazere.util.lang.ThrowableFactory;
import com.trazere.util.type.Maybe;
import java.util.Collection;

/**
 * The {@link AbstractLazyTypeMap} abstract class represents lazy maps from type hierarchies to values.
 * 
 * @param <T> Type of the types.
 * @param <V> Type of the values.
 * @param <X> Type of the exceptions.
 */
public abstract class AbstractLazyTypeMap<T, V, X extends Exception>
extends LazyMap<T, Maybe<V>, X> {
	/** The type upper bound. */
	protected final Maybe<T> _upperBound;
	
	/** The default value. */
	protected final Maybe<V> _defaultValue;
	
	/**
	 * Instantiates a new type map with no upper bounds and defaut values.
	 */
	public AbstractLazyTypeMap() {
		this(Maybe.<V>none());
	}
	
	/**
	 * Instantiates a new type map with no upper bounds and the given default value.
	 * 
	 * @param defaultValue The default value.
	 */
	public AbstractLazyTypeMap(final Maybe<V> defaultValue) {
		this(Maybe.<T>none(), defaultValue);
	}
	
	/**
	 * Instantiates a new type map with the given upper bound and default value.
	 * 
	 * @param upperBound The upper bound.
	 * @param defaultValue The default value.
	 */
	public AbstractLazyTypeMap(final Maybe<T> upperBound, final Maybe<V> defaultValue) {
		// Checks.
		assert null != upperBound;
		assert null != defaultValue;
		
		// Initialization.
		_upperBound = upperBound;
		_defaultValue = defaultValue;
	}
	
	/**
	 * Indicates wether the first given type is a sub type of the second given type.
	 * 
	 * @param type1 The first type.
	 * @param type2 The second type.
	 * @return <code>true</code> when the first type is a sub type of the second type, <code>false</code> otherwise.
	 */
	protected abstract boolean isSubTypeOf(final T type1, final T type2);
	
	/**
	 * Gets the direct super types of the given type.
	 * 
	 * @param type The type.
	 * @return The super types.
	 */
	protected abstract Collection<? extends T> getSuperTypes(final T type);
	
	/**
	 * Get the value associated to the given type.
	 * 
	 * @param type The type.
	 * @param throwableFactory The throwable factory to use.
	 * @return The value. May be <code>null</code>.
	 * @throws X When the value cannot be computed.
	 * @throws X When the type is associated to no values.
	 */
	public V getStrict(final T type, final ThrowableFactory<? extends X> throwableFactory)
	throws X {
		final Maybe<V> result = get(type);
		if (result.isSome()) {
			return result.asSome().getValue();
		} else {
			throw throwableFactory.build("No value for type \"" + type + "\"");
		}
	}
	
	@Override
	protected Maybe<V> compute(final T type)
	throws X {
		// Test the upper bound.
		if (_upperBound.isSome() && !isSubTypeOf(type, _upperBound.asSome().getValue())) {
			return _defaultValue;
		} else {
			final Maybe<V> computedValue = computeDiscrete(type);
			if (computedValue.isSome()) {
				return computedValue;
			} else {
				// Fetch the value attached to the super types.
				for (final T superType : getSuperTypes(type)) {
					final Maybe<V> superValue = get(superType);
					if (superValue.isSome()) {
						return superValue;
					}
				}
				
				// Default value.
				return _defaultValue;
			}
		}
	}
	
	/**
	 * Compute the value to associate to the given type without taking sub typing into account.
	 * 
	 * @param type The type.
	 * @return The computed value. May be <code>null</code>.
	 * @throws X When the value cannot be computed.
	 */
	protected abstract Maybe<V> computeDiscrete(final T type)
	throws X;
}
