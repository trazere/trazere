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
import java.util.List;

/**
 * The {@link CollectionFactory} interface defines extended factories of {@link List lists}.
 * 
 * @param <E> Type of the elements.
 * @param <L> Type of the lists.
 * @see List
 * @since 1.0
 */
public interface ExtendedListFactory<E, L extends List<E>>
extends ExtendedAbstractCollectionFactory<E, List<E>, L> {
	@Override
	default List<E> empty() {
		return Collections.emptyList();
	}
	
	@Override
	default List<E> unmodifiable(final L list) {
		return Collections.unmodifiableList(list);
	}
	
	@Override
	default List<E> synchronize(final L list) {
		return Collections.synchronizedList(list);
	}
}
