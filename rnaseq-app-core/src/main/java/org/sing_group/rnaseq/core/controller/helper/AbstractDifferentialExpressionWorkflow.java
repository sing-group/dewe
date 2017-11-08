/*
 * #%L
 * DEWE Core
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
package org.sing_group.rnaseq.core.controller.helper;

import static java.util.stream.Collectors.joining;
import static org.sing_group.rnaseq.core.io.alignment.SamplesAlignmentStatisticsCsvWriter.write;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.log4j.FileAppender;
import org.sing_group.rnaseq.api.controller.SamtoolsController;
import org.sing_group.rnaseq.api.controller.StringTieController;
import org.sing_group.rnaseq.api.entities.FastqReadsSample;
import org.sing_group.rnaseq.api.entities.FastqReadsSamples;
import org.sing_group.rnaseq.api.entities.alignment.AlignmentStatistics;
import org.sing_group.rnaseq.api.entities.alignment.SampleAlignmentStatistics;
import org.sing_group.rnaseq.api.environment.execution.ExecutionException;
import org.sing_group.rnaseq.api.environment.execution.parameters.ImageConfigurationParameter;
import org.sing_group.rnaseq.api.persistence.entities.ReferenceGenomeIndex;
import org.sing_group.rnaseq.api.progress.OperationStatus;
import org.sing_group.rnaseq.core.controller.DefaultAppController;
import org.sing_group.rnaseq.core.entities.alignment.DefaultAlignmentStatistics;
import org.sing_group.rnaseq.core.entities.alignment.DefaultSampleAlignmentStatistics;
import org.sing_group.rnaseq.core.environment.execution.parameters.DefaultImageConfigurationParameter;
import org.sing_group.rnaseq.core.io.alignment.DefaultAlignmentLogParser;
import org.sing_group.rnaseq.core.persistence.entities.DefaultDifferentialExpressionWorkflowConfiguration;
import org.sing_group.rnaseq.core.util.LoggingUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Abstract class for a differential expression workflow involving four steps:
 * reads alignment, sam to bam conversion, StringTie and differential expression
 * analysis.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public abstract class AbstractDifferentialExpressionWorkflow {
	private static final Logger LOGGER =
		LoggerFactory.getLogger(AbstractDifferentialExpressionWorkflow.class);

	public static final ImageConfigurationParameter DEFAULT_IMAGES_CONFIGURATION = DefaultImageConfigurationParameter.DEFAULT;

	private static final String NEW_LINE = "\n";
	private static final String TAB = "\t";
	private static final String NOT_SET = "<Not set>";
	private static final String STEP_ALIGN = "Align reads";
	private static final String STEP_SAM_TO_BAM = "Converting sam to bam";
	private static final String STEP_STRINGTIE = "Running StringTie";
	private static final String STEP_DE = "Differential expression analysis";
	private static final String READ_MAPPING_STATISTICS_FILE = "read-mapping-statistics.csv";
	private static final float PROGRESS = 0.25f;

	protected ReferenceGenomeIndex referenceGenome;
	protected FastqReadsSamples reads;
	protected File referenceAnnotationFile;
	protected File workingDirectory;
	protected ImageConfigurationParameter imageConfiguration = DEFAULT_IMAGES_CONFIGURATION;
	private FileAppender workflowLogFileAppender;

	/**
	 * Creates a new {@code AbstractDifferentialExpressionWorkflow} with the
	 * specified input data.
	 *
	 * @param referenceGenome the {@code ReferenceGenomeIndex}
	 * @param reads the {@code FastqReadsSamples}
	 * @param referenceAnnotationFile the reference annotation file
	 * @param workingDirectory the working directory
	 */
	public AbstractDifferentialExpressionWorkflow(
		ReferenceGenomeIndex referenceGenome,
		FastqReadsSamples reads,
		File referenceAnnotationFile,
		File workingDirectory
	) {
		this(referenceGenome, reads, referenceAnnotationFile, workingDirectory,
			DEFAULT_IMAGES_CONFIGURATION);
	}

	/**
	/**
	 * Creates a new {@code AbstractDifferentialExpressionWorkflow} with the
	 * specified input data.
	 *
	 * @param referenceGenome the {@code ReferenceGenomeIndex}
	 * @param reads the {@code FastqReadsSamples}
	 * @param referenceAnnotationFile the reference annotation file
	 * @param workingDirectory the working directory
	 * @param imageConfiguration the {@code ImageConfigurationParameter} for
	 * 		  generating the figures
	 */
	public AbstractDifferentialExpressionWorkflow(
		ReferenceGenomeIndex referenceGenome,
		FastqReadsSamples reads,
		File referenceAnnotationFile,
		File workingDirectory,
		ImageConfigurationParameter imageConfiguration
	) {
		this.referenceGenome = referenceGenome;
		this.reads = reads;
		this.referenceAnnotationFile = referenceAnnotationFile;
		this.workingDirectory = workingDirectory;
		this.imageConfiguration = imageConfiguration;
	}

	/**
	 * Runs the analysis. The {@code OperationStatus} is used to monitor the
	 * progress.
	 *
	 * @param status a {@code OperationStatus} is used to monitor the
	 * progress
	 * @throws ExecutionException if an error occurs during the execution
	 * @throws InterruptedException if an error occurs executing the system
	 *         binary
	 */
	public void runAnalysis(OperationStatus status)
		throws ExecutionException, InterruptedException {
		createWorkflowLogger();
		writeWorkflowSummary();
		writeWorkflowConfiguration();

		alignReads(status);
		convertSamToBam(status);
		stringTie(status);
		differentialExpressionAnalysis(status);

		removeWorkflowLogger();
	}

	private void alignReads(OperationStatus status)
		throws ExecutionException, InterruptedException {
		stepLog(STEP_ALIGN);
		status.setStage(STEP_ALIGN);

		List<SampleAlignmentStatistics> statistics = new LinkedList<>();

		float stageProgress = 1f / reads.size();
		for (FastqReadsSample sample : reads) {
			status.setSubStage("Sample: " + sample.getName());

			File output = getSamFile(sample, workingDirectory);
			alignReads(sample, output);
			statistics.add(alignmentStatistics(sample.getName(), output));
			status.setStageProgress(status.getStageProgress() + stageProgress);
		}

		writeAlignmentStatistics(statistics);

		status.setSubStage("");
		status.setStageProgress(0f);

		status.setOverallProgress(status.getOverallProgress() + PROGRESS);
		stepFinishedLog(STEP_ALIGN);
	}

	private void writeAlignmentStatistics(
		List<SampleAlignmentStatistics> statistics) {
		File destFile = getAlignmentStatisticsFile(workingDirectory);
		try {
			write(statistics, destFile);
		} catch (IOException e) {
			LOGGER.error("An error ocurred writing read mapping statistics to "
				+ destFile.getAbsolutePath());
		}
	}

	private File getAlignmentStatisticsFile(File workingDirectory2) {
		return new File(workingDirectory, READ_MAPPING_STATISTICS_FILE);
	}

	private SampleAlignmentStatistics alignmentStatistics(String sampleName, File output) {
		AlignmentStatistics statistics = new DefaultAlignmentStatistics();
		DefaultAlignmentLogParser parser = new DefaultAlignmentLogParser();
		File logFile = new File(output.getAbsolutePath() + ".txt");
		try {
			parser.parseLogFile(logFile);
			statistics = parser.getAlignmentStatistics();
		} catch (IOException e) {
			LOGGER.warn("Warning: an error ocurred reading log file "
				+ logFile.getAbsolutePath() + " corresponding to sample"
				+ sampleName);
		}
		return new DefaultSampleAlignmentStatistics(sampleName, statistics);
	}

	protected abstract void alignReads(FastqReadsSample sample, File output)
		throws ExecutionException, InterruptedException;

	private void convertSamToBam(OperationStatus status
	) throws ExecutionException, InterruptedException {
		stepLog(STEP_SAM_TO_BAM);
		status.setStage(STEP_SAM_TO_BAM);

		SamtoolsController samToolsController =
			DefaultAppController.getInstance().getSamtoolsController();

		float stageProgress = 1f / reads.size();
		for (FastqReadsSample sample : reads) {
			status.setSubStage("Sample: " + sample.getName());

			File sam = getSamFile(sample, workingDirectory);
			File bam = getBamFile(sample, workingDirectory);
			samToolsController.samToBam(sam, bam);

			status.setStageProgress(status.getStageProgress() + stageProgress);
		}

		status.setSubStage("");
		status.setStageProgress(0f);

		status.setOverallProgress(status.getOverallProgress() + PROGRESS);
		stepFinishedLog(STEP_SAM_TO_BAM);
	}

	private void stringTie(OperationStatus status)
		throws ExecutionException, InterruptedException {
		stepLog(STEP_STRINGTIE);
		status.setStage(STEP_STRINGTIE);

		StringTieController stringTieController =
			DefaultAppController.getInstance().getStringTieController();

		float stagePRogress = 1f / (reads.size() * 2 + 1);

		List<File> outputTranscriptsFiles = new LinkedList<>();

		for (FastqReadsSample sample : reads) {
			status.setSubStage("Sample: " + sample.getName());

			File bam = getBamFile(sample, workingDirectory);
			File outputTranscriptsfile = getTranscriptsFile(sample, workingDirectory);

			stringTieController.obtainLabeledTranscripts(referenceAnnotationFile, bam, outputTranscriptsfile, sample.getName());
			outputTranscriptsFiles.add(outputTranscriptsfile);

			status.setStageProgress(status.getStageProgress() + stagePRogress);
		}

		status.setSubStage("Merge samples transcripts");
		File mergedAnnotationFile = getMergedTranscriptsFile(workingDirectory);
		stringTieController.mergeTranscripts(referenceAnnotationFile,
			outputTranscriptsFiles, mergedAnnotationFile);
		status.setStageProgress(status.getStageProgress() + stagePRogress);

		for (FastqReadsSample sample : reads) {
			status.setSubStage("Sample: " + sample.getName());

			File bam = getBamFile(sample, workingDirectory);
			File outputTranscriptsfile = getTranscriptsFile(sample, workingDirectory);
			stringTieController.obtainTranscripts(mergedAnnotationFile, bam,
				outputTranscriptsfile);

			status.setStageProgress(status.getStageProgress() + stagePRogress);
		}

		status.setSubStage("");
		status.setStageProgress(0f);

		status.setOverallProgress(status.getOverallProgress() + PROGRESS);
		stepFinishedLog(STEP_STRINGTIE);
	}

	private void differentialExpressionAnalysis(OperationStatus status)
		throws ExecutionException, InterruptedException {
		stepLog(STEP_DE);
		status.setStage(STEP_DE);

		performDifferentialExpressionAnalysis(status);

		status.setSubStage("");
		status.setStageProgress(0f);
		status.setOverallProgress(status.getOverallProgress() + PROGRESS);
		stepFinishedLog(STEP_DE);
	}

	protected abstract void performDifferentialExpressionAnalysis(
		OperationStatus status) throws ExecutionException, InterruptedException;

	public static File getBallgownWorkingDir(File workingDirectory) {
		return getAnalysisSubDir(getAnalysisDir(workingDirectory), "ballgown");
	}

	public static File getAnalysisDir(File workingDirectory) {
		return getAnalysisSubDir(workingDirectory, "analysis");
	}

	public static File getAnalysisSubDir(File workingDirectory, String subdir) {
		File toret = new File(workingDirectory, subdir);
		toret.mkdirs();
		return toret;
	}

	public static File getSamFile(FastqReadsSample sample, File workingDirectory) {
		File sampleWd = getSampleWorkingDir(sample, workingDirectory);
		return new File(sampleWd, sample.getName() + ".sam");
	}

	public static File getBamFile(FastqReadsSample sample, File workingDirectory) {
		File sampleWd = getSampleWorkingDir(sample, workingDirectory);
		return new File(sampleWd, sample.getName() + ".bam");
	}

	public static File getTranscriptsFile(FastqReadsSample sample, File workingDirectory) {
		File sampleWd = getSampleWorkingDir(sample, workingDirectory);
		return new File(sampleWd, sample.getName() + ".gtf");
	}

	public static File getMergedTranscriptsFile(File workingDirectory) {
		return new File(getStringTieWorkingDir(workingDirectory), "mergedAnnotationFile.gtf");
	}

	public static File getSampleWorkingDir(FastqReadsSample sample, File workingdir) {
		File outputFile = new File(workingdir, sample.getName());
		outputFile.mkdirs();
		return outputFile;
	}

	private static File getStringTieWorkingDir(File workingdir){
		File outputFile = new File(workingdir, "stringtie");
		outputFile.mkdirs();

		return outputFile;
	}

	private void writeWorkflowConfiguration() {
		File destFile = new File(workingDirectory, "workflow.dewe");
		DefaultDifferentialExpressionWorkflowConfiguration
			.persistWorkflowConfiguration(referenceGenome,
				reads, referenceAnnotationFile, workingDirectory, destFile
		);
	}

	private void writeWorkflowSummary() {
		File destFile = new File(workingDirectory, "workflow-description.txt");
		try {
			Files.write(destFile.toPath(), getWorkflowDescription().getBytes());
		} catch (IOException e) {
			LOGGER.error("An error ocurred writing the workflow configuration to "
				+ destFile.getAbsolutePath());
		}
	}

	private String getWorkflowDescription() {
		return getSummary(referenceGenome, referenceAnnotationFile,
			workingDirectory, reads);
	}

	private void createWorkflowLogger() {
		workflowLogFileAppender = LoggingUtils.createAndAddFileAppender(
			new File(workingDirectory, "run.log"),
			org.apache.log4j.Logger.getRootLogger(),
			"WorkflowFileAppender");
	}

	private void removeWorkflowLogger() {
		if (workflowLogFileAppender != null) {
			org.apache.log4j.Logger.getRootLogger()
				.removeAppender(workflowLogFileAppender);
		}
	}

	protected static final void stepLog(String message) {
		LOGGER.info("STEP: " + message);
	}

	protected static final void stepFinishedLog(String message) {
		LOGGER.info("STEP FINISHED: " + message + "\n");
	}

	/**
	 * Creates a summary containing all the provided information.
	 *
	 * @param referenceGenome the reference genome index
	 * @param referenceAnnotationFile the reference annotation file
	 * @param workingDirectory the working directory
	 * @param samples a list of {@code FastqReadsSamples}
	 *
	 * @return a summary containing all the provided information.
	 */
	public static String getSummary(ReferenceGenomeIndex referenceGenome,
		File referenceAnnotationFile, File workingDirectory,
		FastqReadsSamples samples
	) {
		StringBuilder sb = new StringBuilder();
		sb
			.append("Workflow configuration:")
			.append(NEW_LINE)
			.append(TAB)
			.append("- Reference genome index: ")
			.append(referenceGenome.getReferenceGenomeIndex())
			.append(NEW_LINE)
			.append(TAB)
			.append("- Reference annotation file: ")
			.append(referenceAnnotationFile.getAbsolutePath())
			.append(NEW_LINE)
			.append(TAB)
			.append("- Working directory: ")
			.append(workingDirectory.getAbsolutePath())
			.append(NEW_LINE)
			.append(TAB)
			.append("- Conditions: ")
			.append(getConditions(samples).stream().collect(joining(", ")))
			.append(NEW_LINE)
			.append(NEW_LINE)
			.append("Experiment samples: ")
			.append(NEW_LINE);

		sb.append(getSamplesSummary(samples, false));

		return sb.toString();
	}

	/**
	 * Creates a summary with the specified list of samples.
	 *
	 * @param samples a list of {@code FastqReadsSamples}
	 * @param forceShowReadsFile2 whether reads file 2 value must be shown even
	 * 		  when it may be not present (single-end sample) 
	 *
	 * @return a summary with the specified samples
	 */
	public static String getSamplesSummary(FastqReadsSamples samples,
		boolean forceShowReadsFile2
	) {
		StringBuilder sb = new StringBuilder();
		samples.forEach(s -> {
			sb
				.append(TAB)
				.append("- Sample name: ")
				.append(getSampleName(s))
				.append(" [Condition: ")
				.append(getSampleCondition(s) + "]")
				.append(NEW_LINE)
				.append(TAB)
				.append(TAB)
				.append("Reads file 1 file: ")
				.append(getSampleReadsFile1(s))
				.append(NEW_LINE);

			if (forceShowReadsFile2 || s.isPairedEnd()) {
				sb
					.append(TAB)
					.append(TAB)
					.append("Reads file 2 file: ")
					.append(getSampleReadsFile2(s))
					.append(NEW_LINE);
			}
		});
		return sb.toString();
	}

	private static String getSampleName(FastqReadsSample s) {
		return s.getName() != null && !s.getName().isEmpty() ? s.getName()
			: NOT_SET;
	}

	private static String getSampleCondition(FastqReadsSample s) {
		return s.getCondition() != null ? s.getCondition() : NOT_SET;
	}

	private static String getSampleReadsFile1(FastqReadsSample s) {
		return s.getReadsFile1() != null ? s.getReadsFile1().getName()
			: NOT_SET;
	}

	private static String getSampleReadsFile2(FastqReadsSample s) {
		return s.getReadsFile2().isPresent() ? s.getReadsFile2().get().getName()
			: NOT_SET;
	}

	private static Set<String> getConditions(FastqReadsSamples samples) {
		return samples.stream().map(FastqReadsSample::getCondition)
			.collect(Collectors.toSet());
	}
}
