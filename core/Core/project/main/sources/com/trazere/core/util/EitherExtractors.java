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
	
	private static final Function<? extends Either<?, ?>, ? extends Maybe<?>> GET_LEFT = new Function<Either<Object, Object>, Maybe<Object>>() {
		@Override
		public Maybe<Object> evaluate(final Either<Object, Object> instance) {
			return instance.isLeft() ? Maybe.some(instance.asLeft().getLeft()) : Maybe.<Object>none();
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
	
	private static final Function<? extends Either<?, ?>, ? extends Maybe<?>> GET_RIGHT = new Function<Either<Object, Object>, Maybe<Object>>() {
		@Override
		public Maybe<Object> evaluate(final Either<Object, Object> instance) {
			return instance.isRight() ? Maybe.some(instance.asRight().getRight()) : Maybe.<Object>none();
		}
	};
	
	private EitherExtractors() {
		// Prevent instantiation.
	}
}
