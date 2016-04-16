package com.trazere.core.lang;

import com.trazere.core.functional.Function2;
import com.trazere.core.functional.Function3;
import com.trazere.core.functional.Predicate2;
import com.trazere.core.imperative.Procedure2;
import com.trazere.core.util.Maybe;
import com.trazere.core.util.Tuple2;

/**
 * The {@link PairTraversableIterable} interface defines generic containers of pairs of elements that can be iterated and traversed.
 * 
 * @param <E1> Type of the first element of the pairs.
 * @param <E2> Type of the second element of the pairs.
 * @since 2.0
 */
public interface PairTraversableIterable<E1, E2>
extends TraversableIterable<Tuple2<E1, E2>>, PairTraversable<E1, E2> {
	/**
	 * Left folds over the pairs of elements provided by this iterable using the given operator and initial state.
	 * <p>
	 * The pairs of elements are folded according their iteration order.
	 * 
	 * @see IterableUtils#fold(Iterable, Function3, Object)
	 * @since 2.0
	 */
	@Override
	default <S> S fold(final Function3<? super S, ? super E1, ? super E2, ? extends S> operator, final S initialState) {
		return IterableUtils.fold(this, operator, initialState);
	}
	
	/**
	 * Tests whether any pair of elements provided by this iterable is accepted by the given filter.
	 * 
	 * @see IterableUtils#isAny(Iterable, Predicate2)
	 * @since 2.0
	 */
	@Override
	default boolean isAny(final Predicate2<? super E1, ? super E2> filter) {
		return IterableUtils.isAny(this, filter);
	}
	
	/**
	 * Tests whether all pairs of elements provided by this iterable are accepted by the given filter.
	 * 
	 * @see IterableUtils#areAll(Iterable, Predicate2)
	 * @since 2.0
	 */
	@Override
	default boolean areAll(final Predicate2<? super E1, ? super E2> filter) {
		return IterableUtils.areAll(this, filter);
	}
	
	/**
	 * Counts the pairs of elements provided by this iterable accepted by the given filter.
	 * 
	 * @see IterableUtils#count(Iterable, Predicate2)
	 * @since 2.0
	 */
	@Override
	default int count(final Predicate2<? super E1, ? super E2> filter) {
		return IterableUtils.count(this, filter);
	}
	
	/**
	 * Gets any pair of elements provided by this iterable accepted by the given filter.
	 * <p>
	 * The elements are filtered according to their iteration order.
	 * 
	 * @see IterableUtils#filterAny(Iterable, Predicate2)
	 * @since 2.0
	 */
	@Override
	default Maybe<Tuple2<E1, E2>> filterAny(final Predicate2<? super E1, ? super E2> filter) {
		return IterableUtils.filterAny(this, filter);
	}
	
	/**
	 * Gets the element extracted from any pair of elements provided by this iterable by the given extractor.
	 * <p>
	 * The elements are extracted from according to their iteration order.
	 * 
	 * @see IterableUtils#extractAny(Iterable, Function2)
	 * @since 2.0
	 */
	@Override
	default <EE> Maybe<EE> extractAny(final Function2<? super E1, ? super E2, ? extends Maybe<? extends EE>> extractor) {
		return IterableUtils.extractAny(this, extractor);
	}
	
	/**
	 * Executes the given procedure with each pair of elements provided by this iterable.
	 * 
	 * @see IterableUtils#foreach(Iterable, Procedure2)
	 * @since 2.0
	 */
	@Override
	default void foreach(final Procedure2<? super E1, ? super E2> procedure) {
		IterableUtils.foreach(this, procedure);
	}
}
