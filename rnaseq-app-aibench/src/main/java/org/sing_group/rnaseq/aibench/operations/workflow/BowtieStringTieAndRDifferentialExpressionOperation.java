/*
 * #%L
 * DEWE
 * %%
 * Copyright (C) 2016 - 2019 Hugo López-Fernández, Aitor Blanco-García, Florentino Fdez-Riverola,
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

import static javax.swing.SwingUtilities.invokeLater;
import static org.sing_group.rnaseq.core.controller.helper.AbstractDifferentialExpressionWorkflow.getBallgownWorkingDir;
import static org.sing_group.rnaseq.core.controller.helper.EdgeRDifferentialExpressionAnalysis.getEdgeRWorkingDir;

import java.io.File;
import java.util.Map;

import org.sing_group.rnaseq.aibench.datatypes.BallgownEdgeROverlapsWorkingDirectory;
import org.sing_group.rnaseq.aibench.datatypes.BallgownWorkingDirectory;
import org.sing_group.rnaseq.aibench.datatypes.EdgeRWorkingDirectory;
import org.sing_group.rnaseq.aibench.gui.util.AIBenchOperationStatus;
import org.sing_group.rnaseq.api.controller.WorkflowController;
import org.sing_group.rnaseq.api.entities.FastqReadsSamples;
import org.sing_group.rnaseq.api.environment.execution.ExecutionException;
import org.sing_group.rnaseq.api.persistence.entities.Bowtie2ReferenceGenomeIndex;
import org.sing_group.rnaseq.api.progress.OperationStatus;
import org.sing_group.rnaseq.core.controller.DefaultAppController;
import org.sing_group.rnaseq.core.controller.helper.BallgownEdgeROverlapsAnalysis;

import es.uvigo.ei.aibench.core.Core;
import es.uvigo.ei.aibench.core.operation.annotation.Direction;
import es.uvigo.ei.aibench.core.operation.annotation.Operation;
import es.uvigo.ei.aibench.core.operation.annotation.Port;
import es.uvigo.ei.aibench.core.operation.annotation.Progress;
import es.uvigo.ei.aibench.workbench.Workbench;

@Operation(
	name = "bowtie2, StringTie and R (ballgown/edgeR) differential expression",
	description = "Runs the differential expression workflow using bowtie2, StringTie and R (ballgown/edgeR)."
)
public class BowtieStringTieAndRDifferentialExpressionOperation {

	private AIBenchOperationStatus status = new AIBenchOperationStatus();
	private Bowtie2ReferenceGenomeIndex referenceGenome;
	private FastqReadsSamples samples;
	private File referenceAnnotationFile;
	private File workingDirectory;
	private Map<WorkflowController.Parameters, String> commandLineApplicationsParameters;

	@Port(
		direction = Direction.INPUT,
		name = "Bowtie2 reference genome",
		description = "Bowtie2 reference genome",
		allowNull = false,
		order = 1
	)
	public void setReferenceGenome(Bowtie2ReferenceGenomeIndex referenceGenome) {
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
		name = "Command line applications parameters",
		description = "Command line applications parameters",
		allowNull = false,
		order = 4
	)
	public void setCommandLineApplicationsParameters(
		Map<WorkflowController.Parameters, String> commandLineApplicationsParameters) {
		this.commandLineApplicationsParameters = commandLineApplicationsParameters;
	}

	@Port(
		direction = Direction.INPUT,
		name = "Working directory",
		description = "Working directory",
		allowNull = false,
		order = 5,
		extras = "selectionMode=directories"
	)
	public void setWorkingDirectory(File workingDirectory) {
		this.workingDirectory = workingDirectory;

		this.runAnalysis();
	}

	private void runAnalysis() {
		try {
 			DefaultAppController.getInstance().getWorkflowController()
				.runBowtieStringTieAndRDifferentialExpression(
					this.referenceGenome, this.samples,
					this.referenceAnnotationFile,
					this.commandLineApplicationsParameters,
					this.workingDirectory,
					this.status
				);
			processOutputs();
			invokeLater(this::succeed);
		} catch (ExecutionException | InterruptedException e) {
			Workbench.getInstance().error(e, e.getMessage());
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
		
		BallgownEdgeROverlapsWorkingDirectory ballgownEdgeROverlapsDirectory =
			new BallgownEdgeROverlapsWorkingDirectory(
				BallgownEdgeROverlapsAnalysis.getOverlapsWorkingDir(this.workingDirectory)
			);
		Core.getInstance().getClipboard()
			.putItem(ballgownEdgeROverlapsDirectory, 
					 ballgownEdgeROverlapsDirectory.getName());
	}

	private void succeed() {
		Workbench.getInstance().info("Workflow successfully executed.");
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
