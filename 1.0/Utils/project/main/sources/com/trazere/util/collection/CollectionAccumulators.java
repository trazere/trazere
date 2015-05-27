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
package com.trazere.util.collection;

import com.trazere.util.accumulator.Accumulator1;
import com.trazere.util.accumulator.Accumulator2;
import com.trazere.util.accumulator.Accumulators;
import com.trazere.util.accumulator.BaseAccumulator1;
import com.trazere.util.accumulator.BaseAccumulator2;
import com.trazere.util.function.Function1;
import com.trazere.util.lang.InternalException;
import java.util.Collection;
import java.util.Map;

/**
 * The {@link CollectionAccumulators} class provides various factories of accumulators related to collections.
 */
public class CollectionAccumulators {
	/**
	 * Builds an accumulator that populates the given collection.
	 * 
	 * @param <T> Type of the values.
	 * @param <C> Type of the collection.
	 * @param <X> Type of the exceptions.
	 * @param collection The collection to populate.
	 * @return The built accumulator.
	 */
	public static <T, C extends Collection<? super T>, X extends Exception> Accumulator1<T, C, X> add(final C collection) {
		assert null != collection;
		
		return new BaseAccumulator1<T, C, X>() {
			@Override
			public void add(final T value)
			throws X {
				collection.add(value);
			}
			
			@Override
			public C get() {
				return collection;
			}
		};
	}
	
	/**
	 * Builds an accumulator that populates the given collection.
	 * <p>
	 * These accumulators return a fresh copy of the accumulated collection.
	 * 
	 * @param <T> Type of the values.
	 * @param <C> Type of the collection.
	 * @param <X> Type of the exceptions.
	 * @param collection The collection to populate.
	 * @param collectionFactory The collection factory to use to copy the collections.
	 * @return The built accumulator.
	 */
	public static <T, C extends Collection<? super T>, X extends Exception> Accumulator1<T, C, X> add(final Collection<T> collection, final CollectionFactory<T, ? extends C> collectionFactory) {
		assert null != collection;
		assert null != collectionFactory;
		
		final Function1<Collection<T>, C, InternalException> function = new Function1<Collection<T>, C, InternalException>() {
			@Override
			public C evaluate(final Collection<T> values) {
				return collectionFactory.build(values);
			}
		};
		return Accumulators.<T, Collection<T>, C, X>mapState(function, CollectionAccumulators.<T, Collection<T>, X>add(collection));
	}
	
	/**
	 * Builds an accumulator that populates the given map.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <M> Type of the map.
	 * @param <X> Type of the exceptions.
	 * @param map The map to populate.
	 * @return The built accumulator.
	 */
	public static <K, V, M extends Map<? super K, ? super V>, X extends Exception> Accumulator2<K, V, M, X> put(final M map) {
		assert null != map;
		
		return new BaseAccumulator2<K, V, M, X>() {
			@Override
			public void add(final K key, final V value)
			throws X {
				map.put(key, value);
			}
			
			@Override
			public M get() {
				return map;
			}
		};
	}
	
	/**
	 * Builds an accumulator that populates the given multimap.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <M> Type of the multimap.
	 * @param <X> Type of the exceptions.
	 * @param multimap The map to populate.
	 * @return The built accumulator.
	 */
	public static <K, V, M extends Multimap<? super K, ? super V, ?>, X extends Exception> Accumulator2<K, V, M, X> put(final M multimap) {
		assert null != multimap;
		
		return new BaseAccumulator2<K, V, M, X>() {
			@Override
			public void add(final K key, final V value)
			throws X {
				multimap.put(key, value);
			}
			
			@Override
			public M get() {
				return multimap;
			}
		};
	}
	
	private CollectionAccumulators() {
		// Prevents instantiation.
	}
}
