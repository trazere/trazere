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

import com.trazere.util.type.Maybe;

/**
 * The {@link FilterIterator} class implements iterator combinators that filter their values.
 * 
 * @param <T> Type of the values.
 */
public abstract class FilterIterator<T>
extends ExtractIterator<T, T> {
	// Iterator.
	
	@Override
	protected Maybe<? extends T> extract(final T value) {
		return filter(value) ? Maybe.some(value) : Maybe.<T>none();
	}
	
	/**
	 * Filters the given value.
	 * 
	 * @param value The value to filter. May be <code>null</code>.
	 * @return <code>true</code> when the value is accepted, <code>false</code> otherwise.
	 */
	protected abstract boolean filter(final T value);
}
