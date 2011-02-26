/*
 *  Copyright 2006-2011 Julien Dufour
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
package com.trazere.util.lang;

/**
 * The {@link ThrowableFactory} interface defines factories of {@link Throwable throwable objects}.
 * 
 * @param <T> Type of the throwables.
 */
public interface ThrowableFactory<T extends Throwable>
extends Factory<T, RuntimeException> {
	/**
	 * Build a new throwable instance.
	 * 
	 * @return The built throwable.
	 */
	public T build();
	
	/**
	 * Build a new throwable instance with the given message.
	 * 
	 * @param message The message.
	 * @return The built throwable.
	 */
	public T build(final String message);
	
	/**
	 * Build a new throwable instance with the given cause.
	 * 
	 * @param cause The throwable.
	 * @return The built throwable.
	 */
	public T build(final Throwable cause);
	
	/**
	 * Build a new throwable instance with the given message and cause.
	 * 
	 * @param message The message.
	 * @param cause The throwable.
	 * @return The built throwable.
	 */
	public T build(final String message, final Throwable cause);
}
