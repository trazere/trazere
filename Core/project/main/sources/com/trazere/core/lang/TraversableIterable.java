package com.trazere.core.lang;

import com.trazere.core.functional.Function;
import com.trazere.core.functional.Function2;
import com.trazere.core.functional.Predicate;
import com.trazere.core.imperative.Procedure;
import com.trazere.core.util.Maybe;
import java.util.Comparator;

/**
 * The {@link TraversableIterable} interface defines generic containers of elements that can be iterated and traversed.
 * 
 * @param <E> Type of the elements.
 * @since 2.0
 */
public interface TraversableIterable<E>
extends Iterable<E>, Traversable<E> {
	/**
	 * Left folds over the elements provided by this iterable using the given binary operator and initial state.
	 * <p>
	 * The elements are folded according their iteration order.
	 * 
	 * @see IterableUtils#fold(Iterable, Function2, Object)
	 * @since 2.0
	 */
	@Override
	default <S> S fold(final Function2<? super S, ? super E, ? extends S> operator, final S initialState) {
		return IterableUtils.fold(this, operator, initialState);
	}
	
	/**
	 * Tests whether any element provided by this iterable is accepted by the given filter.
	 * 
	 * @see IterableUtils#isAny(Iterable, Predicate)
	 * @since 2.0
	 */
	@Override
	default boolean isAny(final Predicate<? super E> filter) {
		return IterableUtils.isAny(this, filter);
	}
	
	/**
	 * Tests whether all elements provided by this iterable are accepted by the given filter.
	 * 
	 * @see IterableUtils#areAll(Iterable, Predicate)
	 * @since 2.0
	 */
	@Override
	default boolean areAll(final Predicate<? super E> filter) {
		return IterableUtils.areAll(this, filter);
	}
	
	/**
	 * Counts the elements provided by this iterable accepted by the given filter.
	 * 
	 * @see IterableUtils#count(Iterable, Predicate)
	 * @since 2.0
	 */
	@Override
	default int count(final Predicate<? super E> filter) {
		return IterableUtils.count(this, filter);
	}
	
	/**
	 * Gets the least element provided by this iterable according to the given comparator.
	 *
	 * @see IterableUtils#least(Iterable, Comparator)
	 * @since 2.0
	 */
	@Override
	default Maybe<E> least(final Comparator<? super E> comparator) {
		return IterableUtils.least(this, comparator);
	}
	
	/**
	 * Gets the greatest element provided by this iterable according to the given comparator.
	 *
	 * @see IterableUtils#greatest(Iterable, Comparator)
	 * @since 2.0
	 */
	@Override
	default Maybe<E> greatest(final Comparator<? super E> comparator) {
		return IterableUtils.greatest(this, comparator);
	}
	
	/**
	 * Gets any element provided by this iterable accepted by the given filter.
	 * <p>
	 * The elements are filtered according to their iteration order.
	 * 
	 * @see IterableUtils#filterAny(Iterable, Predicate)
	 * @since 2.0
	 */
	@Override
	default Maybe<E> filterAny(final Predicate<? super E> filter) {
		return IterableUtils.filterAny(this, filter);
	}
	
	/**
	 * Gets the element extracted from any element provided by this iterable using the given extractor.
	 * <p>
	 * The elements are extracted from according to their iteration order.
	 * 
	 * @see IterableUtils#extractAny(Iterable, Function)
	 * @since 2.0
	 */
	@Override
	default <EE> Maybe<EE> extractAny(final Function<? super E, ? extends Maybe<? extends EE>> extractor) {
		return IterableUtils.extractAny(this, extractor);
	}
	
	/**
	 * Executes the given procedure with each element provided by this iterable.
	 * 
	 * @see IterableUtils#foreach(Iterable, Procedure)
	 * @since 2.0
	 */
	@Override
	default void foreach(final Procedure<? super E> procedure) {
		IterableUtils.foreach(this, procedure);
	}
}
