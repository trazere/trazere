/*
 *  Copyright 2006-2009 Julien Dufour
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
 */
public interface Multimap<K, V, C extends Collection<V>> {
	/**
	 * Associate the given value to the given key in the receiver multimap.
	 * <p>
	 * The previous associations to the key are preserved.
	 * 
	 * @param key The key. May or may not be <code>null</code> according the implementation.
	 * @param value The value. May or may not be <code>null</code> according the implementation.
	 * @return <code>true</code> when the multimap has changed, <code>false</code> otherwise.
	 */
	public boolean put(final K key, final V value);
	
	/**
	 * Associate the given values to the given key in the receiver multimap.
	 * <p>
	 * The previous associations to the key are preserved.
	 * 
	 * @param key The key. May or may not be <code>null</code> according the implementation.
	 * @param values The values. May or may not contain <code>null</code> according the implementation.
	 * @return <code>true</code> when the multimap has changed, <code>false</code> otherwise.
	 */
	public boolean putAll(final K key, final Collection<? extends V> values);
	
	/**
	 * Copy all the association of the given multimap into the receiver multimap.
	 * 
	 * @param multimap The multimap to copy.
	 * @return <code>true</code> when the multimap has changed, <code>false</code> otherwise.
	 */
	public boolean putAll(final Multimap<? extends K, ? extends V, ?> multimap);
	
	/**
	 * Indicate wether the receiver multimap is empty or not.
	 * 
	 * @return <code>true</code> if the multi is empty, <code>false</code> otherwise.
	 */
	public boolean isEmpty();
	
	/**
	 * Get the keys associated to some values in the receiver multimap.
	 * 
	 * @return The keys.
	 */
	public Set<K> keySet();
	
	/**
	 * Test wether some values are associated to the given key in the receiver multimap.
	 * 
	 * @param key The key. May or may not be <code>null</code> according the implementation.
	 * @return <code>true</code> when some values are associated to the key, <code>false</code> otherwise.
	 */
	public boolean containsKey(final K key);
	
	/**
	 * Test wether the given value is associated to the given key in the receiver multimap.
	 * 
	 * @param key The key. May or may not be <code>null</code> according the implementation.
	 * @param value The value. May or may not be <code>null</code> according the implementation.
	 * @return <code>true</code> when the given value is associated to the key, <code>false</code> otherwise.
	 */
	public boolean containsValue(final K key, final V value);
	
	/**
	 * Get the values associated to the given key in the receiver multimap.
	 * 
	 * @param key The key. May or may not be <code>null</code> according the implementation.
	 * @return The associated values.
	 */
	public C get(final K key);
	
	/**
	 * Clear the receiver multimap.
	 */
	public void clear();
	
	/**
	 * Remove all values associated to the given key in the receiver multimap.
	 * 
	 * @param key The key. May or may not be <code>null</code> according the implementation.
	 * @return The removed values.
	 */
	public C remove(final K key);
	
	/**
	 * Remove the association of the given value to the given key in the receiver multimap.
	 * 
	 * @param key The key. May or may not be <code>null</code> according the implementation.
	 * @param value The value. May or may not be <code>null</code> according the implementation.
	 * @return <code>true</code> when the multimap has changed, <code>false</code> otherwise.
	 */
	public boolean remove(final K key, final V value);
	
	/**
	 * Remove the associations of the given values to the given key in the receiver multimap.
	 * 
	 * @param key The key. May or may not be <code>null</code> according the implementation.
	 * @param values The values. May or may not contain <code>null</code> according the implementation.
	 * @return <code>true</code> when the multimap has changed, <code>false</code> otherwise.
	 */
	public boolean removeAll(final K key, final Collection<? extends V> values);
}
