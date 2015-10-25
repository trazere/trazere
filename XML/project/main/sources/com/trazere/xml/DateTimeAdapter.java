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
package com.trazere.xml;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.GregorianCalendar;
import javax.xml.bind.DatatypeConverter;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * The {@link DateTimeAdapter} class implements JAXB adpaters for dates with time.
 * 
 * @since 2.0
 */
public class DateTimeAdapter
extends XmlAdapter<String, OffsetDateTime> {
	@Override
	public OffsetDateTime unmarshal(final String value) {
		return null != value ? parse(value) : null;
	}
	
	@Override
	public String marshal(final OffsetDateTime value) {
		return null != value ? format(value) : null;
	}
	
	/**
	 * Parses the given date and time.
	 * 
	 * @param representation Representation to parse.
	 * @return The parsed date and time.
	 * @throws IllegalArgumentException When the representation is invalid.
	 * @since 2.0
	 */
	public static OffsetDateTime parse(final String representation)
	throws IllegalArgumentException {
		return OffsetDateTime.ofInstant(DatatypeConverter.parseDate(representation).toInstant(), ZoneId.systemDefault());
	}
	
	/**
	 * Formats the given date and time.
	 * 
	 * @param value Date and time to format.
	 * @return The formatted representation.
	 * @since 2.0
	 */
	public static String format(final OffsetDateTime value) {
		return javax.xml.bind.DatatypeConverter.printDateTime(GregorianCalendar.from(value.toZonedDateTime()));
	}
}
