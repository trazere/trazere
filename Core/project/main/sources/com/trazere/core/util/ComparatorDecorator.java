package com.trazere.core.util;

import com.trazere.core.design.Decorator;
import java.util.Comparator;

/**
 * The {@link ComparatorDecorator} class implements decorators of {@link Comparator comparators}.
 * 
 * @param <T> Type of objects that may be compared.
 * @since 2.0
 */
public class ComparatorDecorator<T>
extends Decorator<Comparator<T>>
implements ExComparator<T> {
	/**
	 * Builds a new decorator.
	 * 
	 * @param decorated Comparator to decorate.
	 * @since 2.0
	 */
	public ComparatorDecorator(final Comparator<T> decorated) {
		super(decorated);
	}
	
	// Comparator.
	
	@Override
	public int compare(final T o1, final T o2) {
		return _decorated.compare(o1, o2);
	}
}
