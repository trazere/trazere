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

/**
 * The {@link ReverseComparator} class is a comparator combinator which reverses the comparison order of the combined comparator.
 * 
 * @param <T> Type of the compared elements.
 */
public class ReverseComparator<T>
implements Comparator<T> {
	/**
	 * Build a comparator from the given comparator which applies the given ordering.
	 * <p>
	 * This methods simply returns the given comparator for the ascending order.
	 * 
	 * @param <T> Type of the compared objets.
	 * @param comparator Comparator to wrap.
	 * @param ascending Flag indicating the comparison order. <code>true</code> for ascending order, <code>false</code> for descending order.
	 * @return The built comparator.
	 */
	public static <T> Comparator<T> build(final Comparator<T> comparator, final boolean ascending) {
		assert null != comparator;
		
		// Build.
		return ascending ? comparator : new ReverseComparator<T>(comparator);
	}
	
	/** Reversed comparator. */
	protected final Comparator<T> _comparator;
	
	/**
	 * Instantiate a new reverse comparator with the given comparator.
	 * 
	 * @param comparator Reversed comparator.
	 */
	public ReverseComparator(final Comparator<T> comparator) {
		assert null != comparator;
		
		// Initialization.
		_comparator = comparator;
	}
	
	/**
	 * Get the comparator reversed by the receiver comparator.
	 * 
	 * @return The reversed comparator.
	 */
	public Comparator<T> getComparator() {
		return _comparator;
	}
	
	public int compare(final T object1, final T object2) {
		return -(_comparator.compare(object1, object2));
	}
	
	@Override
	public int hashCode() {
		final HashCode hashCode = new HashCode(this);
		hashCode.append(_comparator);
		return hashCode.get();
	}
	
	@Override
	public boolean equals(final Object object) {
		if (this == object) {
			return true;
		} else if (null != object && getClass().equals(object.getClass())) {
			final ReverseComparator<?> comparator = (ReverseComparator<?>) object;
			return _comparator.equals(comparator._comparator);
		} else {
			return false;
		}
	}
}
