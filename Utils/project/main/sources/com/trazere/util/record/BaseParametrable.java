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
package com.trazere.util.record;

import com.trazere.util.lang.InternalException;

/**
 * The {@link BaseParametrable} abstract class provides a skeleton implementation of {@link Parametrable parametrables}.
 * 
 * @param <K> Type of the keys of the parameters.
 * @param <V> Type of the values of the parameters.
 * @deprecated Use {@link com.trazere.core.record.Parametrable}.
 */
@Deprecated
public abstract class BaseParametrable<K, V>
implements Parametrable<K, V> {
	@Override
	public RecordSignature<K, V> getRequirements() {
		try {
			return unifyRequirements(new SimpleRecordSignatureBuilder<K, V>()).build();
		} catch (final IncompatibleFieldException exception) {
			throw new InternalException(exception);
		}
	}
}
