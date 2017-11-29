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
package com.trazere.core.imperative;

import com.trazere.core.design.Decorator;
import java.util.ListIterator;
import java.util.function.Consumer;

/**
 * The {@link ListIteratorDecorator} class implements decorators of {@link ListIterator list iterators}.
 * 
 * @param <E> Type of the elements.
 * @since 2.0
 */
public class ListIteratorDecorator<E>
extends Decorator<ListIterator<E>>
implements ExListIterator<E> {
	/**
	 * Builds a new decorator.
	 * 
	 * @param decorated Decorated list iterator.
	 * @since 2.0
	 */
	public ListIteratorDecorator(final ListIterator<E> decorated) {
		super(decorated);
	}
	
	// ListIterator.
	
	@Override
	public boolean hasNext() {
		return _decorated.hasNext();
	}
	
	@Override
	public E next() {
		return _decorated.next();
	}
	
	@Override
	public int nextIndex() {
		return _decorated.nextIndex();
	}
	
	@Override
	public boolean hasPrevious() {
		return _decorated.hasPrevious();
	}
	
	@Override
	public E previous() {
		return _decorated.previous();
	}
	
	@Override
	public int previousIndex() {
		return _decorated.previousIndex();
	}
	
	@Override
	public void remove() {
		_decorated.remove();
	}
	
	@Override
	public void set(final E e) {
		_decorated.set(e);
	}
	
	@Override
	public void add(final E e) {
		_decorated.add(e);
	}
	
	@Override
	public void forEachRemaining(final Consumer<? super E> action) {
		_decorated.forEachRemaining(action);
	}
}