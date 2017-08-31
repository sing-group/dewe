package org.sing_group.rnaseq.api.controller;

import java.io.File;

import org.sing_group.rnaseq.api.environment.execution.ExecutionException;
import org.sing_group.rnaseq.api.environment.execution.HtseqBinariesExecutor;

/**
 * The interface that defines the HTSeq controller.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public interface HtseqController {
	/**
	 * Sets the {@code HtseqBinariesExecutor} that should be used to execute
	 * HTSeq commands.
	 * 
	 * @param executor the {@code HtseqBinariesExecutor}
	 */	
	public abstract void setHtseqBinariesExecutor(
		HtseqBinariesExecutor executor);

	/**
	 * Counts how many reads in {@code inputBam} map to each feature or region 
	 * defined in the {@code referenceAnnotationFile}. Results are written to
	 * {@code output}.
	 * 
	 * @param referenceAnnotationFile the reference annotation file
	 * @param inputBam the input BAM file
	 * @param output the output file
	 * @throws ExecutionException if an error occurs during the execution
	 * @throws InterruptedException if an error occurs executing the system 
	 *         binary
	 */
	public abstract void countBamReverseExon(File referenceAnnotationFile,
		File inputBam, File output)
		throws ExecutionException, InterruptedException;

	/**
	 * Counts how many reads in each input file map to each feature or region 
	 * defined in the {@code referenceAnnotationFile}. Results are generated 
	 * in {@code outputDir} and joined into a unique file at {@code joinFile}.
	 * 
	 * @param referenceAnnotationFile the reference annotation file
	 * @param inputBams the input BAM files
	 * @param outputDir the directory to store the results
	 * @param joinFile the results file
	 * @throws ExecutionException if an error occurs during the execution
	 * @throws InterruptedException if an error occurs executing the system 
	 *         binary
	 */
	public abstract void countBamReverseExon(File referenceAnnotationFile,
		File[] inputBams, File outputDir, File joinFile)
		throws ExecutionException, InterruptedException;
}
