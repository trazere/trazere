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
package com.trazere.core.lang;

import com.trazere.core.functional.Function;
import java.util.Iterator;

/**
 * The {@link IterableFunctions} class provides various factories of {@link Function functions} related to {@link Iterable iterables}.
 * 
 * @see Function
 * @see Iterable
 * @since 2.0
 */
public class IterableFunctions {
	/**
	 * Builds a function that returns iterators of iterables.
	 * 
	 * @param <E> Type of the elements.
	 * @return The built function.
	 * @since 2.0
	 */
	public static <E> Function<Iterable<? extends E>, Iterator<? extends E>> iterator() {
		return Iterable::iterator;
	}
	
	private IterableFunctions() {
		// Prevent instantiation.
	}
}
