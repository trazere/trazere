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
package com.trazere.core.util;

import com.trazere.core.functional.Function;

/**
 * The {@link TagExtractors} class provides various factories of extractors related to tags.
 * <p>
 * An extractor is {@link Function function} that combines a map operation and a filter operation.
 * 
 * @see Function
 * @see Maybe
 * @see Tag1
 * @see Tag2
 * @see Tag3
 * @see Tag4
 * @see Tag5
 * @since 2.0
 */
public class TagExtractors {
	/**
	 * Builds an extractor of cases associated to a first tag.
	 * 
	 * @param <T> Type of the case.
	 * @return The built extractor.
	 * @since 2.0
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Tag1<T>> Function<Tag1<T>, Maybe<T>> as1() {
		return (Function<Tag1<T>, Maybe<T>>) AS1;
	}
	
	private static final Function<? extends Tag1<?>, ? extends Maybe<?>> AS1 = tag -> tag.is1() ? Maybe.some(tag.as1()) : Maybe.none();
	
	/**
	 * Builds an extractor of cases associated to a second tag.
	 * 
	 * @param <T> Type of the case.
	 * @return The built extractor.
	 * @since 2.0
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Tag2<T>> Function<Tag2<T>, Maybe<T>> as2() {
		return (Function<Tag2<T>, Maybe<T>>) AS2;
	}
	
	private static final Function<? extends Tag2<?>, ? extends Maybe<?>> AS2 = tag -> tag.is2() ? Maybe.some(tag.as2()) : Maybe.none();
	
	/**
	 * Builds an extractor of cases associated to a third tag.
	 * 
	 * @param <T> Type of the case.
	 * @return The built extractor.
	 * @since 2.0
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Tag3<T>> Function<Tag3<T>, Maybe<T>> as3() {
		return (Function<Tag3<T>, Maybe<T>>) AS3;
	}
	
	private static final Function<? extends Tag3<?>, ? extends Maybe<?>> AS3 = tag -> tag.is3() ? Maybe.some(tag.as3()) : Maybe.none();
	
	/**
	 * Builds an extractor of cases associated to a fourth tag.
	 * 
	 * @param <T> Type of the case.
	 * @return The built extractor.
	 * @since 2.0
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Tag4<T>> Function<Tag4<T>, Maybe<T>> as4() {
		return (Function<Tag4<T>, Maybe<T>>) AS4;
	}
	
	private static final Function<? extends Tag4<?>, ? extends Maybe<?>> AS4 = tag -> tag.is4() ? Maybe.some(tag.as4()) : Maybe.none();
	
	/**
	 * Builds an extractor of cases associated to a fifth tag.
	 * 
	 * @param <T> Type of the case.
	 * @return The built extractor.
	 * @since 2.0
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Tag5<T>> Function<Tag5<T>, Maybe<T>> as5() {
		return (Function<Tag5<T>, Maybe<T>>) AS5;
	}
	
	private static final Function<? extends Tag5<?>, ? extends Maybe<?>> AS5 = tag -> tag.is5() ? Maybe.some(tag.as5()) : Maybe.none();
	
	private TagExtractors() {
		// Prevent instantiation.
	}
}
