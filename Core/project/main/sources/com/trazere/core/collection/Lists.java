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
import java.util.Collections;
import java.util.List;

/**
 * The {@link Lists} class provides various factories of {@link List lists}.
 * 
 * @see List
 * @since 2.0
 */
public class Lists {
	/**
	 * Builds an unmutable empty list.
	 * 
	 * @param <E> Type of the elements.
	 * @return The built list.
	 * @since 2.0
	 */
	public static <E> List<E> empty() {
		return Collections.emptyList();
	}
	
	/**
	 * Builds a new list containing the given element.
	 * 
	 * @param <E> Type of the element.
	 * @param element Element of the list to build.
	 * @return The built list.
	 * @since 2.0
	 */
	public static <E> List<E> fromElement(final E element) {
		final List<E> list = new ArrayList<>(1);
		list.add(element);
		return list;
	}
	
	/**
	 * Builds a new list containing the given elements.
	 * 
	 * @param <E> Type of the elements.
	 * @param element1 First element of the list to build.
	 * @param element2 Second element of the list to build.
	 * @return The built list.
	 * @since 2.0
	 */
	public static <E> List<E> fromElements(final E element1, final E element2) {
		final List<E> list = new ArrayList<>(2);
		list.add(element1);
		list.add(element2);
		return list;
	}
	
	/**
	 * Builds a new list containing the given elements.
	 * 
	 * @param <E> Type of the elements.
	 * @param element1 First element of the list to build.
	 * @param element2 Second element of the list to build.
	 * @param element3 Third element of the list to build.
	 * @return The built list.
	 * @since 2.0
	 */
	public static <E> List<E> fromElements(final E element1, final E element2, final E element3) {
		final List<E> list = new ArrayList<>(3);
		list.add(element1);
		list.add(element2);
		list.add(element3);
		return list;
	}
	
	/**
	 * Builds a new list containing the given elements.
	 * 
	 * @param <E> Type of the elements.
	 * @param element1 First element of the list to build.
	 * @param element2 Second element of the list to build.
	 * @param element3 Third element of the list to build.
	 * @param element4 Fourth element of the list to build.
	 * @return The built list.
	 * @since 2.0
	 */
	public static <E> List<E> fromElements(final E element1, final E element2, final E element3, final E element4) {
		final List<E> list = new ArrayList<>(4);
		list.add(element1);
		list.add(element2);
		list.add(element3);
		list.add(element4);
		return list;
	}
	
	/**
	 * Builds a new list containing the given elements.
	 * 
	 * @param <E> Type of the elements.
	 * @param element1 First element of the list to build.
	 * @param element2 Second element of the list to build.
	 * @param element3 Third element of the list to build.
	 * @param element4 Fourth element of the list to build.
	 * @param element5 Fifth element of the list to build.
	 * @return The built list.
	 * @since 2.0
	 */
	public static <E> List<E> fromElements(final E element1, final E element2, final E element3, final E element4, final E element5) {
		final List<E> list = new ArrayList<>(5);
		list.add(element1);
		list.add(element2);
		list.add(element3);
		list.add(element4);
		list.add(element5);
		return list;
	}
	
	/**
	 * Builds a new list containing the given elements.
	 * 
	 * @param <E> Type of the elements.
	 * @param elements Elements of the list to build
	 * @return The built list.
	 * @since 2.0
	 */
	@SafeVarargs
	public static <E> List<E> fromElements(final E... elements) {
		final List<E> list = new ArrayList<>(elements.length);
		for (final E element : elements) {
			list.add(element);
		}
		return list;
	}
	
	/**
	 * Builds a new list containing the elements provided by the given iterable
	 *
	 * @param <E> Type of the elements.
	 * @param iterable Iterable providing the elements of the list to build.
	 * @return The built list.
	 * @since 2.0
	 */
	public static <E> List<E> fromIterable(final Iterable<? extends E> iterable) {
		final List<E> list = new ArrayList<>();
		for (final E element : iterable) {
			list.add(element);
		}
		return list;
	}
	
	private Lists() {
		// Prevent instantiation.
	}
}
