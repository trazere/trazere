package com.trazere.core.collection;

import com.trazere.core.design.Decorator;
import com.trazere.core.imperative.ExIterator;
import java.util.Collection;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * The {@link CollectionDecorator} class implements decorators of {@link Collection collections}.
 * 
 * @param <E> Type of the elements.
 * @since 2.0
 */
public class CollectionDecorator<E>
extends Decorator<Collection<E>>
implements ExCollection<E> {
	public CollectionDecorator(final Collection<E> decorated) {
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
