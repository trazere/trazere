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
package com.trazere.core.collection;

import com.trazere.core.functional.Predicate;
import com.trazere.core.lang.ExIterable;
import com.trazere.core.util.Maybe;
import com.trazere.core.util.Tuple2;
import java.util.Collection;

// TODO: comparison and hashing ?

/**
 * The {@link Feed} interface defines lazy sequences of elements.
 * 
 * @param <E> Type of the elements.
 * @since 2.0
 */
public interface Feed<E>
extends ExIterable<E> {
	// Traversable.
	
	/**
	 * Takes the elements of this feed while the given predicates holds.
	 * 
	 * @param predicate Filter predicate.
	 * @return A feed of the taken elements.
	 * @since 2.0
	 */
	default Feed<E> takeWhile(final Predicate<? super E> predicate) {
		assert null != predicate;
		
		final Feed<E> self = this;
		return new BaseMemoizedFeed<E>() {
			@Override
			protected Maybe<? extends Tuple2<? extends E, ? extends Feed<? extends E>>> compute() {
				return self.optionalItem().flatMap((final Tuple2<? extends E, ? extends Feed<? extends E>> item) -> {
					final E head = item.get1();
					if (predicate.evaluate(head)) {
						return Maybe.some(new Tuple2<>(head, item.get2().takeWhile(predicate)));
					} else {
						return Maybe.none();
					}
				});
			}
		};
	}
	
	/**
	 * Drops the elements of this feed while the given predicates holds.
	 * 
	 * @param predicate Filter predicate.
	 * @return A feed of the remaining elements.
	 * @since 2.0
	 */
	default Feed<E> dropWhile(final Predicate<? super E> predicate) {
		assert null != predicate;
		
		final Feed<E> self = this;
		return new BaseMemoizedFeed<E>() {
			@Override
			protected Maybe<? extends Tuple2<? extends E, ? extends Feed<? extends E>>> compute() {
				Feed<? extends E> iterFeed = self;
				while (true) {
					final Maybe<? extends Tuple2<? extends E, ? extends Feed<? extends E>>> maybeItem = iterFeed.optionalItem();
					if (maybeItem.isSome()) {
						final Tuple2<? extends E, ? extends Feed<? extends E>> item = maybeItem.asSome().getValue();
						if (predicate.evaluate(item.get1())) {
							iterFeed = item.get2();
						} else {
							return maybeItem;
						}
					} else {
						return Maybe.none();
					}
				}
			}
		};
	}
	
	/**
	 * Groups the elements of this feed into batches of the given size.
	 *
	 * @return A feed of the batches of elements.
	 * @since 2.0
	 */
	@Override
	default <B extends Collection<? super E>> Feed<B> group(final int n, final CollectionFactory<? super E, B> batchFactory) {
		assert null != batchFactory;
		
		final Feed<E> self = this;
		return new BaseMemoizedFeed<B>() {
			@Override
			protected Maybe<? extends Tuple2<? extends B, ? extends Feed<? extends B>>> compute() {
				Feed<? extends E> iterFeed = self;
				int iterN = n;
				final B batch = batchFactory.build(n);
				while (iterN > 0) {
					final Maybe<? extends Tuple2<? extends E, ? extends Feed<? extends E>>> maybeItem = iterFeed.optionalItem();
					if (maybeItem.isSome()) {
						final Tuple2<? extends E, ? extends Feed<? extends E>> item = maybeItem.asSome().getValue();
						batch.add(item.get1());
						iterFeed = item.get2();
						iterN -= 1;
					} else {
						break;
					}
				}
				if (batch.isEmpty()) {
					return Maybe.none();
				} else {
					return Maybe.some(new Tuple2<>(batch, iterFeed.group(n, batchFactory)));
				}
			}
		};
	}
}
