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
package com.trazere.util.value;

import com.trazere.util.record.FieldSignature;
import com.trazere.util.record.IncompatibleFieldException;
import com.trazere.util.record.Record;
import com.trazere.util.record.RecordException;
import com.trazere.util.record.RecordSignatureBuilder;

// TODO: signature is incomplete when the parameter is optional !

/**
 * The {@link ParameterValueReader} class read values from the parameters.
 * <p>
 * When the parameter read by this reader is mandatory, it has a single requirement corresponding to the parameter. When the parameter is optional, the reader
 * has no requirements and produce a null value when the parameter is missing.
 * 
 * @param <T> Type of the values.
 */
public class ParameterValueReader<T>
extends AbstractValueReader<T> {
	/** Name of the parameter to read. */
	protected final String _name;
	
	/** Flag indicating wether the parameter is optional or mandatory. */
	protected final boolean _optional;
	
	/**
	 * Instantiate a new mandatory parameter reader with the given parameter name and type.
	 * 
	 * @param name Name of the parameter to read.
	 * @param type Type of the value.
	 */
	public ParameterValueReader(final String name, final Class<T> type) {
		this(name, type, false);
	}
	
	/**
	 * Instanciate a new mandatory parameter reader with the given field signature.
	 * 
	 * @param signature Signature of the parameter.
	 */
	public ParameterValueReader(final FieldSignature<String, T> signature) {
		this(signature.getKey(), signature.getType(), false);
	}
	
	/**
	 * Instantiate a new reader with the given parameter name and type and flag.
	 * 
	 * @param name Name of the parameter to read.
	 * @param type Type of the value.
	 * @param optional Flag indicating wether the parameter is optional or mandatory.
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
	 * Instanciate a new mandatory parameter reader with the given field signature.
	 * 
	 * @param signature Signature of the parameter.
	 * @param optional Flag indicating wether the parameter is optional or mandatory.
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
	
	public T read(final Record<String, Object> parameters)
	throws ValueException {
		try {
			return _optional ? parameters.getTyped(_name, _type, null) : parameters.getTyped(_name, _type);
		} catch (final RecordException exception) {
			throw new ValueException(exception);
		}
	}
	
	public <B extends RecordSignatureBuilder<String, Object, ?>> B unifyRequirements(final B builder)
	throws ValueException, IncompatibleFieldException {
		try {
			if (!_optional) {
				builder.unify(new FieldSignature<String, T>(_name, _type));
			}
			return builder;
		} catch (final IncompatibleFieldException exception) {
			throw exception;
		} catch (final RecordException exception) {
			throw new ValueException(exception);
		}
	}
	
	@Override
	public void fillDescription(final StringBuilder builder) {
		builder.append(" - Name = ").append(_name);
		super.fillDescription(builder);
		builder.append(" - Optional = ").append(_optional);
	}
}
