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

import java.util.Collection;
import java.util.HashSet;

/**
 * The <code>HashSetFactory</code> represents factories which build {@link HashSet}s.
 * 
 * @param <T> Type of the elements.
 */
public class HashSetFactory<T>
implements CollectionFactory<T, HashSet<T>> {
	public HashSet<T> build() {
		return new HashSet<T>();
	}

	public HashSet<T> build(final int capacity) {
		return new HashSet<T>(capacity);
	}

	public HashSet<T> build(final Collection<? extends T> values) {
		return new HashSet<T>(values);
	}
}
