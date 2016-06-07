package com.trazere.core.collection;

import java.util.AbstractCollection;

/**
 * The {@link AbstractExCollection} class provides a skeleton implementation of {@link ExCollection extended collections}.
 * 
 * @param <E> Type of the elements.
 * @since 2.0
 */
public abstract class AbstractExCollection<E>
extends AbstractCollection<E>
implements ExCollection<E> {
	/**
	 * Instantiates a new collection.
	 * 
	 * @since 2.0
	 */
	protected AbstractExCollection() {
		super();
	}
}
