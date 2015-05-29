/*
 *  Copyright 2006-2013 Julien Dufour
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

import com.trazere.util.lang.LangUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.TreeSet;

/**
 * The {@link CollectionFactories} class provides various factories of collection factories.
 * 
 * @see CollectionFactory
 * @deprecated Use core.
 */
@Deprecated
public class CollectionFactories {
	/**
	 * Builds a collection factory which produces {@link ArrayList}s.
	 * 
	 * @param <T> Type of the elements.
	 * @return The factory.
	 * @deprecated Use {@link com.trazere.core.collection.CollectionFactories#arrayList()}.
	 */
	@Deprecated
	public static <T> CollectionFactory<T, ArrayList<T>> arrayList() {
		return LangUtils.cast(_ARRAYLIST);
	}
	
	@Deprecated
	private static final CollectionFactory<?, ?> _ARRAYLIST = new BaseCollectionFactory<Object, ArrayList<Object>>() {
		@Override
		public ArrayList<Object> build() {
			return new ArrayList<Object>();
		}
		
		@Override
		public ArrayList<Object> build(final int capacity) {
			return new ArrayList<Object>(capacity);
		}
		
		@Override
		public ArrayList<Object> build(final Collection<? extends Object> values) {
			return new ArrayList<Object>(values);
		}
	};
	
	/**
	 * Builds a collection factory which produces {@link HashSet}s.
	 * 
	 * @param <T> Type of the elements.
	 * @return The factory.
	 * @deprecated Use {@link com.trazere.core.collection.CollectionFactories#hashSet()}.
	 */
	@Deprecated
	public static <T> CollectionFactory<T, HashSet<T>> hashSet() {
		return LangUtils.cast(_HASHSET);
	}
	
	@Deprecated
	private static final CollectionFactory<?, ?> _HASHSET = new BaseCollectionFactory<Object, HashSet<Object>>() {
		@Override
		public HashSet<Object> build() {
			return new HashSet<Object>();
		}
		
		@Override
		public HashSet<Object> build(final int capacity) {
			return new HashSet<Object>(capacity);
		}
		
		@Override
		public HashSet<Object> build(final Collection<? extends Object> values) {
			return new HashSet<Object>(values);
		}
	};
	
	/**
	 * Builds a collection factory which produces {@link LinkedList}s.
	 * 
	 * @param <T> Type of the elements.
	 * @return The factory.
	 * @deprecated Use {@link com.trazere.core.collection.CollectionFactories#linkedList()}.
	 */
	@Deprecated
	public static <T> CollectionFactory<T, LinkedList<T>> linkedList() {
		return LangUtils.cast(_LINKEDLIST);
	}
	
	@Deprecated
	private static final CollectionFactory<?, ?> _LINKEDLIST = new BaseCollectionFactory<Object, LinkedList<Object>>() {
		@Override
		public LinkedList<Object> build() {
			return new LinkedList<Object>();
		}
		
		@Override
		public LinkedList<Object> build(final int capacity) {
			return new LinkedList<Object>();
		}
		
		@Override
		public LinkedList<Object> build(final Collection<? extends Object> values) {
			return new LinkedList<Object>(values);
		}
	};
	
	/**
	 * Builds a collection factory which produces {@link TreeSet}s.
	 * 
	 * @param <T> Type of the elements.
	 * @return The factory.
	 * @deprecated Use {@link com.trazere.core.collection.CollectionFactories#treeSet()}.
	 */
	@Deprecated
	public static <T> CollectionFactory<T, TreeSet<T>> treeSet() {
		return LangUtils.cast(_TREESET);
	}
	
	@Deprecated
	private static final CollectionFactory<?, ?> _TREESET = new BaseCollectionFactory<Object, TreeSet<Object>>() {
		@Override
		public TreeSet<Object> build() {
			return new TreeSet<Object>();
		}
		
		@Override
		public TreeSet<Object> build(final int capacity) {
			return new TreeSet<Object>();
		}
		
		@Override
		public TreeSet<Object> build(final Collection<? extends Object> values) {
			return new TreeSet<Object>(values);
		}
	};
	
	private CollectionFactories() {
		// Prevents instantiation.
	}
}
