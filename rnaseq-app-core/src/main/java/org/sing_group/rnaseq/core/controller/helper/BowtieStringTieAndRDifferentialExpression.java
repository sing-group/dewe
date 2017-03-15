package org.sing_group.rnaseq.core.controller.helper;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.sing_group.rnaseq.api.controller.Bowtie2Controller;
import org.sing_group.rnaseq.api.controller.SamtoolsController;
import org.sing_group.rnaseq.api.controller.StringTieController;
import org.sing_group.rnaseq.api.entities.FastqReadsSample;
import org.sing_group.rnaseq.api.entities.FastqReadsSamples;
import org.sing_group.rnaseq.api.entities.ballgown.BallgownSamples;
import org.sing_group.rnaseq.api.entities.edger.EdgeRSamples;
import org.sing_group.rnaseq.api.environment.execution.ExecutionException;
import org.sing_group.rnaseq.api.persistence.entities.Bowtie2ReferenceGenome;
import org.sing_group.rnaseq.api.progress.OperationStatus;
import org.sing_group.rnaseq.core.controller.DefaultAppController;
import org.sing_group.rnaseq.core.entities.ballgown.DefaultBallgownSample;
import org.sing_group.rnaseq.core.entities.ballgown.DefaultBallgownSamples;
import org.sing_group.rnaseq.core.entities.edgeR.DefaultEdgeRSample;
import org.sing_group.rnaseq.core.entities.edgeR.DefaultEdgeRSamples;

public class BowtieStringTieAndRDifferentialExpression {
	private static final float PROGRESS = 0.25f;

	public static void runAnalysis(
		Bowtie2ReferenceGenome referenceGenome,
		FastqReadsSamples reads, 
		File referenceAnnotationFile,
		File workingDirectory, 
		OperationStatus status
	) throws ExecutionException, InterruptedException {
		alignReads(referenceGenome, reads, workingDirectory, status);
		convertSamToBam(reads, workingDirectory, status);
		stringTie(reads, referenceAnnotationFile, workingDirectory, status);
		differentialExpressionAnalysis(referenceGenome, reads,
			referenceAnnotationFile, workingDirectory, status);
	}

	private static void alignReads(Bowtie2ReferenceGenome referenceGenome,
		FastqReadsSamples reads, File workingDirectory,
		OperationStatus status
	) throws ExecutionException, InterruptedException {
		status.setStage("Align reads");
		
		Bowtie2Controller bowtie2Controller =
			DefaultAppController.getInstance().getBowtie2Controller();

		float subtaskProgress = 1f / reads.size();
		for (FastqReadsSample sample : reads) {
			status.setSubtask("Sample: " + sample.getName());

			File output = getSamFile(sample, workingDirectory);
			bowtie2Controller.alignReads(
				referenceGenome, 
				sample.getReadsFile1(), 
				sample.getReadsFile2(), 
				output
			);
			status.setSubtaskProgress(status.getSubtaskProgress() + subtaskProgress);
		}

		status.setSubtask("");
		status.setSubtaskProgress(0f);

		status.setTotal(status.getTotal() + PROGRESS);
	}

