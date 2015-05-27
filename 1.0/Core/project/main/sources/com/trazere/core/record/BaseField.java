/*
 *  Copyright 2006-2013 Julien Dufour
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

import com.trazere.core.lang.HashCode;
import com.trazere.core.lang.ObjectUtils;

/**
 * The {@link BaseField} class provides a skeleton implementation of {@link Field fields}.
 * 
 * @param <K> Type of the key.
 * @param <V> Type of the value.
 */
public abstract class BaseField<K extends FieldKey<? extends K, ?>, V>
implements Field<K, V> {
	// Object.
	
	@Override
	public int hashCode() {
		final HashCode result = new HashCode(Field.class);
		result.append(getKey());
		result.append(getValue());
		return result.get();
	}
	
	@Override
	public boolean equals(final Object object) {
		if (this == object) {
			return true;
		} else if (null != object && object instanceof Field<?, ?>) {
			final Field<?, ?> field = (Field<?, ?>) object;
			return getKey() == field.getKey() && ObjectUtils.safeEquals(getValue(), field.getValue());
		} else {
			return false;
		}
	}
	
	@Override
	public String toString() {
		return getKey().getLabel() + " : " + getValue();
	}
}
