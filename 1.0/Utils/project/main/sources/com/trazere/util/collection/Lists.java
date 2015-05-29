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

import com.trazere.util.type.Maybe;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * The {@link Lists} class provides various factories of lists.
 * 
 * @see List
 * @deprecated Use core.
 */
@Deprecated
public class Lists {
	/**
	 * Builds a new list containing the given value if any.
	 * <p>
	 * This method instantiates an {@link ArrayList}.
	 * 
	 * @param <V> Type of the the value.
	 * @param value The value.
	 * @return The built list.
	 * @deprecated {@link com.trazere.core.collection.Lists#fromIterable(Iterable)}.
	 */
	@Deprecated
	public static <V> List<V> fromValue(final Maybe<V> value) {
		assert null != value;
		
		final List<V> list = new ArrayList<V>();
		if (value.isSome()) {
			list.add(value.asSome().getValue());
		}
		return list;
	}
	
	/**
	 * Builds a new list containing the given value.
	 * <p>
	 * This method instantiates an {@link ArrayList}.
	 * 
	 * @param <V> Type of the the value.
	 * @param value The value. May be <code>null</code>.
	 * @return The built list.
	 * @deprecated Use {@link com.trazere.core.collection.Lists#fromElement(Object)}.
	 */
	@Deprecated
	public static <V> List<V> fromValue(final V value) {
		final List<V> list = new ArrayList<V>(1);
		list.add(value);
		return list;
	}
	
	/**
	 * Builds a new list containing the given values.
	 * <p>
	 * This method instantiates an {@link ArrayList}.
	 * 
	 * @param <V> Type of the the values.
	 * @param value1 The first value. May be <code>null</code>.
	 * @param value2 The second value. May be <code>null</code>.
	 * @return The built list.
	 * @deprecated Use {@link com.trazere.core.collection.Lists#fromElements(Object, Object)}.
	 */
	@Deprecated
	public static <V> List<V> fromValues(final V value1, final V value2) {
		final List<V> list = new ArrayList<V>(2);
		list.add(value1);
		list.add(value2);
		return list;
	}
	
	/**
	 * Builds a new list containing the given values.
	 * <p>
	 * This method instantiates an {@link ArrayList}.
	 * 
	 * @param <V> Type of the the values.
	 * @param value1 The first value. May be <code>null</code>.
	 * @param value2 The second value. May be <code>null</code>.
	 * @param value3 The third value. May be <code>null</code>.
	 * @return The built list.
	 * @deprecated Use {@link com.trazere.core.collection.Lists#fromElements(Object, Object, Object)}.
	 */
	@Deprecated
	public static <V> List<V> fromValues(final V value1, final V value2, final V value3) {
		final List<V> list = new ArrayList<V>(3);
		list.add(value1);
		list.add(value2);
		list.add(value3);
		return list;
	}
	
	/**
	 * Builds a new list containing the given values.
	 * <p>
	 * This method instantiates an {@link ArrayList}.
	 * 
	 * @param <V> Type of the the values.
	 * @param values The values. May be <code>null</code>.
	 * @return The built list.
	 * @deprecated Use {@link com.trazere.core.collection.Lists#fromElements(Object...)}.
	 */
	@Deprecated
	public static <V> List<V> fromValues(final V... values) {
		assert null != values;
		
		final List<V> list = new ArrayList<V>(values.length);
		for (final V value : values) {
			list.add(value);
		}
		return list;
	}
	
	/**
	 * Builds a view of the given list in the reversed order.
	 * <p>
	 * The built list is backed by the given list, any modification to one list is reported on the other.
	 * 
	 * @param <E> Type of the elements.
	 * @param list List to wrap.
	 * @return The built list.
	 * @deprecated Use {@link com.trazere.core.collection.ListUtils#reversed(List)}.
	 */
	@Deprecated
	public static <E> List<E> reversed(final List<E> list) {
		assert null != list;
		
		return new AbstractList<E>() {
			@Override
			public int size() {
				return list.size();
			}
			
			@Override
			public Iterator<E> iterator() {
				return Iterators.reversed(list);
			}
			
			@Override
			public boolean add(final E e) {
				list.add(0, e);
				return true;
			}
			
			@Override
			public boolean remove(final Object o) {
				return list.remove(o);
			}
			
			@Override
			public boolean addAll(final int index, final Collection<? extends E> c) {
				if (c.isEmpty()) {
					return false;
				} else {
					final int addIndex = computeAddIndex(index);
					for (final E e : c) {
						add(addIndex, e);
					}
					return true;
				}
			}
			
			@Override
			public void clear() {
				list.clear();
			}
			
			@Override
			public E get(final int index) {
				return list.get(computeIndex(index));
			}
			
			@Override
			public E set(final int index, final E element) {
				return list.set(computeIndex(index), element);
			}
			
			@Override
			public void add(final int index, final E element) {
				list.add(computeAddIndex(index) + 1, element);
			}
			
			@Override
			public E remove(final int index) {
				return list.remove(computeIndex(index));
			}
			
			@Override
			public ListIterator<E> listIterator(final int index) {
				final ListIterator<E> iterator = list.listIterator(computeAddIndex(index));
				return new ListIterator<E>() {
					@Override
					public boolean hasNext() {
						return iterator.hasPrevious();
					}
					
					@Override
					public E next() {
						return iterator.previous();
					}
					
					@Override
					public boolean hasPrevious() {
						return iterator.hasNext();
					}
					
					@Override
					public E previous() {
						return iterator.next();
					}
					
					@Override
					public int nextIndex() {
						return computeIndex(iterator.previousIndex());
					}
					
					@Override
					public int previousIndex() {
						return computeIndex(iterator.nextIndex());
					}
					
					@Override
					public void remove() {
						iterator.remove();
					}
					
					@Override
					public void set(final E e) {
						iterator.set(e);
					}
					
					@Override
					public void add(final E e) {
						iterator.add(e);
						iterator.previous();
					}
				};
			}
			
			@Override
			public List<E> subList(final int fromIndex, final int toIndex) {
				return reversed(list.subList(fromIndex, toIndex));
			}
			
			private int computeIndex(final int index) {
				return list.size() - index - 1;
			}
			
			private int computeAddIndex(final int index) {
				return list.size() - index;
			}
		};
	}
	
	private Lists() {
		// Prevent instantiation.
	}
}
