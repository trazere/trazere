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

import com.trazere.core.functional.Thunk;
import com.trazere.core.util.Maybe;
import com.trazere.core.util.Tuple2;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * The {@link Feed} interface defines lazy sequences of elements.
 * 
 * @param <E> Type of the elements.
 */
public interface Feed<E>
extends Thunk<Maybe<? extends Tuple2<? extends E, ? extends Feed<? extends E>>>>, Iterable<E> {
	/**
	 * Tests whether this feed is empty.
	 * 
	 * @return <code>true</code> when the feed is empty, <code>false</code> otherwise.
	 */
	default boolean isEmpty() {
		return evaluate().isNone();
	}
	
	/**
	 * Gets the head element and tail of this feed.
	 * 
	 * @return The head element and tail.
	 * @throws NoSuchElementException When the feed is emtpy.
	 */
	default Tuple2<? extends E, ? extends Feed<? extends E>> get()
	throws NoSuchElementException {
		final Maybe<? extends Tuple2<? extends E, ? extends Feed<? extends E>>> value = evaluate();
		if (value.isSome()) {
			return value.asSome().getValue();
		} else {
			throw new NoSuchElementException();
		}
	}
	
	/**
	 * Gets the head element of this feed.
	 * 
	 * @return The element.
	 * @throws NoSuchElementException When the feed is empty.
	 */
	default E getHead()
	throws NoSuchElementException {
		return get().get1();
	}
	
	/**
	 * Gets the tail of this feed.
	 * 
	 * @return The tail.
	 * @throws NoSuchElementException When the feed is empty.
	 */
	default Feed<? extends E> getTail()
	throws NoSuchElementException {
		return get().get2();
	}
	
	// Iterable.
	
	@Override
	default Iterator<E> iterator() {
		return new Iterator<E>() {
			private Feed<? extends E> _tail = Feed.this;
			
			@Override
			public boolean hasNext() {
				return !_tail.isEmpty();
			}
			
			@Override
			public E next()
			throws NoSuchElementException {
				final E head = _tail.getHead();
				_tail = _tail.getTail();
				return head;
			}
		};
	}
}
