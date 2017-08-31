package org.sing_group.rnaseq.api.environment.execution;

import java.io.File;

import org.sing_group.rnaseq.api.environment.binaries.HtseqBinaries;

/**
 * The interface for running HTSeq binaries. 
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public interface HtseqBinariesExecutor extends BinariesExecutor<HtseqBinaries> {
	/**
	 * Counts how many reads in {@code inputBam} map to each feature or region 
	 * defined in the {@code referenceAnnotationFile}. Results are written to
	 * {@code output}.
	 * 
	 * @param referenceAnnotationFile the reference annotation file
	 * @param inputBam the input BAM file
	 * @param output the output file
	 * 
	 * @return the {@code ExecutionResult}
	 * 
	 * @throws ExecutionException if an error occurs during the execution
	 * @throws InterruptedException if an error occurs executing the system 
	 *         binary
	 */
	public abstract ExecutionResult countBamReverseExon(
		File referenceAnnotationFile, File inputBam, File output)
		throws ExecutionException, InterruptedException;
}
