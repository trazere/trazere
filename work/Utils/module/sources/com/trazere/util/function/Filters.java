/*
 *  Copyright 2008 Julien Dufour
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
package com.trazere.util.function;

import java.util.Collection;

/**
 * The <code>Filters</code> class provides various standard filter functions.
 * 
 * @see Filter
 * @see Filter2
 */
public class Filters {
	private static final Filter<Object> ALL = new Filter<Object>() {
		public boolean filter(final Object value) {
			return true;
		}
	};
	
	/**
	 * Build a filter function which accepts all values.
	 * 
	 * @param <T> Type of the argument values.
	 * @return The filter function.
	 */
	@SuppressWarnings("unchecked")
	public static <T> Filter<T> all() {
		return (Filter<T>) ALL;
	}
	
	private static final Filter<Object> NONE = new Filter<Object>() {
		public boolean filter(final Object value) {
			return false;
		}
	};
	
	/**
	 * Build a filter function which accepts no values.
	 * 
	 * @param <T> Type of the argument values.
	 * @return The filter function.
	 */
	@SuppressWarnings("unchecked")
	public static <T> Filter<T> none() {
		return (Filter<T>) NONE;
	}
	
	private static final Filter2<Object, Object> ALL2 = new Filter2<Object, Object>() {
		public boolean filter(final Object value1, final Object value2) {
			return true;
		}
	};
	
	/**
	 * Build a two arguments filter function which accepts all values.
	 * 
	 * @param <T1> Type of the first argument values.
	 * @param <T2> Type of the second argument values.
	 * @return The filter function.
	 */
	@SuppressWarnings("unchecked")
	public static <T1, T2> Filter2<T1, T2> all2() {
		return (Filter2<T1, T2>) ALL2;
	}
	
	private static final Filter2<Object, Object> NONE2 = new Filter2<Object, Object>() {
		public boolean filter(final Object value1, final Object value2) {
			return false;
		}
	};
	
	/**
	 * Build a two arguments filter function which accepts no values.
	 * 
	 * @param <T1> Type of the first argument values.
	 * @param <T2> Type of the second argument values.
	 * @return The filter function.
	 */
	@SuppressWarnings("unchecked")
	public static <T1, T2> Filter2<T1, T2> none2() {
		return (Filter2<T1, T2>) NONE2;
	}
	
	/**
	 * Build a filter function which accepts the given values.
	 * 
	 * @param <T> Type of the argument values.
	 * @param values Values accepted by the filter function to build.
	 * @return The filter function.
	 */
	public static <T> Filter<T> values(final Collection<T> values) {
		return new Filter<T>() {
			public boolean filter(final T value) {
				return values.contains(value);
			}
		};
	}
	
	/**
	 * Build a two arguments filter function which accepts the values whose first argument is included in the given values.
	 * 
	 * @param <T1> Type of the first argument values.
	 * @param <T2> Type of the second argument values.
	 * @param values Values accepted by the filter function to build.
	 * @return The filter function.
	 */
	public static <T1, T2> Filter2<T1, T2> values1(final Collection<T1> values) {
		return new Filter2<T1, T2>() {
			public boolean filter(final T1 value1, final T2 value2) {
				return values.contains(value1);
			}
		};
	}
	
	/**
	 * Build a two arguments filter function which accepts the values whose second argument is included in the given values.
	 * 
	 * @param <T1> Type of the first argument values.
	 * @param <T2> Type of the second argument values.
	 * @param values Values accepted by the filter function to build.
	 * @return The filter function.
	 */
	public static <T1, T2> Filter2<T1, T2> values2(final Collection<T2> values) {
		return new Filter2<T1, T2>() {
			public boolean filter(final T1 value1, final T2 value2) {
				return values.contains(value2);
			}
		};
	}
	
	private Filters() {
		// Prevent instantiation.
	}
}
