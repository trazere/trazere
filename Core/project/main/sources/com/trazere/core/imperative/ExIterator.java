package com.trazere.core.imperative;

import com.trazere.core.collection.CollectionFactory;
import com.trazere.core.functional.Function;
import com.trazere.core.functional.Function2;
import com.trazere.core.functional.Predicate;
import com.trazere.core.util.Maybe;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;

/**
 * The {@link ExIterator} interfaces defines extended iterators.
 * 
 * @param <E> Type of the elements.
 * @since 2.0
 */
public interface ExIterator<E>
extends Iterator<E> {
	/**
	 * Builds an extended view of the given iterator.
	 * 
	 * @param <E> Type of the elements.
	 * @param iterator Iterator to wrap.
	 * @return The extended view of the given iterator, or the given iterator when it is already an extended view.
	 * @since 2.0
	 */
	@SuppressWarnings("unchecked")
	public static <E> ExIterator<E> build(final Iterator<? extends E> iterator) {
		assert null != iterator;
		
		if (iterator instanceof ExIterator) {
			return (ExIterator<E>) iterator;
		} else {
			return new ExIterator<E>() {
				@Override
				public boolean hasNext() {
					return iterator.hasNext();
				}
				
				@Override
				public E next() {
					return iterator.next();
				}
				
				@Override
				public void remove() {
					iterator.remove();
				}
			};
		}
	}
	
	// TODO: make name coherent with Utils -> getNext() ? => inverse tail and getTail in Feed ?
	/**
	 * Polls the next element provided by this iterator.
	 * 
	 * @return The next element, or nothing when the iterator is exhausted.
	 * @since 2.0
	 */
	default Maybe<E> poll() {
		return IteratorUtils.poll(this);
	}
	
	/**
	 * Drains the next n elements provided by this iterator.
	 * 
	 * @param n Number of elements to drain.
	 * @since 2.0
	 */
	default void drain(final int n) {
		IteratorUtils.drain(this, n);
	}
	
	/**
	 * Drains the next n elements provided by this iterator and populates the given accumulator with them.
	 * 
	 * @param <A> Type of the accumulator to populate.
	 * @param n Number of elements to drain.
	 * @param results Accumulator to populate with the drained elements.
	 * @return The given result accumulator.
	 * @since 2.0
	 */
	default <A extends Accumulator<? super E, ?>> A drain(final int n, final A results) {
		return IteratorUtils.drain(this, n, results);
	}
	
	/**
	 * Drains the next n elements provided by this iterator and adds them to the given collection.
	 * 
	 * @param <C> Type of the collection to populate.
	 * @param n Number of elements to drain.
	 * @param results Collection to populate with the drained elements.
	 * @return The given result collection.
	 * @since 2.0
	 */
	default <C extends Collection<? super E>> C drain(final int n, final C results) {
		return IteratorUtils.drain(this, n, results);
	}
	
	// TODO: rename to drainAll
	/**
	 * Drains all elements provided by the this iterator.
	 * 
	 * @since 2.0
	 */
	default void drain() {
		IteratorUtils.drain(this);
	}
	
	// TODO: rename to drainAll
	/**
	 * Drains all elements provided by the this iterator and populates the given accumulator with them.
	 * 
	 * @param <A> Type of the accumulator to populate.
	 * @param results Accumulator to populate with the drained elements.
	 * @return The given result accumulator.
	 * @since 2.0
	 */
	default <A extends Accumulator<? super E, ?>> A drain(final A results) {
		return IteratorUtils.drain(this, results);
	}
	
	// TODO: rename to drainAll
	/**
	 * Drains all elements provided by the this iterator and adds them to the given collection.
	 * 
	 * @param <C> Type of the collection to populate.
	 * @param results Collection to populate with the drained elements.
	 * @return The given result collection.
	 * @since 2.0
	 */
	default <C extends Collection<? super E>> C drain(final C results) {
		return IteratorUtils.drain(this, results);
	}
	
	/**
	 * Executes the given procedure with each element provided by this iterator.
	 * 
	 * @param procedure Procedure to execute.
	 * @since 2.0
	 */
	default void foreach(final Procedure<? super E> procedure) {
		IteratorUtils.foreach(this, procedure);
	}
	
	/**
	 * Left folds over the elements provided by this iterator using the given binary operator and initial state.
	 * 
	 * @param <S> Type of the state.
	 * @param operator Binary operator to use.
	 * @param initialState Initial state.
	 * @return The folded state.
	 * @since 2.0
	 */
	default <S> S fold(final Function2<? super S, ? super E, ? extends S> operator, final S initialState) {
		return IteratorUtils.fold(this, operator, initialState);
	}
	
	/**
	 * Gets the first element provided by this iterator accepted by the given filter.
	 * 
	 * @param filter Predicate to use to filter the elements.
	 * @return The first accepted element.
	 * @since 2.0
	 */
	default Maybe<E> first(final Predicate<? super E> filter) {
		return IteratorUtils.first(this, filter);
	}
	
	/**
	 * Get the first element extracted from the elements provided by this iterator using the given extractor.
	 * 
	 * @param <EE> Type of the extracted elements.
	 * @param extractor Function to use to extract the elements.
	 * @return The first extracted element.
	 * @since 2.0
	 */
	default <EE> Maybe<EE> extractFirst(final Function<? super E, ? extends Maybe<? extends EE>> extractor) {
		return IteratorUtils.extractFirst(this, extractor);
	}
	
	/**
	 * Tests whether any element provided by this iterator is accepted by the given filter.
	 * 
	 * @param filter Predicate to use to filter the elements.
	 * @return <code>true</code> when some element is accepted, <code>false</code> when all elements are rejected.
	 * @since 2.0
	 */
	default boolean isAny(final Predicate<? super E> filter) {
		return IteratorUtils.isAny(this, filter);
	}
	
	/**
	 * Tests whether all elements provided by this iterator are accepted by the given filter.
	 * 
	 * @param filter Predicate to use to filter the elements.
	 * @return <code>true</code> when all elements are accepted, <code>false</code> when some element is rejected.
	 * @since 2.0
	 */
	default boolean areAll(final Predicate<? super E> filter) {
		return IteratorUtils.areAll(this, filter);
	}
	
	/**
	 * Counts the elements provided by this iterator accepted by the given filter.
	 * 
	 * @param filter Predicate to use to filter the elements.
	 * @return The number of accepted elements.
	 * @since 2.0
	 */
	default int count(final Predicate<? super E> filter) {
		return IteratorUtils.count(this, filter);
	}
	
	/**
	 * Gets the least element provided by this iterator according to the given comparator.
	 *
	 * @param comparator Comparator to use.
	 * @return The least element.
	 * @since 2.0
	 */
	default Maybe<E> least(final Comparator<? super E> comparator) {
		return IteratorUtils.least(this, comparator);
	}
	
	/**
	 * Gets the greatest element provided by this iterator according to the given comparator.
	 *
	 * @param comparator Comparator to use.
	 * @return The greatest element.
	 * @since 2.0
	 */
	default Maybe<E> greatest(final Comparator<? super E> comparator) {
		return IteratorUtils.greatest(this, comparator);
	}
	
	/**
	 * Takes the n first elements provided by this iterator.
	 * <p>
	 * The built iterator feeds from this iterator.
	 * 
	 * @param n Number of elements to take.
	 * @return An iterator providing the taken elements.
	 * @since 2.0
	 */
	default ExIterator<E> take(final int n) {
		return IteratorUtils.take(this, n);
	}
	
	/**
	 * Drops the n first elements provided by this iterator.
	 * <p>
	 * The built iterator feeds from this iterator.
	 * 
	 * @param n Number of elements to drop.
	 * @return An iterator providing the remaining elements.
	 * @since 2.0
	 */
	default ExIterator<E> drop(final int n) {
		return IteratorUtils.drop(this, n);
	}
	
	/**
	 * Groups the elements provided by this iterator into batches of the given size.
	 *
	 * @param <B> Type of the batch collections.
	 * @param n Number of elements of each batch.
	 * @param batchFactory Factory of the batch collections.
	 * @return An iterator providing the groups of elements.
	 * @since 2.0
	 */
	default <B extends Collection<? super E>> ExIterator<B> group(final int n, final CollectionFactory<? super E, B> batchFactory) {
		return IteratorUtils.group(this, n, batchFactory);
	}
	
	/**
	 * Filters the elements provided by this iterator using the given filter.
	 * <p>
	 * The built iterator feeds from this iterator.
	 *
	 * @param filter Predicate to use to filter the elements.
	 * @return An iterator providing the filtered elements.
	 * @since 2.0
	 */
	default ExIterator<E> filter(final Predicate<? super E> filter) {
		return IteratorUtils.filter(this, filter);
	}
	
	/**
	 * Transforms the elements provided by this iterator using the given function.
	 * <p>
	 * The built iterator feeds from this iterator.
	 *
	 * @param <TE> Type of the transformed elements.
	 * @param function Function to use to transform the elements.
	 * @return An iterator providing the transformed elements.
	 * @since 2.0
	 */
	default <TE> ExIterator<TE> map(final Function<? super E, ? extends TE> function) {
		return IteratorUtils.map(this, function);
	}
	
	/**
	 * Transforms and flattens the elements provided by this iterator using the given function.
	 * <p>
	 * The built iterator feeds from this iterator.
	 * 
	 * @param <TE> Type of the transformed elements.
	 * @param extractor Function to use to transform the elements.
	 * @return An iterator providing the flatten, transformed elements.
	 * @since 2.0
	 */
	default <TE> ExIterator<TE> flatMap(final Function<? super E, ? extends Iterator<? extends TE>> extractor) {
		return IteratorUtils.flatMap(this, extractor);
	}
	
	/**
	 * Extracts the elements provided by this iterator using the given extractor.
	 * <p>
	 * The built iterator feeds from this iterator.
	 *
	 * @param <EE> Type of the extracted elements.
	 * @param extractor Function to use to extract the elements.
	 * @return An iterator providing the extracted elements.
	 * @since 2.0
	 */
	default <EE> ExIterator<EE> extract(final Function<? super E, ? extends Maybe<? extends EE>> extractor) {
		return IteratorUtils.extract(this, extractor);
	}
	
	/**
	 * Extracts and flattens the elements provided by this iterator using the given extractor.
	 * <p>
	 * The built iterator feeds from this iterator.
	 *
	 * @param <TE> Type of the extracted elements.
	 * @param extractor Function to use to extract the elements.
	 * @return An iterator providing the extracted elements.
	 * @since 2.0
	 */
	default <TE> ExIterator<TE> extractAll(final Function<? super E, ? extends Iterable<? extends TE>> extractor) {
		return IteratorUtils.extractAll(this, extractor);
	}
	
	/**
	 * Builds an unmodifiable view of this iterator.
	 * 
	 * @return An unmodifiable view of this iterator, or this iterator when is it already unmodifiable.
	 * @since 2.0
	 */
	default ExIterator<E> unmodifiable() {
		return IteratorUtils.unmodifiable(this);
	}
	
	/**
	 * Composes pairs with the elements provided by this iterator and the given iterator.
	 * <p>
	 * The pairs are composed of an element provided by each iterator in order. The extra values of the longest iterator are dropped when this iterators don't
	 * provide the same number of elements.
	 * 
	 * @param <E2> Type of the second elements.
	 * @param iterator2 Iterator providing the second elements of the pairs.
	 * @return An iterator providing the pairs of elements.
	 * @since 2.0
	 */
	default <E2> PairIterator<E, E2> zip(final Iterator<? extends E2> iterator2) {
		return IteratorUtils.zip(this, iterator2);
	}
}
