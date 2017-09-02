/*
 * #%L
 * DEWE API
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
package org.sing_group.rnaseq.api.controller;

import java.io.File;

import org.sing_group.rnaseq.api.entities.edger.EdgeRSamples;
import org.sing_group.rnaseq.api.environment.execution.ExecutionException;
import org.sing_group.rnaseq.api.environment.execution.RBinariesExecutor;

/**
 * The interface for controlling the edgeR R package.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public interface EdgeRController {
	/**
	 * Sets the {@code RBinariesExecutor} to use.
	 *
	 * @param executor the {@code RBinariesExecutor} to use
	 */
	public abstract void setRBinariesExecutor(
		RBinariesExecutor executor);

	/**
	 * <p>
	 * Performs the differential expression analysis between the groups of the
	 * samples in the list and stores the results in {@code outputFolder}. Note
	 * that there must be only two conditions and at least two samples in each
	 * one.
	 * </p>
	 *
	 * <p>
	 * In order to use this function, the following files must be present in the
	 * {@code outputFolder} directory:
	 * </p>
	 * <ul>
	 * <li>GeneID_to_GeneName.txt: the gene id to gene name mapping.</li>
	 * <li>gene_read_counts_table_all.tsv: the read count matrix, including a
	 * header with the sample names and a sample class labels in the last row.
	 * </li>
	 * </ul>
	 *
	 * @param workingDir the directory where results must be stored
	 * @throws ExecutionException if an error occurs during the execution
	 * @throws InterruptedException if an error occurs executing the system
	 *         binary
	 */
	public abstract void differentialExpression(File workingDir)
		throws ExecutionException, InterruptedException;

	/**
	 * <p>
	 * Performs the differential expression analysis between the groups of the
	 * samples in the list and stores the results in {@code outputFolder}. Note
	 * that there must be only two conditions and at least two samples in each
	 * one.
	 * </p>
	 *
	 * <p>
	 * Before the differential expression analysis, this function counts reads
	 * in features for each sample using htseq-count and merges all of them
	 * into a single file, which is used as input by the edgeR analysis script.
	 * Moreover, this function also uses the {@code referenceAnnotationFile} to
	 * create a file with the gene id to gene name mappings.
	 * </p>
	 *
	 * @param samples the list of input {@code EdgeRSample}s
	 * @param referenceAnnotationFile the reference annotation file
	 * @param workingDir the directory where results must be stored
	 * @throws ExecutionException if an error occurs during the execution
	 * @throws InterruptedException if an error occurs executing the system
	 *         binary
	 */
	public abstract void differentialExpression(EdgeRSamples samples,
		File referenceAnnotationFile, File workingDir)
		throws ExecutionException, InterruptedException;
}
