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
