/*
 *  Copyright 2008 Julien Dufour
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
package com.trazere.util.parameter;

import com.trazere.util.Assert;
import com.trazere.util.text.Describable;
import com.trazere.util.text.TextUtils;

/**
 * The {@link ParameterSignature} class describes the signature of a parameter, that is its name and the type of its values.
 * 
 * @param <T> Type of the parameter.
 */
public final class ParameterSignature<T>
implements Describable {
	/** Name of the parameter. */
	private final String _name;
	
	/** Java class of the value of the parameter. */
	private final Class<T> _type;
	
	/**
	 * Instantiate a new parameter signature with the given name and type.
	 * 
	 * @param name Name of the parameter.
	 * @param type Java class of the value of the parameter.
	 */
	public ParameterSignature(final String name, final Class<T> type) {
		Assert.notNull(name);
		Assert.notNull(type);
		
		// Initialization.
		_name = name;
		_type = type;
	}
	
	/**
	 * Get the name of the parameter described by the receiver signature.
	 * 
	 * @return The name.
	 */
	public String getName() {
		return _name;
	}
	
	/**
	 * Get the type of the values of the parameter described by the receiver signature.
	 * 
	 * @return The Java class of the type.
	 */
	public Class<T> getType() {
		return _type;
	}
	
	@Override
	public int hashCode() {
		int result = getClass().hashCode();
		result = result * 31 + _name.hashCode();
		result = result * 31 + _type.hashCode();
		return result;
	}
	
	@Override
	public boolean equals(final Object object) {
		if (this == object) {
			return true;
		} else if (null != object && getClass().equals(object.getClass())) {
			final ParameterSignature<?> signature = (ParameterSignature<?>) object;
			return _name.equals(signature._name) && _type.equals(signature._type);
		} else {
			return false;
		}
	}
	
	@Override
	public String toString() {
		return TextUtils.computeDescription(this);
	}
	
	public void fillDescription(final StringBuilder builder) {
		builder.append(" - Name = ").append(_name);
		builder.append(" - Type = ").append(_type);
	}
}
