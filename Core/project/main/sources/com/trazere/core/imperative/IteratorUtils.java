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

import com.trazere.core.collection.CollectionAccumulators;
import com.trazere.core.collection.CollectionFactory;
import com.trazere.core.collection.MapAccumulators;
import com.trazere.core.collection.Multimap;
import com.trazere.core.collection.MultimapAccumulators;
import com.trazere.core.design.Decorator;
import com.trazere.core.functional.Function;
import com.trazere.core.functional.Function2;
import com.trazere.core.functional.Function3;
import com.trazere.core.functional.FunctionAccumulators;
import com.trazere.core.functional.FunctionUtils;
import com.trazere.core.functional.Predicate;
import com.trazere.core.functional.Predicate2;
import com.trazere.core.lang.ComparableAccumulators;
import com.trazere.core.lang.IterableFunctions;
import com.trazere.core.util.ComparatorAccumulators;
import com.trazere.core.util.Maybe;
import com.trazere.core.util.Tuple2;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * The {@link IteratorUtils} class provides various utilities regarding {@link Iterator iterators}.
 * 
 * @see Iterator
 * @since 2.0
 */
public class IteratorUtils {
	/**
	 * Gets the next element provided by the given iterator.
	 * <p>
	 * This method supports exhausted iterators.
	 * 
	 * @param <E> Type of the elements.
	 * @param iterator Iterator to read.
	 * @return The next element, or nothing when the iterator is exhausted.
	 * @since 2.0
	 */
	public static <E> Maybe<E> optionalNext(final Iterator<? extends E> iterator) {
		return iterator.hasNext() ? Maybe.some(iterator.next()) : Maybe.none();
	}
	
	/**
	 * Drains the next n elements provided by the given iterator.
	 * 
	 * @param n Number of elements to drain.
	 * @param iterator Iterator to drain.
	 * @since 2.0
	 */
	public static void drain(final Iterator<?> iterator, final int n) {
		final IntCounter counter = new IntCounter();
		while (iterator.hasNext() && counter.inc() <= n) {
			iterator.next();
		}
	}
	
	/**
	 * Drains the next n elements provided by the given iterator and populates the given accumulator with them.
	 * 
	 * @param <E> Type of the elements.
	 * @param <A> Type of the accumulator to populate.
	 * @param n Number of elements to drain.
	 * @param iterator Iterator to drain.
	 * @param results Accumulator to populate with the drained elements.
	 * @return The given result accumulator.
	 * @since 2.0
	 */
	public static <E, A extends Accumulator<? super E, ?>> A drain(final Iterator<? extends E> iterator, final int n, final A results) {
		final IntCounter counter = new IntCounter();
		while (iterator.hasNext() && counter.inc() <= n) {
			results.add(iterator.next());
		}
		return results;
	}
	
	/**
	 * Drains the next n elements provided by the given iterator and adds them to the given collection.
	 * 
	 * @param <E> Type of the elements.
	 * @param <C> Type of the collection to populate.
	 * @param n Number of elements to drain.
	 * @param iterator Iterator to drain.
	 * @param results Collection to populate with the drained elements.
	 * @return The given result collection.
	 * @since 2.0
	 */
	public static <E, C extends Collection<? super E>> C drain(final Iterator<? extends E> iterator, final int n, final C results) {
		return drain(iterator, n, CollectionAccumulators.add(results)).get();
	}
	
	// TODO: drain(Iterator, int, CollectionFactory)
	
	/**
	 * Drains the next n pairs of elements provided by the given iterator and populates the given accumulator with them.
	 * 
	 * @param <E1> Type of the first element of the pairs.
	 * @param <E2> Type of the second element of the pairs.
	 * @param <A> Type of the accumulator to populate.
	 * @param n Number of pairs of elements to drain.
	 * @param iterator Iterator to drain.
	 * @param results Accumulator to populate with the drained pairs of elements.
	 * @return The given result accumulator.
	 * @since 2.0
	 */
	public static <E1, E2, A extends Accumulator2<? super E1, ? super E2, ?>> A drain(final Iterator<? extends Tuple2<? extends E1, ? extends E2>> iterator, final int n, final A results) {
		final IntCounter counter = new IntCounter();
		while (iterator.hasNext() && counter.inc() <= n) {
			results.add(iterator.next());
		}
		return results;
	}
	
	/**
	 * Drains the next n pairs of elements provided by the given iterator and puts the corresponding bindings into the given map.
	 * 
	 * @param <E1> Type of the first element of the pairs.
	 * @param <E2> Type of the second element of the pairs.
	 * @param <M> Type of the map to populate.
	 * @param n Number of elements to drain.
	 * @param iterator Iterator to drain.
	 * @param results Collection to populate with the drained elements.
	 * @return The given result collection.
	 * @since 2.0
	 */
	public static <E1, E2, M extends Map<? super E1, ? super E2>> M drain(final Iterator<? extends Tuple2<? extends E1, ? extends E2>> iterator, final int n, final M results) {
		return drain(iterator, n, MapAccumulators.put(results)).get();
	}
	
