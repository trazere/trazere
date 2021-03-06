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

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * The {@link SequenceComparator} class provides comparators according to a sequence of comparators.
 * <p>
 * The next comparator of the sequence refines the order of the previous one in case of equality.
 * 
 * @param <T> Type of the values.
 * @deprecated Use {@link com.trazere.core.util.SequenceComparator}.
 */
@Deprecated
public class SequenceComparator<T>
implements Comparator<T> {
	/**
	 * Instantiates a new comparator with the given comparators.
	 * 
	 * @param comparators The sequence of comparators.
	 * @deprecated Use {@link com.trazere.core.util.SequenceComparator#SequenceComparator(List)}.
	 */
	@Deprecated
	public SequenceComparator(final List<? extends Comparator<? super T>> comparators) {
		assert null != comparators;
		
		// Initialization.
		_comparators = Collections.unmodifiableList(comparators);
	}
	
	/**
	 * Instantiates a new comparator with the given comparators.
	 * 
	 * @param comparators The sequence of comparators.
	 * @deprecated Use {@link com.trazere.core.util.SequenceComparator#SequenceComparator(Comparator...)}.
	 */
	@Deprecated
	public SequenceComparator(final Comparator<? super T>... comparators) {
		this(Arrays.asList(comparators));
	}
	
	// Comparator.
	
	/** The comparators ordered by priority. */
	protected final List<? extends Comparator<? super T>> _comparators;
	
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
