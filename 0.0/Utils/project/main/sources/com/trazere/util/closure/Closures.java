package com.trazere.util.closure;

import com.trazere.util.function.Function0;
import com.trazere.util.type.Maybe;

/**
 * The {@link Closures} class provides various factories of closures.
 */
public class Closures {
	/**
	 * Builds a closure evaluating to the given value.
	 * 
	 * @param <T> Type of the value.
	 * @param <X> Type of the exceptions.
	 * @param value Value. May be <code>null</code>.
	 * @return The closure.
	 */
	public static <T, X extends Exception> Closure<T, X> constant(final T value) {
		return new ConstantClosure<T, X>(value);
	}
	
	/**
	 * Builds a closure evaluating to the result of the given function.
	 * 
	 * @param <T> Type of the value.
	 * @param <X> Type of the exceptions.
	 * @param function Function computing the value.
	 * @return The closure.
	 */
	public static <T, X extends Exception> Closure<T, X> fromFunction(final Function0<? extends T, ? extends X> function) {
		return new LinearClosure<T, X>(function);
	}
	
	/**
	 * Builds a synchronized version of the given closure.
	 * 
	 * @param <T> Type of the value.
	 * @param <X> Type of the exceptions.
	 * @param closure Closure to synchronize.
	 * @return The synchronized closure.
	 */
	public static <T, X extends Exception> Closure<T, X> synchronize(final Closure<T, ? extends X> closure) {
		return new Closure<T, X>() {
			@Override
			public T evaluate()
			throws X {
				return ClosureUtils.synchronizedEvaluate(closure);
			}
			
			@Override
			public boolean isEvaluated() {
				return closure.isEvaluated();
			}
			
			@Override
			public Maybe<T> asMaybe() {
				return closure.asMaybe();
			}
		};
	}
	
	private Closures() {
		// Prevents instantiation.
	}
}
