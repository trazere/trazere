/*
 *  Copyright 2006-2013 Julien Dufour
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
package com.trazere.core.lang;

import com.trazere.core.imperative.IteratorUtils;
import com.trazere.core.util.Maybe;
import java.util.Comparator;

/**
 * The {@link IterableUtils} class provides various helpers regarding {@link Iterable iterables}.
 * 
 * @see Iterable
 */
public class IterableUtils {
	/**
	 * Gets the least value provided by the given iterator.
	 *
	 * @param <T> Type of the values.
	 * @param values Iterator providing the values.
	 * @return The least value.
	 */
	public static <T extends Comparable<T>> Maybe<? extends T> least(final Iterable<? extends T> values) {
		return IteratorUtils.least(values.iterator());
	}
	
	/**
	 * Gets the least value provided by the given iterator according to the given comparator.
	 *
	 * @param <T> Type of the values.
	 * @param values The values.
	 * @param comparator The comparator.
	 * @return The least value.
	 */
	public static <T> Maybe<? extends T> least(final Iterable<? extends T> values, final Comparator<? super T> comparator) {
		return IteratorUtils.least(values.iterator(), comparator);
	}
	
	/**
	 * Gets the greatest value provided by the given iterator.
	 *
	 * @param <T> Type of the values.
	 * @param values Iterator providing the values.
	 * @return The greatest value.
	 */
	public static <T extends Comparable<T>> Maybe<? extends T> greatest(final Iterable<? extends T> values) {
		return IteratorUtils.greatest(values.iterator());
	}
	
	/**
	 * Gets the greatest value provided by the given iterator according to the given comparator.
	 *
	 * @param <T> Type of the values.
	 * @param values The values.
	 * @param comparator The comparator.
	 * @return The greatest value.
	 */
	public static <T> Maybe<? extends T> greatest(final Iterable<? extends T> values, final Comparator<? super T> comparator) {
		return IteratorUtils.greatest(values.iterator(), comparator);
	}
	
	// TODO: filter
	// TODO: map
	// TODO: flatMap
	// TODO: extract
	
	private IterableUtils() {
		// Prevent instantiation.
	}
}
