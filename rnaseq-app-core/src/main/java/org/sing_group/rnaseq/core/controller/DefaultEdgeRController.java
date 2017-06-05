package org.sing_group.rnaseq.core.controller;

import static org.sing_group.rnaseq.core.environment.execution.DefaultRBinariesExecutor.asScriptFile;
import static org.sing_group.rnaseq.core.environment.execution.DefaultRBinariesExecutor.asString;
import static org.sing_group.rnaseq.core.io.edger.GtfParser.writeGeneIdToGeneNameMappings;
import static org.sing_group.rnaseq.core.util.FileUtils.contains;

import java.io.File;
import java.io.IOException;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.sing_group.rnaseq.api.controller.EdgeRController;
import org.sing_group.rnaseq.api.entities.edger.EdgeRSample;
import org.sing_group.rnaseq.api.entities.edger.EdgeRSamples;
import org.sing_group.rnaseq.api.environment.execution.ExecutionException;
import org.sing_group.rnaseq.api.environment.execution.ExecutionResult;
import org.sing_group.rnaseq.api.environment.execution.RBinariesExecutor;

/**
 * The default {@code EdgeRController} implementation.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class DefaultEdgeRController implements EdgeRController {
	public static final String GENE_MAPPING_FILE = "GeneID_to_GeneName.txt";
	public static final String READS_COUNT_FILE = "gene_read_counts_table_all.tsv";
	public static final String OUTPUT_FILE_DE_GENES = "DE_genes.txt";

	private static final Collector<CharSequence, ?, String> JOINING =
		Collectors.joining(" ");
	private static final String SCRIPT_DE_ANALYSIS = asString(
		DefaultEdgeRController.class.getResourceAsStream(
			"/scripts/edgeR-differential-expression.R")
		);

	private RBinariesExecutor rBinariesExecutor;

	@Override
	public void setRBinariesExecutor(RBinariesExecutor executor) {
		this.rBinariesExecutor = executor;
	}

  	@Override
	public void differentialExpression(File workingDir)
		throws ExecutionException, InterruptedException {
  		ExecutionResult result;
  		try {
			checkWorkingDir(workingDir);
  			result = this.rBinariesExecutor.runScript(
				asScriptFile(SCRIPT_DE_ANALYSIS, "edgeR-analysis-"),
				workingDir.getAbsolutePath());

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
		if (!contains(workingDir, GENE_MAPPING_FILE) ||
			!contains(workingDir, READS_COUNT_FILE)
		) {
			throw new ExecutionException(1,
				"Error executing script. Please, check error log.", "");
		}
	}

	@Override
	public void differentialExpression(EdgeRSamples samples,
		File referenceAnnotationFile, File workingDir)
		throws ExecutionException, InterruptedException {
		File geneReadsFile 		= new File(workingDir, READS_COUNT_FILE);
		File geneMappingFile	= new File(workingDir, GENE_MAPPING_FILE);

		File htseqDir = getHtseqDirectory(workingDir);
		DefaultAppController.getInstance().getHtseqController()
			.countBamReverseExon(referenceAnnotationFile, getBamFiles(samples),
				htseqDir, geneReadsFile);
		DefaultAppController.getInstance().getSystemController()
			.sed("-i", getSamplesRow(samples), geneReadsFile.getAbsolutePath());
		DefaultAppController.getInstance().getSystemController()
			.sed("-i", getClassesRow(samples), geneReadsFile.getAbsolutePath());

		geneIdToGeneNameMappings(referenceAnnotationFile, geneMappingFile);

		differentialExpression(workingDir);
	}

	public static void geneIdToGeneNameMappings(final File referenceAnnotationFile,
		final File geneMappingFile) throws ExecutionException {
		try {
			writeGeneIdToGeneNameMappings(referenceAnnotationFile,
				geneMappingFile);
		} catch (final IOException e) {
			throw new ExecutionException(1,
				"Error extracting gene symbols. Please, check error log.", "");
		}
	}

	private File getHtseqDirectory(File workingDir) {
		File htseqDirectory = new File(workingDir, "htseq-count");
		htseqDirectory.mkdirs();
		return htseqDirectory;
	}

	private File[] getBamFiles(EdgeRSamples samples) {
		File[] bamFiles = new File[samples.size()];
		for (int i = 0; i < samples.size(); i++) {
			bamFiles[i] = samples.get(i).getFile();
		}
		return bamFiles;
	}

	private String getSamplesRow(EdgeRSamples samples) {
		StringBuilder sb = new StringBuilder();
		sb.append("1igene ");
		sb.append(samples.stream().map(EdgeRSample::getName).collect(JOINING));
		return sb.toString();
	}

	private String getClassesRow(EdgeRSamples samples) {
		StringBuilder sb = new StringBuilder();
		sb.append("$ a _class ");
		sb.append(samples.stream().map(EdgeRSample::getType).collect(JOINING));
		return sb.toString();
	}
}
