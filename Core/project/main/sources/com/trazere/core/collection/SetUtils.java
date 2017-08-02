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

import com.trazere.core.functional.Predicate;
import com.trazere.core.imperative.ExIterator;
import com.trazere.core.imperative.ImperativePredicates;
import com.trazere.core.imperative.IteratorUtils;
import com.trazere.core.lang.IterableUtils;
import com.trazere.core.util.Maybe;
import com.trazere.core.util.Tuple2;
import java.util.Collection;
import java.util.Set;
import java.util.function.Function;

/**
 * The {@link SetUtils} class provides various utilities regarding {@link Set sets}.
 * 
 * @see Set
 * @since 2.0
 */
public class SetUtils {
	/**
	 * Takes n elements of the given set.
	 * <p>
	 * The elements are taken according their iteration order.
	 *
	 * @param <E> Type of the elements.
	 * @param set Set of the elements to take.
	 * @param n Number of elements to take.
	 * @return A set of the taken elements.
	 * @since 2.0
	 */
	public static <E> ExSet<E> take(final Set<? extends E> set, final int n) {
		assert null != set;
		
		return new AbstractExSet<E>() {
			@Override
			public int size() {
				return Math.min(set.size(), n);
			}
			
			@Override
			public ExIterator<E> iterator() {
				return IteratorUtils.take(set.iterator(), n);
			}
		};
	}
	
	/**
	 * Drops n elements of the given set.
	 * <p>
	 * The elements are dropped according their iteration order.
	 *
	 * @param <E> Type of the elements.
	 * @param set Set of the elements to drop.
	 * @param n Number of elements to drop.
	 * @return A set of the remaining elements.
	 * @since 2.0
	 */
	public static <E> ExSet<E> drop(final Set<? extends E> set, final int n) {
		assert null != set;
		
		return new AbstractExSet<E>() {
			@Override
			public int size() {
				return Math.max(set.size() - n, 0);
			}
			
			@Override
			public ExIterator<E> iterator() {
				return IteratorUtils.drop(set.iterator(), n);
			}
		};
	}
	
	/**
	 * Groups the elements of the given set into batches of the given size.
	 *
	 * @param <E> Type of the elements.
	 * @param <B> Type of the batch collections.
	 * @param set Set of the elements to group.
	 * @param n Number of elements of each batch.
	 * @param batchFactory Factory of the batch collections.
	 * @return A set of the batches of elements.
	 * @since 2.0
	 */
	public static <E, B extends Collection<? super E>> ExSet<B> group(final Set<? extends E> set, final int n, final CollectionFactory<? super E, B> batchFactory) {
		assert null != set;
		assert null != batchFactory;
		
		return new AbstractExSet<B>() {
			@Override
			public int size() {
				return (set.size() + n - 1) / n;
			}
			
			@Override
			public ExIterator<B> iterator() {
				return IteratorUtils.group(set.iterator(), n, batchFactory);
			}
		};
	}
	
	/**
	 * Filters the elements of the given set using the given filter.
	 *
	 * @param <E> Type of the elements.
	 * @param set Set of the elements to filter.
	 * @param filter Predicate to use to filter the elements.
	 * @return A set of the filtered elements.
	 * @since 2.0
	 */
	public static <E> ExSet<E> filter(final Set<? extends E> set, final Predicate<? super E> filter) {
		assert null != set;
		assert null != filter;
		
		return new AbstractExSet<E>() {
			@Override
			public int size() {
				return IterableUtils.count(set, filter);
			}
			
			@Override
			public ExIterator<E> iterator() {
				return IteratorUtils.filter(set.iterator(), filter);
			}
		};
	}
	
	/**
	 * Transforms the elements of the given set using the given function.
	 *
	 * @param <E> Type of the elements.
	 * @param <TE> Type of the transformed elements.
	 * @param set Set of the elements to transform.
	 * @param function Function to use to transform the elements.
	 * @return A set of the transformed elements.
	 * @since 2.0
	 */
	public static <E, TE> ExSet<TE> map(final Set<? extends E> set, final Function<? super E, ? extends TE> function) {
		assert null != set;
		assert null != function;
		
		return new AbstractExSet<TE>() {
			@Override
			public int size() {
				return IteratorUtils.map(set.iterator(), function).count(ImperativePredicates.normalizer());
			}
			
			@Override
			public ExIterator<TE> iterator() {
				return IteratorUtils.<E, TE>map(set.iterator(), function).filter(ImperativePredicates.normalizer());
			}
		};
	}
	
	/**
	 * Extracts the elements from the elements of the given set using the given extractor.
	 *
	 * @param <E> Type of the elements.
	 * @param <EE> Type of the extracted elements.
	 * @param set SEt of the elements to extract from.
	 * @param extractor Function to use to extract from the elements.
	 * @return A set of the extracted elements.
	 * @since 2.0
	 */
	public static <E, EE> ExSet<EE> extract(final Set<? extends E> set, final Function<? super E, ? extends Maybe<? extends EE>> extractor) {
		assert null != set;
		assert null != extractor;
		
		return new AbstractExSet<EE>() {
			@Override
			public int size() {
				return IteratorUtils.extract(set.iterator(), extractor).count(ImperativePredicates.normalizer());
			}
			
			@Override
			public ExIterator<EE> iterator() {
				return IteratorUtils.<E, EE>extract(set.iterator(), extractor).filter(ImperativePredicates.normalizer());
			}
		};
	}
	
