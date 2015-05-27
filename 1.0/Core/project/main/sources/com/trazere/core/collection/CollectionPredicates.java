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
package com.trazere.core.collection;

import com.trazere.core.functional.Predicate;
import java.util.Collection;

/**
 * The {@link CollectionPredicates} class provides various factories of predicates related to collections.
 */
public class CollectionPredicates {
	/**
	 * Builds a predicate that evaluates to <code>true</code> for empty collections.
	 * 
	 * @param <C> Type of the collections.
	 * @return The built predicate.
	 */
	@SuppressWarnings("unchecked")
	public static <C extends Collection<?>> Predicate<C> isEmpty() {
		return (Predicate<C>) IS_EMPTY;
	}
	
	private static Predicate<? extends Collection<?>> IS_EMPTY = collection -> collection.isEmpty();
	
	/**
	 * Builds a predicate that evaluates to <code>true</code> when the argument collection contains the given element.
	 * 
	 * @param <E> Type of the elements.
	 * @param <C> Type of the collections.
	 * @param element Element whose presence is to be tested.
	 * @return The built predicate.
	 */
	public static <E, C extends Collection<? super E>> Predicate<C> contains(final E element) {
		return collection -> collection.contains(element);
	}
	
	private CollectionPredicates() {
		// Prevents instantiation.
	}
}
