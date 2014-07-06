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
 * The {@link ExtractIterator} class implements iterators that extract elements.
 * 
 * @param <E> Type of the feeded elements.
 * @param <RE> Type of the extracted elements.
 */
public abstract class ExtractIterator<E, RE>
implements Iterator<RE> {
	// Iterator.
	
	@Override
	public boolean hasNext() {
		return lookAhead();
	}
	
	@Override
	public RE next() {
		if (lookAhead()) {
			final RE next = _next.asSome().getValue();
			_next = Maybe.none();
			_lookAhead = false;
			return next;
		} else {
			throw new NoSuchElementException();
		}
	}
	
	/** Flag indicating whether the next element has been retrieved from the feed. */
	private boolean _lookAhead = false;
	
	/** Next element from the feed. */
	private Maybe<? extends RE> _next = Maybe.none();
	
	private boolean lookAhead() {
		if (_lookAhead) {
			return _next.isSome();
		} else {
			while (true) {
				final Maybe<E> rawNext = pull();
				if (rawNext.isSome()) {
					final Maybe<? extends RE> next = extract(rawNext.asSome().getValue());
					if (next.isSome()) {
						_next = next;
						_lookAhead = true;
						return true;
					}
				} else {
					_next = Maybe.none();
					_lookAhead = true;
					return false;
				}
			}
		}
	}
	
	/**
	 * Pulls the next element from the feed.
	 * 
	 * @return The next element.
	 */
	protected abstract Maybe<E> pull();
	
	/**
	 * Extracts the element to produce from the given feed element.
	 * 
	 * @param element Feed element to extract from.
	 * @return The extracted element.
	 */
	protected abstract Maybe<? extends RE> extract(final E element);
	
	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}
}
