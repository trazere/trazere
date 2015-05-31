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
import java.util.Collections;

/**
 * The {@link CollectionFactory} interface defines extended factories of {@link Collection collections}.
 * 
 * @param <E> Type of the elements.
 * @param <L> Type of the collections.
 * @see Collection
 * @since 1.0
 */
public interface ExtendedCollectionFactory<E, L extends Collection<E>>
extends ExtendedAbstractCollectionFactory<E, Collection<E>, L> {
	@Override
	default Collection<E> empty() {
		return Collections.emptyList();
	}
	
	@Override
	default Collection<E> unmodifiable(final L collection) {
		return Collections.unmodifiableCollection(collection);
	}
	
	@Override
	default Collection<E> synchronize(final L collection) {
		return Collections.synchronizedCollection(collection);
	}
}
