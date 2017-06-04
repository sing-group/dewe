package org.sing_group.rnaseq.core.io.ballgown;

import static org.sing_group.rnaseq.core.operations.ballgown.BallgownGenesOperations.getTopGeneNames;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import org.sing_group.rnaseq.api.entities.ballgown.BallgownGenes;
import org.sing_group.rnaseq.core.operations.ballgown.BallgownGenesOperations;

/**
 * This class provides methods to write {@code BallgownGenes} lists into files.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class BallgownGenesFileWriter {

	public static void writeTopGeneNames(BallgownGenes genes, File outputFile,
		BallgownGenesOperations.GenesType genesType, int genesCount)
		throws IOException {
		List<String> geneNames = getTopGeneNames(genes, genesCount, genesType);
		Files.write(outputFile.toPath(), geneNames);
	};
}
