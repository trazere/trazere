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
import com.trazere.core.functional.Predicate;
import com.trazere.core.lang.ComparableAccumulators;
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
	 * Gets the least element provided by the given iterator according to their natural order.
	 *
	 * @param <E> Type of the elements.
	 * @param iterator Iterator providing the elements.
	 * @return The least element.
	 */
	public static <E extends Comparable<E>> Maybe<? extends E> least(final Iterator<? extends E> iterator) {
		return drainAll(iterator, ComparableAccumulators.<E>least()).get();
	}
	
	/**
	 * Gets the least element provided by the given iterator according to the given comparator.
	 *
	 * @param <E> Type of the elements.
	 * @param iterator Iterator providing the elements.
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
	 * @param iterator Iterator providing the elements.
	 * @return The greatest element.
	 */
	public static <E extends Comparable<E>> Maybe<? extends E> greatest(final Iterator<? extends E> iterator) {
		return drainAll(iterator, ComparableAccumulators.<E>greatest()).get();
	}
	
	/**
	 * Gets the greatest element provided by the given iterator according to the given comparator.
	 *
	 * @param <E> Type of the elements.
	 * @param iterator Iterator providing the elements.
	 * @param comparator Comparator to use.
	 * @return The greatest element.
	 */
	public static <E> Maybe<? extends E> greatest(final Iterator<? extends E> iterator, final Comparator<? super E> comparator) {
		return drainAll(iterator, ComparatorAccumulators.greatest(comparator)).get();
	}
	
	/**
	 * Takes the n first elements of the given iterator.
	 * <p>
	 * The built iterator feeds from the given iterator.
	 * 
	 * @param <E> Type of the elements.
	 * @param iterator Iterator providing the elements.
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
	 * Drops the n first elements of the given iterator.
	 * <p>
	 * The built iterator feeds from the given iterator.
	 * 
	 * @param <E> Type of the elements.
	 * @param iterator Iterator providing the elements.
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
	
	// TODO: fold
	
	/**
	 * Filters the elements from the given iterator using the given predicate.
	 * <p>
	 * The built iterator feeds from the given iterator.
	 *
	 * @param <E> Type of the elements.
	 * @param iterator Iterator to filter.
	 * @param filter Filter to use.
	 * @return The built iterator over the filtered elements.
	 */
	public static <E> Iterator<E> filter(final Iterator<? extends E> iterator, final Predicate<? super E> filter) {
		assert null != iterator;
		assert null != filter;
		
		return new FilterIterator<E>() {
			@Override
			protected Maybe<E> pull() {
				return IteratorUtils.next(iterator);
			}
			
			@Override
			public boolean filter(final E element) {
				return filter.evaluate(element);
			}
		};
	}
	
	/**
	 * Transforms the elements from the given iterator using the given function.
	 * <p>
	 * The built iterator feeds from the given iterator.
	 *
	 * @param <E> Type of the elements.
	 * @param <RE> Type of the transformed elements.
	 * @param iterator Iterator to transform.
	 * @param function Function to use to transform the elements.
	 * @return The built iterator over the transformed elements.
	 */
	public static <E, RE> Iterator<RE> map(final Iterator<? extends E> iterator, final Function<? super E, ? extends RE> function) {
		assert null != iterator;
		assert null != function;
		
		return new MapIterator<E, RE>() {
			@Override
			protected Maybe<E> pull() {
				return IteratorUtils.next(iterator);
			}
			
			@Override
			protected RE map(final E element) {
				return function.evaluate(element);
			}
		};
	}
	
	// TODO: flatMap
	
	/**
	 * Extracts the elements from the given iterator using the given extractor.
	 * <p>
	 * The built iterator feeds from the given iterator.
	 *
	 * @param <E> Type of the elements.
	 * @param <RE> Type of the extracted elements.
	 * @param iterator Iterator to extract.
	 * @param extractor Extractor to use to extract the elements.
	 * @return The built iterator over the extracted elements.
	 */
	public static <E, RE> Iterator<RE> extract(final Iterator<? extends E> iterator, final Function<? super E, ? extends Maybe<? extends RE>> extractor) {
		assert null != iterator;
		assert null != extractor;
		
		return new ExtractIterator<E, RE>() {
			@Override
			protected Maybe<E> pull() {
				return IteratorUtils.next(iterator);
			}
			
			@Override
			public Maybe<? extends RE> extract(final E element) {
				return extractor.evaluate(element);
			}
		};
	}
	
	private IteratorUtils() {
		// Prevent instantiation.
	}
}
