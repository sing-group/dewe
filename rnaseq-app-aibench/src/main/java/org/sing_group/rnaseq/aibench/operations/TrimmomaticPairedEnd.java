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
import static org.sing_group.rnaseq.aibench.gui.dialogs.PairedEndReadsAlignSamplesParamsWindow.READS_FILE_1;
import static org.sing_group.rnaseq.aibench.gui.dialogs.PairedEndReadsAlignSamplesParamsWindow.READS_FILE_1_DESCRIPTION;
import static org.sing_group.rnaseq.aibench.gui.dialogs.PairedEndReadsAlignSamplesParamsWindow.READS_FILE_2;
import static org.sing_group.rnaseq.aibench.gui.dialogs.PairedEndReadsAlignSamplesParamsWindow.READS_FILE_2_DESCRIPTION;
import static org.sing_group.rnaseq.aibench.gui.util.PortConfiguration.EXTRAS_FASTQ_FILES;

import java.io.File;

import org.sing_group.rnaseq.aibench.gui.dialogs.TrimmomaticPairedEndOperationParamsWindow;
import org.sing_group.rnaseq.api.environment.execution.ExecutionException;
import org.sing_group.rnaseq.api.environment.execution.parameters.trimmomatic.TrimmomaticParameter;
import org.sing_group.rnaseq.core.controller.DefaultAppController;

import es.uvigo.ei.aibench.core.operation.annotation.Direction;
import es.uvigo.ei.aibench.core.operation.annotation.Operation;
import es.uvigo.ei.aibench.core.operation.annotation.Port;
import es.uvigo.ei.aibench.core.operation.annotation.Progress;
import es.uvigo.ei.aibench.workbench.Workbench;

@Operation(
	name = "Paired-end reads filtering using Trimmomatic",
	description = "Filters paired-end reads using Trimmomatic."
)
public class TrimmomaticPairedEnd {
	private File readsFile1;
	private File readsFile2;
	private File outputDir;
	private TrimmomaticParameter[] parameters;

	@Port(
		direction = Direction.INPUT,
		name = READS_FILE_1,
		description = READS_FILE_1_DESCRIPTION,
		allowNull = false,
		order = 1,
		extras = EXTRAS_FASTQ_FILES,
		advanced = false
	)
	public void setReadsFile1(File readsFile1) {
		this.readsFile1 = readsFile1;
	}

	@Port(
		direction = Direction.INPUT,
		name = READS_FILE_2,
		description = READS_FILE_2_DESCRIPTION,
		allowNull = false,
		order = 2,
		extras = EXTRAS_FASTQ_FILES,
		advanced = false
	)
	public void setReadsFile2(File readsFile2) {
		this.readsFile2 = readsFile2;
	}


	@Port(
		direction = Direction.INPUT,
		name = TrimmomaticPairedEndOperationParamsWindow.PORT_NAME,
		description = "Parameters for Trimmomatic",
		allowNull = false,
		order = 2
	)
	public void setParameters(TrimmomaticParameter[] parameters) {
		this.parameters = parameters;;
	}

	@Port(
		direction = Direction.INPUT,
		name = "Ouput directory",
		description = "Optionally, the directory where the filtered files must "
			+ "be created. If not provided, the output files arecreated in the "
			+ "same directory as the reads files being filtered.",
		allowNull = true,
		order = 3,
		extras = "selectionMode=directories"
	)
	public void setReferenceAnnotationFile(File outputDir) {
		this.outputDir = outputDir;
		this.runOperation();
	}

	private void runOperation() {
		try {
			runTrimmomatic();
			invokeLater(this::succeed);
		} catch (ExecutionException e) {
			Workbench.getInstance().error(e, e.getMessage());
		} catch (InterruptedException e) {
			Workbench.getInstance().error(e, e.getMessage());
		}
	}

	private void runTrimmomatic() throws ExecutionException, InterruptedException {
		DefaultAppController appController = DefaultAppController.getInstance();
		if (this.outputDir == null) {
			this.outputDir = this.readsFile1.getParentFile();
		}
		
		File outputFile = new File(outputDir, readsFile1.getName() + "_filtered");
		
		appController.getTrimmomaticController()
			.filterPairedEndReads(readsFile1, readsFile2, outputFile, parameters);
	}

	private void succeed() {
		Workbench.getInstance().info("Trimmomatic successfuly executed.");
	}

	@Progress(
		progressDialogTitle = "Progress",
		workingLabel = "Running Trimmomatic",
		preferredHeight = 200,
		preferredWidth = 300
	)
	public void progress() {}
}

