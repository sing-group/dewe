package org.sing_group.rnaseq.api.environment.execution;

import java.io.File;

import org.sing_group.rnaseq.api.environment.binaries.Hisat2Binaries;
import org.sing_group.rnaseq.api.persistence.entities.Hisat2ReferenceGenome;

/**
 * The interface for running HISAT2 binaries.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public interface Hisat2BinariesExecutor
	extends BinariesExecutor<Hisat2Binaries> {

	/**
	 * Builds a HISAT2 index for the specified reference {@code genome} in
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
	 * specified reference genome, storing the result in {@code output} file. If
	 * {@code saveAlignmentLog} is {@code true}, then the {@code stderr} output
	 * of HISAT2 where the alignment rates are printed is stored alongside the
	 * output file (adding {@code txt} extension}.
	 * 
	 * @param genome the {@code Hisat2ReferenceGenome}
	 * @param reads1 the first pair of reads
	 * @param reads2 the second pair of reads
	 * @param output the file to store the result of the alignment
	 * 
	 * @return the {@code ExecutionResult}
	 *
	 * @throws ExecutionException if an error occurs during the execution
	 * @throws InterruptedException if an error occurs executing the system 
	 *         binary
	 */
	public abstract ExecutionResult alignReads(Hisat2ReferenceGenome genome,
		File reads1, File reads2, File output)
		throws ExecutionException, InterruptedException;

	/**
	 * Aligns paired reads in files {@code reads1} and {@code reads2} using the
	 * specified reference genome, storing the result in {@code output} file.
	 * The {@code alignmentLog} file is used to store the {@code stderr} output
	 * of HISAT2 where the alignment rates are printed.
	 * 
	 * @param genome the {@code Hisat2ReferenceGenome} 
	 * @param reads1 the first pair of reads
	 * @param reads2 the second pair of reads
	 * @param output the file to store the result of the alignment
	 * @param alignmentLog the file to store the  {@code stderr} output
	 * 
	 * @return the {@code ExecutionResult}
	 *
	 * @throws ExecutionException if an error occurs during the execution
	 * @throws InterruptedException if an error occurs executing the system 
	 *         binary
	 */
	public abstract ExecutionResult alignReads(Hisat2ReferenceGenome genome,
		File reads1, File reads2, File output, File alignmentLog)
		throws ExecutionException, InterruptedException;
}
