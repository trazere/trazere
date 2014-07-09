/*
 *  Copyright 2006-2013 Julien Dufour
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
import com.trazere.core.functional.Function;
import com.trazere.core.functional.Function2;
import com.trazere.core.functional.FunctionAccumulators;
import com.trazere.core.functional.Functions;
import com.trazere.core.functional.Predicate;
import com.trazere.core.lang.ComparableAccumulators;
import com.trazere.core.lang.IterableFunctions;
import com.trazere.core.util.ComparatorAccumulators;
import com.trazere.core.util.IntCounter;
import com.trazere.core.util.Maybe;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * The {@link IteratorUtils} class provides various helpers regarding {@link Iterator iterators}.
 */
public class IteratorUtils {
	/**
	 * Gets the next element provided by the given iterator.
	 * 
	 * @param <E> Type of the elements.
	 * @param iterator Iterator to consume.
	 * @return The next element.
	 */
	public static <E> Maybe<E> next(final Iterator<? extends E> iterator) {
		return iterator.hasNext() ? Maybe.<E>some(iterator.next()) : Maybe.<E>none();
	}
	
	/**
	 * Drains the next n elements provided by the given iterator.
	 * 
	 * @param n Number of elements to drain.
	 * @param iterator Iterator to drain.
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
	 */
	public static <E, C extends Collection<? super E>> C drain(final Iterator<? extends E> iterator, final int n, final C results) {
		return drain(iterator, n, CollectionAccumulators.add(results)).get();
	}
	
	/**
	 * Drains all elements provided by the the given iterator.
	 * 
	 * @param <E> Type of the elements.
	 * @param iterator Iterator to drain.
	 */
	public static <E> void drainAll(final Iterator<? extends E> iterator) {
		while (iterator.hasNext()) {
			iterator.next();
		}
	}
	
	/**
	 * Drains all elements provided by the the given iterator and populates the given accumulator with them.
	 * 
	 * @param <E> Type of the elements.
	 * @param <A> Type of the accumulator to populate.
	 * @param iterator Iterator to drain.
	 * @param results Accumulator to populate with the drained elements.
	 * @return The given result accumulator.
	 */
	public static <E, A extends Accumulator<? super E, ?>> A drainAll(final Iterator<? extends E> iterator, final A results) {
		while (iterator.hasNext()) {
			results.add(iterator.next());
		}
		return results;
	}
	
	/**
	 * Drains all elements provided by the the given iterator and adds them to the given collection.
	 * 
	 * @param <E> Type of the elements.
	 * @param <C> Type of the collection to populate.
	 * @param iterator Iterator to drain.
	 * @param results Collection to populate with the drained elements.
	 * @return The given result collection.
	 */
	public static <E, C extends Collection<? super E>> C drainAll(final Iterator<? extends E> iterator, final C results) {
		return drainAll(iterator, CollectionAccumulators.add(results)).get();
	}
	
	/**
	 * Executes the given procedure with each element provided by the given iterator.
	 * 
	 * @param <E> Type of the elements.
	 * @param iterator Iterator providing the elements.
	 * @param procedure Procedure to execute.
	 */
	public static <E> void foreach(final Iterator<? extends E> iterator, final Procedure<? super E> procedure) {
		while (iterator.hasNext()) {
			procedure.execute(iterator.next());
		}
	}
	
	/**
	 * Evaluates the given function with each element provided by the given iterator.
	 * 
	 * @param <E> Type of the elements.
	 * @param iterator Iterator providing the elements.
	 * @param function Function to evaluate.
	 */
	public static <E> void foreach(final Iterator<? extends E> iterator, final Function<? super E, ?> function) {
		while (iterator.hasNext()) {
			function.evaluate(iterator.next());
		}
	}
	
	/**
	 * Left folds over the elements provided by the given iterator using the given binary operator and initial value.
	 * 
	 * @param <E> Type of the elements.
	 * @param <S> Type of the state.
	 * @param iterator Iterator providing the elements to fold over.
	 * @param operator Binary operator to use.
	 * @param initialState Initial state.
	 * @return The folded state.
	 */
	public static <E, S> S fold(final Iterator<? extends E> iterator, final Function2<? super S, ? super E, ? extends S> operator, final S initialState) {
		return drainAll(iterator, FunctionAccumulators.fold(operator, initialState)).get();
	}
	
	/**
	 * Gets the first element provided by the given iterator accepted by the given filter.
	 * 
	 * @param <E> Type of the elements.
	 * @param iterator Iterator providing the elements to filter.
	 * @param filter Filter predicate.
	 * @return The first accepted element.
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
	 * Gets the first element extracted from the elements provided by the given iterator by the given extractor.
	 * 
	 * @param <E> Type of the elements.
	 * @param <RE> Type of the extracted elements.
	 * @param iterator Iterator providing the elements to extract from.
	 * @param extractor Extractor function.
	 * @return The first extracted element.
	 */
	public static <E, RE> Maybe<? extends RE> first(final Iterator<? extends E> iterator, final Function<? super E, ? extends Maybe<? extends RE>> extractor) {
		while (iterator.hasNext()) {
			final Maybe<? extends RE> extractedElement = extractor.evaluate(iterator.next());
			if (extractedElement.isSome()) {
				return extractedElement;
			}
		}
		return Maybe.none();
	}
	
