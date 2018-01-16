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

import static javax.swing.SwingUtilities.invokeLater;
import static org.sing_group.rnaseq.aibench.gui.util.PortConfiguration.EXTRAS_BAM_FILES;
import static org.sing_group.rnaseq.aibench.gui.util.PortConfiguration.EXTRAS_GTF_FILES;

import java.io.File;

import org.sing_group.rnaseq.aibench.gui.util.FileOperationStatus;
import org.sing_group.rnaseq.api.environment.execution.ExecutionException;
import org.sing_group.rnaseq.core.controller.DefaultAppController;
import org.sing_group.rnaseq.core.util.FileUtils;

import es.uvigo.ei.aibench.core.operation.annotation.Direction;
import es.uvigo.ei.aibench.core.operation.annotation.Operation;
import es.uvigo.ei.aibench.core.operation.annotation.Port;
import es.uvigo.ei.aibench.core.operation.annotation.Progress;
import es.uvigo.ei.aibench.workbench.Workbench;

@Operation(
	name = "Reconstruct labeled transcripts using StringTie", 
	description = "Assembles RNA-Seq alignments into potential labeled transcripts using StringTie."
)
public class StringTieLabeled {
	private FileOperationStatus status = new FileOperationStatus();
	private File referenceAnnotationFile;
	private File inputBam;
	private File outputTranscripts;
	private String label;

	@Port(
		direction = Direction.INPUT, 
		name = "Reference annotation file",
		description = "Reference annotation file (.gtf)",
		allowNull = false,
		order = 1,
		extras = EXTRAS_GTF_FILES
	)
	public void setReferenceAnnotationFile(File referenceAnnotationFile) {
		this.referenceAnnotationFile = referenceAnnotationFile;
	}

	@Port(
		direction = Direction.INPUT, 
		name = "Input bam file",
		description = "Input bam file.",
		allowNull = false,
		order = 2,
		extras = EXTRAS_BAM_FILES
	)
	public void setInputBamFile(File inputBam) {
		this.inputBam = inputBam;
	}

	@Port(
		direction = Direction.INPUT, 
		name = "Output transcripts file",
		description = "Optionally, an output transcripts file (.gtf). " + 
			"If not provided, it will be created in the same directory than the input bam file",
		allowNull = true,
		order = 3,
		extras="selectionMode=files"
	)
	public void setOutputTranscripts(File outputTranscripts) {
		this.outputTranscripts = outputTranscripts != null ? outputTranscripts :
			getOutputTranscriptsFile();
	}

	private File getOutputTranscriptsFile() {
		return new File(this.inputBam.getParentFile(), FileUtils.removeExtension(this.inputBam) + ".gtf");
	}

	@Port(
		direction = Direction.INPUT, 
		name = "Label",
		description = "Optionally, the label for the -l option of StringTie. "
			+ "This label is the name prefix for output transcripts. "
			+ "If not provided, it will be used the file name.",
		allowNull = true,
		order = 4
	)
	public void setLabel(String label) {
		this.label = label != null ? label : 
			getLabel();
		
		this.runOperation();
	}

	private String getLabel() {
		return FileUtils.removeExtension(this.inputBam);
	}

	private void runOperation() {
		try {
			this.status.setStage(inputBam.getName());
			DefaultAppController.getInstance().getStringTieController()
				.obtainLabeledTranscripts(referenceAnnotationFile, inputBam,
					outputTranscripts, label);
			invokeLater(this::succeed);
		} catch (ExecutionException e) {
			Workbench.getInstance().error(e, e.getMessage());
		} catch (InterruptedException e) {
			Workbench.getInstance().error(e, e.getMessage());
		}
	}
	
	private void succeed() {
		Workbench.getInstance().info(inputBam.getName() + " successfully transcripted to " + outputTranscripts.getName() + ".");
	}

	@Progress(
		progressDialogTitle = "Progress",
		workingLabel = "Obtaining labeled transcripts",
		preferredHeight = 200,
		preferredWidth = 300
	)
	public FileOperationStatus progress() {
		return this.status;
	}
}
