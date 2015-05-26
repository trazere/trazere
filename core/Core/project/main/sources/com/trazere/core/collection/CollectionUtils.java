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
package com.trazere.core.collection;

import com.trazere.core.functional.Function;
import com.trazere.core.functional.Function2;
import com.trazere.core.functional.Predicate;
import com.trazere.core.functional.Predicates;
import com.trazere.core.imperative.Accumulator;
import com.trazere.core.imperative.IteratorUtils;
import com.trazere.core.imperative.Procedure;
import com.trazere.core.lang.LangAccumulators;
import com.trazere.core.util.Maybe;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;

/**
 * The {@link CollectionUtils} class provides various utilities regarding the manipulation of collections and maps.
 */
public class CollectionUtils {
	/**
	 * Gets an element of the given collection.
	 *
	 * @param <E> Type of the elements.
	 * @param collection Collection to read.
	 * @return An element of the collection, or nothing when the collection is empty.
	 */
	public static <E> Maybe<E> any(final Collection<? extends E> collection) {
		return IteratorUtils.next(collection.iterator());
	}
	
	/**
	 * Adds all given elements to the given collection.
	 * <p>
	 * This method does modify the given collection.
	 *
	 * @param <E> Type of the elements.
	 * @param collection Collection to modify.
	 * @param elements Elements to add.
	 * @return <code>true</code> when the given collection is modified, <code>false</code> otherwise.
	 */
	@SafeVarargs
	public static <E> boolean addAll(final Collection<? super E> collection, final E... elements) {
		final Accumulator<Boolean, Boolean> changed = LangAccumulators.or(false);
		for (final E element : elements) {
			changed.add(collection.add(element));
		}
		return changed.get().booleanValue();
	}
	
	/**
	 * Adds all given elements to the given collection.
	 * <p>
	 * This method does modify the given collection.
	 *
	 * @param <E> Type of the elements.
	 * @param collection Collection to modify.
	 * @param elements Elements to add.
	 * @return <code>true</code> when the given collection is modified, <code>false</code> otherwise.
	 */
	public static <E> boolean addAll(final Collection<? super E> collection, final Iterable<? extends E> elements) {
		final Accumulator<Boolean, Boolean> changed = LangAccumulators.or(false);
		for (final E element : elements) {
			changed.add(collection.add(element));
		}
		return changed.get().booleanValue();
	}
	
	/**
	 * Removes an element from the given collection.
	 * <p>
	 * The collection must support removal through its iterators.
	 * <p>
	 * This method does modify the given collection.
	 *
	 * @param <E> Type of the elements.
	 * @param collection Collection to modify.
	 * @return The removed element, or nothing when the collection is empty.
	 */
	public static <E> Maybe<E> removeAny(final Collection<? extends E> collection) {
		final Iterator<? extends E> iterator = collection.iterator();
		if (iterator.hasNext()) {
			final E element = iterator.next();
			iterator.remove();
			return Maybe.some(element);
		} else {
			return Maybe.none();
		}
	}
	
	/**
	 * Removes all given elements from the given collection.
	 * <p>
	 * This method does modify the given collection.
	 *
	 * @param <E> Type of the elements.
	 * @param collection Collection to modify.
	 * @param elements Elements to remove.
	 * @return <code>true</code> when the given collection is modified, <code>false</code> otherwise.
	 */
	public static <E> boolean removeAll(final Collection<? super E> collection, final Iterable<? extends E> elements) {
		final Accumulator<Boolean, Boolean> changed = LangAccumulators.or(false);
		for (final E element : elements) {
			changed.add(collection.remove(element));
		}
		return changed.get().booleanValue();
	}
	
	/**
	 * Removes the first element of the given collection accepted by the given filter.
	 * <p>
	 * The collection must support removal through its iterators.
	 * <p>
	 * This method does modify the given collection.
	 * 
	 * @param <E> Type of the elements.
	 * @param collection Collection containing the elements to filter.
	 * @param filter Predicate to use to filter the elements.
	 * @return The first removed element.
	 */
	public static <E> Maybe<E> removeFirst(final Collection<? extends E> collection, final Predicate<? super E> filter) {
		final Iterator<? extends E> iterator = collection.iterator();
		while (iterator.hasNext()) {
			final E element = iterator.next();
			if (filter.evaluate(element)) {
				iterator.remove();
				return Maybe.some(element);
			}
		}
		return Maybe.none();
	}
	
