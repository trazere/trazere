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

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.xml.bind.DatatypeConverter;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * The {@link DateTimeAdapter} class implements JAXB adpaters for dates with time.
 */
public class DateTimeAdapter
extends XmlAdapter<String, Date> {
	@Override
	public Date unmarshal(final String value) {
		return null != value ? parse(value) : null;
	}
	
	@Override
	public String marshal(final Date value) {
		return null != value ? format(value) : null;
	}
	
	/**
	 * Parses the given date and time.
	 * 
	 * @param representation Representation to parse.
	 * @return The parsed date and time.
	 * @throws IllegalArgumentException When the representation is invalid.
	 */
	public static Date parse(final String representation)
	throws IllegalArgumentException {
		return DatatypeConverter.parseDateTime(representation).getTime();
	}
	
	/**
	 * Formats the given date and time.
	 * 
	 * @param value Date and time to format.
	 * @return The formatted representation.
	 */
	public static String format(final Date value) {
		final Calendar cal = new GregorianCalendar();
		cal.setTime(value);
		return javax.xml.bind.DatatypeConverter.printDateTime(cal);
	}
}
