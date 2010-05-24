package com.trazere.util.lang;

/**
 * The {@link AbstractThrowableFactory} class implements skeletons of {@link ThrowableFactory throwable factories}.
 * 
 * @param <T> Type of the throwables.
 */
public abstract class AbstractThrowableFactory<T extends Throwable>
extends AbstractFactory<T, RuntimeException>
implements ThrowableFactory<T> {
	// Nothing to do.
	
	// HACK: moved here from AbstractFactory to work aroud a bug of javac (http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=6199662)
	public T evaluate()
	throws RuntimeException {
		return build();
	}
}
