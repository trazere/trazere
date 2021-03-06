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

import com.trazere.util.function.Function0;
import com.trazere.util.type.Maybe;
import com.trazere.util.type.Tuple2;
import java.util.NoSuchElementException;

/**
 * The {@link Feed} interface defines lazy series of elements.
 * 
 * @param <T> Type of the elements.
 * @param <X> Type of the exceptions.
 * @deprecated Use {@link com.trazere.core.collection.Feed}.
 */
@Deprecated
public interface Feed<T, X extends Exception>
extends Function0<Maybe<? extends Tuple2<? extends T, ? extends Feed<? extends T, ? extends X>>>, X> {
	/**
	 * Tests whether the receiver feed is empty.
	 * 
	 * @return <code>true</code> when the feed is empty, <code>false</code> otherwise.
	 * @throws X When the emptyness of the feed cannot be computed.
	 * @deprecated Use {@link com.trazere.core.collection.Feed#isEmpty()}.
	 */
	@Deprecated
	public boolean isEmpty()
	throws X;
	
	/**
	 * Gets the head element of the receiver feed.
	 * 
	 * @return The element.
	 * @throws NoSuchElementException When the feed is empty.
	 * @throws X When the head element cannot be computed.
	 * @deprecated Use {@link com.trazere.core.collection.Feed#head()}.
	 */
	@Deprecated
	public T getHead()
	throws NoSuchElementException, X;
	
	/**
	 * Gets the tail of the receiver feed.
	 * 
	 * @return The tail.
	 * @throws NoSuchElementException When the feed is empty.
	 * @throws X When the tail cannot be computed.
	 * @deprecated Use {@link com.trazere.core.collection.Feed#tail()}.
	 */
	@Deprecated
	public Feed<? extends T, ? extends X> getTail()
	throws NoSuchElementException, X;
}
