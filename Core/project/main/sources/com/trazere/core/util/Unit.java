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
 * The {@link Unit} class implements a data type which represents an empty value (sequence of 0 elements).
 * 
 * @since 2.0
 */
public final class Unit {
	/**
	 * Singleton instance.
	 * 
	 * @since 2.0
	 */
	public static final Unit UNIT = new Unit();
	
	private Unit() {
		super();
	}
	
	// Object.
	
	@Override
	public String toString() {
		return "()";
	}
}
