/*
 *  Copyright 2006-2011 Julien Dufour
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

import com.trazere.util.collection.LazyMap;
import com.trazere.util.function.Function1;
import java.util.Comparator;

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
	 * @return The built iterator.
	 */
	public static <T, V> ViewComparator<T, V> build(final Function1<? super T, ? extends V, ? extends RuntimeException> function, final Comparator<? super V> comparator) {
		assert null != function;
		
		return new ViewComparator<T, V>(comparator) {
			@Override
			protected V computeValue(final T object) {
				return function.evaluate(object);
			}
		};
	}
	
	/**
	 * Build a comparation using the function.
	 * 
	 * @param <T> Type of the compared objets.
	 * @param <V> Type of the values to compore.
	 * @param function The function computing the view.
	 * @param comparator The comparator to use.
	 * @param cache Flag indicating whether the cache if enabled or not.
	 * @return The built iterator.
	 */
	public static <T, V> ViewComparator<T, V> build(final Function1<? super T, ? extends V, ? extends RuntimeException> function, final Comparator<? super V> comparator, final boolean cache) {
		if (cache) {
			return build(LazyMap.build(function), comparator);
		} else {
			return build(function, comparator);
		}
	}
	
	/** Comparator to use. */
	protected final Comparator<? super V> _comparator;
	
	/**
	 * Instantiate a new comparator.
	 * 
	 * @param comparator The comparator to use. May be <code>null</code>.
	 */
	public ViewComparator(final Comparator<? super V> comparator) {
		assert null != comparator;
		
		// Initialization.
		_comparator = comparator;
	}
	
	public int compare(final T object1, final T object2) {
		return LangUtils.compare(_comparator, computeValue(object1), computeValue(object2));
	}
	
	/**
	 * Compute the value to use for comparison for the given object.
	 * 
	 * @param object Compared object whose comparison is retrieved. May be <code>null</code>.
	 * @return The comparison value. May be <code>null</code>.
	 */
	protected abstract V computeValue(final T object);
}
