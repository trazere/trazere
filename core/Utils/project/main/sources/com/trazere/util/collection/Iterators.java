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
package com.trazere.util.collection;

import com.trazere.util.function.Function1;
import com.trazere.util.function.Predicate1;
import com.trazere.util.type.Maybe;
import java.util.Iterator;

/**
 * The {@link Iterators} class provides various factories of iterators.
 * 
 * @see Iterator
 */
public class Iterators {
	/**
	 * Filters the given iterator using the given predicate.
	 * 
	 * @param <T> Type of the values.
	 * @param predicate The predicate.
	 * @param iterator The iterator.
	 * @return The built iterator over the filtered values.
	 */
	public static <T> Iterator<T> filter(final Predicate1<? super T, ? extends RuntimeException> predicate, final Iterator<? extends T> iterator) {
		assert null != predicate;
		assert null != iterator;
		
		return new FilterIterator<T>() {
			@Override
			protected Maybe<T> pull() {
				return CollectionUtils.next(iterator);
			}
			
			@Override
			public boolean filter(final T value) {
				return predicate.evaluate(value);
			}
		};
	}
	
	/**
	 * Transforms the given iterator using the given function.
	 * 
	 * @param <T> Type of the values.
	 * @param <R> Type of the transformed values.
	 * @param function The function.
	 * @param iterator The iterator.
	 * @return The built iterator over the transformed values.
	 */
	public static <T, R> Iterator<R> map(final Function1<? super T, ? extends R, ? extends RuntimeException> function, final Iterator<? extends T> iterator) {
		assert null != function;
		assert null != iterator;
		
		return new MapIterator<T, R>() {
			@Override
			protected Maybe<T> pull() {
				return CollectionUtils.next(iterator);
			}
			
			@Override
			protected R map(final T value) {
				return function.evaluate(value);
			}
		};
	}
	
	/**
	 * Builds an iterator that extracts values from the given iterator using the given extractor.
	 * 
	 * @param <T> Type of the values.
	 * @param <R> Type of the transformed values.
	 * @param extractor The extractor.
	 * @param iterator The iterator.
	 * @return The built iterator over the filtered and transformed values.
	 */
	public static <T, R> Iterator<R> extract(final Function1<? super T, ? extends Maybe<? extends R>, ? extends RuntimeException> extractor, final Iterator<? extends T> iterator) {
		assert null != extractor;
		assert null != iterator;
		
		return new ExtractIterator<T, R>() {
			@Override
			protected Maybe<T> pull() {
				return CollectionUtils.next(iterator);
			}
			
			@Override
			public Maybe<? extends R> extract(final T value) {
				return extractor.evaluate(value);
			}
		};
	}
	
	private Iterators() {
		// Prevent instantiation.
	}
}
