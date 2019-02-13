/*
 * #%L
 * DEWE Core
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
package org.sing_group.rnaseq.core.controller;

import static org.sing_group.rnaseq.core.controller.helper.AbstractDifferentialExpressionWorkflow.getAnalysisSubDir;
import static org.sing_group.rnaseq.core.environment.execution.DefaultRBinariesExecutor.asScriptFile;
import static org.sing_group.rnaseq.core.environment.execution.DefaultRBinariesExecutor.asString;
import static org.sing_group.rnaseq.core.util.FileUtils.contains;

import java.io.File;
import java.io.IOException;

import org.sing_group.rnaseq.api.controller.DEOverlapsController;
import org.sing_group.rnaseq.api.environment.execution.ExecutionException;
import org.sing_group.rnaseq.api.environment.execution.ExecutionResult;
import org.sing_group.rnaseq.api.environment.execution.RBinariesExecutor;
import org.sing_group.rnaseq.api.environment.execution.parameters.VennDiagramConfigurationParameter;

/**
 * The default {@code EdgeRController} implementation.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class DefaultDEOverlapsController implements DEOverlapsController {
	public static final String OUTPUT_FILE_BALLGOWN_DE_SIGNIFICANT_GENES = "phenotype-data_gene_results_sig.tsv";
	public static final String OUTPUT_FILE_EDGER_DE_SIGNIFICANT_GENES = "DE_significant_genes.tsv";
	public static final String OUTPUT_FILE_BALLGOWN_EDGER_OVERLAPS = "overlap-ballgown-edger.tsv";
	
	private static final String SCRIPT_OVERLAPS_ANALYSIS = asString(
		DefaultPathfindRBallgownController.class.getResourceAsStream(
			"/scripts/deoverlaps/overlaps-ballgown-edger.R")
		);
	
	private static final String SCRIPT_OVERLAPS_FIGURE = asString(
		DefaultPathfindRBallgownController.class.getResourceAsStream(
			"/scripts/deoverlaps/overlaps-ballgown-edger-figure.R")
		);

	private RBinariesExecutor rBinariesExecutor;

	@Override
	public void setRBinariesExecutor(RBinariesExecutor executor) {
		this.rBinariesExecutor = executor;
	}

  	@Override
	public void ballgownEdgerROverlaps(File workingDir, File outputDir)
		throws ExecutionException, InterruptedException {
  		ExecutionResult result;
  		try {
			checkWorkingDir(workingDir);
  			result = this.rBinariesExecutor.runScript(
				asScriptFile(SCRIPT_OVERLAPS_ANALYSIS, "ballgown-edger-overlaps-analysis-"),
				workingDir.getAbsolutePath(),
				outputDir.getAbsolutePath());
			
			if (result.getExitStatus() != 0) {
				throw new ExecutionException(result.getExitStatus(),
					"Error executing script. Please, check error log.", "");
			}

			removeLogFiles(outputDir);
		} catch (IOException e) {
			throw new ExecutionException(1,
				"Error executing script. Please, check error log.", "");
		}
	}

	@Override
	public void createBallgownEdgerRVennDiagram(File workingDirectory,
			VennDiagramConfigurationParameter imageConfiguration)
		throws ExecutionException, InterruptedException {
		runFigureScript(
			workingDirectory, imageConfiguration, SCRIPT_OVERLAPS_FIGURE);
	}
	
	public void runFigureScript(File workingDirectory,
			VennDiagramConfigurationParameter imageConfiguration, String script)
				throws ExecutionException, InterruptedException {
			ExecutionResult result;
			try {
				result = this.rBinariesExecutor.runScript(
					asScriptFile(script, "DE-overlaps-figure-"),
					workingDirectory.getParentFile().getAbsolutePath(),
					imageConfiguration.getFormat().getExtension(),
					String.valueOf(imageConfiguration.getWidth()),
					String.valueOf(imageConfiguration.getHeight()),
					String.valueOf(imageConfiguration.isColored()).toUpperCase()
				);
				if (result.getExitStatus() != 0) {
					throw new ExecutionException(result.getExitStatus(),
						"Error executing script. Please, check error log.", "");
				}
				removeLogFiles(workingDirectory);
			} catch (IOException e) {
				throw new ExecutionException(1,
					"Error executing script. Please, check error log.", "");
			}
		}

	private void checkWorkingDir(File workingDir) throws ExecutionException {
		if (!contains(getAnalysisSubDir(workingDir,"ballgown"), OUTPUT_FILE_BALLGOWN_DE_SIGNIFICANT_GENES) ||
			!contains(getAnalysisSubDir(workingDir,"edger"), OUTPUT_FILE_EDGER_DE_SIGNIFICANT_GENES)
		) {
			throw new ExecutionException(1,
				"Error executing script. Please, check error log.", "");
		}
	}
	
	private void removeLogFiles(File workingDirectory){
		for (File file : workingDirectory.listFiles()) {
			if (file.getName().endsWith(".log")) {
				file.delete();
			}
		}
	}
}
