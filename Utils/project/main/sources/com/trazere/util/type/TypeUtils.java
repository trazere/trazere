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
package com.trazere.util.type;

import java.util.Comparator;

/**
 * The {@link TypeUtils} class provides various helpers regarding types.
 * 
 * @deprecated Use {@link com.trazere.core}.
 */
@Deprecated
public class TypeUtils {
	/**
	 * Gets the boolean value of the given {@link Maybe} instance using the given default value when the value is {@link Maybe.None}.
	 * 
	 * @param value The value.
	 * @param defaultValue The default value. May be <code>null</code>.
	 * @return The value. May be <code>null</code>.
	 * @deprecated Use {@link com.trazere.core.util.MaybeUtils#get(com.trazere.core.util.Maybe, boolean)}.
	 */
	@Deprecated
	public static boolean get(final Maybe<Boolean> value, final boolean defaultValue) {
		assert null != value;
		
		return value.isSome() ? value.asSome().getValue().booleanValue() : defaultValue;
	}
	
	/**
	 * Gets the byte value of the given {@link Maybe} instance using the given default value when the value is {@link Maybe.None}.
	 * 
	 * @param value The value.
	 * @param defaultValue The default value. May be <code>null</code>.
	 * @return The value. May be <code>null</code>.
	 * @deprecated Use {@link com.trazere.core.util.MaybeUtils#get(com.trazere.core.util.Maybe, byte)}.
	 */
	@Deprecated
	public static int get(final Maybe<Byte> value, final byte defaultValue) {
		assert null != value;
		
		return value.isSome() ? value.asSome().getValue().byteValue() : defaultValue;
	}
	
	/**
	 * Gets the short value of the given {@link Maybe} instance using the given default value when the value is {@link Maybe.None}.
	 * 
	 * @param value The value.
	 * @param defaultValue The default value. May be <code>null</code>.
	 * @return The value. May be <code>null</code>.
	 * @deprecated Use {@link com.trazere.core.util.MaybeUtils#get(com.trazere.core.util.Maybe, short)}.
	 */
	@Deprecated
	public static int get(final Maybe<Short> value, final short defaultValue) {
		assert null != value;
		
		return value.isSome() ? value.asSome().getValue().shortValue() : defaultValue;
	}
	
	/**
	 * Gets the integer value of the given {@link Maybe} instance using the given default value when the value is {@link Maybe.None}.
	 * 
	 * @param value The value.
	 * @param defaultValue The default value. May be <code>null</code>.
	 * @return The value. May be <code>null</code>.
	 * @deprecated Use {@link com.trazere.core.util.MaybeUtils#get(com.trazere.core.util.Maybe, int)}.
	 */
	@Deprecated
	public static int get(final Maybe<Integer> value, final int defaultValue) {
		assert null != value;
		
		return value.isSome() ? value.asSome().getValue().intValue() : defaultValue;
	}
	
	/**
	 * Gets the long value of the given {@link Maybe} instance using the given default value when the value is {@link Maybe.None}.
	 * 
	 * @param value The value.
	 * @param defaultValue The default value. May be <code>null</code>.
	 * @return The value. May be <code>null</code>.
	 * @deprecated Use {@link com.trazere.core.util.MaybeUtils#get(com.trazere.core.util.Maybe, long)}.
	 */
	@Deprecated
	public static long get(final Maybe<Long> value, final long defaultValue) {
		assert null != value;
		
		return value.isSome() ? value.asSome().getValue().longValue() : defaultValue;
	}
	
	/**
	 * Gets the float value of the given {@link Maybe} instance using the given default value when the value is {@link Maybe.None}.
	 * 
	 * @param value The value.
	 * @param defaultValue The default value. May be <code>null</code>.
	 * @return The value. May be <code>null</code>.
	 * @deprecated Use {@link com.trazere.core.util.MaybeUtils#get(com.trazere.core.util.Maybe, float)}.
	 */
	@Deprecated
	public static float get(final Maybe<Float> value, final float defaultValue) {
		assert null != value;
		
		return value.isSome() ? value.asSome().getValue().floatValue() : defaultValue;
	}
	
	/**
	 * Gets the double value of the given {@link Maybe} instance using the given default value when the value is {@link Maybe.None}.
	 * 
	 * @param value The value.
	 * @param defaultValue The default value. May be <code>null</code>.
	 * @return The value. May be <code>null</code>.
	 * @deprecated Use {@link com.trazere.core.util.MaybeUtils#get(com.trazere.core.util.Maybe, double)}.
	 */
	@Deprecated
	public static double get(final Maybe<Double> value, final double defaultValue) {
		assert null != value;
		
		return value.isSome() ? value.asSome().getValue().doubleValue() : defaultValue;
	}
	
