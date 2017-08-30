package org.sing_group.rnaseq.core.controller.helper;

import static org.sing_group.rnaseq.core.controller.helper.AbstractDifferentialExpressionWorkflow.getAnalysisDir;
import static org.sing_group.rnaseq.core.controller.helper.AbstractDifferentialExpressionWorkflow.getAnalysisSubDir;
import static org.sing_group.rnaseq.core.controller.helper.AbstractDifferentialExpressionWorkflow.getBamFile;

import java.io.File;
import java.util.stream.Collectors;

import org.sing_group.rnaseq.api.entities.FastqReadsSamples;
import org.sing_group.rnaseq.api.entities.edger.EdgeRSamples;
import org.sing_group.rnaseq.api.environment.execution.ExecutionException;
import org.sing_group.rnaseq.core.controller.DefaultAppController;
import org.sing_group.rnaseq.core.entities.edgeR.DefaultEdgeRSample;
import org.sing_group.rnaseq.core.entities.edgeR.DefaultEdgeRSamples;

/**
 * A class to encapsulate the execution of edgeR differential expression
 * analyses.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class EdgeRDifferentialExpressionAnalysis {

	/**
	 * <p>
	 * Performs the differential expression analysis between the groups of the
	 * samples in the list and stores the results in {@code workingDirectory}.
	 * Note that there must be only two conditions and at least two samples in
	 * each one.
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
	 * @param reads the list of input {@code FastqReadsSample}s
	 * @param referenceAnnotationFile the reference annotation file
	 * @param workingDirectory the directory where results must be stored
	 * @throws ExecutionException if an error occurs during the execution
	 * @throws InterruptedException if an error occurs executing the system
	 *         binary
	 */
	public static void edgeRDifferentialExpressionAnalysis(
		FastqReadsSamples reads,
		File referenceAnnotationFile,
		File workingDirectory
	) throws ExecutionException, InterruptedException {
		File edgeRWorkingDir = getEdgeRWorkingDir(workingDirectory);
		EdgeRSamples edgeRSamples = getEdgeRSamples(reads, workingDirectory);

		DefaultAppController.getInstance().getEdgeRController()
			.differentialExpression(edgeRSamples, referenceAnnotationFile,
				edgeRWorkingDir);
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

	/**
	 * Returns the edgeR location in a given working directory.
	 *
	 * @param workingDirectory the working directory where the edgeR working
	 *        directory must be located.
	 * @return the edgeR location in a given working directory.
	 */
	public static File getEdgeRWorkingDir(File workingDirectory) {
		return getAnalysisSubDir(getAnalysisDir(workingDirectory), "edger");
	}
}
