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

import java.util.Collection;

/**
 * The {@link BaseCollectionFactory} class provides a skeleton implementation of {@link CollectionFactory collection factories}.
 * 
 * @param <T> Type of the elements.
 * @param <C> Type of the collections.
 */
public abstract class BaseCollectionFactory<T, C extends Collection<? super T>>
implements CollectionFactory<T, C> {
	@Override
	public C build(final Iterable<? extends T> elements) {
		final C collection = build();
		CollectionUtils.addAll(collection, elements);
		return collection;
	}
}
