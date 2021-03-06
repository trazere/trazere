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
package com.trazere.util.lang;

import com.trazere.util.function.Function1;
import com.trazere.util.type.Maybe;
import com.trazere.util.type.Maybe.None;
import com.trazere.util.type.Maybe.Some;
import com.trazere.util.type.TypeUtils;
import java.util.Comparator;
import java.util.List;

/**
 * The {@link Comparators} class provides various standard comparators.
 * 
 * @deprecated Use {@link com.trazere.core}.
 */
@Deprecated
public class Comparators {
	/**
	 * Builds a comparator according to the natural order of comparable values.
	 * 
	 * @param <T> Type of the values.
	 * @return The built comparator.
	 * @see Comparable#compareTo(Object)
	 * @deprecated Use {@link com.trazere.core.util.Comparators#natural()}.
	 */
	@Deprecated
	public static <T extends Comparable<T>> Comparator<T> natural() {
		return new Comparator<T>() {
			@Override
			public int compare(final T object1, final T object2) {
				assert null != object1;
				
				return object1.compareTo(object2);
			}
		};
	}
	
	/**
	 * Builds a comparator that supports <code>null</code> values.
	 * <p>
	 * <code>null</code> values are less than non <code>null</code> values.
	 * 
	 * @param <T> Type of the values.
	 * @param comparator The unsafe comparator.
	 * @return The built comparator.
	 * @see LangUtils#safeCompare(Comparator, Object, Object)
	 * @deprecated Use {@link com.trazere.core.util.ComparatorUtils#safe(Comparator)}.
	 */
	@Deprecated
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
	 * @deprecated Use {@link com.trazere.core.util.Comparators#safeNatural()}.
	 */
	@Deprecated
	public static <T extends Comparable<T>> Comparator<T> safeNatural() {
		return new Comparator<T>() {
			@Override
			public int compare(final T object1, final T object2) {
				return LangUtils.safeCompare(object1, object2);
			}
		};
	}
	
	// TODO: move to type
	/**
	 * Builds a comparator of {@link Maybe} instances according to the given order for values.
	 * <p>
	 * {@link None} instances are less than {@link Some} instances.
	 * 
	 * @param <T> Type of the values.
	 * @param comparator The comparator of the wrapped values.
	 * @return The built comparator.
	 * @see TypeUtils#compare(Comparator, Maybe, Maybe)
	 * @deprecated Use {@link com.trazere.core.util.MaybeComparators#maybe(Comparator)}.
	 */
	@Deprecated
	public static <T> Comparator<Maybe<T>> maybe(final Comparator<? super T> comparator) {
		assert null != comparator;
		
		return new Comparator<Maybe<T>>() {
			@Override
			public int compare(final Maybe<T> value1, final Maybe<T> value2) {
				return TypeUtils.compare(comparator, value1, value2);
			}
		};
	}
	
	/**
	 * Builds a comparator according to the inverse order of the given comparator.
	 * 
	 * @param <T> Type of the values.
	 * @param comparator The inversed comparator.
	 * @return The built comparator.
	 * @see InverseComparator
	 * @deprecated Use {@link com.trazere.core.util.ComparatorUtils#inverse(Comparator)}.
	 */
	@Deprecated
	public static <T> Comparator<T> inverse(final Comparator<? super T> comparator) {
		assert null != comparator;
		
		return new InverseComparator<T>(comparator);
	}
	
	/**
	 * Builds a comparator according to the given sequence of comparators
	 * 
	 * @param <T> Type of the values.
	 * @param comparators The sequence of comparators.
	 * @return The built comparator.
	 * @see SequenceComparator
	 * @deprecated Use {@link com.trazere.core.util.Comparators#sequence(List)}.
	 */
	@Deprecated
	public static <T> Comparator<T> sequence(final List<? extends Comparator<? super T>> comparators) {
		assert null != comparators;
		
		return new SequenceComparator<T>(comparators);
	}
	
	/**
	 * Builds a comparator according to the given sequence of comparators
	 * 
	 * @param <T> Type of the values.
	 * @param comparators The sequence of comparators.
	 * @return The built comparator.
	 * @see SequenceComparator
	 * @deprecated Use {@link com.trazere.core.util.Comparators#sequence(Comparator...)}.
	 */
	@Deprecated
	public static <T> Comparator<T> sequence(final Comparator<? super T>... comparators) {
		return new SequenceComparator<T>(comparators);
	}
	
	/**
	 * Transforms the given comparator using the given function.
	 * 
	 * @param <T1> Type of the values.
	 * @param <T2> Type of the mapped values.
	 * @param function The function.
	 * @param comparator The comparator.
	 * @return The built comparator.
	 * @see MapComparator
	 * @deprecated Use {@link com.trazere.core.util.ComparatorUtils#map(Comparator, com.trazere.core.functional.Function)}.
	 */
	@Deprecated
	public static <T1, T2> Comparator<T1> map(final Function1<? super T1, ? extends T2, ? extends RuntimeException> function, final Comparator<? super T2> comparator) {
		assert null != function;
		assert null != comparator;
		
		return new MapComparator<T1, T2>(comparator) {
			@Override
			protected T2 mapValue(final T1 object) {
				return function.evaluate(object);
			}
		};
	}
	
	private Comparators() {
		// Prevents instantiation.
	}
}
