/*
 *  Copyright 2006-2013 Julien Dufour
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

import com.trazere.core.util.Maybe;
import com.trazere.core.util.Tuple2;

/**
 * The {@link MemoizedFeed} class provides a skeleton implementation of feeds that memoize their value.
 * 
 * @param <E> Type of the elements.
 */
public abstract class MemoizedFeed<E>
implements Feed<E> {
	/** Indicates whether the feed has been computed or not. */
	protected boolean _evaluated = false;
	
	/** Head and tail of the feed. */
	protected Maybe<? extends Tuple2<? extends E, ? extends Feed<? extends E>>> _feed = null;
	
	@Override
	public Maybe<? extends Tuple2<? extends E, ? extends Feed<? extends E>>> evaluate() {
		if (!_evaluated) {
			_feed = compute();
			_evaluated = true;
		}
		return _feed;
	}
	
	/**
	 * Computes the head and tail of this feed.
	 * 
	 * @return The computed head and tail.
	 */
	protected abstract Maybe<? extends Tuple2<? extends E, ? extends Feed<? extends E>>> compute();
}
