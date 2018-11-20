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

import static java.util.Arrays.asList;
import static org.sing_group.rnaseq.core.controller.DefaultBallgownController.OUTPUT_BALLGOWN_R_DATA;
import static org.sing_group.rnaseq.core.controller.DefaultBallgownController.OUTPUT_FILE_GENES;
import static org.sing_group.rnaseq.core.controller.DefaultBallgownController.OUTPUT_FILE_GENES_FILTERED;
import static org.sing_group.rnaseq.core.controller.DefaultBallgownController.OUTPUT_FILE_GENES_FILTERED_SIGNIFICANT;
import static org.sing_group.rnaseq.core.controller.DefaultBallgownController.OUTPUT_FILE_PHENOTYPE;
import static org.sing_group.rnaseq.core.controller.DefaultBallgownController.OUTPUT_FILE_TRANSCRIPTS;
import static org.sing_group.rnaseq.core.controller.DefaultBallgownController.OUTPUT_FILE_TRANSCRIPTS_FILTERED;
import static org.sing_group.rnaseq.core.controller.DefaultBallgownController.OUTPUT_FILE_TRANSCRIPTS_FILTERED_SIGNIFICANT;
import static org.sing_group.rnaseq.core.io.ballgown.BallgownPhenotypeDataCsvLoader.loadSampleNames;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.sing_group.rnaseq.api.controller.BallgownWorkingDirectoryController;
import org.sing_group.rnaseq.api.entities.ballgown.BallgownGenes;
import org.sing_group.rnaseq.api.entities.ballgown.BallgownTranscripts;
import org.sing_group.rnaseq.api.environment.execution.ExecutionException;
import org.sing_group.rnaseq.api.environment.execution.parameters.ImageConfigurationParameter;
import org.sing_group.rnaseq.core.io.ballgown.BallgownGenesCsvFileLoader;
import org.sing_group.rnaseq.core.io.ballgown.BallgownTranscriptsCsvFileLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The default {@code BallgownWorkingDirectoryController} implementation.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class DefaultBallgownWorkingDirectoryController
	implements BallgownWorkingDirectoryController {
	private static final Logger LOGGER = LoggerFactory.getLogger(
		DefaultBallgownWorkingDirectoryController.class);
	private static final List<String> VIEWABLE_WORKING_DIR_FILES = asList(
		OUTPUT_FILE_GENES, OUTPUT_FILE_GENES_FILTERED,
		OUTPUT_FILE_GENES_FILTERED_SIGNIFICANT, OUTPUT_FILE_TRANSCRIPTS,
		OUTPUT_FILE_TRANSCRIPTS_FILTERED,
		OUTPUT_FILE_TRANSCRIPTS_FILTERED_SIGNIFICANT);

	private File workingDirectory;

	/**
	 * Creates a new {@code DefaultBallgownWorkingDirectoryController} for the
	 * specified directory.
	 *
	 * @param workingDirectory the working directory to be controlled
	 */
	public DefaultBallgownWorkingDirectoryController(File workingDirectory) {
		this.workingDirectory = workingDirectory;
	}

	@Override
	public boolean isBallgownStructurePresent() {
		return getWorkingDirFile(OUTPUT_BALLGOWN_R_DATA).exists();
	}

	@Override
	public boolean isPhenotypeFilePresent() {
		return getWorkingDirFile(OUTPUT_FILE_PHENOTYPE).exists();
	}

	@Override
	public Optional<List<String>> getSampleNames() {
		return Optional.ofNullable(loadSampleNamesFromFile());
	}

	private List<String> loadSampleNamesFromFile() {
		File phenoData = getWorkingDirFile(OUTPUT_FILE_PHENOTYPE);
		List<String> sampleNames = null;
		if (phenoData.exists()) {
			try {
				sampleNames = loadSampleNames(phenoData);
			} catch (IOException e) {
				LOGGER.warn("I/O error loading genes file "
					+ phenoData.getAbsolutePath());
			}
		}
		return sampleNames;
	}

	@Override
	public Optional<BallgownGenes> getGenes() {
		return loadGenes(OUTPUT_FILE_GENES);
	}

	@Override
	public Optional<BallgownGenes> getFilteredGenes() {
		return loadGenes(OUTPUT_FILE_GENES_FILTERED);
	}

	@Override
	public Optional<BallgownGenes> getSignificantFilteredGenes() {
		return loadGenes(OUTPUT_FILE_GENES_FILTERED_SIGNIFICANT);
	}

	private Optional<BallgownGenes> loadGenes(String genesFileName) {
		File genesFile = getWorkingDirFile(genesFileName);
		BallgownGenes genes = null;
		if (genesFile.exists()) {
			try {
				genes = BallgownGenesCsvFileLoader.loadFile(genesFile);
			} catch (IOException e) {
				LOGGER.warn("I/O error loading genes file "
					+ genesFile.getAbsolutePath());
			}
		}

		return Optional.ofNullable(genes);
	}

	@Override
	public Optional<BallgownTranscripts> getTranscripts() {
		return loadTranscripts(OUTPUT_FILE_TRANSCRIPTS);
	}

	@Override
	public Optional<BallgownTranscripts> getFilteredTranscripts() {
		return loadTranscripts(OUTPUT_FILE_TRANSCRIPTS_FILTERED);
	}

	@Override
	public Optional<BallgownTranscripts> getSignificantFilteredTranscripts() {
		return loadTranscripts(OUTPUT_FILE_TRANSCRIPTS_FILTERED_SIGNIFICANT);
	}

	private Optional<BallgownTranscripts> loadTranscripts(
		String transcriptsFileName
	) {
		File transcriptsFile = getWorkingDirFile(transcriptsFileName);
		BallgownTranscripts transcripts = null;
		if (transcriptsFile.exists()) {
			try {
				transcripts = BallgownTranscriptsCsvFileLoader
					.loadFile(transcriptsFile);
			} catch (IOException e) {
				LOGGER.warn("I/O error loading genes file "
					+ transcriptsFile.getAbsolutePath());
			}
		}

		return Optional.ofNullable(transcripts);
	}

	protected File getWorkingDirFile(String file) {
		return new File(workingDirectory, file);
	}

	@Override
	public void createFpkmDistributionFigureForTranscript(String transcriptId,
		ImageConfigurationParameter configuration
	) throws ExecutionException, InterruptedException {

		DefaultAppController.getInstance().getBallgownController()
			.createFpkmDistributionFigureForTranscript(this.workingDirectory,
				transcriptId, configuration);
	}

	@Override
	public void createExpressionLevelsFigure(String transcriptId,
		String sampleName, ImageConfigurationParameter configuration)
		throws ExecutionException, InterruptedException {

		DefaultAppController.getInstance().getBallgownController()
			.createExpressionLevelsFigure(this.workingDirectory, transcriptId,
				sampleName, configuration);
	}

	@Override
	public File exportFilteredGenesTable(double pValue)
		throws ExecutionException, InterruptedException {
		return DefaultAppController.getInstance().getBallgownController()
			.exportFilteredGenesTable(this.workingDirectory, pValue);
	}

	@Override
	public File exportFilteredTranscriptsTable(double pValue)
		throws ExecutionException, InterruptedException {
		return DefaultAppController.getInstance().getBallgownController()
			.exportFilteredTranscriptsTable(this.workingDirectory, pValue);
	}

	@Override
	public void createFpkmDistributionAcrossSamplesFigure(
		ImageConfigurationParameter imageConfiguration)
		throws ExecutionException, InterruptedException {
		DefaultAppController.getInstance().getBallgownController()
			.createFpkmDistributionAcrossSamplesFigure(workingDirectory,
				imageConfiguration);
	}

	@Override
	public void createGenesDEpValuesFigure(
		ImageConfigurationParameter imageConfiguration)
		throws ExecutionException, InterruptedException {
		DefaultAppController.getInstance().getBallgownController()
			.createGenesDEpValuesFigure(workingDirectory, imageConfiguration);
	}

	@Override
	public void createTranscriptsDEpValuesFigure(
		ImageConfigurationParameter imageConfiguration)
		throws ExecutionException, InterruptedException {
		DefaultAppController.getInstance().getBallgownController()
			.createTranscriptsDEpValuesFigure(workingDirectory,
				imageConfiguration);
	}

	@Override
	public List<String> getMissingWorkingDirectoryFiles() {
		List<String> missingFiles = new LinkedList<>();

		for (String fileName : VIEWABLE_WORKING_DIR_FILES) {
			File file = getWorkingDirFile(fileName);
			if (!file.exists()) {
				missingFiles.add(fileName);
			}
		}

		return missingFiles;
	}

	@Override
	public void createDEfoldChangeValuesDistributionFigure(
		ImageConfigurationParameter imageConfiguration)
		throws ExecutionException, InterruptedException {
		DefaultAppController.getInstance().getBallgownController()
			.createDEfoldChangeValuesDistributionFigure(
				workingDirectory, imageConfiguration);
	}

	@Override
	public void createVolcanoFigure(
		ImageConfigurationParameter imageConfiguration
	) throws ExecutionException, InterruptedException {
		DefaultAppController.getInstance().getBallgownController()
			.createVolcanoFigure(workingDirectory, imageConfiguration);
	}

	@Override
	public void createFpkmConditionsCorrelationFigure(
		ImageConfigurationParameter imageConfiguration)
		throws ExecutionException, InterruptedException {
		DefaultAppController.getInstance().getBallgownController()
			.createFpkmConditionsCorrelationFigure(
				workingDirectory, imageConfiguration);
	}

	@Override
	public void createFpkmConditionsDensityFigure(
		ImageConfigurationParameter imageConfiguration)
		throws ExecutionException, InterruptedException {
		DefaultAppController.getInstance().getBallgownController()
			.createFpkmConditionsDensityFigure(
				workingDirectory, imageConfiguration);
	}

	@Override
	public void createPcaFigure(ImageConfigurationParameter imageConfiguration)
		throws ExecutionException, InterruptedException {
		DefaultAppController.getInstance().getBallgownController()
			.createPcaFigure(workingDirectory, imageConfiguration);
	}

	@Override
	public void createHeatmapFigure(
		ImageConfigurationParameter imageConfiguration, int numClusters, double logFC)
		throws ExecutionException, InterruptedException {
		DefaultAppController.getInstance().getBallgownController()
			.createHeatmapFigure(
				workingDirectory, imageConfiguration, numClusters, logFC);
	}
}
