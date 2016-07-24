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
import com.trazere.core.lang.HashCode;
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
	
	/**
	 * Gets the least of the given values according to the given comparator.
	 *
	 * @param <T> Type of the values.
	 * @param comparator Comparator to use.
	 * @param value1 First value.
	 * @param value2 Second value.
	 * @return The least value.
	 * @since 2.0
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
	 * @since 2.0
	 */
	public static <T> T greatest(final Comparator<? super T> comparator, final T value1, final T value2) {
		return comparator.compare(value1, value2) >= 0 ? value1 : value2;
	}
	
	/**
	 * Derives a comparator that supports <code>null</code> values from the given comparator.
	 * <p>
	 * <code>null</code> values are considered less than non <code>null</code> values.
	 * 
	 * @param <T> Type of compared values.
	 * @param comparator Unsafe comparator to derive.
	 * @return The built comparator.
	 * @see ComparatorUtils#safeCompare(Comparator, Object, Object)
	 * @since 2.0
	 */
	@SuppressWarnings("unchecked")
	public static <T> ExComparator<T> safe(final Comparator<? super T> comparator) {
		assert null != comparator;
		
		return comparator instanceof SafeComparator<?> ? (SafeComparator<T>) comparator : new SafeComparator<>(comparator);
	}
	
	private static class SafeComparator<T>
	implements ExComparator<T> {
		protected final Comparator<? super T> _comparator;
		
		public SafeComparator(final Comparator<? super T> comparator) {
			assert null != comparator;
			
			// Initialization.
			_comparator = comparator;
		}
		
		// Comparator.
		
		@Override
		public int compare(final T o1, final T o2) {
			return ComparatorUtils.safeCompare(_comparator, o1, o2);
		}
		
		// Object.
		
		@Override
		public int hashCode() {
			final HashCode result = new HashCode(this);
			result.append(_comparator);
			return result.get();
		}
		
		@Override
		public boolean equals(final Object object) {
			if (this == object) {
				return true;
			} else if (null != object && getClass().equals(object.getClass())) {
				final SafeComparator<?> comparator = (SafeComparator<?>) object;
				return _comparator.equals(comparator._comparator);
			} else {
				return false;
			}
		}
	}
	
	/**
	 * Derives a comparator according to the inverse order of the given comparator.
	 * 
	 * @param <T> Type of compared values.
	 * @param comparator Comparator to inverse.
	 * @return The built comparator.
	 * @since 2.0
	 */
	@SuppressWarnings("unchecked")
	public static <T> ExComparator<T> inversed(final Comparator<? super T> comparator) {
		assert null != comparator;
		
		return comparator instanceof InversedComparator<?> ? ExComparator.build((Comparator<T>) ((InversedComparator<T>) comparator)._comparator) : new InversedComparator<>(comparator);
	}
	
	private static class InversedComparator<T>
	implements ExComparator<T> {
		protected final Comparator<? super T> _comparator;
		
		public InversedComparator(final Comparator<? super T> comparator) {
			assert null != comparator;
			
			// Initialization.
			_comparator = comparator;
		}
		
		// Comparator.
		
		@Override
		public int compare(final T o1, final T o2) {
			return -(_comparator.compare(o1, o2));
		}
		
		// Object.
		
		@Override
		public int hashCode() {
			final HashCode result = new HashCode(this);
			result.append(_comparator);
			return result.get();
		}
		
		@Override
		public boolean equals(final Object object) {
			if (this == object) {
				return true;
			} else if (null != object && getClass().equals(object.getClass())) {
				final InversedComparator<?> comparator = (InversedComparator<?>) object;
				return _comparator.equals(comparator._comparator);
			} else {
				return false;
			}
		}
	}
	
	/**
	 * Derives a comparator according to the direct or inverse order of the given comparator.
	 * 
	 * @param <T> Type of compared values.
	 * @param comparator Comparator to inverse.
	 * @param inverse Indicates whether to inverse the order or not.
	 * @return The built comparator.
	 * @since 2.0
	 */
	public static <T> ExComparator<T> inversed(final Comparator<T> comparator, final boolean inverse) {
		return inverse ? inversed(comparator) : ExComparator.build(comparator);
	}
	
	/**
	 * Derives a comparator from the given comparator that transforms the values to compare using the given function.
	 * 
	 * @param <T> Type of compared values.
	 * @param <TT> Type of the transformed compared values.
	 * @param comparator Comparator to use.
	 * @param function Function to use to transform the values to compare.
	 * @return The built comparator.
	 * @since 2.0
	 */
	public static <T, TT> ExComparator<TT> mapping(final Comparator<? super T> comparator, final Function<? super TT, ? extends T> function) {
		assert null != comparator;
		assert null != function;
		
		return (o1, o2) -> comparator.compare(function.evaluate(o1), function.evaluate(o2));
	}
	
	private ComparatorUtils() {
		// Prevent instantiation.
	}
}