	private static void convertSamToBam(FastqReadsSamples reads, File workingDirectory,
		OperationStatus status
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

	private static void stringTie(FastqReadsSamples reads,
		File referenceAnnotationFile, File workingDirectory,
		OperationStatus status
	) throws ExecutionException, InterruptedException {
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

	private static void differentialExpressionAnalysis(
		Bowtie2ReferenceGenome referenceGenome, FastqReadsSamples reads,
		File referenceAnnotationFile, File workingDirectory,
		OperationStatus status
	) throws ExecutionException, InterruptedException {
		status.setStage("Differential expression analysis");
		
		float subtaskProgress = 0.5f;

		edgeRDifferentialExpressionAnalysis(reads, referenceAnnotationFile, workingDirectory, status);
		status.setTotal(status.getTotal() + PROGRESS);
		status.setSubtaskProgress(status.getSubtaskProgress() + subtaskProgress);
		
		ballgownDifferentialExpressionAnalysis(reads, referenceAnnotationFile, workingDirectory, status);
		
		status.setSubtask("");
		status.setSubtaskProgress(0f);
	}
	
	private static void edgeRDifferentialExpressionAnalysis(FastqReadsSamples reads,
		File referenceAnnotationFile, File workingDirectory,
		OperationStatus status
	) throws ExecutionException, InterruptedException {
		status.setSubtask("EdgeR");
		File edgeRWorkingDir = getEdgeRWorkingDir(workingDirectory);
		EdgeRSamples edgeRSamples = getEdgeRSamples(reads, workingDirectory);
		DefaultAppController.getInstance().getEdgeRController()
			.differentialExpression(edgeRSamples, referenceAnnotationFile, edgeRWorkingDir);
	}

	private static EdgeRSamples getEdgeRSamples(FastqReadsSamples reads,
		File workingDirectory
	) {
		return new DefaultEdgeRSamples(
			reads.stream().map(r -> {
				String name = r.getName();
				String type = r.getCondition();
				File bam = getBamFile(r, workingDirectory);
				return new DefaultEdgeRSample(name, type, bam);
			}).collect(Collectors.toList())
		);
	}

	private static File getEdgeRWorkingDir(File workingDirectory) {
		return getAnalysisSubDir(getAnalysisDir(workingDirectory), "edger");
	}

	private static File getBallgownWorkingDir(File workingDirectory) {
		return getAnalysisSubDir(getAnalysisDir(workingDirectory), "ballgown");
	}

	private static File getAnalysisDir(File workingDirectory) {
		return getAnalysisSubDir(workingDirectory, "analysis");
	}

	private static File getAnalysisSubDir(File workingDirectory, String subdir) {
		File toret = new File(workingDirectory, subdir);
		toret.mkdirs();
		return toret;
	}

	private static void ballgownDifferentialExpressionAnalysis(
		FastqReadsSamples reads,
		File referenceAnnotationFile, 
		File workingDirectory,
		OperationStatus status
	) throws ExecutionException, InterruptedException {
		status.setSubtask("Ballgown");
		File ballgownWorkingDir = getBallgownWorkingDir(workingDirectory);
		BallgownSamples samples = getBallgownSamples(reads, workingDirectory);
		DefaultAppController.getInstance().getBallgownController()
			.differentialExpression(samples, ballgownWorkingDir);
	}

	private static BallgownSamples getBallgownSamples(FastqReadsSamples reads,
		File workingDirectory
	) {
		return new DefaultBallgownSamples(
				reads.stream().map(r -> {
					String name = r.getName();
					String type = r.getCondition();
					File bam = getSampleWorkingDir(r, workingDirectory);
					return new DefaultBallgownSample(name, type, bam);
				}).collect(Collectors.toList())
			);
	}

	private static File getSamFile(FastqReadsSample sample, File workingDirectory) {
		File sampleWd = getSampleWorkingDir(sample, workingDirectory);
		return new File(sampleWd, sample.getName() + ".sam");
	}

	private static File getBamFile(FastqReadsSample sample, File workingDirectory) {
		File sampleWd = getSampleWorkingDir(sample, workingDirectory);
		return new File(sampleWd, sample.getName() + ".bam");
	}

	private static File getTranscriptsFile(FastqReadsSample sample, File workingDirectory) {
		File sampleWd = getSampleWorkingDir(sample, workingDirectory);
		return new File(sampleWd, sample.getName() + ".gtf");
	}
	
	private static File getMergedTranscriptsFile(File workingDirectory) {
		return new File(getStringTieWorkingDir(workingDirectory), "mergedAnnotationFile.gtf");
	}

	private static File getSampleWorkingDir(FastqReadsSample sample, File workingdir) {
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
