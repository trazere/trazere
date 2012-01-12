/*
 *  Copyright 2006-2012 Julien Dufour
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
package com.trazere.util.reference;

import com.trazere.util.type.Maybe;
import com.trazere.util.type.Maybe.None;
import com.trazere.util.type.Maybe.Some;

/**
 * The {@link Reference} interface defines object references.
 * 
 * @param <T> Type of the referenced value.
 */
public interface Reference<T> {
	/**
	 * Tests whether the receiver reference is set.
	 * 
	 * @return <code>true</code> when the reference is set, <code>false</code> otherwise.
	 */
	public boolean isSet();
	
	/**
	 * Gets the value set in the receiver reference.
	 * <p>
	 * The reference must be set.
	 * 
	 * @return The set value.
	 * @throws ReferenceNotSetException When the reference has not been set.
	 */
	public T get()
	throws ReferenceNotSetException;
	
	/**
	 * Gets a view of the value set in the receiver reference as an instance of {@link Maybe}.
	 * 
	 * @return The set value wrapped in {@link Some}, or {@link None} when the reference has not been set.
	 */
	public Maybe<T> asMaybe();
}
