package com.trazere.core.lang;

import com.trazere.core.functional.Function2;
import com.trazere.core.functional.Function3;
import com.trazere.core.functional.Predicate2;
import com.trazere.core.imperative.PairIterator;
import com.trazere.core.imperative.Procedure2;
import com.trazere.core.util.Maybe;
import com.trazere.core.util.Tuple2;

/**
 * The {@link PairIterable} interface defines extended iterables.
 * 
 * @param <E1> Type of the first element of the pairs.
 * @param <E2> Type of the second element of the pairs.
 * @since 2.0
 */
public interface PairIterable<E1, E2>
extends ExIterable<Tuple2<E1, E2>>, PairTraversable<E1, E2> {
	/**
	 * Builds an extended view of the given iterable of pairs of elements.
	 * 
	 * @param <E1> Type of the first element of the pairs.
	 * @param <E2> Type of the second element of the pairs.
	 * @param iterable Iterable to wrap.
	 * @return The extended view of the given iterable, or the given iterable when it is already an extended view.
	 * @since 2.0
	 */
	@SuppressWarnings("unchecked")
	public static <E1, E2> PairIterable<E1, E2> build(final Iterable<? extends Tuple2<E1, E2>> iterable) {
		assert null != iterable;
		
		if (iterable instanceof PairIterable) {
			return (PairIterable<E1, E2>) iterable;
		} else {
			// HACK: not a lambda to work around a bug of Eclipse
			return new PairIterable<E1, E2>() {
				@Override
				public PairIterator<E1, E2> iterator() {
					return PairIterator.build(iterable.iterator());
				}
			};
		}
	}
	
	@Override
	PairIterator<E1, E2> iterator();
	
	/**
	 * Left folds over the pairs of elements provided by this iterable using the given operator and initial state.
	 * 
	 * @param <S> Type of the state.
	 * @param operator Operator to use.
	 * @param initialState Initial state.
	 * @return The folded state.
	 * @since 2.0
	 */
	@Override
	default <S> S fold(final Function3<? super S, ? super E1, ? super E2, ? extends S> operator, final S initialState) {
		return IterableUtils.fold(this, operator, initialState);
	}
	
	/**
	 * Tests whether any pair of elements provided by this iterable is accepted by the given filter.
	 * 
	 * @param filter Predicate to use to filter the pairs of elements.
	 * @return <code>true</code> when some pair of elements is accepted, <code>false</code> when all pairs of elements are rejected.
	 * @since 2.0
	 */
	@Override
	default boolean isAny(final Predicate2<? super E1, ? super E2> filter) {
		return IterableUtils.isAny(this, filter);
	}
	
	/**
	 * Tests whether all pairs of elements provided by this iterable are accepted by the given filter.
	 * 
	 * @param filter Predicate to use to filter the pairs of elements.
	 * @return <code>true</code> when all pairs of elements are accepted, <code>false</code> when some pair of elements is rejected.
	 * @since 2.0
	 */
	@Override
	default boolean areAll(final Predicate2<? super E1, ? super E2> filter) {
		return IterableUtils.areAll(this, filter);
	}
	
	/**
	 * Counts the pairs of elements provided by this iterable accepted by the given filter.
	 * 
	 * @param filter Predicate to use to filter the pairs of elements.
	 * @return The number of accepted pairs of elements.
	 * @since 2.0
	 */
	@Override
	default int count(final Predicate2<? super E1, ? super E2> filter) {
		return IterableUtils.count(this, filter);
	}
	
	/**
	 * Filters the pairs of elements provided by this iterable using the given filter.
	 *
	 * @param filter Predicate to use to filter the pairs of elements.
	 * @return An iterable providing the filtered pairs of elements.
	 * @since 2.0
	 */
	@Override
	default PairIterable<E1, E2> filter(final Predicate2<? super E1, ? super E2> filter) {
		return IterableUtils.filter(this, filter);
	}
	
	/**
	 * Gets the first pair of elements provided by this iterable accepted by the given filter.
	 * 
	 * @param filter Predicate to use to filter the pairs of elements.
	 * @return The first accepted pair of elements.
	 * @since 2.0
	 */
	@Override
	default Maybe<Tuple2<E1, E2>> filterFirst(final Predicate2<? super E1, ? super E2> filter) {
		return IterableUtils.filterFirst(this, filter);
	}
	
	/**
	 * Transforms the pairs of elements provided by this iterable using the given function.
	 *
	 * @param <TE> Type of the transformed elements.
	 * @param function Function to use to transform the pairs of elements.
	 * @return An iterable providing the transformed elements.
	 * @since 2.0
	 */
	@Override
	default <TE> ExIterable<TE> map(final Function2<? super E1, ? super E2, ? extends TE> function) {
		return IterableUtils.map(this, function);
	}
	
	/**
	 * Extracts the pairs of elements provided by this iterable using the given extractor.
	 *
	 * @param <EE> Type of the extracted elements.
	 * @param extractor Function to use to extract the pairs of elements.
	 * @return An iterable of the extracted elements.
	 * @since 2.0
	 */
	@Override
	default <EE> ExIterable<EE> extract(final Function2<? super E1, ? super E2, ? extends Maybe<? extends EE>> extractor) {
		return IterableUtils.extract(this, extractor);
	}
	
	/**
	 * Gets the first element extracted from the pairs of elements provided by this iterable by the given extractor.
	 * 
	 * @param <EE> Type of the extracted elements.
	 * @param extractor Function to use to extract the pairs of elements.
	 * @return The first extracted element.
	 * @since 2.0
	 */
	@Override
	default <EE> Maybe<EE> extractFirst(final Function2<? super E1, ? super E2, ? extends Maybe<? extends EE>> extractor) {
		return IterableUtils.extractFirst(this, extractor);
	}
	
	/**
	 * Gets all elements extracted from the pairs of elements provided by this iterable by the given extractor.
	 * 
	 * @param <EE> Type of the extracted elements.
	 * @param extractor Function to use to extract the pairs of elements.
	 * @return An iterable of the extracted elements.
	 * @since 2.0
	 */
	default <EE> ExIterable<EE> extractAll(final Function2<? super E1, ? super E2, ? extends Iterable<? extends EE>> extractor) {
		return IterableUtils.extractAll(this, extractor);
	}
	
	/**
	 * Transforms and flattens the pairs of elements provided by this iterable using the given function.
	 * 
	 * @param <TE> Type of the transformed elements.
	 * @param function Function to use to transform the pairs of elements.
	 * @return An iterable providing the flatten, transformed elements.
	 * @since 2.0
	 */
	default <TE> ExIterable<TE> flatMap(final Function2<? super E1, ? super E2, ? extends Iterable<? extends TE>> function) {
		return IterableUtils.flatMap(this, function);
	}
	
	/**
	 * Executes the given procedure with each pair of elements provided by this iterable.
	 * 
	 * @param procedure Procedure to execute.
	 * @since 2.0
	 */
	@Override
	default void foreach(final Procedure2<? super E1, ? super E2> procedure) {
		IterableUtils.foreach(this, procedure);
	}
	
	/**
	 * Builds an unmodifiable view of this iterable.
	 * 
	 * @return An unmodifiable view of this iterable, or this iterable when is it already unmodifiable.
	 * @since 2.0
	 */
	@Override
	default PairIterable<E1, E2> unmodifiable() {
		return IterableUtils.unmodifiable(this);
	}
}
