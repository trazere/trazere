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
package com.trazere.util.collection;

import com.trazere.util.function.Function1;
import com.trazere.util.function.FunctionUtils;
import com.trazere.util.lang.InternalException;
import com.trazere.util.type.Maybe;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * The {@link LazyObjectTypeMap} abstract class represents lazy maps from Java class hierarchies to values.
 * 
 * @param <T> Upper bound type.
 * @param <V> Type of the values.
 * @param <X> Type of the exceptions.
 * @deprecated Use {@link com.trazere.core.functional.BaseObjectTypeFunction}.
 */
@Deprecated
public abstract class LazyObjectTypeMap<T, V, X extends Exception>
extends LazyTypeMap<Class<? extends T>, V, X>
implements ObjectTypeMap<T, Maybe<? extends V>, X> {
	// HACK: named buildObject to work around javac bug
	/**
	 * Builds a new type map with no upper bounds and defaut values using the given function.
	 * 
	 * @param <V> Type of the values.
	 * @param <X> Type of the exceptions.
	 * @param function The function.
	 * @return The built map.
	 * @deprecated Use {@link com.trazere.core.functional.BaseObjectTypeFunction#build(com.trazere.core.functional.Function)}.
	 */
	@Deprecated
	public static <V, X extends Exception> LazyObjectTypeMap<Object, V, X> buildObject(final Function1<? super Class<?>, ? extends Maybe<? extends V>, ? extends X> function) {
		return buildObject(function, Object.class, Maybe.<V>none());
	}
	
	// HACK: named buildObject to work around javac bug
	/**
	 * Builds a new type map with no upper bounds and the given default value using the given function.
	 * 
	 * @param <V> Type of the values.
	 * @param <X> Type of the exceptions.
	 * @param function The function.
	 * @param defaultValue The default value.
	 * @return The built map.
	 * @deprecated Use {@link com.trazere.core.functional.BaseObjectTypeFunction#build(com.trazere.core.functional.Function, com.trazere.core.util.Maybe)}.
	 */
	@Deprecated
	public static <V, X extends Exception> LazyObjectTypeMap<Object, V, X> buildObject(final Function1<? super Class<?>, ? extends Maybe<? extends V>, ? extends X> function, final Maybe<? extends V> defaultValue) {
		return buildObject(function, Object.class, defaultValue);
	}
	
	// HACK: named buildObject to work around javac bug
	/**
	 * Builds a new type map with the given upper bound and default value using the given function.
	 * 
	 * @param <T> Upper bound type.
	 * @param <V> Type of the values.
	 * @param <X> Type of the exceptions.
	 * @param function The function.
	 * @param upperBound The upper bound.
	 * @return The built map.
	 * @deprecated Use {@link com.trazere.core.functional.BaseObjectTypeFunction#build(com.trazere.core.functional.Function, Class)}.
	 */
	@Deprecated
	public static <T, V, X extends Exception> LazyObjectTypeMap<T, V, X> buildObject(final Function1<? super Class<? extends T>, ? extends Maybe<? extends V>, ? extends X> function, final Class<T> upperBound) {
		return buildObject(function, upperBound, Maybe.<V>none());
	}
	
	// HACK: named buildObject to work around javac bug
	/**
	 * Builds a new type map with the given upper bound and default value using the given function.
	 * 
	 * @param <T> Upper bound type.
	 * @param <V> Type of the values.
	 * @param <X> Type of the exceptions.
	 * @param function The function.
	 * @param upperBound The upper bound.
	 * @param defaultValue The default value.
	 * @return The built map.
	 * @deprecated Use
	 *             {@link com.trazere.core.functional.BaseObjectTypeFunction#build(com.trazere.core.functional.Function, Class, com.trazere.core.util.Maybe)}.
	 */
	@Deprecated
	public static <T, V, X extends Exception> LazyObjectTypeMap<T, V, X> buildObject(final Function1<? super Class<? extends T>, ? extends Maybe<? extends V>, ? extends X> function, final Class<T> upperBound, final Maybe<? extends V> defaultValue) {
		return new LazyObjectTypeMap<T, V, X>(upperBound, defaultValue) {
			@Override
			protected Maybe<? extends V> computeDiscrete(final Class<? extends T> type)
			throws X {
				return function.evaluate(type);
			}
		};
	}
	
	/**
	 * Instantiates a new type map with no upper bounds and defaut values.
	 * 
	 * @param upperBound The upper bound.
	 * @deprecated Use {@link com.trazere.core.functional.BaseObjectTypeFunction#BaseObjectTypeFunction(Class)}.
	 */
	@Deprecated
	public LazyObjectTypeMap(final Class<T> upperBound) {
		this(upperBound, Maybe.<V>none());
	}
	
	/**
	 * Instantiates a new type map with the given upper bound and default value.
	 * 
	 * @param upperBound The upper bound.
	 * @param defaultValue The default value.
	 * @deprecated Use {@link com.trazere.core.functional.BaseObjectTypeFunction#BaseObjectTypeFunction(Class, com.trazere.core.util.Maybe)}.
	 */
	@Deprecated
	public LazyObjectTypeMap(final Class<T> upperBound, final Maybe<? extends V> defaultValue) {
		super(Maybe.some(upperBound), defaultValue);
	}
	
	@Override
	protected boolean isSubTypeOf(final Class<? extends T> type1, final Class<? extends T> type2) {
		return type2.isAssignableFrom(type1);
	}
	
	@Override
	protected Collection<Class<? extends T>> getSuperTypes(final Class<? extends T> type) {
		assert null != type;
		
		// Filter according to the upper bound.
		final Class<? extends T> upperBound = _upperBound.asSome().getValue();
		final Function1<Class<?>, Maybe<Class<? extends T>>, InternalException> filter = new Function1<Class<?>, Maybe<Class<? extends T>>, InternalException>() {
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
		final Set<Class<? extends T>> superTypes = new HashSet<Class<? extends T>>();
		CollectionUtils.add(superTypes, Maybe.fromValue(type.getSuperclass()).extract(filter));
		FunctionUtils.extract(filter, Arrays.asList(type.getInterfaces()), superTypes);
		return superTypes;
	}
}
