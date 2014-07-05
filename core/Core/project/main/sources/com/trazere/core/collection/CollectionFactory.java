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

import com.trazere.core.design.Factory;
import java.util.Collection;

/**
 * The {@link CollectionFactory} interface defines factories of collections.
 * 
 * @param <T> Type of the values.
 * @param <C> Type of the collections.
 */
public interface CollectionFactory<T, C extends Collection<? super T>>
extends Factory<C> {
	/**
	 * Builds an empty collection.
	 * 
	 * @return The built collection.
	 */
	@Override
	C build();
	
	/**
	 * Builds an empty collection with the given initial capacity.
	 * 
	 * @param capacity Initial capacity of the collection.
	 * @return The built collection.
	 */
	C build(int capacity);
	
	/**
	 * Builds a collection containing the given values.
	 * 
	 * @param values Values.
	 * @return The built collection.
	 */
	C build(Iterable<? extends T> values);
	
	/**
	 * Builds a collection containing the given values.
	 * 
	 * @param values Values.
	 * @return The built collection.
	 */
	C build(Collection<? extends T> values);
}
