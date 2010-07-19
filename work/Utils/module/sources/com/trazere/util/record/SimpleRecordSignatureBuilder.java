/*
 *  Copyright 2006-2010 Julien Dufour
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
package com.trazere.util.record;

import java.util.HashMap;
import java.util.Map;

/**
 * The {@link SimpleRecordSignatureBuilder} class implements builders of {@link SimpleRecordSignature simple record signatures}.
 * 
 * @param <K> Type of the keys.
 * @param <V> Type of the values.
 * @see SimpleRecordSignature
 */
public class SimpleRecordSignatureBuilder<K, V>
extends AbstractSimpleRecordSignatureBuilder<K, V, SimpleRecordSignature<K, V>> {
	/**
	 * Instantiate a new empty builder.
	 */
	public SimpleRecordSignatureBuilder() {
		super();
	}
	
	/**
	 * Instantiate a new builder populated with the given field signatures.
	 * 
	 * @param fields Initial field signatures identified by their keys.
	 */
	public SimpleRecordSignatureBuilder(final Map<K, ? extends FieldSignature<K, ? extends V>> fields) {
		super(fields);
	}
	
	/**
	 * Instantiate a new builder populated with the field signatures of the given record signature.
	 * 
	 * @param signature Record signature containing the initial field signatures.
	 * @throws RecordException When the record signature cannot be read.
	 */
	public SimpleRecordSignatureBuilder(final RecordSignature<K, ? extends V> signature)
	throws RecordException {
		super(signature);
	}
	
	public SimpleRecordSignature<K, V> build() {
		return new SimpleRecordSignature<K, V>(new HashMap<K, FieldSignature<K, ? extends V>>(_fields));
	}
}