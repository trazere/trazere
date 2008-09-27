package com.trazere.test;

import com.trazere.util.collection.CollectionUtils;
import com.trazere.util.function.Function;
import com.trazere.util.function.FunctionUtils;
import com.trazere.util.function.Predicate;
import com.trazere.util.type.Maybe;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
		final Predicate<Integer, RuntimeException> isEven = new Predicate<Integer, RuntimeException>() {
			public boolean evaluate(final Integer value) {
				return 0 == (value % 2);
			}
		};
		final Predicate<Object, RuntimeException> isNotNull = new Predicate<Object, RuntimeException>() {
			public boolean evaluate(final Object value) {
				return null != value;
			}
		};
		
		final List<Integer> evens1 = FunctionUtils.filter(isEven, numbers, new ArrayList<Integer>());
		System.out.println(evens1);
		
		final List<Object> evens2 = FunctionUtils.filter(isEven, numbers, new ArrayList<Object>());
		System.out.println(evens2);
		
		final Set<Integer> notNulls1 = FunctionUtils.filter(isNotNull, numbers, new HashSet<Integer>());
		System.out.println(notNulls1);
		
		final Function<Object, Maybe<String>, RuntimeException> toString = new Function<Object, Maybe<String>, RuntimeException>() {
			public Maybe<String> evaluate(final Object value) {
				return Maybe.some(null != value ? value.toString() : "null");
			}
		};
		
		final List<String> strings1 = FunctionUtils.map(toString, numbers, new ArrayList<String>());
		System.out.println(strings1);
	}
}
