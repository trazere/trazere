/*
 *  Copyright 2006-2015 Julien Dufour
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.trazere.core.lang;

import com.trazere.core.functional.Function2;
import com.trazere.core.functional.Function3;
import com.trazere.core.functional.Predicate;
import com.trazere.core.functional.Predicate2;
import com.trazere.core.functional.PredicateUtils;
import com.trazere.core.imperative.PairIterator;
import com.trazere.core.imperative.Procedure2;
import com.trazere.core.util.Maybe;
import com.trazere.core.util.Tuple2;

/**
 * The {@link PairIterable} interface defines {@link Iterable iterables} of pairs of elements.
 * 
 * @param <E1> Type of the first element of the pairs.
 * @param <E2> Type of the second element of the pairs.
 * @since 2.0
 */
// FIXME: should be a functional interface, bug eclipse ?
//@FunctionalInterface
public interface PairIterable<E1, E2>
extends ExIterable<Tuple2<E1, E2>>, PairTraversable<E1, E2> {
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
	
	// ExIterable.
	
	/**
	 * @see IterableUtils#unmodifiable(PairIterable)
	 * @since 2.0
	 */
	@Override
	default PairIterable<E1, E2> unmodifiable() {
		return IterableUtils.unmodifiable(this);
	}
	
	// Iterable.
	
	/**
	 * @since 2.0
	 */
	@Override
	PairIterator<E1, E2> iterator();
	
	// PairTraversable.
	
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
	 * Gets the element extracted from any pair of elements provided by this iterable by the given extractor.
	 * 
	 * @see IterableUtils#extractAny(Iterable, Function2)
	 * @since 2.0
	 */
	@Override
	default <EE> Maybe<EE> extractAny(final Function2<? super E1, ? super E2, ? extends Maybe<? extends EE>> extractor) {
		return IterableUtils.extractAny(this, extractor);
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
	
	// Note: flatMap is not defined here because Java does not support higher order type parameters.
	//	/**
	//	 * Transforms and flattens the pairs of elements provided by this iterable using the given function.
	//	 *
	//	 * @param <TE> Type of the transformed elements.
	//	 * @param function Function to use to transform the pairs of elements.
	//	 * @return An iterable providing the flatten, transformed elements.
	//	 * @see IterableUtils#flatMap(Iterable, Function2)
	//	 * @since 2.0
	//	 */
	//	default <TE> ExIterable<TE> flatMap(final Function2<? super E1, ? super E2, ? extends Iterable<? extends TE>> function) {
	//		return IterableUtils.flatMap(this, function);
	//	}
	
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
	
	// Traversable.
	
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
	default PairIterable<E1, E2> filter(final Predicate<? super Tuple2<E1, E2>> filter) {
		return IterableUtils.filter(this, PredicateUtils.curry2(filter));
	}
}
