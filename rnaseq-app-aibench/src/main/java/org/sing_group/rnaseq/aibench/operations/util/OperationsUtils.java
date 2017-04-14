package org.sing_group.rnaseq.aibench.operations.util;

import static org.sing_group.rnaseq.gui.sample.FastqSampleEditor.extractSampleNameFromReadsFile;

import java.io.File;

/**
 * A class that provides utilities for AIBench's operations classes.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class OperationsUtils {

	private static final String SAM = ".sam";

	/**
	 * If {@code outputFile} is not {@code null}, then it is returned after
	 * adding the {@code .sam} extension is added to it if not present.
	 * 
	 * If {@code outputFile} is {@code null}, then it is created a new file
	 * alongside {@code readsFile} by extracting the name of the reads file and
	 * adding the {@code .sam} extension.
	 * 
	 * @param outputFile the target sam output file
	 * @param readsFile the target reads file
	 * @return a file with {@code .sam} extension
	 */
	public static File getSamOutputFile(File outputFile, File readsFile) {
		if (outputFile == null) {
			String sampleName = extractSampleNameFromReadsFile(readsFile);

			return new File(readsFile.getParentFile(), sampleName + SAM);
		} else {
			return outputFile.getName().endsWith(SAM) ? outputFile
				: new File(outputFile.getAbsolutePath() + SAM);
		}
	}
}
