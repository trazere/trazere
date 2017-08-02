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
package com.trazere.core.util;

import java.util.Comparator;

/**
 * The {@link ComparatorUtils} class provides various utilities regarding {@link Comparator comparators}.
 * 
 * @see Comparator
 * @since 2.0
 */
public class ComparatorUtils {
	/**
	 * The {@link DummyComparable} interface provides a placeholder type for comparables.
	 * 
	 * @since 2.0
	 */
	public interface DummyComparable
	extends Comparable<DummyComparable> {
		// Nothing to do.
	}
	
	/**
	 * Compares the given optional values using the given comparator.
	 * <p>
	 * Absent values are considered less than available values. Available values are ordered according to the given comparator.
	 * 
	 * @param <T> Type of compared values.
	 * @param comparator Comparator to use.
	 * @param value1 First optional value to compare.
	 * @param value2 Second optional value to compare.
	 * @return The result of the comparison as defined by the {@link Comparator#compare(Object, Object)} method.
	 * @see Comparator#compare(Object, Object)
	 * @since 2.0
	 */
	public static <T> int compare(final Comparator<T> comparator, final Maybe<? extends T> value1, final Maybe<? extends T> value2) {
		if (value1.isNone()) {
			return value2.isNone() ? 0 : -1;
		} else {
			return value2.isNone() ? 1 : comparator.compare(value1.asSome().getValue(), value2.asSome().getValue());
		}
	}
	
	/**
	 * Safely compares the given values using the given comparator.
	 * <p>
	 * This method supports comparison of <code>null</code> values. <code>null</code> values are considered less than non <code>null</code> values.
	 * 
	 * @param <T> Type of compared values.
	 * @param comparator The comparator.
	 * @param value1 First unsafe value to compare. May be <code>null</code>.
	 * @param value2 Second unsafe value to compare. May be <code>null</code>.
	 * @return The result of the comparison as defined by the {@link Comparator#compare(Object, Object)} method.
	 * @see Comparable#compareTo(Object)
	 * @since 2.0
	 */
	public static <T> int safeCompare(final Comparator<T> comparator, final T value1, final T value2) {
		assert null != comparator;
		
		if (null == value1) {
			return null == value2 ? 0 : -1;
		} else {
			return null == value2 ? 1 : comparator.compare(value1, value2);
		}
	}
	
	private ComparatorUtils() {
		// Prevent instantiation.
	}
}