	/**
	 * Gets all elements extracted from the elements of the given set using the given extractor.
	 * 
	 * @param <E> Type of the elements.
	 * @param <EE> Type of the extracted elements.
	 * @param set Set of the elements to extract from.
	 * @param extractor Function to use to extract from the elements.
	 * @return A set of the extracted elements.
	 * @since 2.0
	 */
	public static <E, EE> ExSet<EE> extractAll(final Set<? extends E> set, final Function<? super E, ? extends Iterable<? extends EE>> extractor) {
		assert null != set;
		assert null != extractor;
		
		return new AbstractExSet<EE>() {
			@Override
			public int size() {
				return IteratorUtils.extractAll(set.iterator(), extractor).count(ImperativePredicates.normalizer());
			}
			
			@Override
			public ExIterator<EE> iterator() {
				return IteratorUtils.<E, EE>extractAll(set.iterator(), extractor).filter(ImperativePredicates.normalizer());
			}
		};
	}
	
	/**
	 * Appends the given sets together.
	 * 
	 * @param <E> Type of the elements.
	 * @param set1 First set of the elements to append.
	 * @param set2 Second set of the elements to append.
	 * @return A set of the appended elements.
	 * @since 2.0
	 */
	public static <E> ExSet<E> append(final Set<? extends E> set1, final Set<? extends E> set2) {
		assert null != set1;
		assert null != set2;
		
		return new AbstractExSet<E>() {
			@Override
			public int size() {
				return IteratorUtils.append(set1.iterator(), set2.iterator()).count(ImperativePredicates.normalizer());
			}
			
			@Override
			public ExIterator<E> iterator() {
				return IteratorUtils.append(set1.iterator(), set2.iterator()).filter(ImperativePredicates.normalizer());
			}
		};
	}
	
	/**
	 * Flattens the elements of the sets contained in the given set.
	 *
	 * @param <E> Type of the elements.
	 * @param set Set of the sets of the elements to flatten.
	 * @return A set of the flatten elements.
	 * @since 2.0
	 */
	public static <E> ExSet<E> flatten(final Set<? extends Set<? extends E>> set) {
		assert null != set;
		
		return new AbstractExSet<E>() {
			@Override
			public int size() {
				return IteratorUtils.flatten(IterableUtils.map(set, Iterable::iterator).iterator()).count(ImperativePredicates.normalizer());
			}
			
			@Override
			public ExIterator<E> iterator() {
				return IteratorUtils.<E>flatten(IterableUtils.map(set, Iterable::iterator).iterator()).filter(ImperativePredicates.normalizer());
			}
		};
	}
	
	/**
	 * Transforms and flattens the elements of the given set using the given function.
	 * 
	 * @param <E> Type of the elements.
	 * @param <TE> Type of the transformed elements.
	 * @param set Set of the elements to transform.
	 * @param function Function to use to transform the elements.
	 * @return A set of the flatten, transformed elements.
	 * @since 2.0
	 */
	public static <E, TE> ExSet<TE> flatMap(final Set<? extends E> set, final Function<? super E, ? extends Set<? extends TE>> function) {
		assert null != set;
		assert null != function;
		
		return new AbstractExSet<TE>() {
			@Override
			public int size() {
				return IteratorUtils.flatMap(set.iterator(), element -> function.evaluate(element).iterator()).count(ImperativePredicates.normalizer());
			}
			
			@Override
			public ExIterator<TE> iterator() {
				return IteratorUtils.<E, TE>flatMap(set.iterator(), element -> function.evaluate(element).iterator()).filter(ImperativePredicates.normalizer());
			}
		};
	}
	
	// TODO: intersect
	// TODO: exclude
	
	/**
	 * Composes pairs with the elements of the given sets.
	 * <p>
	 * The pairs are composed of an element of each set according to their iteration order. The extra values of the longest set are dropped when the given sets
	 * don't contain the same number of elements.
	 * 
	 * @param <E1> Type of the first elements.
	 * @param <E2> Type of the second elements.
	 * @param set1 Set of the first elements of the pairs.
	 * @param set2 Set of the second elements of the pairs.
	 * @return A set of the pairs of elements.
	 * @since 2.0
	 */
	public static <E1, E2> ExSet<Tuple2<E1, E2>> zip(final Set<? extends E1> set1, final Set<? extends E2> set2) {
		assert null != set1;
		assert null != set2;
		
		return new AbstractExSet<Tuple2<E1, E2>>() {
			@Override
			public int size() {
				return Math.min(set1.size(), set2.size());
			}
			
			@Override
			public ExIterator<Tuple2<E1, E2>> iterator() {
				return IteratorUtils.zip(set1.iterator(), set2.iterator());
			}
		};
	}
	
	// TODO: unzip
	
	private SetUtils() {
		// Prevent instantiation.
	}
}