	// TODO: drain(Iterator, int, MapFactory)
	
	/**
	 * Drains the next n pairs of elements provided by the given iterator and puts the corresponding bindings into the given multimap.
	 * 
	 * @param <E1> Type of the first element of the pairs.
	 * @param <E2> Type of the second element of the pairs.
	 * @param <M> Type of the multimap to populate.
	 * @param n Number of elements to drain.
	 * @param iterator Iterator to drain.
	 * @param results Collection to populate with the drained elements.
	 * @return The given result collection.
	 * @since 2.0
	 */
	public static <E1, E2, M extends Multimap<? super E1, ? super E2, ?>> M drain(final Iterator<? extends Tuple2<? extends E1, ? extends E2>> iterator, final int n, final M results) {
		return drain(iterator, n, MultimapAccumulators.put(results)).get();
	}
	
	// TODO: drain(Iterator, int, MultimapFactory)
	
	// TODO: rename to drainAll
	/**
	 * Drains all elements provided by the the given iterator.
	 * 
	 * @param iterator Iterator to drain.
	 * @since 2.0
	 */
	public static void drain(final Iterator<?> iterator) {
		while (iterator.hasNext()) {
			iterator.next();
		}
	}
	
	// TODO: rename to drainAll
	/**
	 * Drains all elements provided by the the given iterator and populates the given accumulator with them.
	 * 
	 * @param <E> Type of the elements.
	 * @param <A> Type of the accumulator to populate.
	 * @param iterator Iterator to drain.
	 * @param results Accumulator to populate with the drained elements.
	 * @return The given result accumulator.
	 * @since 2.0
	 */
	public static <E, A extends Accumulator<? super E, ?>> A drain(final Iterator<? extends E> iterator, final A results) {
		while (iterator.hasNext()) {
			results.add(iterator.next());
		}
		return results;
	}
	
	// TODO: rename to drainAll
	/**
	 * Drains all elements provided by the the given iterator and adds them to the given collection.
	 * 
	 * @param <E> Type of the elements.
	 * @param <C> Type of the collection to populate.
	 * @param iterator Iterator to drain.
	 * @param results Collection to populate with the drained elements.
	 * @return The given result collection.
	 * @since 2.0
	 */
	public static <E, C extends Collection<? super E>> C drain(final Iterator<? extends E> iterator, final C results) {
		return drain(iterator, CollectionAccumulators.add(results)).get();
	}
	
	// TODO: drain(Iterator, CollectionFactory)
	
	// TODO: rename to drainAll
	/**
	 * Drains all pairs of elements provided by the the given iterator and populates the given accumulator with them.
	 * 
	 * @param <E1> Type of the first element of the pairs.
	 * @param <E2> Type of the second element of the pairs.
	 * @param <A> Type of the accumulator to populate.
	 * @param iterator Iterator to drain.
	 * @param results Accumulator to populate with the drained pairs of elements.
	 * @return The given result accumulator.
	 * @since 2.0
	 */
	public static <E1, E2, A extends Accumulator2<? super E1, ? super E2, ?>> A drain(final Iterator<? extends Tuple2<? extends E1, ? extends E2>> iterator, final A results) {
		while (iterator.hasNext()) {
			results.add(iterator.next());
		}
		return results;
	}
	
	// TODO: rename to drainAll
	/**
	 * Drains all pairs of elements provided by the the given iterator and puts the corresponding bindings into the given map.
	 * 
	 * @param <E1> Type of the first element of the pairs.
	 * @param <E2> Type of the second element of the pairs.
	 * @param <M> Type of the map to populate.
	 * @param iterator Iterator to drain.
	 * @param results Map to populate with the drained pairs of elements.
	 * @return The given result collection.
	 * @since 2.0
	 */
	public static <E1, E2, M extends Map<? super E1, ? super E2>> M drain(final Iterator<? extends Tuple2<? extends E1, ? extends E2>> iterator, final M results) {
		return drain(iterator, MapAccumulators.put(results)).get();
	}
	
	// TODO: drain(Iterator, MapFactory)
	
	// TODO: rename to drainAll
	/**
	 * Drains all pairs of elements provided by the the given iterator and puts the corresponding bindings into the given multimap.
	 * 
	 * @param <E1> Type of the first element of the pairs.
	 * @param <E2> Type of the second element of the pairs.
	 * @param <M> Type of the multimap to populate.
	 * @param iterator Iterator to drain.
	 * @param results Multimap to populate with the drained pairs of elements.
	 * @return The given result collection.
	 * @since 2.0
	 */
	public static <E1, E2, M extends Multimap<? super E1, ? super E2, ?>> M drain(final Iterator<? extends Tuple2<? extends E1, ? extends E2>> iterator, final M results) {
		return drain(iterator, MultimapAccumulators.put(results)).get();
	}
	
