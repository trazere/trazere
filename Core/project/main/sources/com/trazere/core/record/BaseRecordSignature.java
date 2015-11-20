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
package com.trazere.core.record;

import com.trazere.core.collection.MapExtractors;
import com.trazere.core.functional.Function;
import com.trazere.core.functional.Functions;
import com.trazere.core.lang.HashCode;
import com.trazere.core.lang.IterableUtils;
import com.trazere.core.text.DescriptionBuilder;
import com.trazere.core.text.DescriptionFormats;
import com.trazere.core.util.Maybe;
import com.trazere.core.util.Result;
import com.trazere.core.util.Unit;

/**
 * The {@link BaseRecordSignature} class provides a skeleton implementation of {@link Record record signatures}.
 * 
 * @param <K> Type of the field keys.
 * @see FieldKey
 * @since 2.0
 */
public abstract class BaseRecordSignature<K extends FieldKey<K, ?>>
implements RecordSignature<K> {
	// Signature.
	
	@Override
	public boolean isAssignableFrom(final RecordSignature<?> signature) {
		final Function<FieldKey<?, ?>, Maybe<FieldKey<?, ?>>> signatureKeys = MapExtractors.fromKeys(signature.keys(), Functions.identity());
		//		return IterableUtils.areAll(keys(), key -> signatureKeys.evaluate(key).filter(key::isAssignableFrom).isSome());
		return IterableUtils.areAll(keys(), (final FieldKey<? extends K, ?> key) -> {
			final Maybe<FieldKey<?, ?>> signatureKey = signatureKeys.evaluate(key);
			return signatureKey.isSome() && key.isAssignableFrom(signatureKey.asSome().getValue());
		});
	}
	
	@Override
	public Result<Unit> checkAssignableFrom(final RecordSignature<?> signature) {
		final Function<FieldKey<?, ?>, Maybe<FieldKey<?, ?>>> signatureKeys = MapExtractors.fromKeys(signature.keys(), Functions.identity());
		//		final Iterable<Result<Unit>> keyChecks = IterableUtils.map(keys(), (final FieldKey<? extends K, ?> key) -> {
		//			return signatureKeys.evaluate(key) // Get the signature key
		//			.map(key::checkAssignableFrom) // Check that it is compatible
		//			.get(Result.failure(new MissingFieldException("Missing key \"" + key + "\""))); // Handle missing key
		//		});
		//		return IterableUtils.first(keyChecks, Result::isFailure).get(CHECK_SUCCESS);
		for (final FieldKey<? extends K, ?> key : keys()) {
			final Maybe<FieldKey<?, ?>> signatureKey = signatureKeys.evaluate(key);
			if (signatureKey.isNone()) {
				return Result.failure(new MissingFieldException("Missing key \"" + key + "\""));
			} else {
				final Result<Unit> keyResult = key.checkAssignableFrom(signatureKey.asSome().getValue());
				if (keyResult.isFailure()) {
					return keyResult;
				}
			}
		}
		return CHECK_SUCCESS;
	}
	
	@Override
	public Result<Unit> checkValues(final Record<K> record) {
		for (final FieldKey<? extends K, ?> key : keys()) {
			final Maybe<?> value = record.get(key);
			if (value.isNone()) {
				return Result.failure(new MissingFieldException("Missing field \"" + key + "\""));
			} else {
				final Result<?> valueResult = key.checkValue(value.asSome().getValue());
				if (valueResult.isFailure()) {
					return Result.failure(new RecordException("Incompatible field \"" + key + "\"", valueResult.asFailure().getCause()));
				}
			}
		}
		return CHECK_SUCCESS;
	}
	
	private static final Result<Unit> CHECK_SUCCESS = Result.success(Unit.UNIT);
	
	// Object.
	
	@Override
	public int hashCode() {
		final HashCode result = new HashCode(Record.class);
		result.appendAll(keys());
		return result.get();
	}
	
	@Override
	public boolean equals(final Object object) {
		if (this == object) {
			return true;
		} else if (null != object && object instanceof Record) {
			final RecordSignature<?> signature = (RecordSignature<?>) object;
			return keys().equals(signature.keys());
		} else {
			return false;
		}
	}
	
	@Override
	public String toString() {
		final DescriptionBuilder description = new DescriptionBuilder(DescriptionFormats.BASIC);
		for (final FieldKey<? extends K, ?> key : keys()) {
			description.append(key.toString());
		}
		return description.toString();
	}
}
