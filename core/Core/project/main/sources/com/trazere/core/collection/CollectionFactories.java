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
package com.trazere.core.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.TreeSet;

/**
 * The {@link CollectionFactories} class provides various factories of collection factories.
 * 
 * @see CollectionFactory
 */
public class CollectionFactories {
	/**
	 * Builds a collection factory that builds {@link ArrayList}s.
	 * 
	 * @param <T> Type of the elements.
	 * @return The built factory.
	 */
	@SuppressWarnings("unchecked")
	public static <T> CollectionFactory<T, ArrayList<T>> arrayList() {
		return (CollectionFactory<T, ArrayList<T>>) ARRAY_LIST;
	}
	
	private static final CollectionFactory<?, ?> ARRAY_LIST = new CollectionFactory<Object, ArrayList<Object>>() {
		@Override
		public ArrayList<Object> build() {
			return new ArrayList<>();
		}
		
		@Override
		public ArrayList<Object> build(final int capacity) {
			return new ArrayList<>(capacity);
		}
		
		@Override
		public ArrayList<Object> build(final Collection<? extends Object> elements) {
			return new ArrayList<>(elements);
		}
	};
	
	/**
	 * Builds a collection factory that builds {@link LinkedList}s.
	 * 
	 * @param <T> Type of the elements.
	 * @return The built factory.
	 */
	@SuppressWarnings("unchecked")
	public static <T> CollectionFactory<T, LinkedList<T>> linkedList() {
		return (CollectionFactory<T, LinkedList<T>>) LINKED_LIST;
	}
	
	private static final CollectionFactory<?, ?> LINKED_LIST = new CollectionFactory<Object, LinkedList<Object>>() {
		@Override
		public LinkedList<Object> build() {
			return new LinkedList<>();
		}
		
		@Override
		public LinkedList<Object> build(final Collection<? extends Object> elements) {
			return new LinkedList<>(elements);
		}
	};
	
	/**
	 * Builds a collection factory that builds {@link HashSet}s.
	 * 
	 * @param <T> Type of the elements.
	 * @return The built factory.
	 */
	@SuppressWarnings("unchecked")
	public static <T> CollectionFactory<T, HashSet<T>> hashSet() {
		return (CollectionFactory<T, HashSet<T>>) HASH_SET;
	}
	
	private static final CollectionFactory<?, ?> HASH_SET = new CollectionFactory<Object, HashSet<Object>>() {
		@Override
		public HashSet<Object> build() {
			return new HashSet<>();
		}
		
		@Override
		public HashSet<Object> build(final int capacity) {
			return new HashSet<>(capacity);
		}
		
		@Override
		public HashSet<Object> build(final Collection<? extends Object> elements) {
			return new HashSet<>(elements);
		}
	};
	
	/**
	 * Builds a collection factory that builds {@link TreeSet}s.
	 * 
	 * @param <T> Type of the elements.
	 * @return The built factory.
	 */
	@SuppressWarnings("unchecked")
	public static <T> CollectionFactory<T, TreeSet<T>> treeSet() {
		return (CollectionFactory<T, TreeSet<T>>) TREE_SET;
	}
	
	private static final CollectionFactory<?, ?> TREE_SET = new CollectionFactory<Object, TreeSet<Object>>() {
		@Override
		public TreeSet<Object> build() {
			return new TreeSet<>();
		}
		
		@Override
		public TreeSet<Object> build(final Collection<? extends Object> elements) {
			return new TreeSet<>(elements);
		}
	};
	
	private CollectionFactories() {
		// Prevents instantiation.
	}
}
