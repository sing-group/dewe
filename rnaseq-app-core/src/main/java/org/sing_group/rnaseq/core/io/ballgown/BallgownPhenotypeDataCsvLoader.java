package org.sing_group.rnaseq.core.io.ballgown;

import static es.uvigo.ei.sing.commons.csv.io.CsvReader.CsvReaderBuilder.newCsvReaderBuilder;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import es.uvigo.ei.sing.commons.csv.entities.CsvData;
import es.uvigo.ei.sing.commons.csv.entities.CsvFormat;

/**
 * A class to load information from the phenotype CSV file generated by the
 * {@code BallgownController#differentialExpression} analysis methods.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class BallgownPhenotypeDataCsvLoader {
	private static final CsvFormat CSV_FORMAT =
		new CsvFormat(",", '.', true, "\n");

	/**
	 * Reads the sample names from the specified {@code phenotypeDataFile}.
	 *
	 * @param phenotypeDataFile the phenotype data file
	 *
	 * @return the list of sample names
	 * @throws IOException if an error occurs when reading the file
	 */
	public static List<String> loadSampleNames(File phenotypeDataFile)
		throws IOException {
		return	extractSampleNames(newCsvReaderBuilder()
					.withFormat(CSV_FORMAT)
					.withHeader()
				.build().read(phenotypeDataFile));
	}

	private static List<String> extractSampleNames(CsvData csvData) {
		return csvData.stream().map(e -> e.get(0)).collect(Collectors.toList());
	}
}
