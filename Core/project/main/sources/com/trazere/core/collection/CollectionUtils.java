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
package com.trazere.core.collection;

import com.trazere.core.functional.Function;
import com.trazere.core.functional.Predicate;
import com.trazere.core.functional.Predicates;
import com.trazere.core.imperative.Accumulator;
import com.trazere.core.imperative.Accumulators;
import com.trazere.core.imperative.ExIterator;
import com.trazere.core.imperative.IteratorUtils;
import com.trazere.core.lang.IterableUtils;
import com.trazere.core.lang.LangAccumulators;
import com.trazere.core.lang.PairIterable;
import com.trazere.core.util.Maybe;
import com.trazere.core.util.Tuple2;
import java.util.Collection;
import java.util.Iterator;

/**
 * The {@link CollectionUtils} class provides various utilities regarding the manipulation of {@link Collection collections}.
 * 
 * @see Collection
 * @since 2.0
 */
public class CollectionUtils {
	// TODO: rename
	/**
	 * Tests whether the given collections contain some common values.
	 * <p>
	 * This method iterates over the first collection and tests the presence of the values within the second collection. Therefore, providing a smaller first
	 * collection and a second collection with a faster {@link Collection#contains(Object)} method is more efficient.
	 * 
	 * @param <E> Type of the elements.
	 * @param collection1 First collection.
	 * @param collection2 Second collection.
	 * @return <code>true</code> if the collections intersect, <code>false</code> otherwise.
	 * @since 2.0
	 */
	public static <E> boolean intersects(final Collection<? extends E> collection1, final Collection<? extends E> collection2) {
		return IterableUtils.isAny(collection1, Predicates.values(collection2));
	}
	
	// TODO: add(Collection, Maybe)
	
	/**
	 * Adds all given elements to the given collection.
	 * <p>
	 * This method does modify the given collection.
	 *
	 * @param <E> Type of the elements.
	 * @param collection Collection to modify.
	 * @param elements Elements to add.
	 * @return <code>true</code> when the given collection is modified, <code>false</code> otherwise.
	 * @since 2.0
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
	 * @param elements Iterable providing the elements to add.
	 * @return <code>true</code> when the given collection is modified, <code>false</code> otherwise.
	 * @since 2.0
	 */
	public static <E> boolean addAll(final Collection<? super E> collection, final Iterable<? extends E> elements) {
		final Accumulator<Boolean, Boolean> changed = LangAccumulators.or(false);
		for (final E element : elements) {
			changed.add(collection.add(element));
		}
		return changed.get().booleanValue();
	}
	
