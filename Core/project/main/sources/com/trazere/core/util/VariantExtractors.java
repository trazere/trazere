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

// TODO: move to TagExctractors ?
/**
 * The {@link VariantExtractors} class provides various extractors related to variants.
 * <p>
 * An extractor is {@link Function function} that combines a map operation and a filter operation.
 * 
 * @see Function
 * @see Maybe
 * @since 1.0
 */
public class VariantExtractors {
	/**
	 * Builds an extractor of the value wrapped in first case instances of variants.
	 * 
	 * @param <T> Type of the value.
	 * @return The built extractor.
	 * @since 1.0
	 */
	@SuppressWarnings("unchecked")
	public static <T> Function<Tag1<? extends Field<T>>, Maybe<T>> as1() {
		return (Function<Tag1<? extends Field<T>>, Maybe<T>>) AS1;
	}
	
	private static final Function<? extends Tag1<? extends Field<?>>, ? extends Maybe<?>> AS1 = instance -> instance.is1() ? Maybe.some(instance.as1().get()) : Maybe.none();
	
	/**
	 * Builds an extractor of the value wrapped in second case instances of variants.
	 * 
	 * @param <T> Type of the value.
	 * @return The built extractor.
	 * @since 1.0
	 */
	@SuppressWarnings("unchecked")
	public static <T> Function<Tag2<? extends Field<T>>, Maybe<T>> as2() {
		return (Function<Tag2<? extends Field<T>>, Maybe<T>>) AS2;
	}
	
	private static final Function<? extends Tag2<? extends Field<?>>, ? extends Maybe<?>> AS2 = instance -> instance.is2() ? Maybe.some(instance.as2().get()) : Maybe.none();
	
	/**
	 * Builds an extractor of the value wrapped in third case instances of variants.
	 * 
	 * @param <T> Type of the value.
	 * @return The built extractor.
	 * @since 1.0
	 */
	@SuppressWarnings("unchecked")
	public static <T> Function<Tag3<? extends Field<T>>, Maybe<T>> as3() {
		return (Function<Tag3<? extends Field<T>>, Maybe<T>>) AS3;
	}
	
	private static final Function<? extends Tag3<? extends Field<?>>, ? extends Maybe<?>> AS3 = instance -> instance.is3() ? Maybe.some(instance.as3().get()) : Maybe.none();
	
	/**
	 * Builds an extractor of the value wrapped in fourth case instances of variants.
	 * 
	 * @param <T> Type of the value.
	 * @return The built extractor.
	 * @since 1.0
	 */
	@SuppressWarnings("unchecked")
	public static <T> Function<Tag4<? extends Field<T>>, Maybe<T>> as4() {
		return (Function<Tag4<? extends Field<T>>, Maybe<T>>) AS4;
	}
	
	private static final Function<? extends Tag4<? extends Field<?>>, ? extends Maybe<?>> AS4 = instance -> instance.is4() ? Maybe.some(instance.as4().get()) : Maybe.none();
	
	/**
	 * Builds an extractor of the value wrapped in fifth case instances of variants.
	 * 
	 * @param <T> Type of the value.
	 * @return The built extractor.
	 * @since 1.0
	 */
	@SuppressWarnings("unchecked")
	public static <T> Function<Tag5<? extends Field<T>>, Maybe<T>> as5() {
		return (Function<Tag5<? extends Field<T>>, Maybe<T>>) AS5;
	}
	
	private static final Function<? extends Tag5<? extends Field<?>>, ? extends Maybe<?>> AS5 = instance -> instance.is5() ? Maybe.some(instance.as5().get()) : Maybe.none();
	
	private VariantExtractors() {
		// Prevent instantiation.
	}
}
