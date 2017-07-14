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
