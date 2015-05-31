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

import java.util.Collection;

/**
 * The {@link CollectionFactory} interface defines extended factories of {@link Collection collections} that support providing unmodifiable and synchronized
 * views of the collections.
 * 
 * @param <E> Type of the elements.
 * @param <AC> Type of the abstract collections.
 * @param <C> Type of the collections.
 * @see Collection
 * @since 1.0
 */
public interface ExtendedAbstractCollectionFactory<E, AC extends Collection<E>, C extends AC>
extends CollectionFactory<E, C> {
	/**
	 * Builds an unmodifiable view of an empty collection.
	 * 
	 * @return The built unmodifiable view.
	 * @since 1.0
	 */
	AC empty();
	
	/**
	 * Builds an unmodifiable view of the given collection.
	 * 
	 * @param collection Collection to wrap.
	 * @return The built unmodifiable view.
	 * @since 1.0
	 */
	AC unmodifiable(C collection);
	
	/**
	 * Builds a synchronized view of the given collection.
	 * 
	 * @param collection Collection to wrap.
	 * @return The built synchronized view.
	 * @since 1.0
	 */
	AC synchronize(C collection);
}
