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
import java.util.Iterator;
import java.util.function.Consumer;

/**
 * The {@link IteratorDecorator} class implements decorators of {@link Iterator iterators}.
 * 
 * @param <E> Type of the elements.
 * @since 2.0
 */
public class IteratorDecorator<E>
extends Decorator<Iterator<E>>
implements ExIterator<E> {
	public IteratorDecorator(final Iterator<E> decorated) {
		super(decorated);
	}
	
	// Iterator.
	
	@Override
	public boolean hasNext() {
		return _decorated.hasNext();
	}
	
	@Override
	public E next() {
		return _decorated.next();
	}
	
	@Override
	public void remove() {
		_decorated.remove();
	}
	
	@Override
	public void forEachRemaining(final Consumer<? super E> action) {
		_decorated.forEachRemaining(action);
	}
}
