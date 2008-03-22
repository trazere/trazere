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
package com.trazere.util;

import java.util.Comparator;

/**
 * The <code>ObjectUtils</code> class provides various helpers regarding the manipulation of objets.
 */
public class ObjectUtils {
	/**
	 * Cast the given object to any type.
	 * <p>
	 * This methods aims to work around various limitations of the Java type system where regular casts cannot be used. It performs no verifications whatsoever
	 * and should be used as seldom as possible because it is inherently unsafe.
	 * 
	 * @param <R> Resulting type of the object.
	 * @param object Object to cast.
	 * @return The casted object.
	 */
	@SuppressWarnings("unchecked")
	public static <R> R cast(final Object object) {
		return (R) object;
	}
	
	/**
	 * Test for equality of the given objects.
	 * <p>
	 * This method supports <code>null</code> values.
	 * 
	 * @param <T> Type of the values.
	 * @param object1 First value to compare. May be <code>null</code>.
	 * @param object2 Second value to compare. May be <code>null</code>.
	 * @return <code>true</code> if the values are both <code>null</code> or both not <code>null</code> and equal.
	 * @see Comparable#compareTo(Object)
	 */
	public static <T extends Object> boolean equals(final T object1, final T object2) {
		// Compare.
		return (null == object1 && null == object2) || (null != object1 && null != object2 && object1.equals(object2));
	}
	
	/**
	 * Compare the given comparable objets.
	 * <p>
	 * This method supports comparisons of <code>null</code> values. <code>null</code> values are considered as less than non <code>null</code> values.
	 * 
	 * @param <T> Type of the values.
	 * @param object1 First value to compare. May be <code>null</code>.
	 * @param object2 Second value to compare. May be <code>null</code>.
	 * @return The result of the comparison as defined by the {@link Comparable#compareTo(Object)} method.
	 * @see Comparable#compareTo(Object)
	 */
	public static <T extends Comparable<T>> int compare(final T object1, final T object2) {
		// Compare.
		if (null == object1) {
			return null == object2 ? 0 : -1;
		} else {
			return null == object2 ? 1 : object1.compareTo(object2);
		}
	}
	
	/**
	 * Compare the given objets using the given comparator.
	 * <p>
	 * This method supports comparisons of <code>null</code> values. <code>null</code> values are considered as less than non <code>null</code> values.
	 * 
	 * @param <T> Type of the values.
	 * @param object1 First value to compare. May be <code>null</code>.
	 * @param object2 Second value to compare. May be <code>null</code>.
	 * @param comparator Comparator to use.
	 * @return The result of the comparison as defined by the {@link Comparator#compare(Object, Object)} method.
	 * @see Comparable#compareTo(Object)
	 */
	public static <T> int compare(final T object1, final T object2, final Comparator<T> comparator) {
		// Checks.
		Assert.notNull(comparator);
		
		// Compare.
		if (null == object1) {
			return null == object2 ? 0 : -1;
		} else {
			return null == object2 ? 1 : comparator.compare(object1, object2);
		}
	}
	
	private ObjectUtils() {
		// Prevent instantiation.
	}
}
