/*
 *  Copyright 2006-2009 Julien Dufour
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

import com.trazere.util.function.AbstractParametrable;
import com.trazere.util.lang.ThrowableFactory;
import com.trazere.util.record.Record;
import com.trazere.util.text.Describable;
import com.trazere.util.text.Description;
import com.trazere.util.text.TextUtils;

/**
 * The {@link AbstractValueReader} abstract class implements skeletons of {@link ValueReader value readers}.
 * 
 * @param <T> Type of the values.
 */
public abstract class AbstractValueReader<T>
extends AbstractParametrable<String, Object, ValueException>
implements ValueReader<T>, Describable {
	/**
	 * Instanciate a new reader with the given type.
	 * 
	 * @param type Type of the values.
	 */
	protected AbstractValueReader(final Class<T> type) {
		assert null != type;
		
		// Initialization.
		_type = type;
	}
	
	// Type.
	
	/** Type of the values. */
	protected final Class<T> _type;
	
	public Class<T> getType() {
		return _type;
	}
	
	// Function.
	
	public T evaluate(final Record<String, Object> parameters)
	throws ValueException {
		return read(parameters);
	}
	
	@Override
	protected ThrowableFactory<ValueException> getThrowableFactory() {
		return ValueException.FACTORY;
	}
	
	// Object.
	
	@Override
	public String toString() {
		return TextUtils.computeDescription(this);
	}
	
	public void fillDescription(final Description description) {
		description.append("Type", _type.getName());
	}
}
