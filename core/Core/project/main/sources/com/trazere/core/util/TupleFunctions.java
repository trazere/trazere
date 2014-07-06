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
import com.trazere.core.functional.Function2;
import com.trazere.core.functional.Function3;
import com.trazere.core.functional.Function4;
import com.trazere.core.functional.Function5;

/**
 * The {@link TupleFunctions} class provides various functions related to tuples.
 */
public class TupleFunctions {
	/**
	 * Builds a function that builds 1-tuples.
	 * 
	 * @param <E1> Type of the first element.
	 * @return The built function.
	 */
	@SuppressWarnings("unchecked")
	public static <E1> Function<E1, Tuple1<E1>> tuple1() {
		return (Function<E1, Tuple1<E1>>) TUPLE1;
	}
	
	private static final Function<?, ? extends Tuple1<?>> TUPLE1 = new Function<Object, Tuple1<Object>>() {
		@Override
		public Tuple1<Object> evaluate(final Object e1) {
			return new Tuple1<>(e1);
		}
	};
	
	/**
	 * Builds a function that builds 2-tuples.
	 * 
	 * @param <E1> Type of the first element.
	 * @param <E2> Type of the second element.
	 * @return The built function.
	 */
	@SuppressWarnings("unchecked")
	public static <E1, E2> Function2<E1, E2, Tuple2<E1, E2>> tuple2() {
		return (Function2<E1, E2, Tuple2<E1, E2>>) TUPLE2;
	}
	
	private static final Function2<?, ?, ? extends Tuple2<?, ?>> TUPLE2 = new Function2<Object, Object, Tuple2<Object, Object>>() {
		@Override
		public Tuple2<Object, Object> evaluate(final Object e1, final Object e2) {
			return new Tuple2<>(e1, e2);
		}
	};
	
	/**
	 * Builds a function that builds 3-tuples.
	 * 
	 * @param <E1> Type of the first element.
	 * @param <E2> Type of the second element.
	 * @param <E3> Type of the third element.
	 * @return The built function.
	 */
	@SuppressWarnings("unchecked")
	public static <E1, E2, E3> Function3<E1, E2, E3, Tuple3<E1, E2, E3>> tuple3() {
		return (Function3<E1, E2, E3, Tuple3<E1, E2, E3>>) TUPLE3;
	}
	
	private static final Function3<?, ?, ?, ? extends Tuple3<?, ?, ?>> TUPLE3 = new Function3<Object, Object, Object, Tuple3<Object, Object, Object>>() {
		@Override
		public Tuple3<Object, Object, Object> evaluate(final Object e1, final Object e2, final Object e3) {
			return new Tuple3<>(e1, e2, e3);
		}
	};
	
	/**
	 * Builds a function that builds 4-tuples.
	 * 
	 * @param <E1> Type of the first element.
	 * @param <E2> Type of the second element.
	 * @param <E3> Type of the third element.
	 * @param <E4> Type of the fourth element.
	 * @return The built function.
	 */
	@SuppressWarnings("unchecked")
	public static <E1, E2, E3, E4> Function4<E1, E2, E3, E4, Tuple4<E1, E2, E3, E4>> tuple4() {
		return (Function4<E1, E2, E3, E4, Tuple4<E1, E2, E3, E4>>) TUPLE4;
	}
	
	private static final Function4<?, ?, ?, ?, ? extends Tuple4<?, ?, ?, ?>> TUPLE4 = new Function4<Object, Object, Object, Object, Tuple4<Object, Object, Object, Object>>() {
		@Override
		public Tuple4<Object, Object, Object, Object> evaluate(final Object e1, final Object e2, final Object e3, final Object e4) {
			return new Tuple4<>(e1, e2, e3, e4);
		}
	};
	
	/**
	 * Builds a function that builds 5-tuples.
	 * 
	 * @param <E1> Type of the first element.
	 * @param <E2> Type of the second element.
	 * @param <E3> Type of the third element.
	 * @param <E4> Type of the fourth element.
	 * @param <E5> Type of the fifth element.
	 * @return The built function.
	 */
	@SuppressWarnings("unchecked")
	public static <E1, E2, E3, E4, E5> Function5<E1, E2, E3, E4, E5, Tuple5<E1, E2, E3, E4, E5>> tuple5() {
		return (Function5<E1, E2, E3, E4, E5, Tuple5<E1, E2, E3, E4, E5>>) TUPLE5;
	}
	
