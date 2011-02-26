package com.trazere.util.lang;

/**
 * The {@link AbstractFactory} class implements skeletons of {@link Factory factories}.
 * 
 * @param <T> Type of the built values.
 * @param <X> Type of the exceptions.
 */
public abstract class AbstractFactory<T, X extends Exception>
implements Factory<T, X> {
	// Function.
	
	public T evaluate()
	throws X {
		return build();
	}
}
