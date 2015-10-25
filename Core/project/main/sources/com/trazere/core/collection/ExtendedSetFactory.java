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

import java.util.Collections;
import java.util.Set;

/**
 * The {@link CollectionFactory} interface defines extended factories of {@link Set sets}.
 * 
 * @param <E> Type of the elements.
 * @param <S> Type of the sets.
 * @see Set
 * @since 2.0
 */
public interface ExtendedSetFactory<E, S extends Set<E>>
extends ExtendedAbstractCollectionFactory<E, Set<E>, S> {
	@Override
	default Set<E> empty() {
		return Collections.emptySet();
	}
	
	@Override
	default Set<E> unmodifiable(final S set) {
		return Collections.unmodifiableSet(set);
	}
	
	@Override
	default Set<E> synchronized_(final S set) {
		return Collections.synchronizedSet(set);
	}
}
