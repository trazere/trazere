/*
 *  Copyright 2006-2012 Julien Dufour
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

import com.trazere.util.text.TextUtils;
import com.trazere.util.type.Maybe;
import com.trazere.util.type.TypeUtils;
import java.util.Comparator;

/**
 * The {@link Comparators} class provides various standard comparators.
 */
public class Comparators {
	/**
	 * Builds a comparator wich uses the natural order of comparable values and handle <code>null</code> values.
	 * 
	 * @param <T> Type of the values.
	 * @return The built comparator.
	 * @see LangUtils#compare(Comparable, Comparable)
	 */
	public static <T extends Comparable<T>> Comparator<T> comparable() {
		return new Comparator<T>() {
			@Override
			public int compare(final T object1, final T object2) {
				return LangUtils.compare(object1, object2);
			}
		};
	}
	
	/**
	 * Builds a comparator of string which ignores and handle <code>null</code> values.
	 * 
	 * @return The built comparator.
	 * @see TextUtils#compareIgnoreCase(String, String)
	 */
	public static Comparator<String> stringIgnoreCase() {
		return _STRING_IGNORE_CASE;
	}
	
	private static Comparator<String> _STRING_IGNORE_CASE = new Comparator<String>() {
		@Override
		public int compare(final String value1, final String value2) {
			return TextUtils.compareIgnoreCase(value1, value2);
		}
	};
	
	/**
	 * Builds a comparator of instances of {@link Maybe} using the given comparator of the wrapped values.
	 * 
	 * @param <T> Type of the values.
	 * @param comparator The comparator of the wrapped values.
	 * @return The built comparator.
	 * @see TypeUtils#compare(Comparator, Maybe, Maybe)
	 */
	public static <T> Comparator<Maybe<T>> maybe(final Comparator<? super T> comparator) {
		assert null != comparator;
		
		return new Comparator<Maybe<T>>() {
			@Override
			public int compare(final Maybe<T> value1, final Maybe<T> value2) {
				return TypeUtils.compare(comparator, value1, value2);
			}
		};
	}
	
	private Comparators() {
		// Prevents instantiation.
	}
}
