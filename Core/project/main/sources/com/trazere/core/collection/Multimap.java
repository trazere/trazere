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
import com.trazere.core.functional.Function2;
import com.trazere.core.functional.Function3;
import com.trazere.core.functional.Predicate;
import com.trazere.core.functional.Predicate2;
import com.trazere.core.functional.PredicateUtils;
import com.trazere.core.imperative.Accumulator;
import com.trazere.core.imperative.ExIterator;
import com.trazere.core.imperative.Procedure;
import com.trazere.core.imperative.Procedure2;
import com.trazere.core.lang.LangAccumulators;
import com.trazere.core.lang.PairIterable;
import com.trazere.core.lang.PairTraversable;
import com.trazere.core.util.FieldComparators;
import com.trazere.core.util.Maybe;
import com.trazere.core.util.Tuple2;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * The {@link Multimap} interface defines collections of bindings where each keys can be associated to a collection of values.
 * <p>
 * This data structure is similar to {@link Map}s but allows to associate multiple values to a same key.
 * <p>
 * The values associated to a each key are hold inside collections. The semantics of those collections therefore impact the semantics of the operations of the
 * multimap.
 * <p>
 * The implementations may have restrictions on the keys they may contain. For example, <code>null</code> keys may be prohibited or restricted to certains
 * types. Similarily, the values may be restricted as well according the type of the collection holding them.
 * 
 * @param <K> Type of the keys.
 * @param <V> Type of the values.
 * @param <C> Type of the collections of values.
 * @since 2.0
 */
