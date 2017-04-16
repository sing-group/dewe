package org.sing_group.rnaseq.api.environment.execution;

import java.io.File;

import org.sing_group.rnaseq.api.environment.binaries.Bowtie2Binaries;
import org.sing_group.rnaseq.api.environment.execution.parameters.bowtie2.Bowtie2EndToEndConfiguration;
import org.sing_group.rnaseq.api.persistence.entities.Bowtie2ReferenceGenome;

/**
 * The interface for running Bowtie2 binares.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public interface Bowtie2BinariesExecutor
	extends BinariesExecutor<Bowtie2Binaries> {

	/**
	 * Builds a Bowtie2 index for the specified reference {@code genome} in
	 * {@code outDir} directory with the specified {@code baseName}.
	 * 
	 * @param genome the reference genome
	 * @param outDir the directory where the index must be created
	 * @param baseName the index prefix
	 * 
	 * @return the {@code ExecutionResult}
	 * 
	 * @throws ExecutionException if an error occurs during the execution
	 * @throws InterruptedException if an error occurs executing the system 
	 *         binary
	 */
	public abstract ExecutionResult buildIndex(File genome, File outDir,
		String baseName) 
		throws ExecutionException, InterruptedException;

	/**
	 * Aligns paired reads in files {@code reads1} and {@code reads2} using the
	 * specified reference genome and end-to-end mode configuration, storing 
	 * the result in {@code output} file.
	 * 
	 * @param genome the {@code Bowtie2ReferenceGenome} 
	 * @param reads1 the first pair of reads
	 * @param reads2 the second pair of reads
	 * @param configuration the {@code Bowtie2EndToEndConfiguration}
	 * @param output the file to store the result of the alignment
	 * 
	 * @return the {@code ExecutionResult}
	 * 
	 * @throws ExecutionException if an error occurs during the execution
	 * @throws InterruptedException if an error occurs executing the system 
	 *         binary
	 */
	public abstract ExecutionResult alignReads(Bowtie2ReferenceGenome genome,
		File reads1, File reads2, Bowtie2EndToEndConfiguration configuration,
		File output)
		throws ExecutionException, InterruptedException;

	/**
	 * Aligns paired reads in files {@code reads1} and {@code reads2} using the
	 * specified reference genome and end-to-end mode configuration, storing 
	 * the result in {@code output} file. The {@code alignmentLog} file is used 
	 * to store the {@code stderr} output of Bowtie2 where the alignment rates 
	 * are printed.
	 * 
	 * @param genome the {@code Bowtie2ReferenceGenome} 
	 * @param reads1 the first pair of reads
	 * @param reads2 the second pair of reads
	 * @param configuration the {@code Bowtie2EndToEndConfiguration}
	 * @param output the file to store the result of the alignment
	 * @param alignmentLog the file to store the  {@code stderr} output
	 * 
	 * @return the {@code ExecutionResult}
	 * 
	 * @throws ExecutionException if an error occurs during the execution
	 * @throws InterruptedException if an error occurs executing the system 
	 *         binary
	 */
	public abstract ExecutionResult alignReads(Bowtie2ReferenceGenome genome,
		File reads1, File reads2, Bowtie2EndToEndConfiguration configuration,
		File output, File alignmentLog)
		throws ExecutionException, InterruptedException;
}