	/**
	 * Gets the character value of the given {@link Maybe} instance using the given default value when the value is {@link Maybe.None}.
	 * 
	 * @param value The value.
	 * @param defaultValue The default value. May be <code>null</code>.
	 * @return The value. May be <code>null</code>.
	 * @deprecated Use {@link com.trazere.core.util.MaybeUtils#get(com.trazere.core.util.Maybe, char)}.
	 */
	@Deprecated
	@SuppressWarnings("javadoc")
	public static char get(final Maybe<Character> value, final char defaultValue) {
		assert null != value;
		
		return value.isSome() ? value.asSome().getValue().charValue() : defaultValue;
	}
	
	/**
	 * Compares the values wrapped in the given maybe instances using the given comparator.
	 * <p>
	 * <code>None</code> instances are considered as less than <code>Some</code> instances.
	 * 
	 * @param <T> Type of the values.
	 * @param comparator The comparator.
	 * @param value1 The first instance.
	 * @param value2 The second instance.
	 * @return The result of the comparison as defined by the {@link Comparator#compare(Object, Object)} method.
	 * @see Comparable#compareTo(Object)
	 * @deprecated Use {@link com.trazere.core.util.MaybeComparators#maybe(Comparator)}.
	 */
	@Deprecated
	public static <T> int compare(final Comparator<T> comparator, final Maybe<? extends T> value1, final Maybe<? extends T> value2) {
		assert null != comparator;
		assert null != value1;
		assert null != value2;
		
		if (value1.isNone()) {
			return value2.isNone() ? 0 : -1;
		} else {
			return value2.isNone() ? 1 : comparator.compare(value1.asSome().getValue(), value2.asSome().getValue());
		}
	}
	
	/**
	 * Adapts the given util maybe to a core maybe.
	 * 
	 * @param <T> Type of the value.
	 * @param maybe Util maybe to adapt.
	 * @return The adapted core maybe.
	 * @deprecated Use {@link com.trazere.core.util.Maybe}.
	 */
	@Deprecated
	public static <T> com.trazere.core.util.Maybe<T> toMaybe(final Maybe<? extends T> maybe) {
		if (maybe.isSome()) {
			return com.trazere.core.util.Maybe.<T>some(maybe.asSome().getValue());
		} else {
			return com.trazere.core.util.Maybe.none();
		}
	}
	
	/**
	 * Adapts the given core maybe to an util maybe.
	 * 
	 * @param <T> Type of the value.
	 * @param maybe Core maybe to adapt.
	 * @return The adapted util maybe.
	 * @deprecated Use {@link com.trazere.core.util.Maybe}.
	 */
	@Deprecated
	public static <T> Maybe<T> fromMaybe(final com.trazere.core.util.Maybe<? extends T> maybe) {
		if (maybe.isSome()) {
			return Maybe.<T>some(maybe.asSome().getValue());
		} else {
			return Maybe.none();
		}
	}
	
	/**
	 * Adapts the given util tuple to a core tuple.
	 * 
	 * @param <T1> Type of the first value.
	 * @param tuple Util tuple to adapt.
	 * @return The adapted core tuple.
	 * @deprecated Use {@link com.trazere.core.util.Tuple1}.
	 */
	@Deprecated
	public static <T1> com.trazere.core.util.Tuple1<T1> toTuple1(final Tuple1<? extends T1> tuple) {
		return new com.trazere.core.util.Tuple1<>(tuple.getFirst());
	}
	
	/**
	 * Adapts the given core tuple to an util tuple.
	 * 
	 * @param <E1> Type of the first element.
	 * @param tuple Core tuple to adapt.
	 * @return The adapted util tuple.
	 * @deprecated Use {@link com.trazere.core.util.Tuple1}.
	 */
	@Deprecated
	public static <E1> Tuple1<E1> fromTuple1(final com.trazere.core.util.Tuple1<? extends E1> tuple) {
		return new Tuple1<>(tuple.get1());
	}
	
	/**
	 * Adapts the given util tuple to a core tuple.
	 * 
	 * @param <T1> Type of the first value.
	 * @param <T2> Type of the second value.
	 * @param tuple Util tuple to adapt.
	 * @return The adapted core tuple.
	 * @deprecated Use {@link com.trazere.core.util.Tuple2}.
	 */
	@Deprecated
	public static <T1, T2> com.trazere.core.util.Tuple2<T1, T2> toTuple2(final Tuple2<? extends T1, ? extends T2> tuple) {
		return new com.trazere.core.util.Tuple2<>(tuple.getFirst(), tuple.getSecond());
	}
	
