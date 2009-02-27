/*
 *  Copyright 2006-2009 Julien Dufour
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
package com.trazere.util.lang;

import java.util.Comparator;

/**
 * The {@link LangUtils} class provides various helpers regarding the manipulation of objets.
 */
public class LangUtils {
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
	 * Cast the given object to some subtype.
	 * <p>
	 * This methods aims to be called where regular downcasts would be used in order to track them in the code. It is a little safer than {@link #cast(Object)}
	 * because the result type can statically be constrained by the argument type but should still be used as seldom as possible.
	 * 
	 * @param <T> Original type of the object.
	 * @param <R> Resulting type of the object.
	 * @param object Object to cast.
	 * @return The casted object.
	 */
	@SuppressWarnings("unchecked")
	public static <T, R extends T> R downcast(final T object) {
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
		return (object1 == object2) || (null != object1 && object1.equals(object2));
	}
	
	/**
	 * Build a natural value comparator.
	 * 
	 * @param <T> Type of the value.
	 * @return The build comparator.
	 */
	public static <T extends Comparable<T>> Comparator<T> comparator() {
		return new Comparator<T>() {
			public int compare(final T object1, final T object2) {
				return object1.compareTo(object2);
			}
		};
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
		assert null != comparator;
		
		// Compare.
		if (null == object1) {
			return null == object2 ? 0 : -1;
		} else {
			return null == object2 ? 1 : comparator.compare(object1, object2);
		}
	}
	
	private LangUtils() {
		// Prevent instantiation.
	}
}