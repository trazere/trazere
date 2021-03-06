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

import com.trazere.util.lang.InternalException;
import com.trazere.util.lang.MutableBoolean;
import com.trazere.util.reference.MutableReference;
import com.trazere.util.reference.ReferenceNotSetException;
import com.trazere.util.type.Maybe;
import java.util.NoSuchElementException;

/**
 * The {@link CheckedExtractIterator} class provides iterators combinators that extract values.
 * 
 * @param <T> Type of the values of the feed.
 * @param <R> Type of the extracted values.
 * @param <X> Type of the exceptions.
 * @deprecated Use {@link com.trazere.core.imperative.IteratorUtils#extract(java.util.Iterator, com.trazere.core.functional.Function)}.
 */
@Deprecated
public abstract class CheckedExtractIterator<T, R, X extends Exception>
implements CheckedIterator<R, X> {
	// Iterator.
	
	@Override
	public boolean hasNext()
	throws X {
		return lookAhead();
	}
	
	@Override
	public R next()
	throws X {
		if (lookAhead()) {
			final R next;
			try {
				next = _next.get();
			} catch (final ReferenceNotSetException exception) {
				throw new InternalException(exception);
			}
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
	
	private boolean lookAhead()
	throws X {
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
	 * @throws X When the next value cannot be pulled.
	 * @deprecated Use {@link com.trazere.core.imperative.IteratorUtils#extract(java.util.Iterator, com.trazere.core.functional.Function)}.
	 */
	@Deprecated
	protected abstract Maybe<T> pull()
	throws X;
	
	/**
	 * Extracts the value to produce from the given value.
	 * 
	 * @param value Value to filter. May be <code>null</code>.
	 * @return <code>true</code> when the value is accepted, <code>false</code> otherwise.
	 * @throws X When the extraction fails.
	 * @deprecated Use {@link com.trazere.core.imperative.IteratorUtils#extract(java.util.Iterator, com.trazere.core.functional.Function)}.
	 */
	@Deprecated
	protected abstract Maybe<? extends R> extract(final T value)
	throws X;
}
