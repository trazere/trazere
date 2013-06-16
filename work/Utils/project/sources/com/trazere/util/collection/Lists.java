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
import java.util.ArrayList;
import java.util.List;

/**
 * The {@link Lists} class provides various factories of lists.
 * 
 * @see List
 */
public class Lists {
	/**
	 * Builds a new list containing the given value if any.
	 * <p>
	 * This method instantiates an {@link ArrayList}.
	 * 
	 * @param <V> Type of the the value.
	 * @param value The value.
	 * @return The built list.
	 */
	public static <V> List<V> fromValue(final Maybe<V> value) {
		assert null != value;
		
		final List<V> list = new ArrayList<V>();
		if (value.isSome()) {
			list.add(value.asSome().getValue());
		}
		return list;
	}
	
	/**
	 * Builds a new list containing the given value.
	 * <p>
	 * This method instantiates an {@link ArrayList}.
	 * 
	 * @param <V> Type of the the value.
	 * @param value The value. May be <code>null</code>.
	 * @return The built list.
	 */
	public static <V> List<V> fromValue(final V value) {
		final List<V> list = new ArrayList<V>(1);
		list.add(value);
		return list;
	}
	
	/**
	 * Builds a new list containing the given values.
	 * <p>
	 * This method instantiates an {@link ArrayList}.
	 * 
	 * @param <V> Type of the the values.
	 * @param value1 The first value. May be <code>null</code>.
	 * @param value2 The second value. May be <code>null</code>.
	 * @return The built list.
	 */
	public static <V> List<V> fromValues(final V value1, final V value2) {
		final List<V> list = new ArrayList<V>(2);
		list.add(value1);
		list.add(value2);
		return list;
	}
	
	/**
	 * Builds a new list containing the given values.
	 * <p>
	 * This method instantiates an {@link ArrayList}.
	 * 
	 * @param <V> Type of the the values.
	 * @param value1 The first value. May be <code>null</code>.
	 * @param value2 The second value. May be <code>null</code>.
	 * @param value3 The third value. May be <code>null</code>.
	 * @return The built list.
	 */
	public static <V> List<V> fromValues(final V value1, final V value2, final V value3) {
		final List<V> list = new ArrayList<V>(3);
		list.add(value1);
		list.add(value2);
		list.add(value3);
		return list;
	}
	
	/**
	 * Builds a new list containing the given values.
	 * <p>
	 * This method instantiates an {@link ArrayList}.
	 * 
	 * @param <V> Type of the the values.
	 * @param values The values. May be <code>null</code>.
	 * @return The built list.
	 */
	public static <V> List<V> fromValues(final V... values) {
		assert null != values;
		
		final List<V> list = new ArrayList<V>(values.length);
		for (final V value : values) {
			list.add(value);
		}
		return list;
	}
	
	private Lists() {
		// Prevent instantiation.
	}
}
