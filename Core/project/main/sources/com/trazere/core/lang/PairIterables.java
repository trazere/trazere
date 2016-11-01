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

import com.trazere.core.collection.CollectionFactory;
import com.trazere.core.functional.Function;
import com.trazere.core.functional.Function2;
import com.trazere.core.functional.Function3;
import com.trazere.core.functional.Predicate;
import com.trazere.core.functional.Predicate2;
import com.trazere.core.imperative.PairIterator;
import com.trazere.core.imperative.PairIterators;
import com.trazere.core.imperative.Procedure;
import com.trazere.core.util.Maybe;
import com.trazere.core.util.Tuple2;
import java.util.Collection;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;

/**
 * The {@link PairIterables} class provides various factories of {@link PairIterable iterables of pairs of elements}.
 * 
 * @see PairIterable
 * @since 2.0
 */
public class PairIterables {
	/**
	 * Builds an empty iterable.
	 * 
	 * @param <E1> Type of the first element of the pairs.
	 * @param <E2> Type of the second element of the pairs.
	 * @return The built iterable.
	 * @since 2.0
	 */
	@SuppressWarnings("unchecked")
	public static <E1, E2> PairIterable<E1, E2> empty() {
		return (PairIterable<E1, E2>) EMPTY;
	}
	
	private static final PairIterable<?, ?> EMPTY = new PairIterable<Object, Object>() {
		// Iterable.
		
		@Override
		public PairIterator<Object, Object> iterator() {
			return PairIterators.empty();
		}
		
		@Override
		public void forEach(final Consumer<? super Tuple2<Object, Object>> action) {
			// Nothing to do.
		}
		
		@Override
		public Spliterator<Tuple2<Object, Object>> spliterator() {
			return Spliterators.emptySpliterator();
		}
		
		// ExIterable.
		
		@Override
		public Tuple2<Object, Object> any()
		throws NoSuchElementException {
			throw new NoSuchElementException();
		}
		
		@Override
		public Maybe<Tuple2<Object, Object>> optionalAny() {
			return Maybe.none();
		}
		
		@Override
		public <E2> PairIterable<Tuple2<Object, Object>, E2> zip(final Iterable<? extends E2> iterable2) {
			return PairIterables.empty();
		}
		
		@Override
		public PairIterable<Object, Object> unmodifiable() {
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
		public PairIterable<Object, Object> filter(final Predicate2<? super Object, ? super Object> filter) {
			return empty();
		}
		
		@Override
		public Maybe<Tuple2<Object, Object>> filterAny(final Predicate2<? super Object, ? super Object> filter) {
			return Maybe.none();
		}
		
		@Override
		public <TE> ExIterable<TE> map(final Function2<? super Object, ? super Object, ? extends TE> function) {
			return Iterables.empty();
		}
		
		@Override
		public <EE> ExIterable<EE> extract(final Function2<? super Object, ? super Object, ? extends Maybe<? extends EE>> extractor) {
			return Iterables.empty();
		}
		
		@Override
		public <EE> Maybe<EE> extractAny(final Function2<? super Object, ? super Object, ? extends Maybe<? extends EE>> extractor) {
			return Maybe.none();
		}
		
		@Override
		public <EE> ExIterable<EE> extractAll(final Function2<? super Object, ? super Object, ? extends Iterable<? extends EE>> extractor) {
			return Iterables.empty();
		}
		
		// Note: flatMap is not defined here because Java does not support higher order type parameters.
		
		@Override
		public void foreach(final com.trazere.core.imperative.Procedure2<? super Object, ? super Object> procedure) {
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
		public PairIterable<Object, Object> take(final int n) {
			return empty();
		}
		
		@Override
		public PairIterable<Object, Object> drop(final int n) {
			return empty();
		}
		
		@Override
		public <B extends Collection<? super Tuple2<Object, Object>>> ExIterable<B> group(final int n, final CollectionFactory<? super Tuple2<Object, Object>, B> batchFactory) {
			return Iterables.empty();
		}
		
		@Override
		public PairIterable<Object, Object> filter(final Predicate<? super Tuple2<Object, Object>> filter) {
			return empty();
		}
		
		@Override
		public Maybe<Tuple2<Object, Object>> filterAny(final Predicate<? super Tuple2<Object, Object>> filter) {
			return Maybe.none();
		}
		
		@Override
		public <TE> ExIterable<TE> map(final Function<? super Tuple2<Object, Object>, ? extends TE> function) {
			return Iterables.empty();
		}
		
		@Override
		public <EE> ExIterable<EE> extract(final Function<? super Tuple2<Object, Object>, ? extends Maybe<? extends EE>> extractor) {
			return Iterables.empty();
		}
		
		@Override
		public <EE> Maybe<EE> extractAny(final Function<? super Tuple2<Object, Object>, ? extends Maybe<? extends EE>> extractor) {
			return Maybe.none();
		}
		
		@Override
		public <EE> ExIterable<EE> extractAll(final Function<? super Tuple2<Object, Object>, ? extends Iterable<? extends EE>> extractor) {
			return Iterables.empty();
		}
		
		// Note: flatMap is not defined here because Java does not support higher order type parameters.
		
		@Override
		public void foreach(final Procedure<? super Tuple2<Object, Object>> procedure) {
			// Nothing to do.
		}
		
		// Object.
		// FIXME
	};
	
	private PairIterables() {
		// Prevent instantiation.
	}
}