	/**
	 * Adapts the given core tuple to an util tuple.
	 * 
	 * @param <E1> Type of the first element.
	 * @param <E2> Type of the second element.
	 * @param tuple Core tuple to adapt.
	 * @return The adapted util tuple.
	 * @deprecated Use {@link com.trazere.core.util.Tuple2}.
	 */
	@Deprecated
	public static <E1, E2> Tuple2<E1, E2> fromTuple2(final com.trazere.core.util.Tuple2<? extends E1, ? extends E2> tuple) {
		return new Tuple2<>(tuple.get1(), tuple.get2());
	}
	
	/**
	 * Adapts the given util tuple to a core tuple.
	 * 
	 * @param <T1> Type of the first value.
	 * @param <T2> Type of the second value.
	 * @param <T3> Type of the third value.
	 * @param tuple Util tuple to adapt.
	 * @return The adapted core tuple.
	 * @deprecated Use {@link com.trazere.core.util.Tuple3}.
	 */
	@Deprecated
	public static <T1, T2, T3> com.trazere.core.util.Tuple3<T1, T2, T3> toTuple3(final Tuple3<? extends T1, ? extends T2, ? extends T3> tuple) {
		return new com.trazere.core.util.Tuple3<>(tuple.getFirst(), tuple.getSecond(), tuple.getThird());
	}
	
	/**
	 * Adapts the given core tuple to an util tuple.
	 * 
	 * @param <E1> Type of the first element.
	 * @param <E2> Type of the second element.
	 * @param <E3> Type of the third element.
	 * @param tuple Core tuple to adapt.
	 * @return The adapted util tuple.
	 * @deprecated Use {@link com.trazere.core.util.Tuple3}.
	 */
	@Deprecated
	public static <E1, E2, E3> Tuple3<E1, E2, E3> fromTuple3(final com.trazere.core.util.Tuple3<? extends E1, ? extends E2, ? extends E3> tuple) {
		return new Tuple3<>(tuple.get1(), tuple.get2(), tuple.get3());
	}
	
	/**
	 * Adapts the given util tuple to a core tuple.
	 * 
	 * @param <T1> Type of the first value.
	 * @param <T2> Type of the second value.
	 * @param <T3> Type of the third value.
	 * @param <T4> Type of the fourth value.
	 * @param tuple Util tuple to adapt.
	 * @return The adapted core tuple.
	 * @deprecated Use {@link com.trazere.core.util.Tuple4}.
	 */
	@Deprecated
	public static <T1, T2, T3, T4> com.trazere.core.util.Tuple4<T1, T2, T3, T4> toTuple4(final Tuple4<? extends T1, ? extends T2, ? extends T3, ? extends T4> tuple) {
		return new com.trazere.core.util.Tuple4<>(tuple.getFirst(), tuple.getSecond(), tuple.getThird(), tuple.getFourth());
	}
	
	/**
	 * Adapts the given core tuple to an util tuple.
	 * 
	 * @param <E1> Type of the first element.
	 * @param <E2> Type of the second element.
	 * @param <E3> Type of the third element.
	 * @param <E4> Type of the fourth element.
	 * @param tuple Core tuple to adapt.
	 * @return The adapted util tuple.
	 * @deprecated Use {@link com.trazere.core.util.Tuple4}.
	 */
	@Deprecated
	public static <E1, E2, E3, E4> Tuple4<E1, E2, E3, E4> fromTuple4(final com.trazere.core.util.Tuple4<? extends E1, ? extends E2, ? extends E3, ? extends E4> tuple) {
		return new Tuple4<>(tuple.get1(), tuple.get2(), tuple.get3(), tuple.get4());
	}
	
	/**
	 * Adapts the given util tuple to a core tuple.
	 * 
	 * @param <T1> Type of the first value.
	 * @param <T2> Type of the second value.
	 * @param <T3> Type of the third value.
	 * @param <T4> Type of the fourth value.
	 * @param <T5> Type of the fifth value.
	 * @param tuple Util tuple to adapt.
	 * @return The adapted core tuple.
	 * @deprecated Use {@link com.trazere.core.util.Tuple5}.
	 */
	@Deprecated
	public static <T1, T2, T3, T4, T5> com.trazere.core.util.Tuple5<T1, T2, T3, T4, T5> toTuple5(final Tuple5<? extends T1, ? extends T2, ? extends T3, ? extends T4, ? extends T5> tuple) {
		return new com.trazere.core.util.Tuple5<>(tuple.getFirst(), tuple.getSecond(), tuple.getThird(), tuple.getFourth(), tuple.getFifth());
	}
	
