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
 * The {@link Field5} interface defines the fifth field of product types.
 * 
 * @param <T> Type of the value of the field.
 */
public interface Field5<T> {
	/**
	 * Gets the value of the fifth field of this object.
	 * 
	 * @return The value.
	 */
	T get5();
}
