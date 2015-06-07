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
import com.trazere.core.collection.FeedUtils;
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
 * @since 1.0
 */
public class ThrowableUtils {
	/**
	 * Gets the chain of the given throwable and its causes.
	 * 
	 * @param throwable Throwable to explore.
	 * @return The chain of causes.
	 * @since 1.0
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
	 * @since 1.0
	 */
	public static Feed<Throwable> getCauseTail(final Throwable throwable) {
		assert null != throwable;
		
		return new Feed<Throwable>() {
			@Override
			public Maybe<? extends Tuple2<? extends Throwable, ? extends Feed<? extends Throwable>>> evaluate() {
				return MaybeUtils.fromNullable(throwable.getCause()).map(cause -> new Tuple2<>(cause, getCauseTail(cause)));
			}
		};
	}
	
	/**
	 * Gets the first throwable from the chain of the given throwable and its causes accepted by the given filter.
	 * 
	 * @param throwable Throwable to explore.
	 * @param filter Predicate to use to filter the throwables.
	 * @return The first accepted throwable.
	 * @see #getCauseChain(Throwable)
	 * @since 1.0
	 */
	public static Maybe<Throwable> findCause(final Throwable throwable, final Predicate<? super Throwable> filter) {
		return FeedUtils.first(getCauseChain(throwable), filter);
	}
	
	/**
	 * Gets the first throwable from the chain of the given throwable and its causes matching the given type.
	 * 
	 * @param <T> Type of the throwable to match.
	 * @param throwable Throwable to explore.
	 * @param type Type of the throwable to match.
	 * @return The first extracted throwable.
	 * @see #getCauseChain(Throwable)
	 * @since 1.0
	 */
	public static <T extends Throwable> Maybe<T> findCause(final Throwable throwable, final Class<T> type) {
		return extractCause(throwable, ObjectExtractors.match(type));
	}
	
	/**
	 * Gets the first element extracted from the chain of the given throwable and its causes by the given extractor.
	 * 
	 * @param <E> Type of the extracted element.
	 * @param throwable Throwable to explore.
	 * @param extractor Function to use to extract the elements.
	 * @return The first extracted element.
	 * @see #getCauseChain(Throwable)
	 * @since 1.0
	 */
	public static <E> Maybe<E> extractCause(final Throwable throwable, final Function<? super Throwable, ? extends Maybe<? extends E>> extractor) {
		return FeedUtils.extractFirst(getCauseChain(throwable), extractor);
	}
	
	/**
	 * Gets the first throwable from the chain of the given throwable and its causes matching the given type and throws it.
	 * 
	 * @param <T> Type of the throwable to match.
	 * @param throwable Throwable to explore.
	 * @param type Type of the throwable to match.
	 * @throws T The matched throwable.
	 * @see #getCauseChain(Throwable)
	 * @since 1.0
	 */
	public static <T extends Throwable> void throwCause(final Throwable throwable, final Class<T> type)
	throws T {
		throwCause(throwable, ObjectExtractors.match(type));
	}
	
	/**
	 * Gets the first throwable extracted from the chain of the given throwable and its causes by the given extractor and throws it.
	 * 
	 * @param <T> Type of the extracted throwable.
	 * @param throwable Throwable to explore.
	 * @param extractor Function to use to extract the throwable.
	 * @throws T The extracted throwable.
	 * @see #getCauseChain(Throwable)
	 * @since 1.0
	 */
	public static <T extends Throwable> void throwCause(final Throwable throwable, final Function<? super Throwable, ? extends Maybe<? extends T>> extractor)
	throws T {
		final Maybe<? extends T> cause = extractCause(throwable, extractor);
		if (cause.isSome()) {
			throw cause.asSome().getValue();
		}
	}
	
	private ThrowableUtils() {
		// Prevent instantiation.
	}
}