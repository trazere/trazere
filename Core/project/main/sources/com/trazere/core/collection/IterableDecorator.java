package com.trazere.core.collection;

import com.trazere.core.design.Decorator;
import com.trazere.core.imperative.ExIterator;
import com.trazere.core.lang.ExIterable;

/**
 * The {@link IterableDecorator} class implements decorators of {@link Iterable iterables}.
 * 
 * @param <E> Type of the elements.
 * @since 2.0
 */
public class IterableDecorator<E>
extends Decorator<Iterable<E>>
implements ExIterable<E> {
	public IterableDecorator(final Iterable<E> decorated) {
		super(decorated);
	}
	
	@Override
	public ExIterator<E> iterator() {
		return ExIterator.build(_decorated.iterator());
	}
}