	// TODO: drain(Iterator, MultimapFactory)
	
	/**
	 * Executes the given procedure with each element provided by the given iterator.
	 * 
	 * @param <E> Type of the elements.
	 * @param iterator Iterator providing the elements.
	 * @param procedure Procedure to execute.
	 * @since 2.0
	 */
	public static <E> void foreach(final Iterator<? extends E> iterator, final Procedure<? super E> procedure) {
		while (iterator.hasNext()) {
			procedure.execute(iterator.next());
		}
	}
	
	/**
	 * Executes the given procedure with each pair of elements provided by the given iterator.
	 * 
	 * @param <E1> Type of the first element of the pairs.
	 * @param <E2> Type of the second element of the pairs.
	 * @param iterator Iterator providing the pairs of elements.
	 * @param procedure Procedure to execute.
	 * @since 2.0
	 */
	public static <E1, E2> void foreach(final Iterator<? extends Tuple2<? extends E1, ? extends E2>> iterator, final Procedure2<? super E1, ? super E2> procedure) {
		while (iterator.hasNext()) {
			final Tuple2<? extends E1, ? extends E2> elementPair = iterator.next();
			procedure.execute(elementPair.get1(), elementPair.get2());
		}
	}
	
	/**
	 * Left folds over the elements provided by the given iterator using the given binary operator and initial state.
	 * 
	 * @param <E> Type of the elements.
	 * @param <S> Type of the state.
	 * @param iterator Iterator providing the elements to fold over.
	 * @param operator Binary operator to use.
	 * @param initialState Initial state.
	 * @return The folded state.
	 * @since 2.0
	 */
	public static <E, S> S fold(final Iterator<? extends E> iterator, final Function2<? super S, ? super E, ? extends S> operator, final S initialState) {
		return drain(iterator, FunctionAccumulators.fold(operator, initialState)).get();
	}
	
	/**
	 * Left folds over the pairs of elements provided by the given iterator using the given operator and initial state.
	 * 
	 * @param <E1> Type of the first element of the pairs.
	 * @param <E2> Type of the second element of the pairs.
	 * @param <S> Type of the state.
	 * @param iterator Iterator providing the pairs of elements to fold over.
	 * @param operator Operator to use.
	 * @param initialState Initial state.
	 * @return The folded state.
	 * @since 2.0
	 */
	public static <E1, E2, S> S fold(final Iterator<? extends Tuple2<? extends E1, ? extends E2>> iterator, final Function3<? super S, ? super E1, ? super E2, ? extends S> operator, final S initialState) {
		return drain(iterator, FunctionAccumulators.fold2(operator, initialState)).get();
	}
	
	/**
	 * Gets the first element provided by the given iterator accepted by the given filter.
	 * 
	 * @param <E> Type of the elements.
	 * @param iterator Iterator providing the elements to filter.
	 * @param filter Predicate to use to filter the elements.
	 * @return The first accepted element.
	 * @since 2.0
	 */
	public static <E> Maybe<E> first(final Iterator<? extends E> iterator, final Predicate<? super E> filter) {
		while (iterator.hasNext()) {
			final E element = iterator.next();
			if (filter.evaluate(element)) {
				return Maybe.some(element);
			}
		}
		return Maybe.none();
	}
	
	/**
	 * Gets the first pair of elements provided by the given iterator accepted by the given filter.
	 * 
	 * @param <E1> Type of the first element of the pairs.
	 * @param <E2> Type of the second element of the pairs.
	 * @param iterator Iterator providing the pairs of elements to filter.
	 * @param filter Predicate to use to filter the pairs of elements.
	 * @return The first accepted pair of elements.
	 * @since 2.0
	 */
	public static <E1, E2> Maybe<Tuple2<E1, E2>> first(final Iterator<? extends Tuple2<? extends E1, ? extends E2>> iterator, final Predicate2<? super E1, ? super E2> filter) {
		while (iterator.hasNext()) {
			final Tuple2<? extends E1, ? extends E2> elementPair = iterator.next();
			final E1 element1 = elementPair.get1();
			final E2 element2 = elementPair.get2();
			if (filter.evaluate(element1, element2)) {
				return Maybe.some(new Tuple2<>(element1, element2));
			}
		}
		return Maybe.none();
	}
	
	/**
	 * Gets the first element extracted from the elements provided by the given iterator using the given extractor.
	 * 
	 * @param <E> Type of the elements.
	 * @param <EE> Type of the extracted elements.
	 * @param iterator Iterator providing the elements to extract from.
	 * @param extractor Function to use to extract the elements.
	 * @return The first extracted element.
	 * @since 2.0
	 */
	public static <E, EE> Maybe<EE> extractFirst(final Iterator<? extends E> iterator, final Function<? super E, ? extends Maybe<? extends EE>> extractor) {
		while (iterator.hasNext()) {
			final Maybe<? extends EE> extractedElement = extractor.evaluate(iterator.next());
			if (extractedElement.isSome()) {
				return Maybe.some(extractedElement.asSome().getValue());
			}
		}
		return Maybe.none();
	}
	
