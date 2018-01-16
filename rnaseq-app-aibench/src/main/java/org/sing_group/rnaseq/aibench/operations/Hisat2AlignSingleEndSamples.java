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
package org.sing_group.rnaseq.aibench.operations;

import static org.sing_group.rnaseq.aibench.operations.util.OperationsUtils.getSamOutputFile;
import static javax.swing.SwingUtilities.invokeLater;
import static org.sing_group.rnaseq.aibench.gui.dialogs.Hisat2AlignSingleEndSamplesParamsWindow.REFERENCE_GENOME;
import static org.sing_group.rnaseq.aibench.gui.dialogs.Hisat2AlignSingleEndSamplesParamsWindow.TRANSCRIPT_ASSEMBLERS_DESCRIPTION;
import static org.sing_group.rnaseq.aibench.gui.dialogs.SingleEndReadsAlignSamplesParamsWindow.READS_FILE;
import static org.sing_group.rnaseq.aibench.gui.dialogs.SingleEndReadsAlignSamplesParamsWindow.READS_FILE_DESCRIPTION;
import static org.sing_group.rnaseq.aibench.gui.util.PortConfiguration.EXTRAS_FASTQ_FILES;

import java.io.File;

import org.sing_group.rnaseq.aibench.gui.util.FileOperationStatus;
import org.sing_group.rnaseq.api.environment.execution.ExecutionException;
import org.sing_group.rnaseq.api.persistence.entities.Hisat2ReferenceGenomeIndex;
import org.sing_group.rnaseq.core.controller.DefaultAppController;

import es.uvigo.ei.aibench.core.operation.annotation.Direction;
import es.uvigo.ei.aibench.core.operation.annotation.Operation;
import es.uvigo.ei.aibench.core.operation.annotation.Port;
import es.uvigo.ei.aibench.core.operation.annotation.Progress;
import es.uvigo.ei.aibench.workbench.Workbench;

@Operation(
	name = "Align reads using HISAT2",
	description = "Aligns single-end reads using HISAT2."
)
public class Hisat2AlignSingleEndSamples {
	private Hisat2ReferenceGenomeIndex referenceGenome;
	private File readsFile;
	private File outputFile;
	private boolean saveAlignmentLog;
	private boolean dta;
	private FileOperationStatus status = new FileOperationStatus();

	@Port(
		direction = Direction.INPUT,
		name = REFERENCE_GENOME,
		description = "The reference genome index to use. It must have been "
			+ "previously built or imported into the application.",
		allowNull = false,
		order = 1
	)
	public void setReferenceGenome(Hisat2ReferenceGenomeIndex referenceGenome) {
		this.referenceGenome = referenceGenome;
	}

	@Port(
		direction = Direction.INPUT,
		name = READS_FILE,
		description = READS_FILE_DESCRIPTION,
		allowNull = false,
		order = 2,
		extras = EXTRAS_FASTQ_FILES,
		advanced = false
	)
	public void setReadsFile(File readsFile) {
		this.readsFile = readsFile;
	}

	@Port(
		direction = Direction.INPUT,
		name = "Save alignment log",
		description = "Whether the alignment log must be saved or not.",
		allowNull = false,
		order = 4,
		defaultValue = "true",
		advanced = false
	)
	public void setSaveAlignmentLog(boolean saveAlignmentLog) {
		this.saveAlignmentLog = saveAlignmentLog;
	}

	@Port(
		direction = Direction.INPUT,
		name = "Transcript assemblers",
		description = TRANSCRIPT_ASSEMBLERS_DESCRIPTION,
		allowNull = false,
		order = 5,
		defaultValue = "false",
		advanced = false
	)
	public void setDta(boolean dta) {
		this.dta = dta;
	}

	@Port(
		direction = Direction.INPUT,
		name = "Output file",
		description = "Output file.",
		allowNull = true,
		order = 6,
		extras = "selectionMode=files",
		advanced = false
	)
	public void setOutputFile(File outputFile) {
		this.outputFile = getSamOutputFile(outputFile, this.readsFile);

		this.runOperation();
	}

	private void runOperation() {
		try {
			this.status.setStage(outputFile.getName());
			DefaultAppController.getInstance().getHisat2Controller().alignReads(
				referenceGenome, readsFile, dta, outputFile,
				saveAlignmentLog);
			invokeLater(this::succeed);
		} catch (ExecutionException | InterruptedException e) {
			Workbench.getInstance().error(e, e.getMessage());
		}
	}

	private void succeed() {
		Workbench.getInstance().info("Reads successfully aligned.");
	}

	@Progress(
		progressDialogTitle = "Progress",
		workingLabel = "Hisat2 reads alignment",
		preferredHeight = 200,
		preferredWidth = 300
	)
	public FileOperationStatus progress() {
		return this.status;
	}
}
