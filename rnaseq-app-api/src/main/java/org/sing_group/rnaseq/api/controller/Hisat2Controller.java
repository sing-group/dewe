package org.sing_group.rnaseq.api.controller;

import java.io.File;

import org.sing_group.rnaseq.api.environment.execution.ExecutionException;
import org.sing_group.rnaseq.api.environment.execution.Hisat2BinariesExecutor;
import org.sing_group.rnaseq.api.persistence.entities.Hisat2ReferenceGenomeIndex;

/**
 *  * The interface for controlling HISAT2 commands.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public interface Hisat2Controller {
	/**
	 * Sets the {@code Hisat2BinariesExecutor} that should be used to execute
	 * HISAT2 commands.
	 * 
	 * @param executor the {@code Hisat2BinariesExecutor}
	 */
	public abstract void setHisat2BinariesExecutor(
		Hisat2BinariesExecutor executor);

	/**
	 * Builds a HISAT2 index for the specified reference {@code genome} in
	 * {@code outDir} directory with the specified {@code baseName}.
	 * 
	 * @param genome the reference genome
	 * @param outDir the directory where the index must be created
	 * @param baseName the index prefix
	 * @throws ExecutionException if an error occurs during the execution
	 * @throws InterruptedException if an error occurs executing the system 
	 *         binary
	 */
	public abstract void buildIndex(File genome, File outDir, String baseName)
		throws ExecutionException, InterruptedException;

	/**
	 * Aligns paired reads in files {@code reads1} and {@code reads2} using the
	 * specified reference genome, storing the result in {@code output} file.
	 * 
	 * @param genome the {@code Hisat2ReferenceGenome} 
	 * @param reads1 the first pair of reads
	 * @param reads2 the second pair of reads
	 * @param dta reports alignments tailored for transcript assemblers
	 * @param output the file to store the result of the alignment
	 * @throws ExecutionException if an error occurs during the execution
	 * @throws InterruptedException if an error occurs executing the system 
	 *         binary
	 */
	public abstract void alignReads(Hisat2ReferenceGenomeIndex genome, File reads1,
		File reads2, boolean dta, File output)
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
	 * @param dta reports alignments tailored for transcript assemblers
	 * @param output the file to store the result of the alignment
	 * @param saveAlignmentLog whether the alignment log must be stored or not.
	 * @throws ExecutionException if an error occurs during the execution
	 * @throws InterruptedException if an error occurs executing the system 
	 *         binary
	 */
	public abstract void alignReads(Hisat2ReferenceGenomeIndex genome, File reads1,
		File reads2, boolean dta, File output, boolean saveAlignmentLog)
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
	 * @param dta reports alignments tailored for transcript assemblers
	 * @param output the file to store the result of the alignment
	 * @param alignmentLog the file to store the  {@code stderr} output
	 * @throws ExecutionException if an error occurs during the execution
	 * @throws InterruptedException if an error occurs executing the system 
	 *         binary
	 */
	public abstract void alignReads(Hisat2ReferenceGenomeIndex genome, File reads1,
		File reads2, boolean dta, File output, File alignmentLog)
			throws ExecutionException, InterruptedException;
}
