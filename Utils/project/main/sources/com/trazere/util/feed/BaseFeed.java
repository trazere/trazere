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
import java.util.NoSuchElementException;

/**
 * The {@link BaseFeed} class provides a skeleton implementation of feeds.
 * 
 * @param <T> Type of the elements.
 * @param <X> Type of the exceptions.
 * @deprecated Use {@link com.trazere.core.collection.Feed}.
 */
@Deprecated
public abstract class BaseFeed<T, X extends Exception>
implements Feed<T, X> {
	@Override
	public boolean isEmpty()
	throws X {
		return evaluate().isNone();
	}
	
	public Tuple2<? extends T, ? extends Feed<? extends T, ? extends X>> get()
	throws NoSuchElementException, X {
		final Maybe<? extends Tuple2<? extends T, ? extends Feed<? extends T, ? extends X>>> value = evaluate();
		if (value.isSome()) {
			return value.asSome().getValue();
		} else {
			throw new NoSuchElementException();
		}
	}
	
	@Override
	public T getHead()
	throws NoSuchElementException, X {
		return get().getFirst();
	}
	
	@Override
	public Feed<? extends T, ? extends X> getTail()
	throws NoSuchElementException, X {
		return get().getSecond();
	}
}
