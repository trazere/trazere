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
package com.trazere.util.xml;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.UUID;

/**
 * The {@link DatatypeConverters} class provides various methods to convert XML values when using JAXB.
 */
public class DatatypeConverters {
	/**
	 * Parses the given date.
	 * 
	 * @param representation The representation of the date.
	 * @return The date.
	 * @throws IllegalArgumentException When the representation is invalid.
	 */
	public static Date parseDate(final String representation)
	throws IllegalArgumentException {
		assert null != representation;
		
		return javax.xml.bind.DatatypeConverter.parseDate(representation).getTime();
	}
	
	/**
	 * Formats the given date.
	 * 
	 * @param date The date.
	 * @return The representation.
	 */
	public static String printDate(final Date date) {
		assert null != date;
		
		final Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		return javax.xml.bind.DatatypeConverter.printDate(cal);
	}
	
	/**
	 * Parses the given time.
	 * 
	 * @param representation The representation of the time.
	 * @return The time.
	 * @throws IllegalArgumentException When the representation is invalid.
	 */
	public static Date parseTime(final String representation)
	throws IllegalArgumentException {
		assert null != representation;
		
		return javax.xml.bind.DatatypeConverter.parseTime(representation).getTime();
	}
	
	/**
	 * Formats the given time.
	 * 
	 * @param date The time.
	 * @return The representation.
	 */
	public static String printTime(final Date date) {
		assert null != date;
		
		final Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		return javax.xml.bind.DatatypeConverter.printTime(cal);
	}
	
	/**
	 * Parses the given date and time.
	 * 
	 * @param representation The representation of the time.
	 * @return The date and time.
	 * @throws IllegalArgumentException When the representation is invalid.
	 */
	public static Date parseDateTime(final String representation)
	throws IllegalArgumentException {
		assert null != representation;
		
		return javax.xml.bind.DatatypeConverter.parseDateTime(representation).getTime();
	}
	
	/**
	 * Formats the given date and time.
	 * 
	 * @param date The date and time.
	 * @return The representation.
	 */
	public static String printDateTime(final Date date) {
		assert null != date;
		
		final Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		return javax.xml.bind.DatatypeConverter.printDateTime(cal);
	}
	
	/**
	 * Parses the given UUID.
	 * 
	 * @param representation The representation of the time.
	 * @return The UUID.
	 * @throws IllegalArgumentException When the representation is invalid.
	 */
	public static UUID parseUUID(final String representation)
	throws IllegalArgumentException {
		assert null != representation;
		
		return UUID.fromString(representation);
	}
	
	/**
	 * Formats the given UUID.
	 * 
	 * @param uuid The UUID.
	 * @return The representation.
	 */
	public static String printUUID(final UUID uuid) {
		assert null != uuid;
		
		return uuid.toString();
	}
	
	private DatatypeConverters() {
		// Prevents instantiation.
	}
}
