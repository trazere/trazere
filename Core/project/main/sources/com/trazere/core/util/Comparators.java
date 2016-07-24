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

import com.trazere.core.lang.ComparableUtils;
import com.trazere.core.lang.HashCode;
import com.trazere.core.util.ComparatorUtils.DummyComparable;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * The {@link Comparators} class provides various factories of {@link Comparator comparators}.
 * 
 * @see Comparator
 * @since 2.0
 */
public class Comparators {
	/**
	 * Builds a comparator according to the natural order of comparable objects.
	 * 
	 * @param <T> Type of compared objects.
	 * @return The built comparator.
	 * @see Comparable#compareTo(Object)
	 * @since 2.0
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Comparable<T>> ExComparator<T> natural() {
		return (ExComparator<T>) NATURAL;
		//		return Comparable::compareTo;
	}
	
	private static final ExComparator<? extends Comparable<?>> NATURAL = (final DummyComparable o1, final DummyComparable o2) -> o1.compareTo(o2);
	
	/**
	 * Builds a comparator according to the natural order of comparable objects that supports <code>null</code> values.
	 * 
	 * @param <T> Type of the values.
	 * @return The built comparator.
	 * @see Comparable#compareTo(Object)
	 * @since 2.0
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Comparable<T>> ExComparator<T> safeNatural() {
		return (ExComparator<T>) SAFE_NATURAL;
		//		return ComparableUtils::safeCompareTo;
	}
	
	private static final ExComparator<? extends Comparable<?>> SAFE_NATURAL = (final DummyComparable o1, final DummyComparable o2) -> ComparableUtils.safeCompareTo(o1, o2);
	
	/**
	 * Builds a comparator according to the given sequence of comparators
	 * <p>
	 * The succequents comparators of the sequence refine the order of the previous ones in case of equality.
	 * 
	 * @param <T> Type of the values.
	 * @param comparators The sequence of comparators.
	 * @return The built comparator.
	 * @see SequenceComparator
	 * @since 2.0
	 */
	@SafeVarargs
	public static <T> ExComparator<T> sequence(final Comparator<? super T>... comparators) {
		return new SequenceComparator<>(Arrays.asList(comparators));
	}
	
	/**
	 * Builds a comparator according to the given sequence of comparators
	 * <p>
	 * The succequents comparators of the sequence refine the order of the previous ones in case of equality.
	 * 
	 * @param <T> Type of the values.
	 * @param comparators The sequence of comparators.
	 * @return The built comparator.
	 * @see SequenceComparator
	 * @since 2.0
	 */
	public static <T> ExComparator<T> sequence(final List<? extends Comparator<? super T>> comparators) {
		return new SequenceComparator<>(comparators);
	}
	
	private static class SequenceComparator<T>
	implements ExComparator<T> {
		protected final List<? extends Comparator<? super T>> _comparators;
		
		public SequenceComparator(final List<? extends Comparator<? super T>> comparators) {
			assert null != comparators;
			
			// Initialization.
			_comparators = Collections.unmodifiableList(comparators);
		}
		
		// Comparator.
		
		@Override
		public int compare(final T object1, final T object2) {
			for (final Comparator<? super T> comparator : _comparators) {
				final int result = comparator.compare(object1, object2);
				if (0 != result) {
					return result;
				}
			}
			return 0;
		}
		
		// Object.
		
		@Override
		public int hashCode() {
			final HashCode result = new HashCode(this);
			result.append(_comparators);
			return result.get();
		}
		
		@Override
		public boolean equals(final Object object) {
			if (this == object) {
				return true;
			} else if (null != object && getClass().equals(object.getClass())) {
				final SequenceComparator<?> comparator = (SequenceComparator<?>) object;
				return _comparators.equals(comparator._comparators);
			} else {
				return false;
			}
		}
	}
	
	private Comparators() {
		// Prevents instantiation.
	}
}
