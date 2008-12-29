/*
 *  Copyright 2006-2008 Julien Dufour
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

import com.trazere.util.function.Function;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * The {@link ViewComparator} abstract class is a comparator working according some value computed from the compared objects. It applies some kind of view over
 * the compared objects.
 * <p>
 * This class can cache the computed values in order to improved the performances. The computed values as well as the compared objects must be immutable in
 * order to use this cache.
 * 
 * @param <T> Type of the compared objets.
 * @param <V> Type of the values to compore.
 */
public abstract class ViewComparator<T, V>
implements Comparator<T> {
	/**
	 * Build a comparation using the function.
	 * 
	 * @param <T> Type of the compared objets.
	 * @param <V> Type of the values to compore.
	 * @param function The function computing the view.
	 * @param comparator The comparator to use.
	 * @param cache Flag indicating wether the cache if enabled or not.
	 * @return The built iterator.
	 */
	public static <T, V> ViewComparator<T, V> build(final Function<? super T, ? extends V, ? extends RuntimeException> function, final Comparator<? super V> comparator, final boolean cache) {
		assert null != function;
		
		return new ViewComparator<T, V>(comparator, cache) {
			@Override
			protected V computeValue(final T object) {
				return function.evaluate(object);
			}
		};
	}
	
	/** Comparator to use. */
	protected final Comparator<? super V> _comparator;
	
	/** Flag indicating wether the cache if enabled or not. */
	protected final boolean _cache;
	
	/** Cached values identified by the objects they where computed from. */
	protected final Map<T, V> _values = new HashMap<T, V>();
	
	/**
	 * Instantiate a new comparator.
	 * 
	 * @param comparator The comparator to use. May be <code>null</code>.
	 * @param cache Flag indicating wether the cache if enabled or not.
	 */
	public ViewComparator(final Comparator<? super V> comparator, final boolean cache) {
		assert null != comparator;
		
		// Initialization.
		_comparator = comparator;
		_cache = cache;
	}
	
	public int compare(final T object1, final T object2) {
		final V value1 = _cache ? getValue(object1) : computeValue(object1);
		final V value2 = _cache ? getValue(object2) : computeValue(object2);
		return LangUtils.compare(value1, value2, _comparator);
	}
	
	/**
	 * Get the value to use for comparison for the given object. This methods uses and populates the cache.
	 * 
	 * @param object Compared object whose comparison is retrieved. May be <code>null</code>.
	 * @return The comparison value. May be <code>null</code>.
	 */
	protected V getValue(final T object) {
		// Read the cache.
		final V cachedValue = _values.get(object);
		if (null != cachedValue) {
			return cachedValue;
		}
		
		// Compute the value.
		final V value = computeValue(object);
		
		// Fill the cache.
		_values.put(object, value);
		
		return value;
	}
	
	/**
	 * Compute the value to use for comparison for the given object.
	 * 
	 * @param object Compared object whose comparison is retrieved. May be <code>null</code>.
	 * @return The comparison value. May be <code>null</code>.
	 */
	protected abstract V computeValue(final T object);
	
	public void flushCache() {
		_values.clear();
	}
}
