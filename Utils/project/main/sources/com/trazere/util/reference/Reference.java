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
package com.trazere.util.reference;

import com.trazere.util.function.Function0;
import com.trazere.util.type.Maybe;
import com.trazere.util.type.Maybe.None;
import com.trazere.util.type.Maybe.Some;

/**
 * The {@link Reference} interface defines object references.
 * 
 * @param <T> Type of the referenced value.
 * @deprecated Use {@link com.trazere.core.reference.Reference}.
 */
@Deprecated
public interface Reference<T> {
	/**
	 * Tests whether the receiver reference is set.
	 * 
	 * @return <code>true</code> when the reference is set, <code>false</code> otherwise.
	 * @deprecated Use {@link com.trazere.core.reference.Reference#isSet()}.
	 */
	@Deprecated
	public boolean isSet();
	
	/**
	 * Gets the value set in the receiver reference.
	 * <p>
	 * The reference must be set.
	 * 
	 * @return The set value.
	 * @throws ReferenceNotSetException When the reference is not set.
	 * @deprecated Use {@link com.trazere.core.reference.Reference#get()}.
	 */
	@Deprecated
	public T get()
	throws ReferenceNotSetException;
	
	/**
	 * Gets the value set in the receiver reference.
	 * 
	 * @param defaultValue Default default. Maybe <code>null</code>.
	 * @return The set value or the given default value when the reference is not set. Maybe <code>null</code>.
	 * @deprecated Use {@link com.trazere.core.reference.Reference#get(Object)}.
	 */
	@Deprecated
	public T get(final T defaultValue);
	
	/**
	 * Gets the value set in the receiver reference.
	 * 
	 * @param <X> Type of the default value evaluation exceptions.
	 * @param defaultValue Default default.
	 * @return The set value or the given default value when no values are set. Maybe <code>null</code>.
	 * @throws X When the default value cannot be evaluated.
	 * @deprecated Use {@link com.trazere.core.reference.Reference#get(com.trazere.core.functional.Thunk)}.
	 */
	@Deprecated
	public <X extends Exception> T get(final Function0<? extends T, X> defaultValue)
	throws X;
	
	/**
	 * Gets a view of the value set in the receiver reference as an instance of {@link Maybe}.
	 * 
	 * @return The set value wrapped in {@link Some}, or {@link None} when the reference is not set.
	 * @deprecated Use {@link com.trazere.core.reference.Reference#asMaybe()}.
	 */
	@Deprecated
	public Maybe<T> asMaybe();
}
