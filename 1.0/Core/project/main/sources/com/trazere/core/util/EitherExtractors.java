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

/**
 * The {@link EitherExtractors} class provides various extractors related to {@link Either}s.
 */
public class EitherExtractors {
	/**
	 * Builds an extractor of left values.
	 * 
	 * @param <L> Type of the left value.
	 * @return The built extractor.
	 */
	@SuppressWarnings("unchecked")
	public static <L> Function<Either<? extends L, ?>, Maybe<L>> getLeft() {
		return (Function<Either<? extends L, ?>, Maybe<L>>) GET_LEFT;
	}
	
	private static final Function<? extends Either<?, ?>, ? extends Maybe<?>> GET_LEFT = Either::getLeft;
	
	/**
	 * Builds an extractor of right values.
	 * 
	 * @param <R> Type of the right value.
	 * @return The built extractor.
	 */
	@SuppressWarnings("unchecked")
	public static <R> Function<Either<?, ? extends R>, Maybe<R>> getRight() {
		return (Function<Either<?, ? extends R>, Maybe<R>>) GET_RIGHT;
	}
	
	private static final Function<? extends Either<?, ?>, ? extends Maybe<?>> GET_RIGHT = Either::getRight;
	
	private EitherExtractors() {
		// Prevent instantiation.
	}
}
