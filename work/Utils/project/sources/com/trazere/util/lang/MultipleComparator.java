/*
 *  Copyright 2006-2012 Julien Dufour
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

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * The {@link MultipleComparator} class provides comparators which compare according to multiple criterion.
 * <p>
 * The comparator applies the its criterion in order until one of them orders the given objects.
 * 
 * @param <T> Type of the compared objects.
 */
public class MultipleComparator<T>
implements Comparator<T> {
	/**
	 * Builds a new comparator with the given comparators.
	 * 
	 * @param <T> Type of the compared objects.
	 * @param comparators The comparators ordered by priority.
	 * @return The built comparator.
	 */
	public static <T> MultipleComparator<T> build(final List<? extends Comparator<? super T>> comparators) {
		return new MultipleComparator<T>(comparators);
	}
	
	/**
	 * Builds a new comparator with the given comparators.
	 * 
	 * @param <T> Type of the compared objects.
	 * @param comparators The comparators ordered by priority.
	 * @return The built comparator.
	 */
	public static <T> MultipleComparator<T> build(final Comparator<? super T>... comparators) {
		return new MultipleComparator<T>(comparators);
	}
	
	/**
	 * Instantiates a new comparator with the given comparators.
	 * 
	 * @param comparators The comparators ordered by priority.
	 */
	public MultipleComparator(final List<? extends Comparator<? super T>> comparators) {
		assert null != comparators;
		
		// Initialization.
		_comparators = Collections.unmodifiableList(comparators);
	}
	
	/**
	 * Instantiates a new comparator with the given comparators.
	 * 
	 * @param comparators The comparators ordered by priority.
	 */
	public MultipleComparator(final Comparator<? super T>... comparators) {
		this(Arrays.asList(comparators));
	}
	
	// Comparator.
	
	/** The comparators ordered by priority. */
	protected final List<? extends Comparator<? super T>> _comparators;
	
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
			final MultipleComparator<?> comparator = (MultipleComparator<?>) object;
			return _comparators.equals(comparator._comparators);
		} else {
			return false;
		}
	}
}