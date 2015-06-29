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

import com.trazere.core.util.Serializer;
import com.trazere.util.function.Function1;
import com.trazere.util.lang.TypeCheckException;
import com.trazere.util.lang.WrapException;
import com.trazere.util.record.IncompatibleFieldException;
import com.trazere.util.record.InvalidFieldException;
import com.trazere.util.record.Record;
import com.trazere.util.record.RecordException;
import com.trazere.util.record.RecordSignatureBuilder;

/**
 * The {@link ValueUtils} class provides various helpers regarding values.
 * 
 * @deprecated Use {@link com.trazere.core}.
 */
@Deprecated
public class ValueUtils {
	/**
	 * Check the type of the values of the given value reader.
	 * 
	 * @param <T> Type of the values.
	 * @param reader The value reader.
	 * @param valueClass The excepted type of values.
	 * @return The typed value reader.
	 * @throws TypeCheckException When the value type of the value reader is not compatible with the excepted type.
	 * @deprecated To be removed.
	 */
	@Deprecated
	@SuppressWarnings("unchecked")
	public static <T> ValueReader<? extends T> typeCheckUpperBound(final ValueReader<?> reader, final Class<T> valueClass)
	throws TypeCheckException {
		assert null != reader;
		assert null != valueClass;
		
		if (!valueClass.isAssignableFrom(reader.getValueClass())) {
			throw new TypeCheckException("Incompatible value type " + reader.getValueClass().getName() + " with type " + valueClass.getName());
		} else {
			return (ValueReader<? extends T>) reader;
		}
	}
	
	/**
	 * Check the type of the values of the given value serializer.
	 * 
	 * @param <T> Type of the values.
	 * @param <R> Type of the representations.
	 * @param <X> Type of the exceptions.
	 * @param serializer The value serializer.
	 * @param valueClass The excepted type of values.
	 * @return The typed value serializer.
	 * @throws TypeCheckException When the value type of the value serializer is not compatible with the excepted type.
	 * @deprecated To be removed.
	 */
	@Deprecated
	@SuppressWarnings("unchecked")
	public static <T, R, X extends Exception> ValueSerializer<T, R, X> typeCheckValue(final ValueSerializer<?, R, X> serializer, final Class<T> valueClass)
	throws TypeCheckException {
		assert null != serializer;
		assert null != valueClass;
		
		if (!serializer.getValueClass().equals(valueClass)) {
			throw new TypeCheckException("Incompatible value type " + serializer.getValueClass().getName() + " with type " + valueClass.getName());
		} else {
			return (ValueSerializer<T, R, X>) serializer;
		}
	}
	
	/**
	 * Map the values produced by the given value reader according to the given function.
	 * 
	 * @param <T> Type of the values.
	 * @param <R> Type of the mapped values.
	 * @param type The type of the mapped values.
	 * @param nullable Flag indicating wether the mapped values can be <code>null</code>.
	 * @param function The mapping function.
	 * @param valueReader The value reader.
	 * @return The build value reader.
	 * @deprecated To be removed.
	 */
	@Deprecated
	public static <T, R> ValueReader<R> map(final Class<R> type, final boolean nullable, final Function1<? super T, ? extends R, ? extends ValueException> function, final ValueReader<T> valueReader) {
		assert null != valueReader;
		assert null != function;
		
		return new BaseValueReader<R>(type, nullable) {
			@Override
			public <B extends RecordSignatureBuilder<String, Object, ?>> B unifyRequirements(final B builder)
			throws IncompatibleFieldException {
				return valueReader.unifyRequirements(builder);
			}
			
			@Override
			public R read(final Record<String, Object> parameters)
			throws ValueException {
				return function.evaluate(valueReader.read(parameters));
			}
			
			@Override
			public ValueReader<R> compose(final RecordReader<String, Object> reader)
			throws RecordException {
				final ValueReader<T> valueReader_ = valueReader.compose(reader);
				return map(type, nullable, function, valueReader_);
			}
		};
	}
	
	/**
	 * Reduce the given value reader using the given parameters if possible.
	 * 
	 * @param <T> Type of the value.
	 * @param reader The value reader.
	 * @param parameters The parameters.
	 * @return A reduced value reader if the requirements were met, or the given value reader.
	 * @throws InvalidFieldException When some parameter cannot be read.
	 * @throws ValueException When the value cannot be read.
	 * @deprecated To be removed.
	 */
	@Deprecated
	public static <T> ValueReader<T> reduce(final ValueReader<T> reader, final Record<String, Object> parameters)
	throws InvalidFieldException, ValueException {
		assert null != reader;
		
		if (reader.getRequirements().accepts(parameters)) {
			return ConstantValueReader.build(reader.read(parameters), reader.getValueClass());
		} else {
			return reader;
		}
	}
	
	/**
	 * Adpats the given value serializer to a serializer.
	 * 
	 * @param <T> Type of the values.
	 * @param <R> Type of the representations.
	 * @param serializer Value serializer to adapt.
	 * @return The adapter serializer.
	 * @deprecated Use {@link Serializer}.
	 */
	@Deprecated
	public static <T, R> Serializer<T, R> toSerializer(final ValueSerializer<T, R, ?> serializer) {
		assert null != serializer;
		
		return new Serializer<T, R>() {
			@Override
			public R serialize(final T value) {
				try {
					return serializer.serialize(value);
				} catch (final Exception exception) {
					throw new WrapException(exception);
				}
			}
			
			@Override
			public T deserialize(final R representation) {
				try {
					return serializer.deserialize(representation);
				} catch (final Exception exception) {
					throw new WrapException(exception);
				}
			}
		};
	}
	
	/**
	 * Adapts the given serializer to a value serializer.
	 * 
	 * @param <V> Type of the values.
	 * @param <R> Type of the representations.
	 * @param serializer Serializer to adapt.
	 * @param valueClass Type of the values.
	 * @param representationClass Type of the representations.
	 * @return The adapted value serializer.
	 * @deprecated Use {@link Serializer}.
	 */
	@Deprecated
	public static <V, R> ValueSerializer<V, R, RuntimeException> fromSerializer(final Serializer<V, R> serializer, final Class<V> valueClass, final Class<R> representationClass) {
		assert null != serializer;
		
		return new BaseValueSerializer<V, R, RuntimeException>(valueClass, representationClass) {
			@Override
			public R serialize(final V value) {
				return serializer.serialize(value);
			}
			
			@Override
			public V deserialize(final R representation) {
				return serializer.deserialize(representation);
			}
		};
	}
	
	private ValueUtils() {
		// Prevents instantiation.
	}
}
