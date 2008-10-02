package com.trazere.util.csv;

import com.trazere.util.record.AbstractSimpleRecordBuilder;
import com.trazere.util.record.Record;
import com.trazere.util.record.RecordBuilder;
import com.trazere.util.record.RecordException;
import java.util.HashMap;
import java.util.Map;

/**
 * The {@link CSVLineBuilder} class implements builders of {@link CSVLine CSV lines}.
 */
public class CSVLineBuilder
extends AbstractSimpleRecordBuilder<String, String, CSVLine> {
	/**
	 * Instantiate a new empty CSV line builder.
	 */
	public CSVLineBuilder() {
		super();
	}
	
	/**
	 * Instantiate a new CSV line builder populated with the given fields.
	 * 
	 * @param fields Values of the initial fields identified by their keys.
	 */
	protected CSVLineBuilder(final Map<String, String> fields) {
		super(fields);
	}
	
	/**
	 * Instantiate a new CSV line builder populated with the fields of the given record.
	 * 
	 * @param record Record containing the initial fields of the new record builder.
	 * @throws RecordException When the given record cannot be read.
	 */
	public CSVLineBuilder(final Record<String, String> record)
	throws RecordException {
		super(record);
	}
	
	/**
	 * Instantiate a new CSV line builder populated with the fields of the given record builder.
	 * 
	 * @param builder Record builder containing the initial fields of the new record builder.
	 * @throws RecordException When the given record builder cannot populate the new record builder.
	 */
	public CSVLineBuilder(final RecordBuilder<String, String, ?> builder)
	throws RecordException {
		super(builder);
	}
	
	public CSVLine build() {
		return new CSVLine(new HashMap<String, String>(_fields));
	}
}
