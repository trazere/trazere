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
package com.trazere.core.util;

import com.trazere.core.functional.Function;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;

/**
 * The {@link CalendarFunctions} class provides various factories of {@link Function functions} related {@link Calendar calendars}.
 * 
 * @see Function
 * @see Calendar
 * @since 1.0
 */
public class CalendarFunctions {
	/**
	 * Builds a function that gets the time of the argument calendars.
	 *
	 * @return The built function.
	 * @since 1.0
	 */
	public static Function<Calendar, Date> getTime() {
		return GET_TIME;
	}
	
	private static final Function<Calendar, Date> GET_TIME = Calendar::getTime;
	
	/**
	 * Builds a function that gets the instant of the argument calendars.
	 *
	 * @return The built function.
	 * @since 1.0
	 */
	public static Function<Calendar, Instant> toInstant() {
		return TO_INSTANT;
	}
	
	private static final Function<Calendar, Instant> TO_INSTANT = Calendar::toInstant;
	
	private CalendarFunctions() {
		// Prevent instantiation.
	}
}
