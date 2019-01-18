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
package org.sing_group.rnaseq.core.io.ballgownedgeroverlaps;

import static es.uvigo.ei.sing.commons.csv.io.CsvReader.CsvReaderBuilder.newCsvReaderBuilder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;

import org.sing_group.rnaseq.api.entities.ballgownedgeroverlaps.BallgownEdgeROverlap;
import org.sing_group.rnaseq.api.entities.ballgownedgeroverlaps.BallgownEdgeROverlaps;
import org.sing_group.rnaseq.core.entities.ballgownedgeroverlaps.DefaultBallgownEdgeROverlap;
import org.sing_group.rnaseq.core.entities.ballgownedgeroverlaps.DefaultBallgownEdgeROverlaps;

import es.uvigo.ei.sing.commons.csv.entities.CsvData;
import es.uvigo.ei.sing.commons.csv.entities.CsvEntry;
import es.uvigo.ei.sing.commons.csv.entities.CsvFormat;

/**
 * A class to load a pathways TSV file into a {@code BallgownEdgeROverlaps} list. 
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class BallgownEdgeROverlapsTSVFileLoader {
	private static final CsvFormat CSV_FORMAT =
		new CsvFormat("\t", '.', true, "\n");

	/**
	 * Reads the specified genes TSV file and returns a {@code BallgownEdgeROverlaps}
	 * list.
	 *
	 * @param overlapsFile the genes TSV file
	 *
	 * @return the {@code BallgownEdgeROverlaps} list
	 * @throws IOException if an error occurs when reading the file
	 */
	public static BallgownEdgeROverlaps loadFile(File overlapsFile) throws IOException {
		BallgownEdgeROverlaps overlaps = new DefaultBallgownEdgeROverlaps();
		BufferedReader br = new BufferedReader(new FileReader(overlapsFile));   
		String line = br.readLine();
		if (line != null && !line.startsWith("\"\"")) {
			overlaps = parsePathways(newCsvReaderBuilder()
					.withFormat(CSV_FORMAT)
					.withHeader()
				.build().read(overlapsFile));
		}
		br.close();
		return overlaps;
	}

	private static BallgownEdgeROverlaps parsePathways(CsvData csvOverlapsData)
		throws IOException {
		BallgownEdgeROverlaps overlaps = new DefaultBallgownEdgeROverlaps();
		for (CsvEntry entry : csvOverlapsData) {
			overlaps.add(parseOverlap(entry, csvOverlapsData.getFormat()));
		}
		return overlaps;
	}

	private static BallgownEdgeROverlap parseOverlap(CsvEntry csvOverlapEntry,
		CsvFormat csvFormat) throws IOException {
		try {
			return new DefaultBallgownEdgeROverlap(
					csvOverlapEntry.get(0),
				asDouble(csvOverlapEntry.get(1), csvFormat),
				asDouble(csvOverlapEntry.get(3), csvFormat),
				asDouble(csvOverlapEntry.get(2), csvFormat),
				asDouble(csvOverlapEntry.get(4), csvFormat)
			);
		} catch (IOException e) {
			throw new IOException("Can't parse number at entry " + csvOverlapEntry,
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
