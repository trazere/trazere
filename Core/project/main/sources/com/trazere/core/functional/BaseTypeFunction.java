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
package com.trazere.core.functional;

import com.trazere.core.util.Maybe;
import java.util.Collection;

/**
 * The {@link BaseTypeFunction} abstract class provides a skeleton implementation of function from type hierarchies to values.
 * <p>
 * TODO: document upper bound
 * <p>
 * TODO: document default value
 * 
 * @param <T> Type of the types.
 * @param <R> Type of the results.
 * @since 1.0
 */
public abstract class BaseTypeFunction<T, R>
extends BaseMemoizedFunction<T, Maybe<R>> {
	/**
	 * Upper bound.
	 * 
	 * @since 1.0
	 */
	protected final Maybe<? extends T> _upperBound;
	
	/**
	 * Default value.
	 * 
	 * @since 1.0
	 */
	protected final Maybe<R> _defaultValue;
	
	/**
	 * Instantiates a new type map with no upper bounds and defaut values.
	 * 
	 * @since 1.0
	 */
	public BaseTypeFunction() {
		this(Maybe.<R>none());
	}
	
	/**
	 * Instantiates a new type map with no upper bounds and the given default value.
	 * 
	 * @param defaultValue Default value.
	 * @since 1.0
	 */
	public BaseTypeFunction(final Maybe<R> defaultValue) {
		this(Maybe.<T>none(), defaultValue);
	}
	
	/**
	 * Instantiates a new type map with the given upper bound and default value.
	 * 
	 * @param upperBound Upper bound.
	 * @param defaultValue Default value.
	 * @since 1.0
	 */
	public BaseTypeFunction(final Maybe<? extends T> upperBound, final Maybe<R> defaultValue) {
		assert null != upperBound;
		assert null != defaultValue;
		
		// Initialization.
		_upperBound = upperBound;
		_defaultValue = defaultValue;
	}
	
	/**
	 * Indicates wether the first given type is a sub type of the second given type.
	 * 
	 * @param type1 First type.
	 * @param type2 Second type.
	 * @return <code>true</code> when the first type is a sub type of the second type, <code>false</code> otherwise.
	 * @since 1.0
	 */
	protected abstract boolean isSubTypeOf(final T type1, final T type2);
	
	/**
	 * Gets the direct super types of the given type.
	 * 
	 * @param type Type.
	 * @return The super types.
	 * @since 1.0
	 */
	protected abstract Collection<? extends T> getSuperTypes(final T type);
	
	/**
	 * Computes the result of the evaluation of this function with the given argument.
	 * <p>
	 * This methods implements the evaluation logic regarding inheritance.
	 * 
	 * @param type Type to evaluate the function with.
	 * @return The result of the function evaluation.
	 * @since 1.0
	 */
	@Override
	protected Maybe<R> compute(final T type) {
		// Test the upper bound.
		if (_upperBound.isSome() && !isSubTypeOf(type, _upperBound.asSome().getValue())) {
			return _defaultValue;
		} else {
			final Maybe<R> computedValue = computeDiscrete(type);
			if (computedValue.isSome()) {
				return computedValue;
			} else {
				// Fetch the value attached to the super types.
				for (final T superType : getSuperTypes(type)) {
					final Maybe<R> superValue = evaluate(superType);
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
	 * Computes the result of the evaluation of this function with the given argument without taking sub typing into account.
	 * 
	 * @param type Type to evaluate the function with.
	 * @return The result of the function evaluation.
	 * @since 1.0
	 */
	protected abstract Maybe<R> computeDiscrete(final T type);
}
