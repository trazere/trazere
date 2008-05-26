package com.trazere.util.collection;

import com.trazere.util.function.ApplicationException;
import com.trazere.util.function.Function;
import java.util.Iterator;

/**
 * The {@link FunctionIterator} abstract class represents iterator combinators which transform the iterated elements.
 * 
 * @param <T> Type of the elements of the feeds.
 * @param <R> Type of the produced elements.
 */
public abstract class FunctionIterator<T, R>
implements Iterator<R>, Function<T, R> {
	/**
	 * Build an iterator using the given feed and function.
	 * 
	 * @param <T> Type of the elements of the feeds.
	 * @param <R> Type of the produced elements.
	 * @param feed Element feed.
	 * @param function Function to use to transform the elements.
	 * @return The built iterator.
	 */
	public static <T, R> FunctionIterator<T, R> build(final Iterator<T> feed, final Function<? super T, ? extends R> function) {
		assert null != function;
		
		return new FunctionIterator<T, R>(feed) {
			public R apply(final T value)
			throws ApplicationException {
				return function.apply(value);
			}
		};
	}
	
	/** Element feed. */
	private final Iterator<T> _feed;
	
	/**
	 * Instanciate a new iterator with the given feed.
	 * 
	 * @param feed Element feed.
	 */
	public FunctionIterator(final Iterator<T> feed) {
		assert null != feed;
		
		// Initialization.
		_feed = feed;
	}
	
	public boolean hasNext() {
		return _feed.hasNext();
	}
	
	public R next() {
		return apply(_feed.next());
	}
	
	public void remove() {
		_feed.remove();
	}
}
