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

import com.trazere.core.lang.HashCode;
import com.trazere.core.lang.ObjectUtils;
import com.trazere.core.text.DescriptionBuilder;
import com.trazere.core.text.DescriptionFormats;
import com.trazere.core.util.Maybe;
import java.util.Collection;

/**
 * The {@link BaseRecord} class provides a skeleton implementation of {@link Record records}.
 * 
 * @param <K> Type of the field keys.
 * @since 2.0
 */
public abstract class BaseRecord<K extends FieldKey<K, ?>>
implements Record<K> {
	// Object.
	
	@Override
	public int hashCode() {
		final HashCode result = new HashCode(Record.class);
		for (final Field<K, ?> field : fields()) {
			result.append(field);
		}
		return result.get();
	}
	
	@Override
	public boolean equals(final Object object) {
		if (this == object) {
			return true;
		} else if (null != object && object instanceof Record) {
			@SuppressWarnings("unchecked")
			final Record<K> record = (Record<K>) object;
			final Collection<? extends Field<K, ?>> fields = fields();
			if (record.size() != fields.size()) {
				return false;
			}
			for (final Field<K, ?> field : fields) {
				final Maybe<?> value = record.get(field.getKey());
				if (value.isNone() || !ObjectUtils.safeEquals(field.getValue(), value.asSome().getValue())) {
					return false;
				}
			}
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public String toString() {
		final DescriptionBuilder description = new DescriptionBuilder(DescriptionFormats.BASIC);
		for (final Field<?, ?> field : fields()) {
			description.append(field.getKey().getLabel(), field.getValue());
		}
		return description.toString();
	}
}
