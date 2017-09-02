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

import java.io.File;

import org.sing_group.rnaseq.api.controller.WorkflowController;
import org.sing_group.rnaseq.api.entities.FastqReadsSamples;
import org.sing_group.rnaseq.api.environment.execution.ExecutionException;
import org.sing_group.rnaseq.api.persistence.entities.Bowtie2ReferenceGenomeIndex;
import org.sing_group.rnaseq.api.persistence.entities.Hisat2ReferenceGenomeIndex;
import org.sing_group.rnaseq.api.progress.OperationStatus;
import org.sing_group.rnaseq.core.controller.helper.BowtieStringTieAndRDifferentialExpression;
import org.sing_group.rnaseq.core.controller.helper.HisatStringTieAndBallgownDifferentialExpression;

/**
 * The default {@link WorkflowController} implementation.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class DefaultWorkflowController implements WorkflowController {

	@Override
	public void runBowtieStringTieAndRDifferentialExpression(
		Bowtie2ReferenceGenomeIndex referenceGenome,
		FastqReadsSamples reads, 
		File referenceAnnotationFile,
		File workingDirectory, 
		OperationStatus status
	) throws ExecutionException, InterruptedException {
		BowtieStringTieAndRDifferentialExpression workflow =
			new BowtieStringTieAndRDifferentialExpression(
				referenceGenome, reads, 
				referenceAnnotationFile, workingDirectory);
		workflow.runAnalysis(status);
	}

	@Override
	public void runHisatStringTieAndBallgownDifferentialExpression(
		Hisat2ReferenceGenomeIndex referenceGenome,
		FastqReadsSamples reads, 
		File referenceAnnotationFile,
		File workingDirectory, 
		OperationStatus status
	) throws ExecutionException, InterruptedException {
		HisatStringTieAndBallgownDifferentialExpression workflow =
			new HisatStringTieAndBallgownDifferentialExpression(
				referenceGenome, reads, 
				referenceAnnotationFile, workingDirectory);
		workflow.runAnalysis(status);
	}
}
