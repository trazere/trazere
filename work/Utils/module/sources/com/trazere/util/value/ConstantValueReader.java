/*
 *  Copyright 2006-2008 Julien Dufour
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

import com.trazere.util.lang.HashCode;
import com.trazere.util.lang.LangUtils;
import com.trazere.util.record.Record;
import com.trazere.util.record.RecordSignature;
import com.trazere.util.record.RecordSignatureBuilder;
import com.trazere.util.record.SimpleRecordSignature;
import com.trazere.util.text.Description;

/**
 * The {@link ConstantValueReader} class reads constant values.
 * <p>
 * This reader does not use the parameters it is given. It has therefore no requirements over them.
 * 
 * @param <T> Type of the values.
 */
public class ConstantValueReader<T>
extends AbstractValueReader<T> {
	/** Produced value. May be <code>null</code>. */
	protected final T _value;
	
	/**
	 * Instanciate a new reader producing the given value.
	 * 
	 * @param value Produced value. May be <code>null</code>.
	 * @param type Type of the produced value.
	 */
	public ConstantValueReader(final T value, final Class<T> type) {
		super(type);
		
		// Initialization.
		_value = value;
	}
	
	/**
	 * Get the value produced by the receiver reader.
	 * 
	 * @return The value. May be <code>null</code>.
	 */
	public T getValue() {
		return _value;
	}
	
	@Override
	public RecordSignature<String, Object> getRequirements() {
		return SimpleRecordSignature.build();
	}
	
	public <B extends RecordSignatureBuilder<String, Object, ?>> B unifyRequirements(final B builder) {
		return builder;
	}
	
	public T read(final Record<String, Object> parameters) {
		return _value;
	}
	
	@Override
	public int hashCode() {
		final HashCode result = new HashCode(this);
		result.append(_type);
		result.append(_value);
		return result.get();
	}
	
	@Override
	public boolean equals(final Object object) {
		if (this == object) {
			return true;
		} else if (null != object && getClass().equals(object.getClass())) {
			final ConstantValueReader<?> reader = (ConstantValueReader<?>) object;
			return _type.equals(reader._type) && LangUtils.equals(_value, reader._value);
		} else {
			return false;
		}
	}
	
	@Override
	public void fillDescription(final Description description) {
		super.fillDescription(description);
		description.append("Value", _value);
	}
}
