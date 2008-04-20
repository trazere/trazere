/*
 *  Copyright 2008 Julien Dufour
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

import java.util.Comparator;
import java.util.List;

/**
 * The {@link MultipleComparator} class is a comparator combinator which compares according to multiple criterion.
 * <p>
 * The comparator applies the given comparators in order until one of them orders the given objects.
 * 
 * @param <T> Type of the compared objects.
 */
public class MultipleComparator<T>
implements Comparator<T> {
	/** Comparators to apply ordered by priority. */
	protected final Comparator<T>[] _comparators;
	
	/**
	 * Instantiate a new multiple criterion comparator with the given comparators.
	 * 
	 * @param comparators Comparators to apply ordered by priority.
	 */
	public MultipleComparator(final List<? extends Comparator<T>> comparators) {
		assert null != comparators;
		
		// Initialization.
		@SuppressWarnings("unchecked")
		final Comparator<T>[] comparators_ = new Comparator[comparators.size()];
		comparators.toArray(comparators_);
		_comparators = comparators_;
	}
	
	public int compare(final T object1, final T object2) {
		for (final Comparator<T> comparator : _comparators) {
			final int result = comparator.compare(object1, object2);
			if (0 != result) {
				return result;
			}
		}
		return 0;
	}
	
	@Override
	public int hashCode() {
		final HashCode hashCode = new HashCode(this);
		hashCode.append(_comparators);
		return hashCode.get();
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