	/**
	 * Adapts the given core tuple to an util tuple.
	 * 
	 * @param <E1> Type of the first element.
	 * @param <E2> Type of the second element.
	 * @param <E3> Type of the third element.
	 * @param <E4> Type of the fourth element.
	 * @param <E5> Type of the fifth element.
	 * @param tuple Core tuple to adapt.
	 * @return The adapted util tuple.
	 * @deprecated Use {@link com.trazere.core.util.Tuple5}.
	 */
	@Deprecated
	public static <E1, E2, E3, E4, E5> Tuple5<E1, E2, E3, E4, E5> fromTuple5(final com.trazere.core.util.Tuple5<? extends E1, ? extends E2, ? extends E3, ? extends E4, ? extends E5> tuple) {
		return new Tuple5<>(tuple.get1(), tuple.get2(), tuple.get3(), tuple.get4(), tuple.get5());
	}
	
	/**
	 * Adapts the given util tuple to a core tuple.
	 * 
	 * @param <T1> Type of the first value.
	 * @param <T2> Type of the second value.
	 * @param <T3> Type of the third value.
	 * @param <T4> Type of the fourth value.
	 * @param <T5> Type of the fifth value.
	 * @param <T6> Type of the sixth value.
	 * @param tuple Util tuple to adapt.
	 * @return The adapted core tuple.
	 * @deprecated Use {@link com.trazere.core.util.Tuple6}.
	 */
	@Deprecated
	public static <T1, T2, T3, T4, T5, T6> com.trazere.core.util.Tuple6<T1, T2, T3, T4, T5, T6> toTuple6(final Tuple6<? extends T1, ? extends T2, ? extends T3, ? extends T4, ? extends T5, ? extends T6> tuple) {
		return new com.trazere.core.util.Tuple6<>(tuple.getFirst(), tuple.getSecond(), tuple.getThird(), tuple.getFourth(), tuple.getFifth(), tuple.getSixth());
	}
	
	/**
	 * Adapts the given core tuple to an util tuple.
	 * 
	 * @param <E1> Type of the first element.
	 * @param <E2> Type of the second element.
	 * @param <E3> Type of the third element.
	 * @param <E4> Type of the fourth element.
	 * @param <E5> Type of the fifth element.
	 * @param <E6> Type of the sixth element.
	 * @param tuple Core tuple to adapt.
	 * @return The adapted util tuple.
	 * @deprecated Use {@link com.trazere.core.util.Tuple6}.
	 */
	@Deprecated
	public static <E1, E2, E3, E4, E5, E6> Tuple6<E1, E2, E3, E4, E5, E6> fromTuple6(final com.trazere.core.util.Tuple6<? extends E1, ? extends E2, ? extends E3, ? extends E4, ? extends E5, ? extends E6> tuple) {
		return new Tuple6<>(tuple.get1(), tuple.get2(), tuple.get3(), tuple.get4(), tuple.get5(), tuple.get6());
	}
	
	/**
	 * Adapts the given util tuple to a core tuple.
	 * 
	 * @param <T1> Type of the first value.
	 * @param <T2> Type of the second value.
	 * @param <T3> Type of the third value.
	 * @param <T4> Type of the fourth value.
	 * @param <T5> Type of the fifth value.
	 * @param <T6> Type of the sixth value.
	 * @param <T7> Type of the seventh value.
	 * @param tuple Util tuple to adapt.
	 * @return The adapted core tuple.
	 * @deprecated Use {@link com.trazere.core.util.Tuple7}.
	 */
	@Deprecated
	public static <T1, T2, T3, T4, T5, T6, T7> com.trazere.core.util.Tuple7<T1, T2, T3, T4, T5, T6, T7> toTuple7(final Tuple7<? extends T1, ? extends T2, ? extends T3, ? extends T4, ? extends T5, ? extends T6, ? extends T7> tuple) {
		return new com.trazere.core.util.Tuple7<>(tuple.getFirst(), tuple.getSecond(), tuple.getThird(), tuple.getFourth(), tuple.getFifth(), tuple.getSixth(), tuple.getSeventh());
	}
	
