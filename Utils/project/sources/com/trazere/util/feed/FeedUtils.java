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
package com.trazere.util.feed;

import com.trazere.util.collection.CheckedIterator;
import java.util.NoSuchElementException;

/**
 * DOCME
 */
public class FeedUtils {
	// TODO: move to CheckedIterators
	/**
	 * Builds an iterator over the given feed.
	 * 
	 * @param <T> Type of the elements.
	 * @param <X> Type of the exceptions.
	 * @param feed The feed.
	 * @return The built iterator.
	 */
	public static <T, X extends Exception> CheckedIterator<T, X> iterator(final Feed<T, X> feed) {
		assert null != feed;
		
		return new CheckedIterator<T, X>() {
			private Feed<T, X> _tail = feed;
			
			@Override
			public boolean hasNext()
			throws X {
				return !_tail.isEmpty();
			}
			
			@Override
			public T next()
			throws NoSuchElementException, X {
				final T value = _tail.getHead();
				_tail = _tail.getTail();
				return value;
			}
		};
	}
	
	private FeedUtils() {
		// Prevent instantiation.
	}
}
