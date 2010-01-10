/*
 *  Copyright 2006-2010 Julien Dufour
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

/**
 * The {@link CSVReaderOption} enumeration represents options of {@link CSVReader CSV readers}.
 */
public enum CSVReaderOption {
	/** Option indicating whether the fields should be trimed or not. */
	TRIM_FIELDS,
	
	/** Option indicating whether the empty fields should be striped or not. */
	STRIP_EMPTY_FIELDS,
	
	/** Option indicating whether the cardinality of the data line should be checked or not. */
	CHECK_CARDINALITY,
	
	/** Option indicating whether the invalid CSV lines should be skipped or not. */
	SKIP_INVALID_LINES,
}
