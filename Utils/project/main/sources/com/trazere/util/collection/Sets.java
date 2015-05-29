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
package com.trazere.util.collection;

import com.trazere.util.type.Maybe;
import java.util.HashSet;
import java.util.Set;

/**
 * The {@link Sets} class provides various factories of sets.
 * 
 * @deprecated Use core.
 */
@Deprecated
public class Sets {
	/**
	 * Builds a set containing the given value if any.
	 * <p>
	 * This method instantiates a {@link HashSet}.
	 * 
	 * @param <T> Type of the the value.
	 * @param value The value.
	 * @return The built set.
	 * @deprecated Use {@link com.trazere.core.collection.Sets#fromIterable(Iterable)}.
	 */
	@Deprecated
	public static <T> Set<T> fromValue(final Maybe<T> value) {
		assert null != value;
		
		final Set<T> set = new HashSet<T>();
		if (value.isSome()) {
			set.add(value.asSome().getValue());
		}
		return set;
	}
	
	/**
	 * Builds a set containing the given value.
	 * <p>
	 * This method instantiates a {@link HashSet}.
	 * 
	 * @param <T> Type of the the value.
	 * @param value The value. May be <code>null</code>.
	 * @return The built set.
	 * @deprecated Use {@link com.trazere.core.collection.Sets#fromElement(Object)}.
	 */
	@Deprecated
	public static <T> Set<T> fromValue(final T value) {
		final Set<T> set = new HashSet<T>(1);
		set.add(value);
		return set;
	}
	
	/**
	 * Builds a set containing the given values.
	 * <p>
	 * This method instantiates a {@link HashSet}.
	 * 
	 * @param <T> Type of the the values.
	 * @param value1 The first value. May be <code>null</code>.
	 * @param value2 The second value. May be <code>null</code>.
	 * @return The built set.
	 * @deprecated Use {@link com.trazere.core.collection.Sets#fromElements(Object, Object)}.
	 */
	@Deprecated
	public static <T> Set<T> fromValues(final T value1, final T value2) {
		final Set<T> set = new HashSet<T>(2);
		set.add(value1);
		set.add(value2);
		return set;
	}
	
	/**
	 * Builds a set containing the given values.
	 * <p>
	 * This method instantiates a {@link HashSet}.
	 * 
	 * @param <T> Type of the the values.
	 * @param value1 The first value. May be <code>null</code>.
	 * @param value2 The second value. May be <code>null</code>.
	 * @param value3 The third value. May be <code>null</code>.
	 * @return The built set.
	 * @deprecated Use {@link com.trazere.core.collection.Sets#fromElements(Object, Object, Object)}.
	 */
	@Deprecated
	public static <T> Set<T> fromValues(final T value1, final T value2, final T value3) {
		final Set<T> set = new HashSet<T>(3);
		set.add(value1);
		set.add(value2);
		set.add(value3);
		return set;
	}
	
	/**
	 * Builds a set containing the given values.
	 * <p>
	 * This method instantiates a {@link HashSet}.
	 * 
	 * @param <T> Type of the the values.
	 * @param values The values. May be <code>null</code>.
	 * @return The built set.
	 * @deprecated Use {@link com.trazere.core.collection.Sets#fromElements(Object...)}.
	 */
	@Deprecated
	public static <T> Set<T> fromValues(final T... values) {
		assert null != values;
		
		final Set<T> set = new HashSet<T>(values.length);
		for (final T value : values) {
			set.add(value);
		}
		return set;
	}
	
	private Sets() {
		// Prevent instantiation.
	}
}
