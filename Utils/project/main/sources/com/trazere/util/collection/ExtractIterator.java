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

import com.trazere.core.lang.MutableBoolean;
import com.trazere.util.reference.MutableReference;
import com.trazere.util.type.Maybe;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * The {@link ExtractIterator} class implements iterator combinators that extract values.
 * 
 * @param <T> Type of the values of the feed.
 * @param <R> Type of the extracted values.
 */
public abstract class ExtractIterator<T, R>
implements Iterator<R> {
	// Iterator.
	
	@Override
	public boolean hasNext() {
		return lookAhead();
	}
	
	@Override
	public R next() {
		if (lookAhead()) {
			final R next = _next.get();
			_next.reset();
			_lookAhead.set(false);
			return next;
		} else {
			throw new NoSuchElementException();
		}
	}
	
	/** Flag indicating whether the next element has been retrieved from the feed. */
	private final MutableBoolean _lookAhead = new MutableBoolean(false);
	
	/** Next element from the feed. */
	private final MutableReference<R> _next = new MutableReference<R>();
	
	private boolean lookAhead() {
		if (_lookAhead.get()) {
			return _next.isSet();
		} else {
			while (true) {
				final Maybe<T> rawNext = pull();
				if (rawNext.isSome()) {
					final Maybe<? extends R> next = extract(rawNext.asSome().getValue());
					if (next.isSome()) {
						_next.update(next);
						_lookAhead.set(true);
						return true;
					}
				} else {
					_next.reset();
					_lookAhead.set(true);
					return false;
				}
			}
		}
	}
	
	/**
	 * Pulls the next value from the feed.
	 * 
	 * @return The next value.
	 */
	protected abstract Maybe<T> pull();
	
	/**
	 * Extracts the value to produce from the given value.
	 * 
	 * @param value Value to filter. May be <code>null</code>.
	 * @return <code>true</code> when the value is accepted, <code>false</code> otherwise.
	 */
	protected abstract Maybe<? extends R> extract(final T value);
	
	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}
}
