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

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import org.sing_group.rnaseq.api.controller.BallgownController;
import org.sing_group.rnaseq.api.entities.ballgown.BallgownSample;
import org.sing_group.rnaseq.api.environment.execution.ExecutionException;
import org.sing_group.rnaseq.api.environment.execution.ExecutionResult;
import org.sing_group.rnaseq.api.environment.execution.RBinariesExecutor;
import org.sing_group.rnaseq.api.environment.execution.parameters.ImageConfigurationParameter;
import org.sing_group.rnaseq.core.environment.execution.parameters.DefaultImageConfigurationParameter;

/**
 * The default {@code BallgownController} implementation.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class DefaultBallgownController implements BallgownController {
	public static final String SCRIPT_DE_ANALYSIS =	asString(
		DefaultBallgownController.class.getResourceAsStream(
			"/scripts/ballgown/ballgown-differential-expression.R")
		);
	public static final String SCRIPT_FIGURE_FKPM_TRANSCRIPT = asString(
		DefaultBallgownController.class.getResourceAsStream(
			"/scripts/ballgown/figure-fkpm-transcript-distribution.R")
		);
	public static final String SCRIPT_FIGURE_EXPRESSION_LEVELS = asString(
		DefaultBallgownController.class.getResourceAsStream(
			"/scripts/ballgown/figure-expression-levels-gene-sample.R")
		);
	public static final String SCRIPT_FIGURE_FPKM_TRANSCRIPT_ACROSS_SAMPLES = asString(
		DefaultBallgownController.class.getResourceAsStream(
			"/scripts/ballgown/figure-fpkm-distribution-across-samples.R")
		);
	public static final String SCRIPT_FIGURE_TRANSCRIPTS_DE_PVALUES = asString(
		DefaultBallgownController.class.getResourceAsStream(
			"/scripts/ballgown/figure-transcripts-DE-pValues-distribution.R")
		);
	public static final String SCRIPT_FIGURE_GENES_DE_PVALUES = asString(
		DefaultBallgownController.class.getResourceAsStream(
			"/scripts/ballgown/figure-genes-DE-pValues-distribution.R")
	);
	public static final String SCRIPT_TABLE_GENES_SIG = asString(
		DefaultBallgownController.class.getResourceAsStream(
			"/scripts/ballgown/table-genes-sig.R")
		);
	public static final String SCRIPT_TABLE_TRANSCRIPTS_SIG = asString(
		DefaultBallgownController.class.getResourceAsStream(
			"/scripts/ballgown/table-transcripts-sig.R")
		);

	public static final String OUTPUT_BALLGOWN_R_DATA = "bg.rda";
	public static final String OUTPUT_FILE_PHENOTYPE = "phenotype-data.csv";
	public static final String OUTPUT_FILE_GENES = "phenotype-data_gene_results.tsv";
	public static final String OUTPUT_FILE_GENES_FILTERED = "phenotype-data_gene_results_filtered.tsv";
	public static final String OUTPUT_FILE_GENES_FILTERED_SIGNIFICANT = "phenotype-data_gene_results_sig.tsv";
	public static final String OUTPUT_FILE_TRANSCRIPTS = "phenotype-data_transcript_results.tsv";
	public static final String OUTPUT_FILE_TRANSCRIPTS_FILTERED = "phenotype-data_transcript_results_filtered.tsv";
	public static final String OUTPUT_FILE_TRANSCRIPTS_FILTERED_SIGNIFICANT = "phenotype-data_transcript_results_sig.tsv";

	private RBinariesExecutor rBinariesExecutor;

	@Override
	public void setRBinariesExecutor(RBinariesExecutor executor) {
		this.rBinariesExecutor = executor;
	}

	@Override
	public void differentialExpression(List<BallgownSample> samples,
		File outputFolder) throws ExecutionException, InterruptedException {
		differentialExpression(samples, outputFolder, DefaultImageConfigurationParameter.DEFAULT);
	}
	@Override
	public void differentialExpression(List<BallgownSample> samples,
		File outputFolder, ImageConfigurationParameter imageConfiguration
	) throws ExecutionException, InterruptedException {
		ExecutionResult result;
		try {
			File phenotypeData = writePhenotypeData(samples, outputFolder);

			result = this.rBinariesExecutor.runScript(
				asScriptFile(SCRIPT_DE_ANALYSIS, "ballgown-analysis-"),
				outputFolder.getAbsolutePath(),
				phenotypeData.getName(),
				imageConfiguration.getFormat().getExtension(),
				String.valueOf(imageConfiguration.getWidth()),
				String.valueOf(imageConfiguration.getHeight()),
				String.valueOf(imageConfiguration.isColored()).toUpperCase());

			if (result.getExitStatus() != 0) {
				throw new ExecutionException(result.getExitStatus(),
					"Error executing script. Please, check error log.", "");
			}
		} catch (IOException e) {
			throw new ExecutionException(1,
				"Error executing script. Please, check error log.", "");
		}
	}

	private File writePhenotypeData(List<BallgownSample> samples,
		File outputFolder) throws IOException {
		outputFolder.mkdirs();
		File phenotypeData = new File(outputFolder, "phenotype-data.csv");
		Files.write(phenotypeData.toPath(), phenotypeData(samples).getBytes());

		return phenotypeData;
	}

	private String phenotypeData(List<BallgownSample> samples) {
		StringBuilder sb = new StringBuilder();
		sb.append("\"ids\",\"type\",\"path\"\n");
		for(BallgownSample sample : samples) {
			sb
				.append("\"")
				.append(sample.getName())
				.append("\",\"")
				.append(sample.getType())
				.append("\",\"")
				.append(sample.getFile().getAbsolutePath())
				.append("\"\n");
		}
		return sb.toString();
	}

	@Override
	public void createFpkmDistributionAcrossSamplesFigure(
		File workingDirectory, ImageConfigurationParameter imageConfiguration
	) throws ExecutionException, InterruptedException {
		ExecutionResult result;
		try {
			result = this.rBinariesExecutor.runScript(
				asScriptFile(SCRIPT_FIGURE_FPKM_TRANSCRIPT_ACROSS_SAMPLES, "ballgown-figure-"),
				workingDirectory.getAbsolutePath(),
				imageConfiguration.getFormat().getExtension(),
				String.valueOf(imageConfiguration.getWidth()),
				String.valueOf(imageConfiguration.getHeight()),
				String.valueOf(imageConfiguration.isColored()).toUpperCase()
			);

			if (result.getExitStatus() != 0) {
				throw new ExecutionException(result.getExitStatus(),
					"Error executing script. Please, check error log.", "");
			}
		} catch (IOException e) {
			throw new ExecutionException(1,
				"Error executing script. Please, check error log.", "");
		}
	}

	@Override
	public void createGenesDEpValuesFigure(
		File workingDirectory, ImageConfigurationParameter imageConfiguration
	) throws ExecutionException, InterruptedException {
		ExecutionResult result;
		try {
			result = this.rBinariesExecutor.runScript(
				asScriptFile(SCRIPT_FIGURE_GENES_DE_PVALUES, "ballgown-figure-"),
				workingDirectory.getAbsolutePath(),
				imageConfiguration.getFormat().getExtension(),
				String.valueOf(imageConfiguration.getWidth()),
				String.valueOf(imageConfiguration.getHeight()),
				String.valueOf(imageConfiguration.isColored()).toUpperCase()
			);

			if (result.getExitStatus() != 0) {
				throw new ExecutionException(result.getExitStatus(),
					"Error executing script. Please, check error log.", "");
			}
		} catch (IOException e) {
			throw new ExecutionException(1,
				"Error executing script. Please, check error log.", "");
		}
	}

	@Override
	public void createTranscriptsDEpValuesFigure(
		File workingDirectory, ImageConfigurationParameter imageConfiguration
	) throws ExecutionException, InterruptedException {
		ExecutionResult result;
		try {
			result = this.rBinariesExecutor.runScript(
				asScriptFile(SCRIPT_FIGURE_TRANSCRIPTS_DE_PVALUES, "ballgown-figure-"),
				workingDirectory.getAbsolutePath(),
				imageConfiguration.getFormat().getExtension(),
				String.valueOf(imageConfiguration.getWidth()),
				String.valueOf(imageConfiguration.getHeight()),
				String.valueOf(imageConfiguration.isColored()).toUpperCase()
			);

			if (result.getExitStatus() != 0) {
				throw new ExecutionException(result.getExitStatus(),
					"Error executing script. Please, check error log.", "");
			}
		} catch (IOException e) {
			throw new ExecutionException(1,
				"Error executing script. Please, check error log.", "");
		}
	}

	@Override
	public void createFpkmDistributionFigureForTranscript(File workingDirectory,
		String transcriptId, ImageConfigurationParameter imageConfiguration
	) throws ExecutionException, InterruptedException {
		ExecutionResult result;
		try {
			checkWorkingDirectoryContainsBallgownData(workingDirectory);

			result = this.rBinariesExecutor.runScript(
				asScriptFile(SCRIPT_FIGURE_FKPM_TRANSCRIPT, "ballgown-figure-"),
				workingDirectory.getAbsolutePath(),
				transcriptId,
				imageConfiguration.getFormat().getExtension(),
				String.valueOf(imageConfiguration.getWidth()),
				String.valueOf(imageConfiguration.getHeight()),
				String.valueOf(imageConfiguration.isColored()).toUpperCase()
			);

			if (result.getExitStatus() != 0) {
				throw new ExecutionException(result.getExitStatus(),
					"Error executing script. Please, check error log.", "");
			}
		} catch (IOException e) {
			throw new ExecutionException(1,
				"Error executing script. Please, check error log.", "");
		}
	}

	@Override
	public void createExpressionLevelsFigure(File workingDirectory,
		String transcriptId, String sampleName,
		ImageConfigurationParameter imageConfiguration
	) throws ExecutionException, InterruptedException {
		ExecutionResult result;
		try {
			checkWorkingDirectoryContainsBallgownData(workingDirectory);

			result = this.rBinariesExecutor.runScript(
				asScriptFile(SCRIPT_FIGURE_EXPRESSION_LEVELS, "ballgown-figure-"),
				workingDirectory.getAbsolutePath(),
				transcriptId,
				sampleName,
				imageConfiguration.getFormat().getExtension(),
				String.valueOf(imageConfiguration.getWidth()),
				String.valueOf(imageConfiguration.getHeight()),
				String.valueOf(imageConfiguration.isColored()).toUpperCase()
			);

			if (result.getExitStatus() != 0) {
				throw new ExecutionException(result.getExitStatus(),
					"Error executing script. Please, check error log.", "");
			}
		} catch (IOException e) {
			throw new ExecutionException(1,
				"Error executing script. Please, check error log.", "");
		}
	}

	private static void checkWorkingDirectoryContainsBallgownData(
		File workingDirectory
	) throws ExecutionException {
		File ballgownData = new File(workingDirectory, OUTPUT_BALLGOWN_R_DATA);
		if (!ballgownData.exists()) {
			throw new ExecutionException(1,
				"Error creating figure: bg.rda can't be found in working directory.",
				"");
		}
	}

	@Override
	public File exportFilteredGenesTable(File workingDirectory, double pValue)
		throws ExecutionException, InterruptedException {
		ExecutionResult result;
		try {
			checkWorkingDirectoryContainsBallgownData(workingDirectory);

			result = this.rBinariesExecutor.runScript(
				asScriptFile(SCRIPT_TABLE_GENES_SIG, "ballgown-table-genes-"),
				workingDirectory.getAbsolutePath(),
				String.valueOf(pValue));

			if (result.getExitStatus() != 0) {
				throw new ExecutionException(result.getExitStatus(),
					"Error executing script. Please, check error log.", "");
			} else {
				String fileName = exportFilteredGenesTableName(pValue);
				return new File(workingDirectory, fileName);
			}
		} catch (IOException e) {
			throw new ExecutionException(1,
				"Error executing script. Please, check error log.", "");
		}
	}

	private String exportFilteredGenesTableName(double pValue) {
		return "user-tables/gene_results_sig_" + pValue + ".tsv";
	}

	@Override
	public File exportFilteredTranscriptsTable(File workingDirectory,
		double pValue)
		throws ExecutionException, InterruptedException {
		ExecutionResult result;
		try {
			checkWorkingDirectoryContainsBallgownData(workingDirectory);

			result = this.rBinariesExecutor.runScript(
				asScriptFile(SCRIPT_TABLE_TRANSCRIPTS_SIG, "ballgown-table-genes-"),
				workingDirectory.getAbsolutePath(),
				String.valueOf(pValue));

			if (result.getExitStatus() != 0) {
				throw new ExecutionException(result.getExitStatus(),
					"Error executing script. Please, check error log.", "");
			} else {
				String fileName = exportFilteredTranscriptsTableName(pValue);
				return new File(workingDirectory, fileName);
			}
		} catch (IOException e) {
			throw new ExecutionException(1,
				"Error executing script. Please, check error log.", "");
		}
	}

	private String exportFilteredTranscriptsTableName(double pValue) {
		return "user-tables/transcript_results_sig_" + pValue + ".tsv";
	}
}
