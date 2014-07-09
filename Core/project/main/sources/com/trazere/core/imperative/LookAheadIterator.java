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
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * The {@link LookAheadIterator} class implements iterators that look ahead the next element.
 * 
 * @param <E> Type of the elements.
 */
public abstract class LookAheadIterator<E>
implements Iterator<E> {
	// Iterator.
	
	/** Flag indicating whether the next element has been looked ahead. */
	private boolean _lookAhead = false;
	
	/** Next element. */
	private Maybe<? extends E> _next = Maybe.none();
	
	@Override
	public boolean hasNext() {
		lookAhead();
		return _next.isSome();
	}
	
	@Override
	public E next() {
		lookAhead();
		if (_next.isSome()) {
			return _next.asSome().getValue();
		} else {
			throw new NoSuchElementException();
		}
	}
	
	private void lookAhead() {
		if (!_lookAhead) {
			_next = pull();
			_lookAhead = true;
		}
	}
	
	/**
	 * Pulls the next element from the feed.
	 * 
	 * @return The next element.
	 */
	protected abstract Maybe<? extends E> pull();
	
	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}
}
