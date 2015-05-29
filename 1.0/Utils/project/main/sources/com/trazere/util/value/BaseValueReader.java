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
package com.trazere.util.value;

import com.trazere.util.record.BaseParametrable;
import com.trazere.util.record.Record;
import com.trazere.util.text.Describable;
import com.trazere.util.text.Description;
import com.trazere.util.text.TextUtils;

/**
 * The {@link BaseValueReader} abstract class provides a skeleton implementation of {@link ValueReader value readers}.
 * 
 * @param <T> Type of the values.
 * @deprecated To be removed.
 */
@Deprecated
public abstract class BaseValueReader<T>
extends BaseParametrable<String, Object>
implements ValueReader<T>, Describable {
	/**
	 * Instanciate a new reader with the given type and nullablity.
	 * 
	 * @param valueClass The type of the values.
	 * @param nullable The flag indicating whether the values can be <code>null</code> or not.
	 */
	protected BaseValueReader(final Class<T> valueClass, final boolean nullable) {
		assert null != valueClass;
		
		// Initialization.
		_valueClass = valueClass;
		_nullable = nullable;
	}
	
	// Type parameters.
	
	/** Type of the values. */
	protected final Class<T> _valueClass;
	
	@Override
	public Class<T> getValueClass() {
		return _valueClass;
	}
	
	// Nullabity.
	
	/** The flag indicating whether the values can be <code>null</code> or not. */
	protected final boolean _nullable;
	
	@Override
	public boolean isNullable() {
		return _nullable;
	}
	
	// Function.
	
	@Override
	public T evaluate(final Record<String, Object> parameters)
	throws ValueException {
		return read(parameters);
	}
	
	// Object.
	
	@Override
	public String toString() {
		return TextUtils.computeDescription(this);
	}
	
	@Override
	public void fillDescription(final Description description) {
		description.append("Type", _valueClass.getName());
		description.append("Nullable", _nullable);
	}
}
