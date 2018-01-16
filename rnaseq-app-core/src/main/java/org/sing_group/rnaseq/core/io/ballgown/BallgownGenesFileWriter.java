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
