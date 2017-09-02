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
package org.sing_group.rnaseq.core.controller;

import static org.sing_group.rnaseq.core.environment.execution.DefaultRBinariesExecutor.asScriptFile;
import static org.sing_group.rnaseq.core.environment.execution.DefaultRBinariesExecutor.asString;
import static org.sing_group.rnaseq.core.io.edger.GtfParser.writeGeneIdToGeneNameMappings;
import static org.sing_group.rnaseq.core.util.FileUtils.contains;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
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
		File geneMappingFile	= new File(workingDir, GENE_MAPPING_FILE);
		try {
			File tmpGeneReadsFile  = new File(Files.createTempFile(READS_COUNT_FILE, ".tmp").toString());
			File htseqDir = getHtseqDirectory(workingDir);
			DefaultAppController.getInstance().getHtseqController()
				.countBamReverseExon(referenceAnnotationFile, getBamFiles(samples),
					htseqDir, tmpGeneReadsFile);
			
			modifyReadsCountFile(tmpGeneReadsFile, samples, workingDir);
			
			geneIdToGeneNameMappings(referenceAnnotationFile, geneMappingFile);
			
			differentialExpression(workingDir);
		} catch (IOException e) {
			throw new ExecutionException(1,
					"Error creating temporal HTSeq reads count file. Please, check error log.", "");
		}

		
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
	
	private void modifyReadsCountFile(final File tmpGeneReadsFile, final EdgeRSamples samples, 
	                                  final  File workingDir) throws ExecutionException{
		try {
			File geneReadsFile = new File(workingDir, READS_COUNT_FILE);
			final List<String> rows = Files.readAllLines(Paths.get(tmpGeneReadsFile.getAbsolutePath()), 
													     Charset.defaultCharset());	
			try (PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(geneReadsFile.getAbsolutePath())))) {
				pw.println(getSamplesRow(samples));
				rows.forEach(r -> pw.println(r));
				pw.println(getClassesRow(samples));
			}catch (final IOException e) {
				throw new ExecutionException(1,
						"Error editing HTSeq reads count file. Please, check error log.", "");
			}
			Files.delete(Paths.get(tmpGeneReadsFile.getAbsolutePath()));
		} catch (IOException e) {
			throw new ExecutionException(1,
					"Error editing the HTSeq reads count file. Please, check error log.", "");
		}
	}

	private String getSamplesRow(EdgeRSamples samples) {
		StringBuilder sb = new StringBuilder();
		sb.append("gene ");
		sb.append(samples.stream().map(EdgeRSample::getName).collect(JOINING));
		return sb.toString();
	}

	private String getClassesRow(EdgeRSamples samples) {
		StringBuilder sb = new StringBuilder();
		sb.append("_class ");
		sb.append(samples.stream().map(EdgeRSample::getType).collect(JOINING));
		return sb.toString();
	}
}
