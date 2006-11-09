package com.trazere.util.task;

import java.util.Arrays;
import java.util.List;

/**
 * The <code>TaskCSVReportStoreHeaders</code> class provides the headers of the CSV files storing the task report entries.
 */
public class TaskCSVReportStoreHeaders {
	public static final String NAME = "Name";
	public static final String STATUS = "Status";

	public static final List<String> ENTRY_HEADERS = Arrays.asList(new String[] {
	    NAME,
	    STATUS,
	});

	private TaskCSVReportStoreHeaders() {
		// Prevent instanciation.
	}
}
