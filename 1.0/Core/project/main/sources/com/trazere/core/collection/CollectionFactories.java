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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.TreeSet;

/**
 * The {@link CollectionFactories} class provides various factories of {@link CollectionFactory collection factories}.
 * 
 * @see CollectionFactory
 * @since 1.0
 */
public class CollectionFactories {
	/**
	 * Builds a factory of {@link ArrayList}s.
	 * 
	 * @param <E> Type of the elements.
	 * @return The built factory.
	 * @see ArrayList
	 * @since 1.0
	 */
	@SuppressWarnings("unchecked")
	public static <E> ExtendedListFactory<E, ArrayList<E>> arrayList() {
		return (ExtendedListFactory<E, ArrayList<E>>) ARRAY_LIST;
	}
	
	private static final ExtendedListFactory<?, ?> ARRAY_LIST = new ExtendedListFactory<Object, ArrayList<Object>>() {
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
	 * Builds a factory of {@link LinkedList}s.
	 * 
	 * @param <E> Type of the elements.
	 * @return The built factory.
	 * @see LinkedList
	 * @since 1.0
	 */
	@SuppressWarnings("unchecked")
	public static <E> ExtendedListFactory<E, LinkedList<E>> linkedList() {
		return (ExtendedListFactory<E, LinkedList<E>>) LINKED_LIST;
	}
	
	private static final ExtendedListFactory<?, ?> LINKED_LIST = new ExtendedListFactory<Object, LinkedList<Object>>() {
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
	 * Builds a factory of {@link HashSet}s.
	 * 
	 * @param <E> Type of the elements.
	 * @return The built factory.
	 * @see HashSet
	 * @since 1.0
	 */
	@SuppressWarnings("unchecked")
	public static <E> ExtendedSetFactory<E, HashSet<E>> hashSet() {
		return (ExtendedSetFactory<E, HashSet<E>>) HASH_SET;
	}
	
	private static final ExtendedSetFactory<?, ?> HASH_SET = new ExtendedSetFactory<Object, HashSet<Object>>() {
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
	 * Builds a factory of {@link TreeSet}s.
	 * 
	 * @param <E> Type of the elements.
	 * @return The built factory.
	 * @see TreeSet
	 * @since 1.0
	 */
	@SuppressWarnings("unchecked")
	public static <E> ExtendedSetFactory<E, TreeSet<E>> treeSet() {
		return (ExtendedSetFactory<E, TreeSet<E>>) TREE_SET;
	}
	
	private static final ExtendedSetFactory<?, ?> TREE_SET = new ExtendedSetFactory<Object, TreeSet<Object>>() {
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
