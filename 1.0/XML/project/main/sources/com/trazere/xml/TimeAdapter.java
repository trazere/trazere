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
package com.trazere.xml;

import java.time.LocalDate;
import java.time.OffsetTime;
import java.time.ZoneId;
import java.util.GregorianCalendar;
import javax.xml.bind.DatatypeConverter;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * The {@link TimeAdapter} class implements JAXB adpaters for times.
 */
public class TimeAdapter
extends XmlAdapter<String, OffsetTime> {
	@Override
	public OffsetTime unmarshal(final String value) {
		return null != value ? parse(value) : null;
	}
	
	@Override
	public String marshal(final OffsetTime value) {
		return null != value ? format(value) : null;
	}
	
	/**
	 * Parses the given time.
	 * 
	 * @param representation Representation to parse.
	 * @return The parsed time.
	 * @throws IllegalArgumentException When the representation is invalid.
	 */
	public static OffsetTime parse(final String representation)
	throws IllegalArgumentException {
		return OffsetTime.ofInstant(DatatypeConverter.parseDate(representation).toInstant(), ZoneId.systemDefault());
	}
	
	/**
	 * Formats the given time.
	 * 
	 * @param value Time to format.
	 * @return The formatted representation.
	 */
	public static String format(final OffsetTime value) {
		return DatatypeConverter.printDate(GregorianCalendar.from(value.atDate(LocalDate.now()).toZonedDateTime()));
	}
}
