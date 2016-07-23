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

import com.trazere.core.design.Decorator;
import com.trazere.core.imperative.ExIterator;
import java.util.Collection;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * The {@link SetDecorator} class implements decorators of {@link Set sets}.
 * 
 * @param <E> Type of the elements.
 * @since 2.0
 */
public class SetDecorator<E>
extends Decorator<Set<E>>
implements ExSet<E> {
	public SetDecorator(final Set<E> decorated) {
		super(decorated);
	}
	
	// Query operations.
	
	@Override
	public int size() {
		return _decorated.size();
	}
	
	@Override
	public boolean isEmpty() {
		return _decorated.isEmpty();
	}
	
	@Override
	public boolean contains(final Object o) {
		return _decorated.contains(o);
	}
	
	@Override
	public ExIterator<E> iterator() {
		return ExIterator.build(_decorated.iterator());
	}
	
	@Override
	public Object[] toArray() {
		return _decorated.toArray();
	}
	
	@Override
	public <T> T[] toArray(final T[] a) {
		return _decorated.toArray(a);
	}
	
	// Modification operations.
	
	@Override
	public boolean add(final E e) {
		return _decorated.add(e);
	}
	
	@Override
	public boolean remove(final Object o) {
		return _decorated.remove(o);
	}
	
	// Bulk operations.
	
	@Override
	public void forEach(final Consumer<? super E> action) {
		_decorated.forEach(action);
	}
	
	@Override
	public boolean containsAll(final Collection<?> c) {
		return _decorated.containsAll(c);
	}
	
	@Override
	public boolean addAll(final Collection<? extends E> c) {
		return _decorated.addAll(c);
	}
	
	@Override
	public boolean retainAll(final Collection<?> c) {
		return _decorated.retainAll(c);
	}
	
	@Override
	public boolean removeAll(final Collection<?> c) {
		return _decorated.removeAll(c);
	}
	
	@Override
	public boolean removeIf(final Predicate<? super E> filter) {
		return _decorated.removeIf(filter);
	}
	
	@Override
	public void clear() {
		_decorated.clear();
	}
	
	// Streams.
	
	@Override
	public Spliterator<E> spliterator() {
		return _decorated.spliterator();
	}
	
	@Override
	public Stream<E> stream() {
		return _decorated.stream();
	}
	
	@Override
	public Stream<E> parallelStream() {
		return _decorated.parallelStream();
	}
}
