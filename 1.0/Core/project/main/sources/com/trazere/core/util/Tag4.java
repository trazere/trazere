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

/**
 * The {@link Tag4} interface defines sum types that support a case associated to a fourth tag.
 * 
 * @param <T> Type of the case.
 * @since 1.0
 */
public interface Tag4<T extends Tag4<T>> {
	/**
	 * Indicates whether this object is an instance of the case associated to the fourth tag.
	 * 
	 * @return <code>true</code> when the object is an instance of the case associated to the fourth tag, <code>false</code> otherwise.
	 * @since 1.0
	 */
	default boolean is4() {
		return false;
	}
	
	/**
	 * Gets a view of this object as an instance of the case associated to the fourth tag.
	 * 
	 * @return The view.
	 * @throws InvalidConstructorException When the object is not an instance of the case associated to the fourth tag.
	 * @since 1.0
	 */
	default T as4()
	throws InvalidConstructorException {
		throw InvalidConstructorException.build(this, Tag4.class);
	}
}
