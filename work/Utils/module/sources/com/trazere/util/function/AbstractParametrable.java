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
package com.trazere.util.function;

import com.trazere.util.lang.ThrowableFactory;
import com.trazere.util.record.FieldSignature;
import com.trazere.util.record.IncompatibleFieldException;
import com.trazere.util.record.RecordException;
import com.trazere.util.record.RecordSignature;
import com.trazere.util.record.RecordSignatureBuilder;
import com.trazere.util.record.SimpleRecordSignatureBuilder;

/**
 * The {@link AbstractParametrable} abstract class provides a skeleton for {@link Parametrable parametrable elements}.
 * 
 * @param <K> Type of the keys of the parameters.
 * @param <V> Type of the values of the parameters.
 * @param <X> Type of the exceptions.
 */
public abstract class AbstractParametrable<K, V, X extends Exception>
implements Parametrable<K, V, X> {
	public RecordSignature<K, V> getRequirements()
	throws X {
		try {
			return unifyRequirements(new SimpleRecordSignatureBuilder<K, V>()).build();
		} catch (final IncompatibleFieldException exception) {
			throw getThrowableFactory().build(exception);
		}
	}
	
	/**
	 * Unify the signature of the field corresponding to the given key and type within the given record signature builder.
	 * <p>
	 * The unified field must either not be signed or be signed with a compatible type in the given record signature builder.
	 * 
	 * @param key The key of the field.
	 * @param type The type of the values.
	 * @param nullable The flag indicating whether the values can be <code>null</code> or not.
	 * @param builder The signature builder.
	 * @throws IncompatibleFieldException When the given and current signature of the field are not compatible.
	 * @throws X When the field signature cannot be unified.
	 */
	public void unify(final String key, final Class<? extends Object> type, final boolean nullable, final RecordSignatureBuilder<String, Object, ?> builder)
	throws X, IncompatibleFieldException {
		assert null != builder;
		
		try {
			builder.unify(key, type, nullable);
		} catch (final IncompatibleFieldException exception) {
			throw exception;
		} catch (final RecordException exception) {
			throw getThrowableFactory().build(exception);
		}
	}
	
	/**
	 * Unify the given field signature within the given record signature builder.
	 * <p>
	 * The unified field must either not be signed or be signed with a compatible type in the given record signature builder.
	 * 
	 * @param field Field signature to unify.
	 * @param builder The signature builder.
	 * @throws IncompatibleFieldException When the given and current signature of the field are not compatible.
	 * @throws X When the field signature cannot be unified.
	 */
	public void unify(final FieldSignature<String, ? extends Object> field, final RecordSignatureBuilder<String, Object, ?> builder)
	throws X, IncompatibleFieldException {
		assert null != builder;
		
		try {
			builder.unify(field);
		} catch (final IncompatibleFieldException exception) {
			throw exception;
		} catch (final RecordException exception) {
			throw getThrowableFactory().build(exception);
		}
	}
	
	/**
	 * Unify the fields of the given signature record within the given record signature builder.
	 * <p>
	 * The unified fields must either not be signed or be signed with a compatible type in the given record signature builder.
	 * 
	 * @param record The record signature.
	 * @param builder The signature builder.
	 * @throws IncompatibleFieldException When the given and current signature of the field are not compatible.
	 * @throws X When the field signature cannot be unified.
	 */
	public void unifyAll(final RecordSignature<String, ? extends Object> record, final RecordSignatureBuilder<String, Object, ?> builder)
	throws X, IncompatibleFieldException {
		assert null != builder;
		
		try {
			builder.unifyAll(record);
		} catch (final IncompatibleFieldException exception) {
			throw exception;
		} catch (final RecordException exception) {
			throw getThrowableFactory().build(exception);
		}
	}
	
	/**
	 * Get the exception factory to use.
	 * 
	 * @return The exception factory.
	 */
	protected abstract ThrowableFactory<X> getThrowableFactory();
}
