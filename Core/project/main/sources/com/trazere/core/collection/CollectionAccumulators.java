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

import com.trazere.core.imperative.Accumulator;
import com.trazere.core.imperative.Accumulator2;
import com.trazere.core.util.Tuple2;
import java.util.Collection;

/**
 * The {@link CollectionAccumulators} class provides various factories of accumulators related to collections.
 */
public class CollectionAccumulators {
	/**
	 * Builds an accumulator that adds elements to the given collection.
	 * 
	 * @param <E> Type of the elements.
	 * @param <C> Type of the collection.
	 * @param collection Collection to populate.
	 * @return The built accumulator.
	 */
	public static <E, C extends Collection<? super E>> Accumulator<E, C> add(final C collection) {
		assert null != collection;
		
		return new Accumulator<E, C>() {
			@Override
			public void add(final E element) {
				collection.add(element);
			}
			
			@Override
			public C get() {
				return collection;
			}
		};
	}
	
	/**
	 * Builds an accumulator that adds pairs of elements to the given collection.
	 * 
	 * @param <E1> Type of the first element of the pairs.
	 * @param <E2> Type of the second element of the pairs.
	 * @param <C> Type of the collection.
	 * @param collection Collection to populate.
	 * @return The built accumulator.
	 */
	public static <E1, E2, C extends Collection<? super Tuple2<? extends E1, ? extends E2>>> Accumulator2<E1, E2, C> add2(final C collection) {
		assert null != collection;
		
		return new Accumulator2<E1, E2, C>() {
			@Override
			public void add(final E1 element1, final E2 element2) {
				collection.add(new Tuple2<>(element1, element2));
			}
			
			@Override
			public C get() {
				return collection;
			}
		};
	}
	
	private CollectionAccumulators() {
		// Prevents instantiation.
	}
}