	/**
	 * Adapts the given core tuple to an util tuple.
	 * 
	 * @param <E1> Type of the first element.
	 * @param <E2> Type of the second element.
	 * @param <E3> Type of the third element.
	 * @param <E4> Type of the fourth element.
	 * @param <E5> Type of the fifth element.
	 * @param <E6> Type of the sixth element.
	 * @param <E7> Type of the seventh element.
	 * @param tuple Core tuple to adapt.
	 * @return The adapted util tuple.
	 * @deprecated Use {@link com.trazere.core.util.Tuple7}.
	 */
	@Deprecated
	public static <E1, E2, E3, E4, E5, E6, E7> Tuple7<E1, E2, E3, E4, E5, E6, E7> fromTuple7(final com.trazere.core.util.Tuple7<? extends E1, ? extends E2, ? extends E3, ? extends E4, ? extends E5, ? extends E6, ? extends E7> tuple) {
		return new Tuple7<>(tuple.get1(), tuple.get2(), tuple.get3(), tuple.get4(), tuple.get5(), tuple.get6(), tuple.get7());
	}
	
	/**
	 * Adapts the given util tuple to a core tuple.
	 * 
	 * @param <T1> Type of the first value.
	 * @param <T2> Type of the second value.
	 * @param <T3> Type of the third value.
	 * @param <T4> Type of the fourth value.
	 * @param <T5> Type of the fifth value.
	 * @param <T6> Type of the sixth value.
	 * @param <T7> Type of the seventh value.
	 * @param <T8> Type of the eighth value.
	 * @param tuple Util tuple to adapt.
	 * @return The adapted core tuple.
	 * @deprecated Use {@link com.trazere.core.util.Tuple8}.
	 */
	@Deprecated
	public static <T1, T2, T3, T4, T5, T6, T7, T8> com.trazere.core.util.Tuple8<T1, T2, T3, T4, T5, T6, T7, T8> toTuple8(final Tuple8<? extends T1, ? extends T2, ? extends T3, ? extends T4, ? extends T5, ? extends T6, ? extends T7, ? extends T8> tuple) {
		return new com.trazere.core.util.Tuple8<>(tuple.getFirst(), tuple.getSecond(), tuple.getThird(), tuple.getFourth(), tuple.getFifth(), tuple.getSixth(), tuple.getSeventh(), tuple.getEighth());
	}
	
	/**
	 * Adapts the given core tuple to an util tuple.
	 * 
	 * @param <E1> Type of the first element.
	 * @param <E2> Type of the second element.
	 * @param <E3> Type of the third element.
	 * @param <E4> Type of the fourth element.
	 * @param <E5> Type of the fifth element.
	 * @param <E6> Type of the sixth element.
	 * @param <E7> Type of the seventh element.
	 * @param <E8> Type of the eighth element.
	 * @param tuple Core tuple to adapt.
	 * @return The adapted util tuple.
	 * @deprecated Use {@link com.trazere.core.util.Tuple8}.
	 */
	@Deprecated
	public static <E1, E2, E3, E4, E5, E6, E7, E8> Tuple8<E1, E2, E3, E4, E5, E6, E7, E8> fromTuple8(final com.trazere.core.util.Tuple8<? extends E1, ? extends E2, ? extends E3, ? extends E4, ? extends E5, ? extends E6, ? extends E7, ? extends E8> tuple) {
		return new Tuple8<>(tuple.get1(), tuple.get2(), tuple.get3(), tuple.get4(), tuple.get5(), tuple.get6(), tuple.get7(), tuple.get8());
	}
	
	/**
	 * Adapts the given util either to a core either.
	 * 
	 * @param <L> Type of the left value.
	 * @param <R> Type of the right value.
	 * @param either Util either to adapt.
	 * @return The adapted core either.
	 * @deprecated Use {@link com.trazere.core.util.Either}.
	 */
	@Deprecated
	public static <L, R> com.trazere.core.util.Either<L, R> toEither(final Either<? extends L, ? extends R> either) {
		if (either.isLeft()) {
			return com.trazere.core.util.Either.<L, R>left(either.asLeft().getLeft());
		} else {
			return com.trazere.core.util.Either.<L, R>right(either.asRight().getRight());
		}
	}
	
	/**
	 * Adapts the given core either to an util either.
	 * 
	 * @param <L> Type of the left value.
	 * @param <R> Type of the right value.
	 * @param either Core either to adapt.
	 * @return The adapted util either.
	 * @deprecated Use {@link com.trazere.core.util.Either}.
	 */
	@Deprecated
	public static <L, R> Either<L, R> fromEither(final com.trazere.core.util.Either<? extends L, ? extends R> either) {
		if (either.isLeft()) {
			return Either.<L, R>left(either.asLeft().getValue());
		} else {
			return Either.<L, R>right(either.asRight().getValue());
		}
	}
	
	private TypeUtils() {
		// Prevents instantiation.
	}
}
