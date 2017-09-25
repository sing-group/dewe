/*
 * #%L
 * DEWE
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
package org.sing_group.rnaseq.aibench.operations;


import static javax.swing.SwingUtilities.invokeLater;
import static org.sing_group.rnaseq.aibench.gui.dialogs.EdgeRDifferentialExpressionAnalysisParamsWindow.SAMPLES;
import static org.sing_group.rnaseq.aibench.gui.dialogs.EdgeRDifferentialExpressionAnalysisParamsWindow.SAMPLES_DESCRIPTION;

import java.io.File;

import org.sing_group.rnaseq.aibench.datatypes.EdgeRWorkingDirectory;
import org.sing_group.rnaseq.api.entities.edger.EdgeRSamples;
import org.sing_group.rnaseq.api.environment.execution.ExecutionException;
import org.sing_group.rnaseq.core.controller.DefaultAppController;

import es.uvigo.ei.aibench.core.Core;
import es.uvigo.ei.aibench.core.operation.annotation.Direction;
import es.uvigo.ei.aibench.core.operation.annotation.Operation;
import es.uvigo.ei.aibench.core.operation.annotation.Port;
import es.uvigo.ei.aibench.workbench.Workbench;

@Operation(
	name = "Differential expression analysis with edgeR",
	description = "Performs a differential expression analysis using edgeR."
)
public class EdgeRDifferentialExpressionAnalysis {
	private File workingDir;
	private File referenceAnnotationFile;
	private EdgeRSamples samples;

	@Port(
		direction = Direction.INPUT,
		name = "Working directory",
		description = "Te output directory where results are stored.",
		allowNull = false,
		order = 1,
		extras="selectionMode=directories"
	)
	public void setWorkingDir(File workingDir) {
		this.workingDir = workingDir;
	}

	@Port(
		direction = Direction.INPUT,
		name = "Reference annotation file",
		description = "The reference annotation file (.gtf).",
		allowNull = false,
		order = 2,
		extras="selectionMode=files"
	)
	public void setReferenceAnnotationFile(File referenceAnnotationFile) {
		this.referenceAnnotationFile = referenceAnnotationFile;
	}

	@Port(
		direction = Direction.INPUT,
		name = SAMPLES,
		description = SAMPLES_DESCRIPTION,
		allowNull = false,
		order = 3
	)
	public void setEdgeRSamples(EdgeRSamples samples) {
		this.samples = samples;

		this.runOperation();
	}

	private void runOperation() {
		try {
			DefaultAppController.getInstance().getEdgeRController()
				.differentialExpression(this.samples,
					this.referenceAnnotationFile, this.workingDir);
			invokeLater(this::succeed);
			processOutputs();
		} catch (ExecutionException e) {
			Workbench.getInstance().error(e, e.getMessage());
		} catch (InterruptedException e) {
			Workbench.getInstance().error(e, e.getMessage());
		}
	}

	private void processOutputs() {
		EdgeRWorkingDirectory edgeRDirectory =
			new EdgeRWorkingDirectory(this.workingDir);
		Core.getInstance().getClipboard()
			.putItem(edgeRDirectory, edgeRDirectory.getName());
	}

	private void succeed() {
		Workbench.getInstance().info(
			"Succeed: edgeR analysis results can be found at " +
			this.workingDir.getAbsolutePath() + "."
		);
	}
}
