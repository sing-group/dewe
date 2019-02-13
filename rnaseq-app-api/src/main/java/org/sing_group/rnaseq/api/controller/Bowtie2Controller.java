/*
 * #%L
 * DEWE API
 * %%
 * Copyright (C) 2016 - 2019 Hugo López-Fernández, Aitor Blanco-García, Florentino Fdez-Riverola,
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
package org.sing_group.rnaseq.api.controller;

import java.io.File;

import org.sing_group.rnaseq.api.environment.execution.Bowtie2BinariesExecutor;
import org.sing_group.rnaseq.api.environment.execution.ExecutionException;
import org.sing_group.rnaseq.api.persistence.entities.Bowtie2ReferenceGenomeIndex;

/**
 * The interface for controlling Bowtie2 commands.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public interface Bowtie2Controller {
	/**
	 * Sets the {@code Bowtie2BinariesExecutor} that should be used to execute
	 * Bowtie2 commands.
	 *
	 * @param executor the {@code Bowtie2BinariesExecutor}
	 */
	public abstract void setBowtie2BinariesExecutor(
		Bowtie2BinariesExecutor executor);

	/**
	 * Builds a Bowtie2 index for the specified reference {@code genome} in
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
	 * Aligns paired-end reads in files {@code reads1} and {@code reads2} using the
	 * specified reference genome and end-to-end mode configuration, storing
	 * the result in {@code output} file.
	 *
	 * @param genome the {@code Bowtie2ReferenceGenome}
	 * @param reads1 the first pair of reads
	 * @param reads2 the second pair of reads
	 * @param params the list of command-line parameters
	 * @param output the file to store the result of the alignment
	 * @throws ExecutionException if an error occurs during the execution
	 * @throws InterruptedException if an error occurs executing the system
	 *         binary
	 */
	public abstract void alignReads(Bowtie2ReferenceGenomeIndex genome, File reads1,
		File reads2, String params, File output)
		throws ExecutionException, InterruptedException;

	/**
	 * Aligns single-end reads in file {@code reads} using the
	 * specified reference genome and end-to-end mode configuration, storing
	 * the result in {@code output} file.
	 *
	 * @param genome the {@code Bowtie2ReferenceGenome}
	 * @param reads the single-end reads file
	 * @param params the list of command-line parameters
	 * @param output the file to store the result of the alignment
	 * @throws ExecutionException if an error occurs during the execution
	 * @throws InterruptedException if an error occurs executing the system
	 *         binary
	 */
	public abstract void alignReads(Bowtie2ReferenceGenomeIndex genome, File reads,
		String params, File output)
		throws ExecutionException, InterruptedException;

	/**
	 * Aligns paired-end reads in files {@code reads1} and {@code reads2} using the
	 * specified reference genome and end-to-end mode configuration, storing
	 * the result in {@code output} file. If {@code saveAlignmentLog} is
	 * {@code true}, then the {@code stderr} output of Bowtie2 where the
	 * alignment rates are printed is stored alongside the output file (adding
	 * {@code txt} extension}.
	 *
	 * @param genome the {@code Bowtie2ReferenceGenome}
	 * @param reads1 the first pair of reads
	 * @param reads2 the second pair of reads
	 * @param params the list of command-line parameters
	 * @param output the file to store the result of the alignment
	 * @param saveAlignmentLog whether the alignment log must be stored or not.
	 * @throws ExecutionException if an error occurs during the execution
	 * @throws InterruptedException if an error occurs executing the system
	 *         binary
	 */
	public abstract void alignReads(Bowtie2ReferenceGenomeIndex genome, File reads1,
		File reads2, String params, File output,
		boolean saveAlignmentLog)
		throws ExecutionException, InterruptedException;

	/**
	 * Aligns single-end reads in file {@code reads} using the
	 * specified reference genome and end-to-end mode configuration, storing
	 * the result in {@code output} file. If {@code saveAlignmentLog} is
	 * {@code true}, then the {@code stderr} output of Bowtie2 where the
	 * alignment rates are printed is stored alongside the output file (adding
	 * {@code txt} extension}.
	 *
	 * @param genome the {@code Bowtie2ReferenceGenome}
	 * @param reads the single-end reads file
	 * @param params the list of command-line parameters
	 * @param output the file to store the result of the alignment
	 * @param saveAlignmentLog whether the alignment log must be stored or not.
	 * @throws ExecutionException if an error occurs during the execution
	 * @throws InterruptedException if an error occurs executing the system
	 *         binary
	 */
	public abstract void alignReads(Bowtie2ReferenceGenomeIndex genome, File reads,
		String params, File output,
		boolean saveAlignmentLog)
		throws ExecutionException, InterruptedException;

	/**
	 * Aligns paired-end reads in files {@code reads1} and {@code reads2} using the
	 * specified reference genome and end-to-end mode configuration, storing
	 * the result in {@code output} file. The {@code alignmentLog} file is used
	 * to store the {@code stderr} output of Bowtie2 where the alignment rates
	 * are printed.
	 *
	 * @param genome the {@code Bowtie2ReferenceGenome}
	 * @param reads1 the first pair of reads
	 * @param reads2 the second pair of reads
	 * @param params the list of command-line parameters
	 * @param output the file to store the result of the alignment
	 * @param alignmentLog the file to store the  {@code stderr} output
	 * @throws ExecutionException if an error occurs during the execution
	 * @throws InterruptedException if an error occurs executing the system
	 *         binary
	 */
	public abstract void alignReads(Bowtie2ReferenceGenomeIndex genome, File reads1,
		File reads2, String params, File output,
		File alignmentLog)
		throws ExecutionException, InterruptedException;

	/**
	 * Aligns single-end reads in file {@code reads} using the
	 * specified reference genome and end-to-end mode configuration, storing
	 * the result in {@code output} file. The {@code alignmentLog} file is used
	 * to store the {@code stderr} output of Bowtie2 where the alignment rates
	 * are printed.
	 *
	 * @param genome the {@code Bowtie2ReferenceGenome}
	 * @param reads the single-end reads file
	 * @param params the list of command-line parameters
	 * @param output the file to store the result of the alignment
	 * @param alignmentLog the file to store the  {@code stderr} output
	 * @throws ExecutionException if an error occurs during the execution
	 * @throws InterruptedException if an error occurs executing the system
	 *         binary
	 */
	public abstract void alignReads(Bowtie2ReferenceGenomeIndex genome, File reads,
		String params, File output,
		File alignmentLog)
		throws ExecutionException, InterruptedException;
}
