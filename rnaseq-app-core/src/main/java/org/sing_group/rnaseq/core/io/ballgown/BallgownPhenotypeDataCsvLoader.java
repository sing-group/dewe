/*
 * #%L
 * DEWE Core
 * %%
 * Copyright (C) 2016 - 2017 Hugo López-Fernández, Aitor Blanco-García, Florentino Fdez-Riverola, 
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