	// TODO: generalize and move to IterableUtils
	/**
	 * Removes any element from the given collection.
	 * <p>
	 * The collection must support removal through its iterators.
	 * <p>
	 * This method does modify the given collection.
	 *
	 * @param <E> Type of the elements.
	 * @param collection Collection to modify.
	 * @return The removed element, or nothing when the collection is empty.
	 * @since 2.0
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
	
	// TODO: generalize and move to IterableUtils
	/**
	 * Removes any element of the given collection accepted by the given filter.
	 * <p>
	 * The collection must support removal through its iterators.
	 * <p>
	 * This method does modify the given collection.
	 * 
	 * @param <E> Type of the elements.
	 * @param collection Collection of the elements to filter.
	 * @param filter Predicate to use to filter the elements.
	 * @return The removed element, or nothing when no elements are accepted by the filter or when the collection is empty.
	 * @since 2.0
	 */
	public static <E> Maybe<E> removeAny(final Collection<? extends E> collection, final Predicate<? super E> filter) {
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
	 * Removes all given elements from the given collection.
	 * <p>
	 * This method does modify the given collection.
	 *
	 * @param <E> Type of the elements.
	 * @param collection Collection to modify.
	 * @param elements Elements to remove.
	 * @return <code>true</code> when the given collection is modified, <code>false</code> otherwise.
	 * @since 2.0
	 */
	@SafeVarargs
	public static <E> boolean removeAll(final Collection<? super E> collection, final E... elements) {
		final Accumulator<Boolean, Boolean> changed = LangAccumulators.or(false);
		for (final E element : elements) {
			changed.add(collection.remove(element));
		}
		return changed.get().booleanValue();
	}
	
	/**
	 * Removes all given elements from the given collection.
	 * <p>
	 * This method does modify the given collection.
	 *
	 * @param <E> Type of the elements.
	 * @param collection Collection to modify.
	 * @param elements Iterable providing the elements to remove.
	 * @return <code>true</code> when the given collection is modified, <code>false</code> otherwise.
	 * @since 2.0
	 */
	public static <E> boolean removeAll(final Collection<? super E> collection, final Iterable<? extends E> elements) {
		final Accumulator<Boolean, Boolean> changed = LangAccumulators.or(false);
		for (final E element : elements) {
			changed.add(collection.remove(element));
		}
		return changed.get().booleanValue();
	}
	
	// TODO: generalize and move to IterableUtils
	/**
	 * Removes all elements accepted by the given filter from the given collection.
	 * <p>
	 * This method does modify the given collection.
	 *
	 * @param <E> Type of the elements.
	 * @param collection Collection to modify.
	 * @param filter Predicate to use to filter the elements to remove.
	 * @return <code>true</code> when the given collection is modified, <code>false</code> otherwise.
	 * @since 2.0
	 */
	public static <E> boolean removeAll(final Collection<? extends E> collection, final Predicate<? super E> filter) {
		final Accumulator<Boolean, Boolean> changed = LangAccumulators.or(false);
		final Iterator<? extends E> elements = collection.iterator();
		while (elements.hasNext()) {
			if (filter.evaluate(elements.next())) {
				elements.remove();
				changed.add(true);
			}
		}
		return changed.get().booleanValue();
	}
	
	// TODO: generalize and move to IterableUtils
	/**
	 * Retains the elements accepted by the given filter in the given collection.
	 * <p>
	 * This method does modify the given collection.
	 *
	 * @param <E> Type of the elements.
	 * @param collection Collection to filter.
	 * @param filter Predicate to use to filter the elements to retain.
	 * @return <code>true</code> when the given collection is modified, <code>false</code> otherwise.
	 * @since 2.0
	 */
	public static <E> boolean retainAll(final Collection<? extends E> collection, final Predicate<? super E> filter) {
		final Accumulator<Boolean, Boolean> changed = LangAccumulators.or(false);
		final Iterator<? extends E> elements = collection.iterator();
		while (elements.hasNext()) {
			if (!filter.evaluate(elements.next())) {
				elements.remove();
				changed.add(true);
			}
		}
		return changed.get().booleanValue();
	}
	
	/**
	 * Takes n elements of the given collection.
	 * <p>
	 * The elements are taken according their iteration order.
	 *
	 * @param <E> Type of the elements.
	 * @param collection Collection of the elements to take.
	 * @param n Number of elements to take.
	 * @return A collection of the taken elements.
	 * @since 2.0
	 */
	public static <E> ExCollection<E> take(final Collection<? extends E> collection, final int n) {
		assert null != collection;
		
		return new AbstractExCollection<E>() {
			@Override
			public int size() {
				return Math.min(collection.size(), n);
			}
			
			@Override
			public ExIterator<E> iterator() {
				return IteratorUtils.take(collection.iterator(), n);
			}
		};
	}
	
	/**
	 * Takes n elements of the given collection.
	 * <p>
	 * The elements are taken according their iteration order.
	 *
	 * @param <E> Type of the elements.
	 * @param <C> Type of the result collection.
	 * @param collection Collection of the elements to take.
	 * @param n Number of elements to take.
	 * @param resultFactory Factory of the result collection.
	 * @return A new collection of the taken elements.
	 * @since 2.0
	 */
	public static <E, C extends Collection<? super E>> C take(final Collection<? extends E> collection, final int n, final CollectionFactory<? super E, C> resultFactory) {
		final ExCollection<E> elements = take(collection, n);
		return elements.iterator().drain(resultFactory.build(elements.size()));
	}
	
	/**
	 * Drops n elements of the given collection.
	 * <p>
	 * The elements are dropped according their iteration order.
	 *
	 * @param <E> Type of the elements.
	 * @param collection Collection of the elements to drop.
	 * @param n Number of elements to drop.
	 * @return A collection of the remaining elements.
	 * @since 2.0
	 */
	public static <E> ExCollection<E> drop(final Collection<? extends E> collection, final int n) {
		assert null != collection;
		
		return new AbstractExCollection<E>() {
			@Override
			public int size() {
				return Math.max(collection.size() - n, 0);
			}
			
			@Override
			public ExIterator<E> iterator() {
				return IteratorUtils.drop(collection.iterator(), n);
			}
		};
	}
	
	/**
	 * Drops n elements of the given collection.
	 * <p>
	 * The elements are dropped according their iteration order.
	 *
	 * @param <E> Type of the elements.
	 * @param <C> Type of the result collection.
	 * @param collection Collection of the elements to drop.
	 * @param n Number of elements to drop.
	 * @param resultFactory Factory of the result collection.
	 * @return A new collection of the remaining elements.
	 * @since 2.0
	 */
	public static <E, C extends Collection<? super E>> C drop(final Collection<? extends E> collection, final int n, final CollectionFactory<? super E, C> resultFactory) {
		final ExCollection<E> elements = drop(collection, n);
		return elements.iterator().drain(resultFactory.build(elements.size()));
	}
	
	/**
	 * Groups the elements of the given collection into batches of the given size.
	 *
	 * @param <E> Type of the elements.
	 * @param <B> Type of the batch collections.
	 * @param collection Collection of the elements to group.
	 * @param n Number of elements of each batch.
	 * @param batchFactory Factory of the batch collections.
	 * @return A collection of the batches of elements.
	 * @since 2.0
	 */
	public static <E, B extends Collection<? super E>> ExCollection<B> group(final Collection<? extends E> collection, final int n, final CollectionFactory<? super E, B> batchFactory) {
		assert null != collection;
		assert null != batchFactory;
		
		return new AbstractExCollection<B>() {
			@Override
			public int size() {
				return (collection.size() + n - 1) / n;
			}
			
			@Override
			public ExIterator<B> iterator() {
				return IteratorUtils.group(collection.iterator(), n, batchFactory);
			}
		};
	}
	
	/**
	 * Groups the elements of the given collection into batches of the given size.
	 *
	 * @param <E> Type of the elements.
	 * @param <B> Type of the batch collections.
	 * @param <C> Type of the result collection.
	 * @param collection Collection of the elements to group.
	 * @param n Number of elements of each batch.
	 * @param batchFactory Factory of the batch collections.
	 * @param resultFactory Factory of the result collection.
	 * @return A new collection of the batches of elements.
	 * @since 2.0
	 */
	public static <E, B extends Collection<? super E>, C extends Collection<? super B>> C group(final Collection<? extends E> collection, final int n, final CollectionFactory<? super E, B> batchFactory, final CollectionFactory<? super B, C> resultFactory) {
		final ExCollection<B> groups = group(collection, n, batchFactory);
		return groups.iterator().drain(resultFactory.build(groups.size()));
	}
	
	/**
	 * Filters the elements of the given collection using the given filter.
	 *
	 * @param <E> Type of the elements.
	 * @param collection Collection of the elements to filter.
	 * @param filter Predicate to use to filter the elements.
	 * @return A collection of the filtered elements.
	 * @since 2.0
	 */
	public static <E> ExCollection<E> filter(final Collection<? extends E> collection, final Predicate<? super E> filter) {
		assert null != collection;
		assert null != filter;
		
		return new AbstractExCollection<E>() {
			@Override
			public int size() {
				return IterableUtils.count(collection, filter);
			}
			
			@Override
			public ExIterator<E> iterator() {
				return IteratorUtils.filter(collection.iterator(), filter);
			}
		};
	}
	
	/**
	 * Filters the elements of the given collection using the given filter.
	 *
	 * @param <E> Type of the elements.
	 * @param <C> Type of the result collection.
	 * @param collection Collection of the elements to filter.
	 * @param filter Predicate to use to filter the elements.
	 * @param resultFactory Factory of the result collection.
	 * @return A new collection of the filtered elements.
	 * @since 2.0
	 */
	public static <E, C extends Collection<? super E>> C filter(final Collection<? extends E> collection, final Predicate<? super E> filter, final CollectionFactory<? super E, C> resultFactory) {
		return filter(collection, filter).iterator().drain(resultFactory.build());
	}
	
	/**
	 * Transforms the elements of the given collection using the given function.
	 *
	 * @param <E> Type of the elements.
	 * @param <TE> Type of the transformed elements.
	 * @param collection Collection of the elements to transform.
	 * @param function Function to use to transform the elements.
	 * @return A collection of the transformed elements.
	 * @since 2.0
	 */
	public static <E, TE> ExCollection<TE> map(final Collection<? extends E> collection, final Function<? super E, ? extends TE> function) {
		assert null != collection;
		assert null != function;
		
		return new AbstractExCollection<TE>() {
			@Override
			public int size() {
				return collection.size();
			}
			
			@Override
			public ExIterator<TE> iterator() {
				return IteratorUtils.map(collection.iterator(), function);
			}
		};
	}
	
	/**
	 * Transforms the elements of the given collection using the given function.
	 *
	 * @param <E> Type of the elements.
	 * @param <TE> Type of the transformed elements.
	 * @param <C> Type of the result collection.
	 * @param collection Collection of the elements to transform.
	 * @param function Function to use to transform the elements.
	 * @param resultFactory Factory of the result collection.
	 * @return A new collection of the transformed elements.
	 * @since 2.0
	 */
	public static <E, TE, C extends Collection<? super TE>> C map(final Collection<? extends E> collection, final Function<? super E, ? extends TE> function, final CollectionFactory<? super TE, C> resultFactory) {
		return IteratorUtils.map(collection.iterator(), function).drain(resultFactory.build(collection.size()));
	}
	
	/**
	 * Extracts the elements from the elements of the given collection using the given extractor.
	 *
	 * @param <E> Type of the elements.
	 * @param <EE> Type of the extracted elements.
	 * @param collection Collection of the elements to extract from.
	 * @param extractor Function to use to extract from the elements.
	 * @return A collection of the extracted elements.
	 * @since 2.0
	 */
	public static <E, EE> ExCollection<EE> extract(final Collection<? extends E> collection, final Function<? super E, ? extends Maybe<? extends EE>> extractor) {
		assert null != collection;
		assert null != extractor;
		
		return new AbstractExCollection<EE>() {
			@Override
			public int size() {
				return IteratorUtils.count(collection.iterator(), e -> extractor.evaluate(e).isSome());
			}
			
			@Override
			public ExIterator<EE> iterator() {
				return IteratorUtils.extract(collection.iterator(), extractor);
			}
		};
	}
	
	/**
	 * Extracts the elements from the elements of the given collection using the given extractor.
	 *
	 * @param <E> Type of the elements.
	 * @param <EE> Type of the extracted elements.
	 * @param <C> Type of the result collection.
	 * @param collection Collection of the elements to extract from.
	 * @param extractor Function to use to extract from the elements.
	 * @param resultFactory Factory of the result collection.
	 * @return A new collection of the extracted elements.
	 * @since 2.0
	 */
	public static <E, EE, C extends Collection<? super EE>> C extract(final Collection<? extends E> collection, final Function<? super E, ? extends Maybe<? extends EE>> extractor, final CollectionFactory<? super EE, C> resultFactory) {
		return IteratorUtils.extract(collection.iterator(), extractor).drain(resultFactory.build());
	}
	
	/**
	 * Gets all elements extracted from the elements of the given collection using the given extractor.
	 * 
	 * @param <E> Type of the elements.
	 * @param <EE> Type of the extracted elements.
	 * @param collection Collection of the elements to extract from.
	 * @param extractor Function to use to extract from the elements.
	 * @return A collection of the extracted elements.
	 * @since 2.0
	 */
	public static <E, EE> ExCollection<EE> extractAll(final Collection<? extends E> collection, final Function<? super E, ? extends Iterable<? extends EE>> extractor) {
		assert null != collection;
		assert null != extractor;
		
		return new AbstractExCollection<EE>() {
			@Override
			public int size() {
				final Accumulator<EE, Integer> size = Accumulators.counter();
				for (final E element : collection) {
					size.addAll(extractor.evaluate(element));
				}
				return size.get().intValue();
			}
			
			@Override
			public ExIterator<EE> iterator() {
				return IteratorUtils.extractAll(collection.iterator(), extractor);
			}
		};
	}
	
	/**
	 * Gets all elements extracted from the elements of the given collection using the given extractor.
	 * 
	 * @param <E> Type of the elements.
	 * @param <EE> Type of the extracted elements.
	 * @param <C> Type of the result collection.
	 * @param collection Collection of the elements to extract from.
	 * @param extractor Function to use to extract from the elements.
	 * @param resultFactory Factory of the result collection.
	 * @return A new collection of the extracted elements.
	 * @since 2.0
	 */
	public static <E, EE, C extends Collection<? super EE>> C extractAll(final Collection<? extends E> collection, final Function<? super E, ? extends Iterable<? extends EE>> extractor, final CollectionFactory<? super EE, C> resultFactory) {
		return IteratorUtils.extractAll(collection.iterator(), extractor).drain(resultFactory.build());
	}
	
	/**
	 * Appends the given collections together.
	 * 
	 * @param <E> Type of the elements.
	 * @param collection1 First collection of the elements to append.
	 * @param collection2 Second collection of the elements to append.
	 * @return A collection of the appended elements.
	 * @since 2.0
	 */
	public static <E> ExCollection<E> append(final Collection<? extends E> collection1, final Collection<? extends E> collection2) {
		assert null != collection1;
		assert null != collection2;
		
		return new AbstractExCollection<E>() {
			@Override
			public int size() {
				return collection1.size() + collection2.size();
			}
			
			@Override
			public ExIterator<E> iterator() {
				return IteratorUtils.append(collection1.iterator(), collection2.iterator());
			}
		};
	}
	
	/**
	 * Appends the given collections together.
	 * 
	 * @param <E> Type of the elements.
	 * @param <C> Type of the result collection.
	 * @param collection1 First collection of the elements to append.
	 * @param collection2 Second collection of the elements to append.
	 * @param resultFactory Factory of the result collection.
	 * @return A new collection of the appended elements.
	 * @since 2.0
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
	 * @param collection Collection of the collections of the elements to flatten.
	 * @return A collection of the flatten elements.
	 * @since 2.0
	 */
	public static <E> ExCollection<E> flatten(final Collection<? extends Collection<? extends E>> collection) {
		assert null != collection;
		
		return new AbstractExCollection<E>() {
			@Override
			public int size() {
				return IterableUtils.fold(collection, (s, l) -> s + l.size(), 0);
			}
			
			@Override
			public ExIterator<E> iterator() {
				return IteratorUtils.flatten(IterableUtils.map(collection, Iterable::iterator).iterator());
			}
		};
	}
	
	/**
	 * Flattens the elements of the collections contained in the given collection.
	 *
	 * @param <E> Type of the elements.
	 * @param <C> Type of the result collection.
	 * @param collection Collection of the collections of the elements to flatten.
	 * @param resultFactory Factory of the result collection.
	 * @return A new collection of the flatten elements.
	 * @since 2.0
	 */
	public static <E, C extends Collection<? super E>> C flatten(final Collection<? extends Collection<? extends E>> collection, final CollectionFactory<? super E, C> resultFactory) {
		final C results = resultFactory.build();
		for (final Collection<? extends E> elements : collection) {
			results.addAll(elements);
		}
		return results;
	}
	
	/**
	 * Transforms and flattens the elements of the given collection using the given function.
	 * 
	 * @param <E> Type of the elements.
	 * @param <TE> Type of the transformed elements.
	 * @param collection Collection of the elements to transform.
	 * @param function Function to use to transform the elements.
	 * @return A collection of the flatten, transformed elements.
	 * @since 2.0
	 */
	public static <E, TE> ExCollection<TE> flatMap(final Collection<? extends E> collection, final Function<? super E, ? extends Collection<? extends TE>> function) {
		assert null != collection;
		assert null != function;
		
		return new AbstractExCollection<TE>() {
			@Override
			public int size() {
				final Accumulator<Integer, Integer> size = LangAccumulators.sum(0);
				for (final E element : collection) {
					size.add(function.evaluate(element).size());
				}
				return size.get().intValue();
			}
			
			@Override
			public ExIterator<TE> iterator() {
				return IteratorUtils.flatMap(collection.iterator(), element -> function.evaluate(element).iterator());
			}
		};
	}
	
	/**
	 * Transforms and flattens the elements of the given collection using the given function.
	 * 
	 * @param <E> Type of the elements.
	 * @param <TE> Type of the transformed elements.
	 * @param <C> Type of the result collection.
	 * @param collection Collection of the elements to transform.
	 * @param function Function to use to transform the elements.
	 * @param resultFactory Factory of the result collection.
	 * @return A new collection of the flatten, transformed elements.
	 * @since 2.0
	 */
	public static <E, TE, C extends Collection<? super TE>> C flatMap(final Collection<? extends E> collection, final Function<? super E, ? extends Collection<? extends TE>> function, final CollectionFactory<? super TE, C> resultFactory) {
		return IteratorUtils.flatMap(collection.iterator(), arg -> function.evaluate(arg).iterator()).drain(resultFactory.build());
	}
	
	// TODO: intersect(Collection, Collection)
	
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
	 * @return A collection of the common elements.
	 * @since 2.0
	 */
	public static <E, C extends Collection<? super E>> C intersect(final Collection<? extends E> collection1, final Collection<? extends E> collection2, final CollectionFactory<? super E, C> resultFactory) {
		final C results = resultFactory.build();
		for (final E value : collection1) {
			if (collection2.contains(value)) {
				results.add(value);
			}
		}
		return results;
	}
	
	// TODO: exclude(Collection, Collection)
	
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
	 * @return A collection of the excluded elements.
	 * @since 2.0
	 */
	public static <E, C extends Collection<? super E>> C exclude(final Collection<? extends E> collection1, final Collection<? extends E> collection2, final CollectionFactory<? super E, C> resultFactory) {
		final C results = resultFactory.build();
		for (final E value : collection1) {
			if (!collection2.contains(value)) {
				results.add(value);
			}
		}
		return results;
	}
	
	/**
	 * Composes pairs with the elements of the given collections.
	 * <p>
	 * The pairs are composed of an element of each collection according to their iteration order. The extra values of the longest collection are dropped when
	 * the given collections don't contain the same number of elements.
	 * 
	 * @param <E1> Type of the first elements.
	 * @param <E2> Type of the second elements.
	 * @param collection1 Collection of the first elements of the pairs.
	 * @param collection2 Collection of the second elements of the pairs.
	 * @return A collection of the pairs of elements.
	 * @since 2.0
	 */
	public static <E1, E2> ExCollection<Tuple2<E1, E2>> zip(final Collection<? extends E1> collection1, final Collection<? extends E2> collection2) {
		assert null != collection1;
		assert null != collection2;
		
		return new AbstractExCollection<Tuple2<E1, E2>>() {
			@Override
			public int size() {
				return Math.min(collection1.size(), collection2.size());
			}
			
			@Override
			public ExIterator<Tuple2<E1, E2>> iterator() {
				return IteratorUtils.zip(collection1.iterator(), collection2.iterator());
			}
		};
	}
	
	/**
	 * Composes pairs with the elements of the given collections.
	 * <p>
	 * The pairs are composed of an element of each collection according to their iteration order. The extra values of the longest collection are dropped when
	 * the given collections don't contain the same number of elements.
	 * 
	 * @param <E1> Type of the first elements.
	 * @param <E2> Type of the second elements.
	 * @param <C> Type of the result collection.
	 * @param collection1 Collection of the first elements of the pairs.
	 * @param collection2 Collection of the second elements of the pairs.
	 * @param resultFactory Factory of the pair collection.
	 * @return A new collection of the pairs of elements.
	 * @since 2.0
	 */
	public static <E1, E2, C extends Collection<? super Tuple2<? extends E1, ? extends E2>>> C zip(final Collection<? extends E1> collection1, final Collection<? extends E2> collection2, final CollectionFactory<? super Tuple2<? extends E1, ? extends E2>, C> resultFactory) {
		return IteratorUtils.zip(collection1.iterator(), collection2.iterator()).drain(resultFactory.build(Math.min(collection1.size(), collection2.size())));
	}
	
	/**
	 * Decomposes the pairs of elements of the given collection.
	 *
	 * @param <E1> Type of the first elements.
	 * @param <E2> Type of the second elements.
	 * @param <C1> Type of the first result collection.
	 * @param <C2> Type of the second result collection.
	 * @param collection Collection of the pairs of elements.
	 * @param resultFactory1 Factory of the first element collection.
	 * @param resultFactory2 Factory of the second element collection.
	 * @return A collection of the first elements of the pairs and a collection of the second elements of the pairs.
	 * @since 2.0
	 */
	public static <E1, E2, C1 extends Collection<? super E1>, C2 extends Collection<? super E2>> Tuple2<C1, C2> unzip(final Collection<? extends Tuple2<E1, E2>> collection, final CollectionFactory<? super E1, C1> resultFactory1, final CollectionFactory<? super E2, C2> resultFactory2) {
		final C1 results1 = resultFactory1.build(collection.size());
		final C2 results2 = resultFactory2.build(collection.size());
		for (final Tuple2<E1, E2> pair : collection) {
			results1.add(pair.get1());
			results2.add(pair.get2());
		}
		return new Tuple2<>(results1, results2);
	}
	
	/**
	 * Builds an unmodifiable view of the given collection.
	 * 
	 * @param <E> Type of the elements.
	 * @param collection Collection to wrap.
	 * @return An unmodifiable view of the given collection, or the given collection when is it already unmodifiable.
	 * @since 2.0
	 */
	public static <E> ExCollection<E> unmodifiable(final Collection<E> collection) {
		assert null != collection;
		
		return collection instanceof UnmodifiableCollection<?> ? (UnmodifiableCollection<E>) collection : new UnmodifiableCollection<>(collection);
	}
	
	private static class UnmodifiableCollection<E>
	extends CollectionDecorator<E> {
		public UnmodifiableCollection(final Collection<E> decorated) {
			super(decorated);
		}
		
		// Collection.
		
		@Override
		public boolean add(final E e) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public boolean remove(final Object o) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public boolean addAll(final Collection<? extends E> c) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public boolean removeAll(final Collection<?> c) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public boolean removeIf(final java.util.function.Predicate<? super E> filter) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public boolean retainAll(final Collection<?> c) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public void clear() {
			throw new UnsupportedOperationException();
		}
		
		// ExCollection.
		
		@Override
		public boolean addAll(@SuppressWarnings("unchecked") final E... elements) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public boolean addAll(final Iterable<? extends E> elements) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public Maybe<E> removeAny() {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public Maybe<E> removeAny(final Predicate<? super E> filter) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public boolean removeAll(@SuppressWarnings("unchecked") final E... elements) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public boolean removeAll(final Iterable<? extends E> elements) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public boolean removeAll(final Predicate<? super E> filter) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public boolean retainAll(final Predicate<? super E> filter) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public <E2> ExCollection<Tuple2<E, E2>> zip(final Collection<? extends E2> collection2) {
			return super.<E2>zip(collection2).unmodifiable();
		}
		
		// Iterable.
		
		@Override
		public ExIterator<E> iterator() {
			return IteratorUtils.unmodifiable(_decorated.iterator());
		}
		
		// ExIterable.
		
		@Override
		public <E2> PairIterable<E, E2> zip(final Iterable<? extends E2> iterable2) {
			return super.<E2>zip(iterable2).unmodifiable();
		}
		
		// Traversable.
		
		@Override
		public ExCollection<E> take(final int n) {
			return super.take(n).unmodifiable();
		}
		
		@Override
		public ExCollection<E> drop(final int n) {
			return super.drop(n).unmodifiable();
		}
		
		@Override
		public <B extends Collection<? super E>> ExCollection<B> group(final int n, final CollectionFactory<? super E, B> batchFactory) {
			return super.group(n, batchFactory).unmodifiable();
		}
		
		@Override
		public ExCollection<E> filter(final Predicate<? super E> filter) {
			return super.filter(filter).unmodifiable();
		}
		
		@Override
		public <TE> ExCollection<TE> map(final Function<? super E, ? extends TE> function) {
			return super.<TE>map(function).unmodifiable();
		}
		
		@Override
		public <EE> ExCollection<EE> extract(final Function<? super E, ? extends Maybe<? extends EE>> extractor) {
			return super.<EE>extract(extractor).unmodifiable();
		}
		
		@Override
		public <EE> ExCollection<EE> extractAll(final Function<? super E, ? extends Iterable<? extends EE>> extractor) {
			return super.<EE>extractAll(extractor).unmodifiable();
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
	
	private CollectionUtils() {
		// Prevents instantiation.
	}
}
