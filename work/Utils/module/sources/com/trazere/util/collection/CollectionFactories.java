/*
 *  Copyright 2006-2008 Julien Dufour
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
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.TreeSet;

/**
 * The {@link CollectionFactories} class provides various standard collection factories.
 * 
 * @see CollectionFactory
 */
public class CollectionFactories {
	private static final CollectionFactory<?, ?> ARRAYLIST = new CollectionFactory<Object, ArrayList<Object>>() {
		public ArrayList<Object> build() {
			return new ArrayList<Object>();
		}
		
		public ArrayList<Object> build(final int capacity) {
			return new ArrayList<Object>(capacity);
		}
		
		public ArrayList<Object> build(final Collection<? extends Object> values) {
			return new ArrayList<Object>(values);
		}
	};
	
	/**
	 * Build a collection factory which produces {@link ArrayList}s.
	 * 
	 * @param <T> Type of the elements.
	 * @return The factory.
	 */
	@SuppressWarnings("unchecked")
	public static <T> CollectionFactory<T, ArrayList<T>> arrayList() {
		return (CollectionFactory<T, ArrayList<T>>) ARRAYLIST;
	}
	
	private static final CollectionFactory<?, ?> HASHSET = new CollectionFactory<Object, HashSet<Object>>() {
		public HashSet<Object> build() {
			return new HashSet<Object>();
		}
		
		public HashSet<Object> build(final int capacity) {
			return new HashSet<Object>(capacity);
		}
		
		public HashSet<Object> build(final Collection<? extends Object> values) {
			return new HashSet<Object>(values);
		}
	};
	
	/**
	 * Build a collection factory which produces {@link HashSet}s.
	 * 
	 * @param <T> Type of the elements.
	 * @return The factory.
	 */
	@SuppressWarnings("unchecked")
	public static <T> CollectionFactory<T, HashSet<T>> hashSet() {
		return (CollectionFactory<T, HashSet<T>>) HASHSET;
	}
	
	private static final CollectionFactory<?, ?> LINKEDLIST = new CollectionFactory<Object, LinkedList<Object>>() {
		public LinkedList<Object> build() {
			return new LinkedList<Object>();
		}
		
		public LinkedList<Object> build(final int capacity) {
			return new LinkedList<Object>();
		}
		
		public LinkedList<Object> build(final Collection<? extends Object> values) {
			return new LinkedList<Object>(values);
		}
	};
	
	/**
	 * Build a collection factory which produces {@link LinkedList}s.
	 * 
	 * @param <T> Type of the elements.
	 * @return The factory.
	 */
	@SuppressWarnings("unchecked")
	public static <T> CollectionFactory<T, LinkedList<T>> linkedList() {
		return (CollectionFactory<T, LinkedList<T>>) LINKEDLIST;
	}
	
	private static final CollectionFactory<?, ?> TREESET = new CollectionFactory<Object, TreeSet<Object>>() {
		public TreeSet<Object> build() {
			return new TreeSet<Object>();
		}
		
		public TreeSet<Object> build(final int capacity) {
			return new TreeSet<Object>();
		}
		
		public TreeSet<Object> build(final Collection<? extends Object> values) {
			return new TreeSet<Object>(values);
		}
	};
	
	/**
	 * Build a collection factory which produces {@link TreeSet}s.
	 * 
	 * @param <T> Type of the elements.
	 * @return The factory.
	 */
	@SuppressWarnings("unchecked")
	public static <T> CollectionFactory<T, TreeSet<T>> treeSet() {
		return (CollectionFactory<T, TreeSet<T>>) TREESET;
	}
	
	private CollectionFactories() {
		// Prevent instantiation.
	}
}
