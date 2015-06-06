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

import com.trazere.util.type.Maybe;
import com.trazere.util.type.TypeUtils;

/**
 * The {@link ReferenceUtils} class provides various utilities regarding references.
 * 
 * @deprecated Use core.
 */
@Deprecated
public class ReferenceUtils {
	/**
	 * Adapts the given util reference to a core reference.
	 * 
	 * @param <T> Type of the referenced value.
	 * @param reference Util reference to adapt.
	 * @return The adapted core reference.
	 * @deprecated Use {@link com.trazere.core.reference.Reference}.
	 */
	@Deprecated
	public static <T> com.trazere.core.reference.Reference<T> toReference(final Reference<? extends T> reference) {
		assert null != reference;
		
		return new com.trazere.core.reference.Reference<T>() {
			@Override
			public boolean isSet() {
				return reference.isSet();
			}
			
			@Override
			public T get()
			throws ReferenceNotSetException {
				try {
					return reference.get();
				} catch (final com.trazere.util.reference.ReferenceNotSetException exception) {
					throw new ReferenceNotSetException(exception);
				}
			}
			
			@Override
			public com.trazere.core.util.Maybe<T> asMaybe() {
				return TypeUtils.toMaybe(reference.asMaybe());
			}
		};
	}
	
	/**
	 * Adapts the given core reference to an util reference.
	 * 
	 * @param <T> Type of the referenced value.
	 * @param reference Core reference to adapt.
	 * @return The adapted util reference.
	 * @deprecated Use {@link com.trazere.core.reference.Reference}.
	 */
	@Deprecated
	public static <T> Reference<T> fromReference(final com.trazere.core.reference.Reference<? extends T> reference) {
		assert null != reference;
		
		return new BaseReference<T>() {
			@Override
			public boolean isSet() {
				return reference.isSet();
			}
			
			@Override
			public T get()
			throws ReferenceNotSetException {
				try {
					return reference.get();
				} catch (final com.trazere.core.reference.ReferenceNotSetException exception) {
					throw new ReferenceNotSetException(exception);
				}
			}
			
			@Override
			public Maybe<T> asMaybe() {
				return TypeUtils.fromMaybe(reference.asMaybe());
			}
		};
	}
	
	private ReferenceUtils() {
		// Prevent instantiation.
	}
}
