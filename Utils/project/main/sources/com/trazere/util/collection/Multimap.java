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

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * The {@link Multimap} interface defines indexed collections of values.
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
 * @deprecated Use {@link com.trazere.core.collection.Multimap}.
 */
@Deprecated
public interface Multimap<K, V, C extends Collection<V>> {
	/**
	 * Associates the given value to the given key in the receiver multimap.
	 * <p>
	 * The previous associations to the key are preserved.
	 * 
	 * @param key The key. May or may not be <code>null</code> according the implementation.
	 * @param value The value. May or may not be <code>null</code> according the implementation.
	 * @return <code>true</code> when the multimap has changed, <code>false</code> otherwise.
	 * @deprecated Use {@link com.trazere.core.collection.Multimap#put(Object, Object)}.
	 */
	@Deprecated
	public boolean put(final K key, final V value);
	
	/**
	 * Associates the given values to the given key in the receiver multimap.
	 * <p>
	 * The previous associations to the key are preserved.
	 * 
	 * @param key The key. May or may not be <code>null</code> according the implementation.
	 * @param values The values. May or may not contain <code>null</code> according the implementation.
	 * @return <code>true</code> when the multimap has changed, <code>false</code> otherwise.
	 * @deprecated Use {@link com.trazere.core.collection.Multimap#putAll(Object, Iterable)}.
	 */
	@Deprecated
	public boolean putAll(final K key, final Collection<? extends V> values);
	
	/**
	 * Copies all the association of the given multimap into the receiver multimap.
	 * 
	 * @param multimap The multimap to copy.
	 * @return <code>true</code> when the multimap has changed, <code>false</code> otherwise.
	 * @deprecated Use {@link com.trazere.core.collection.Multimap#putAll(com.trazere.core.collection.Multimap)}.
	 */
	@Deprecated
	public boolean putAll(final Multimap<? extends K, ? extends V, ?> multimap);
	
	/**
	 * Indicates whether the receiver multimap is empty or not.
	 * 
	 * @return <code>true</code> if the multi is empty, <code>false</code> otherwise.
	 * @deprecated Use {@link com.trazere.core.collection.Multimap#isEmpty()}.
	 */
	@Deprecated
	public boolean isEmpty();
	
	/**
	 * Gets the number of associations of the receiver multimap.
	 * 
	 * @return The size.
	 * @deprecated Use {@link com.trazere.core.collection.Multimap#size()}.
	 */
	@Deprecated
	public int size();
	
	/**
	 * Gets the keys associated to some values in the receiver multimap.
	 * 
	 * @return The keys.
	 * @deprecated Use {@link com.trazere.core.collection.Multimap#keySet()}.
	 */
	@Deprecated
	public Set<K> keySet();
	
	/**
	 * Tests whether some values are associated to the given key in the receiver multimap.
	 * 
	 * @param key The key. May or may not be <code>null</code> according the implementation.
	 * @return <code>true</code> when some values are associated to the key, <code>false</code> otherwise.
	 * @deprecated Use {@link com.trazere.core.collection.Multimap#containsKey(Object)}.
	 */
	@Deprecated
	public boolean containsKey(final K key);
	
	/**
	 * Tests whether the given value is associated to the given key in the receiver multimap.
	 * 
	 * @param key The key. May or may not be <code>null</code> according the implementation.
	 * @param value The value. May or may not be <code>null</code> according the implementation.
	 * @return <code>true</code> when the given value is associated to the key, <code>false</code> otherwise.
	 * @deprecated Use {@link com.trazere.core.collection.Multimap#containsValue(Object)}.
	 */
	@Deprecated
	public boolean containsValue(final K key, final V value);
	
	/**
	 * Gets the values associated to the given key in the receiver multimap.
	 * 
	 * @param key The key. May or may not be <code>null</code> according the implementation.
	 * @return The associated values.
	 * @deprecated Use {@link com.trazere.core.collection.Multimap#get(Object)}.
	 */
	@Deprecated
	public C get(final K key);
	
	/**
	 * Clears the receiver multimap.
	 * 
	 * @deprecated Use {@link com.trazere.core.collection.Multimap#clear()}.
	 */
	@Deprecated
	public void clear();
	
	/**
	 * Removes all values associated to the given key in the receiver multimap.
	 * 
	 * @param key The key. May or may not be <code>null</code> according the implementation.
	 * @return The removed values.
	 * @deprecated Use {@link com.trazere.core.collection.Multimap#removeKey(Object)}.
	 */
	@Deprecated
	public C remove(final K key);
	
	/**
	 * Removes the association of the given value to the given key in the receiver multimap.
	 * 
	 * @param key The key. May or may not be <code>null</code> according the implementation.
	 * @param value The value. May or may not be <code>null</code> according the implementation.
	 * @return <code>true</code> when the multimap has changed, <code>false</code> otherwise.
	 * @deprecated Use {@link com.trazere.core.collection.Multimap#remove(Object, Object)}.
	 */
	@Deprecated
	public boolean remove(final K key, final V value);
	
	/**
	 * Removes the associations of the given values to the given key in the receiver multimap.
	 * 
	 * @param key The key. May or may not be <code>null</code> according the implementation.
	 * @param values The values. May or may not contain <code>null</code> according the implementation.
	 * @return <code>true</code> when the multimap has changed, <code>false</code> otherwise.
	 * @deprecated Use {@link com.trazere.core.collection.Multimap#removeAll(Object, Iterable)}.
	 */
	@Deprecated
	public boolean removeAll(final K key, final Collection<? extends V> values);
}
