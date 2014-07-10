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
package com.trazere.core.design;

/**
 * The {@link Factory} interface defines factories.
 * <p>
 * TODO: compare to factory design pattern
 * 
 * @param <T> Type of the built values.
 */
@FunctionalInterface
public interface Factory<T> {
	/**
	 * Builds a new value.
	 * 
	 * @return The built value.
	 */
	T build();
}
