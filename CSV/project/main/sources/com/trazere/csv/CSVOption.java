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
package com.trazere.csv;

/**
 * The {@link CSVOption} enumeration represents options for CSV {@link CSVReader readers} and {@link CSVWriter writers}.
 * 
 * @since 2.0
 */
public enum CSVOption {
	/**
	 * Indicates that the fields should be trimed (heading and trailing white spaces).
	 * 
	 * @since 2.0
	 */
	TRIM_FIELDS,
	
	/**
	 * Indicates that the cardinality of the lines should be checked.
	 * 
	 * @since 2.0
	 */
	CHECK_CARDINALITY,
	
	/**
	 * Indicates that the invalid lines should be ignored.
	 * 
	 * @since 2.0
	 */
	IGNORE_INVALID_LINES,
}
