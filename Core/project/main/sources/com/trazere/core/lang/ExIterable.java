package com.trazere.core.lang;

import com.trazere.core.collection.CollectionFactory;
import com.trazere.core.functional.Function;
import com.trazere.core.functional.Function2;
import com.trazere.core.functional.Predicate;
import com.trazere.core.imperative.ExIterator;
import com.trazere.core.imperative.Procedure;
import com.trazere.core.util.Maybe;
import java.util.Collection;
import java.util.Comparator;
import java.util.NoSuchElementException;

/**
 * The {@link ExIterable} interface defines extended iterables.
 * 
 * @param <E> Type of the elements.
 * @since 2.0
 */
public interface ExIterable<E>
extends Iterable<E> {
	/**
	 * Builds an extended view of this iterable.
	 * 
	 * @param <E> Type of the elements.
	 * @param iterable Iterable to wrap.
	 * @return The extended view of this iterable, or this iterable when it is already an extended view.
	 * @since 2.0
	 */
	@SuppressWarnings("unchecked")
	public static <E> ExIterable<E> build(final Iterable<? extends E> iterable) {
		assert null != iterable;
		
		if (iterable instanceof ExIterable) {
			return (ExIterable<E>) iterable;
		} else {
			return new ExIterable<E>() {
				@Override
				public ExIterator<E> iterator() {
					return ExIterator.build(iterable.iterator());
				}
			};
		}
	}
	
	@Override
	ExIterator<E> iterator();
	
	/**
	 * Gets any element provided by this iterable.
	 *
	 * @return An element provided by the iterable..
	 * @throws NoSuchElementException When the iterable is empty.
	 * @see IterableUtils#any(Iterable)
	 * @since 2.0
	 */
	default E any()
	throws NoSuchElementException {
		return IterableUtils.any(this);
	}
	
	/**
	 * Gets any element provided by this iterable.
	 *
	 * @return An element provided by the iterable, or nothing when the iterable is empty.
	 * @see IterableUtils#optionalAny(Iterable)
	 * @since 2.0
	 */
	default Maybe<E> optionalAny() {
		return IterableUtils.optionalAny(this);
	}
	
	/**
	 * Executes the given procedure with each element provided by this iterable.
	 * 
	 * @param procedure Procedure to execute.
	 * @since 2.0
	 */
	default void foreach(final Procedure<? super E> procedure) {
		IterableUtils.foreach(this, procedure);
	}
	
	/**
	 * Left folds over the elements provided by this iterable using the given binary operator and initial state.
	 * 
	 * @param <S> Type of the state.
	 * @param operator Binary operator to use.
	 * @param initialState Initial state.
	 * @return The folded state.
	 * @since 2.0
	 */
	default <S> S fold(final Function2<? super S, ? super E, ? extends S> operator, final S initialState) {
		return IterableUtils.fold(this, operator, initialState);
	}
	
	/**
	 * Gets the first element provided by this iterable accepted by the given filter.
	 * 
	 * @param filter Predicate to use to filter the elements.
	 * @return The first accepted element.
	 * @since 2.0
	 */
	default Maybe<E> first(final Predicate<? super E> filter) {
		return IterableUtils.first(this, filter);
	}
	
	/**
	 * Gets the first element extracted from the elements provided by this iterable by the given extractor.
	 * 
	 * @param <EE> Type of the extracted elements.
	 * @param extractor Function to use to extract the elements.
	 * @return The first extracted element.
	 * @since 2.0
	 */
	default <EE> Maybe<? extends EE> extractFirst(final Function<? super E, ? extends Maybe<? extends EE>> extractor) {
		return IterableUtils.extractFirst(this, extractor);
	}
	
	/**
	 * Tests whether any element provided by this iterable is accepted by the given filter.
	 * 
	 * @param filter Predicate to use to filter the elements.
	 * @return <code>true</code> when some element is accepted, <code>false</code> when all elements are rejected.
	 * @since 2.0
	 */
	default boolean isAny(final Predicate<? super E> filter) {
		return IterableUtils.isAny(this, filter);
	}
	
	/**
	 * Tests whether all elements provided by this iterable are accepted by the given filter.
	 * 
	 * @param filter Predicate to use to filter the elements.
	 * @return <code>true</code> when all elements are accepted, <code>false</code> when some element is rejected.
	 * @since 2.0
	 */
	default boolean areAll(final Predicate<? super E> filter) {
		return IterableUtils.areAll(this, filter);
	}
	
	/**
	 * Counts the elements provided by this iterable accepted by the given filter.
	 * 
	 * @param filter Predicate to use to filter the elements.
	 * @return The number of accepted elements.
	 * @since 2.0
	 */
	default int count(final Predicate<? super E> filter) {
		return IterableUtils.count(this, filter);
	}
	
	/**
	 * Gets the least element provided by this iterable according to the given comparator.
	 *
	 * @param comparator Comparator to use.
	 * @return The least element.
	 * @since 2.0
	 */
	default Maybe<E> least(final Comparator<? super E> comparator) {
		return IterableUtils.least(this, comparator);
	}
	
	/**
	 * Gets the greatest element provided by this iterable according to the given comparator.
	 *
	 * @param comparator Comparator to use.
	 * @return The greatest element.
	 * @since 2.0
	 */
	default Maybe<E> greatest(final Comparator<? super E> comparator) {
		return IterableUtils.greatest(this, comparator);
	}
	
	/**
	 * Takes the n first elements provided by this iterable.
	 * 
	 * @param n Number of elements to take.
	 * @return An iterable providing the taken elements.
	 * @since 2.0
	 */
	default ExIterable<E> take(final int n) {
		return IterableUtils.take(this, n);
	}
	
	/**
	 * Drops the n first elements provided by this iterable.
	 * 
	 * @param n Number of elements to drop.
	 * @return An iterable providing the remaining elements.
	 * @since 2.0
	 */
	default ExIterable<E> drop(final int n) {
		return IterableUtils.drop(this, n);
	}
	
	/**
	 * Groups the elements provided by this iterable into batches of the given size.
	 *
	 * @param <B> Type of the batch collections.
	 * @param n Number of elements of each batch.
	 * @param batchFactory Factory of the batch collections.
	 * @return An iterable providing the groups of elements.
	 * @since 2.0
	 */
	default <B extends Collection<? super E>> ExIterable<B> group(final int n, final CollectionFactory<? super E, B> batchFactory) {
		return IterableUtils.group(this, n, batchFactory);
	}
	
	/**
	 * Filters the elements provided by this iterable using the given filter.
	 *
	 * @param filter Predicate to use to filter the elements.
	 * @return An iterable providing the filtered elements.
	 * @since 2.0
	 */
	default ExIterable<E> filter(final Predicate<? super E> filter) {
		return IterableUtils.filter(this, filter);
	}
	
	/**
	 * Transforms the elements provided by this iterable using the given function.
	 *
	 * @param <TE> Type of the transformed elements.
	 * @param function Function to use to transform the elements.
	 * @return An iterable providing the transformed elements.
	 * @since 2.0
	 */
	default <TE> ExIterable<TE> map(final Function<? super E, ? extends TE> function) {
		return IterableUtils.map(this, function);
	}
	
	/**
	 * Transforms and flattens the elements provided by this iterable using the given function.
	 * 
	 * @param <TE> Type of the transformed elements.
	 * @param function Function to use to transform the elements.
	 * @return An iterable providing the flatten, transformed elements.
	 * @since 2.0
	 */
	default <TE> ExIterable<TE> flatMap(final Function<? super E, ? extends Iterable<? extends TE>> function) {
		return IterableUtils.flatMap(this, function);
	}
	
	// TODO: extract(...) and extractAll(...) methods
	
	/**
	 * Builds an unmodifiable view of this iterable.
	 * 
	 * @return An unmodifiable view of this iterable, or this iterable when is it already unmodifiable.
	 * @since 2.0
	 */
	default ExIterable<E> unmodifiable() {
		return IterableUtils.unmodifiable(this);
	}
	
	/**
	 * Composes pairs with the elements provided by this iterable and the given iterable.
	 * <p>
	 * The pairs are composed of an element provided by each iterable in order. The extra values of the longest iterable are dropped when this iterables don't
	 * provide the same number of elements.
	 * 
	 * @param <E2> Type of the second elements.
	 * @param iterable2 Iterable providing the second elements of the pairs.
	 * @return An iterable providing the pairs of elements.
	 * @since 2.0
	 */
	default <E2> PairIterable<E, E2> zip(final Iterable<? extends E2> iterable2) {
		return IterableUtils.zip(this, iterable2);
	}
}
