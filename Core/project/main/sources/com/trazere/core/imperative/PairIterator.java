package com.trazere.core.imperative;

import com.trazere.core.collection.Multimap;
import com.trazere.core.functional.Function2;
import com.trazere.core.functional.Function3;
import com.trazere.core.functional.Predicate2;
import com.trazere.core.util.Maybe;
import com.trazere.core.util.Tuple2;
import java.util.Iterator;
import java.util.Map;

/**
 * The {@link PairIterator} interfaces defines extended iterators over pairs of elements.
 * 
 * @param <E1> Type of the first element of the pairs.
 * @param <E2> Type of the second element of the pairs.
 * @since 2.0
 */
public interface PairIterator<E1, E2>
extends ExIterator<Tuple2<E1, E2>> {
	/**
	 * Builds an extended iterator that wraps this iterator.
	 * 
	 * @param <E1> Type of the first element of the pairs.
	 * @param <E2> Type of the second element of the pairs.
	 * @param iterator Iterator to wrap.
	 * @return The extended iterator.
	 * @since 2.0
	 */
	@SuppressWarnings("unchecked")
	public static <E1, E2> PairIterator<E1, E2> fromIterator(final Iterator<? extends Tuple2<E1, E2>> iterator) {
		assert null != iterator;
		
		if (iterator instanceof PairIterator) {
			return (PairIterator<E1, E2>) iterator;
		} else {
			return new PairIterator<E1, E2>() {
				@Override
				public boolean hasNext() {
					return iterator.hasNext();
				}
				
				@Override
				public Tuple2<E1, E2> next() {
					return iterator.next();
				}
				
				@Override
				public void remove() {
					iterator.remove();
				}
			};
		}
	}
	
	/**
	 * Drains the next n pairs of elements provided by this iterator and populates the given accumulator with them.
	 * 
	 * @param <A> Type of the accumulator to populate.
	 * @param n Number of pairs of elements to drain.
	 * @param results Accumulator to populate with the drained pairs of elements.
	 * @return The given result accumulator.
	 * @since 2.0
	 */
	default <A extends Accumulator2<? super E1, ? super E2, ?>> A drain(final int n, final A results) {
		return IteratorUtils.drain(this, n, results);
	}
	
	/**
	 * Drains the next n pairs of elements provided by this iterator and puts the corresponding bindings into the given map.
	 * 
	 * @param <M> Type of the map to populate.
	 * @param n Number of elements to drain.
	 * @param results Collection to populate with the drained elements.
	 * @return The given result collection.
	 * @since 2.0
	 */
	default <M extends Map<? super E1, ? super E2>> M drain(final int n, final M results) {
		return IteratorUtils.drain(this, n, results);
	}
	
	/**
	 * Drains the next n pairs of elements provided by this iterator and puts the corresponding bindings into the given multimap.
	 * 
	 * @param <M> Type of the multimap to populate.
	 * @param n Number of elements to drain.
	 * @param results Collection to populate with the drained elements.
	 * @return The given result collection.
	 * @since 2.0
	 */
	default <M extends Multimap<? super E1, ? super E2, ?>> M drain(final int n, final M results) {
		return IteratorUtils.drain(this, n, results);
	}
	
	/**
	 * Drains all pairs of elements provided by the this iterator and populates the given accumulator with them.
	 * 
	 * @param <A> Type of the accumulator to populate.
	 * @param results Accumulator to populate with the drained pairs of elements.
	 * @return The given result accumulator.
	 * @since 2.0
	 */
	default <A extends Accumulator2<? super E1, ? super E2, ?>> A drain(final A results) {
		return IteratorUtils.drain(this, results);
	}
	
	/**
	 * Drains all pairs of elements provided by the this iterator and puts the corresponding bindings into the given map.
	 * 
	 * @param <M> Type of the map to populate.
	 * @param results Map to populate with the drained pairs of elements.
	 * @return The given result collection.
	 * @since 2.0
	 */
	default <M extends Map<? super E1, ? super E2>> M drain(final M results) {
		return IteratorUtils.drain(this, results);
	}
	
	/**
	 * Drains all pairs of elements provided by the this iterator and puts the corresponding bindings into the given multimap.
	 * 
	 * @param <M> Type of the multimap to populate.
	 * @param results Multimap to populate with the drained pairs of elements.
	 * @return The given result collection.
	 * @since 2.0
	 */
	default <M extends Multimap<? super E1, ? super E2, ?>> M drain(final M results) {
		return IteratorUtils.drain(this, results);
	}
	
	/**
	 * Executes the given procedure with each pair of elements provided by this iterator.
	 * 
	 * @param procedure Procedure to execute.
	 * @since 2.0
	 */
	default void foreach(final Procedure2<? super E1, ? super E2> procedure) {
		IteratorUtils.foreach(this, procedure);
	}
	
	/**
	 * Left folds over the pairs of elements provided by this iterator using the given operator and initial state.
	 * 
	 * @param <S> Type of the state.
	 * @param operator Operator to use.
	 * @param initialState Initial state.
	 * @return The folded state.
	 * @since 2.0
	 */
	default <S> S fold(final Function3<? super S, ? super E1, ? super E2, ? extends S> operator, final S initialState) {
		return IteratorUtils.fold(this, operator, initialState);
	}
	
	/**
	 * Gets the first pair of elements provided by this iterator accepted by the given filter.
	 * 
	 * @param filter Predicate to use to filter the pairs of elements.
	 * @return The first accepted pair of elements.
	 * @since 2.0
	 */
	default Maybe<Tuple2<E1, E2>> first(final Predicate2<? super E1, ? super E2> filter) {
		return IteratorUtils.first(this, filter);
	}
	
	/**
	 * Gets the first element extracted from the pairs of elements provided by this iterator using the given extractor.
	 * 
	 * @param <EE> Type of the extracted elements.
	 * @param extractor Function to use to extract the elements.
	 * @return The first extracted element.
	 * @since 2.0
	 */
	default <EE> Maybe<EE> extractFirst(final Function2<? super E1, ? super E2, ? extends Maybe<? extends EE>> extractor) {
		return IteratorUtils.extractFirst(this, extractor);
	}
	
	/**
	 * Tests whether any pair of elements provided by this iterator is accepted by the given filter.
	 * 
	 * @param filter Predicate to use to filter the pairs of elements.
	 * @return <code>true</code> when some pair of elements is accepted, <code>false</code> when all pairs of elements are rejected.
	 * @since 2.0
	 */
	default boolean isAny(final Predicate2<? super E1, ? super E2> filter) {
		return IteratorUtils.isAny(this, filter);
	}
	
	/**
	 * Tests whether all pairs of elements provided by this iterator are accepted by the given filter.
	 * 
	 * @param filter Predicate to use to filter the pairs of elements.
	 * @return <code>true</code> when all pairs of elements are accepted, <code>false</code> when some pair of elements is rejected.
	 * @since 2.0
	 */
	default boolean areAll(final Predicate2<? super E1, ? super E2> filter) {
		return IteratorUtils.areAll(this, filter);
	}
	
	/**
	 * Counts the pairs of elements provided by this iterator accepted by the given filter.
	 * 
	 * @param filter Predicate to use to filter the pairs of elements.
	 * @return The number of accepted pairs of elements.
	 * @since 2.0
	 */
	default int count(final Predicate2<? super E1, ? super E2> filter) {
		return IteratorUtils.count(this, filter);
	}
	
	/**
	 * Filters the pairs of elements provided by this iterator using the given filter.
	 * <p>
	 * The built iterator feeds from this iterator.
	 *
	 * @param filter Predicate to use to filter the pairs of elements.
	 * @return An iterator providing the filtered pairs of elements.
	 * @since 2.0
	 */
	default PairIterator<E1, E2> filter(final Predicate2<? super E1, ? super E2> filter) {
		return IteratorUtils.filter(this, filter);
	}
	
	/**
	 * Transforms the pairs of elements provided by this iterator using the given function.
	 * <p>
	 * The built iterator feeds from this iterator.
	 *
	 * @param <TE> Type of the transformed elements.
	 * @param function Function to use to transform the pairs of elements.
	 * @return An iterator providing the transformed elements.
	 * @since 2.0
	 */
	default <TE> ExIterator<TE> map(final Function2<? super E1, ? super E2, ? extends TE> function) {
		return IteratorUtils.map(this, function);
	}
	
	/**
	 * Transforms and flattens the pairs of elements provided by this iterator using the given function.
	 * <p>
	 * The built iterator feeds from this iterator.
	 * 
	 * @param <TE> Type of the transformed elements.
	 * @param function Function to use to transform the pairs of elements.
	 * @return An iterator providing the flatten, transformed elements.
	 * @since 2.0
	 */
	default <TE> ExIterator<TE> flatMap(final Function2<? super E1, ? super E2, ? extends Iterator<? extends TE>> function) {
		return IteratorUtils.flatMap(this, function);
	}
	
	/**
	 * Extracts the pairs of elements provided by this iterator using the given extractor.
	 * <p>
	 * The built iterator feeds from this iterator.
	 *
	 * @param <EE> Type of the extracted elements.
	 * @param extractor Function to use to extract the pairs of elements.
	 * @return An iterator providing the extracted elements.
	 * @since 2.0
	 */
	default <EE> ExIterator<EE> extract(final Function2<? super E1, ? super E2, ? extends Maybe<? extends EE>> extractor) {
		return IteratorUtils.extract(this, extractor);
	}
	
	/**
	 * Extracts and flattens the pairs of elements provided by this iterator using the given extractor.
	 * <p>
	 * The built iterator feeds from this iterator.
	 *
	 * @param <EE> Type of the extracted elements.
	 * @param extractor Function to use to extract the pairs of elements.
	 * @return An iterator providing the extracted elements.
	 * @since 2.0
	 */
	default <EE> ExIterator<EE> extractAll(final Function2<? super E1, ? super E2, ? extends Iterable<? extends EE>> extractor) {
		return IteratorUtils.extractAll(this, extractor);
	}
	
	/**
	 * Builds an unmodifiable view of this iterator.
	 * 
	 * @return An unmodifiable view of this iterator, or this iterator when is it already unmodifiable.
	 * @since 2.0
	 */
	@Override
	default PairIterator<E1, E2> unmodifiable() {
		return IteratorUtils.unmodifiable(this);
	}
}
