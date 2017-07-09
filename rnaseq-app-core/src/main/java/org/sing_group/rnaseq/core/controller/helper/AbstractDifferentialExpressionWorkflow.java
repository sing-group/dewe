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
import org.sing_group.rnaseq.core.io.alignment.DefaultAlignmentLogParser;
import org.sing_group.rnaseq.core.util.LoggingUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * Abstract class for a differential expression workflow involving four steps:
 * reads alignment, sam to bam conversion, StringTie and differential expression
 * analysis.
 * </p>
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public abstract class AbstractDifferentialExpressionWorkflow {
	private static final Logger LOGGER =
		LoggerFactory.getLogger(AbstractDifferentialExpressionWorkflow.class);
	public static final ImageConfigurationParameter DEFAULT_IMAGES_CONFIGURATION = null;
	private static final String NEW_LINE = "\n";
	private static final String TAB = "\t";
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
	protected ImageConfigurationParameter imageConfiguration;
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
			alignReads(sample.getReadsFile1(), sample.getReadsFile2(), output);
			statistics.add(alignmentStatistics(sample.getName(), output));
			status.setStageProgress(status.getStageProgress() + stageProgress);
		}

		writeAlignmentStatistics(statistics);

		status.setSubStage("");
		status.setStageProgress(0f);

		status.setOverallProgress(status.getOverallProgress() + PROGRESS);
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

	protected abstract void alignReads(File readsFile1, File readsFile2,
		File output) throws ExecutionException, InterruptedException;

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
	}

	private void differentialExpressionAnalysis(OperationStatus status)
		throws ExecutionException, InterruptedException {
		stepLog(STEP_DE);
		status.setStage(STEP_DE);

		performDifferentialExpressionAnalysis(status);

		status.setSubStage("");
		status.setStageProgress(0f);
		status.setOverallProgress(status.getOverallProgress() + PROGRESS);
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
		LOGGER.info("STEP:" + message);
	}

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

		samples.forEach(s -> {
			sb
				.append(TAB)
				.append("- Sample name: ")
				.append(s.getName())
				.append(" [Condition: ")
				.append(s.getCondition() + "]")
				.append(NEW_LINE)
				.append(TAB)
				.append(TAB)
				.append("Mate 1 file: ")
				.append(s.getReadsFile1().getName())
				.append(NEW_LINE)
				.append(TAB)
				.append(TAB)
				.append("Mate 2 file: ")
				.append(s.getReadsFile2().getName())
				.append(NEW_LINE);
		});
		return sb.toString();
	}

	private static Set<String> getConditions(FastqReadsSamples samples) {
		return samples.stream().map(FastqReadsSample::getCondition)
			.collect(Collectors.toSet());
	}
}
