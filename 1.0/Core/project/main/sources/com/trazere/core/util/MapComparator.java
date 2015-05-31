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

import java.util.Comparator;

/**
 * The {@link MapComparator} abstract class implements comparator that compared mapped values.
 * 
 * @param <T> Type of the values.
 * @param <V> Type of the mapped values.
 * @since 1.0
 */
public abstract class MapComparator<T, V>
implements Comparator<T> {
	/**
	 * The comparator to use.
	 * 
	 * @since 1.0
	 */
	protected final Comparator<? super V> _comparator;
	
	/**
	 * Instantiates a new comparator.
	 * 
	 * @param comparator Comparator to use.
	 * @since 1.0
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
	 * Maps the value to use for comparison.
	 * 
	 * @param object Object to map.
	 * @return The comparison value.
	 * @since 1.0
	 */
	protected abstract V mapValue(final T object);
}