	private static final Function5<?, ?, ?, ?, ?, ? extends Tuple5<?, ?, ?, ?, ?>> TUPLE5 = new Function5<Object, Object, Object, Object, Object, Tuple5<Object, Object, Object, Object, Object>>() {
		@Override
		public Tuple5<Object, Object, Object, Object, Object> evaluate(final Object e1, final Object e2, final Object e3, final Object e4, final Object e5) {
			return new Tuple5<>(e1, e2, e3, e4, e5);
		}
	};
	
	/**
	 * Builds a function that gets the first element of the tuples.
	 * 
	 * @param <E1> Type of the first elements.
	 * @return The built function.
	 */
	@SuppressWarnings("unchecked")
	public static <E1> Function<Tuple1<? extends E1>, E1> get1() {
		return (Function<Tuple1<? extends E1>, E1>) GET1;
	}
	
	private static final Function<? extends Tuple1<?>, ?> GET1 = new Function<Tuple1<Object>, Object>() {
		@Override
		public Object evaluate(final Tuple1<Object> tuple) {
			return tuple.get1();
		}
	};
	
	/**
	 * Builds a function that gets the second element of tuples.
	 * 
	 * @param <E2> Type of the second elements.
	 * @return The built function.
	 */
	@SuppressWarnings("unchecked")
	public static <E2> Function<Tuple2<?, ? extends E2>, E2> get2() {
		return (Function<Tuple2<?, ? extends E2>, E2>) GET2;
	}
	
	private static final Function<? extends Tuple2<?, ?>, ?> GET2 = new Function<Tuple2<Object, Object>, Object>() {
		@Override
		public Object evaluate(final Tuple2<Object, Object> tuple) {
			return tuple.get2();
		}
	};
	
	/**
	 * Builds a function that gets the third element of tuples.
	 * 
	 * @param <E3> Type of the third elements.
	 * @return The built function.
	 */
	@SuppressWarnings("unchecked")
	public static <E3> Function<Tuple3<?, ?, ? extends E3>, E3> get3() {
		return (Function<Tuple3<?, ?, ? extends E3>, E3>) GET3;
	}
	
	private static final Function<? extends Tuple3<?, ?, ?>, ?> GET3 = new Function<Tuple3<Object, Object, Object>, Object>() {
		@Override
		public Object evaluate(final Tuple3<Object, Object, Object> tuple) {
			return tuple.get3();
		}
	};
	
	/**
	 * Builds a function that gets the fourth element of tuples.
	 * 
	 * @param <E4> Type of the fourth elements.
	 * @return The built function.
	 */
	@SuppressWarnings("unchecked")
	public static <E4> Function<Tuple4<?, ?, ?, ? extends E4>, E4> get4() {
		return (Function<Tuple4<?, ?, ?, ? extends E4>, E4>) GET4;
	}
	
	private static final Function<? extends Tuple4<?, ?, ?, ?>, ?> GET4 = new Function<Tuple4<Object, Object, Object, Object>, Object>() {
		@Override
		public Object evaluate(final Tuple4<Object, Object, Object, Object> tuple) {
			return tuple.get4();
		}
	};
	
	/**
	 * Builds a function that gets the fifth element of tuples.
	 * 
	 * @param <E5> Type of the fifth elements.
	 * @return The built function.
	 */
	@SuppressWarnings("unchecked")
	public static <E5> Function<Tuple5<?, ?, ?, ?, ? extends E5>, E5> get5() {
		return (Function<Tuple5<?, ?, ?, ?, ? extends E5>, E5>) GET5;
	}
	
	private static final Function<? extends Tuple5<?, ?, ?, ?, ?>, ?> GET5 = new Function<Tuple5<Object, Object, Object, Object, Object>, Object>() {
		@Override
		public Object evaluate(final Tuple5<Object, Object, Object, Object, Object> tuple) {
			return tuple.get5();
		}
	};
	
