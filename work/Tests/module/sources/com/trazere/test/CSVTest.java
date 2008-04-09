package com.trazere.test;

import com.trazere.util.collection.CollectionUtils;
import com.trazere.util.csv.CSVLine;
import com.trazere.util.csv.CSVReader;
import com.trazere.util.csv.CSVReaderOption;
import com.trazere.util.csv.CSVWriter;
import com.trazere.util.csv.CSVWriterOption;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

/**
 * DOCME
 */
public class CSVTest {
	public static void main(final String[] args)
	throws IOException {
		// Read.
		final File file = new File(args[0]);
		read(file, EnumSet.of(CSVReaderOption.TRIM_FIELDS));
		read(file, EnumSet.of(CSVReaderOption.TRIM_FIELDS, CSVReaderOption.STRIP_EMPTY_FIELDS));
		final List<CSVLine> lines = read(file, EnumSet.of(CSVReaderOption.TRIM_FIELDS, CSVReaderOption.STRIP_EMPTY_FIELDS, CSVReaderOption.CHECK_CARDINALITY, CSVReaderOption.SKIP_INVALID_LINES));
		
		// Write.
		write(new File(args[1]), CollectionUtils.listN(new String[] {
		    "C1",
		    "C2",
		    "C3",
		    "----"
		}), EnumSet.noneOf(CSVWriterOption.class), lines);
	}
	
	public static List<CSVLine> read(final File file, final EnumSet<CSVReaderOption> options)
	throws IOException {
		System.out.println("Reading file " + file + " with options " + options);
		final List<CSVLine> lines = new ArrayList<CSVLine>();
		final Reader reader = new FileReader(file);
		try {
			final CSVReader csvReader = new CSVReader(reader, ";", options);
			while (csvReader.hasNext()) {
				final CSVLine line = csvReader.next();
				lines.add(line);
				System.out.println(" => " + line);
			}
			System.out.println("Read " + csvReader.getLine() + " lines");
		} finally {
			reader.close();
		}
		return lines;
	}
	
	public static void write(final File file, final List<String> headers, final EnumSet<CSVWriterOption> options, final List<CSVLine> lines)
	throws IOException {
		System.out.println("Writing file " + file + " with options " + options);
		final Writer writer = new FileWriter(file);
		try {
			final CSVWriter csvWriter = new CSVWriter(writer, ";", headers, options);
			csvWriter.writeHeaders();
			for (final CSVLine line : lines) {
				csvWriter.writeLine(line);
			}
		} finally {
			writer.close();
		}
	}
}
