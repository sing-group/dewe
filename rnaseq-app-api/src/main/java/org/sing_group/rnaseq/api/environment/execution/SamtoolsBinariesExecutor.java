package org.sing_group.rnaseq.api.environment.execution;

import java.io.File;

import org.sing_group.rnaseq.api.environment.binaries.SamtoolsBinaries;

/**
 * The interface for running samtools binaries.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public interface SamtoolsBinariesExecutor
	extends BinariesExecutor<SamtoolsBinaries> {

	/**
	 * Converts a file in SAM format into a file in BAM format.
	 * 
	 * @param sam the input file in SAM format
	 * @param bam the output file in BAM format
	 * 
	 * @return the {@code ExecutionResult}
	 * 
	 * @throws ExecutionException if an error occurs during the execution
	 * @throws InterruptedException if an error occurs executing the system 
	 *         binary
	 */	
	public abstract ExecutionResult samToBam(File sam, File bam)
		throws ExecutionException, InterruptedException;
}