	/**
	 * Builds a function that gets the sixth element of tuples.
	 * 
	 * @param <E6> Type of the sixth elements.
	 * @return The built function.
	 */
	@SuppressWarnings("unchecked")
	public static <E6> Function<Tuple6<?, ?, ?, ?, ?, ? extends E6>, E6> get6() {
		return (Function<Tuple6<?, ?, ?, ?, ?, ? extends E6>, E6>) GET6;
	}
	
	private static final Function<? extends Tuple6<?, ?, ?, ?, ?, ?>, ?> GET6 = new Function<Tuple6<Object, Object, Object, Object, Object, Object>, Object>() {
		@Override
		public Object evaluate(final Tuple6<Object, Object, Object, Object, Object, Object> tuple) {
			return tuple.get6();
		}
	};
	
	/**
	 * Builds a function that gets the seventh element of tuples.
	 * 
	 * @param <E7> Type of the seventh elements.
	 * @return The built function.
	 */
	@SuppressWarnings("unchecked")
	public static <E7> Function<Tuple7<?, ?, ?, ?, ?, ?, ? extends E7>, E7> get7() {
		return (Function<Tuple7<?, ?, ?, ?, ?, ?, ? extends E7>, E7>) GET7;
	}
	
	private static final Function<? extends Tuple7<?, ?, ?, ?, ?, ?, ?>, ?> GET7 = new Function<Tuple7<Object, Object, Object, Object, Object, Object, Object>, Object>() {
		@Override
		public Object evaluate(final Tuple7<Object, Object, Object, Object, Object, Object, Object> tuple) {
			return tuple.get7();
		}
	};
	
	/**
	 * Builds a function that gets the eighth element of tuples.
	 * 
	 * @param <E8> Type of the eighth elements.
	 * @return The built function.
	 */
	@SuppressWarnings("unchecked")
	public static <E8> Function<Tuple8<?, ?, ?, ?, ?, ?, ?, ? extends E8>, E8> get8() {
		return (Function<Tuple8<?, ?, ?, ?, ?, ?, ?, ? extends E8>, E8>) GET8;
	}
	
	private static final Function<? extends Tuple8<?, ?, ?, ?, ?, ?, ?, ?>, ?> GET8 = new Function<Tuple8<Object, Object, Object, Object, Object, Object, Object, Object>, Object>() {
		@Override
		public Object evaluate(final Tuple8<Object, Object, Object, Object, Object, Object, Object, Object> tuple) {
			return tuple.get8();
		}
	};
	
	/**
	 * Builds a function that gets the ninth element of tuples.
	 * 
	 * @param <E9> Type of the ninth elements.
	 * @return The built function.
	 */
	@SuppressWarnings("unchecked")
	public static <E9> Function<Tuple9<?, ?, ?, ?, ?, ?, ?, ?, ? extends E9>, E9> get9() {
		return (Function<Tuple9<?, ?, ?, ?, ?, ?, ?, ?, ? extends E9>, E9>) GET9;
	}
	
	private static final Function<? extends Tuple9<?, ?, ?, ?, ?, ?, ?, ?, ?>, ?> GET9 = new Function<Tuple9<Object, Object, Object, Object, Object, Object, Object, Object, Object>, Object>() {
		@Override
		public Object evaluate(final Tuple9<Object, Object, Object, Object, Object, Object, Object, Object, Object> tuple) {
			return tuple.get9();
		}
	};
	
	/**
	 * Builds a function that gets the tenth element of tuples.
	 * 
	 * @param <E10> Type of the tenth elements.
	 * @return The built function.
	 */
	@SuppressWarnings("unchecked")
	public static <E10> Function<Tuple10<?, ?, ?, ?, ?, ?, ?, ?, ?, ? extends E10>, E10> get10() {
		return (Function<Tuple10<?, ?, ?, ?, ?, ?, ?, ?, ?, ? extends E10>, E10>) GET10;
	}
	
	private static final Function<? extends Tuple10<?, ?, ?, ?, ?, ?, ?, ?, ?, ?>, ?> GET10 = new Function<Tuple10<Object, Object, Object, Object, Object, Object, Object, Object, Object, Object>, Object>() {
		@Override
		public Object evaluate(final Tuple10<Object, Object, Object, Object, Object, Object, Object, Object, Object, Object> tuple) {
			return tuple.get10();
		}
	};
	
	private TupleFunctions() {
		// Prevent instantiation.
	}
}
