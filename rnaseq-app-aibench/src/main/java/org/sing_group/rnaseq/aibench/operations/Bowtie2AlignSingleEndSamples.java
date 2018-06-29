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
import static org.sing_group.rnaseq.aibench.gui.dialogs.Bowtie2AlignSingleEndSamplesParamsWindow.REFERENCE_GENOME;
import static org.sing_group.rnaseq.aibench.gui.dialogs.SingleEndReadsAlignSamplesParamsWindow.READS_FILE;
import static org.sing_group.rnaseq.aibench.gui.dialogs.SingleEndReadsAlignSamplesParamsWindow.READS_FILE_DESCRIPTION;
import static org.sing_group.rnaseq.aibench.gui.util.PortConfiguration.EXTRAS_FASTQ_FILES;
import static org.sing_group.rnaseq.aibench.operations.util.OperationsUtils.getSamOutputFile;

import java.io.File;

import org.sing_group.rnaseq.aibench.gui.util.FileOperationStatus;
import org.sing_group.rnaseq.api.environment.execution.ExecutionException;
import org.sing_group.rnaseq.api.environment.execution.parameters.bowtie2.Bowtie2EndToEndConfiguration;
import org.sing_group.rnaseq.api.persistence.entities.Bowtie2ReferenceGenomeIndex;
import org.sing_group.rnaseq.core.controller.DefaultAppController;
import org.sing_group.rnaseq.core.environment.execution.parameters.bowtie2.Bowtie2ParametersChecker;
import org.sing_group.rnaseq.core.environment.execution.parameters.bowtie2.DefaultBowtie2EndToEndConfiguration;

import es.uvigo.ei.aibench.core.operation.annotation.Direction;
import es.uvigo.ei.aibench.core.operation.annotation.Operation;
import es.uvigo.ei.aibench.core.operation.annotation.Port;
import es.uvigo.ei.aibench.core.operation.annotation.Progress;
import es.uvigo.ei.aibench.workbench.Workbench;

@Operation(
	name = "Align reads using Bowtie2",
	description = "Aligns single-end reads using Bowtie2."
)
public class Bowtie2AlignSingleEndSamples {
	private Bowtie2ReferenceGenomeIndex referenceGenome;
	private File readsFile;
	private File outputFile;
	private boolean saveAlignmentLog;
	private FileOperationStatus status = new FileOperationStatus();
	private Bowtie2EndToEndConfiguration configuration;
	private String commandParameters;

	@Port(
		direction = Direction.INPUT,
		name = REFERENCE_GENOME,
		description = "The reference genome index to use. It must have been "
			+ "previously built or imported into the application.",
		allowNull = false,
		order = 1
	)
	public void setReferenceGenome(Bowtie2ReferenceGenomeIndex referenceGenome) {
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
		name = "Presets",
		description = "Presets options for the --end-to-end mode.",
		allowNull = false,
		order = 5,
		extras = "mode=radiobuttons, numrows=1, numcolumns=4",
		advanced = true,
		defaultValue = DefaultBowtie2EndToEndConfiguration.DEFAULT_VALUE_STR
	)
	public void setBowtie2EndToEndConfiguration(
		DefaultBowtie2EndToEndConfiguration configuration
	) {
		this.configuration = configuration;
	}

	@Port(
		direction = Direction.INPUT,
		name = "Command parameters",
		description = "Additional command parameters. By indicating any "
			+ "parameters here, presets choice is ignored."
			+ Bowtie2ParametersChecker.ALIGN_PARAMS,
		allowNull = false,
		order = 6,
		advanced = true,
		defaultValue = "",
		validateMethod = "validateCommandParameters"
	)
	public void setCommandParameters(String commandParameters) {
		this.commandParameters = commandParameters;
	}

	public void validateCommandParameters(
		String commandParameters
	) {
		if(!Bowtie2ParametersChecker.validateAlignReadsParameters(commandParameters)
		) {
			throw new IllegalArgumentException(
				Bowtie2ParametersChecker.ALIGN_ERROR);
		};
	}

	@Port(
		direction = Direction.INPUT,
		name = "Output file",
		description = "The output file to save the alignments.",
		allowNull = true,
		order = 7,
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
			DefaultAppController.getInstance().getBowtie2Controller()
				.alignReads(referenceGenome, readsFile,
					getCmdParams(), outputFile, saveAlignmentLog);
			invokeLater(this::succeed);
		} catch (ExecutionException | InterruptedException e) {
			Workbench.getInstance().error(e, e.getMessage());
		}
	}

	private String getCmdParams() {
		if (commandParameters.isEmpty()) {
			return this.configuration.getParameter();
		} else {
			return this.commandParameters;
		}
	}

	private void succeed() {
		Workbench.getInstance().info("Reads successfully aligned.");
	}

	@Progress(
		progressDialogTitle = "Progress",
		workingLabel = "Bowtie2 reads alignment",
		preferredHeight = 200,
		preferredWidth = 300
	)
	public FileOperationStatus progress() {
		return this.status;
	}
}
