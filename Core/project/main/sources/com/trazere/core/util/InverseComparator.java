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

import com.trazere.core.lang.HashCode;
import java.util.Comparator;

/**
 * The {@link InverseComparator} class implements comparators according to the inverse order of some other comparator.
 * 
 * @param <T> Type of the values.
 * @since 2.0
 */
public class InverseComparator<T>
implements Comparator<T> {
	/**
	 * Instantiates a new inversed comparator.
	 * 
	 * @param comparator Comparator to inverse.
	 * @since 2.0
	 */
	public InverseComparator(final Comparator<? super T> comparator) {
		assert null != comparator;
		
		// Initialization.
		_comparator = comparator;
	}
	
	// Comparator.
	
	/**
	 * The inversed comparator.
	 * 
	 * @since 2.0
	 */
	protected final Comparator<? super T> _comparator;
	
	/**
	 * Gets the inversed comparator.
	 * 
	 * @return The inversed comparator.
	 * @since 2.0
	 */
	public Comparator<? super T> getComparator() {
		return _comparator;
	}
	
	@Override
	public int compare(final T object1, final T object2) {
		return -_comparator.compare(object1, object2);
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
			final InverseComparator<?> comparator = (InverseComparator<?>) object;
			return _comparator.equals(comparator._comparator);
		} else {
			return false;
		}
	}
}
