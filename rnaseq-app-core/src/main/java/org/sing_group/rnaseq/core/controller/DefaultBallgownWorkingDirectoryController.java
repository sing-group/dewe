package org.sing_group.rnaseq.core.controller;

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

	private File workingDirectory;

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
				transcriptId, configuration.getFormat().getExtension(),
				configuration.getWidth(), configuration.getHeight(), configuration.isColored());
	}

	@Override
	public void createExpressionLevelsFigure(String transcriptId,
		String sampleName, ImageConfigurationParameter configuration)
		throws ExecutionException, InterruptedException {

		DefaultAppController.getInstance().getBallgownController()
			.createExpressionLevelsFigure(this.workingDirectory, transcriptId,
				sampleName, configuration.getFormat().getExtension(),
				configuration.getWidth(), configuration.getHeight(), configuration.isColored());
	}

	@Override
	public void exportFilteredGenesTable(double pValue)
		throws ExecutionException, InterruptedException {
		DefaultAppController.getInstance().getBallgownController()
			.exportFilteredGenesTable(this.workingDirectory, pValue);
	}

	@Override
	public void exportFilteredTranscriptsTable(double pValue)
		throws ExecutionException, InterruptedException {
		DefaultAppController.getInstance().getBallgownController()
			.exportFilteredTranscriptsTable(this.workingDirectory, pValue);
	}
}
