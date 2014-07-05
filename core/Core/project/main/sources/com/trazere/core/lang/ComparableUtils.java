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
package com.trazere.core.lang;

/**
 * The {@link ComparableUtils} class provides various utilities regarding {@link Comparable comparables}.
 * 
 * @see Comparable
 */
public class ComparableUtils {
	/**
	 * Safely compares the given comparable values.
	 * <p>
	 * This method supports comparison of <code>null</code> values. <code>null</code> values are considered as less than non <code>null</code> values.
	 * 
	 * @param <T> Type of the values.
	 * @param object1 The first value. May be <code>null</code>.
	 * @param object2 The second value. May be <code>null</code>.
	 * @return The result of the comparison as defined by the {@link Comparable#compareTo(Object)} method.
	 * @see Comparable#compareTo(Object)
	 */
	public static <T extends Comparable<T>> int safeCompareTo(final T object1, final T object2) {
		if (null == object1) {
			return null == object2 ? 0 : -1;
		} else {
			return null == object2 ? 1 : object1.compareTo(object2);
		}
	}
	
	/**
	 * Gets the least given values according to their natural order.
	 *
	 * @param <T> Type of the values.
	 * @param value1 First value.
	 * @param value2 Second value.
	 * @return The least value.
	 */
	public static <T extends Comparable<T>> T least(final T value1, final T value2) {
		return value1.compareTo(value2) <= 0 ? value1 : value2;
	}
	
	/**
	 * Gets the greatest given values according to their natural order.
	 *
	 * @param <T> Type of the values.
	 * @param value1 First value.
	 * @param value2 Second value.
	 * @return The greatest value.
	 */
	public static <T extends Comparable<T>> T greatest(final T value1, final T value2) {
		return value1.compareTo(value2) >= 0 ? value1 : value2;
	}
	
	private ComparableUtils() {
		// Prevent instantiation.
	}
}
