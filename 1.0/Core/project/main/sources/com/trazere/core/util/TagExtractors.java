package com.trazere.core.util;

import com.trazere.core.functional.Function;

/**
 * The {@link TagExtractors} class provides various extractors related to tags.
 */
public class TagExtractors {
	/**
	 * Builds an extractor of cases associated to a first tag.
	 * 
	 * @param <T> Type of the case.
	 * @return The built extractor.
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
