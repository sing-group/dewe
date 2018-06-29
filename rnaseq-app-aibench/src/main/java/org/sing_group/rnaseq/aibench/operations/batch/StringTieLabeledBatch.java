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
package org.sing_group.rnaseq.aibench.operations.batch;

import static org.sing_group.rnaseq.aibench.gui.util.PortConfiguration.EXTRAS_BAM_FILES;
import static org.sing_group.rnaseq.aibench.gui.util.PortConfiguration.EXTRAS_GTF_FILES;
import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.sing_group.rnaseq.core.environment.execution.parameters.stringtie.StringTieParametersChecker;
import org.sing_group.rnaseq.core.util.FileUtils;

import es.uvigo.ei.aibench.core.Core;
import es.uvigo.ei.aibench.core.operation.annotation.Direction;
import es.uvigo.ei.aibench.core.operation.annotation.Operation;
import es.uvigo.ei.aibench.core.operation.annotation.Port;

@Operation(
	name = "Reconstruct transcripts using StringTie", 
	description = "Assembles RNA-Seq alignments into potential labeled transcripts using StringTie."
)
public class StringTieLabeledBatch {
	private File referenceAnnotationFile;
	private File[] inputBamFiles;
	private String label;
	private String commandParameters;

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
		name = "Input bam files",
		description = "Input bam files.",
		allowNull = false,
		order = 2,
		extras = EXTRAS_BAM_FILES
	)
	public void setInputBamFile(File[] inputBamFiles) {
		this.inputBamFiles = inputBamFiles;
	}

	@Port(
		direction = Direction.INPUT,
		name = "Command parameters",
		description = "Additional command parameters. "
			+ StringTieParametersChecker.OBTAIN_LABELED_TRANSCRIPTS_PARAMS,
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
		if(!StringTieParametersChecker.validateObtainLabeledTranscriptsParameters(commandParameters)
		) {
			throw new IllegalArgumentException(
				StringTieParametersChecker.OBTAIN_LABELED_TRANSCRIPTS_ERROR);
		};
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
		this.label = label;
		
		this.runOperation();
	}
	
	private void runOperation() {
		for(final File f : inputBamFiles) {
			Core.getInstance().executeOperation(
				"operations.stringtielabeled", null, 
				stringTieLabeledParams(f)
			);
		}
	}

	private List<?> stringTieLabeledParams(File f) {
		return Arrays.asList(this.referenceAnnotationFile, f, null, this.commandParameters, getLabel(f));
	}

	private String getLabel(File f) {
		return label == null ? FileUtils.removeExtension(f) : label;
	}
}
