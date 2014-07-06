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
package com.trazere.core.imperative;

import com.trazere.core.util.Maybe;

/**
 * The {@link MapIterator} class implements iterator combinators that transform elements.
 * 
 * @param <E> Type of the feeded elements.
 * @param <RE> Type of the transformed elements.
 */
public abstract class MapIterator<E, RE>
extends ExtractIterator<E, RE> {
	// Iterator.
	
	@Override
	protected Maybe<? extends RE> extract(final E element) {
		return Maybe.some(map(element));
	}
	
	/**
	 * Transforms the given element.
	 * 
	 * @param element Feed element to transform.
	 * @return The transformed element.
	 */
	protected abstract RE map(final E element);
}
