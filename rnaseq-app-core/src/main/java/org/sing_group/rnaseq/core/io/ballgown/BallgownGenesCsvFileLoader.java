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
