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
 * The {@link FilterIterator} class implements iterator combinators that filter elements.
 * 
 * @param <E> Type of the elements.
 */
public abstract class FilterIterator<E>
extends ExtractIterator<E, E> {
	// Iterator.
	
	@Override
	protected Maybe<? extends E> extract(final E element) {
		return filter(element) ? Maybe.some(element) : Maybe.<E>none();
	}
	
	/**
	 * Filters the given element.
	 * 
	 * @param element Element to filter.
	 * @return <code>true</code> when the value is accepted, <code>false</code> otherwise.
	 */
	protected abstract boolean filter(final E element);
}
