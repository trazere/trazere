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
package com.trazere.util.collection;

import java.util.Iterator;

/**
 * The {@link FilterIterator} abstract class represents iterator combinators which filter their values.
 * 
 * @param <T> Type of the values.
 * @deprecated Use {@link com.trazere.core.imperative.IteratorUtils#filter(java.util.Iterator, com.trazere.core.functional.Predicate)}.
 */
@Deprecated
public abstract class FilterIterator<T>
extends CheckedFilterIterator<T, RuntimeException>
implements Iterator<T> {
	// Iterator.
	
	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}
}
