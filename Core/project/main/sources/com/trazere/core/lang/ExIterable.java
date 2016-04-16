package com.trazere.core.lang;

import com.trazere.core.collection.CollectionFactory;
import com.trazere.core.functional.Function;
import com.trazere.core.functional.Predicate;
import com.trazere.core.imperative.ExIterator;
import com.trazere.core.util.Maybe;
import java.util.Collection;
import java.util.NoSuchElementException;

/**
 * The {@link ExIterable} interface defines extended iterables.
 * 
 * @param <E> Type of the elements.
 * @since 2.0
 */
@FunctionalInterface
public interface ExIterable<E>
extends TraversableIterable<E> {
	// TODO: rename -> fromIterable ?
	/**
	 * Builds an extended view of the given iterable.
	 * 
	 * @param <E> Type of the elements.
	 * @param iterable Iterable to wrap.
	 * @return The extended view of the given iterable, or the given iterable when it is already an extended view.
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
	
	/**
	 * @since 2.0
	 */
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
	 * Takes n elements provided by this iterable.
	 * <p>
	 * The elements are taken according their iteration order.
	 * 
	 * @return An iterable providing the taken elements.
	 * @see IterableUtils#take(Iterable, int)
	 * @since 2.0
	 */
	@Override
	default ExIterable<E> take(final int n) {
		return IterableUtils.take(this, n);
	}
	
	/**
	 * Drops n elements provided by this iterable.
	 * <p>
	 * The elements are dropped according their iteration order.
	 * 
	 * @return An iterable providing the remaining elements.
	 * @see IterableUtils#drop(Iterable, int)
	 * @since 2.0
	 */
	@Override
	default ExIterable<E> drop(final int n) {
		return IterableUtils.drop(this, n);
	}
	
	/**
	 * Groups the elements provided by this iterable into batches of the given size.
	 * 
	 * @return An iterable providing the batches of elements.
	 * @since 2.0
	 */
	@Override
	default <B extends Collection<? super E>> ExIterable<B> group(final int n, final CollectionFactory<? super E, B> batchFactory) {
		return IterableUtils.group(this, n, batchFactory);
	}
	
	/**
	 * Filters the elements provided by this iterable using the given filter.
	 *
	 * @return An iterable providing the filtered elements.
	 * @see IterableUtils#filter(Iterable, Predicate)
	 * @since 2.0
	 */
	@Override
	default ExIterable<E> filter(final Predicate<? super E> filter) {
		return IterableUtils.filter(this, filter);
	}
	
	/**
	 * Transforms the elements provided by this iterable using the given function.
	 *
	 * @return An iterable providing the transformed elements.
	 * @see IterableUtils#map(Iterable, Function)
	 * @since 2.0
	 */
	@Override
	default <TE> ExIterable<TE> map(final Function<? super E, ? extends TE> function) {
		return IterableUtils.map(this, function);
	}
	
	/**
	 * Extracts the elements provided by this iterable using the given extractor.
	 *
	 * @return An iterable of the extracted elements.
	 * @see IterableUtils#extract(Iterable, Function)
	 * @since 2.0
	 */
	@Override
	default <EE> ExIterable<EE> extract(final Function<? super E, ? extends Maybe<? extends EE>> extractor) {
		return IterableUtils.extract(this, extractor);
	}
	
	/**
	 * Gets all elements extracted from the elements provided by this iterable using the given extractor.
	 * 
	 * @param <EE> Type of the extracted elements.
	 * @param extractor Function to use to extract the elements.
	 * @return An iterable of the extracted elements.
	 * @see IterableUtils#extractAll(Iterable, Function)
	 * @since 2.0
	 */
	default <EE> ExIterable<EE> extractAll(final Function<? super E, ? extends Iterable<? extends EE>> extractor) {
		return IterableUtils.extractAll(this, extractor);
	}
	
	/**
	 * Transforms and flattens the elements provided by this iterable using the given function.
	 * 
	 * @param <TE> Type of the transformed elements.
	 * @param function Function to use to transform the elements.
	 * @return An iterable providing the flatten, transformed elements.
	 * @see IterableUtils#flatMap(Iterable, Function)
	 * @since 2.0
	 */
	default <TE> ExIterable<TE> flatMap(final Function<? super E, ? extends Iterable<? extends TE>> function) {
		return IterableUtils.flatMap(this, function);
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
	 * @see IterableUtils#zip(Iterable, Iterable)
	 * @since 2.0
	 */
	default <E2> PairIterable<E, E2> zip(final Iterable<? extends E2> iterable2) {
		return IterableUtils.zip(this, iterable2);
	}
	
	/**
	 * Builds an unmodifiable view of this iterable.
	 * 
	 * @return An unmodifiable view of this iterable, or this iterable when is it already unmodifiable.
	 * @see IterableUtils#unmodifiable(Iterable)
	 * @since 2.0
	 */
	default ExIterable<E> unmodifiable() {
		return IterableUtils.unmodifiable(this);
	}
}
