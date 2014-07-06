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

import com.trazere.core.functional.Function;
import java.util.Iterator;

/**
 * The {@link IterableFunctions} class provides various factories of function related to {@link Iterable iterables}.
 * 
 * @see Iterable
 */
public class IterableFunctions {
	/**
	 * Builds a function that returns iterators of iterables.
	 * 
	 * @param <E> Type of the elements.
	 * @return The built function.
	 */
	@SuppressWarnings("unchecked")
	public static <E> Function<Iterable<E>, Iterator<E>> iterator() {
		return (Function<Iterable<E>, Iterator<E>>) ITERATOR;
	}
	
	private static final Function<? extends Iterable<?>, ? extends Iterator<?>> ITERATOR = new Function<Iterable<?>, Iterator<?>>() {
		@Override
		public Iterator<?> evaluate(final Iterable<?> arg) {
			return arg.iterator();
		}
	};
	
	private IterableFunctions() {
		// Prevent instantiation.
	}
}
