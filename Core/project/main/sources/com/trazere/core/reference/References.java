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
package com.trazere.core.reference;

import com.trazere.core.util.Maybe;

/**
 * The {@link References} class provides various factories of references.
 * 
 * @see Reference
 */
public class References {
	/**
	 * Builds an unset reference.
	 * 
	 * @param <T> Type of the referenced value.
	 * @return The built reference.
	 */
	@SuppressWarnings("unchecked")
	public static <T> Reference<T> empty() {
		return (Reference<T>) EMTPY;
	}
	
	private static final Reference<?> EMTPY = new BaseReference<Object>() {
		@Override
		public boolean isSet() {
			return false;
		}
		
		@Override
		public Object get()
		throws ReferenceNotSetException {
			throw new ReferenceNotSetException();
		}
		
		@Override
		public Maybe<Object> asMaybe() {
			return Maybe.none();
		}
	};
	
	/**
	 * Builds a reference set with the given value.
	 * 
	 * @param <T> Type of the referenced value.
	 * @param value Value to set.
	 * @return The built reference.
	 */
	public static <T> Reference<T> fromValue(final T value) {
		return new BaseReference<T>() {
			@Override
			public boolean isSet() {
				return true;
			}
			
			@Override
			public T get() {
				return value;
			}
			
			@Override
			public Maybe<T> asMaybe() {
				return Maybe.some(value);
			}
		};
	}
	
	/**
	 * Builds a reference set with the given value if any.
	 * 
	 * @param <T> Type of the referenced value.
	 * @param value Value to set.
	 * @return The built reference.
	 */
	public static <T> Reference<T> fromValue(final Maybe<T> value) {
		return value.isSome() ? fromValue(value.asSome().getValue()) : References.<T>empty();
	}
	
	private References() {
		// Prevent instantiation.
	}
}
