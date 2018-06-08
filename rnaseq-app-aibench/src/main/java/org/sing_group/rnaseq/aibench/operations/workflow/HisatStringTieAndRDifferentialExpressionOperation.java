/*
 * #%L
 * DEWE
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
package org.sing_group.rnaseq.aibench.operations.workflow;

import static org.sing_group.rnaseq.core.controller.helper.AbstractDifferentialExpressionWorkflow.getBallgownWorkingDir;
import static org.sing_group.rnaseq.core.controller.helper.EdgeRDifferentialExpressionAnalysis.getEdgeRWorkingDir;

import java.io.File;

import org.sing_group.rnaseq.aibench.datatypes.BallgownWorkingDirectory;
import org.sing_group.rnaseq.aibench.datatypes.EdgeRWorkingDirectory;
import org.sing_group.rnaseq.aibench.gui.util.AIBenchOperationStatus;
import org.sing_group.rnaseq.api.entities.FastqReadsSamples;
import org.sing_group.rnaseq.api.environment.execution.ExecutionException;
import org.sing_group.rnaseq.api.persistence.entities.Hisat2ReferenceGenomeIndex;
import org.sing_group.rnaseq.api.progress.OperationStatus;
import org.sing_group.rnaseq.core.controller.DefaultAppController;
import org.slf4j.LoggerFactory;

import es.uvigo.ei.aibench.core.Core;
import es.uvigo.ei.aibench.core.operation.annotation.Direction;
import es.uvigo.ei.aibench.core.operation.annotation.Operation;
import es.uvigo.ei.aibench.core.operation.annotation.Port;
import es.uvigo.ei.aibench.core.operation.annotation.Progress;

@Operation(
	name = "HISAT2, StringTie and R (ballgown/edgeR) differential expression",
	description = "Runs the differential expression workflow using HISTA2, StringTie and R (ballgown/edgeR)."
)
public class HisatStringTieAndRDifferentialExpressionOperation {

	private AIBenchOperationStatus status = new AIBenchOperationStatus();
	private Hisat2ReferenceGenomeIndex referenceGenome;
	private FastqReadsSamples samples;
	private File referenceAnnotationFile;
	private File workingDirectory;

	@Port(
		direction = Direction.INPUT,
		name = "HISAT2 reference genome",
		description = "HISAT2 reference genome",
		allowNull = false,
		order = 1
	)
	public void setReferenceGenome(Hisat2ReferenceGenomeIndex referenceGenome) {
		this.referenceGenome = referenceGenome;
	}

	@Port(
		direction = Direction.INPUT,
		name = "FastQ samples",
		description = "FastQ samples",
		allowNull = false,
		order = 2
	)
	public void setSamples(FastqReadsSamples samples) {
		this.samples = samples;
	}

	@Port(
		direction = Direction.INPUT,
		name = "Reference annotation file",
		description = "Reference annotation file",
		allowNull = false,
		order = 3,
		extras="selectionMode=files"
	)
	public void setReferenceAnnotationFile(File referenceAnnotationFile) {
		this.referenceAnnotationFile = referenceAnnotationFile;
	}

	@Port(
		direction = Direction.INPUT,
		name = "Working directory",
		description = "Working directory",
		allowNull = false,
		order = 4,
		extras = "selectionMode=directories"
	)
	public void setWorkingDirectory(File workingDirectory) {
		this.workingDirectory = workingDirectory;

		this.runAnalysis();
	}

	private void runAnalysis() {
		try {
			DefaultAppController.getInstance().getWorkflowController()
				.runHisatStringTieAndRDifferentialExpression(
					this.referenceGenome, this.samples,
					this.referenceAnnotationFile, this.workingDirectory,
					this.status);
			processOutputs();
		} catch (ExecutionException | InterruptedException e) {
			LoggerFactory.getLogger(getClass()).error(e.getMessage());
		}
	}

	private void processOutputs() {
		BallgownWorkingDirectory ballgownDirectory =
			new BallgownWorkingDirectory(
				getBallgownWorkingDir(this.workingDirectory)
			);
		Core.getInstance().getClipboard()
			.putItem(ballgownDirectory, ballgownDirectory.getName());

		EdgeRWorkingDirectory edgeRDirectory =
			new EdgeRWorkingDirectory(
				getEdgeRWorkingDir(this.workingDirectory)
			);
		Core.getInstance().getClipboard()
			.putItem(edgeRDirectory, edgeRDirectory.getName());
	}

	@Progress(
		workingLabel = "Running workflow",
		progressDialogTitle = "Execution progress",
		modal = true
	)
	public OperationStatus getStatus(){
	      return this.status;
	}
}
