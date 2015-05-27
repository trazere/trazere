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
package com.trazere.core.util;

/**
 * The {@link Tag5} interface defines sum types that support a case associated to a fifth tag.
 * 
 * @param <T> Type of the case.
 */
public interface Tag5<T extends Tag5<T>> {
	/**
	 * Indicates whether this object is an instance of the case associated to the fifth tag.
	 * 
	 * @return <code>true</code> when the object is an instance of the case associated to the fifth tag, <code>false</code> otherwise.
	 */
	default boolean is5() {
		return false;
	}
	
	/**
	 * Gets a view of this object as an instance of the case associated to the fifth tag.
	 * 
	 * @return The view.
	 * @throws InvalidConstructorException When the object is not an instance of the case associated to the fifth tag.
	 */
	default T as5()
	throws InvalidConstructorException {
		throw InvalidConstructorException.build(this, Tag5.class);
	}
}
