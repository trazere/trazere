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
import com.trazere.core.functional.Predicate;
import com.trazere.core.functional.Predicate2;
import com.trazere.core.lang.PairIterable;
import com.trazere.core.util.Comparators;
import com.trazere.core.util.Maybe;
import com.trazere.core.util.Tuple2;
import java.util.Collection;
import java.util.Map;

/**
 * The {@link MultimapUtils} class provides various utilities regarding {@link Multimap multimaps}.
 * 
 * @see Multimap
 * @since 2.0
 */
public class MultimapUtils {
	/**
	 * Gets the binding with the least value of the given multimap.
	 *
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param multimap Multimap containing the values to compare.
	 * @return The binding with the least value, or nothing when the map is empty.
	 * @since 2.0
	 */
	public static <K, V extends Comparable<V>> Maybe<Tuple2<K, V>> leastByValue(final Multimap<K, V, ?> multimap) {
		return multimap.leastByValue(Comparators.<V>natural());
	}
	
	/**
	 * Gets the binding with the greatest value of the given multimap.
	 *
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param multimap Multimap containing the values to compare.
	 * @return The binding with the greatest value, or nothing when the map is empty.
	 * @since 2.0
	 */
	public static <K, V extends Comparable<V>> Maybe<Tuple2<K, V>> greatestByValue(final Multimap<K, V, ?> multimap) {
		return multimap.greatestByValue(Comparators.<V>natural());
	}
	
	// TODO: flatten
	
	/**
	 * Builds an unmodifiable view of the given multimap.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <C> Type of the collections of values.
	 * @param multimap Multimap to view.
	 * @return The unmodifiable view.
	 * @since 2.0
	 */
	public static <K, V, C extends Collection<V>> Multimap<K, V, C> unmodifiable(final Multimap<K, V, C> multimap) {
		assert null != multimap;
		
		return multimap instanceof UnmodifiableMutimap ? multimap : new UnmodifiableMutimap<>(multimap);
	}
	
	private static class UnmodifiableMutimap<K, V, C extends Collection<V>>
	extends MultimapDecorator<K, V, C> {
		public UnmodifiableMutimap(final Multimap<K, V, C> decorated) {
			super(decorated);
		}
		
		// Multimap.
		
		@Override
		public ExSet<K> keys() {
			return super.keys().unmodifiable();
		}
		
		@Override
		public ExCollection<V> values() {
			return super.values().unmodifiable();
		}
		
		@Override
		public PairIterable<K, V> bindings() {
			return super.bindings().unmodifiable();
		}
		
		@Override
		public PairIterable<K, C> collectionBindings() {
			return super.collectionBindings().unmodifiable();
		}
		
		@Override
		public boolean put(final K key, final V value) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public boolean put(final Tuple2<? extends K, ? extends V> binding) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public boolean putAll(final K key, @SuppressWarnings("unchecked") final V... values) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public boolean putAll(final K key, final Iterable<? extends V> values) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public boolean putAll(@SuppressWarnings("unchecked") final Tuple2<? extends K, ? extends V>... bindings) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public boolean putAll(final Iterable<? extends Tuple2<? extends K, ? extends V>> bindings) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public boolean putAll(final Map<? extends K, ? extends V> map) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public boolean putAll(final Multimap<? extends K, ? extends V, ?> multimap_) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public C removeKey(final K key) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public boolean removeValue(final V value) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public boolean remove(final K key, final V value) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public boolean removeAll(final K key, @SuppressWarnings("unchecked") final V... values) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public boolean removeAll(final K key, final Iterable<? extends V> values) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public boolean removeAll(@SuppressWarnings("unchecked") final Tuple2<? extends K, ? extends V>... bindings) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public boolean removeAll(final Iterable<? extends Tuple2<? extends K, ? extends V>> bindings) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public void clear() {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public Multimap<K, V, C> unmodifiable() {
			return this;
		}
		
		// PairTranversable.
		
		@Override
		public Multimap<K, V, C> filter2(final Predicate2<? super K, ? super V> filter) {
			return super.filter2(filter).unmodifiable();
		}
		
		@Override
		public <TE> ExCollection<TE> map2(final Function2<? super K, ? super V, ? extends TE> function) {
			return super.<TE>map2(function).unmodifiable();
		}
		
		@Override
		public <EE> ExCollection<EE> extract2(final Function2<? super K, ? super V, ? extends Maybe<? extends EE>> extractor) {
			return super.<EE>extract2(extractor).unmodifiable();
		}
		
		// Traversable.
		
		@Override
		public Multimap<K, V, C> take(final int n) {
			return super.take(n).unmodifiable();
		}
		
		@Override
		public Multimap<K, V, C> drop(final int n) {
			return super.drop(n).unmodifiable();
		}
		
		@Override
		public Multimap<K, V, C> filter(final Predicate<? super Tuple2<K, V>> filter) {
			return super.filter(filter).unmodifiable();
		}
		
		@Override
		public <TE> ExCollection<TE> map(final Function<? super Tuple2<K, V>, ? extends TE> function) {
			return super.<TE>map(function).unmodifiable();
		}
		
		@Override
		public <EE> ExCollection<EE> extract(final Function<? super Tuple2<K, V>, ? extends Maybe<? extends EE>> extractor) {
			return super.<EE>extract(extractor).unmodifiable();
		}
	}
	
	private MultimapUtils() {
		// Prevent instantiation.
	}
}
