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
package com.trazere.core.collection;

import com.trazere.core.util.Maybe;
import com.trazere.core.util.Tuple2;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * The {@link Feed} interface defines lazy sequences of elements.
 * 
 * @param <E> Type of the elements.
 * @since 2.0
 */
public interface Feed<E>
extends Iterable<E> {
	/**
	 * Tests whether this feed is empty.
	 * 
	 * @return <code>true</code> when the feed is empty, <code>false</code> otherwise.
	 * @since 2.0
	 */
	boolean isEmpty();
	
	/**
	 * Gets the head element of this feed.
	 * 
	 * @return The element.
	 * @throws NoSuchElementException When the feed is empty.
	 * @since 2.0
	 */
	E head()
	throws NoSuchElementException;
	
	/**
	 * Gets the head element of this feed.
	 * <p>
	 * This method supports empty feeds.
	 *
	 * @return The head element, or nothing when the feed is empty.
	 * @see #head()
	 * @since 2.0
	 */
	Maybe<E> optionalHead();
	
	/**
	 * Gets the tail of this feed.
	 * 
	 * @return The tail.
	 * @throws NoSuchElementException When the feed is empty.
	 * @since 2.0
	 */
	Feed<? extends E> tail()
	throws NoSuchElementException;
	
	/**
	 * Gets the tail of this feed.
	 * <p>
	 * This method supports empty feeds.
	 *
	 * @return The tail of the feed, or nothing when the feed is empty.
	 * @since 2.0
	 */
	Maybe<? extends Feed<? extends E>> optionalTail();
	
	/**
	 * Gets the head and tail of this feed.
	 * 
	 * @return The head and tail.
	 * @throws NoSuchElementException When the feed is emtpy.
	 * @since 2.0
	 */
	Tuple2<? extends E, ? extends Feed<? extends E>> item()
	throws NoSuchElementException;
	
	/**
	 * Gets the head and tail of this feed.
	 * <p>
	 * This method supports empty feeds.
	 * 
	 * @return The head and tail, or nothing when the feed is empty.
	 * @since 2.0
	 */
	Maybe<? extends Tuple2<? extends E, ? extends Feed<? extends E>>> optionalItem();
	
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
				final E head = _tail.head();
				_tail = _tail.tail();
				return head;
			}
		};
	}
}
