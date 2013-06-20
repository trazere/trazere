package com.trazere.util.collection;

import com.trazere.util.function.Predicate1;
import java.util.Collection;

/**
 * The {@link CollectionPredicates} class provides various factories of predicates related to collections.
 */
public class CollectionPredicates {
	/**
	 * Builds a predicate that evaluates to <code>true</code> for empty collections.
	 * 
	 * @param <C> Type of the collections.
	 * @param <X> Type of the exceptions.
	 * @return The built predicate.
	 */
	@SuppressWarnings("unchecked")
	public static <C extends Collection<?>, X extends Exception> Predicate1<C, X> isEmpty() {
		return (Predicate1<C, X>) _IS_EMPTY;
	}
	
	private static Predicate1<Collection<?>, ?> _IS_EMPTY = new Predicate1<Collection<?>, RuntimeException>() {
		@Override
		public boolean evaluate(final Collection<?> collection) {
			assert null != collection;
			
			return collection.isEmpty();
		}
	};
	
	/**
	 * Builds a predicate that evaluates to <code>true</code> when the argument collection contains the given value.
	 * 
	 * @param <T> Type of the argument values.
	 * @param <C> Type of the collections.
	 * @param <X> Type of the exceptions.
	 * @param value The value. May be <code>null</code>.
	 * @return The built predicate.
	 */
	public static <T, C extends Collection<? super T>, X extends Exception> Predicate1<C, X> contains(final T value) {
		return new Predicate1<C, X>() {
			@Override
			public boolean evaluate(final C collection)
			throws X {
				assert null != collection;
				
				return collection.contains(value);
			}
		};
	}
	
	private CollectionPredicates() {
		// Prevents instantiation.
	}
}
