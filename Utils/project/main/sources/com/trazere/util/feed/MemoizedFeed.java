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
package com.trazere.util.feed;

import com.trazere.util.type.Maybe;
import com.trazere.util.type.Tuple2;

/**
 * The {@link MemoizedFeed} class provides a skeleton implementation of feeds that memoize their value.
 * 
 * @param <T> Type of the elements.
 * @param <X> Type of the exceptions.
 * @deprecated Use {@link com.trazere.core.collection.MemoizedFeed}.
 */
@Deprecated
public abstract class MemoizedFeed<T, X extends Exception>
extends BaseFeed<T, X> {
	/** Indicates whether the feed has been computed. */
	protected boolean _evaluated = false;
	
	/** Head and tail of the feed. */
	protected Maybe<? extends Tuple2<? extends T, ? extends Feed<? extends T, ? extends X>>> _value = null;
	
	@Override
	public Maybe<? extends Tuple2<? extends T, ? extends Feed<? extends T, ? extends X>>> evaluate()
	throws X {
		if (!_evaluated) {
			_value = compute();
			_evaluated = true;
		}
		return _value;
	}
	
	/**
	 * Computes the head and tail of the receiver feed.
	 * 
	 * @return The computed head and tail.
	 * @throws X On failure.
	 * @deprecated Use {@link com.trazere.core.collection.MemoizedFeed#compute()}.
	 */
	@Deprecated
	@SuppressWarnings("javadoc")
	protected abstract Maybe<? extends Tuple2<? extends T, ? extends Feed<? extends T, ? extends X>>> compute()
	throws X;
}
