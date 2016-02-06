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

import com.trazere.core.lang.Releasable;

/**
 * The {@link ResettableFeed} interface defines memoized feeds that can be re-evaluated.
 * 
 * @param <E> Type of the elements.
 * @since 2.0
 */
public interface ResettableFeed<E>
extends MemoizedFeed<E>, Releasable {
	/**
	 * Resets this feed, discarding its possibly memoized head and tail. The head and tail will be computed (again) the next time this feed is evaluated.
	 * 
	 * @since 2.0
	 */
	void reset();
	
	// Releasable.
	
	@Override
	default void release() {
		reset();
	}
}
