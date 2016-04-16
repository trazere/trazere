package com.trazere.core.lang;

import com.trazere.core.functional.Function2;
import com.trazere.core.functional.Predicate2;
import com.trazere.core.imperative.PairIterator;
import com.trazere.core.util.Maybe;
import com.trazere.core.util.Tuple2;

/**
 * The {@link PairIterable} interface defines extended iterables.
 * 
 * @param <E1> Type of the first element of the pairs.
 * @param <E2> Type of the second element of the pairs.
 * @since 2.0
 */
// FIXME: should be a functional interface, bug eclipse ?
//@FunctionalInterface
public interface PairIterable<E1, E2>
extends ExIterable<Tuple2<E1, E2>>, PairTraversableIterable<E1, E2> {
	// TODO: rename -> fromIterable ?
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
	
	/**
	 * @since 2.0
	 */
	@Override
	PairIterator<E1, E2> iterator();
	
	/**
	 * Takes n pairs of elements provided by this iterable.
	 * <p>
	 * The pairs of elements are taken according their iteration order.
	 * 
	 * @return An iterable providing the taken pairs of elements.
	 * @see IterableUtils#take2(Iterable, int)
	 * @since 2.0
	 */
	@Override
	default PairIterable<E1, E2> take(final int n) {
		return IterableUtils.take2(this, n);
	}
	
	/**
	 * Drops n pairs of elements provided by this iterable.
	 * <p>
	 * The pairs of elements are dropped according their iteration order.
	 * 
	 * @return An iterable providing the remaining pairs of elements.
	 * @see IterableUtils#drop2(Iterable, int)
	 * @since 2.0
	 */
	@Override
	default PairIterable<E1, E2> drop(final int n) {
		return IterableUtils.drop2(this, n);
	}
	
	/**
	 * Filters the pairs of elements provided by this iterable using the given filter.
	 *
	 * @return An iterable providing the filtered pairs of elements.
	 * @see IterableUtils#filter(Iterable, Predicate2)
	 * @since 2.0
	 */
	@Override
	default PairIterable<E1, E2> filter(final Predicate2<? super E1, ? super E2> filter) {
		return IterableUtils.filter(this, filter);
	}
	
	/**
	 * Transforms the pairs of elements provided by this iterable using the given function.
	 *
	 * @return An iterable providing the transformed elements.
	 * @see IterableUtils#map(Iterable, Function2)
	 * @since 2.0
	 */
	@Override
	default <TE> ExIterable<TE> map(final Function2<? super E1, ? super E2, ? extends TE> function) {
		return IterableUtils.map(this, function);
	}
	
	/**
	 * Extracts the pairs of elements provided by this iterable using the given extractor.
	 *
	 * @return An iterable providing the extracted elements.
	 * @see IterableUtils#extract(Iterable, Function2)
	 * @since 2.0
	 */
	@Override
	default <EE> ExIterable<EE> extract(final Function2<? super E1, ? super E2, ? extends Maybe<? extends EE>> extractor) {
		return IterableUtils.extract(this, extractor);
	}
	
	/**
	 * Gets all elements extracted from the pairs of elements provided by this iterable by the given extractor.
	 * 
	 * @param <EE> Type of the extracted elements.
	 * @param extractor Function to use to extract from the pairs of elements.
	 * @return An iterable of the extracted elements.
	 * @see IterableUtils#extractAll(Iterable, Function2)
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
	 * @see IterableUtils#flatMap(Iterable, Function2)
	 * @since 2.0
	 */
	default <TE> ExIterable<TE> flatMap(final Function2<? super E1, ? super E2, ? extends Iterable<? extends TE>> function) {
		return IterableUtils.flatMap(this, function);
	}
	
	/**
	 * @see IterableUtils#unmodifiable(PairIterable)
	 * @since 2.0
	 */
	@Override
	default PairIterable<E1, E2> unmodifiable() {
		return IterableUtils.unmodifiable(this);
	}
}
