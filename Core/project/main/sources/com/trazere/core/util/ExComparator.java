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
 * The {@link ExComparator} interfaces defines {@link Comparator comparators} extended with various utilities.
 * 
 * @param <T> Type of compared values.
 * @see Comparator
 * @since 2.0
 */
@FunctionalInterface
public interface ExComparator<T>
extends Comparator<T> {
	// TODO: move to Comparators ?
	// TODO: rename -> fromComparator ?
	
	/**
	 * Builds an extended view of the given comparator.
	 * 
	 * @param <T> Type of compared values.
	 * @param comparator Comparator to wrap.
	 * @return The extended view of the given comparator, or the given comparator when it is already an extented view.
	 * @since 2.0
	 */
	public static <T> ExComparator<T> build(final Comparator<T> comparator) {
		assert null != comparator;
		
		if (comparator instanceof ExComparator<?>) {
			return (ExComparator<T>) comparator;
		} else {
			return new ComparatorDecorator<>(comparator);
		}
	}
	
	// ExComparator.
	
	/**
	 * Safely compares the given values using this comparator.
	 * <p>
	 * This method supports comparison of <code>null</code> values. <code>null</code> values are considered less than non <code>null</code> values.
	 * 
	 * @param value1 First unsafe value to compare. May be <code>null</code>.
	 * @param value2 Second unsafe value to compare. May be <code>null</code>.
	 * @return The result of the comparison as defined by the {@link Comparator#compare(Object, Object)} method.
	 * @see ComparatorUtils#safeCompare(Comparator, Object, Object)
	 * @since 2.0
	 */
	default int safeCompare(final T value1, final T value2) {
		return ComparatorUtils.safeCompare(this, value1, value2);
	}
	
	/**
	 * Gets the least of the given values according to this comparator.
	 *
	 * @param value1 First value.
	 * @param value2 Second value.
	 * @return The least value.
	 * @see ComparatorUtils#least(Comparator, Object, Object)
	 * @since 2.0
	 */
	default T least(final T value1, final T value2) {
		return ComparatorUtils.least(this, value1, value2);
	}
	
	/**
	 * Gets the greatest of the given values according to this comparator.
	 *
	 * @param value1 First value.
	 * @param value2 Second value.
	 * @return The greatest value.
	 * @see ComparatorUtils#greatest(Comparator, Object, Object)
	 * @since 2.0
	 */
	default T greatest(final T value1, final T value2) {
		return ComparatorUtils.greatest(this, value1, value2);
	}
	
	/**
	 * Derives a comparator that supports <code>null</code> values from this comparator.
	 * <p>
	 * <code>null</code> values are considered less than non <code>null</code> values.
	 * 
	 * @return The built comparator.
	 * @see ComparatorUtils#safe(Comparator)
	 * @since 2.0
	 */
	default ExComparator<T> safe() {
		return ComparatorUtils.safe(this);
	}
	
	/**
	 * Derives a comparator according to the inverse order of this comparator.
	 * 
	 * @return The built comparator.
	 * @see ComparatorUtils#inversed(Comparator)
	 * @since 2.0
	 */
	default ExComparator<T> inversed() {
		return ComparatorUtils.inversed(this);
	}
	
	/**
	 * Derives a comparator from this comparator that transforms the values to compare using the given function.
	 * 
	 * @param <TT> Type of the transformed compared values.
	 * @param function Function to use to transform the values to compare.
	 * @return The built comparator.
	 * @see ComparatorUtils#mapping(Comparator, Function)
	 * @since 2.0
	 */
	default <TT> ExComparator<TT> mapping(final Function<? super TT, ? extends T> function) {
		return ComparatorUtils.mapping(this, function);
	}
}
