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
import com.trazere.core.util.Either.Left;
import com.trazere.core.util.Either.Right;

/**
 * The {@link EitherFunctions} class provides functions related to {@link Either}s.
 */
public class EitherFunctions {
	/**
	 * Builds a function that builds {@link Either} instances using the {@link Left} constructor.
	 * 
	 * @param <L> Type of the left values.
	 * @param <R> Type of the right values.
	 * @return The built function.
	 * @see Either#left(Object)
	 */
	@SuppressWarnings("unchecked")
	public static <L, R> Function<L, Either<L, R>> left() {
		return (Function<L, Either<L, R>>) LEFT;
	}
	
	private static final Function<?, ?> LEFT = Either::left;
	
	/**
	 * Builds a function that builds {@link Either} instances using the {@link Right} constructor.
	 * 
	 * @param <L> Type of the left values.
	 * @param <R> Type of the right values.
	 * @return The built function.
	 * @see Either#right(Object)
	 */
	@SuppressWarnings("unchecked")
	public static <L, R> Function<R, Either<L, R>> right() {
		return (Function<R, Either<L, R>>) RIGHT;
	}
	
	private static final Function<?, ?> RIGHT = Either::right;
	
	/**
	 * Builds a function that maps the left value wrapped in {@link Either} instances using the given function.
	 * 
	 * @param <L> Type of the left value.
	 * @param <R> Type of the right value.
	 * @param <RL> Type of the mapped left value.
	 * @param function Mapping function to use.
	 * @return The built function.
	 * @see Either#mapLeft(Function)
	 */
	public static <L, R, RL> Function<Either<? extends L, R>, Either<RL, R>> mapLeft(final Function<? super L, ? extends RL> function) {
		assert null != function;
		
		return either -> either.mapLeft(function);
	}
	
	/**
	 * Builds a function that maps the right value wrapped in {@link Either} instances using the given function.
	 * 
	 * @param <L> Type of the left value.
	 * @param <R> Type of the right value.
	 * @param <RR> Type of the mapped right value.
	 * @param function Mapping function to use.
	 * @return The built function.
	 * @see Either#mapRight(Function)
	 */
	public static <L, R, RR> Function<Either<L, ? extends R>, Either<L, RR>> mapRight(final Function<? super R, ? extends RR> function) {
		assert null != function;
		
		return either -> either.mapRight(function);
	}
	
	private EitherFunctions() {
		// Prevent instantiation.
	}
}
