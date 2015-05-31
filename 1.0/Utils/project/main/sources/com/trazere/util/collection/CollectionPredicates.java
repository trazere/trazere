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
package com.trazere.util.collection;

import com.trazere.util.function.Predicate1;
import java.util.Collection;

/**
 * The {@link CollectionPredicates} class provides various factories of predicates related to collections.
 * 
 * @deprecated Use core.
 */
@Deprecated
public class CollectionPredicates {
	/**
	 * Builds a predicate that evaluates to <code>true</code> for empty collections.
	 * 
	 * @param <C> Type of the collections.
	 * @param <X> Type of the exceptions.
	 * @return The built predicate.
	 * @deprecated Use {@link com.trazere.core.collection.CollectionPredicates#isEmpty()}.
	 */
	@Deprecated
	@SuppressWarnings("unchecked")
	public static <C extends Collection<?>, X extends Exception> Predicate1<C, X> isEmpty() {
		return (Predicate1<C, X>) _IS_EMPTY;
	}
	
	private static Predicate1<Collection<?>, ?> _IS_EMPTY = new Predicate1<Collection<?>, RuntimeException>() {
		@Override
		public boolean evaluate(final Collection<?> collection) {
			assert null != collection;
			
			return collection.isEmpty();
		}
	};
	
	/**
	 * Builds a predicate that evaluates to <code>true</code> when the argument collection contains the given value.
	 * 
	 * @param <T> Type of the argument values.
	 * @param <C> Type of the collections.
	 * @param <X> Type of the exceptions.
	 * @param value The value. May be <code>null</code>.
	 * @return The built predicate.
	 * @deprecated Use {@link com.trazere.core.collection.CollectionPredicates#contains(Object)}.
	 */
	@Deprecated
	public static <T, C extends Collection<? super T>, X extends Exception> Predicate1<C, X> contains(final T value) {
		return new Predicate1<C, X>() {
			@Override
			public boolean evaluate(final C collection)
			throws X {
				assert null != collection;
				
				return collection.contains(value);
			}
		};
	}
	
	private CollectionPredicates() {
		// Prevents instantiation.
	}
}
