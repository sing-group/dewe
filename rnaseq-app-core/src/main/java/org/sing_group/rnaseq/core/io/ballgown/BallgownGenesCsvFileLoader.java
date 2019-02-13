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
package org.sing_group.rnaseq.core.io.ballgown;

import static es.uvigo.ei.sing.commons.csv.io.CsvReader.CsvReaderBuilder.newCsvReaderBuilder;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

import org.sing_group.rnaseq.api.entities.ballgown.BallgownGene;
import org.sing_group.rnaseq.api.entities.ballgown.BallgownGenes;
import org.sing_group.rnaseq.core.entities.ballgown.DefaultBallgownGene;
import org.sing_group.rnaseq.core.entities.ballgown.DefaultBallgownGenes;

import es.uvigo.ei.sing.commons.csv.entities.CsvData;
import es.uvigo.ei.sing.commons.csv.entities.CsvEntry;
import es.uvigo.ei.sing.commons.csv.entities.CsvFormat;

/**
 * A class to load a genes CSV file into a {@code BallgownGenes} list.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class BallgownGenesCsvFileLoader {
	private static final CsvFormat CSV_FORMAT =
		new CsvFormat("\t", '.', true, "\n");

	/**
	 * Reads the specified genes CSV file and returns a {@code BallgownGenes}
	 * list.
	 *
	 * @param genesFile the genes CSV file
	 *
	 * @return the {@code BallgownGenes} list
	 * @throws IOException if an error occurs when reading the file
	 */
	public static BallgownGenes loadFile(File genesFile) throws IOException {
		return	parseGenes(newCsvReaderBuilder()
					.withFormat(CSV_FORMAT)
					.withHeader()
				.build().read(genesFile));
	}

	private static BallgownGenes parseGenes(CsvData csvGenesData)
		throws IOException {
		BallgownGenes genes = new DefaultBallgownGenes();
		for(CsvEntry entry : csvGenesData) {
			genes.add(parseGene(entry, csvGenesData.getFormat()));
		}
		return genes;
	}

	private static BallgownGene parseGene(CsvEntry csvGeneEntry,
		CsvFormat csvFormat) throws IOException {
		try {
			return new DefaultBallgownGene(
				csvGeneEntry.get(0),
				csvGeneEntry.get(5),
				asDouble(csvGeneEntry.get(2), csvFormat),
				asDouble(csvGeneEntry.get(3), csvFormat),
				asDouble(csvGeneEntry.get(4), csvFormat)
			);
		} catch (IOException e) {
			throw new IOException("Can't parse number at entry " + csvGeneEntry,
				e);
		}
	}

	private static double asDouble(String string, CsvFormat format)
		throws IOException {
		if (string.equals("NA")) {
			return Double.NaN;
		} else {
			try {
				return format.asDouble(string);
			} catch (ParseException | NumberFormatException e) {
				throw new IOException("Can't parse number " + string, e);
			}
		}
	}
}