	/**
	 * Gets the first element extracted from the pairs of elements provided by the given iterator using the given extractor.
	 * 
	 * @param <E1> Type of the first element of the pairs.
	 * @param <E2> Type of the second element of the pairs.
	 * @param <EE> Type of the extracted elements.
	 * @param iterator Iterator providing the elements to extract from.
	 * @param extractor Function to use to extract the elements.
	 * @return The first extracted element.
	 * @since 2.0
	 */
	public static <E1, E2, EE> Maybe<EE> extractFirst(final Iterator<? extends Tuple2<? extends E1, ? extends E2>> iterator, final Function2<? super E1, ? super E2, ? extends Maybe<? extends EE>> extractor) {
		while (iterator.hasNext()) {
			final Tuple2<? extends E1, ? extends E2> elementPair = iterator.next();
			final Maybe<? extends EE> extractedElement = extractor.evaluate(elementPair.get1(), elementPair.get2());
			if (extractedElement.isSome()) {
				return Maybe.some(extractedElement.asSome().getValue());
			}
		}
		return Maybe.none();
	}
	
	/**
	 * Tests whether any element provided by the given iterator is accepted by the given filter.
	 * 
	 * @param <E> Type of the elements.
	 * @param iterator Iterator providing the elements to test.
	 * @param filter Predicate to use to filter the elements.
	 * @return <code>true</code> when some element is accepted, <code>false</code> when all elements are rejected.
	 * @since 2.0
	 */
	public static <E> boolean isAny(final Iterator<? extends E> iterator, final Predicate<? super E> filter) {
		while (iterator.hasNext()) {
			if (filter.evaluate(iterator.next())) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Tests whether any pair of elements provided by the given iterator is accepted by the given filter.
	 * 
	 * @param <E1> Type of the first element of the pairs.
	 * @param <E2> Type of the second element of the pairs.
	 * @param iterator Iterator providing the pairs of elements to test.
	 * @param filter Predicate to use to filter the pairs of elements.
	 * @return <code>true</code> when some pair of elements is accepted, <code>false</code> when all pairs of elements are rejected.
	 * @since 2.0
	 */
	public static <E1, E2> boolean isAny(final Iterator<? extends Tuple2<? extends E1, ? extends E2>> iterator, final Predicate2<? super E1, ? super E2> filter) {
		while (iterator.hasNext()) {
			final Tuple2<? extends E1, ? extends E2> elementPair = iterator.next();
			if (filter.evaluate(elementPair.get1(), elementPair.get2())) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Tests whether all elements provided by the given iterator are accepted by the given filter.
	 * 
	 * @param <E> Type of the elements.
	 * @param iterator Iterator providing the elements to test.
	 * @param filter Predicate to use to filter the elements.
	 * @return <code>true</code> when all elements are accepted, <code>false</code> when some element is rejected.
	 * @since 2.0
	 */
	public static <E> boolean areAll(final Iterator<? extends E> iterator, final Predicate<? super E> filter) {
		while (iterator.hasNext()) {
			if (!filter.evaluate(iterator.next())) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Tests whether all pairs of elements provided by the given iterator are accepted by the given filter.
	 * 
	 * @param <E1> Type of the first element of the pairs.
	 * @param <E2> Type of the second element of the pairs.
	 * @param iterator Iterator providing the pairs of elements to test.
	 * @param filter Predicate to use to filter the pairs of elements.
	 * @return <code>true</code> when all pairs of elements are accepted, <code>false</code> when some pair of elements is rejected.
	 * @since 2.0
	 */
	public static <E1, E2> boolean areAll(final Iterator<? extends Tuple2<? extends E1, ? extends E2>> iterator, final Predicate2<? super E1, ? super E2> filter) {
		while (iterator.hasNext()) {
			final Tuple2<? extends E1, ? extends E2> elementPair = iterator.next();
			if (!filter.evaluate(elementPair.get1(), elementPair.get2())) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Counts the elements provided by the given iterator accepted by the given filter.
	 * 
	 * @param <E> Type of the elements.
	 * @param iterator Iterator providing the elements to count.
	 * @param filter Predicate to use to filter the elements.
	 * @return The number of accepted elements.
	 * @since 2.0
	 */
	public static <E> int count(final Iterator<? extends E> iterator, final Predicate<? super E> filter) {
		final IntCounter counter = new IntCounter();
		while (iterator.hasNext()) {
			if (filter.evaluate(iterator.next())) {
				counter.inc();
			}
		}
		return counter.get();
	}
	
	/**
	 * Counts the pairs of elements provided by the given iterator accepted by the given filter.
	 * 
	 * @param <E1> Type of the first element of the pairs.
	 * @param <E2> Type of the second element of the pairs.
	 * @param iterator Iterator providing the pairs of elements to count.
	 * @param filter Predicate to use to filter the pairs of elements.
	 * @return The number of accepted pairs of elements.
	 * @since 2.0
	 */
	public static <E1, E2> int count(final Iterator<? extends Tuple2<? extends E1, ? extends E2>> iterator, final Predicate2<? super E1, ? super E2> filter) {
		final IntCounter counter = new IntCounter();
		while (iterator.hasNext()) {
			final Tuple2<? extends E1, ? extends E2> elementPair = iterator.next();
			if (filter.evaluate(elementPair.get1(), elementPair.get2())) {
				counter.inc();
			}
		}
		return counter.get();
	}
	
	/**
	 * Gets the least element provided by the given iterator according to their natural order.
	 *
	 * @param <E> Type of the elements.
	 * @param iterator Iterator providing the elements to compare.
	 * @return The least element.
	 * @since 2.0
	 */
	public static <E extends Comparable<E>> Maybe<E> least(final Iterator<? extends E> iterator) {
		return drain(iterator, ComparableAccumulators.<E>least()).get();
	}
	
	/**
	 * Gets the least element provided by the given iterator according to the given comparator.
	 *
	 * @param <E> Type of the elements.
	 * @param iterator Iterator providing the elements to compare.
	 * @param comparator Comparator to use.
	 * @return The least element.
	 * @since 2.0
	 */
	public static <E> Maybe<E> least(final Iterator<? extends E> iterator, final Comparator<? super E> comparator) {
		return drain(iterator, ComparatorAccumulators.<E>least(comparator)).get();
	}
	
	/**
	 * Gets the greatest element provided by the given iterator according to their natural order.
	 *
	 * @param <E> Type of the elements.
	 * @param iterator Iterator providing the elements to compare.
	 * @return The greatest element.
	 * @since 2.0
	 */
	public static <E extends Comparable<E>> Maybe<E> greatest(final Iterator<? extends E> iterator) {
		return drain(iterator, ComparableAccumulators.<E>greatest()).get();
	}
	
	/**
	 * Gets the greatest element provided by the given iterator according to the given comparator.
	 *
	 * @param <E> Type of the elements.
	 * @param iterator Iterator providing the elements to compare.
	 * @param comparator Comparator to use.
	 * @return The greatest element.
	 * @since 2.0
	 */
	public static <E> Maybe<E> greatest(final Iterator<? extends E> iterator, final Comparator<? super E> comparator) {
		return drain(iterator, ComparatorAccumulators.<E>greatest(comparator)).get();
	}
	
	/**
	 * Appends the given iterators together.
	 * <p>
	 * The built iterator feeds from the given iterators.
	 * 
	 * @param <E> Type of the elements.
	 * @param iterator1 First iterator providing the elements to append.
	 * @param iterator2 Second iterator providing the elements to append.
	 * @return An iterator providing the appended elements.
	 * @since 2.0
	 */
	public static <E> ExIterator<E> append(final Iterator<? extends E> iterator1, final Iterator<? extends E> iterator2) {
		assert null != iterator1;
		assert null != iterator2;
		
		return new ExIterator<E>() {
			private boolean _appended = false;
			
			@Override
			public boolean hasNext() {
				return iterator1.hasNext() || iterator2.hasNext();
			}
			
			@Override
			public E next() {
				if (iterator1.hasNext()) {
					return iterator1.next();
				} else {
					_appended = true;
					return iterator2.next();
				}
			}
			
			@Override
			public void remove() {
				if (_appended) {
					iterator2.remove();
				} else {
					iterator1.remove();
				}
			}
		};
	}
	
	/**
	 * Flattens the elements provided by the iterators provided by the given iterator.
	 * <p>
	 * The built iterator feeds from the given iterators.
	 *
	 * @param <E> Type of the elements.
	 * @param iterator Iterator providing the iterators providing the elements to flatten.
	 * @return An iterator providing the flatten elements.
	 * @since 2.0
	 */
	public static <E> ExIterator<E> flatten(final Iterator<? extends Iterator<? extends E>> iterator) {
		assert null != iterator;
		
		return new ExIterator<E>() {
			private Iterator<? extends E> _iterator = Iterators.empty();
			
			@Override
			public boolean hasNext() {
				lookAhead();
				return _iterator.hasNext();
			}
			
			@Override
			public E next() {
				lookAhead();
				return _iterator.next();
			}
			
			private void lookAhead() {
				while (!_iterator.hasNext() && iterator.hasNext()) {
					_iterator = iterator.next();
				}
			}
		};
	}
	
	/**
	 * Takes the n first elements provided by the given iterator.
	 * <p>
	 * The built iterator feeds from the given iterator.
	 * 
	 * @param <E> Type of the elements.
	 * @param iterator Iterator providing the elements to take.
	 * @param n Number of elements to take.
	 * @return An iterator providing the taken elements.
	 * @since 2.0
	 */
	public static <E> ExIterator<E> take(final Iterator<? extends E> iterator, final int n) {
		assert null != iterator;
		
		return new ExIterator<E>() {
			private int _i = 0;
			
			@Override
			public boolean hasNext() {
				return iterator.hasNext() && _i < n;
			}
			
			@Override
			public E next() {
				if (_i < n) {
					_i += 1;
					return iterator.next();
				} else {
					throw new NoSuchElementException();
				}
			}
			
			@Override
			public void remove() {
				iterator.remove();
			}
		};
	}
	
	/**
	 * Drops the n first elements provided by the given iterator.
	 * <p>
	 * The built iterator feeds from the given iterator.
	 * 
	 * @param <E> Type of the elements.
	 * @param iterator Iterator providing the elements to drop.
	 * @param n Number of elements to drop.
	 * @return An iterator providing the remaining elements.
	 * @since 2.0
	 */
	public static <E> ExIterator<E> drop(final Iterator<? extends E> iterator, final int n) {
		assert null != iterator;
		
		return new ExIterator<E>() {
			private int _droppedN = 0;
			
			@Override
			public boolean hasNext() {
				drop();
				return iterator.hasNext();
			}
			
			@Override
			public E next() {
				drop();
				return iterator.next();
			}
			
			@Override
			public void remove() {
				drop();
				iterator.remove();
			}
			
			private void drop() {
				while (_droppedN < n && iterator.hasNext()) {
					iterator.next();
					_droppedN += 1;
				}
			}
		};
	}
	
	/**
	 * Groups the elements provided by the given iterator into batches of the given size.
	 *
	 * @param <E> Type of the elements.
	 * @param <B> Type of the batch collections.
	 * @param iterator Iterator providing the elements to group.
	 * @param n Number of elements of each batch.
	 * @param batchFactory Factory of the batch collections.
	 * @return An iterator providing the groups of elements.
	 * @since 2.0
	 */
	public static <E, B extends Collection<? super E>> ExIterator<B> group(final Iterator<? extends E> iterator, final int n, final CollectionFactory<? super E, B> batchFactory) {
		assert null != iterator;
		
		return new ExIterator<B>() {
			@Override
			public boolean hasNext() {
				return iterator.hasNext();
			}
			
			@Override
			public B next() {
				return IteratorUtils.drain(iterator, n, batchFactory.build(n));
			}
		};
	}
	
	/**
	 * Filters the elements provided by the given iterator using the given filter.
	 * <p>
	 * The built iterator feeds from the given iterator.
	 *
	 * @param <E> Type of the elements.
	 * @param iterator Iterator providing the elements to filter.
	 * @param filter Predicate to use to filter the elements.
	 * @return An iterator providing the filtered elements.
	 * @since 2.0
	 */
	public static <E> ExIterator<E> filter(final Iterator<? extends E> iterator, final Predicate<? super E> filter) {
		assert null != iterator;
		assert null != filter;
		
		return new LookAheadIterator<E>() {
			@Override
			protected Maybe<? extends E> pull() {
				return IteratorUtils.first(iterator, filter);
			}
		};
	}
	
	/**
	 * Filters the pairs of elements provided by the given iterator using the given filter.
	 * <p>
	 * The built iterator feeds from the given iterator.
	 *
	 * @param <E1> Type of the first element of the pairs.
	 * @param <E2> Type of the second element of the pairs.
	 * @param iterator Iterator providing the pairs of elements to filter.
	 * @param filter Predicate to use to filter the pairs of elements.
	 * @return An iterator providing the filtered pairs of elements.
	 * @since 2.0
	 */
	public static <E1, E2> PairIterator<E1, E2> filter(final Iterator<? extends Tuple2<? extends E1, ? extends E2>> iterator, final Predicate2<? super E1, ? super E2> filter) {
		assert null != iterator;
		assert null != filter;
		
		return new LookAheadPairIterator<E1, E2>() {
			@Override
			protected Maybe<Tuple2<E1, E2>> pull() {
				return IteratorUtils.first(iterator, filter);
			}
		};
	}
	
	/**
	 * Transforms the elements provided by the given iterator using the given function.
	 * <p>
	 * The built iterator feeds from the given iterator.
	 *
	 * @param <E> Type of the elements.
	 * @param <TE> Type of the transformed elements.
	 * @param iterator Iterator providing the elements to transform.
	 * @param function Function to use to transform the elements.
	 * @return An iterator providing the transformed elements.
	 * @since 2.0
	 */
	public static <E, TE> ExIterator<TE> map(final Iterator<? extends E> iterator, final Function<? super E, ? extends TE> function) {
		assert null != iterator;
		assert null != function;
		
		return new ExIterator<TE>() {
			@Override
			public boolean hasNext() {
				return iterator.hasNext();
			}
			
			@Override
			public TE next() {
				return function.evaluate(iterator.next());
			}
			
			@Override
			public void remove() {
				iterator.remove();
			}
		};
	}
	
	/**
	 * Transforms the pairs of elements provided by the given iterator using the given function.
	 * <p>
	 * The built iterator feeds from the given iterator.
	 *
	 * @param <E1> Type of the first element of the pairs.
	 * @param <E2> Type of the second element of the pairs.
	 * @param <TE> Type of the transformed elements.
	 * @param iterator Iterator providing the pairs of elements to transform.
	 * @param function Function to use to transform the pairs of elements.
	 * @return An iterator providing the transformed elements.
	 * @since 2.0
	 */
	public static <E1, E2, TE> ExIterator<TE> map(final Iterator<? extends Tuple2<? extends E1, ? extends E2>> iterator, final Function2<? super E1, ? super E2, ? extends TE> function) {
		assert null != iterator;
		assert null != function;
		
		return new ExIterator<TE>() {
			@Override
			public boolean hasNext() {
				return iterator.hasNext();
			}
			
			@Override
			public TE next() {
				final Tuple2<? extends E1, ? extends E2> next = iterator.next();
				return function.evaluate(next.get1(), next.get2());
			}
			
			@Override
			public void remove() {
				iterator.remove();
			}
		};
	}
	
	/**
	 * Transforms and flattens the elements provided by the given iterator using the given function.
	 * <p>
	 * The built iterator feeds from the given iterator.
	 * 
	 * @param <E> Type of the elements.
	 * @param <TE> Type of the transformed elements.
	 * @param iterator Iterator providing the elements to transform.
	 * @param function Function to use to transform the elements.
	 * @return An iterator providing the flatten, transformed elements.
	 * @since 2.0
	 */
	public static <E, TE> ExIterator<TE> flatMap(final Iterator<? extends E> iterator, final Function<? super E, ? extends Iterator<? extends TE>> function) {
		return flatten(map(iterator, function));
	}
	
	/**
	 * Transforms and flattens the pairs of elements provided by the given iterator using the given function.
	 * <p>
	 * The built iterator feeds from the given iterator.
	 * 
	 * @param <E1> Type of the first element of the pairs.
	 * @param <E2> Type of the second element of the pairs.
	 * @param <TE> Type of the transformed elements.
	 * @param iterator Iterator providing the pairs of elements to transform.
	 * @param function Function to use to transform the pairs of elements.
	 * @return An iterator providing the flatten, transformed elements.
	 * @since 2.0
	 */
	public static <E1, E2, TE> ExIterator<TE> flatMap(final Iterator<? extends Tuple2<? extends E1, ? extends E2>> iterator, final Function2<? super E1, ? super E2, ? extends Iterator<? extends TE>> function) {
		return flatten(map(iterator, function));
	}
	
	/**
	 * Extracts the elements provided by the given iterator using the given extractor.
	 * <p>
	 * The built iterator feeds from the given iterator.
	 *
	 * @param <E> Type of the elements.
	 * @param <EE> Type of the extracted elements.
	 * @param iterator Iterator providing the elements to extract from.
	 * @param extractor Function to use to extract the elements.
	 * @return An iterator providing the extracted elements.
	 * @since 2.0
	 */
	public static <E, EE> ExIterator<EE> extract(final Iterator<? extends E> iterator, final Function<? super E, ? extends Maybe<? extends EE>> extractor) {
		assert null != iterator;
		assert null != extractor;
		
		return new LookAheadIterator<EE>() {
			@Override
			protected Maybe<? extends EE> pull() {
				return IteratorUtils.extractFirst(iterator, extractor);
			}
		};
	}
	
	/**
	 * Extracts the pairs of elements provided by the given iterator using the given extractor.
	 * <p>
	 * The built iterator feeds from the given iterator.
	 *
	 * @param <E1> Type of the first element of the pairs.
	 * @param <E2> Type of the second element of the pairs.
	 * @param <EE> Type of the extracted elements.
	 * @param iterator Iterator providing the pairs of elements to extract from.
	 * @param extractor Function to use to extract the pairs of elements.
	 * @return An iterator providing the extracted elements.
	 * @since 2.0
	 */
	public static <E1, E2, EE> ExIterator<EE> extract(final Iterator<? extends Tuple2<? extends E1, ? extends E2>> iterator, final Function2<? super E1, ? super E2, ? extends Maybe<? extends EE>> extractor) {
		assert null != iterator;
		assert null != extractor;
		
		return new LookAheadIterator<EE>() {
			@Override
			protected Maybe<? extends EE> pull() {
				return IteratorUtils.extractFirst(iterator, extractor);
			}
		};
	}
	
	/**
	 * Extracts and flattens the elements provided by the given iterator using the given extractor.
	 * <p>
	 * The built iterator feeds from the given iterator.
	 *
	 * @param <E> Type of the elements.
	 * @param <TE> Type of the extracted elements.
	 * @param iterator Iterator providing the elements to extract from.
	 * @param extractor Function to use to extract the elements.
	 * @return An iterator providing the extracted elements.
	 * @since 2.0
	 */
	public static <E, TE> ExIterator<TE> extractAll(final Iterator<? extends E> iterator, final Function<? super E, ? extends Iterable<? extends TE>> extractor) {
		return flatMap(iterator, FunctionUtils.map(extractor, IterableFunctions.iterator()));
	}
	
	/**
	 * Extracts and flattens the pairs of elements provided by the given iterator using the given extractor.
	 * <p>
	 * The built iterator feeds from the given iterator.
	 *
	 * @param <E1> Type of the first element of the pairs.
	 * @param <E2> Type of the second element of the pairs.
	 * @param <EE> Type of the extracted elements.
	 * @param iterator Iterator providing the pairs of elements to extract from.
	 * @param extractor Function to use to extract the pairs of elements.
	 * @return An iterator providing the extracted elements.
	 * @since 2.0
	 */
	public static <E1, E2, EE> ExIterator<EE> extractAll(final Iterator<? extends Tuple2<? extends E1, ? extends E2>> iterator, final Function2<? super E1, ? super E2, ? extends Iterable<? extends EE>> extractor) {
		return flatMap(iterator, FunctionUtils.map2(extractor, IterableFunctions.iterator()));
	}
	
	/**
	 * Builds an unmodifiable view of the given iterator.
	 * 
	 * @param <E> Type of the elements.
	 * @param iterator Iterator to wrap.
	 * @return An unmodifiable view of the given iterator, or the given iterator when is it already unmodifiable.
	 * @since 2.0
	 */
	public static <E> ExIterator<E> unmodifiable(final Iterator<E> iterator) {
		assert null != iterator;
		
		return iterator instanceof UnmodifiableIterator ? (UnmodifiableIterator<E>) iterator : new UnmodifiableIterator<>(iterator);
	}
	
	private static class UnmodifiableIterator<E>
	extends Decorator<Iterator<E>>
	implements ExIterator<E> {
		public UnmodifiableIterator(final Iterator<E> decorated) {
			super(decorated);
		}
		
		// Iterator.
		
		@Override
		public boolean hasNext() {
			return _decorated.hasNext();
		}
		
		@Override
		public E next() {
			return _decorated.next();
		}
		
		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
		
		// Object.
		
		@Override
		public int hashCode() {
			return _decorated.hashCode();
		}
		
		@Override
		public boolean equals(final Object o) {
			return _decorated.equals(o);
		}
	}
	
	/**
	 * Builds an unmodifiable view of the given iterator of pairs of elements.
	 * 
	 * @param <E1> Type of the first element of the pairs.
	 * @param <E2> Type of the second element of the pairs.
	 * @param iterator Iterator to wrap.
	 * @return An unmodifiable view of the given iterator, or the given iterator when is it already unmodifiable.
	 * @since 2.0
	 */
	public static <E1, E2> PairIterator<E1, E2> unmodifiable(final PairIterator<E1, E2> iterator) {
		assert null != iterator;
		
		return iterator instanceof UnmodifiablePairIterator ? iterator : new UnmodifiablePairIterator<>(iterator);
	}
	
	private static class UnmodifiablePairIterator<E1, E2>
	extends UnmodifiableIterator<Tuple2<E1, E2>>
	implements PairIterator<E1, E2> {
		public UnmodifiablePairIterator(final Iterator<Tuple2<E1, E2>> decorated) {
			super(decorated);
		}
	}
	
	/**
	 * Composes pairs with the elements provided by the given iterators.
	 * <p>
	 * The pairs are composed of an element provided by each iterator in order. The extra values of the longest iterator are dropped when the given iterators
	 * don't provide the same number of elements.
	 * 
	 * @param <E1> Type of the first elements.
	 * @param <E2> Type of the second elements.
	 * @param iterator1 Iterator providing the first elements of the pairs.
	 * @param iterator2 Iterator providing the second elements of the pairs.
	 * @return An iterator providing the pairs of elements.
	 * @since 2.0
	 */
	public static <E1, E2> PairIterator<E1, E2> zip(final Iterator<? extends E1> iterator1, final Iterator<? extends E2> iterator2) {
		assert null != iterator1;
		assert null != iterator2;
		
		return new PairIterator<E1, E2>() {
			@Override
			public boolean hasNext() {
				return iterator1.hasNext() && iterator2.hasNext();
			}
			
			@Override
			public Tuple2<E1, E2> next() {
				return new Tuple2<>(iterator1.next(), iterator2.next());
			}
		};
	}
	
	private IteratorUtils() {
		// Prevent instantiation.
	}
}
