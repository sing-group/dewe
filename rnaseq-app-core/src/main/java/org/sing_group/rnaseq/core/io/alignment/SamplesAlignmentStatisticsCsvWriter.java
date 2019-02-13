/*
 * #%L
 * DEWE Core
 * %%
 * Copyright (C) 2016 - 2019 Hugo López-Fernández, Aitor Blanco-García, Florentino Fdez-Riverola, 
 * 			Borja Sánchez, and Anália Lourenço
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */
package org.sing_group.rnaseq.core.io.alignment;

import static es.uvigo.ei.sing.commons.csv.entities.CsvData.CsvDataBuilder.newCsvDataBuilder;
import static java.util.Arrays.asList;
import static org.sing_group.rnaseq.api.entities.alignment.AlignmentStatistics.MULTIMAPPED_READS;
import static org.sing_group.rnaseq.api.entities.alignment.AlignmentStatistics.OVERALL_ALIGNMENT_RATE;
import static org.sing_group.rnaseq.api.entities.alignment.AlignmentStatistics.UNIQUELY_ALIGNED_READS;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.sing_group.rnaseq.api.entities.alignment.AlignmentStatistics;
import org.sing_group.rnaseq.api.entities.alignment.SampleAlignmentStatistics;

import es.uvigo.ei.sing.commons.csv.entities.CsvData;
import es.uvigo.ei.sing.commons.csv.entities.CsvEntry;
import es.uvigo.ei.sing.commons.csv.entities.CsvFormat;
import es.uvigo.ei.sing.commons.csv.io.CsvWriter;

/**
 * A class to write {@code SampleAlignmentStatistics} into CSV files.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class SamplesAlignmentStatisticsCsvWriter {
	public static final CsvFormat CSV_FORMAT =
		new CsvFormat(",", '.', false, "\n");
	private static final CsvEntry HEADER = new CsvEntry(asList("Sample",
		UNIQUELY_ALIGNED_READS, MULTIMAPPED_READS, OVERALL_ALIGNMENT_RATE));

	/**
	 * Writes the given list of {@code SampleAlignmentStatistics} as CSV into
	 * {@code file}.
	 * 
	 * @param samples the list of {@code SampleAlignmentStatistics} to write
	 * @param file the output file
	 * @throws IOException if an error occurs writing the file
	 */
	public static void write(List<SampleAlignmentStatistics> samples, File file)
		throws IOException {
		CsvWriter csvWriter = CsvWriter.of(CSV_FORMAT);
		CsvData data = createData(samples, CSV_FORMAT);
		csvWriter.write(data, file);
	}
	
	private static CsvData createData(List<SampleAlignmentStatistics> samples,
		CsvFormat csvFormat
	) {
		List<CsvEntry> entries = new ArrayList<>();
		samples.stream().map(s -> toEntry(s, csvFormat)).forEach(entries::add);

		return newCsvDataBuilder(csvFormat)
					.withHeader(HEADER)
					.withEntries(entries)
				.build();
	}
	
	private static CsvEntry toEntry(SampleAlignmentStatistics sample,
		CsvFormat csvFormat
	) {
		List<String> sampleValues = new LinkedList<>();
		sampleValues.add(sample.getSampleName());
		AlignmentStatistics statistics = sample.getStatistics();
		sampleValues
			.add(doubleValue(statistics.getUniquelyAlignedReads(), csvFormat));
		sampleValues
			.add(doubleValue(statistics.getMultimappedReads(), csvFormat));
		sampleValues
			.add(doubleValue(statistics.getOverallAlignmentRate(), csvFormat));

		return new CsvEntry(sampleValues);
	}

	private static String doubleValue(Double value, CsvFormat csvFormat) {
		if (value == null || Double.isNaN(value)) {
			return "";
		} else {
			return csvFormat.getDecimalFormatter().format(value);
		}
	}
}
