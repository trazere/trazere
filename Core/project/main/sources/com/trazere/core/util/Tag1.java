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
package com.trazere.core.util;

// TODO: rename to Label1, Labelable ?
// TODO: extends Field1<Maybe<T>> ?

/**
 * The {@link Tag1} interface defines sum types that support a case associated to a first tag.
 * 
 * @param <T> Type of the case.
 * @since 2.0
 */
public interface Tag1<T extends Tag1<T>> {
	/**
	 * Indicates whether this object is an instance of the case associated to the first tag.
	 * 
	 * @return <code>true</code> when the object is an instance of the case associated to the first tag, <code>false</code> otherwise.
	 * @since 2.0
	 */
	default boolean is1() {
		return false;
	}
	
	/**
	 * Gets a view of this object as an instance of the case associated to the first tag.
	 * 
	 * @return The view.
	 * @throws InvalidConstructorException When the object is not an instance of the case associated to the first tag.
	 * @since 2.0
	 */
	default T as1()
	throws InvalidConstructorException {
		throw InvalidConstructorException.build(this, Tag1.class);
	}
}
