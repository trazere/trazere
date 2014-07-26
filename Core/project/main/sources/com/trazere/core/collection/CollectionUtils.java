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
	 * Populates the given accumulator with copies of the elements of the given collection.
	 *
	 * @param <E> Type of the elements.
	 * @param <A> Type of the accumulator to populate.
	 * @param collection Collection to read.
	 * @param results Accumulator to populate with the elements.
	 * @return The given result accumulator.
	 */
	public static <E, A extends Accumulator<? super E, ?>> A copy(final Collection<? extends E> collection, final A results) {
		return IteratorUtils.drain(collection.iterator(), results);
	}
	
	/**
	 * Adds copies of the bindings of the given map to the given collection.
	 *
	 * @param <E> Type of the elements.
	 * @param <C> Type of the collection to populate.
	 * @param collection Collection to read.
	 * @param results Collection to populate with the bindings.
	 * @return The given result collection.
	 */
	public static <E, C extends Collection<? super E>> C copy(final Collection<? extends E> collection, final C results) {
		return IteratorUtils.drain(collection.iterator(), results);
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
	public static <E, EE> Maybe<EE> first(final Collection<? extends E> collection, final Function<? super E, ? extends Maybe<? extends EE>> extractor) {
		return IteratorUtils.first(collection.iterator(), extractor);
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
	 * @param collection1 First collection providing the elements to append.
	 * @param collection2 Second collection providing the elements to append.
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
	
	//
	//
	//
	//
	//
	
	//	/**
	//	 * Computes the union of the given collections and populates the given result collection with it.
	//	 * <p>
	//	 * When the populated collection is ordered, the items of the first given collection precede the items of the second one.
	//	 *
	//	 * @param <T> Type of the elements.
	//	 * @param <C> Type of the result collection.
	//	 * @param collection1 The first collection.
	//	 * @param collection2 The second collection.
	//	 * @param results The collection to populate with the results.
	//	 * @return The given result collection.
	//	 */
	//	public static <T, C extends Collection<? super T>> C union(final Collection<? extends T> collection1, final Collection<? extends T> collection2, final C results) {
	//		assert null != collection1;
	//		assert null != collection2;
	//		assert null != results;
	//
	//		results.addAll(collection1);
	//		results.addAll(collection2);
	//		return results;
	//	}
	//
	//	/**
	//	 * Computes the union of the given maps and populates the given result map with it.
	//	 * <p>
	//	 * The values of the first map have precedence over the values of the second map when their domains intersect.
	//	 *
	//	 * @param <K> Type of the keys.
	//	 * @param <V> Type of the values.
	//	 * @param <M> Type of the result map.
	//	 * @param map1 The first map.
	//	 * @param map2 The second map.
	//	 * @param results The map to populate with the results.
	//	 * @return The given result map.
	//	 */
	//	public static <K, V, M extends Map<? super K, ? super V>> M union(final Map<? extends K, ? extends V> map1, final Map<? extends K, ? extends V> map2, final M results) {
	//		assert null != map1;
	//		assert null != map2;
	//		assert null != results;
	//
	//		results.putAll(map2);
	//		results.putAll(map1);
	//		return results;
	//	}
	//
	//	/**
	//	 * Tests whether the given collections intersect. The collections instersect when they have some common value.
	//	 * <p>
	//	 * This method iterates over the first collection and tests the presence of the values within the second collection. Therefore, providing a smaller first
	//	 * collection and a second collection with a faster test method is more efficient.
	//	 *
	//	 * @param <T> Type of the elements.
	//	 * @param collection1 The first collection.
	//	 * @param collection2 The second collection.
	//	 * @return <code>true</code> if the collections intersect, <code>false</code> otherwise.
	//	 */
	//	public static <T> boolean intersects(final Collection<? extends T> collection1, final Collection<? extends T> collection2) {
	//		assert null != collection1;
	//		assert null != collection2;
	//
	//		for (final T value : collection1) {
	//			if (collection2.contains(value)) {
	//				return true;
	//			}
	//		}
	//		return false;
	//	}
	//
	//	/**
	//	 * Computes the intersection of the given collections and populates the given result collection with it.
	//	 * <p>
	//	 * This method iterates over the first collection and tests the presence of the values within the second collection. Therefore, providing a smaller first
	//	 * collection and a second collection with a faster test method is more efficient.
	//	 *
	//	 * @param <T> Type of the elements.
	//	 * @param <C> Type of the result collection.
	//	 * @param collection1 The first collection.
	//	 * @param collection2 The second collection.
	//	 * @param results The collection to populate with the results.
	//	 * @return The given result collection.
	//	 */
	//	public static <T, C extends Collection<? super T>> C intersection(final Collection<? extends T> collection1, final Collection<? extends T> collection2, final C results) {
	//		assert null != collection1;
	//		assert null != collection2;
	//		assert null != results;
	//
	//		for (final T value : collection1) {
	//			if (collection2.contains(value)) {
	//				results.add(value);
	//			}
	//		}
	//		return results;
	//	}
	//
	//	/**
	//	 * Computes the exclusion of given collections (first minus second) and populates the given result collection with it.
	//	 * <p>
	//	 * This method iterates over the first collection and tests the presence of the values within the second collection. Therefore, providing a smaller first
	//	 * collection and a second collection with a faster test method is more efficient.
	//	 *
	//	 * @param <T> Type of the elements.
	//	 * @param <C> Type of the result collection.
	//	 * @param collection1 The first collection.
	//	 * @param collection2 The second collection.
	//	 * @param results The collection to populate with the results.
	//	 * @return The given result collection.
	//	 */
	//	public static <T, C extends Collection<? super T>> C exclusion(final Collection<? extends T> collection1, final Collection<? extends T> collection2, final C results) {
	//		assert null != collection1;
	//		assert null != collection2;
	//		assert null != results;
	//
	//		for (final T value : collection1) {
	//			if (!collection2.contains(value)) {
	//				results.add(value);
	//			}
	//		}
	//		return results;
	//	}
	//
	//	/**
	//	 * Fills the given map with the bindings corresponding to the given value associated to the given keys.
	//	 *
	//	 * @param <K> Type of the keys.
	//	 * @param <V> Type of the values.
	//	 * @param <M> Type of the map.
	//	 * @param map The map.
	//	 * @param keys The keys.
	//	 * @param value The value. May be <code>null</code>.
	//	 * @return The given map.
	//	 */
	//	public static <K, V, M extends Map<? super K, ? super V>> M fill(final M map, final Iterable<? extends K> keys, final V value) {
	//		assert null != map;
	//		assert null != keys;
	//
	//		for (final K key : keys) {
	//			map.put(key, value);
	//		}
	//		return map;
	//	}
	//
	//	// TODO: rename to retain or filter
	//	// TODO: genalize with a funciton instead of a map
	//	/**
	//	 * Gets the bindings of the given map associated to the given keys and populates the given result map with them.
	//	 *
	//	 * @param <K> Type of the keys.
	//	 * @param <V> Type of the values.
	//	 * @param <M> Type of the result map.
	//	 * @param map The map.
	//	 * @param keys The keys of the bindings.
	//	 * @param results The map to populate with the results.
	//	 * @return The given result map.
	//	 */
	//	public static <K, V, M extends Map<? super K, ? super V>> M sub(final Map<? extends K, ? extends V> map, final Iterable<? extends K> keys, final M results) {
	//		assert null != map;
	//		assert null != keys;
	//		assert null != results;
	//
	//		for (final K key : keys) {
	//			if (map.containsKey(key)) {
	//				final V value = map.get(key);
	//				results.put(key, value);
	//			}
	//		}
	//		return results;
	//	}
	//
	//	// TODO: rename to ???
	//	// TODO: genalize with a funciton instead of a map
	//	/**
	//	 * Gets the bindings of the given map not associated to the given keys and populates the given result map with them.
	//	 *
	//	 * @param <K> Type of the keys.
	//	 * @param <V> Type of the values.
	//	 * @param <M> Type of the result map.
	//	 * @param map The map.
	//	 * @param keys The keys of the bindings.
	//	 * @param results The map to populate with the results.
	//	 * @return The given result map.
	//	 */
	//	public static <K, V, M extends Map<? super K, ? super V>> M retain(final Map<? extends K, ? extends V> map, final Collection<? extends K> keys, final M results) {
	//		assert null != map;
	//		assert null != keys;
	//		assert null != results;
	//
	//		for (final Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
	//			final K key = entry.getKey();
	//			if (!keys.contains(key)) {
	//				results.put(key, entry.getValue());
	//			}
	//		}
	//		return results;
	//	}
	//
	//	// TODO: accumulator version
	//	/**
	//	 * Groups the given values by batches of the given size and populates the given collections with the batches.
	//	 *
	//	 * @param <T> Type of the values.
	//	 * @param <C> Type of the batch collections.
	//	 * @param <C> Type of the result collection.
	//	 * @param factory The collection factory of the batches.
	//	 * @param size The maximum size of each batch.
	//	 * @param values The values.
	//	 * @param results The collection to populates with the results.
	//	 * @return The given result collection.
	//	 */
	//	public static <T, C extends Collection<? super T>, R extends Collection<? super C>> R group(final CollectionFactory<T, C> factory, final int size, final Iterable<? extends T> values, final R results) {
	//		assert null != factory;
	//		assert null != values;
	//		assert null != results;
	//
	//		final Iterator<? extends T> valuesIt = values.iterator();
	//		while (valuesIt.hasNext()) {
	//			results.add(IteratorUtils.drain(valuesIt, size, factory.build(size)));
	//		}
	//		return results;
	//	}
	//
	//	// TODO: accumulator version
	//	/**
	//	 * Groups the elements of the given lists and populates the given result list with the pairs.
	//	 * <p>
	//	 * The pairs are formed with respect of the positions of the values in the lists. The extra values of the longest list are dropped when the lists don't have
	//	 * the same length.
	//	 *
	//	 * @param <T1> Type of the first values.
	//	 * @param <T2> Type of the second values.
	//	 * @param <L> Type of the result list.
	//	 * @param list1 The list containing the first values.
	//	 * @param list2 The list containing the second values.
	//	 * @param results The list to populate with the results.
	//	 * @return The given result list.
	//	 */
	//	public static <T1, T2, L extends List<? super Tuple2<T1, T2>>> L zip(final Iterable<? extends T1> list1, final Iterable<? extends T2> list2, final L results) {
	//		assert null != list1;
	//		assert null != list2;
	//
	//		return zip(list1.iterator(), list2.iterator(), results);
	//	}
	//
	//	/**
	//	 * Groups the elements provided by the given iterators and populates the given result list with the pairs.
	//	 * <p>
	//	 * The pairs are formed with respect of the order of the iterators. The extra values of the longest iterator are dropped when the given iterators don't
	//	 * provide the same number of elements.
	//	 *
	//	 * @param <T1> Type of the first values.
	//	 * @param <T2> Type of the second values.
	//	 * @param <L> Type of the result list.
	//	 * @param iterator1 The iterator providing the first values.
	//	 * @param iterator2 The iterator providing the second values.
	//	 * @param results The list to populate with the results.
	//	 * @return The given result list.
	//	 */
	//	public static <T1, T2, L extends List<? super Tuple2<T1, T2>>> L zip(final Iterator<? extends T1> iterator1, final Iterator<? extends T2> iterator2, final L results) {
	//		assert null != iterator1;
	//		assert null != iterator2;
	//		assert null != results;
	//
	//		while (iterator1.hasNext() && iterator2.hasNext()) {
	//			results.add(new Tuple2<T1, T2>(iterator1.next(), iterator2.next()));
	//		}
	//		return results;
	//	}
	//
	//	// TODO: accumulator version
	//	/**
	//	 * Decomposes the pairs of the given collection and populates the given collections with the first and second values.
	//	 *
	//	 * @param <T1> Type of the first values.
	//	 * @param <T2> Type of the second values.
	//	 * @param pairs The pairs.
	//	 * @param results1 The collection to populate with the first values.
	//	 * @param results2 The collection to populate with the second values.
	//	 */
	//	public static <T1, T2> void unzip(final Iterable<? extends Tuple2<T1, T2>> pairs, final Collection<? super T1> results1, final Collection<? super T2> results2) {
	//		assert null != pairs;
	//		assert null != results1;
	//		assert null != results2;
	//
	//		unzip(pairs.iterator(), results1, results2);
	//	}
	//
	//	/**
	//	 * Decomposes the pairs provided by the given iterator and populates the given collections with the first and second values.
	//	 *
	//	 * @param <T1> Type of the first values.
	//	 * @param <T2> Type of the second values.
	//	 * @param pairs The iterator.
	//	 * @param results1 The collection to populate with the first values.
	//	 * @param results2 The collection to populate with the second values.
	//	 */
	//	public static <T1, T2> void unzip(final Iterator<? extends Tuple2<T1, T2>> pairs, final Collection<? super T1> results1, final Collection<? super T2> results2) {
	//		assert null != pairs;
	//		assert null != results1;
	//		assert null != results2;
	//
	//		while (pairs.hasNext()) {
	//			final Tuple2<T1, T2> value = pairs.next();
	//			results1.add(value.get1());
	//			results2.add(value.get2());
	//		}
	//	}
	
	private CollectionUtils() {
		// Prevents instantiation.
	}
}
