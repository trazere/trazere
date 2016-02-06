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

import com.trazere.core.util.Maybe;
import com.trazere.core.util.Tuple2;

/**
 * The {@link MemoizedFeed} interface defines feeds that memoize their head and tail.
 * 
 * @param <E> Type of the elements.
 * @since 2.0
 */
public interface MemoizedFeed<E>
extends Feed<E> {
	/**
	 * Indicates whether the head and tail of this feed has already been evaluated and memoized.
	 * 
	 * @return <code>true</code> when the head and tail have been evaluated and memoized, <code>false</code> otherwise.
	 * @since 2.0
	 */
	boolean isMemoized();
	
	/**
	 * Probes the memoized head and tail of this feed.
	 * 
	 * @return The memoized head and tail, or nothing when the head and tail have not been evaluated and memoized yet.
	 * @since 2.0
	 */
	Maybe<Maybe<? extends Tuple2<? extends E, ? extends Feed<? extends E>>>> probe();
}
