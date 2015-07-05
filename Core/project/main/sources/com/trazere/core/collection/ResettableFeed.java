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
import com.trazere.core.text.Describable;
import com.trazere.core.text.Description;
import com.trazere.core.text.TextUtils;

/**
 * The {@link ResettableFeed} class implements memoized feeds that can be re-evaluated.
 * 
 * @param <E> Type of the elements.
 * @since 1.0
 */
public abstract class ResettableFeed<E>
extends BaseMemoizedFeed<E>
implements Releasable, Describable {
	/**
	 * Resets this feed, discarding its possibly memoized head and tail. The head and tail will be computed (again) the next time this feed is evaluated.
	 * 
	 * @since 1.0
	 */
	public void reset() {
		if (_evaluated) {
			// Reset.
			_evaluated = false;
			_value = null;
		}
	}
	
	// Releasable.
	
	@Override
	public void release() {
		reset();
	}
	
	// Object.
	
	@Override
	public String toString() {
		if (_evaluated) {
			return String.valueOf(_value);
		} else {
			return TextUtils.description(this);
		}
	}
	
	@Override
	public void appendDescription(final Description description) {
		// Nothing to do.
	}
}
