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
