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

import com.trazere.core.functional.Function;
import java.util.Comparator;

/**
 * The {@link ComparatorUtils} class provides various utilities regarding {@link Comparator comparators}.
 * 
 * @see Comparator
 * @since 1.0
 */
public class ComparatorUtils {
	/**
	 * The {@link DummyComparable} interface provides a placeholder type for comparables.
	 * 
	 * @since 1.0
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
	 * @param <T> Type of the values.
	 * @param comparator Comparator of the values.
	 * @param value1 First value to compare.
	 * @param value2 Second value to compare.
	 * @return The result of the comparison as defined by the {@link Comparator#compare(Object, Object)} method.
	 * @see Comparator#compare(Object, Object)
	 * @since 1.0
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
	 * @param <T> Type of the values.
	 * @param comparator The comparator.
	 * @param value1 First unsafe value to compare. May be <code>null</code>.
	 * @param value2 Second unsafe value to compare. May be <code>null</code>.
	 * @return The result of the comparison as defined by the {@link Comparator#compare(Object, Object)} method.
	 * @see Comparable#compareTo(Object)
	 * @since 1.0
	 */
	public static <T> int safeCompare(final Comparator<T> comparator, final T value1, final T value2) {
		assert null != comparator;
		
		if (null == value1) {
			return null == value2 ? 0 : -1;
		} else {
			return null == value2 ? 1 : comparator.compare(value1, value2);
		}
	}
	
	/**
	 * Gets the least of the given values according to the given comparator.
	 *
	 * @param <T> Type of the values.
	 * @param comparator Comparator to use.
	 * @param value1 First value.
	 * @param value2 Second value.
	 * @return The least value.
	 * @since 1.0
	 */
	public static <T> T least(final Comparator<? super T> comparator, final T value1, final T value2) {
		return comparator.compare(value1, value2) <= 0 ? value1 : value2;
	}
	
	/**
	 * Gets the greatest of the given values according to the given comparator.
	 *
	 * @param <T> Type of the values.
	 * @param comparator Comparator to use.
	 * @param value1 First value.
	 * @param value2 Second value.
	 * @return The greatest value.
	 * @since 1.0
	 */
	public static <T> T greatest(final Comparator<? super T> comparator, final T value1, final T value2) {
		return comparator.compare(value1, value2) >= 0 ? value1 : value2;
	}
	
	/**
	 * Derives a safe comparator from the given comparator.
	 * <p>
	 * The derived comparator supports comparison of <code>null</code> values. <code>null</code> values are considered less than non <code>null</code> values.
	 * 
	 * @param <T> Type of the values.
	 * @param comparator Unsafe comparator to derive.
	 * @return The built comparator.
	 * @see ComparatorUtils#safeCompare(Comparator, Object, Object)
	 * @since 1.0
	 */
	public static <T> Comparator<T> safe(final Comparator<? super T> comparator) {
		assert null != comparator;
		
		return (object1, object2) -> ComparatorUtils.safeCompare(comparator, object1, object2);
	}
	
	/**
	 * Derives a comparator according to the inverse order of the given comparator.
	 * 
	 * @param <T> Type of the values.
	 * @param comparator Comparator to inverse.
	 * @return The built comparator.
	 * @see InverseComparator
	 * @since 1.0
	 */
	public static <T> Comparator<T> inverse(final Comparator<? super T> comparator) {
		return new InverseComparator<>(comparator);
	}
	
	/**
	 * Derives a comparator according to the direct or inverse order of the given comparator.
	 * 
	 * @param <T> Type of the values.
	 * @param comparator Comparator to inverse.
	 * @param inverse Indicates whether to inverse the order or not.
	 * @return The built comparator.
	 * @since 1.0
	 */
	public static <T> Comparator<T> inverse(final Comparator<T> comparator, final boolean inverse) {
		return inverse ? inverse(comparator) : comparator;
	}
	
	/**
	 * Maps the given comparator using the given function.
	 * 
	 * @param <T1> Type of the values.
	 * @param <T2> Type of the mapped values.
	 * @param comparator Comparator to map.
	 * @param function Mapping function to use.
	 * @return The built comparator.
	 * @see MapComparator
	 * @since 1.0
	 */
	public static <T1, T2> Comparator<T1> map(final Comparator<? super T2> comparator, final Function<? super T1, ? extends T2> function) {
		assert null != function;
		
		return new MapComparator<T1, T2>(comparator) {
			@Override
			protected T2 mapValue(final T1 object) {
				return function.evaluate(object);
			}
		};
	}
	
	private ComparatorUtils() {
		// Prevent instantiation.
	}
}
