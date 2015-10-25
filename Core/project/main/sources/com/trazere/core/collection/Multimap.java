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

import java.util.Collection;
import java.util.Map;
import java.util.Set;

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
public interface Multimap<K, V, C extends Collection<V>> {
	/**
	 * Puts the given binding in this multimap.
	 * <p>
	 * The current bindings of the given key are preserved.
	 * 
	 * @param key Key of the binding.
	 * @param value Value of the binding.
	 * @return <code>true</code> when the multimap has changed, <code>false</code> otherwise.
	 * @since 2.0
	 */
	boolean put(K key, V value);
	
	/**
	 * Puts the given bindings in this multimap.
	 * <p>
	 * The current bindings of the given key are preserved.
	 * 
	 * @param key Key of the bindings.
	 * @param values Values of the bindings.
	 * @return <code>true</code> when the multimap has changed, <code>false</code> otherwise.
	 * @since 2.0
	 */
	boolean putAll(K key, Iterable<? extends V> values);
	
	/**
	 * Puts the bindings of the given multimap in this multimap.
	 * <p>
	 * The current bindings are preserved.
	 * 
	 * @param multimap Multimap containing the bindings.
	 * @return <code>true</code> when the multimap has changed, <code>false</code> otherwise.
	 * @since 2.0
	 */
	boolean putAll(Multimap<? extends K, ? extends V, ?> multimap);
	
	/**
	 * Indicates whether this multimap is empty or not.
	 * 
	 * @return <code>true</code> if the multi is empty, <code>false</code> otherwise.
	 * @since 2.0
	 */
	boolean isEmpty();
	
	/**
	 * Gets the number of bindings of this multimap.
	 * 
	 * @return The number of bindings.
	 * @since 2.0
	 */
	int size();
	
	/**
	 * Tests whether this multimap contains some binding with the given key.
	 * 
	 * @param key Key to test.
	 * @return <code>true</code> when the multimap contains bindings with the given key, <code>false</code> otherwise.
	 * @since 2.0
	 */
	boolean containsKey(K key);
	
	/**
	 * Gets the keys of the bindings of this multimap.
	 * 
	 * @return An unmodifiable set of the keys.
	 * @since 2.0
	 */
	Set<K> keySet();
	
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
	 * Gets the entries corresponding to the bindings of this multimap.
	 * 
	 * @return An unmodifiable set of the entries.
	 * @since 2.0
	 */
	Set<Map.Entry<K, V>> entrySet();
	
	/**
	 * Gets the entries corresponding to the binding groups of this multimap.
	 * 
	 * @return An unmodifiable set of the entries of the values.
	 * @since 2.0
	 */
	Set<Map.Entry<K, C>> collectionEntrySet();
	
	/**
	 * Gets the values associated to the given key in this multimap.
	 * 
	 * @param key The key. May or may not be <code>null</code> according the implementation.
	 * @return An unmodifiable collection of the values.
	 * @since 2.0
	 */
	C get(K key);
	
	/**
	 * Tests whether this multimap contains some binding with the given value.
	 * 
	 * @param value Value to test.
	 * @return <code>true</code> when the multimap contains bindings with the given value, <code>false</code> otherwise.
	 * @since 2.0
	 */
	boolean containsValue(V value);
	
	/**
	 * Clears this multimap.
	 * 
	 * @since 2.0
	 */
	void clear();
	
	/**
	 * Removes the given binding from this multimap.
	 * 
	 * @param key Key of the binding to remove.
	 * @param value Value of the binding to remove.
	 * @return <code>true</code> when the multimap has changed, <code>false</code> otherwise.
	 * @since 2.0
	 */
	boolean remove(K key, V value);
	
	/**
	 * Removes the given bindings from this multimap.
	 * 
	 * @param key Key of the bindings to remove.
	 * @param values Values of the bindings to remove.
	 * @return <code>true</code> when the multimap has changed, <code>false</code> otherwise.
	 * @since 2.0
	 */
	boolean removeAll(K key, Iterable<? extends V> values);
	
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
	
	// Object.
	
	/**
	 * Returns the hash code value for this multimap.
	 * <p>
	 * The hash code of a multimap is defined to be the sum of the hash codes of each binding of the multimap. This ensures that <tt>m1.equals(m2)</tt> implies
	 * that <tt>m1.hashCode()==m2.hashCode()</tt> for any two multimaps <tt>m1</tt> and <tt>m2</tt>, as required by the general contract of
	 * {@link Object#hashCode}.
	 *
	 * @return The hash code value.
	 * @see java.util.Map.Entry#hashCode()
	 * @since 2.0
	 */
	@Override
	int hashCode();
	
	/**
	 * Compares the given object with this multimap for equality.
	 * <p>
	 * Returns <tt>true</tt> if the given object is also a multimap and the two multimaps represent the same collections bindings This ensures that the
	 * <tt>equals</tt> method works properly across different implementations of the <tt>Multimap</tt> interface.
	 *
	 * @param o Object to be compared for equality.
	 * @return <tt>true</tt> if the given object is equal to this multimap, <code>false</code> otherwise.
	 * @since 2.0
	 */
	@Override
	boolean equals(Object o);
}
