/*
 *  Copyright 2006-2009 Julien Dufour
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
package com.trazere.util.lang.ref;

import com.trazere.util.type.Maybe;

/**
 * The {@link ReferenceListener} interface defines listeners of references.
 * 
 * @param <T> Type of the observed values.
 * @see ObservableReference
 */
public interface ReferenceListener<T> {
	/**
	 * Notify the receiver listener that the value of the observed reference has changed.
	 * 
	 * @param value The new value.
	 */
	public void updatedReference(final Maybe<? extends T> value);
}
