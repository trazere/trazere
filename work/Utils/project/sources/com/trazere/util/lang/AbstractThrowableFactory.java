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
}
