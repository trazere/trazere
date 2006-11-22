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

import java.util.HashMap;
import java.util.Map;

/**
 * The <code>HashMapFactory</code> represents factories which build {@link HashMap}s.
 * 
 * @param <K> Type of the keys.
 * @param <V> Type of the values.
 */
public class HashMapFactory<K, V>
implements MapFactory<K, V, HashMap<K, V>> {
	public HashMap<K, V> build() {
		return new HashMap<K, V>();
	}

	public HashMap<K, V> build(final int capacity) {
		return new HashMap<K, V>(capacity);
	}

	public HashMap<K, V> build(final Map<? extends K, ? extends V> values) {
		return new HashMap<K, V>(values);
	}
}
