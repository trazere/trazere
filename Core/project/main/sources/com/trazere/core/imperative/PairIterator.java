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
package com.trazere.core.imperative;

import com.trazere.core.collection.Multimap;
import com.trazere.core.functional.Function2;
import com.trazere.core.functional.Function3;
import com.trazere.core.functional.Predicate;
import com.trazere.core.functional.Predicate2;
import com.trazere.core.functional.PredicateUtils;
import com.trazere.core.lang.PairTraversable;
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
extends ExIterator<Tuple2<E1, E2>>, PairTraversable<E1, E2> {
	// TODO: move to Iterators
	// TODO: rename -> fromIterator ?
	/**
	 * Builds an extended view of the given iterator of pairs of elements.
	 * 
	 * @param <E1> Type of the first element of the pairs.
	 * @param <E2> Type of the second element of the pairs.
	 * @param iterator Iterator to wrap.
	 * @return The extended view of the given iterator, or the given iterator when it is already an extended view.
	 * @since 2.0
	 */
	@SuppressWarnings("unchecked")
	public static <E1, E2> PairIterator<E1, E2> build(final Iterator<? extends Tuple2<E1, E2>> iterator) {
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
	
	// PairIterator.
	
	/**
	 * Drains all pairs of elements provided by the this iterator and populates the given accumulator with them.
	 * 
	 * @param <A> Type of the accumulator to populate.
	 * @param results Accumulator to populate with the drained pairs of elements.
	 * @return The given result accumulator.
	 * @see IteratorUtils#drain(Iterator, Accumulator2)
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
	 * @see IteratorUtils#drain(Iterator, Map)
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
	 * @see IteratorUtils#drain(Iterator, Multimap)
	 * @since 2.0
	 */
	default <M extends Multimap<? super E1, ? super E2, ?>> M drain(final M results) {
		return IteratorUtils.drain(this, results);
	}
	
	// ExIterator.
	
	/**
	 * Builds an unmodifiable view of this iterator.
	 * 
	 * @return An unmodifiable view of this iterator, or this iterator when is it already unmodifiable.
	 * @see IteratorUtils#unmodifiable(PairIterator)
	 * @since 2.0
	 */
	@Override
	default PairIterator<E1, E2> unmodifiable() {
		return IteratorUtils.unmodifiable(this);
	}
	
	// PairTraversable.
	
	/**
	 * Left folds over the pairs of elements provided by this iterator using the given operator and initial state.
	 * 
	 * @see IteratorUtils#fold(Iterator, Function3, Object)
	 * @since 2.0
	 */
	@Override
	default <S> S fold(final Function3<? super S, ? super E1, ? super E2, ? extends S> operator, final S initialState) {
		return IteratorUtils.fold(this, operator, initialState);
	}
	
	/**
	 * Tests whether any pair of elements provided by this iterator is accepted by the given filter.
	 * 
	 * @return <code>true</code> when some pair of elements is accepted, <code>false</code> when all pairs of elements are rejected or when the iterator is
	 *         exhausted.
	 * @see IteratorUtils#isAny(Iterator, Predicate2)
	 * @since 2.0
	 */
	@Override
	default boolean isAny(final Predicate2<? super E1, ? super E2> filter) {
		return IteratorUtils.isAny(this, filter);
	}
	
	/**
	 * Tests whether all pairs of elements provided by this iterator are accepted by the given filter.
	 * 
	 * @return <code>true</code> when all pairs of elements are accepted or when the iterator is exhausted, <code>false</code> when some pair of elements is
	 *         rejected.
	 * @see IteratorUtils#areAll(Iterator, Predicate2)
	 * @since 2.0
	 */
	@Override
	default boolean areAll(final Predicate2<? super E1, ? super E2> filter) {
		return IteratorUtils.areAll(this, filter);
	}
	
	/**
	 * Counts the pairs of elements provided by this iterator accepted by the given filter.
	 * 
	 * @see IteratorUtils#count(Iterator, Predicate2)
	 * @since 2.0
	 */
	@Override
	default int count(final Predicate2<? super E1, ? super E2> filter) {
		return IteratorUtils.count(this, filter);
	}
	
	/**
	 * Filters the pairs of elements provided by this iterator using the given filter.
	 * <p>
	 * The built iterator feeds lazyly from this iterator.
	 *
	 * @return An iterator providing the filtered pairs of elements.
	 * @see IteratorUtils#filter(Iterator, Predicate2)
	 * @since 2.0
	 */
	@Override
	default PairIterator<E1, E2> filter(final Predicate2<? super E1, ? super E2> filter) {
		return IteratorUtils.filter(this, filter);
	}
	
	/**
	 * Gets any pair of elements provided by this iterator accepted by the given filter.
	 * 
	 * @see IteratorUtils#filterAny(Iterator, Predicate2)
	 * @since 2.0
	 */
	@Override
	default Maybe<Tuple2<E1, E2>> filterAny(final Predicate2<? super E1, ? super E2> filter) {
		return IteratorUtils.filterAny(this, filter);
	}
	
	/**
	 * Transforms the pairs of elements provided by this iterator using the given function.
	 * <p>
	 * The built iterator feeds lazyly from this iterator.
	 *
	 * @return An iterator providing the transformed elements.
	 * @see IteratorUtils#map(Iterator, Function2)
	 * @since 2.0
	 */
	@Override
	default <TE> ExIterator<TE> map(final Function2<? super E1, ? super E2, ? extends TE> function) {
		return IteratorUtils.map(this, function);
	}
	
	/**
	 * Extracts the elements from the pairs of elements provided by this iterator using the given extractor.
	 * <p>
	 * The built iterator feeds lazyly from this iterator.
	 *
	 * @return An iterator providing the extracted elements.
	 * @see IteratorUtils#extract(Iterator, Function2)
	 * @since 2.0
	 */
	@Override
	default <EE> ExIterator<EE> extract(final Function2<? super E1, ? super E2, ? extends Maybe<? extends EE>> extractor) {
		return IteratorUtils.extract(this, extractor);
	}
	
	/**
	 * Gets the element extracted from any pair of elements provided by this iterator using the given extractor.
	 * 
	 * @see IteratorUtils#extractAny(Iterator, Function2)
	 * @since 2.0
	 */
	@Override
	default <EE> Maybe<EE> extractAny(final Function2<? super E1, ? super E2, ? extends Maybe<? extends EE>> extractor) {
		return IteratorUtils.extractAny(this, extractor);
	}
	
	/**
	 * Gets all elements extracted from the pairs of elements provided by this iterator using the given extractor.
	 * <p>
	 * The built iterator feeds lazyly from this iterator.
	 *
	 * @param <EE> Type of the extracted elements.
	 * @param extractor Function to use to extract from the pairs of elements.
	 * @return An iterator providing the extracted elements.
	 * @see IteratorUtils#extractAll(Iterator, Function2)
	 * @since 2.0
	 */
	default <EE> ExIterator<EE> extractAll(final Function2<? super E1, ? super E2, ? extends Iterable<? extends EE>> extractor) {
		return IteratorUtils.extractAll(this, extractor);
	}
	
	/**
	 * Transforms and flattens the pairs of elements provided by this iterator using the given function.
	 * <p>
	 * The built iterator feeds lazyly from this iterator.
	 * 
	 * @param <TE> Type of the transformed elements.
	 * @param function Function to use to transform the pairs of elements.
	 * @return An iterator providing the flatten, transformed elements.
	 * @see IteratorUtils#flatMap(Iterator, Function2)
	 * @since 2.0
	 */
	default <TE> ExIterator<TE> flatMap(final Function2<? super E1, ? super E2, ? extends Iterator<? extends TE>> function) {
		return IteratorUtils.flatMap(this, function);
	}
	
	/**
	 * Executes the given procedure with each pair of elements provided by this iterator.
	 * 
	 * @see IteratorUtils#foreach(Iterator, Procedure2)
	 * @since 2.0
	 */
	@Override
	default void foreach(final Procedure2<? super E1, ? super E2> procedure) {
		IteratorUtils.foreach(this, procedure);
	}
	
	// Traversable.
	
	/**
	 * Takes n pairs of elements provided by this iterator.
	 * <p>
	 * The pairs of elements are taken according their iteration order.
	 * <p>
	 * The built iterator feeds lazyly from the given iterator.
	 * 
	 * @param n Number of pairs of elements to take.
	 * @return An iterator providing the taken pairs of elements.
	 * @see IteratorUtils#take2(Iterator, int)
	 * @since 2.0
	 */
	@Override
	default PairIterator<E1, E2> take(final int n) {
		return IteratorUtils.take2(this, n);
	}
	
	/**
	 * Drops n pairs of elements provided by this iterator.
	 * <p>
	 * The pairs of elements are dropped according their iteration order.
	 * <p>
	 * The built iterator feeds lazyly from the given iterator.
	 * 
	 * @param n Number of pairs of elements to drop.
	 * @return An iterator providing the remaining pairs of elements.
	 * @see IteratorUtils#drop2(Iterator, int)
	 * @since 2.0
	 */
	@Override
	default PairIterator<E1, E2> drop(final int n) {
		return IteratorUtils.drop2(this, n);
	}
	
	/**
	 * Filters the pairs of elements provided by this iterator using the given filter.
	 * <p>
	 * The built iterator feeds lazyly from this iterator.
	 * 
	 * @return An iterator providing the filtered pairs of elements.
	 * @see IteratorUtils#filter(Iterator, Predicate2)
	 * @since 2.0
	 */
	@Override
	default PairIterator<E1, E2> filter(final Predicate<? super Tuple2<E1, E2>> filter) {
		return IteratorUtils.filter(this, PredicateUtils.curry2(filter));
	}
}
