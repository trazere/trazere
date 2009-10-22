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

import com.trazere.util.lang.HashCode;
import com.trazere.util.record.FieldSignature;
import com.trazere.util.record.IncompatibleFieldException;
import com.trazere.util.record.Record;
import com.trazere.util.record.RecordException;
import com.trazere.util.record.RecordSignatureBuilder;
import com.trazere.util.text.Description;

// TODO: signature is incomplete when the parameter is optional !

/**
 * The {@link ParameterValueReader} class reads values from the parameters.
 * <p>
 * When the parameter read by this reader is mandatory, it has a single requirement corresponding to the parameter. When the parameter is optional, the reader
 * has no requirements and produce a null value when the parameter is missing.
 * 
 * @param <T> Type of the value.
 */
public class ParameterValueReader<T>
extends AbstractValueReader<T> {
	/**
	 * Build a new parameter reader with the given parameter name and type.
	 * 
	 * @param <T> Type of the value.
	 * @param name The name of the parameter to read.
	 * @param type The type of the value.
	 * @return The built reader.
	 */
	public static <T> ParameterValueReader<T> build(final String name, final Class<T> type) {
		return new ParameterValueReader<T>(name, type);
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
	
	/**
	 * Build a new parameter reader with the given parameter name and type.
	 * 
	 * @param <T> Type of the value.
	 * @param name The name of the parameter to read.
	 * @param type The type of the value.
	 * @param optional Flag indicating whether the parameter is optional or mandatory.
	 * @return The built reader.
	 */
	public static <T> ParameterValueReader<T> build(final String name, final Class<T> type, final boolean optional) {
		return new ParameterValueReader<T>(name, type, optional);
	}
	
	/**
	 * Build a new parameter reader with the given parameter name and type.
	 * 
	 * @param <T> Type of the value.
	 * @param signature The signature of the parameter.
	 * @param optional Flag indicating whether the parameter is optional or mandatory.
	 * @return The built reader.
	 */
	public static <T> ParameterValueReader<T> build(final FieldSignature<String, T> signature, final boolean optional) {
		return new ParameterValueReader<T>(signature, optional);
	}
	
	/** The name of the parameter to read. */
	protected final String _name;
	
	/** Flag indicating whether the parameter is optional or mandatory. */
	protected final boolean _optional;
	
	/**
	 * Instantiate a new mandatory parameter reader with the given parameter name and type.
	 * 
	 * @param name The name of the parameter to read.
	 * @param type The type of the value.
	 */
	public ParameterValueReader(final String name, final Class<T> type) {
		this(name, type, false);
	}
	
	/**
	 * Instanciate a new mandatory parameter reader with the given field signature.
	 * 
	 * @param signature The signature of the parameter.
	 */
	public ParameterValueReader(final FieldSignature<String, T> signature) {
		this(signature.getKey(), signature.getType(), false);
	}
	
	/**
	 * Instantiate a new reader with the given parameter name and type and flag.
	 * 
	 * @param name The name of the parameter to read.
	 * @param type The type of the value.
	 * @param optional Flag indicating whether the parameter is optional or mandatory.
	 */
	public ParameterValueReader(final String name, final Class<T> type, final boolean optional) {
		super(type);
		
		// Checks.
		assert null != name;
		
		// Initialization. 
		_name = name;
		_optional = optional;
	}
	
	/**
	 * Instanciate a new parameter reader with the given field signature.
	 * 
	 * @param signature The signature of the parameter.
	 * @param optional Flag indicating whether the parameter is optional or mandatory.
	 */
	public ParameterValueReader(final FieldSignature<String, T> signature, final boolean optional) {
		this(signature.getKey(), signature.getType(), optional);
	}
	
	/**
	 * Get the name of the parameter to read.
	 * 
	 * @return The name.
	 */
	public String getName() {
		return _name;
	}
	
	public <B extends RecordSignatureBuilder<String, Object, ?>> B unifyRequirements(final B builder)
	throws ValueException, IncompatibleFieldException {
		if (!_optional) {
			unify(_name, _type, builder);
		}
		return builder;
	}
	
	public T read(final Record<String, Object> parameters)
	throws ValueException {
		try {
			return _optional ? parameters.getTyped(_name, _type, null) : parameters.getTyped(_name, _type);
		} catch (final RecordException exception) {
			throw new ValueException(exception);
		}
	}
	
	@SuppressWarnings("unchecked")
	public ValueReader<T> compose(final RecordReader<String, Object> reader)
	throws ValueException {
		assert null != reader;
		
		if (!_optional || reader.contains(_name)) {
			final ValueReader<? extends Object> valueReader = reader.get(_name);
			final Class<? extends Object> type = valueReader.getType();
			if (_type.equals(type)) {
				return (ValueReader<T>) valueReader;
			} else if (_type.isAssignableFrom(valueReader.getType())) {
				return adapt((ValueReader<? extends T>) valueReader);
			} else {
				throw new ValueException("Value reader of field " + _name + " is not compatible with type " + _type + " in record reader " + reader);
			}
		} else {
			return ConstantValueReader.build(null, _type);
		}
	}
	
	private ValueReader<T> adapt(final ValueReader<? extends T> valueReader) {
		assert null != valueReader;
		
		return new AbstractValueReader<T>(_type) {
			public <B extends RecordSignatureBuilder<String, Object, ?>> B unifyRequirements(final B builder)
			throws ValueException, IncompatibleFieldException {
				return valueReader.unifyRequirements(builder);
			}
			
			public T read(final Record<String, Object> parameters)
			throws ValueException {
				return valueReader.read(parameters);
			}
			
			public ValueReader<T> compose(final RecordReader<String, Object> reader)
			throws ValueException {
				return adapt(valueReader.compose(reader));
			}
		};
	}
	
	@Override
	public int hashCode() {
		final HashCode result = new HashCode(this);
		result.append(_type);
		result.append(_name);
		result.append(_optional);
		return result.get();
	}
	
	@Override
	public boolean equals(final Object object) {
		if (this == object) {
			return true;
		} else if (null != object && getClass().equals(object.getClass())) {
			final ParameterValueReader<?> reader = (ParameterValueReader<?>) object;
			return _type.equals(reader._type) && _name.equals(reader._name) && _optional == reader._optional;
		} else {
			return false;
		}
	}
	
	@Override
	public void fillDescription(final Description description) {
		description.append("Name", _name);
		super.fillDescription(description);
		description.append("Optional", _optional);
	}
}
