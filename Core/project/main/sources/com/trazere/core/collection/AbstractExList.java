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

import com.trazere.core.imperative.ExIterator;
import com.trazere.core.imperative.ExListIterator;
import java.util.AbstractList;

/**
 * The {@link AbstractExList} class provides a skeleton implementation of {@link ExList extended lists}.
 * 
 * @param <E> Type of the elements.
 * @since 2.0
 */
public abstract class AbstractExList<E>
extends AbstractList<E>
implements ExList<E> {
	/**
	 * Instantiates a new collection.
	 * 
	 * @since 2.0
	 */
	protected AbstractExList() {
		super();
	}
	
	// List.
	
	@Override
	public ExListIterator<E> listIterator() {
		return ExListIterator.build(super.listIterator());
	}
	
	@Override
	public ExListIterator<E> listIterator(final int index) {
		return ExListIterator.build(super.listIterator(index));
	}
	
	@Override
	public ExList<E> subList(final int fromIndex, final int toIndex) {
		return ExList.build(super.subList(fromIndex, toIndex));
	}
	
	// Iterable.
	
	@Override
	public ExIterator<E> iterator() {
		return ExIterator.build(super.iterator());
	}
}