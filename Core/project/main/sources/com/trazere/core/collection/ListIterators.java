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

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * The {@link ListIterators} class provides various factories of {@link Iterator iterators} related to {@link List lists}.
 * 
 * @see Iterator
 * @see List
 * @since 2.0
 */
public class ListIterators {
	/**
	 * Builds an iterator that iterates the given list from the last element to the first one.
	 * 
	 * @param <E> Type of the elements.
	 * @param list List to iterate.
	 * @return The built iterator.
	 * @since 2.0
	 */
	public static <E> Iterator<E> backward(final List<? extends E> list) {
		final ListIterator<? extends E> iterator = list.listIterator(list.size());
		return new Iterator<E>() {
			@Override
			public boolean hasNext() {
				return iterator.hasPrevious();
			}
			
			@Override
			public E next() {
				return iterator.previous();
			}
			
			@Override
			public void remove() {
				iterator.remove();
			}
		};
	}
	
	private ListIterators() {
		// Prevent instantiation.
	}
}
