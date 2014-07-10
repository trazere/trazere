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
	
	private static final Function<? extends Either<?, ?>, ? extends Maybe<?>> GET_LEFT = (final Either<Object, Object> instance) -> {
		if (instance.isLeft()) {
			return Maybe.some(instance.asLeft().getLeft());
		} else {
			return Maybe.none();
		}
	};
	
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
	
	private static final Function<? extends Either<?, ?>, ? extends Maybe<?>> GET_RIGHT = (final Either<Object, Object> instance) -> {
		if (instance.isRight()) {
			return Maybe.some(instance.asRight().getRight());
		} else {
			return Maybe.none();
		}
	};
	
	private EitherExtractors() {
		// Prevent instantiation.
	}
}
