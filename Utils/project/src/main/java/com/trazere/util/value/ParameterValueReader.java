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

import com.trazere.util.lang.HashCode;
import com.trazere.util.record.FieldSignature;
import com.trazere.util.record.IncompatibleFieldException;
import com.trazere.util.record.Record;
import com.trazere.util.record.RecordException;
import com.trazere.util.record.RecordSignatureBuilder;
import com.trazere.util.text.Description;

/**
 * The {@link ParameterValueReader} class reads values from the parameters.
 * <p>
 * When the parameter read by this reader is mandatory, it has a single requirement corresponding to the parameter. When the parameter is optional, the reader
 * has no requirements and produce a null value when the parameter is missing.
 * 
 * @param <T> Type of the value.
 */
public class ParameterValueReader<T>
extends BaseValueReader<T> {
	/**
	 * Build a new parameter reader with the given parameter name and type.
	 * 
	 * @param <T> Type of the value.
	 * @param name The name of the parameter to read.
	 * @param type The type of the value.
	 * @return The built reader.
	 */
	public static <T> ParameterValueReader<T> build(final String name, final Class<T> type) {
		return new ParameterValueReader<T>(name, type, true);
	}
	
	/**
	 * Build a new parameter reader with the given parameter name and type.
	 * 
	 * @param <T> Type of the value.
	 * @param name The name of the parameter to read.
	 * @param type The type of the value.
	 * @param nullable The flag indicating whether the value of the parameter can be <code>null</code> or not.
	 * @return The built reader.
	 */
	public static <T> ParameterValueReader<T> build(final String name, final Class<T> type, final boolean nullable) {
		return new ParameterValueReader<T>(name, type, nullable);
	}
	
	/**
	 * Build a new parameter reader with the given parameter name and type.
	 * 
	 * @param <T> Type of the value.
	 * @param signature The signature of the parameter.
	 * @return The built reader.
	 */
	public static <T> ParameterValueReader<T> build(final FieldSignature<String, T> signature) {
		return new ParameterValueReader<T>(signature);
	}
	
	/** The name of the parameter to read. */
	protected final String _name;
	
	/**
	 * Instantiate a new reader with the given parameter name and type and flag.
	 * 
	 * @param name The name of the parameter to read.
	 * @param type The type of the value.
	 * @param nullable The flag indicating whether the value of the parameter can be <code>null</code> or not.
	 */
	public ParameterValueReader(final String name, final Class<T> type, final boolean nullable) {
		super(type, nullable);
		
		// Checks.
		assert null != name;
		
		// Initialization.
		_name = name;
	}
	
	/**
	 * Instanciate a new parameter reader with the given field signature.
	 * 
	 * @param signature The signature of the parameter.
	 */
	public ParameterValueReader(final FieldSignature<String, T> signature) {
		this(signature.getKey(), signature.getType(), signature.isNullable());
	}
	
	/**
	 * Get the name of the parameter to read.
	 * 
	 * @return The name.
	 */
	public String getName() {
		return _name;
	}
	
	@Override
	public <B extends RecordSignatureBuilder<String, Object, ?>> B unifyRequirements(final B builder)
	throws IncompatibleFieldException {
		builder.unify(_name, _valueClass, _nullable);
		return builder;
	}
	
	@Override
	public T read(final Record<String, Object> parameters)
	throws ValueException {
		try {
			return parameters.getTyped(_name, _valueClass);
		} catch (final RecordException exception) {
			throw new ValueException(exception);
		}
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public ValueReader<T> compose(final RecordReader<String, Object> reader)
	throws ValueException {
		assert null != reader;
		
		// Get the value reader which produces the parameter to read.
		final ValueReader<? extends Object> valueReader = reader.get(_name);
		
		// Check the nullability.
		if (!_nullable && valueReader.isNullable()) {
			throw new ValueException("Value reader of field \"" + _name + "\" should not be nullable in record reader " + reader);
		}
		
		// Check the type.
		final Class<? extends Object> valueClass = valueReader.getValueClass();
		if (_valueClass.equals(valueClass)) {
			return (ValueReader<T>) valueReader;
		} else if (_valueClass.isAssignableFrom(valueClass)) {
			return adapt((ValueReader<? extends T>) valueReader);
		} else {
			throw new ValueException("Value reader of field \"" + _name + "\" is not compatible with type \"" + _valueClass + "\" in record reader " + reader);
		}
	}
	
	private ValueReader<T> adapt(final ValueReader<? extends T> valueReader) {
		assert null != valueReader;
		
		return new BaseValueReader<T>(_valueClass, _nullable) {
			@Override
			public <B extends RecordSignatureBuilder<String, Object, ?>> B unifyRequirements(final B builder)
			throws IncompatibleFieldException {
				return valueReader.unifyRequirements(builder);
			}
			
			@Override
			public T read(final Record<String, Object> parameters)
			throws ValueException {
				return valueReader.read(parameters);
			}
			
			@Override
			public ValueReader<T> compose(final RecordReader<String, Object> reader)
			throws ValueException {
				return adapt(valueReader.compose(reader));
			}
		};
	}
	
	@Override
	public int hashCode() {
		final HashCode result = new HashCode(this);
		result.append(_name);
		result.append(_valueClass);
		result.append(_nullable);
		return result.get();
	}
	
	@Override
	public boolean equals(final Object object) {
		if (this == object) {
			return true;
		} else if (null != object && getClass().equals(object.getClass())) {
			final ParameterValueReader<?> reader = (ParameterValueReader<?>) object;
			return _name.equals(reader._name) && _valueClass.equals(reader._valueClass) && _nullable == reader._nullable;
		} else {
			return false;
		}
	}
	
	@Override
	public void fillDescription(final Description description) {
		description.append("Name", _name);
		super.fillDescription(description);
	}
}
