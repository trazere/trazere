/*
 *  Copyright 2006 Julien Dufour
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

import java.util.Map;
import java.util.TreeMap;

/**
 * The <code>TreeMapFactory</code> represents factories which build {@link TreeMap}s.
 * 
 * @param <K> Type of the keys.
 * @param <V> Type of the values.
 */
public class TreeMapFactory<K, V>
implements MapFactory<K, V, TreeMap<K, V>> {
	public TreeMap<K, V> build() {
		return new TreeMap<K, V>();
	}

	public TreeMap<K, V> build(final int capacity) {
		return new TreeMap<K, V>();
	}

	public TreeMap<K, V> build(final Map<? extends K, ? extends V> values) {
		return new TreeMap<K, V>(values);
	}
}
