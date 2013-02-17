/*
 *  Copyright 2006-2012 Julien Dufour
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
package com.trazere.util.value;

import com.trazere.util.text.Describable;
import com.trazere.util.text.Description;
import com.trazere.util.text.TextUtils;

/**
 * The {@link BaseValueSerializer} abstract class implements skeletons of {@link ValueSerializer value serializers}.
 * 
 * @param <T> Type of the values.
 * @param <R> Type of the representations.
 * @param <X> Type of the exceptions.
 */
public abstract class BaseValueSerializer<T, R, X extends Exception>
implements ValueSerializer<T, R, X>, Describable {
	/**
	 * Instantiates a new serializer.
	 * 
	 * @param valueClass The type of the values.
	 * @param representationClass The type of the representations.
	 */
	public BaseValueSerializer(final Class<T> valueClass, final Class<R> representationClass) {
		assert null != valueClass;
		assert null != representationClass;
		
		// Initialization.
		_valueClass = valueClass;
		_representationClass = representationClass;
	}
	
	// Type parameters.
	
	/** Type of the values. */
	protected final Class<T> _valueClass;
	
	@Override
	public Class<T> getValueClass() {
		return _valueClass;
	}
	
	/** Type of the representations. */
	protected final Class<R> _representationClass;
	
	@Override
	public Class<R> getRepresentationClass() {
		return _representationClass;
	}
	
	// Object.
	
	@Override
	public String toString() {
		return TextUtils.computeDescription(this);
	}
	
	@Override
	public void fillDescription(final Description description) {
		description.append("Type", _valueClass.getName());
		description.append("Representation", _representationClass.getName());
	}
}