	/**
	 * Retains the elements in the given collection using the given filter.
	 * <p>
	 * This method does modify the given collection.
	 *
	 * @param <E> Type of the elements.
	 * @param <C> Type of the collection.
	 * @param collection Collection to filter.
	 * @param filter Predicate to use to filter the elements.
	 * @return The given filtered collection.
	 */
	public static <E, C extends Collection<? extends E>> C retain(final C collection, final Predicate<? super E> filter) {
		final Iterator<? extends E> elements = collection.iterator();
		while (elements.hasNext()) {
			if (!filter.evaluate(elements.next())) {
				elements.remove();
			}
		}
		return collection;
	}
	
	/**
	 * Executes the given procedure with each element of the given collection.
	 * 
	 * @param <E> Type of the elements.
	 * @param collection Collection of elements.
	 * @param procedure Procedure to execute.
	 */
	public static <E> void foreach(final Collection<? extends E> collection, final Procedure<? super E> procedure) {
		IteratorUtils.foreach(collection.iterator(), procedure);
	}
	
	/**
	 * Left folds over the elements of the given collection using the given binary operator and initial state.
	 * 
	 * @param <E> Type of the elements.
	 * @param <S> Type of the state.
	 * @param collection Collection containing the elements to fold over.
	 * @param operator Binary operator to use.
	 * @param initialState Initial state.
	 * @return The folded state.
	 */
	public static <E, S> S fold(final Collection<? extends E> collection, final Function2<? super S, ? super E, ? extends S> operator, final S initialState) {
		return IteratorUtils.fold(collection.iterator(), operator, initialState);
	}
	
	/**
	 * Gets the first element of the given collection accepted by the given filter.
	 * 
	 * @param <E> Type of the elements.
	 * @param collection Collection containing the elements to filter.
	 * @param filter Predicate to use to filter the elements.
	 * @return The first accepted element.
	 */
	public static <E> Maybe<E> first(final Collection<? extends E> collection, final Predicate<? super E> filter) {
		return IteratorUtils.first(collection.iterator(), filter);
	}
	
	/**
	 * Gets the first element extracted from the given collection by the given extractor.
	 * 
	 * @param <E> Type of the elements.
	 * @param <EE> Type of the extracted elements.
	 * @param collection Collection containing the elements to extract from.
	 * @param extractor Function to use to extract the elements.
	 * @return The first extracted element.
	 */
	public static <E, EE> Maybe<EE> extractFirst(final Collection<? extends E> collection, final Function<? super E, ? extends Maybe<? extends EE>> extractor) {
		return IteratorUtils.extractFirst(collection.iterator(), extractor);
	}
	
	/**
	 * Tests whether any element of the given collection is accepted by the given filter.
	 * 
	 * @param <E> Type of the elements.
	 * @param collection Collection to test.
	 * @param filter Predicate to use to filter the elements.
	 * @return <code>true</code> when some element is accepted, <code>false</code> when all elements are rejected.
	 */
	public static <E> boolean isAny(final Collection<? extends E> collection, final Predicate<? super E> filter) {
		return IteratorUtils.isAny(collection.iterator(), filter);
	}
	
	/**
	 * Tests whether all elements of the given collection are accepted by the given filter.
	 * 
	 * @param <E> Type of the elements.
	 * @param collection Collection to test.
	 * @param filter Predicate to use to filter the elements.
	 * @return <code>true</code> when all elements are accepted, <code>false</code> when some element is rejected.
	 */
	public static <E> boolean areAll(final Collection<? extends E> collection, final Predicate<? super E> filter) {
		return IteratorUtils.areAll(collection.iterator(), filter);
	}
	
	/**
	 * Tests whether the given collections contains some common value.
	 * <p>
	 * This method iterates over the first collection and tests the presence of the values within the second collection. Therefore, providing a smaller first
	 * collection and a second collection with a faster test method is more efficient.
	 * 
	 * @param <E> Type of the elements.
	 * @param collection1 First collection.
	 * @param collection2 Second collection.
	 * @return <code>true</code> if the collections intersect, <code>false</code> otherwise.
	 */
	public static <E> boolean intersects(final Collection<? extends E> collection1, final Collection<? extends E> collection2) {
		return isAny(collection1, Predicates.values(collection2));
	}
	
	/**
	 * Counts the elements of the given collection accepted by the given filter.
	 * 
	 * @param <E> Type of the elements.
	 * @param collection Collection containing the elements to count.
	 * @param filter Predicate to use to filter the elements.
	 * @return The number of accepted elements.
	 */
	public static <E> int count(final Collection<? extends E> collection, final Predicate<? super E> filter) {
		return IteratorUtils.count(collection.iterator(), filter);
	}
	
