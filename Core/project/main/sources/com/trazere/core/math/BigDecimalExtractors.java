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
package com.trazere.core.math;

import com.trazere.core.functional.Function;
import com.trazere.core.util.Maybe;
import java.math.BigDecimal;

/**
 * The {@link BigDecimalExtractors} class provides various factories of extractors related to {@link BigDecimal big decimals}.
 * <p>
 * An extractor is {@link Function function} that combines a map operation and a filter operation.
 * 
 * @see Function
 * @see Maybe
 * @see BigDecimal
 * @since 2.0
 */
public class BigDecimalExtractors {
	/**
	 * Builds an extractor that extracts the value of the argument big decimals as bytes.
	 * <p>
	 * The extractors truncate the decimal part and control that the values fit in bytes.
	 * 
	 * @return The built extractor.
	 * @since 2.0
	 */
	public static Function<BigDecimal, Maybe<Byte>> byteValue() {
		return BYTE_VALUE;
	}
	
	private static final Function<BigDecimal, Maybe<Byte>> BYTE_VALUE = (final BigDecimal arg) -> {
		try {
			return Maybe.some(arg.byteValueExact());
		} catch (@SuppressWarnings("unused") final ArithmeticException exception) {
			return Maybe.none();
		}
	};
	
	/**
	 * Builds an extractor that extracts the value of the argument big decimals as short integers.
	 * <p>
	 * The extractors truncate the decimal part and control that the values fit in short integers.
	 * 
	 * @return The built extractor.
	 * @since 2.0
	 */
	public static Function<BigDecimal, Maybe<Short>> shortValue() {
		return SHORT_VALUE;
	}
	
	private static final Function<BigDecimal, Maybe<Short>> SHORT_VALUE = (final BigDecimal arg) -> {
		try {
			return Maybe.some(arg.shortValueExact());
		} catch (@SuppressWarnings("unused") final ArithmeticException exception) {
			return Maybe.none();
		}
	};
	
	/**
	 * Builds an extractor that extracts the value of the argument big decimals as integers.
	 * <p>
	 * The extractors truncate the decimal part and control that the values fit in integers.
	 * 
	 * @return The built extractor.
	 * @since 2.0
	 */
	public static Function<BigDecimal, Maybe<Integer>> integerValue() {
		return INTEGER_VALUE;
	}
	
	private static final Function<BigDecimal, Maybe<Integer>> INTEGER_VALUE = (final BigDecimal arg) -> {
		try {
			return Maybe.some(arg.intValueExact());
		} catch (@SuppressWarnings("unused") final ArithmeticException exception) {
			return Maybe.none();
		}
	};
	
	/**
	 * Builds an extractor that extracts the value of the argument big decimals as long integers.
	 * <p>
	 * The extractors truncate the decimal part and control that the values fit in long integers.
	 * 
	 * @return The built extractor.
	 * @since 2.0
	 */
	public static Function<BigDecimal, Maybe<Long>> longValue() {
		return LONG_VALUE;
	}
	
	private static final Function<BigDecimal, Maybe<Long>> LONG_VALUE = (final BigDecimal arg) -> {
		try {
			return Maybe.some(arg.longValueExact());
		} catch (@SuppressWarnings("unused") final ArithmeticException exception) {
			return Maybe.none();
		}
	};
	
	/**
	 * Builds an extractor that extracts the value of the argument big decimals as floats.
	 * <p>
	 * The extractors control that the values fit in floats.
	 * 
	 * @return The built extractor.
	 * @since 2.0
	 */
	public static Function<BigDecimal, Maybe<Float>> floatValue() {
		return FLOAT_VALUE;
	}
	
	private static final Function<BigDecimal, Maybe<Float>> FLOAT_VALUE = (final BigDecimal arg) -> {
		try {
			return Maybe.some(arg.floatValue());
		} catch (@SuppressWarnings("unused") final NumberFormatException exception) {
			return Maybe.none();
		}
	};
	
	/**
	 * Builds an extractor that extracts the value of the argument big decimals as doubles.
	 * <p>
	 * The extractors control that the values fit in doubles.
	 * 
	 * @return The built extractor.
	 * @since 2.0
	 */
	public static Function<BigDecimal, Maybe<Double>> doubleValue() {
		return DOUBLE_VALUE;
	}
	
	private static final Function<BigDecimal, Maybe<Double>> DOUBLE_VALUE = (final BigDecimal arg) -> {
		try {
			return Maybe.some(arg.doubleValue());
		} catch (@SuppressWarnings("unused") final NumberFormatException exception) {
			return Maybe.none();
		}
	};
	
	private BigDecimalExtractors() {
		// Prevents instantiation.
	}
}
