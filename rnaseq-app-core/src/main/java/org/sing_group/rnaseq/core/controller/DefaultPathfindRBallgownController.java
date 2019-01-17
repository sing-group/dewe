/*
 * #%L
 * DEWE Core
 * %%
 * Copyright (C) 2016 - 2018 Hugo López-Fernández, Aitor Blanco-García, Florentino Fdez-Riverola,
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
package org.sing_group.rnaseq.core.controller;

import static org.sing_group.rnaseq.core.environment.execution.DefaultRBinariesExecutor.asScriptFile;
import static org.sing_group.rnaseq.core.environment.execution.DefaultRBinariesExecutor.asString;
import static org.sing_group.rnaseq.core.util.FileUtils.contains;

import java.io.File;
import java.io.IOException;

import org.sing_group.rnaseq.api.controller.PathfindRBallgownController;
import org.sing_group.rnaseq.api.environment.execution.ExecutionException;
import org.sing_group.rnaseq.api.environment.execution.ExecutionResult;
import org.sing_group.rnaseq.api.environment.execution.RBinariesExecutor;

/**
 * The default {@code PathfindRController} implementation.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class DefaultPathfindRBallgownController implements PathfindRBallgownController {
	public static final String OUTPUT_FILE_PHENOTYPE = "phenotype-data.csv";
	public static final String OUTPUT_FILE_GENES_FILTERED_SIGNIFICANT = "phenotype-data_gene_results_sig.tsv";
	public static final String OUTPUT_FILE_FPKMS = "phenotype-data_gene_results_sig_fpkm_plus_1.tsv";
	public static final String OUTPUT_FILE_PE = "pathfindR_clustered_pathways.tsv";
	
	private static final String SCRIPT_PE_ANALYSIS = asString(
		DefaultPathfindRBallgownController.class.getResourceAsStream(
			"/scripts/pathfindr/pathfindR-pathway-enrichment-over-ballgown.R")
		);

	private RBinariesExecutor rBinariesExecutor;

	@Override
	public void setRBinariesExecutor(RBinariesExecutor executor) {
		this.rBinariesExecutor = executor;
	}

  	@Override
  	public void pathwaysEnrichment(File input,
  								   File output,
								   boolean humanGeneSymbols,
								   String geneSets,
								   String clusteringMethod)
			throws ExecutionException, InterruptedException {
  		ExecutionResult result;
  		try {
			checkWorkingDir(input);
  			result = this.rBinariesExecutor.runScript(
				asScriptFile(SCRIPT_PE_ANALYSIS, "edgeR-analysis-"),
				input.getAbsolutePath(),
				output.getAbsolutePath(),
				String.valueOf(humanGeneSymbols).toUpperCase(),
				geneSets,
				clusteringMethod);

			if (result.getExitStatus() != 0) {
				throw new ExecutionException(result.getExitStatus(),
					"Error executing script. Please, check error log.", "");
			}
		} catch (IOException e) {
			throw new ExecutionException(1,
				"Error executing script. Please, check error log.", "");
		}
	}

	private void checkWorkingDir(File workingDir) throws ExecutionException {
		if (!contains(workingDir, OUTPUT_FILE_PHENOTYPE) ||
			!contains(workingDir, OUTPUT_FILE_GENES_FILTERED_SIGNIFICANT) ||
			!contains(workingDir, OUTPUT_FILE_FPKMS)
		) {
			throw new ExecutionException(1,
				"The Ballgown result directory appears to be incorrect or some file is missing.", "");
		}
	}
	
}
