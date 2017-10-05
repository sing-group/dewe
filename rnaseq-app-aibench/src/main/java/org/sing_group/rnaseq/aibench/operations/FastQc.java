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
import static org.sing_group.rnaseq.aibench.gui.util.PortConfiguration.EXTRAS_FASTQ_FILES;

import java.io.File;

import org.sing_group.rnaseq.api.environment.execution.ExecutionException;
import org.sing_group.rnaseq.core.controller.DefaultAppController;

import es.uvigo.ei.aibench.core.operation.annotation.Direction;
import es.uvigo.ei.aibench.core.operation.annotation.Operation;
import es.uvigo.ei.aibench.core.operation.annotation.Port;
import es.uvigo.ei.aibench.core.operation.annotation.Progress;
import es.uvigo.ei.aibench.workbench.Workbench;

@Operation(
	name = "Reads quality control using FastQC",
	description = "Generates a reads quality control report using FastQC."
)
public class FastQc {
	private File[] inputFiles;
	private File outputDir;

	@Port(
		direction = Direction.INPUT,
		name = "Input files",
		description = "The input files.",
		allowNull = false,
		order = 1,
		extras = EXTRAS_FASTQ_FILES
	)
	public void setInputBamFile(File[] inputFiles) {
		this.inputFiles = inputFiles;
	}

	@Port(
		direction = Direction.INPUT,
		name = "Ouput directory",
		description = "Optionally, the directory where the reports must be "
			+ "generated. If not provided, the output report for each reads "
			+ "file is created in the same directory as the reads file "
			+ "being processed.",
		allowNull = true,
		order = 2,
		extras = "selectionMode=directories"
	)
	public void setReferenceAnnotationFile(File outputDir) {
		this.outputDir = outputDir;

		this.runOperation();
	}

	private void runOperation() {
		try {
			runFastQc();
			invokeLater(this::succeed);
		} catch (ExecutionException e) {
			Workbench.getInstance().error(e, e.getMessage());
		} catch (InterruptedException e) {
			Workbench.getInstance().error(e, e.getMessage());
		}
	}

	private void runFastQc() throws ExecutionException, InterruptedException {
		DefaultAppController appController = DefaultAppController.getInstance();
		if (this.outputDir == null) {
			appController.getFastQcController().fastqc(inputFiles);
		} else {
			appController.getFastQcController().fastqc(inputFiles, outputDir);
		}
	}

	private void succeed() {
		Workbench.getInstance().info("FastQC reports successfully created.");
	}

	@Progress(
		progressDialogTitle = "Progress",
		workingLabel = "Running FastQC",
		preferredHeight = 200,
		preferredWidth = 300
	)
	public void progress() {}
}

