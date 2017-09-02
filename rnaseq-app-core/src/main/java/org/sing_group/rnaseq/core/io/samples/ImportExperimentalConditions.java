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
package org.sing_group.rnaseq.core.io.samples;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.sing_group.rnaseq.api.entities.FastqReadsSamples;

/**
 * Imports the experimental conditions contained in a given directory. Each
 * condition is represented by a directory. For each condition's directory, it
 * imports the paired samples in them.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 * @see ImportPairedSamplesDirectory
 */
public class ImportExperimentalConditions {

	/**
	 * Imports the experimental conditions contained in the specified directory.
	 * Each condition is represented by a directory. For each condition's
	 * directory, it imports the paired samples in them.
	 * 
	 * @param directory  the directory where the conditions are stored
	 * @return a map from condition labels to their corresponding
	 *         {@code FastqReadsSamples} lists.
	 */
	public static Map<String, FastqReadsSamples> importDirectory(
		File directory
	) {
		Map<String, FastqReadsSamples> toret = new HashMap<>();
		for(File subDirectory : directory.listFiles(f -> f.isDirectory())) {
			FastqReadsSamples subDirectorySamples = 
				ImportPairedSamplesDirectory.importDirectory(subDirectory);
			toret.put(subDirectory.getName(), subDirectorySamples);
		}
		return toret;
	}
}
