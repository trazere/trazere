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
package com.trazere.core.math;

import com.trazere.core.functional.Function;
import com.trazere.core.util.Maybe;
import java.math.BigInteger;

/**
 * The {@link BigIntegerExtractors} class provides various factories of extractors related to {@link BigInteger big integers}.
 * <p>
 * An extractor is function that combines a map and a filter operation.
 * 
 * @see Function
 * @see Maybe
 * @see BigInteger
 */
public class BigIntegerExtractors {
	/**
	 * Builds an extractor that extracts the value of the argument big integers as bytes.
	 * <p>
	 * The extractors control that the values fit in bytes.
	 * 
	 * @return The built extractor.
	 */
	public static Function<BigInteger, Maybe<Byte>> byteValue() {
		return BYTE_VALUE;
	}
	
	private static final Function<BigInteger, Maybe<Byte>> BYTE_VALUE = arg -> arg.bitLength() < Byte.SIZE ? Maybe.some(arg.byteValue()) : Maybe.none();
	
	/**
	 * Builds an extractor that extracts the value of the argument big integers as short integers.
	 * <p>
	 * The extractors control that the values fit in short integers.
	 * 
	 * @return The built extractor.
	 */
	public static Function<BigInteger, Maybe<Short>> shortValue() {
		return SHORT_VALUE;
	}
	
	private static final Function<BigInteger, Maybe<Short>> SHORT_VALUE = arg -> arg.bitLength() < Short.SIZE ? Maybe.some(arg.shortValue()) : Maybe.none();
	
	/**
	 * Builds an extractor that extracts the value of the argument big integers as integers.
	 * <p>
	 * The extractors control that the values fit in integers.
	 * 
	 * @return The built extractor.
	 */
	public static Function<BigInteger, Maybe<Integer>> integerValue() {
		return INTEGER_VALUE;
	}
	
	private static final Function<BigInteger, Maybe<Integer>> INTEGER_VALUE = arg -> arg.bitLength() < Integer.SIZE ? Maybe.some(arg.intValue()) : Maybe.none();
	
	/**
	 * Builds an extractor that extracts the value of the argument big integers as long integers.
	 * <p>
	 * The extractors control that the values fit in long integers.
	 * 
	 * @return The built extractor.
	 */
	public static Function<BigInteger, Maybe<Long>> longValue() {
		return LONG_VALUE;
	}
	
	private static final Function<BigInteger, Maybe<Long>> LONG_VALUE = arg -> arg.bitLength() < Long.SIZE ? Maybe.some(arg.longValue()) : Maybe.none();
	
	private BigIntegerExtractors() {
		// Prevents instantiation.
	}
}
