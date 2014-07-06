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

import com.trazere.core.util.Maybe;
import com.trazere.core.util.Tuple2;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * The {@link BaseFeed} class provides a skeleton implementation of feeds.
 * 
 * @param <E> Type of the elements.
 */
public abstract class BaseFeed<E>
implements Feed<E> {
	// Feed.
	
	@Override
	public boolean isEmpty() {
		return evaluate().isNone();
	}
	
	public Tuple2<? extends E, ? extends Feed<? extends E>> get()
	throws NoSuchElementException {
		final Maybe<? extends Tuple2<? extends E, ? extends Feed<? extends E>>> value = evaluate();
		if (value.isSome()) {
			return value.asSome().getValue();
		} else {
			throw new NoSuchElementException();
		}
	}
	
	@Override
	public E getHead()
	throws NoSuchElementException {
		return get().get1();
	}
	
	@Override
	public Feed<? extends E> getTail()
	throws NoSuchElementException {
		return get().get2();
	}
	
	// Iterable.
	
	@Override
	public Iterator<E> iterator() {
		return new Iterator<E>() {
			private Feed<? extends E> _tail = BaseFeed.this;
			
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
			
			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}
}
