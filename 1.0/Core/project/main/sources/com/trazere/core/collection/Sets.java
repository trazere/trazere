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

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * The {@link Sets} class provides various factories of {@link Set sets}.
 * 
 * @see Set
 * @since 1.0
 */
public class Sets {
	/**
	 * Builds an unmutable empty set.
	 * 
	 * @param <E> Type of the elements.
	 * @return The built set.
	 * @since 1.0
	 */
	public static <E> Set<E> empty() {
		return Collections.emptySet();
	}
	
	/**
	 * Builds a set containing the given element.
	 * 
	 * @param <E> Type of the the element.
	 * @param element Element of the set to build.
	 * @return The built set.
	 * @since 1.0
	 */
	public static <E> Set<E> fromElement(final E element) {
		final Set<E> set = new HashSet<>(1);
		set.add(element);
		return set;
	}
	
	/**
	 * Builds a set containing the given elements.
	 * 
	 * @param <E> Type of the the elements.
	 * @param element1 First element of the set to build.
	 * @param element2 Second element of the set to build.
	 * @return The built set.
	 * @since 1.0
	 */
	public static <E> Set<E> fromElements(final E element1, final E element2) {
		final Set<E> set = new HashSet<>(2);
		set.add(element1);
		set.add(element2);
		return set;
	}
	
	/**
	 * Builds a set containing the given elements.
	 * 
	 * @param <E> Type of the the elements.
	 * @param element1 First element of the set to build.
	 * @param element2 Second element of the set to build.
	 * @param element3 Third element of the set to build.
	 * @return The built set.
	 * @since 1.0
	 */
	public static <E> Set<E> fromElements(final E element1, final E element2, final E element3) {
		final Set<E> set = new HashSet<>(3);
		set.add(element1);
		set.add(element2);
		set.add(element3);
		return set;
	}
	
	/**
	 * Builds a set containing the given elements.
	 * 
	 * @param <E> Type of the the elements.
	 * @param element1 First element of the set to build.
	 * @param element2 Second element of the set to build.
	 * @param element3 Third element of the set to build.
	 * @param element4 Fourth element of the set to build.
	 * @return The built set.
	 * @since 1.0
	 */
	public static <E> Set<E> fromElements(final E element1, final E element2, final E element3, final E element4) {
		final Set<E> set = new HashSet<>(4);
		set.add(element1);
		set.add(element2);
		set.add(element3);
		set.add(element4);
		return set;
	}
	
	/**
	 * Builds a set containing the given elements.
	 * 
	 * @param <E> Type of the the elements.
	 * @param element1 First element of the set to build.
	 * @param element2 Second element of the set to build.
	 * @param element3 Third element of the set to build.
	 * @param element4 Fourth element of the set to build.
	 * @param element5 Fifth element of the set to build.
	 * @return The built set.
	 * @since 1.0
	 */
	public static <E> Set<E> fromElements(final E element1, final E element2, final E element3, final E element4, final E element5) {
		final Set<E> set = new HashSet<>(5);
		set.add(element1);
		set.add(element2);
		set.add(element3);
		set.add(element4);
		set.add(element5);
		return set;
	}
	
	/**
	 * Builds a set containing the given elements.
	 * 
	 * @param <E> Type of the the elements.
	 * @param elements Elements of the set to build
	 * @return The built set.
	 * @since 1.0
	 */
	@SafeVarargs
	public static <E> Set<E> fromElements(final E... elements) {
		final Set<E> set = new HashSet<>(elements.length);
		for (final E element : elements) {
			set.add(element);
		}
		return set;
	}
	
	/**
	 * Builds a set containing the elements provided by the given iterable.
	 * 
	 * @param <E> Type of the the elements.
	 * @param iterable Iterable providing the elements of the set to build.
	 * @return The built set.
	 * @since 1.0
	 */
	public static <E> Set<E> fromIterable(final Iterable<? extends E> iterable) {
		final Set<E> set = new HashSet<>();
		for (final E element : iterable) {
			set.add(element);
		}
		return set;
	}
	
	private Sets() {
		// Prevent instantiation.
	}
}
