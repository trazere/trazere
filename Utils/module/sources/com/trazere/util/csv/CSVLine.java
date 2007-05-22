/*
 *  Copyright 2006 Julien Dufour
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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.trazere.util.Assert;
import com.trazere.util.text.Descriptable;
import com.trazere.util.text.TextUtils;

/**
 * The <code>CSVLine</code> class represents lines from CSV files.
 * <p>
 * CSV lines are defined as a set of values associated to the field headers.
 */
public class CSVLine
implements Descriptable {
	/**
	 * The <code>Builder</code> class provides a tool to build of CSV lines.
	 */
	public static class Builder {
		/** Values of the CSV fields identified by their headers. */
		protected final Map<String, String> _fields;

		/**
		 * Instantiate a new builder.
		 */
		public Builder() {
			_fields = new HashMap<String, String>();
		}

		/**
		 * Instantiate a new builder and initialize it with the fields of the given line.
		 * 
		 * @param line CSV line initially populating the builder.
		 */
		public Builder(final CSVLine line) {
			Assert.notNull(line);

			// Initialization.
			_fields = new HashMap<String, String>(line.getFields());
		}

		/**
		 * Instantiate a new builder and initialize it with the given fields.
		 * 
		 * @param fields CSV fields initially populating the builder.
		 */
		public Builder(final Map<String, String> fields) {
			Assert.notNull(fields);

			// Initialization.
			_fields = new HashMap<String, String>(fields);
		}

		/**
		 * Get the value of the field with the given header from the receiver builder.
		 * 
		 * @param header Header of the field to get.
		 * @return The value of the field, or <code>null</code> if no field with the given name exists.
		 */
		public String getField(final String header) {
			Assert.notNull(header);

			// Get.
			return _fields.get(header);
		}

		/**
		 * Set the value of the field with the given header in the receiver builder.
		 * 
		 * @param header Header of the field to set.
		 * @param value Value of the field.
		 */
		public void setField(final String header, final String value) {
			Assert.notNull(header);
			Assert.notNull(value);

			// Set.
			_fields.put(header, value);
		}

		/**
		 * Set the value of the given fields in the receiver builder.
		 * 
		 * @param fields Values of the CSV fields to set identified by their headers.
		 */
		public void setFields(final Map<String, String> fields) {
			Assert.notNull(fields);

			// Set.
			_fields.putAll(fields);
		}

		/**
		 * Remove the field with the given header from the receiver builder.
		 * 
		 * @param header Header of the field to remove.
		 */
		public void removeField(final String header) {
			Assert.notNull(header);

			// Remove.
			_fields.remove(header);
		}

		/**
		 * Build a new CSV line with the fields of the receiver builder.
		 * 
		 * @return The built CSV line.
		 */
		public CSVLine build() {
			return new CSVLine(new HashMap<String, String>(_fields));
		}
	}

	/**
	 * Empty line.
	 */
	@SuppressWarnings("unchecked")
	public static final CSVLine EMPTY = new CSVLine(Collections.EMPTY_MAP);

	/**
	 * Build a new CSV line with the given field.
	 * <p>
	 * <code>null</code> values are ignored.
	 * 
	 * @param header Header of the field.
	 * @param value Value of the field.
	 * @return The built CSV line.
	 */
	public static CSVLine build(final String header, final String value) {
		Assert.notNull(header);

		// Build.
		final Map<String, String> fields = new HashMap<String, String>();
		if (null != value) {
			fields.put(header, value);
		}
		return new CSVLine(fields);
	}

	/**
	 * Build a new CSV line with the given fields.
	 * <p>
	 * <code>null</code> values are ignored.
	 * 
	 * @param header1 Header of the first field.
	 * @param value1 Value of the first field.
	 * @param header2 Header of the second field.
	 * @param value2 Value of the second field.
	 * @return The built CSV line.
	 */
	public static CSVLine build(final String header1, final String value1, final String header2, final String value2) {
		Assert.notNull(header1);
		Assert.notNull(header2);

		// Build.
		final Map<String, String> fields = new HashMap<String, String>();
		if (null != value1) {
			fields.put(header1, value1);
		}
		if (null != value2) {
			fields.put(header2, value2);
		}
		return new CSVLine(fields);
	}

	/** Values of the fields identified by their header. */
	protected final Map<String, String> _fields;

	/**
	 * Instantiate a new line with the given fields.
	 * 
	 * @param fields Values of the fields identified by their header.
	 */
	protected CSVLine(final Map<String, String> fields) {
		_fields = Collections.unmodifiableMap(fields);
	}

	/**
	 * Test wether the receiver line contains a value for the given header.
	 * 
	 * @param header Header of the field to test.
	 * @return <code>true</code> if the lien contains a value for the field, <code>false</code> otherwise.
	 */
	public boolean containsField(final String header) {
		Assert.notNull(header);

		// Test.
		return _fields.containsKey(header);
	}

	/**
	 * Get the value of the field with the given header from the receiver line.
	 * 
	 * @param header Header of the field to get.
	 * @return The value of the field, or <code>null</code> if no field with the given name exists.
	 */
	public String getField(final String header) {
		Assert.notNull(header);

		// Get.
		return _fields.get(header);
	}

	/**
	 * Get the fields of the receicer line.
	 * 
	 * @return An unmodificable map of the values of the fields identified by their headers.
	 */
	public Map<String, String> getFields() {
		return _fields;
	}

	@Override
	public int hashCode() {
		return _fields.hashCode();
	}

	@Override
	public boolean equals(final Object object) {
		if (this == object) {
			return true;
		} else if (null != object && getClass().equals(object.getClass())) {
			final CSVLine line = (CSVLine) object;
			return _fields.equals(line._fields);
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		return TextUtils.computeDescription(this);
	}

	public void fillDescription(final StringBuilder builder) {
		for (final Map.Entry<String, String> entry : _fields.entrySet()) {
			builder.append(" - ").append(entry.getKey()).append(" = ").append(entry.getValue());
		}
	}
}