	/**
	 * Gets the least element of the given collection.
	 *
	 * @param <E> Type of the elements.
	 * @param collection Collection containing the elements to compare.
	 * @return The least element.
	 */
	public static <E extends Comparable<E>> Maybe<E> least(final Collection<? extends E> collection) {
		return IteratorUtils.least(collection.iterator());
	}
	
	/**
	 * Gets the least element of the given collection according to the given comparator.
	 *
	 * @param <E> Type of the elements.
	 * @param collection Collection providing the elements to compare.
	 * @param comparator Comparator to use.
	 * @return The least element.
	 */
	public static <E> Maybe<E> least(final Collection<? extends E> collection, final Comparator<? super E> comparator) {
		return IteratorUtils.least(collection.iterator(), comparator);
	}
	
	/**
	 * Gets the greatest element of the given collection.
	 *
	 * @param <E> Type of the elements.
	 * @param collection Collection containing the elements to compare.
	 * @return The greatest element.
	 */
	public static <E extends Comparable<E>> Maybe<E> greatest(final Collection<? extends E> collection) {
		return IteratorUtils.greatest(collection.iterator());
	}
	
	/**
	 * Gets the greatest element of the given collection according to the given comparator.
	 *
	 * @param <E> Type of the elements.
	 * @param collection Collection containing the elements to compare.
	 * @param comparator Comparator to use.
	 * @return The greatest element.
	 */
	public static <E> Maybe<E> greatest(final Collection<? extends E> collection, final Comparator<? super E> comparator) {
		return IteratorUtils.greatest(collection.iterator(), comparator);
	}
	
	/**
	 * Appends the given collections together.
	 * 
	 * @param <E> Type of the elements.
	 * @param <C> Type of the result collection.
	 * @param collection1 First collection containing the elements to append.
	 * @param collection2 Second collection containing the elements to append.
	 * @param resultFactory Factory of the result collection.
	 * @return A collection containing the appended elements.
	 */
	public static <E, C extends Collection<? super E>> C append(final Collection<? extends E> collection1, final Collection<? extends E> collection2, final CollectionFactory<? super E, C> resultFactory) {
		final C results = resultFactory.build(collection1.size() + collection2.size());
		results.addAll(collection1);
		results.addAll(collection2);
		return results;
	}
	
	/**
	 * Flattens the elements of the collections contained in the given collection.
	 *
	 * @param <E> Type of the elements.
	 * @param <C> Type of the result collection.
	 * @param collection Collection containing the collections containing the elements to flatten.
	 * @param resultFactory Factory of the result collection.
	 * @return A collection containing the flatten elements.
	 */
	public static <E, C extends Collection<? super E>> C flatten(final Collection<? extends Collection<? extends E>> collection, final CollectionFactory<? super E, C> resultFactory) {
		final C results = resultFactory.build();
		for (final Collection<? extends E> elements : collection) {
			results.addAll(elements);
		}
		return results;
	}
	
	/**
	 * Computes the intersection of the given collections.
	 * <p>
	 * This method iterates over the first collection and tests the presence of the values within the second collection. Therefore, providing a smaller first
	 * collection and a second collection with a faster test method is more efficient.
	 *
	 * @param <E> Type of the elements.
	 * @param <C> Type of the result collection.
	 * @param collection1 First collection.
	 * @param collection2 Second collection.
	 * @param resultFactory Factory of the result collection.
	 * @return A collection containing the common elements.
	 */
	public static <E, C extends Collection<? super E>> C intersection(final Collection<? extends E> collection1, final Collection<? extends E> collection2, final CollectionFactory<? super E, C> resultFactory) {
		final C results = resultFactory.build();
		for (final E value : collection1) {
			if (collection2.contains(value)) {
				results.add(value);
			}
		}
		return results;
	}
	
	/**
	 * Computes the exclusion of given collections (first minus second).
	 * <p>
	 * This method iterates over the first collection and tests the presence of the values within the second collection. Therefore, providing a smaller first
	 * collection and a second collection with a faster test method is more efficient.
	 *
	 * @param <E> Type of the elements.
	 * @param <C> Type of the result collection.
	 * @param collection1 First collection.
	 * @param collection2 Second collection.
	 * @param resultFactory Factory of the result collection.
	 * @return A collection containing the excluded elements.
	 */
	public static <E, C extends Collection<? super E>> C exclusion(final Collection<? extends E> collection1, final Collection<? extends E> collection2, final CollectionFactory<? super E, C> resultFactory) {
		final C results = resultFactory.build();
		for (final E value : collection1) {
			if (!collection2.contains(value)) {
				results.add(value);
			}
		}
		return results;
	}
	
