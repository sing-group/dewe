/*
 * #%L
 * DEWE Core
 * %%
 * Copyright (C) 2016 - 2018 Hugo López-Fernández, Aitor Blanco-García, Florentino Fdez-Riverola, 
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
package org.sing_group.rnaseq.core.io.pathfindr;

import static es.uvigo.ei.sing.commons.csv.io.CsvReader.CsvReaderBuilder.newCsvReaderBuilder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;

import org.sing_group.rnaseq.api.entities.pathfindr.PathfindRPathway;
import org.sing_group.rnaseq.api.entities.pathfindr.PathfindRPathways;
import org.sing_group.rnaseq.core.entities.pathfindr.DefaultPathfindRPathway;
import org.sing_group.rnaseq.core.entities.pathfindr.DefaultPathfindRPathways;

import es.uvigo.ei.sing.commons.csv.entities.CsvData;
import es.uvigo.ei.sing.commons.csv.entities.CsvEntry;
import es.uvigo.ei.sing.commons.csv.entities.CsvFormat;

/**
 * A class to load a pathways RSV file into a {@code PathfindRPathways} list. 
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class PathfindRPathwaysTSVFileLoader {
	private static final CsvFormat CSV_FORMAT =
		new CsvFormat("\t", '.', true, "\n");

	/**
	 * Reads the specified genes TSV file and returns a {@code PathfindRPathways}
	 * list.
	 *
	 * @param pathwaysFile the genes TSV file
	 *
	 * @return the {@code PathfindRPathways} list
	 * @throws IOException if an error occurs when reading the file
	 */
	public static PathfindRPathways loadFile(File pathwaysFile) throws IOException {
		PathfindRPathways pathways = new DefaultPathfindRPathways();
		BufferedReader br = new BufferedReader(new FileReader(pathwaysFile));   
		String line = br.readLine();
		if (line != null && !line.startsWith("\"\"")) {
			pathways = parsePathways(newCsvReaderBuilder()
					.withFormat(CSV_FORMAT)
					.withHeader()
				.build().read(pathwaysFile));
		}
		br.close();
		return pathways;
	}

	private static PathfindRPathways parsePathways(CsvData csvPathwaysData)
		throws IOException {
		PathfindRPathways pathways = new DefaultPathfindRPathways();
		for (CsvEntry entry : csvPathwaysData) {
			pathways.add(parsePathway(entry, csvPathwaysData.getFormat()));
		}
		return pathways;
	}

	private static PathfindRPathway parsePathway(CsvEntry csvPathwayEntry,
		CsvFormat csvFormat) throws IOException {
		try {
			return new DefaultPathfindRPathway(
				csvPathwayEntry.get(0),
				csvPathwayEntry.get(1),
				asDouble(csvPathwayEntry.get(2), csvFormat),
				asInteger(csvPathwayEntry.get(3), csvFormat),
				asDouble(csvPathwayEntry.get(4), csvFormat),
				asDouble(csvPathwayEntry.get(5), csvFormat),
				csvPathwayEntry.get(6),
				csvPathwayEntry.get(7),
				asInteger(csvPathwayEntry.get(8), csvFormat),
				csvPathwayEntry.get(9)
			);
		} catch (IOException e) {
			throw new IOException("Can't parse number at entry " + csvPathwayEntry,
				e);
		}
	}
	


	private static int asInteger(String string, CsvFormat format)
		throws IOException {		
		try {
			return Integer.parseInt(string);
		} catch (NumberFormatException e) {
			throw new IOException("Can't parse number " + string, e);
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
