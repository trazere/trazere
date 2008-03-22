package com.trazere.test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.trazere.util.collection.CollectionFactories;
import com.trazere.util.collection.CollectionUtils;
import com.trazere.util.function.Filter;
import com.trazere.util.function.Function;
import com.trazere.util.function.FunctionUtils;

/**
 * DOCME
 */
public class Generics {
	public static void collections() {
		final List<Integer> numbers1 = CollectionUtils.list(0, 1, 2);
		System.out.println(numbers1);
		final List<Object> numbers2 = CollectionUtils.<Object>list(0, 1, 2);
		System.out.println(numbers2);
		
		final List<Object> objects1 = CollectionUtils.<Object>list(0, "a");
		System.out.println(objects1);
	}
	
	public static void functions() {
		final List<Integer> numbers = CollectionUtils.list(0, 1, 2);
		final Filter<Integer> isEven = new Filter<Integer>() {
			public boolean filter(final Integer value) {
				return 0 == (value % 2);
			}
		};
		final Filter<Object> isNotNull = new Filter<Object>() {
			public boolean filter(final Object value) {
				return null != value;
			}
		};
		
		final List<Integer> evens1 = FunctionUtils.filter(numbers, isEven, new ArrayList<Integer>());
		System.out.println(evens1);
		final List<Integer> evens2 = FunctionUtils.filter(numbers, isEven, CollectionFactories.<Integer>arrayList());
		System.out.println(evens2);
		
		final List<Object> evens3 = FunctionUtils.filter(numbers, isEven, new ArrayList<Object>());
		System.out.println(evens3);
		final List<Object> evens4 = FunctionUtils.filter(numbers, isEven, CollectionFactories.<Object>arrayList());
		System.out.println(evens4);
		
		final Set<Integer> notNulls1 = FunctionUtils.filter(numbers, isNotNull, new HashSet<Integer>());
		System.out.println(notNulls1);
		final Set<Integer> notNulls2 = FunctionUtils.filter(numbers, isNotNull, CollectionFactories.<Integer>hashSet());
		System.out.println(notNulls2);
		
		final Function<Object, String> toString = new Function<Object, String>() {
			public String apply(final Object value) {
				return null != value ? value.toString() : "null";
			}
		};
		
		final List<String> strings1 = FunctionUtils.map(numbers, toString, false, new ArrayList<String>());
		System.out.println(strings1);
		final List<String> strings2 = FunctionUtils.map(numbers, toString, false, CollectionFactories.<String>arrayList());
		System.out.println(strings2);
	}
}
