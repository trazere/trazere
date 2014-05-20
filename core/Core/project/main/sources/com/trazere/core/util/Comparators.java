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

import com.trazere.core.lang.LangUtils;
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
	 * Builds a comparator that supports <code>null</code> values.
	 * <p>
	 * <code>null</code> values are less than non <code>null</code> values.
	 * 
	 * @param <T> Type of the values.
	 * @param comparator The unsafe comparator.
	 * @return The built comparator.
	 * @see LangUtils#safeCompare(Comparator, Object, Object)
	 */
	public static <T> Comparator<T> safe(final Comparator<? super T> comparator) {
		assert null != comparator;
		
		return new Comparator<T>() {
			@Override
			public int compare(final T object1, final T object2) {
				return LangUtils.safeCompare(comparator, object1, object2);
			}
		};
	}
	
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
			return LangUtils.safeCompare(object1, object2);
		}
	};
	
	//	/**
	//	 * Builds a comparator of {@link Maybe} instances according to the given order for values.
	//	 * <p>
	//	 * {@link None} instances are less than {@link Some} instances.
	//	 * 
	//	 * @param <T> Type of the values.
	//	 * @param comparator The comparator of the wrapped values.
	//	 * @return The built comparator.
	//	 * @see TypeUtils#compare(Comparator, Maybe, Maybe)
	//	 */
	//	public static <T> Comparator<Maybe<T>> maybe(final Comparator<? super T> comparator) {
	//		assert null != comparator;
	//		
	//		return new Comparator<Maybe<T>>() {
	//			@Override
	//			public int compare(final Maybe<T> value1, final Maybe<T> value2) {
	//				return TypeUtils.compare(comparator, value1, value2);
	//			}
	//		};
	//	}
	
	/**
	 * Builds a comparator according to the inverse order of the given comparator.
	 * 
	 * @param <T> Type of the values.
	 * @param comparator The inversed comparator.
	 * @return The built comparator.
	 * @see InverseComparator
	 */
	public static <T> Comparator<T> inverse(final Comparator<? super T> comparator) {
		return new InverseComparator<T>(comparator);
	}
	
	/**
	 * Builds a comparator according to the direct or inverse order of the given comparator.
	 * 
	 * @param <T> Type of the values.
	 * @param comparator Comparator to use.
	 * @param inverse Indicates whether to inverse the order or not.
	 * @return The built comparator.
	 */
	public static <T> Comparator<T> inverse(final Comparator<T> comparator, final boolean inverse) {
		return inverse ? inverse(comparator) : comparator;
	}
	
	/**
	 * Builds a comparator according to the given sequence of comparators
	 * 
	 * @param <T> Type of the values.
	 * @param comparators The sequence of comparators.
	 * @return The built comparator.
	 * @see SequenceComparator
	 */
	public static <T> Comparator<T> sequence(final List<? extends Comparator<? super T>> comparators) {
		return new SequenceComparator<T>(comparators);
	}
	
	/**
	 * Builds a comparator according to the given sequence of comparators
	 * 
	 * @param <T> Type of the values.
	 * @param comparators The sequence of comparators.
	 * @return The built comparator.
	 * @see SequenceComparator
	 */
	public static <T> Comparator<T> sequence(final Comparator<? super T>... comparators) {
		return new SequenceComparator<T>(comparators);
	}
	
	private Comparators() {
		// Prevents instantiation.
	}
}
