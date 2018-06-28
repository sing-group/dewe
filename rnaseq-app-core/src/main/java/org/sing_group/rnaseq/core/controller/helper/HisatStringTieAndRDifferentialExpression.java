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
package org.sing_group.rnaseq.core.controller.helper;

import static org.sing_group.rnaseq.core.controller.helper.BallgownDifferentialExpressionAnalysis.ballgownDifferentialExpressionAnalysis;
import static org.sing_group.rnaseq.core.controller.helper.EdgeRDifferentialExpressionAnalysis.edgeRDifferentialExpressionAnalysis;

import java.io.File;
import java.util.Collections;

import org.sing_group.rnaseq.api.controller.Hisat2Controller;
import org.sing_group.rnaseq.api.entities.FastqReadsSample;
import org.sing_group.rnaseq.api.entities.FastqReadsSamples;
import org.sing_group.rnaseq.api.environment.execution.ExecutionException;
import org.sing_group.rnaseq.api.persistence.entities.Hisat2ReferenceGenomeIndex;
import org.sing_group.rnaseq.api.progress.OperationStatus;
import org.sing_group.rnaseq.core.controller.DefaultAppController;

/**
 * A concrete {@code AbstractDifferentialExpressionWorkflow} implementation to 
 * run a complete differential expression analysis using HISAT2, StringTie and 
 * R packages (Ballgown and edgeR).
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class HisatStringTieAndRDifferentialExpression
	extends AbstractDifferentialExpressionWorkflow {

	private Hisat2Controller hisat2Controller;

	/**
	 * Creates a new {@code HisatStringTieAndRDifferentialExpression}
	 * instance in order to perform a differential expression analysis.
	 *
	 * @param referenceGenome the reference genome to use in the analysis
	 * @param reads the {@code FastqReadsSamples} to analyze
	 * @param referenceAnnotationFile the reference annotation file
	 * @param workingDirectory the working directory to store the analysis
	 * 		  results
	 */
	public HisatStringTieAndRDifferentialExpression(
		Hisat2ReferenceGenomeIndex referenceGenome, FastqReadsSamples reads,
		File referenceAnnotationFile, File workingDirectory
	) {
		super(referenceGenome, reads, referenceAnnotationFile,
			Collections.emptyMap(), workingDirectory);
		this.hisat2Controller =
			DefaultAppController.getInstance().getHisat2Controller();
	}

	@Override
	protected void alignReads(FastqReadsSample sample, File output)
		throws ExecutionException, InterruptedException {

		if (sample.isPairedEnd()) {
			hisat2Controller.alignReads(
				(Hisat2ReferenceGenomeIndex) referenceGenome,
				sample.getReadsFile1(), sample.getReadsFile2().get(), true,
				output, true);
		} else {
			hisat2Controller.alignReads(
				(Hisat2ReferenceGenomeIndex) referenceGenome,
				sample.getReadsFile1(), true, output, true);
		}
	}

	@Override
	protected void performDifferentialExpressionAnalysis(OperationStatus status)
		throws ExecutionException, InterruptedException {
		status.setStageProgress(0f);
		status.setSubStage("Ballgown");
		ballgownDifferentialExpressionAnalysis(
			reads, workingDirectory, imageConfiguration);
		status.setStageProgress(0.5f);
		status.setSubStage("EdgeR");
		edgeRDifferentialExpressionAnalysis(
			reads, referenceAnnotationFile, workingDirectory);
		status.setStageProgress(1f);
	}
}
