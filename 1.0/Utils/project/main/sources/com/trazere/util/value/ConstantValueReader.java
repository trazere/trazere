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
 * @param <T> Type of the value.
 * @deprecated To be removed.
 */
@Deprecated
public class ConstantValueReader<T>
extends BaseValueReader<T> {
	/**
	 * Build a new reader producing the given value.
	 * 
	 * @param <T> Type of the value.
	 * @param value The produced value. May be <code>null</code>.
	 * @param type Type of the produced value.
	 * @return The built reader.
	 */
	public static <T> ConstantValueReader<T> build(final T value, final Class<T> type) {
		return new ConstantValueReader<T>(value, type);
	}
	
	/** The produced value. May be <code>null</code>. */
	protected final T _value;
	
	/**
	 * Instanciate a new reader producing the given value.
	 * 
	 * @param value The produced value. May be <code>null</code>.
	 * @param type Type of the produced value.
	 */
	public ConstantValueReader(final T value, final Class<T> type) {
		super(type, null == value);
		
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
	
	@Override
	public <B extends RecordSignatureBuilder<String, Object, ?>> B unifyRequirements(final B builder) {
		return builder;
	}
	
	@Override
	public T read(final Record<String, Object> parameters) {
		return _value;
	}
	
	@Override
	public ValueReader<T> compose(final RecordReader<String, Object> reader) {
		return this;
	}
	
	@Override
	public int hashCode() {
		final HashCode result = new HashCode(this);
		result.append(_valueClass);
		result.append(_value);
		return result.get();
	}
	
	@Override
	public boolean equals(final Object object) {
		if (this == object) {
			return true;
		} else if (null != object && getClass().equals(object.getClass())) {
			final ConstantValueReader<?> reader = (ConstantValueReader<?>) object;
			return _valueClass.equals(reader._valueClass) && LangUtils.safeEquals(_value, reader._value);
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
