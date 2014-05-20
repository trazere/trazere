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
package com.trazere.util.type;

import com.trazere.core.text.TextUtils;

/**
 * {@link InvalidConstructorException} exceptions are thrown when trying to cast some instance of some algebraic data type according to a wrong constructor.
 * <p>
 * These exceptions are runtime exceptions in order to reduce clutter.
 * 
 * @deprecated Use {@link com.trazere.core.util.InvalidConstructorException}.
 */
@Deprecated
public class InvalidConstructorException
extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public static <T> InvalidConstructorException build(final T object, final Class<? extends T> type) {
		assert null != object;
		assert null != type;
		
		return new InvalidConstructorException(object + " is not a " + TextUtils.computeClassName(type));
	}
	
	private InvalidConstructorException(final String message) {
		super(message);
	}
}
