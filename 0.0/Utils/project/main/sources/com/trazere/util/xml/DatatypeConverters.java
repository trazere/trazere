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

import java.util.Date;
import java.util.UUID;

/**
 * The {@link DatatypeConverters} class provides various methods to convert XML values when using JAXB.
 */
@Deprecated
public class DatatypeConverters {
	/**
	 * Parses the given date.
	 * 
	 * @param representation The representation of the date.
	 * @return The date.
	 * @throws IllegalArgumentException When the representation is invalid.
	 * @deprecated Use {@link DateAdapter#parse(String)}
	 */
	@Deprecated
	public static Date parseDate(final String representation)
	throws IllegalArgumentException {
		return DateAdapter.parse(representation);
	}
	
	/**
	 * Formats the given date.
	 * 
	 * @param date The date.
	 * @return The representation.
	 * @deprecated Use {@link DateAdapter#print(Date)}
	 */
	@Deprecated
	public static String printDate(final Date date) {
		return DateAdapter.print(date);
	}
	
	/**
	 * Parses the given time.
	 * 
	 * @param representation The representation of the time.
	 * @return The time.
	 * @throws IllegalArgumentException When the representation is invalid.
	 * @deprecated Use {@link TimeAdapter#parse(String)}
	 */
	@Deprecated
	public static Date parseTime(final String representation)
	throws IllegalArgumentException {
		return TimeAdapter.parse(representation);
	}
	
	/**
	 * Formats the given time.
	 * 
	 * @param date The time.
	 * @return The representation.
	 * @deprecated Use {@link TimeAdapter#print(Date)}
	 */
	@Deprecated
	public static String printTime(final Date date) {
		return TimeAdapter.print(date);
	}
	
	/**
	 * Parses the given date and time.
	 * 
	 * @param representation The representation of the time.
	 * @return The date and time.
	 * @throws IllegalArgumentException When the representation is invalid.
	 * @deprecated Use {@link DateTimeAdapter#parse(String)}
	 */
	@Deprecated
	public static Date parseDateTime(final String representation)
	throws IllegalArgumentException {
		return DateTimeAdapter.parse(representation);
	}
	
	/**
	 * Formats the given date and time.
	 * 
	 * @param date The date and time.
	 * @return The representation.
	 * @deprecated Use {@link DateTimeAdapter#print(Date)}
	 */
	@Deprecated
	public static String printDateTime(final Date date) {
		return DateTimeAdapter.print(date);
	}
	
	/**
	 * Parses the given UUID.
	 * 
	 * @param representation The representation of the time.
	 * @return The UUID.
	 * @throws IllegalArgumentException When the representation is invalid.
	 * @deprecated Use {@link UUIDAdapter#parse(String)}
	 */
	@Deprecated
	public static UUID parseUUID(final String representation)
	throws IllegalArgumentException {
		return UUIDAdapter.parse(representation);
	}
	
	/**
	 * Formats the given UUID.
	 * 
	 * @param uuid The UUID.
	 * @return The representation.
	 * @deprecated Use {@link UUIDAdapter#print(UUID)}
	 */
	@Deprecated
	public static String printUUID(final UUID uuid) {
		return UUIDAdapter.print(uuid);
	}
	
	private DatatypeConverters() {
		// Prevents instantiation.
	}
}
