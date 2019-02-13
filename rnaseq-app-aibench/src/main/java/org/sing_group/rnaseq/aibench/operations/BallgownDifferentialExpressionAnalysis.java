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
package org.sing_group.rnaseq.aibench.operations;

import static javax.swing.SwingUtilities.invokeLater;
import static org.sing_group.rnaseq.aibench.gui.dialogs.BallgownDifferentialExpressionAnalysisParamsWindow.SAMPLES;
import static org.sing_group.rnaseq.aibench.gui.dialogs.BallgownDifferentialExpressionAnalysisParamsWindow.SAMPLES_DESCRIPTION;

import java.io.File;

import org.sing_group.rnaseq.aibench.datatypes.BallgownWorkingDirectory;
import org.sing_group.rnaseq.api.entities.ballgown.BallgownSamples;
import org.sing_group.rnaseq.api.environment.execution.ExecutionException;
import org.sing_group.rnaseq.core.controller.DefaultAppController;

import es.uvigo.ei.aibench.core.Core;
import es.uvigo.ei.aibench.core.operation.annotation.Direction;
import es.uvigo.ei.aibench.core.operation.annotation.Operation;
import es.uvigo.ei.aibench.core.operation.annotation.Port;
import es.uvigo.ei.aibench.workbench.Workbench;

@Operation(
	name = "Differential expression analysis with Ballgown",
	description = "Performs a differential expression analysis using Ballgown."
)
public class BallgownDifferentialExpressionAnalysis {
	private BallgownSamples samples;
	private File directory;

	@Port(
		direction = Direction.INPUT,
		name = SAMPLES,
		description = SAMPLES_DESCRIPTION,
		allowNull = false,
		order = 1
	)
	public void setSamples(BallgownSamples samples) {
		this.samples = samples;
	}

	@Port(
		direction = Direction.INPUT,
		name = "Directory",
		description = "The output directory where results are stored.",
		allowNull = false,
		order = 2,
		extras="selectionMode=directories"
	)
	public void setDirectory(File directory) {
		this.directory = directory;

		this.runOperation();
	}

	private void runOperation() {
		try {
			DefaultAppController.getInstance().getBallgownController()
				.differentialExpression(samples, this.directory);
			invokeLater(this::succeed);
			processOutputs();
		} catch (ExecutionException | InterruptedException e) {
			Workbench.getInstance().error(e, e.getMessage());
		}
	}

	private void processOutputs() {
		BallgownWorkingDirectory ballgownDirectory =
			new BallgownWorkingDirectory(this.directory);
		Core.getInstance().getClipboard()
			.putItem(ballgownDirectory, ballgownDirectory.getName());
	}

	private void succeed() {
		Workbench.getInstance().info(
			"Succeed: ballgown analysis results can be found at " +
			this.directory.getAbsolutePath() + "."
		);
	}
}
