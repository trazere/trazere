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
package com.trazere.util.collection;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * The {@link CheckedIterator} interface defines iterators which can fail (checked exceptions).
 * 
 * @param <T> Type of the values.
 * @param <X> Type of the exceptions.
 * @deprecated Use {@link Iterator}.
 */
@Deprecated
public interface CheckedIterator<T, X extends Exception> {
	/**
	 * Indicates whether the receiver iterator contains another value.
	 * 
	 * @return <code>true</code> when another value is available, <code>false</code> otherwise.
	 * @throws X When the availability test fails.
	 */
	public boolean hasNext()
	throws X;
	
	/**
	 * Gets the next value of the receiver iterator.
	 * <p>
	 * This method should only be called when {@link #hasNext()} returns <code>true</code>.
	 * 
	 * @return The next item.
	 * @throws NoSuchElementException When no more values are available.
	 * @throws X When the retrieval of the next value fails.
	 */
	public T next()
	throws NoSuchElementException, X;
}
