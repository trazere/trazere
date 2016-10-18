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
import com.trazere.core.functional.Predicate;
import com.trazere.core.imperative.Procedure;
import com.trazere.core.util.Maybe;
import java.util.Collection;
import java.util.Comparator;

/**
 * The {@link Traversable} interface defines generic containers of elements that can be traversed.
 * 
 * @param <E> Type of the elements.
 * @since 2.0
 */
public interface Traversable<E> {
	// TODO: drain like methods
	
	/**
	 * Left folds over the elements of this traversable using the given binary operator and initial state.
	 * 
	 * @param <S> Type of the state.
	 * @param operator Binary operator to use.
	 * @param initialState Initial state.
	 * @return The folded state.
	 * @since 2.0
	 */
	<S> S fold(Function2<? super S, ? super E, ? extends S> operator, S initialState);
	
	/**
	 * Tests whether any element of this traversable is accepted by the given filter.
	 * 
	 * @param filter Predicate to use to filter the elements.
	 * @return <code>true</code> when some element is accepted, <code>false</code> when all elements are rejected or when the traverable is empty.
	 * @since 2.0
	 */
	boolean isAny(Predicate<? super E> filter);
	
	/**
	 * Tests whether all elements of this traversable are accepted by the given filter.
	 * 
	 * @param filter Predicate to use to filter the elements.
	 * @return <code>true</code> when all elements are accepted or when the traversable is empty, <code>false</code> when some element is rejected.
	 * @since 2.0
	 */
	boolean areAll(Predicate<? super E> filter);
	
	/**
	 * Counts the elements of this traversable accepted by the given filter.
	 * 
	 * @param filter Predicate to use to filter the elements.
	 * @return The number of accepted elements.
	 * @since 2.0
	 */
	int count(Predicate<? super E> filter);
	
	/**
	 * Gets the least element of this traversable according to the given comparator.
	 * 
	 * @param comparator Comparator to use.
	 * @return The least element, or nothing when the traversable is empty.
	 * @since 2.0
	 */
	Maybe<E> least(Comparator<? super E> comparator);
	
	/**
	 * Gets the greatest element of this traversable according to the given comparator.
	 * 
	 * @param comparator Comparator to use.
	 * @return The greatest element, or nothing when the traversable is empty.
	 * @since 2.0
	 */
	Maybe<E> greatest(Comparator<? super E> comparator);
	
	/**
	 * Takes n elements of this traversable.
	 * <p>
	 * Note: The result type of this method is loosely typed because Java does not support higher order type parameters.
	 * 
	 * @param n Number of elements to take.
	 * @return A traversable of the taken elements.
	 * @since 2.0
	 */
	Traversable<E> take(int n);
	
	// TODO: takeWhile
	
	/**
	 * Drops n elements of this traversable.
	 * <p>
	 * Note: The result type of this method is loosely typed because Java does not support higher order type parameters.
	 * 
	 * @param n Number of elements to drop.
	 * @return A traversable of the remaining elements.
	 * @since 2.0
	 */
	Traversable<E> drop(int n);
	
	// TODO: dropWhile
	
	/**
	 * Groups the elements of this traversable into batches of the given size.
	 * <p>
	 * Note: The result type of this method is loosely typed because Java does not support higher order type parameters.
	 * 
	 * @param <B> Type of the batch collections.
	 * @param n Number of elements of each batch.
	 * @param batchFactory Factory of the batch collections.
	 * @return A traversable providing the batches of elements.
	 * @since 2.0
	 */
	<B extends Collection<? super E>> Traversable<B> group(int n, CollectionFactory<? super E, B> batchFactory);
	
	/**
	 * Filters the elements of this traversable using the given filter.
	 * <p>
	 * Note: The result type of this method is loosely typed because Java does not support higher order type parameters.
	 * 
	 * @param filter Predicate to use to filter the elements.
	 * @return A traversable of the filtered elements.
	 * @since 2.0
	 */
	Traversable<E> filter(Predicate<? super E> filter);
	
	/**
	 * Gets any element of this traversable accepted by the given filter.
	 * 
	 * @param filter Predicate to use to filter the elements.
	 * @return The accepted element, or nothing when no elements are accepted by the filter.
	 * @since 2.0
	 */
	Maybe<E> filterAny(Predicate<? super E> filter);
	
	/**
	 * Transforms the elements of this traversable using the given function.
	 * <p>
	 * Note: The result type of this method is loosely typed because Java does not support higher order type parameters.
	 * 
	 * @param <TE> Type of the transformed elements.
	 * @param function Function to use to transform the elements.
	 * @return A traversable of the transformed elements.
	 * @since 2.0
	 */
	<TE> Traversable<TE> map(Function<? super E, ? extends TE> function);
	
	/**
	 * Extracts the elements from the elements of this traversable using the given extractor.
	 * <p>
	 * Note: The result type of this method is loosely typed because Java does not support higher order type parameters.
	 * 
	 * @param <EE> Type of the extracted elements.
	 * @param extractor Function to use to extract from the elements.
	 * @return A traversable of the extracted elements.
	 * @since 2.0
	 */
	<EE> Traversable<EE> extract(Function<? super E, ? extends Maybe<? extends EE>> extractor);
	
	/**
	 * Gets the element extracted from any element of this traversable using the given extractor.
	 * 
	 * @param <EE> Type of the extracted element.
	 * @param extractor Function to use to extract the elements.
	 * @return The extracted element, or nothing when no elements can be extracted from any element.
	 * @since 2.0
	 */
	<EE> Maybe<EE> extractAny(Function<? super E, ? extends Maybe<? extends EE>> extractor);
	
	// Note: flatMap is not defined here because Java does not support higher order type parameters.
	// TODO: rename ? extractMany ? extractN ? extractMultiple ?
	//	/**
	//	 * Gets all elements extracted from the elements of this traversable using the given extractor.
	//	 * <p>
	//	 * Note: The result type of this method is loosely typed because Java does not support higher order type parameters.
	//	 *
	//	 * @param <EE> Type of the extracted elements.
	//	 * @param extractor Function to use to extract from the elements.
	//	 * @return A traversable of the extracted elements.
	//	 * @since 2.0
	//	 */
	//	<EE> Traversable<EE> extractAll(Function<? super E, ? extends Iterable<? extends EE>> extractor);
	
	// Note: flatMap is not defined here because Java does not support higher order type parameters.
	//	/**
	//	 * Transforms and flattens the elements of this traversable using the given function.
	//	 * <p>
	//	 * Note: The result type of this method is loosely typed because Java does not support higher order type parameters.
	//	 *
	//	 * @param <TE> Type of the transformed elements.
	//	 * @param extractor Function to use to transform the elements.
	//	 * @return A traversable of the flatten, transformed elements.
	//	 * @since 2.0
	//	 */
	//	<TE> Traversable<TE> flatMap(Function<? super E, ? extends Traversable<? extends TE>> extractor);
	
	/**
	 * Executes the given procedure with each element of this traversable.
	 * 
	 * @param procedure Procedure to execute.
	 * @since 2.0
	 */
	void foreach(Procedure<? super E> procedure);
}
