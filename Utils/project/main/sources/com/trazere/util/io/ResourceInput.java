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
package com.trazere.util.io;

import com.trazere.util.lang.HashCode;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * The {@link FileInput} class implements data inputs based on URLs.
 * 
 * @deprecated Use {@link com.trazere.core.io.ResourceInput}.
 */
@Deprecated
public class ResourceInput
implements Input {
	/** Base class. */
	protected final Class<?> _base;
	
	/** Name of the resource. */
	protected final String _name;
	
	/**
	 * Instanciate a new data input with the given URL.
	 * 
	 * @param base Base class
	 * @param name Name of the resource.
	 * @deprecated Use {@link com.trazere.core.io.ResourceInput#ResourceInput(Class, String)}.
	 */
	@Deprecated
	public ResourceInput(final Class<?> base, final String name) {
		assert null != base;
		assert null != name;
		
		// Initialization.
		_base = base;
		_name = name;
	}
	
	/**
	 * @deprecated Use {@link com.trazere.core.io.ResourceInput#getBase()}.
	 */
	@Deprecated
	@SuppressWarnings("javadoc")
	public Class<?> getBase() {
		return _base;
	}
	
	/**
	 * @deprecated Use {@link com.trazere.core.io.ResourceInput#getName()}.
	 */
	@Deprecated
	@SuppressWarnings("javadoc")
	public String getName() {
		return _name;
	}
	
	@Override
	public boolean exists()
	throws IOException {
		return null != _base.getResource(_name);
	}
	
	@Override
	public InputStream open()
	throws IOException {
		final InputStream stream = _base.getResourceAsStream(_name);
		if (null != stream) {
			return stream;
		} else {
			throw new FileNotFoundException("Missing resource \"" + _name + "\" from class \"" + _base + "\"");
		}
	}
	
	@Override
	public int hashCode() {
		final HashCode result = new HashCode(this);
		result.append(_base);
		result.append(_name);
		return result.get();
	}
	
	@Override
	public boolean equals(final Object object) {
		if (this == object) {
			return true;
		} else if (null != object && getClass().equals(object.getClass())) {
			final ResourceInput input = (ResourceInput) object;
			return _base.equals(input._base) && _name.equals(input._name);
		} else {
			return false;
		}
	}
	
	@Override
	public String toString() {
		return _base + "#" + _name;
	}
}
