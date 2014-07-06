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
package com.trazere.core.util;

import com.trazere.core.lang.ComparableUtils;
import com.trazere.core.util.ComparatorUtils.DummyComparable;
import java.util.Comparator;
import java.util.List;

/**
 * The {@link Comparators} class provides various standard comparators.
 */
public class Comparators {
	/**
	 * Builds a comparator according to the natural order of comparable values.
	 * 
	 * @param <T> Type of the values.
	 * @return The built comparator.
	 * @see Comparable#compareTo(Object)
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Comparable<T>> Comparator<T> natural() {
		return (Comparator<T>) NATURAL;
	}
	
	private static final Comparator<? extends Comparable<?>> NATURAL = new Comparator<DummyComparable>() {
		@Override
		public int compare(final DummyComparable object1, final DummyComparable object2) {
			return object1.compareTo(object2);
		}
	};
	
	/**
	 * Builds a comparator according to the natural order of comparable values that supports <code>null</code> values.
	 * 
	 * @param <T> Type of the values.
	 * @return The built comparator.
	 * @see Comparable#compareTo(Object)
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Comparable<T>> Comparator<T> safeNatural() {
		return (Comparator<T>) SAFE_NATURAL;
	}
	
	private static final Comparator<? extends Comparable<?>> SAFE_NATURAL = new Comparator<DummyComparable>() {
		@Override
		public int compare(final DummyComparable object1, final DummyComparable object2) {
			return ComparableUtils.safeCompareTo(object1, object2);
		}
	};
	
	/**
	 * Builds a comparator according to the given sequence of comparators
	 * 
	 * @param <T> Type of the values.
	 * @param comparators The sequence of comparators.
	 * @return The built comparator.
	 * @see SequenceComparator
	 */
	public static <T> Comparator<T> sequence(final List<? extends Comparator<? super T>> comparators) {
		return new SequenceComparator<>(comparators);
	}
	
	/**
	 * Builds a comparator according to the given sequence of comparators
	 * 
	 * @param <T> Type of the values.
	 * @param comparators The sequence of comparators.
	 * @return The built comparator.
	 * @see SequenceComparator
	 */
	@SafeVarargs
	public static <T> Comparator<T> sequence(final Comparator<? super T>... comparators) {
		return new SequenceComparator<>(comparators);
	}
	
	private Comparators() {
		// Prevents instantiation.
	}
}
