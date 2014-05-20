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

import com.trazere.core.functional.Function;
import java.util.Comparator;

/**
 * The {@link ComparatorUtils} class provides various helpers regarding comparators.
 */
public class ComparatorUtils {
	/**
	 * The {@link DummyComparable} interface provides a placeholder type for comparables.
	 */
	public interface DummyComparable
	extends Comparable<DummyComparable> {
		// Nothing to do.
	}
	
	/**
	 * Maps the given comparator using the given function.
	 * 
	 * @param <T1> Type of the values.
	 * @param <T2> Type of the mapped values.
	 * @param comparator Comparator to map.
	 * @param function Mapping function to use.
	 * @return The built comparator.
	 * @see MapComparator
	 */
	public static <T1, T2> Comparator<T1> map(final Comparator<? super T2> comparator, final Function<? super T1, ? extends T2> function) {
		assert null != function;
		
		return new MapComparator<T1, T2>(comparator) {
			@Override
			protected T2 mapValue(final T1 object) {
				return function.evaluate(object);
			}
		};
	}
	
	private ComparatorUtils() {
		// Prevent instantiation.
	}
}
