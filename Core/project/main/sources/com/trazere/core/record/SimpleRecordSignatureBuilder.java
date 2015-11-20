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

import java.util.HashSet;

/**
 * The {@link SimpleRecordSignatureBuilder} class provides a simple implementation of {@link RecordSignatureBuilder record signature builders}.
 * 
 * @param <K> Type of the field keys.
 * @since 2.0
 */
public class SimpleRecordSignatureBuilder<K extends FieldKey<K, ?>>
extends BaseRecordSignatureBuilder<K, RecordSignature<K>> {
	@Override
	@SuppressWarnings("unused")
	public RecordSignature<K> build() {
		return new SimpleRecordSignature<K>(new HashSet<>(_keys.keySet())); // HACK: explicit type arguments to work around a bug of javac
	}
}