public interface Multimap<K, V, C extends Collection<V>>
extends PairTraversable<K, V> {
	// Multimap.
	
	/**
	 * Gets a view of the keys of the bindings of this multimap.
	 * 
	 * @return An unmodifiable set of the keys.
	 * @since 2.0
	 */
	ExSet<K> keys();
	
	/**
	 * Gets a view of the values of this multimap.
	 * 
	 * @return An unmodifiable collection of the values.
	 * @since 2.0
	 */
	ExCollection<V> values();
	
	/**
	 * Gets a view of the bindings corresponding to this multimap.
	 * 
	 * @return The bindings.
	 * @see MapUtils#bindings(Map)
	 * @since 2.0
	 */
	// TODO: PairCollection
	PairIterable<K, V> bindings();
	
	/**
	 * Gets a view of collection bindings of this multimap.
	 * 
	 * @return An unmodifiable set of the entries of the values.
	 * @since 2.0
	 */
	// TODO: ExSet & PairIterable => BindingSet ?
	// TODO: PairCollection
	// TODO: ExMap
	PairIterable<K, C> collectionBindings();
	
	/**
	 * Gets the number of bindings of this multimap.
	 * 
	 * @return The number of bindings.
	 * @since 2.0
	 */
	int size();
	
	/**
	 * Indicates whether this multimap is empty or not.
	 * <p>
	 * A multimap is empty when it does not contain any binding.
	 * 
	 * @return <code>true</code> if the multimap is empty, <code>false</code> otherwise.
	 * @since 2.0
	 */
	boolean isEmpty();
	
	/**
	 * Tests whether this multimap contains some binding with the given key.
	 * 
	 * @param key Key to test.
	 * @return <code>true</code> when the multimap contains bindings with the given key, <code>false</code> otherwise.
	 * @since 2.0
	 */
	boolean containsKey(K key);
	
	/**
	 * Tests whether this multimap contains some binding with the given value.
	 * 
	 * @param value Value to test.
	 * @return <code>true</code> when the multimap contains bindings with the given value, <code>false</code> otherwise.
	 * @since 2.0
	 */
	boolean containsValue(V value);
	
	/**
	 * Tests whether this multimap contains the given binding.
	 * 
	 * @param key Key of the binding to test.
	 * @param value Value of the binding to test.
	 * @return <code>true</code> when the multimap contains the binding, <code>false</code> otherwise.
	 * @since 2.0
	 */
	boolean contains(K key, V value);
	
	/**
	 * Gets any binding from this multimap.
	 *
	 * @return A binding of the multimap.
	 * @throws NoSuchElementException When the multimap is empty.
	 * @since 2.0
	 */
	default Tuple2<K, V> any()
	throws NoSuchElementException {
		return bindings().any();
	}
	
	/**
	 * Gets any binding from this multimap.
	 * <p>
	 * This methods supports empty multimaps.
	 *
	 * @return A binding of the multimap, or nothing when the multimap is empty.
	 * @since 2.0
	 */
	default Maybe<Tuple2<K, V>> optionalAny() {
		return bindings().optionalAny();
	}
	
	/**
	 * Gets the values of the bindings with the given key of this multimap.
	 * 
	 * @param key Key of the bindings to get.
	 * @return An unmodifiable collection of the values.
	 * @since 2.0
	 */
	C get(K key);
	
	/**
	 * Puts the binding corresponding to the given key and value in this multimap.
	 * <p>
	 * The current bindings of the given key are preserved.
	 * 
	 * @param key Key of the binding to put.
	 * @param value Value of the binding to put.
	 * @return <code>true</code> when the multimap has changed, <code>false</code> otherwise.
	 * @since 2.0
	 */
	boolean put(K key, V value);
	
	/**
	 * Puts the given binding in this multimap.
	 * <p>
	 * The current bindings of the key of the given binding are preserved.
	 * 
	 * @param binding Binding to put.
	 * @return <code>true</code> when the multimap has changed, <code>false</code> otherwise.
	 */
	default boolean put(final Tuple2<? extends K, ? extends V> binding) {
		return put(binding.get1(), binding.get2());
	}
	
	/**
	 * Puts bindings corresponding to the given key and values in this multimap.
	 * <p>
	 * The current bindings of the given key are preserved.
	 * 
	 * @param key Key of the bindings to put.
	 * @param values Values of the bindings to put.
	 * @return <code>true</code> when the multimap has changed, <code>false</code> otherwise.
	 * @since 2.0
	 */
	default boolean putAll(final K key, @SuppressWarnings("unchecked") final V... values) {
		final Accumulator<Boolean, Boolean> changed = LangAccumulators.or(false);
		for (final V value : values) {
			changed.add(put(key, value));
		}
		return changed.get();
	}
	
	/**
	 * Puts bindings corresponding to the given key and values in this multimap.
	 * <p>
	 * The current bindings of the given key are preserved.
	 * 
	 * @param key Key of the bindings to put.
	 * @param values Values of the bindings to put.
	 * @return <code>true</code> when the multimap has changed, <code>false</code> otherwise.
	 * @since 2.0
	 */
	default boolean putAll(final K key, final Iterable<? extends V> values) {
		final Accumulator<Boolean, Boolean> changed = LangAccumulators.or(false);
		for (final V value : values) {
			changed.add(put(key, value));
		}
		return changed.get();
	}
	
	/**
	 * Puts the given bindings in this multimap.
	 * <p>
	 * The current bindings of the given key are preserved.
	 * 
	 * @param bindings Bindings to put.
	 * @return <code>true</code> when the multimap has changed, <code>false</code> otherwise.
	 */
	default boolean putAll(@SuppressWarnings("unchecked") final Tuple2<? extends K, ? extends V>... bindings) {
		final Accumulator<Boolean, Boolean> changed = LangAccumulators.or(false);
		for (final Tuple2<? extends K, ? extends V> binding : bindings) {
			changed.add(put(binding));
		}
		return changed.get();
	}
	
	/**
	 * Puts the given bindings in this multimap.
	 * <p>
	 * The current bindings of the given key are preserved.
	 * 
	 * @param bindings Bindings to put.
	 * @return <code>true</code> when the multimap has changed, <code>false</code> otherwise.
	 */
	default boolean putAll(final Iterable<? extends Tuple2<? extends K, ? extends V>> bindings) {
		final Accumulator<Boolean, Boolean> changed = LangAccumulators.or(false);
		for (final Tuple2<? extends K, ? extends V> binding : bindings) {
			changed.add(put(binding));
		}
		return changed.get();
	}
	
	/**
	 * Puts the bindings of the given map in this multimap.
	 * <p>
	 * The current bindings are preserved.
	 * 
	 * @param map Map containing the bindings to put.
	 * @return <code>true</code> when the multimap has changed, <code>false</code> otherwise.
	 * @since 2.0
	 */
	default boolean putAll(final Map<? extends K, ? extends V> map) {
		return putAll(MapUtils.bindings(map));
	}
	
	/**
	 * Puts the bindings of the given multimap in this multimap.
	 * <p>
	 * The current bindings are preserved.
	 * 
	 * @param multimap Multimap containing the bindings to put.
	 * @return <code>true</code> when the multimap has changed, <code>false</code> otherwise.
	 * @since 2.0
	 */
	default boolean putAll(final Multimap<? extends K, ? extends V, ?> multimap) {
		return putAll(multimap.bindings());
	}
	
	/**
	 * Removes all bindings with the given key from this multimap.
	 * 
	 * @param key Key of the bindings to remove.
	 * @return An unmodifiable collection of the removed values.
	 * @since 2.0
	 */
	C removeKey(K key);
	
	/**
	 * Removes all bindings with the given value from this multimap.
	 * 
	 * @param value Value of the binding to remove.
	 * @return <code>true</code> when the multimap has changed, <code>false</code> otherwise.
	 * @since 2.0
	 */
	boolean removeValue(V value);
	
	/**
	 * Removes the binding correspong to the given key and value from this multimap.
	 * 
	 * @param key Key of the binding to remove.
	 * @param value Value of the binding to remove.
	 * @return <code>true</code> when the multimap has changed, <code>false</code> otherwise.
	 * @since 2.0
	 */
	boolean remove(K key, V value);
	
	/**
	 * Removes the given binding from this multimap.
	 * 
	 * @param binding Binding to remove.
	 * @return <code>true</code> when the multimap has changed, <code>false</code> otherwise.
	 * @since 2.0
	 */
	default boolean remove(final Tuple2<? extends K, ? extends V> binding) {
		return remove(binding.get1(), binding.get2());
	}
	
	// TODO: removeAny()
	// TODO: removeAny(K)
	// TODO: removeAny(Predicate)
	
	/**
	 * Removes the bindings corresponding to the given key and values from this multimap.
	 * 
	 * @param key Key of the bindings to remove.
	 * @param values Values of the bindings to remove.
	 * @return <code>true</code> when the multimap has changed, <code>false</code> otherwise.
	 * @since 2.0
	 */
	default boolean removeAll(final K key, @SuppressWarnings("unchecked") final V... values) {
		final Accumulator<Boolean, Boolean> changed = LangAccumulators.or(false);
		for (final V value : values) {
			changed.add(remove(key, value));
		}
		return changed.get();
	}
	
	/**
	 * Removes the bindings corresponding to the given key and values from this multimap.
	 * 
	 * @param key Key of the bindings to remove.
	 * @param values Values of the bindings to remove.
	 * @return <code>true</code> when the multimap has changed, <code>false</code> otherwise.
	 * @since 2.0
	 */
	default boolean removeAll(final K key, final Iterable<? extends V> values) {
		final Accumulator<Boolean, Boolean> changed = LangAccumulators.or(false);
		for (final V value : values) {
			changed.add(remove(key, value));
		}
		return changed.get();
	}
	
	/**
	 * Removes the given bindings from this multimap.
	 * 
	 * @param bindings Bindings to remove.
	 * @return <code>true</code> when the multimap has changed, <code>false</code> otherwise.
	 * @since 2.0
	 */
	default boolean removeAll(@SuppressWarnings("unchecked") final Tuple2<? extends K, ? extends V>... bindings) {
		final Accumulator<Boolean, Boolean> changed = LangAccumulators.or(false);
		for (final Tuple2<? extends K, ? extends V> binding : bindings) {
			changed.add(remove(binding));
		}
		return changed.get();
	}
	
	/**
	 * Removes the given bindings from this multimap.
	 * 
	 * @param bindings Bindings to remove.
	 * @return <code>true</code> when the multimap has changed, <code>false</code> otherwise.
	 * @since 2.0
	 */
	default boolean removeAll(final Iterable<? extends Tuple2<? extends K, ? extends V>> bindings) {
		final Accumulator<Boolean, Boolean> changed = LangAccumulators.or(false);
		for (final Tuple2<? extends K, ? extends V> binding : bindings) {
			changed.add(remove(binding));
		}
		return changed.get();
	}
	
	// TODO: removeAll(Predicate)
	// TODO: removeAll(Predicate2)
	// TODO: removeAll(Map)
	// TODO: removeAll(Multimap)
	
	// TODO: retainAll(Predicate)
	// TODO: retainAll(Predicate2)
	
	/**
	 * Clears this multimap.
	 * 
	 * @since 2.0
	 */
	void clear();
	
	// TODO: append
	
	/**
	 * Builds an unmodifiable view of this multimap.
	 * 
	 * @return An unmodifiable view of this multimap, or this multimap when is it already unmodifiable.
	 * @see MultimapUtils#unmodifiable(Multimap)
	 * @since 2.0
	 */
	default Multimap<K, V, C> unmodifiable() {
		return MultimapUtils.unmodifiable(this);
	}
	
	// PairTraversable.
	
	/**
	 * Left folds over the bindings of this multimap using the given operator and initial state.
	 * <p>
	 * The bindings are folded according their iteration order.
	 * 
	 * @since 2.0
	 */
	@Override
	default <S> S fold2(final Function3<? super S, ? super K, ? super V, ? extends S> operator, final S initialState) {
		return bindings().fold2(operator, initialState);
	}
	
	/**
	 * Tests whether any binding of this multimap is accepted by the given filter.
	 * 
	 * @param filter Predicate to use to filter the binding.
	 * @return <code>true</code> when some binding is accepted, <code>false</code> when all bindings are rejected or when the multimap is empty.
	 * @since 2.0
	 */
	@Override
	default boolean isAny2(final Predicate2<? super K, ? super V> filter) {
		return bindings().isAny2(filter);
	}
	
	/**
	 * Tests whether all bindings of this multimap are accepted by the given filter.
	 * 
	 * @param filter Predicate to use to filter the binding.
	 * @return <code>true</code> when all bindings are accepted or when the multimap is empty, <code>false</code> when some binding is rejected.
	 * @since 2.0
	 */
	@Override
	default boolean areAll2(final Predicate2<? super K, ? super V> filter) {
		return bindings().areAll2(filter);
	}
	
	/**
	 * Counts the bindings of this multimap accepted by the given filter.
	 * 
	 * @param filter Predicate to use to filter the binding.
	 * @return The number of accepted bindings.
	 * @since 2.0
	 */
	@Override
	default int count2(final Predicate2<? super K, ? super V> filter) {
		return bindings().count2(filter);
	}
	
	/**
	 * Filters the bindings of this multimap using the given filter.
	 *
	 * @param filter Predicate to use to filter the bindings.
	 * @return A multimap containing the filtered bindings.
	 * @since 2.0
	 */
	@Override
	default Multimap<K, V, C> filter2(final Predicate2<? super K, ? super V> filter) {
		throw new UnsupportedOperationException();
		//		assert null != filter;
		//
		//		final Multimap<K, V, C> self = this;
		//		return new BaseMultimap<K, V, C>() {
		//			@Override
		//			public ExSet<K> keys() {
		//				return self.keys().filter(k -> IterableUtils.isAny(self.get(k), v -> filter.evaluate(k, v))); // TODO: optimize, use collectionBindings
		//			}
		//
		//			@Override
		//			public ExCollection<V> values() {
		//				return new AbstractExCollection<V>() {
		//					@Override
		//					public int size() {
		//						return self.bindings().filter2(filter).count(v -> true); // TODO: optimize
		//					}
		//
		//					@Override
		//					public ExIterator<V> iterator() {
		//						return self.bindings().iterator().filter2(filter).map(Tuple2::get2);
		//					}
		//				};
		//			}
		//
		//			@Override
		//			public PairIterable<K, V> bindings() {
		//				return self.bindings().filter2(filter);
		//			}
		//
		//			@Override
		//			public PairIterable<K, C> collectionBindings() {
		//				return self.collectionBindings().filter2((k, c) -> IterableUtils.isAny(c, v -> filter.evaluate(k, v)));
		//			}
		//
		//			@Override
		//			public int size() {
		//				return self.bindings().filter2(filter).count(v -> true); // TODO: optimize
		//			}
		//
		//			@Override
		//			public C get(final K key) {
		//
		//			}
		//
		//			@Override
		//			public boolean put(final K key, final V value) {
		//				throw new UnsupportedOperationException();
		//			}
		//
		//			@Override
		//			public C removeKey(final K key) {
		//			}
		//
		//			@Override
		//			public boolean removeValue(final V value) {
		//			}
		//
		//			@Override
		//			public boolean remove(final K key, final V value) {
		//			}
		//
		//			@Override
		//			public void clear() {
		//			}
		//		};
	}
	
	// TODO: filter2(Predicate2, MultimapFactory)
	
	/**
	 * Gets any binding of this multimap accepted by the given filter.
	 * 
	 * @param filter Predicate to use to filter the binding.
	 * @return The accepted binding, or when no bindings are accepted or when the multimap is empty.
	 * @since 2.0
	 */
	@Override
	default Maybe<Tuple2<K, V>> filterAny2(final Predicate2<? super K, ? super V> filter) {
		return bindings().filterAny2(filter);
	}
	
	/**
	 * Transforms the bindings of this multimap using the given function.
	 *
	 * @param <TE> Type of the transformed elements.
	 * @param function Function to use to transform the bindings.
	 * @return A collection of the transformed elements.
	 * @since 2.0
	 */
	@Override
	default <TE> ExCollection<TE> map2(final Function2<? super K, ? super V, ? extends TE> function) {
		assert null != function;
		
		return new AbstractExCollection<TE>() {
			@Override
			public int size() {
				return Multimap.this.size();
			}
			
			@Override
			public ExIterator<TE> iterator() {
				return Multimap.this.bindings().iterator().map2(function);
			}
		};
	}
	
	/**
	 * Transforms the bindings of this multimap using the given function.
	 *
	 * @param <TE> Type of the transformed elements.
	 * @param <RC> Type of the result collection.
	 * @param function Function to use to transform the bindings.
	 * @param resultFactory Factory of the result collection.
	 * @return A new collection of the transformed elements.
	 * @since 2.0
	 */
	default <TE, RC extends Collection<? super TE>> RC map2(final Function2<? super K, ? super V, ? extends TE> function, final CollectionFactory<? super TE, RC> resultFactory) {
		return bindings().iterator().map2(function).drain(resultFactory.build(size()));
	}
	
	// TODO: mapValues
	// TODO: mapValues
	
	/**
	 * Extracts the bindings of this multimap using the given extractor.
	 * 
	 * @param <EE> Type of the extracted elements.
	 * @param extractor Function to use to extract the bindings.
	 * @return A collection of the extracted elements.
	 * @since 2.0
	 */
	@Override
	default <EE> ExCollection<EE> extract2(final Function2<? super K, ? super V, ? extends Maybe<? extends EE>> extractor) {
		assert null != extractor;
		
		return new AbstractExCollection<EE>() {
			@Override
			public int size() {
				return Multimap.this.bindings().iterator().count2((k, v) -> extractor.evaluate(k, v).isSome());
			}
			
			@Override
			public ExIterator<EE> iterator() {
				return Multimap.this.bindings().iterator().extract2(extractor);
			}
		};
	}
	
	/**
	 * Extracts the bindings of this multimap using the given extractor.
	 * 
	 * @param <EE> Type of the extracted elements.
	 * @param <RC> Type of the result collection.
	 * @param extractor Function to use to extract the bindings.
	 * @param resultFactory Factory of the result collection.
	 * @return A new collection of the extracted elements.
	 * @since 2.0
	 */
	default <EE, RC extends Collection<? super EE>> RC extract2(final Function2<? super K, ? super V, ? extends Maybe<? extends EE>> extractor, final CollectionFactory<? super EE, RC> resultFactory) {
		return bindings().iterator().extract2(extractor).drain(resultFactory.build());
	}
	
	/**
	 * Gets the element extracted from any binding provided by this multimap using the given extractor.
	 * 
	 * @param <EE> Type of the extracted element.
	 * @param extractor Function to use to extract the bindings.
	 * @return The extracted element, or nothing when no elements can be extracted from any binding.
	 * @since 2.0
	 */
	@Override
	default <EE> Maybe<EE> extractAny2(final Function2<? super K, ? super V, ? extends Maybe<? extends EE>> extractor) {
		return bindings().extractAny2(extractor);
	}
	
	// TODO: extractAll2
	
	// TODO: extractValues
	// TODO: extractValues
	
	/**
	 * Executes the given procedure with each binding of this multimap.
	 * 
	 * @since 2.0
	 */
	@Override
	default void foreach2(final Procedure2<? super K, ? super V> procedure) {
		bindings().foreach2(procedure);
	}
	
	// Traversable.
	
	/**
	 * Left folds over the bindings of this multimap using the given operator and initial state.
	 * <p>
	 * The bindings are folded according their iteration order.
	 * 
	 * @since 2.0
	 */
	@Override
	default <S> S fold(final Function2<? super S, ? super Tuple2<K, V>, ? extends S> operator, final S initialState) {
		return bindings().fold(operator, initialState);
	}
	
	/**
	 * Tests whether any binding of this multimap is accepted by the given filter.
	 * 
	 * @param filter Predicate to use to filter the binding.
	 * @return <code>true</code> when some binding is accepted, <code>false</code> when all bindings are rejected or when the multimap is empty.
	 * @since 2.0
	 */
	@Override
	default boolean isAny(final Predicate<? super Tuple2<K, V>> filter) {
		return bindings().isAny(filter);
	}
	
	/**
	 * Tests whether all bindings of this multimap are accepted by the given filter.
	 * 
	 * @param filter Predicate to use to filter the binding.
	 * @return <code>true</code> when all bindings are accepted or when the multimap is empty, <code>false</code> when some binding is rejected.
	 * @since 2.0
	 */
	@Override
	default boolean areAll(final Predicate<? super Tuple2<K, V>> filter) {
		return bindings().areAll(filter);
	}
	
	/**
	 * Counts the bindings of this multimap accepted by the given filter.
	 * 
	 * @param filter Predicate to use to filter the binding.
	 * @return The number of accepted bindings.
	 * @since 2.0
	 */
	@Override
	default int count(final Predicate<? super Tuple2<K, V>> filter) {
		return bindings().count(filter);
	}
	
	/**
	 * Gets the least binding of this multimap according to the given comparator.
	 * 
	 * @return The least binding, or nothing when the multimap is empty.
	 * @since 2.0
	 */
	@Override
	default Maybe<Tuple2<K, V>> least(final Comparator<? super Tuple2<K, V>> comparator) {
		return bindings().least(comparator);
	}
	
	/**
	 * Gets the greatest binding of this multimap according to the given comparator.
	 *
	 * @return The greatest binding, or nothing when the multimap is empty.
	 * @since 2.0
	 */
	@Override
	default Maybe<Tuple2<K, V>> greatest(final Comparator<? super Tuple2<K, V>> comparator) {
		return bindings().greatest(comparator);
	}
	
	/**
	 * Gets the binding with the least value of this multimap according to the given comparator.
	 *
	 * @param comparator Comparator to use.
	 * @return The binding with the least value, or nothing when the multimap is empty.
	 * @since 2.0
	 */
	default Maybe<Tuple2<K, V>> leastByValue(final Comparator<? super V> comparator) {
		return least(FieldComparators.field2(comparator));
	}
	
	/**
	 * Gets the binding with the greatest value of this multimap according to the given comparator.
	 *
	 * @param comparator Comparator to use.
	 * @return The binding with the greatest value, or nothing when the multimap is empty.
	 * @since 2.0
	 */
	default Maybe<Tuple2<K, V>> greatestByValue(final Comparator<? super V> comparator) {
		return greatest(FieldComparators.field2(comparator));
	}
	
	/**
	 * Takes n bindings of this multimap.
	 * <p>
	 * The bindings are taken according their iteration order.
	 * 
	 * @param n Number of bindings to take.
	 * @return A multimap containing the taken bindings.
	 * @since 2.0
	 */
	@Override
	default Multimap<K, V, C> take(final int n) {
		throw new UnsupportedOperationException();
	}
	
	// TODO: take(int, MultimapFactory)
	
	/**
	 * Drops n bindings of this multimap.
	 * <p>
	 * The bindings are dropped according their iteration order.
	 * 
	 * @param n Number of bindings to drop.
	 * @return A multimap containing the remaining bindings.
	 * @since 2.0
	 */
	@Override
	default Multimap<K, V, C> drop(final int n) {
		throw new UnsupportedOperationException();
	}
	
	// TODO: drop(int, MultimapFactory)
	
	/**
	 * Groups the bindings of this multimap into batches of the given size.
	 *
	 * @param <B> Type of the batch collections.
	 * @param n Number of bindings of each batch.
	 * @param batchFactory Factory of the batch collections.
	 * @return A collection of the batches of bindings.
	 * @since 2.0
	 */
	@Override
	default <B extends Collection<? super Tuple2<K, V>>> ExCollection<B> group(final int n, final CollectionFactory<? super Tuple2<K, V>, B> batchFactory) {
		assert null != batchFactory;
		
		return new AbstractExCollection<B>() {
			@Override
			public int size() {
				return (Multimap.this.size() + n - 1) / n;
			}
			
			@Override
			public ExIterator<B> iterator() {
				return bindings().iterator().group(n, batchFactory);
			}
		};
	}
	
	// TODO: group(int, CollectionFactory, CollectionFactory)
	
	/**
	 * Filters the bindings of this multimap using the given filter.
	 *
	 * @param filter Predicate to use to filter the bindings.
	 * @return A multimap containing the filtered bindings.
	 * @since 2.0
	 */
	@Override
	default Multimap<K, V, C> filter(final Predicate<? super Tuple2<K, V>> filter) {
		return filter2(PredicateUtils.curry2(filter));
	}
	
	// TODO: filter(Predicate, MultimapFactory)
	
	/**
	 * Gets any binding of this multimap accepted by the given filter.
	 * 
	 * @param filter Predicate to use to filter the binding.
	 * @return The accepted binding, or when no bindings are accepted or when the multimap is empty.
	 * @since 2.0
	 */
	@Override
	default Maybe<Tuple2<K, V>> filterAny(final Predicate<? super Tuple2<K, V>> filter) {
		return bindings().filterAny(filter);
	}
	
	/**
	 * Transforms the bindings of this multimap using the given function.
	 *
	 * @param <TE> Type of the transformed elements.
	 * @param function Function to use to transform the bindings.
	 * @return A collection of the transformed elements.
	 * @since 2.0
	 */
	@Override
	default <TE> ExCollection<TE> map(final Function<? super Tuple2<K, V>, ? extends TE> function) {
		assert null != function;
		
		return new AbstractExCollection<TE>() {
			@Override
			public int size() {
				return Multimap.this.size();
			}
			
			@Override
			public ExIterator<TE> iterator() {
				return Multimap.this.bindings().iterator().map(function);
			}
		};
	}
	
	// TODO: map(Function, CollectionFactory)
	
	/**
	 * Extracts the bindings of this multimap using the given extractor.
	 * 
	 * @param <EE> Type of the extracted elements.
	 * @param extractor Function to use to extract the bindings.
	 * @return A collection of the extracted elements.
	 * @since 2.0
	 */
	@Override
	default <EE> ExCollection<EE> extract(final Function<? super Tuple2<K, V>, ? extends Maybe<? extends EE>> extractor) {
		assert null != extractor;
		
		return new AbstractExCollection<EE>() {
			@Override
			public int size() {
				return Multimap.this.bindings().iterator().count(b -> extractor.evaluate(b).isSome());
			}
			
			@Override
			public ExIterator<EE> iterator() {
				return Multimap.this.bindings().iterator().extract(extractor);
			}
		};
	}
	
	// TODO: extract(Function, CollectionFactory)
	
	/**
	 * Gets the element extracted from any binding provided by this multimap using the given extractor.
	 * 
	 * @param <EE> Type of the extracted element.
	 * @param extractor Function to use to extract the bindings.
	 * @return The extracted element, or nothing when no elements can be extracted from any binding.
	 * @since 2.0
	 */
	@Override
	default <EE> Maybe<EE> extractAny(final Function<? super Tuple2<K, V>, ? extends Maybe<? extends EE>> extractor) {
		return bindings().extractAny(extractor);
	}
	
	// TODO: extractAll
	
	/**
	 * Executes the given procedure with each binding of this multimap.
	 * 
	 * @since 2.0
	 */
	@Override
	default void foreach(final Procedure<? super Tuple2<K, V>> procedure) {
		bindings().foreach(procedure);
	}
	
	// Object.
	
	/**
	 * Returns the hash code value for this multimap.
	 * <p>
	 * The hash code of a multimap is defined to be the sum of the hash codes of each collection binding of the multimap. This ensures that
	 * <tt>m1.equals(m2)</tt> implies that <tt>m1.hashCode()==m2.hashCode()</tt> for any two multimaps <tt>m1</tt> and <tt>m2</tt>, as required by the general
	 * contract of {@link Object#hashCode}.
	 * 
	 * @return The hash code value.
	 * @since 2.0
	 */
	@Override
	int hashCode();
	
	/**
	 * Compares the given object with this multimap for equality.
	 * <p>
	 * Returns <tt>true</tt> if the given object is also a multimap and the two multimaps represent the same collection bindings This ensures that the
	 * <tt>equals</tt> method works properly across different implementations of the <tt>Multimap</tt> interface.
	 * 
	 * @param o Object to be compared for equality.
	 * @return <tt>true</tt> if the given object is equal to this multimap, <code>false</code> otherwise.
	 * @since 2.0
	 */
	@Override
	boolean equals(Object o);
}
