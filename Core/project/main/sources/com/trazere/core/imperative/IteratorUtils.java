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
import com.trazere.core.util.IntCounter;
import com.trazere.core.util.Maybe;
import java.util.Collection;
import java.util.Iterator;

/**
 * The {@link IteratorUtils} class provides various helpers regarding {@link Iterator iterators}.
 */
public class IteratorUtils {
	/**
	 * Drops the next value provided by the given iterator.
	 * 
	 * @param iterator Iterator to consume.
	 * @return <code>true</code> when some value is dropped, <code>false</code> when the iterator is empty.
	 */
	public static boolean drop(final Iterator<?> iterator) {
		if (iterator.hasNext()) {
			iterator.next();
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Drops the next n values provided by the given iterator.
	 * 
	 * @param n Number of values to drop.
	 * @param values Iterator to consume.
	 */
	public static void drop(final Iterator<?> values, final int n) {
		final IntCounter counter = new IntCounter();
		while (values.hasNext() && counter.inc() <= n) {
			values.next();
		}
	}
	
	/**
	 * Gets the next value provided by the given iterator.
	 * 
	 * @param <T> Type of the values.
	 * @param iterator Iterator to consume.
	 * @return The next value.
	 */
	public static <T> Maybe<T> next(final Iterator<? extends T> iterator) {
		return iterator.hasNext() ? Maybe.<T>some(iterator.next()) : Maybe.<T>none();
	}
	
	/**
	 * Drains all values provided by the the given iterator and populates the given accumulator with them.
	 * 
	 * @param <T> Type of the values.
	 * @param <A> Type of the accumulator to populate.
	 * @param values Iterator to drain.
	 * @param results Accumulator to populate with the drained values.
	 * @return The given result accumulator.
	 */
	public static <T, A extends Accumulator<? super T, ?>> A drain(final Iterator<? extends T> values, final A results) {
		while (values.hasNext()) {
			results.add(values.next());
		}
		return results;
	}
	
	/**
	 * Drains all values provided by the the given iterator and adds them to the given collection.
	 * 
	 * @param <T> Type of the values.
	 * @param <C> Type of the collection to populate.
	 * @param values Iterator to drain.
	 * @param results Collection to populate with the drained values.
	 * @return The given result collection.
	 */
	public static <T, C extends Collection<? super T>> C drain(final Iterator<? extends T> values, final C results) {
		return drain(values, CollectionAccumulators.add(results)).get();
	}
	
	/**
	 * Drains the next n values provided by the given iterator and populates the given accumulator with them.
	 * 
	 * @param <T> Type of the values.
	 * @param <A> Type of the accumulator to populate.
	 * @param n Number of values to drain.
	 * @param values Iterator to drain.
	 * @param results Accumulator to populate with the drained values.
	 * @return The given result accumulator.
	 */
	public static <T, A extends Accumulator<? super T, ?>> A drain(final Iterator<? extends T> values, final int n, final A results) {
		final IntCounter counter = new IntCounter();
		while (values.hasNext() && counter.inc() <= n) {
			results.add(values.next());
		}
		return results;
	}
	
	/**
	 * Drains the next n values provided by the given iterator and adds them to the given collection.
	 * 
	 * @param <T> Type of the values.
	 * @param <C> Type of the collection to populate.
	 * @param n Number of values to drain.
	 * @param values Iterator to drain.
	 * @param results Collection to populate with the drained values.
	 * @return The given result collection.
	 */
	public static <T, C extends Collection<? super T>> C drain(final Iterator<? extends T> values, final int n, final C results) {
		return drain(values, n, CollectionAccumulators.add(results)).get();
	}
	
	/**
	 * Filters the values from the given iterator using the given predicate.
	 * <p>
	 * The built iterator feeds from the given iterator.
	 *
	 * @param <T> Type of the values.
	 * @param iterator Iterator to filter.
	 * @param filter Filter to use.
	 * @return The built iterator over the filtered values.
	 */
	public static <T> Iterator<T> filter(final Iterator<? extends T> iterator, final Predicate<? super T> filter) {
		assert null != iterator;
		assert null != filter;
		
		return new FilterIterator<T>() {
			@Override
			protected Maybe<T> pull() {
				return IteratorUtils.next(iterator);
			}
			
			@Override
			public boolean filter(final T value) {
				return filter.evaluate(value);
			}
		};
	}
	
	/**
	 * Transforms the values from the given iterator using the given function.
	 * <p>
	 * The built iterator feeds from the given iterator.
	 *
	 * @param <T> Type of the values.
	 * @param <R> Type of the transformed values.
	 * @param iterator Iterator to transform.
	 * @param function Function to use to transform the values.
	 * @return The built iterator over the transformed values.
	 */
	public static <T, R> Iterator<R> map(final Iterator<? extends T> iterator, final Function<? super T, ? extends R> function) {
		assert null != iterator;
		assert null != function;
		
		return new MapIterator<T, R>() {
			@Override
			protected Maybe<T> pull() {
				return IteratorUtils.next(iterator);
			}
			
			@Override
			protected R map(final T value) {
				return function.evaluate(value);
			}
		};
	}
	
	// TODO: flatMap
	
	/**
	 * Extracts the values from the given iterator using the given extractor.
	 * <p>
	 * The built iterator feeds from the given iterator.
	 *
	 * @param <T> Type of the values.
	 * @param <R> Type of the extracted values.
	 * @param iterator Iterator to extract.
	 * @param extractor Extractor to use to extract the values.
	 * @return The built iterator over the extracted values.
	 */
	public static <T, R> Iterator<R> extract(final Iterator<? extends T> iterator, final Function<? super T, ? extends Maybe<? extends R>> extractor) {
		assert null != iterator;
		assert null != extractor;
		
		return new ExtractIterator<T, R>() {
			@Override
			protected Maybe<T> pull() {
				return IteratorUtils.next(iterator);
			}
			
			@Override
			public Maybe<? extends R> extract(final T value) {
				return extractor.evaluate(value);
			}
		};
	}
	
	private IteratorUtils() {
		// Prevent instantiation.
	}
}
