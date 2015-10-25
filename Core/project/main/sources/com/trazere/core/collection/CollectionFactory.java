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
package com.trazere.core.collection;

import com.trazere.core.design.Factory;
import java.util.Collection;

/**
 * The {@link CollectionFactory} interface defines factories of {@link Collection collections}.
 * 
 * @param <E> Type of the elements.
 * @param <C> Type of the collections.
 * @see Collection
 * @since 2.0
 */
public interface CollectionFactory<E, C extends Collection<E>>
extends Factory<C> {
	/**
	 * Builds an empty collection.
	 * 
	 * @return The built collection.
	 * @since 2.0
	 */
	@Override
	C build();
	
	/**
	 * Builds an empty collection with the given initial capacity.
	 * 
	 * @param capacity Initial capacity of the collection.
	 * @return The built collection.
	 * @since 2.0
	 */
	default C build(final int capacity) {
		return build();
	}
	
	/**
	 * Builds a collection containing the given elements.
	 * 
	 * @param elements Elements.
	 * @return The built collection.
	 * @since 2.0
	 */
	default C build(@SuppressWarnings("unchecked") final E... elements) {
		final C collection = build();
		for (final E element : elements) {
			collection.add(element);
		}
		return collection;
	}
	
	/**
	 * Builds a collection containing the given elements.
	 * 
	 * @param elements Elements.
	 * @return The built collection.
	 * @since 2.0
	 */
	default C build(final Iterable<? extends E> elements) {
		final C collection = build();
		for (final E element : elements) {
			collection.add(element);
		}
		return collection;
	}
	
	/**
	 * Builds a collection containing the given elements.
	 * 
	 * @param elements Elements.
	 * @return The built collection.
	 * @since 2.0
	 */
	C build(Collection<? extends E> elements);
}
