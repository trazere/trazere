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
package com.trazere.util.csv;

import com.trazere.util.collection.Maps;
import com.trazere.util.record.SimpleRecord;
import java.util.Collections;
import java.util.Map;

/**
 * The {@link CSVLine} class represents lines of CSV files.
 * <p>
 * CSV lines are defined as records of the values of the fields identified by the headers of the fields.
 * 
 * @deprecated Use {@link com.trazere.core.record.Record}.
 */
@Deprecated
public class CSVLine
extends SimpleRecord<String, String> {
	/**
	 * Empty line.
	 * 
	 * @deprecated Use {@link com.trazere.core.record.Records#empty()}.
	 */
	@Deprecated
	public static final CSVLine EMPTY = new CSVLine(Collections.<String, String>emptyMap());
	
	/**
	 * Build a new CSV line with the given field.
	 * 
	 * @param header Header of the field.
	 * @param value Value of the field. May be <code>null</code>.
	 * @return The built CSV line.
	 * @deprecated Use {@link com.trazere.core.record.Records#fromKeyAndValue(com.trazere.core.record.FieldKey, Object)}.
	 */
	@Deprecated
	public static CSVLine build(final String header, final String value) {
		assert null != header;
		
		// Build.
		return new CSVLine(Maps.fromBinding(header, value));
	}
	
	/**
	 * Build a new CSV line with the given fields.
	 * 
	 * @param header1 Header of the first field.
	 * @param value1 Value of the first field.
	 * @param header2 Header of the second field.
	 * @param value2 Value of the second field.
	 * @return The built CSV line.
	 * @deprecated Use
	 *             {@link com.trazere.core.record.Records#fromKeysAndValues(com.trazere.core.record.FieldKey, Object, com.trazere.core.record.FieldKey, Object)}
	 *             .
	 */
	@Deprecated
	public static CSVLine build(final String header1, final String value1, final String header2, final String value2) {
		assert null != header1;
		assert null != header2;
		
		// Build.
		return new CSVLine(Maps.fromBindings(header1, value1, header2, value2));
	}
	
	/**
	 * Instantiate a new line with the given fields.
	 * 
	 * @param fields Values of the fields identified by their headers.
	 */
	protected CSVLine(final Map<String, String> fields) {
		super(fields);
	}
}
