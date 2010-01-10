/*
 *  Copyright 2006-2010 Julien Dufour
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
 * The {@link NullComparator} class adds support for comparison of <code>null</code> values to other comparators.
 * 
 * @param <T> Type of the compared objets.
 * @see LangUtils#compare(Object, Object, Comparator)
 */
public class NullComparator<T>
implements Comparator<T> {
	/**
	 * Build a comparator using the given comparator..
	 * 
	 * @param <T> Type of the compared objets.
	 * @param comparator The comparator.
	 * @return The built comparator.
	 */
	public static <T> Comparator<T> build(final Comparator<T> comparator) {
		return new NullComparator<T>(comparator);
	}
	
	/** The comparator. */
	protected final Comparator<T> _comparator;
	
	/**
	 * Instantiate a new comparator with the given comparator.
	 * 
	 * @param comparator The comparator.
	 */
	public NullComparator(final Comparator<T> comparator) {
		assert null != comparator;
		
		// Initialization.
		_comparator = comparator;
	}
	
	public int compare(final T object1, final T object2) {
		return LangUtils.compare(object1, object2, _comparator);
	}
}
