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

import org.sing_group.rnaseq.api.entities.ballgown.BallgownTranscript;
import org.sing_group.rnaseq.api.entities.ballgown.BallgownTranscripts;
import org.sing_group.rnaseq.core.entities.ballgown.DefaultBallgownTranscript;
import org.sing_group.rnaseq.core.entities.ballgown.DefaultBallgownTranscripts;

import es.uvigo.ei.sing.commons.csv.entities.CsvData;
import es.uvigo.ei.sing.commons.csv.entities.CsvEntry;
import es.uvigo.ei.sing.commons.csv.entities.CsvFormat;

/**
 * A class to load a transcripts CSV file into a {@code BallgownTranscripts} list.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class BallgownTranscriptsCsvFileLoader {
	private static final CsvFormat CSV_FORMAT =
		new CsvFormat("\t", '.', true, "\n");

	/**
	 * Reads the specified transcripts CSV file and returns a
	 * {@code BallgownTranscripts} list.
	 *
	 * @param transcriptsFile the transcripts CSV file
	 *
	 * @return the {@code BallgownTranscripts} list
	 * @throws IOException if an error occurs when reading the file
	 */
	public static BallgownTranscripts loadFile(File transcriptsFile)
		throws IOException {
		return 	parseTranscripts(newCsvReaderBuilder()
					.withFormat(CSV_FORMAT)
					.withHeader()
					.build().read(transcriptsFile)
				);
	}

	private static BallgownTranscripts parseTranscripts(
		CsvData csvTranscriptsData) throws IOException {
		BallgownTranscripts transcripts = new DefaultBallgownTranscripts();
		for(CsvEntry entry : csvTranscriptsData) {
			transcripts
				.add(parseTranscript(entry, csvTranscriptsData.getFormat()));
		}
		return transcripts;
	}

	private static BallgownTranscript parseTranscript(
		CsvEntry csvTranscriptEntry, CsvFormat csvFormat) throws IOException {
		try {
			return new DefaultBallgownTranscript(
				csvTranscriptEntry.get(1),
				csvTranscriptEntry.get(0),
				csvTranscriptEntry.get(2),
				Integer.valueOf(csvTranscriptEntry.get(4)),
				asDouble(csvTranscriptEntry.get(5), csvFormat),
				asDouble(csvTranscriptEntry.get(6), csvFormat),
				asDouble(csvTranscriptEntry.get(7), csvFormat)
			);
		} catch (NumberFormatException | IOException e) {
			throw new IOException(
				"Can't parse number at entry " + csvTranscriptEntry, e);
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