	/**
	 * Tests whether any element provided by the given iterator is accepted by the given filter.
	 * 
	 * @param <E> Type of the elements.
	 * @param iterator Iterator providing the elements to test.
	 * @param filter Filter predicate.
	 * @return <code>true</code> when some element is accepted, <code>false</code> when all elements are rejected.
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
	 * Tests whether all elements provided by the given iterator are accepted by the given filter.
	 * 
	 * @param <E> Type of the elements.
	 * @param iterator Iterator providing the elements to test.
	 * @param filter Filter predicate.
	 * @return <code>true</code> when all values are accepted, <code>false</code> when some value is rejected.
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
	 * Counts the elements provided by the given iterator accepted by the given filter.
	 * 
	 * @param <E> Type of the elements.
	 * @param iterator Iterator providing the elements to count.
	 * @param filter Filter predicate.
	 * @return The number of accepted elements.
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
	 * Gets the least element provided by the given iterator according to their natural order.
	 *
	 * @param <E> Type of the elements.
	 * @param iterator Iterator providing the elements to compare.
	 * @return The least element.
	 */
	public static <E extends Comparable<E>> Maybe<? extends E> least(final Iterator<? extends E> iterator) {
		return drainAll(iterator, ComparableAccumulators.<E>least()).get();
	}
	
	/**
	 * Gets the least element provided by the given iterator according to the given comparator.
	 *
	 * @param <E> Type of the elements.
	 * @param iterator Iterator providing the elements to compare.
	 * @param comparator Comparator to use.
	 * @return The least element.
	 */
	public static <E> Maybe<? extends E> least(final Iterator<? extends E> iterator, final Comparator<? super E> comparator) {
		return drainAll(iterator, ComparatorAccumulators.least(comparator)).get();
	}
	
	/**
	 * Gets the greatest element provided by the given iterator according to their natural order.
	 *
	 * @param <E> Type of the elements.
	 * @param iterator Iterator providing the elements to compare.
	 * @return The greatest element.
	 */
	public static <E extends Comparable<E>> Maybe<? extends E> greatest(final Iterator<? extends E> iterator) {
		return drainAll(iterator, ComparableAccumulators.<E>greatest()).get();
	}
	
	/**
	 * Gets the greatest element provided by the given iterator according to the given comparator.
	 *
	 * @param <E> Type of the elements.
	 * @param iterator Iterator providing the elements to compare.
	 * @param comparator Comparator to use.
	 * @return The greatest element.
	 */
	public static <E> Maybe<? extends E> greatest(final Iterator<? extends E> iterator, final Comparator<? super E> comparator) {
		return drainAll(iterator, ComparatorAccumulators.greatest(comparator)).get();
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
	 */
	public static <E> Iterator<E> append(final Iterator<? extends E> iterator1, final Iterator<? extends E> iterator2) {
		assert null != iterator1;
		assert null != iterator2;
		
		return new Iterator<E>() {
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
	 */
	public static <E> Iterator<E> flatten(final Iterator<? extends Iterator<? extends E>> iterator) {
		assert null != iterator;
		
		return new Iterator<E>() {
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
	 */
	public static <E> Iterator<E> take(final Iterator<? extends E> iterator, final int n) {
		assert null != iterator;
		
		return new Iterator<E>() {
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
	 */
	public static <E> Iterator<E> drop(final Iterator<? extends E> iterator, final int n) {
		assert null != iterator;
		
		return new Iterator<E>() {
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
	 * Filters the elements provided by the given iterator using the given filter.
	 * <p>
	 * The built iterator feeds from the given iterator.
	 *
	 * @param <E> Type of the elements.
	 * @param iterator Iterator providing the elements to filter.
	 * @param filter Predicate to use to filter the elements.
	 * @return An iterator providing the filtered elements.
	 */
	public static <E> Iterator<E> filter(final Iterator<? extends E> iterator, final Predicate<? super E> filter) {
		assert null != iterator;
		assert null != filter;
		
		return new LookAheadIterator<E>() {
			@Override
			protected Maybe<? extends E> pull() {
				return first(iterator, filter);
			}
		};
	}
	
	/**
	 * Transforms the elements provided by the given iterator using the given function.
	 * <p>
	 * The built iterator feeds from the given iterator.
	 *
	 * @param <E> Type of the elements.
	 * @param <RE> Type of the transformed elements.
	 * @param iterator Iterator providing the elements to transform.
	 * @param function Function to use to transform the elements.
	 * @return An iterator providing the transformed elements.
	 */
	public static <E, RE> Iterator<RE> map(final Iterator<? extends E> iterator, final Function<? super E, ? extends RE> function) {
		assert null != iterator;
		assert null != function;
		
		return new LookAheadIterator<RE>() {
			@Override
			protected Maybe<? extends RE> pull() {
				return IteratorUtils.next(iterator).map(function);
			}
		};
	}
	
	/**
	 * Transforms and flattens the elements provided by the given iterator using the given function.
	 * <p>
	 * The built iterator feeds from the given iterator.
	 * 
	 * @param <E> Type of the elements.
	 * @param <RE> Type of the transformed elements.
	 * @param iterator Iterator providing the elements to transform.
	 * @param function Function to use to transform the elements.
	 * @return An iterator providing the flatten, transformed elements.
	 */
	public static <E, RE> Iterator<RE> flatMap(final Iterator<? extends E> iterator, final Function<? super E, ? extends Iterator<? extends RE>> function) {
		return flatten(map(iterator, function));
	}
	
	/**
	 * Extracts and flattens the elements provided by the given iterator using the given extractor.
	 * <p>
	 * The built iterator feeds from the given iterator.
	 *
	 * @param <E> Type of the elements.
	 * @param <RE> Type of the extracted elements.
	 * @param iterator Iterator providing the elements to extract from.
	 * @param function Function to use to extract the elements.
	 * @return An iterator providing the extracted elements.
	 */
	public static <E, RE> Iterator<RE> extract(final Iterator<? extends E> iterator, final Function<? super E, ? extends Iterable<? extends RE>> function) {
		return flatMap(iterator, Functions.compose(IterableFunctions.iterator(), function));
	}
	
	private IteratorUtils() {
		// Prevent instantiation.
	}
}
