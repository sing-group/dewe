package org.sing_group.rnaseq.core.controller.helper;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import org.sing_group.rnaseq.api.controller.SamtoolsController;
import org.sing_group.rnaseq.api.controller.StringTieController;
import org.sing_group.rnaseq.api.entities.FastqReadsSample;
import org.sing_group.rnaseq.api.entities.FastqReadsSamples;
import org.sing_group.rnaseq.api.environment.execution.ExecutionException;
import org.sing_group.rnaseq.api.persistence.entities.ReferenceGenome;
import org.sing_group.rnaseq.api.progress.OperationStatus;
import org.sing_group.rnaseq.core.controller.DefaultAppController;

public abstract class AbstractDifferentialExpressionWorkflow {
	private static final float PROGRESS = 0.25f;

	protected ReferenceGenome referenceGenome;
	protected FastqReadsSamples reads;
	protected File referenceAnnotationFile;
	protected File workingDirectory;

	public AbstractDifferentialExpressionWorkflow(
		ReferenceGenome referenceGenome,
		FastqReadsSamples reads, 
		File referenceAnnotationFile,
		File workingDirectory
	) {
		this.referenceGenome = referenceGenome;
		this.reads = reads;
		this.referenceAnnotationFile = referenceAnnotationFile;
		this.workingDirectory = workingDirectory;
	}
	
	public void runAnalysis(OperationStatus status)
		throws ExecutionException, InterruptedException {
		alignReads(status);
		convertSamToBam(status);
		stringTie(status);
		differentialExpressionAnalysis(status);
	}

	private void alignReads(OperationStatus status)
		throws ExecutionException, InterruptedException {
		status.setStage("Align reads");
		
		float subtaskProgress = 1f / reads.size();
		for (FastqReadsSample sample : reads) {
			status.setSubtask("Sample: " + sample.getName());

			File output = getSamFile(sample, workingDirectory);
			alignReads(sample.getReadsFile1(), sample.getReadsFile2(), output);
			status.setSubtaskProgress(status.getSubtaskProgress() + subtaskProgress);
		}

		status.setSubtask("");
		status.setSubtaskProgress(0f);

		status.setTotal(status.getTotal() + PROGRESS);
	}

	protected abstract void alignReads(File readsFile1, File readsFile2,
		File output) throws ExecutionException, InterruptedException;

	private void convertSamToBam(OperationStatus status
	) throws ExecutionException, InterruptedException {
		status.setStage("Converting sam to bam");
		
		SamtoolsController samToolsController =
			DefaultAppController.getInstance().getSamtoolsController();
		
		float subtaskProgress = 1f / reads.size();
		for (FastqReadsSample sample : reads) {
			status.setSubtask("Sample: " + sample.getName());

			File sam = getSamFile(sample, workingDirectory);
			File bam = getBamFile(sample, workingDirectory);
			samToolsController.samToBam(sam, bam);
			
			status.setSubtaskProgress(status.getSubtaskProgress() + subtaskProgress);
		}

		status.setSubtask("");
		status.setSubtaskProgress(0f);

		status.setTotal(status.getTotal() + PROGRESS);
	}

	private void stringTie(OperationStatus status)
		throws ExecutionException, InterruptedException {
		status.setStage("Running StringTie");
		
		StringTieController stringTieController =
			DefaultAppController.getInstance().getStringTieController();
		
		float subtaskProgress = 1f / (reads.size() * 2 + 1);
		
		List<File> outputTranscriptsFiles = new LinkedList<>();
		
		for (FastqReadsSample sample : reads) {
			status.setSubtask("Sample: " + sample.getName());
			
			File bam = getBamFile(sample, workingDirectory);
			File outputTranscriptsfile = getTranscriptsFile(sample, workingDirectory);
			
			stringTieController.obtainLabeledTranscripts(referenceAnnotationFile, bam, outputTranscriptsfile, sample.getName());
			outputTranscriptsFiles.add(outputTranscriptsfile);

			status.setSubtaskProgress(status.getSubtaskProgress() + subtaskProgress);
		}

		status.setSubtask("Merge samples transcripts");
		File mergedAnnotationFile = getMergedTranscriptsFile(workingDirectory);
		stringTieController.mergeTranscripts(referenceAnnotationFile,
			outputTranscriptsFiles, mergedAnnotationFile);
		status.setSubtaskProgress(status.getSubtaskProgress() + subtaskProgress);
		
		for (FastqReadsSample sample : reads) {
			status.setSubtask("Sample: " + sample.getName());
			
			File bam = getBamFile(sample, workingDirectory);
			File outputTranscriptsfile = getTranscriptsFile(sample, workingDirectory);
			stringTieController.obtainTranscripts(mergedAnnotationFile, bam,
				outputTranscriptsfile);
			
			status.setSubtaskProgress(status.getSubtaskProgress() + subtaskProgress);
		}
		
		status.setSubtask("");
		status.setSubtaskProgress(0f);

		status.setTotal(status.getTotal() + PROGRESS);
	}

	private void differentialExpressionAnalysis(OperationStatus status)
		throws ExecutionException, InterruptedException {
		status.setStage("Differential expression analysis");
		
		performDifferentialExpressionAnalysis(status);
		
		status.setSubtask("");
		status.setSubtaskProgress(0f);
		status.setTotal(status.getTotal() + PROGRESS);
	}

	protected abstract void performDifferentialExpressionAnalysis(
		OperationStatus status) throws ExecutionException, InterruptedException;

	protected static File getBallgownWorkingDir(File workingDirectory) {
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
}
