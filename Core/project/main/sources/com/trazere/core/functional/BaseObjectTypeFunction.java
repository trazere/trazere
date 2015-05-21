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
package com.trazere.core.functional;

import com.trazere.core.collection.CollectionUtils;
import com.trazere.core.lang.IterableUtils;
import com.trazere.core.util.Maybe;
import com.trazere.core.util.MaybeUtils;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * The {@link BaseObjectTypeFunction} abstract class provides a skeleton implementation of function from Java class hierarchies to values.
 * 
 * @param <T> Upper bound type.
 * @param <R> Type of the results.
 */
public abstract class BaseObjectTypeFunction<T, R>
extends BaseTypeFunction<Class<? extends T>, R>
implements ObjectTypeFunction<T, Maybe<R>> {
	/**
	 * Builds a new type map with no upper bounds and defaut values using the given function.
	 *
	 * @param <R> Type of the values.
	 * @param function The function.
	 * @return The built map.
	 */
	public static <R> BaseObjectTypeFunction<Object, R> build(final Function<? super Class<?>, ? extends Maybe<R>> function) {
		return build(function, Object.class, Maybe.<R>none());
	}
	
	/**
	 * Builds a new type map with no upper bounds and the given default value using the given function.
	 *
	 * @param <R> Type of the values.
	 * @param function The function.
	 * @param defaultValue The default value.
	 * @return The built map.
	 */
	public static <R> BaseObjectTypeFunction<Object, R> build(final Function<? super Class<?>, ? extends Maybe<R>> function, final Maybe<R> defaultValue) {
		return build(function, Object.class, defaultValue);
	}
	
	/**
	 * Builds a new type map with the given upper bound and no default values using the given function.
	 *
	 * @param <T> Upper bound type.
	 * @param <R> Type of the values.
	 * @param function The function.
	 * @param upperBound The upper bound.
	 * @return The built map.
	 */
	public static <T, R> BaseObjectTypeFunction<T, R> build(final Function<? super Class<? extends T>, ? extends Maybe<R>> function, final Class<T> upperBound) {
		return build(function, upperBound, Maybe.<R>none());
	}
	
	/**
	 * Builds a new type map with the given upper bound and default value using the given function.
	 *
	 * @param <T> Upper bound type.
	 * @param <R> Type of the values.
	 * @param function The function.
	 * @param upperBound The upper bound.
	 * @param defaultValue The default value.
	 * @return The built map.
	 */
	public static <T, R> BaseObjectTypeFunction<T, R> build(final Function<? super Class<? extends T>, ? extends Maybe<R>> function, final Class<T> upperBound, final Maybe<R> defaultValue) {
		return new BaseObjectTypeFunction<T, R>(upperBound, defaultValue) {
			@Override
			protected Maybe<R> computeDiscrete(final Class<? extends T> type) {
				return function.evaluate(type);
			}
		};
	}
	
	/**
	 * Instantiates a new type map with the given upper bound and no defaut values.
	 * 
	 * @param upperBound Upper bound.
	 */
	public BaseObjectTypeFunction(final Class<T> upperBound) {
		this(upperBound, Maybe.<R>none());
	}
	
	/**
	 * Instantiates a new type map with the given upper bound and default value.
	 * 
	 * @param upperBound Upper bound.
	 * @param defaultValue Default value.
	 */
	public BaseObjectTypeFunction(final Class<T> upperBound, final Maybe<R> defaultValue) {
		super(Maybe.some(upperBound), defaultValue);
	}
	
	@Override
	protected boolean isSubTypeOf(final Class<? extends T> type1, final Class<? extends T> type2) {
		return type2.isAssignableFrom(type1);
	}
	
	@Override
	protected Collection<Class<? extends T>> getSuperTypes(final Class<? extends T> type) {
		// Filter according to the upper bound.
		final Class<? extends T> upperBound = _upperBound.asSome().getValue();
		final Function<Class<?>, Maybe<Class<? extends T>>> filter = new Function<Class<?>, Maybe<Class<? extends T>>>() {
			@Override
			@SuppressWarnings("unchecked")
			public Maybe<Class<? extends T>> evaluate(final Class<?> superType) {
				if (upperBound.isAssignableFrom(superType)) {
					return Maybe.<Class<? extends T>>some((Class<? extends T>) superType);
				} else {
					return Maybe.none();
				}
			}
		};
		
		// Get the super types.
		final Set<Class<? extends T>> superTypes = new HashSet<>();
		CollectionUtils.addAll(superTypes, MaybeUtils.fromNullable(type.getSuperclass()).flatMap(filter));
		CollectionUtils.addAll(superTypes, IterableUtils.flatMap(Arrays.asList(type.getInterfaces()), filter));
		return superTypes;
	}
}