	/**
	 * Takes the n first elements of the given collection.
	 *
	 * @param <E> Type of the elements.
	 * @param <C> Type of the result collection.
	 * @param collection Collection containing the elements to take.
	 * @param n Number of elements to take.
	 * @param resultFactory Factory of the result collection.
	 * @return A collection containing the taken elements.
	 */
	public static <E, C extends Collection<? super E>> C take(final Collection<? extends E> collection, final int n, final CollectionFactory<? super E, C> resultFactory) {
		return IteratorUtils.drain(IteratorUtils.take(collection.iterator(), n), resultFactory.build(n));
	}
	
	/**
	 * Drops the n first elements of the given collection.
	 *
	 * @param <E> Type of the elements.
	 * @param <C> Type of the result collection.
	 * @param collection Collection containing the elements to drop.
	 * @param n Number of elements to drop.
	 * @param resultFactory Factory of the result collection.
	 * @return A collection containing the remaining elements.
	 */
	public static <E, C extends Collection<? super E>> C drop(final Collection<? extends E> collection, final int n, final CollectionFactory<? super E, C> resultFactory) {
		return IteratorUtils.drain(IteratorUtils.drop(collection.iterator(), n), resultFactory.build(Math.max(0, collection.size() - n)));
	}
	
	/**
	 * Filters the elements of the given collection using the given filter.
	 *
	 * @param <E> Type of the elements.
	 * @param <C> Type of the result collection.
	 * @param collection Collection containing the elements to filter.
	 * @param filter Predicate to use to filter the elements.
	 * @param resultFactory Factory of the result collection.
	 * @return A collection containing the filtered elements.
	 */
	public static <E, C extends Collection<? super E>> C filter(final Collection<? extends E> collection, final Predicate<? super E> filter, final CollectionFactory<? super E, C> resultFactory) {
		final C results = resultFactory.build();
		for (final E element : collection) {
			if (filter.evaluate(element)) {
				results.add(element);
			}
		}
		return results;
	}
	
	/**
	 * Transforms the elements of the given collection using the given function.
	 *
	 * @param <E> Type of the elements.
	 * @param <TE> Type of the transformed elements.
	 * @param <C> Type of the result collection.
	 * @param collection Collection containing the elements to transform.
	 * @param function Function to use to transform the elements.
	 * @param resultFactory Factory of the result collection.
	 * @return A collection containing the transformed elements.
	 */
	public static <E, TE, C extends Collection<? super TE>> C map(final Collection<? extends E> collection, final Function<? super E, ? extends TE> function, final CollectionFactory<? super TE, C> resultFactory) {
		final C results = resultFactory.build(collection.size());
		for (final E element : collection) {
			results.add(function.evaluate(element));
		}
		return results;
	}
	
	/**
	 * Transforms and flattens the elements of the given collection using the given function.
	 * 
	 * @param <E> Type of the elements.
	 * @param <TE> Type of the transformed elements.
	 * @param <C> Type of the result collection.
	 * @param collection Collection containing the elements to transform.
	 * @param function Function to use to transform the elements.
	 * @param resultFactory Factory of the result collection.
	 * @return A collection containing the flatten, transformed elements.
	 */
	public static <E, TE, C extends Collection<? super TE>> C flatMap(final Collection<? extends E> collection, final Function<? super E, ? extends Collection<? extends TE>> function, final CollectionFactory<? super TE, C> resultFactory) {
		final C results = resultFactory.build();
		for (final E element : collection) {
			addAll(results, function.evaluate(element));
		}
		return results;
	}
	
	/**
	 * Extracts the elements of the given collection using the given extractor.
	 *
	 * @param <E> Type of the elements.
	 * @param <EE> Type of the extracted elements.
	 * @param <C> Type of the result collection.
	 * @param collection Collection containing the elements to extract from.
	 * @param extractor Function to use to extract the elements.
	 * @param resultFactory Factory of the result collection.
	 * @return A collection containing the extracted elements.
	 */
	public static <E, EE, C extends Collection<? super EE>> C extract(final Collection<? extends E> collection, final Function<? super E, ? extends Maybe<? extends EE>> extractor, final CollectionFactory<? super EE, C> resultFactory) {
		final C results = resultFactory.build();
		for (final E element : collection) {
			final Maybe<? extends EE> extractedElement = extractor.evaluate(element);
			if (extractedElement.isSome()) {
				results.add(extractedElement.asSome().getValue());
			}
		}
		return results;
	}
	
	private CollectionUtils() {
		// Prevents instantiation.
	}
}
