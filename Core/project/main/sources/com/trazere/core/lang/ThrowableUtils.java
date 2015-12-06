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
package com.trazere.core.lang;

import com.trazere.core.collection.Feed;
import com.trazere.core.collection.Feeds;
import com.trazere.core.functional.Function;
import com.trazere.core.functional.Predicate;
import com.trazere.core.util.Maybe;
import com.trazere.core.util.MaybeUtils;
import com.trazere.core.util.Tuple2;

/**
 * The {@link ThrowableUtils} class provides various utilities regarding {@link Throwable throwables}.
 * 
 * @see Throwable
 * @since 2.0
 */
public class ThrowableUtils {
	/**
	 * Gets the chain of the given throwable and its causes.
	 * 
	 * @param throwable Throwable to explore.
	 * @return The chain of causes.
	 * @since 2.0
	 */
	public static Feed<Throwable> getCauseChain(final Throwable throwable) {
		return Feeds.feed(throwable, getCauseTail(throwable));
	}
	
	/**
	 * Gets the chain of causes of the given throwable.
	 * <p>
	 * The given throwable is not included in the chain.
	 * 
	 * @param throwable Throwable to explore.
	 * @return The chain of causes.
	 * @since 2.0
	 */
	public static Feed<Throwable> getCauseTail(final Throwable throwable) {
		assert null != throwable;
		
		return () -> MaybeUtils.fromNullable(throwable.getCause()).map(cause -> new Tuple2<>(cause, getCauseTail(cause)));
	}
	
	/**
	 * Tests whether the given throwable has a cause accepted by the given filter.
	 * 
	 * @param throwable Throwable to explore.
	 * @param filter Predicate to use to filter the throwables.
	 * @return <code>true</code> when the throwable or any of its causes if accepted by the filter, <code>false</code> otherwise.
	 * @see #getCauseChain(Throwable)
	 * @since 2.0
	 */
	public static boolean hasCause(final Throwable throwable, final Predicate<? super Throwable> filter) {
		return getCauseChain(throwable).isAny(filter);
	}
	
	/**
	 * Tests whether the given throwable has a cause matching the given type.
	 * 
	 * @param <T> Type of the throwable to match.
	 * @param throwable Throwable to explore.
	 * @param type Type of the throwable to match.
	 * @return <code>true</code> when the throwable or any of its causes has the type, <code>false</code> otherwise.
	 * @see #getCauseChain(Throwable)
	 * @since 2.0
	 */
	public static <T extends Throwable> boolean hasCause(final Throwable throwable, final Class<T> type) {
		return hasCause(throwable, ObjectPredicates.isInstanceOf(type));
	}
	
	/**
	 * Gets the first throwable from the cause chain of the given throwable accepted by the given filter.
	 * 
	 * @param throwable Throwable to explore.
	 * @param filter Predicate to use to filter the throwables.
	 * @return The first accepted throwable.
	 * @see #getCauseChain(Throwable)
	 * @since 2.0
	 */
	public static Maybe<Throwable> findCause(final Throwable throwable, final Predicate<? super Throwable> filter) {
		return getCauseChain(throwable).first(filter);
	}
	
	/**
	 * Gets the first throwable from the cause chain of the given throwable matching the given type.
	 * 
	 * @param <T> Type of the throwable to match.
	 * @param throwable Throwable to explore.
	 * @param type Type of the throwable to match.
	 * @return The first found throwable.
	 * @see #getCauseChain(Throwable)
	 * @since 2.0
	 */
	public static <T extends Throwable> Maybe<T> findCause(final Throwable throwable, final Class<T> type) {
		return extractCause(throwable, ObjectExtractors.match(type));
	}
	
	/**
	 * Gets the first element extracted from the cause chain of the given throwable by the given extractor.
	 * 
	 * @param <E> Type of the extracted element.
	 * @param throwable Throwable to explore.
	 * @param extractor Function to use to extract the elements.
	 * @return The first extracted element.
	 * @see #getCauseChain(Throwable)
	 * @since 2.0
	 */
	public static <E> Maybe<E> extractCause(final Throwable throwable, final Function<? super Throwable, ? extends Maybe<? extends E>> extractor) {
		return getCauseChain(throwable).extractFirst(extractor);
	}
	
	/**
	 * Throws the first throwable extracted from the cause chain of the given throwable by the given extractor.
	 * <p>
	 * This methods does nothing when no throwables are accepted by the extractor.
	 * 
	 * @param <T> Type of the extracted throwable.
	 * @param throwable Throwable to explore.
	 * @param extractor Function to use to extract the throwable.
	 * @throws T The first extracted throwable.
	 * @see #getCauseChain(Throwable)
	 * @since 2.0
	 */
	public static <T extends Throwable> void throwCause(final Throwable throwable, final Function<? super Throwable, ? extends Maybe<? extends T>> extractor)
	throws T {
		final Maybe<? extends T> cause = extractCause(throwable, extractor);
		if (cause.isSome()) {
			throw cause.asSome().getValue();
		}
	}
	
	/**
	 * Throws the first throwable from the cause chain of the given throwable matching the given type.
	 * <p>
	 * This methods does nothing when no throwables match the type.
	 * 
	 * @param <T> Type of the throwable to match.
	 * @param throwable Throwable to explore.
	 * @param type Type of the throwable to match.
	 * @throws T The matched throwable.
	 * @see #getCauseChain(Throwable)
	 * @since 2.0
	 */
	public static <T extends Throwable> void throwCause(final Throwable throwable, final Class<T> type)
	throws T {
		throwCause(throwable, ObjectExtractors.match(type));
	}
	
	private ThrowableUtils() {
		// Prevent instantiation.
	}
}
