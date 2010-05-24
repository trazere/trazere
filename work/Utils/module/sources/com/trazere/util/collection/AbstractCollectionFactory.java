package com.trazere.util.collection;

import com.trazere.util.lang.AbstractFactory;
import java.util.Collection;

/**
 * The {@link AbstractCollectionFactory} class implements skeleton of {@link CollectionFactory collection factories}.
 * 
 * @param <T> Type of the elements.
 * @param <C> Type of the collections.
 */
public abstract class AbstractCollectionFactory<T, C extends Collection<? super T>>
extends AbstractFactory<C, RuntimeException>
implements CollectionFactory<T, C> {
	// Nothing to do.
	
	// HACK: moved here from AbstractFactory to work aroud a bug of javac (http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=6199662)
	public C evaluate() {
		return build();
	}
}
