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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.TreeSet;

/**
 * The <code>CollectionFactories</code> class provides various standard collection factories.
 * 
 * @see CollectionFactory
 */
public class CollectionFactories {
	private static final ArrayListFactory<?> ARRAYLIST = new ArrayListFactory<Object>();

	/**
	 * Build a collection factory which produces <code>ArrayList</code>s.
	 * 
	 * @param <T> Type of the elements.
	 * @return The factory.
	 */
	@SuppressWarnings("unchecked")
	public static <T> CollectionFactory<T, ArrayList<T>> arrayList() {
		return (ArrayListFactory<T>) ARRAYLIST;
	}

	private static final HashSetFactory<?> HASHSET = new HashSetFactory<Object>();

	/**
	 * Build a collection factory which produces <code>HashSet</code>s.
	 * 
	 * @param <T> Type of the elements.
	 * @return The factory.
	 */
	@SuppressWarnings("unchecked")
	public static <T> CollectionFactory<T, HashSet<T>> hashSet() {
		return (HashSetFactory<T>) HASHSET;
	}

	private static final TreeSetFactory<?> TREESET = new TreeSetFactory<Object>();

	/**
	 * Build a collection factory which produces <code>TreeSet</code>s.
	 * 
	 * @param <T> Type of the elements.
	 * @return The factory.
	 */
	@SuppressWarnings("unchecked")
	public static <T> CollectionFactory<T, TreeSet<T>> treeSet() {
		return (TreeSetFactory<T>) TREESET;
	}

	private CollectionFactories() {
		// Prevent instanciation.
	}
}
