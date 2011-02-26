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
package com.trazere.util.function;

import com.trazere.util.record.RecordException;
import com.trazere.util.record.RecordSignature;
import com.trazere.util.record.SimpleRecordSignatureBuilder;

/**
 * The {@link AbstractParametrable} abstract class provides a skeleton for {@link Parametrable parametrable elements}.
 * 
 * @param <K> Type of the keys of the parameters.
 * @param <V> Type of the values of the parameters.
 */
public abstract class AbstractParametrable<K, V>
implements Parametrable<K, V> {
	public RecordSignature<K, V> getRequirements()
	throws RecordException {
		return unifyRequirements(new SimpleRecordSignatureBuilder<K, V>()).build();
	}
}
