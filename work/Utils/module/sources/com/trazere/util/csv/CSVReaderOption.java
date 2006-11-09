package com.trazere.util.csv;

public enum CSVReaderOption {
	/** Flag indicating wether the fields should be trimed or not. */
	TRIM_FIELDS,
	
	/** Flag indicating wether the empty fields should be striped or not. */
	STRIP_EMPTY_FIELDS,
	
	/** Flag indicating wether the cardinality of the data line should be checked or not. */
	CHECK_CARDINALITY,
	
	/** Flag indicating wether the invalid CSV lines should be skipped or not. */
	SKIP_INVALID_LINES,
}
