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
import com.trazere.core.util.Maybe;
import com.trazere.core.util.Tuple2;
import java.util.Collection;
import java.util.Set;

/**
 * The {@link ExSet} interface defines extended {@link Set sets}.
 * 
 * @param <E> Type of the elements.
 * @since 2.0
 */
public interface ExSet<E>
extends Set<E>, ExCollection<E> {
	// TODO: move to Sets ?
	// TODO: rename -> fromSet ?
	/**
	 * Builds an extended view of the given set.
	 * 
	 * @param <E> Type of the elements.
	 * @param set Set to wrap.
	 * @return The extended view of the given set, or the given set when it is already an extended view.
	 * @since 2.0
	 */
	public static <E> ExSet<E> build(final Set<E> set) {
		assert null != set;
		
		if (set instanceof ExSet) {
			return (ExSet<E>) set;
		} else {
			return new SetDecorator<>(set);
		}
	}
	
	// Traversable.
	
	/**
	 * Takes n elements of this set.
	 * <p>
	 * The elements are taken according their iteration order.
	 *
	 * @return A set of the taken elements.
	 * @see SetUtils#take(Set, int)
	 * @since 2.0
	 */
	@Override
	default ExSet<E> take(final int n) {
		return SetUtils.take(this, n);
	}
	
	/**
	 * Drops n elements of this set.
	 * <p>
	 * The elements are dropped according their iteration order.
	 *
	 * @return A set of the remaining elements.
	 * @see SetUtils#drop(Set, int)
	 * @since 2.0
	 */
	@Override
	default ExSet<E> drop(final int n) {
		return SetUtils.drop(this, n);
	}
	
	/**
	 * Groups the elements of this set into batches of the given size.
	 *
	 * @return A set of the batches of elements.
	 * @see SetUtils#group(Set, int, CollectionFactory)
	 * @since 2.0
	 */
	@Override
	default <B extends Collection<? super E>> ExSet<B> group(final int n, final CollectionFactory<? super E, B> batchFactory) {
		return SetUtils.group(this, n, batchFactory);
	}
	
	/**
	 * Filters the elements of this set using the given filter.
	 *
	 * @return A set of the filtered elements.
	 * @see SetUtils#filter(Set, Predicate)
	 * @since 2.0
	 */
	@Override
	default ExSet<E> filter(final Predicate<? super E> filter) {
		return SetUtils.filter(this, filter);
	}
	
	/**
	 * Transforms the elements of this set using the given function.
	 * 
	 * @return A set of the transformed elements.
	 * @see SetUtils#map(Set, Function)
	 * @since 2.0
	 */
	@Override
	default <TE> ExSet<TE> map(final Function<? super E, ? extends TE> function) {
		return SetUtils.map(this, function);
	}
	
	/**
	 * Extracts the elements of this set using the given extractor.
	 *
	 * @return A set of the extracted elements.
	 * @see SetUtils#extract(Set, Function)
	 * @since 2.0
	 */
	@Override
	default <EE> ExSet<EE> extract(final Function<? super E, ? extends Maybe<? extends EE>> extractor) {
		return SetUtils.extract(this, extractor);
	}
	
	/**
	 * Gets all elements extracted from the elements of this set using the given extractor.
	 * 
	 * @param <EE> Type of the extracted elements.
	 * @param extractor Function to use to extract from the elements.
	 * @return A set of the extracted elements.
	 * @see SetUtils#extractAll(Set, Function)
	 * @since 2.0
	 */
	@Override
	default <EE> ExSet<EE> extractAll(final Function<? super E, ? extends Iterable<? extends EE>> extractor) {
		return SetUtils.extractAll(this, extractor);
	}
	
	// Note: flatMap is not defined here because Java does not support higher order type parameters.
	//	/**
	//	 * Transforms and flattens the elements of this set using the given function.
	//	 *
	//	 * @param <TE> Type of the transformed elements.
	//	 * @param function Function to use to transform the elements.
	//	 * @return A set of the flatten, transformed elements.
	//	 * @see SetUtils#flatMap(Set, Function)
	//	 * @since 2.0
	//	 */
	//	default <TE> ExSet<TE> flatMap(final Function<? super E, ? extends Set<? extends TE>> function) {
	//		return SetUtils.flatMap(this, function);
	//	}
	
	/**
	 * Composes pairs with the elements of this set and the given set.
	 * <p>
	 * The pairs are composed of an element of each set according to their iteration order. The extra values of the longest set are dropped when the given sets
	 * don't contain the same number of elements.
	 * 
	 * @param <E2> Type of the second elements.
	 * @param set2 Set of the second elements of the pairs.
	 * @return A set of the pairs of elements.
	 * @see SetUtils#zip(Set, Set)
	 * @since 2.0
	 */
	default <E2> ExSet<Tuple2<E, E2>> zip(final Set<? extends E2> set2) {
		return SetUtils.zip(this, set2);
	}
	
	// Misc.
	
	/**
	 * Builds an unmodifiable view of this set.
	 * 
	 * @return An unmodifiable view of this set, or this set when is it already unmodifiable.
	 * @see SetUtils#unmodifiable(Set)
	 * @since 2.0
	 */
	@Override
	default ExSet<E> unmodifiable() {
		return SetUtils.unmodifiable(this);
	}
}
