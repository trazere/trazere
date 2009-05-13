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

import com.trazere.util.record.IncompatibleFieldException;
import com.trazere.util.record.Record;
import com.trazere.util.record.RecordException;
import com.trazere.util.record.RecordSignature;
import com.trazere.util.record.RecordSignatureBuilder;
import com.trazere.util.record.SimpleRecordBuilder;
import com.trazere.util.record.SimpleRecordSignatureBuilder;

/**
 * The {@link AbstractRecordReader} abstract class implements skeletons of {@link RecordReader record readers}.
 * 
 * @param <K> Type of the keys.
 * @param <V> Type of the values.
 */
public abstract class AbstractRecordReader<K, V>
implements RecordReader<K, V> {
	public RecordSignature<String, Object> getRequirements()
	throws ValueException {
		try {
			return unifyRequirements(new SimpleRecordSignatureBuilder<String, Object>()).build();
		} catch (final IncompatibleFieldException exception) {
			throw new ValueException("Internal error", exception);
		}
	}
	
	public static <B extends RecordSignatureBuilder<String, Object, ?>> B unify(final String key, final Class<? extends Object> type, final B builder)
	throws ValueException, IncompatibleFieldException {
		assert null != builder;
		
		try {
			builder.unify(key, type);
			return builder;
		} catch (final IncompatibleFieldException exception) {
			throw exception;
		} catch (final RecordException exception) {
			throw new ValueException(exception);
		}
	}
	
	public Record<K, V> read(final Record<String, Object> parameters)
	throws ValueException {
		return read(parameters, new SimpleRecordBuilder<K, V>()).build();
	}
	
	public Record<K, V> evaluate(final Record<String, Object> parameters)
	throws ValueException {
		return read(parameters);
	}
}
