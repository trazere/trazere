package com.trazere.core.lang;

import com.trazere.core.functional.Function2;
import com.trazere.core.functional.Function3;
import com.trazere.core.functional.Predicate2;
import com.trazere.core.imperative.Procedure2;
import com.trazere.core.util.Maybe;
import com.trazere.core.util.Tuple2;

/**
 * The {@link PairTraversable} interface defines generic containers of pairs of elements that can be traversed.
 * 
 * @param <E1> Type of the first element of the pairs.
 * @param <E2> Type of the second element of the pairs.
 * @since 2.0
 */
public interface PairTraversable<E1, E2>
extends Traversable<Tuple2<E1, E2>> {
	// TODO: drain like methods
	
	/**
	 * Left folds over the pairs of elements of this traversable using the given operator and initial state.
	 *
	 * @param <S> Type of the state.
	 * @param operator Operator to use.
	 * @param initialState Initial state.
	 * @return The folded state.
	 * @since 2.0
	 */
	<S> S fold(Function3<? super S, ? super E1, ? super E2, ? extends S> operator, S initialState);
	
	/**
	 * Tests whether any pair of elements of this traversable is accepted by the given filter.
	 *
	 * @param filter Predicate to use to filter the pairs of elements.
	 * @return <code>true</code> when some pair of elements is accepted, <code>false</code> when all pairs of elements are rejected.
	 * @since 2.0
	 */
	boolean isAny(Predicate2<? super E1, ? super E2> filter);
	
	/**
	 * Tests whether all pairs of elements of this traversable are accepted by the given filter.
	 *
	 * @param filter Predicate to use to filter the pairs of elements.
	 * @return <code>true</code> when all pairs of elements are accepted, <code>false</code> when some pair of elements is rejected.
	 * @since 2.0
	 */
	boolean areAll(Predicate2<? super E1, ? super E2> filter);
	
	/**
	 * Counts the pairs of elements of this traversable accepted by the given filter.
	 *
	 * @param filter Predicate to use to filter the pairs of elements.
	 * @return The number of accepted pairs of elements.
	 * @since 2.0
	 */
	int count(Predicate2<? super E1, ? super E2> filter);
	
	/**
	 * Filters the pairs of elements of this traversable using the given filter.
	 * <p>
	 * Note: The result type of this method is loosely typed because Java does not support higher order type parameters.
	 *
	 * @param filter Predicate to use to filter the pairs of elements.
	 * @return A traversable of the filtered pairs of elements.
	 * @since 2.0
	 */
	PairTraversable<E1, E2> filter(Predicate2<? super E1, ? super E2> filter);
	
	/**
	 * Gets the first pair of elements of this traversable accepted by the given filter.
	 *
	 * @param filter Predicate to use to filter the pairs of elements.
	 * @return The first accepted pair of elements.
	 * @since 2.0
	 */
	Maybe<Tuple2<E1, E2>> filterFirst(Predicate2<? super E1, ? super E2> filter);
	
	/**
	 * Transforms the pairs of elements of this traversable using the given function.
	 * <p>
	 * Note: The result type of this method is loosely typed because Java does not support higher order type parameters.
	 *
	 * @param <TE> Type of the transformed elements.
	 * @param function Function to use to transform the pairs of elements.
	 * @return A traversable of the transformed elements.
	 * @since 2.0
	 */
	<TE> Traversable<TE> map(Function2<? super E1, ? super E2, ? extends TE> function);
	
	/**
	 * Extracts the pairs of elements of this traversable using the given extractor.
	 * <p>
	 * Note: The result type of this method is loosely typed because Java does not support higher order type parameters.
	 *
	 * @param <EE> Type of the extracted elements.
	 * @param extractor Function to use to extract the pairs of elements.
	 * @return An iterable of the extracted elements.
	 * @since 2.0
	 */
	<EE> Traversable<EE> extract(Function2<? super E1, ? super E2, ? extends Maybe<? extends EE>> extractor);
	
	/**
	 * Gets the first element extracted from the pairs of elements of this traversable by the given extractor.
	 *
	 * @param <EE> Type of the extracted elements.
	 * @param extractor Function to use to extract the pairs of elements.
	 * @return The first extracted element.
	 * @since 2.0
	 */
	<EE> Maybe<EE> extractFirst(Function2<? super E1, ? super E2, ? extends Maybe<? extends EE>> extractor);
	
	//	/**
	//	 * Gets all elements extracted from the pairs of elements of this traversable by the given extractor.
	//	 * <p>
	//	 * Note: The result type of this method is loosely typed because Java does not support higher order type parameters.
	//	 *
	//	 * @param <EE> Type of the extracted elements.
	//	 * @param extractor Function to use to extract the pairs of elements.
	//	 * @return An iterable of the extracted elements.
	//	 * @since 2.0
	//	 */
	//	<EE> Traversable<EE> extractAll(Function2<? super E1, ? super E2, ? extends Iterable<? extends EE>> extractor);
	
	// Note: flatMap is not defined here because Java does not support higher order type parameters.
	//	/**
	//	 * Transforms and flattens the pairs of elements of this traversable using the given function.
	//	 * <p>
	//	 * Note: The result type of this method is loosely typed because Java does not support higher order type parameters.
	//	 *
	//	 * @param <TE> Type of the transformed elements.
	//	 * @param function Function to use to transform the pairs of elements.
	//	 * @return A traversable of the flatten, transformed elements.
	//	 * @since 2.0
	//	 */
	//	<TE> Traversable<TE> flatMap(Function2<? super E1, ? super E2, ? extends Traversable<? extends TE>> function);
	
	/**
	 * Executes the given procedure with each pair of elements of this traversable.
	 *
	 * @param procedure Procedure to execute.
	 * @since 2.0
	 */
	void foreach(Procedure2<? super E1, ? super E2> procedure);
}
