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
package org.sing_group.rnaseq.core.io.edger;

import static es.uvigo.ei.sing.commons.csv.io.CsvReader.CsvReaderBuilder.newCsvReaderBuilder;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

import org.sing_group.rnaseq.api.controller.EdgeRController;
import org.sing_group.rnaseq.api.entities.edger.EdgeRGene;
import org.sing_group.rnaseq.api.entities.edger.EdgeRGenes;
import org.sing_group.rnaseq.core.entities.edgeR.DefaultEdgeRGene;
import org.sing_group.rnaseq.core.entities.edgeR.DefaultEdgeRGenes;

import es.uvigo.ei.sing.commons.csv.entities.CsvData;
import es.uvigo.ei.sing.commons.csv.entities.CsvEntry;
import es.uvigo.ei.sing.commons.csv.entities.CsvFormat;

/**
 * A class to load a genes TXT file into a {@code EdgeRGenes} list. This file is
 * generated, for example, by the
 * {@link EdgeRController#differentialExpression(File)} and
 * {@code EdgeRController#differentialExpression(org.sing_group.rnaseq.api.entities.edger.EdgeRSamples, File, File)}
 * functions.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class EdgeRGenesTxtFileLoader {
	private static final CsvFormat CSV_FORMAT =
		new CsvFormat("\t", '.', true, "\n");

	/**
	 * Reads the specified genes TXT file and returns a {@code EdgeRGenes}
	 * list.
	 *
	 * @param genesFile the genes TXT file
	 *
	 * @return the {@code EdgeRGenes} list
	 * @throws IOException if an error occurs when reading the file
	 */
	public static EdgeRGenes loadFile(File genesFile) throws IOException {
		return	parseGenes(newCsvReaderBuilder()
					.withFormat(CSV_FORMAT)
					.withHeader()
				.build().read(genesFile));
	}

	private static EdgeRGenes parseGenes(CsvData csvGenesData)
		throws IOException {
		EdgeRGenes genes = new DefaultEdgeRGenes();
		for (CsvEntry entry : csvGenesData) {
			genes.add(parseGene(entry, csvGenesData.getFormat()));
		}
		return genes;
	}

	private static EdgeRGene parseGene(CsvEntry csvGeneEntry,
		CsvFormat csvFormat) throws IOException {
		try {
			return new DefaultEdgeRGene(
				csvGeneEntry.get(0),
				csvGeneEntry.get(1),
				asDouble(csvGeneEntry.get(2), csvFormat),
				asDouble(csvGeneEntry.get(3), csvFormat)
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
