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

import com.trazere.core.collection.CollectionFactory;
import com.trazere.core.collection.Multimap;
import com.trazere.core.functional.Function;
import com.trazere.core.functional.Function2;
import com.trazere.core.functional.Function3;
import com.trazere.core.functional.Predicate;
import com.trazere.core.functional.Predicate2;
import com.trazere.core.util.Maybe;
import com.trazere.core.util.Tuple2;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

/**
 * The {@link PairIterators} class provides various factories of {@link PairIterator iterators of pairs of elements}.
 * 
 * @see PairIterator
 * @since 2.0
 */
public class PairIterators {
	/**
	 * Builds an empty iterator.
	 * 
	 * @param <E1> Type of the first element of the pairs.
	 * @param <E2> Type of the second element of the pairs.
	 * @return The built iterator.
	 * @since 2.0
	 */
	@SuppressWarnings("unchecked")
	public static <E1, E2> PairIterator<E1, E2> empty() {
		return (PairIterator<E1, E2>) EMPTY;
	}
	
	private static final PairIterator<?, ?> EMPTY = new PairIterator<Object, Object>() {
		// PairIterator.
		
		@Override
		public <A extends Accumulator2<? super Object, ? super Object, ?>> A drain(final A results) {
			return results;
		}
		
		@Override
		public <M extends Map<? super Object, ? super Object>> M drain(final M results) {
			return results;
		}
		
		@Override
		public <M extends Multimap<? super Object, ? super Object, ?>> M drain(final M results) {
			return results;
		}
		
		// Iterator.
		
		@Override
		public boolean hasNext() {
			return false;
		}
		
		@Override
		public Tuple2<Object, Object> next() {
			throw new NoSuchElementException();
		}
		
		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public void forEachRemaining(final Consumer<? super Tuple2<Object, Object>> action) {
			// Nothing to do.
		}
		
		// ExIterator.
		
		@Override
		public Maybe<Tuple2<Object, Object>> optionalNext() {
			return Maybe.none();
		}
		
		@Override
		public void drain() {
			// Nothing to do.
		}
		
		@Override
		public <A extends Accumulator<? super Tuple2<Object, Object>, ?>> A drain(final A results) {
			return results;
		}
		
		@Override
		public <C extends Collection<? super Tuple2<Object, Object>>> C drain(final C results) {
			return results;
		}
		
		@Override
		public <E2> PairIterator<Tuple2<Object, Object>, E2> zip(final Iterator<? extends E2> iterator2) {
			return PairIterators.empty();
		}
		
		@Override
		public PairIterator<Object, Object> unmodifiable() {
			return empty();
		}
		
		// PairTraversable.
		
		@Override
		public <S> S fold(final Function3<? super S, ? super Object, ? super Object, ? extends S> operator, final S initialState) {
			return initialState;
		}
		
		@Override
		public boolean isAny(final Predicate2<? super Object, ? super Object> filter) {
			return false;
		}
		
		@Override
		public boolean areAll(final Predicate2<? super Object, ? super Object> filter) {
			return true;
		}
		
		@Override
		public int count(final Predicate2<? super Object, ? super Object> filter) {
			return 0;
		}
		
		@Override
		public PairIterator<Object, Object> filter(final Predicate2<? super Object, ? super Object> filter) {
			return empty();
		}
		
		@Override
		public Maybe<Tuple2<Object, Object>> filterAny(final Predicate2<? super Object, ? super Object> filter) {
			return Maybe.none();
		}
		
		@Override
		public <TE> ExIterator<TE> map(final Function2<? super Object, ? super Object, ? extends TE> function) {
			return Iterators.empty();
		}
		
		@Override
		public <EE> ExIterator<EE> extract(final Function2<? super Object, ? super Object, ? extends Maybe<? extends EE>> extractor) {
			return Iterators.empty();
		}
		
		@Override
		public <EE> Maybe<EE> extractAny(final Function2<? super Object, ? super Object, ? extends Maybe<? extends EE>> extractor) {
			return Maybe.none();
		}
		
		@Override
		public <EE> ExIterator<EE> extractAll(final Function2<? super Object, ? super Object, ? extends Iterable<? extends EE>> extractor) {
			return Iterators.empty();
		}
		
		@Override
		public <TE> ExIterator<TE> flatMap(final Function2<? super Object, ? super Object, ? extends java.util.Iterator<? extends TE>> function) {
			return Iterators.empty();
		}
		
		@Override
		public void foreach(final Procedure2<? super Object, ? super Object> procedure) {
			// Nothing to do.
		}
		
		// Traversable.
		
		@Override
		public <S> S fold(final Function2<? super S, ? super Tuple2<Object, Object>, ? extends S> operator, final S initialState) {
			return initialState;
		}
		
		@Override
		public boolean isAny(final Predicate<? super Tuple2<Object, Object>> filter) {
			return false;
		}
		
		@Override
		public boolean areAll(final Predicate<? super Tuple2<Object, Object>> filter) {
			return true;
		}
		
		@Override
		public int count(final Predicate<? super Tuple2<Object, Object>> filter) {
			return 0;
		}
		
		@Override
		public Maybe<Tuple2<Object, Object>> least(final Comparator<? super Tuple2<Object, Object>> comparator) {
			return Maybe.none();
		}
		
		@Override
		public Maybe<Tuple2<Object, Object>> greatest(final Comparator<? super Tuple2<Object, Object>> comparator) {
			return Maybe.none();
		}
		
		@Override
		public PairIterator<Object, Object> take(final int n) {
			return empty();
		}
		
		@Override
		public PairIterator<Object, Object> drop(final int n) {
			return empty();
		}
		
		@Override
		public <B extends Collection<? super Tuple2<Object, Object>>> ExIterator<B> group(final int n, final CollectionFactory<? super Tuple2<Object, Object>, B> batchFactory) {
			return Iterators.empty();
		}
		
		@Override
		public PairIterator<Object, Object> filter(final Predicate<? super Tuple2<Object, Object>> filter) {
			return empty();
		}
		
		@Override
		public Maybe<Tuple2<Object, Object>> filterAny(final Predicate<? super Tuple2<Object, Object>> filter) {
			return Maybe.none();
		}
		
		@Override
		public <TE> ExIterator<TE> map(final Function<? super Tuple2<Object, Object>, ? extends TE> function) {
			return Iterators.empty();
		}
		
		@Override
		public <EE> ExIterator<EE> extract(final Function<? super Tuple2<Object, Object>, ? extends Maybe<? extends EE>> extractor) {
			return Iterators.empty();
		}
		
		@Override
		public <EE> Maybe<EE> extractAny(final Function<? super Tuple2<Object, Object>, ? extends Maybe<? extends EE>> extractor) {
			return Maybe.none();
		}
		
		@Override
		public <EE> ExIterator<EE> extractAll(final Function<? super Tuple2<Object, Object>, ? extends Iterable<? extends EE>> extractor) {
			return Iterators.empty();
		}
		
		@Override
		public <TE> ExIterator<TE> flatMap(final Function<? super Tuple2<Object, Object>, ? extends Iterator<? extends TE>> extractor) {
			return Iterators.empty();
		}
		
		@Override
		public void foreach(final Procedure<? super Tuple2<Object, Object>> procedure) {
			// Nothing to do.
		}
		
		// Object.
		// FIXME
	};
	
	private PairIterators() {
		// Prevent instantiation.
	}
}
