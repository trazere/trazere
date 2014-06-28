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
package com.trazere.core.util;

import java.util.Comparator;

/**
 * The {@link MaybeComparators} class provides various comparators related to {@link Maybe}s.
 */
public class MaybeComparators {
	/**
	 * Builds a comparator of optional values according to the given comparator of values.
	 * <p>
	 * Absent values are considered less than available values. Available values are ordered according to the given comparator.
	 * 
	 * @param <T> Type of the values.
	 * @param comparator Comparator of the values.
	 * @return The built comparator.
	 * @see ComparatorUtils#compare(Comparator, Maybe, Maybe)
	 */
	public static <T> Comparator<Maybe<T>> maybe(final Comparator<? super T> comparator) {
		assert null != comparator;
		
		return new Comparator<Maybe<T>>() {
			@Override
			public int compare(final Maybe<T> value1, final Maybe<T> value2) {
				return ComparatorUtils.compare(comparator, value1, value2);
			}
		};
	}
	
	private MaybeComparators() {
		// Prevents instantiation.
	}
}
