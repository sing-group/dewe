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
import static org.sing_group.rnaseq.aibench.gui.util.PortConfiguration.EXTRAS_GTF_FILES;

import java.io.File;
import java.util.Arrays;

import org.sing_group.rnaseq.api.environment.execution.ExecutionException;
import org.sing_group.rnaseq.core.controller.DefaultAppController;
import org.sing_group.rnaseq.core.environment.execution.parameters.stringtie.StringTieParametersChecker;

import es.uvigo.ei.aibench.core.operation.annotation.Direction;
import es.uvigo.ei.aibench.core.operation.annotation.Operation;
import es.uvigo.ei.aibench.core.operation.annotation.Port;
import es.uvigo.ei.aibench.workbench.Workbench;

@Operation(
	name = "Merge transcripts using StringTie", 
	description = "Assembles transcripts from multiple input files generating a unified non-redundant set of isoforms."
)
public class StringTieMerge {
	private File referenceAnnotationFile;
	private File[] inputAnnotationFiles;
	private File outputTranscripts;
	private String commandParameters;

	@Port(
		direction = Direction.INPUT, 
		name = "Reference annotation file",
		description = "The reference annotation file (.gtf).",
		allowNull = false,
		order = 1,
		extras = EXTRAS_GTF_FILES
	)
	public void setReferenceAnnotationFile(File referenceAnnotationFile) {
		this.referenceAnnotationFile = referenceAnnotationFile;
	}
	
	@Port(
		direction = Direction.INPUT, 
		name = "Input annotation files",
		description = "The input annotation files (.gft).",
		allowNull = false,
		order = 2,
		extras = EXTRAS_GTF_FILES
	)	
	public void setInputAnnotationFiles(File[] inputAnnotationFiles) {
		this.inputAnnotationFiles = inputAnnotationFiles;
	}

	@Port(
		direction = Direction.INPUT,
		name = "Command parameters",
		description = "Additional command parameters. "
			+ StringTieParametersChecker.MERGE_TRANSCRIPTS_PARAMS,
		allowNull = false,
		order = 3,
		advanced = true,
		defaultValue = "",
		validateMethod = "validateCommandParameters"
	)
	public void setCommandParameters(String commandParameters) {
		this.commandParameters = commandParameters;
	}

	public void validateCommandParameters(String commandParameters) {
		if(!StringTieParametersChecker.validateMergeTranscriptsParameters(commandParameters)
		) {
			throw new IllegalArgumentException(
				StringTieParametersChecker.MERGE_TRANSCRIPTS_ERROR);
		};
	}

	@Port(
		direction = Direction.INPUT, 
		name = "Output transcripts file",
		description = "Optionally, an output transcripts file (.gtf)." + 
			"If not provided, it will be created in the same directory "
			+ "than the reference annotation file with name mergedAnnotation.gtf",
		allowNull = true,
		order = 4,
		extras = "selectionMode=files"
	)
	public void setOutputTranscripts(File outputTranscripts) {
		this.outputTranscripts = outputTranscripts != null ? outputTranscripts :
			getOutputTranscriptsFile();

		this.runOperation();
	}

	private File getOutputTranscriptsFile() {
		return new File(this.referenceAnnotationFile.getParentFile(), "mergedAnnotation.gtf");
	}

	private void runOperation() {
		try {
			DefaultAppController.getInstance().getStringTieController()
				.mergeTranscripts(referenceAnnotationFile,
					Arrays.asList(this.inputAnnotationFiles),
					outputTranscripts, commandParameters);
			invokeLater(this::succeed);
		} catch (ExecutionException e) {
			Workbench.getInstance().error(e, e.getMessage());
		} catch (InterruptedException e) {
			Workbench.getInstance().error(e, e.getMessage());
		}
	}
	
	private void succeed() {
		Workbench.getInstance().info("Transcript files successfully merged into " + outputTranscripts.getName() + ".");
	}
}

