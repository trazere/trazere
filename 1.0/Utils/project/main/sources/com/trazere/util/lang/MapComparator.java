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

import java.util.Comparator;

/**
 * The {@link MapComparator} abstract class is a comparator combinator which transforms the compared values.
 * 
 * @param <T> Type of the values.
 * @param <V> Type of the mapped values.
 * @deprecated Use {@link com.trazere.core.util.MapComparator}.
 */
@Deprecated
public abstract class MapComparator<T, V>
implements Comparator<T> {
	/** The comparator to use. */
	protected final Comparator<? super V> _comparator;
	
	/**
	 * Instantiate a new comparator.
	 * 
	 * @param comparator The comparator to use. May be <code>null</code>.
	 */
	public MapComparator(final Comparator<? super V> comparator) {
		assert null != comparator;
		
		// Initialization.
		_comparator = comparator;
	}
	
	@Override
	public int compare(final T object1, final T object2) {
		return _comparator.compare(mapValue(object1), mapValue(object2));
	}
	
	/**
	 * Computes the object to use for comparison for the given object.
	 * 
	 * @param object The object to transform.
	 * @return The comparison object.
	 */
	protected abstract V mapValue(final T object);
}
