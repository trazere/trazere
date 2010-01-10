/*
 *  Copyright 2006-2010 Julien Dufour
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
package com.trazere.util;

// TODO: move in another package

/**
 * The {@link Decorator} abstract class provides a skeleton to implement decorators.
 * 
 * @param <T>
 */
public abstract class Decorator<T> {
	/** The decorated object. */
	protected final T _decorated;
	
	/**
	 * Instantiate a new decorator.
	 * 
	 * @param decorated The decorated object.
	 */
	public Decorator(final T decorated) {
		assert null != decorated;
		
		// Initialization.
		_decorated = decorated;
	}
}
