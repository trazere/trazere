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

import com.trazere.core.functional.Functions;
import com.trazere.core.util.Maybe.None;
import com.trazere.core.util.Maybe.Some;

/**
 * The {@link MaybeUtils} class provides various utilities regarding types.
 */
public class MaybeUtils {
	/**
	 * Builds an instance of {@link Maybe} from the given nullable value according to the following rules:
	 * <ul>
	 * <li><code>null</code> are translated to an absent value ({@link None}),
	 * <li>non-<code>null</code> values are wrapped in available values ({@link Some}).
	 * <p>
	 * This method aims to simplify the interoperability with legacy Java code.
	 * 
	 * @param <T> Type of the value.
	 * @param value Value to wrap.
	 * @return The built instance.
	 */
	public static <T> Maybe<T> fromNullable(final T value) {
		if (null != value) {
			return Maybe.some(value);
		} else {
			return Maybe.none();
		}
	}
	
	// TODO: move to Maybe
	/**
	 * Convert the given {@link Maybe} instance to a nullable value according to the following rules:
	 * <ul>
	 * <li>absents values ({@link None}) are translated to <code>null</code>,
	 * <li>available values ({@link Some}) are unwrapped.
	 * <p>
	 * This method aims to simplify the interoperability with legacy Java code.
	 * 
	 * @param <T> Type of the value.
	 * @param maybe Instance to convert.
	 * @return The resulting value.
	 */
	public static <T> T toNullable(final Maybe<T> maybe) {
		return maybe.get((T) null);
	}
	
	/**
	 * Gets the boolean value of the given {@link Maybe} instance.
	 * <p>
	 * This method returns the wrapped value for the instances built using the {@link Some} constructor and the given default value for instances built using
	 * the {@link Maybe.None} constructor.
	 *
	 * @param value Instance to read.
	 * @param defaultValue Default value.
	 * @return The value.
	 */
	public static boolean get(final Maybe<Boolean> value, final boolean defaultValue) {
		return value.isSome() ? value.asSome().getValue().booleanValue() : defaultValue;
	}
	
	/**
	 * Gets the byte value of the given {@link Maybe} instance.
	 * <p>
	 * This method returns the wrapped value for the instances built using the {@link Some} constructor and the given default value for instances built using
	 * the {@link Maybe.None} constructor.
	 *
	 * @param value Instance to read.
	 * @param defaultValue Default value.
	 * @return The value.
	 */
	public static int get(final Maybe<Byte> value, final byte defaultValue) {
		return value.isSome() ? value.asSome().getValue().byteValue() : defaultValue;
	}
	
	/**
	 * Gets the short integer value of the given {@link Maybe} instance.
	 * <p>
	 * This method returns the wrapped value for the instances built using the {@link Some} constructor and the given default value for instances built using
	 * the {@link Maybe.None} constructor.
	 *
	 * @param value Instance to read.
	 * @param defaultValue Default value.
	 * @return The value.
	 */
	public static int get(final Maybe<Short> value, final short defaultValue) {
		return value.isSome() ? value.asSome().getValue().shortValue() : defaultValue;
	}
	
	/**
	 * Gets the integer value of the given {@link Maybe} instance.
	 * <p>
	 * This method returns the wrapped value for the instances built using the {@link Some} constructor and the given default value for instances built using
	 * the {@link Maybe.None} constructor.
	 *
	 * @param value Instance to read.
	 * @param defaultValue Default value.
	 * @return The value.
	 */
	public static int get(final Maybe<Integer> value, final int defaultValue) {
		return value.isSome() ? value.asSome().getValue().intValue() : defaultValue;
	}
	
	/**
	 * Gets the long integer value of the given {@link Maybe} instance.
	 * <p>
	 * This method returns the wrapped value for the instances built using the {@link Some} constructor and the given default value for instances built using
	 * the {@link Maybe.None} constructor.
	 *
	 * @param value Instance to read.
	 * @param defaultValue Default value.
	 * @return The value.
	 */
	public static long get(final Maybe<Long> value, final long defaultValue) {
		return value.isSome() ? value.asSome().getValue().longValue() : defaultValue;
	}
	
	/**
	 * Gets the short value of the given {@link Maybe} instance.
	 * <p>
	 * This method returns the wrapped value for the instances built using the {@link Some} constructor and the given default value for instances built using
	 * the {@link Maybe.None} constructor.
	 *
	 * @param value Instance to read.
	 * @param defaultValue Default value.
	 * @return The value.
	 */
	public static float get(final Maybe<Float> value, final float defaultValue) {
		return value.isSome() ? value.asSome().getValue().floatValue() : defaultValue;
	}
	
	/**
	 * Gets the double value of the given {@link Maybe} instance.
	 * <p>
	 * This method returns the wrapped value for the instances built using the {@link Some} constructor and the given default value for instances built using
	 * the {@link Maybe.None} constructor.
	 *
	 * @param value Instance to read.
	 * @param defaultValue Default value.
	 * @return The value.
	 */
	public static double get(final Maybe<Double> value, final double defaultValue) {
		return value.isSome() ? value.asSome().getValue().doubleValue() : defaultValue;
	}
	
	/**
	 * Gets the character value of the given {@link Maybe} instance.
	 * <p>
	 * This method returns the wrapped value for the instances built using the {@link Some} constructor and the given default value for instances built using
	 * the {@link Maybe.None} constructor.
	 *
	 * @param value Instance to read.
	 * @param defaultValue Default value.
	 * @return The value.
	 */
	public static char get(final Maybe<Character> value, final char defaultValue) {
		return value.isSome() ? value.asSome().getValue().charValue() : defaultValue;
	}
	
	/**
	 * Flattens the value wrapped in the {@link Maybe} instance wrapped in the given {@link Maybe} instance.
	 *
	 * @param <T> Type of the value.
	 * @param maybe {@link Maybe} instance wrapping the {@link Maybe} instance to flatten.
	 * @return A {@link Maybe} instance wrapping the flatten value.
	 */
	public static <T> Maybe<T> flatten(final Maybe<? extends Maybe<? extends T>> maybe) {
		return maybe.flatMap(Functions.<Maybe<? extends T>>identity());
	}
	
	private MaybeUtils() {
		// Prevents instantiation.
	}
}
