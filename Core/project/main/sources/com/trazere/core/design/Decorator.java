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
package com.trazere.core.design;

/**
 * The {@link Decorator} class provides a skeleton implementation of decorators.
 * 
 * @param <T> Type of the decorated object.
 * @since 2.0
 */
public abstract class Decorator<T> {
	/**
	 * Decorated object.
	 * 
	 * @since 2.0
	 */
	protected final T _decorated;
	
	/**
	 * Instantiates a new decorator.
	 * 
	 * @param decorated Decorated object.
	 * @since 2.0
	 */
	public Decorator(final T decorated) {
		assert null != decorated;
		
		// Initialization.
		_decorated = decorated;
	}
	
	// Object.
	
	@Override
	public String toString() {
		return _decorated.toString();
	}
}
