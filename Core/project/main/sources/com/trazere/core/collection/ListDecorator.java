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
import com.trazere.core.imperative.ExListIterator;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

/**
 * The {@link ListDecorator} class implements decorators of {@link List lists}.
 * 
 * @param <E> Type of the elements.
 * @since 2.0
 */
public class ListDecorator<E>
extends Decorator<List<E>>
implements ExList<E> {
	/**
	 * Builds a new decorator.
	 * 
	 * @param decorated List to decorate.
	 * @since 2.0
	 */
	public ListDecorator(final List<E> decorated) {
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
	public E get(final int index) {
		return _decorated.get(index);
	}
	
	@Override
	public int indexOf(final Object o) {
		return _decorated.indexOf(o);
	}
	
	@Override
	public int lastIndexOf(final Object o) {
		return _decorated.lastIndexOf(o);
	}
	
	@Override
	public ExIterator<E> iterator() {
		return ExIterator.build(_decorated.iterator());
	}
	
	@Override
	public ExListIterator<E> listIterator() {
		return ExListIterator.build(_decorated.listIterator());
	}
	
	@Override
	public ExListIterator<E> listIterator(final int index) {
		return ExListIterator.build(_decorated.listIterator(index));
	}
	
	@Override
	public ExList<E> subList(final int fromIndex, final int toIndex) {
		return ExList.build(_decorated.subList(fromIndex, toIndex));
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
	public E set(final int index, final E element) {
		return _decorated.set(index, element);
	}
	
	@Override
	public boolean add(final E e) {
		return _decorated.add(e);
	}
	
	@Override
	public void add(final int index, final E element) {
		_decorated.add(index, element);
	}
	
	@Override
	public boolean remove(final Object o) {
		return _decorated.remove(o);
	}
	
	@Override
	public E remove(final int index) {
		return _decorated.remove(index);
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
	public boolean addAll(final int index, final Collection<? extends E> c) {
		return _decorated.addAll(index, c);
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
	public boolean retainAll(final Collection<?> c) {
		return _decorated.retainAll(c);
	}
	
	@Override
	public void replaceAll(final UnaryOperator<E> operator) {
		_decorated.replaceAll(operator);
	}
	
	@Override
	public void sort(final Comparator<? super E> c) {
		_decorated.sort(c);
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
