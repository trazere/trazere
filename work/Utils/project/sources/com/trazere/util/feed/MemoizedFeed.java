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
package com.trazere.util.feed;

import com.trazere.util.type.Maybe;
import com.trazere.util.type.Tuple2;

/**
 * DOCME
 * 
 * @param <T>
 * @param <X>
 */
public abstract class MemoizedFeed<T, X extends Exception>
extends BaseFeed<T, X> {
	// Function.
	
	protected boolean _evaluated = false;
	protected Maybe<Tuple2<T, Feed<T, X>>> _value = null;
	
	@Override
	public Maybe<Tuple2<T, Feed<T, X>>> evaluate()
	throws X {
		if (!_evaluated) {
			_value = compute();
			_evaluated = true;
		}
		return _value;
	}
	
	protected abstract Maybe<Tuple2<T, Feed<T, X>>> compute()
	throws X;
}
