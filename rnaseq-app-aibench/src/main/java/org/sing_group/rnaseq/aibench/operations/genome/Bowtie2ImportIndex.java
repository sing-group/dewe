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
package org.sing_group.rnaseq.aibench.operations.genome;

import static javax.swing.SwingUtilities.invokeLater;
import static org.sing_group.rnaseq.aibench.gui.dialogs.ReferenceGenomeOperationParamsWindow.GENOME;
import static org.sing_group.rnaseq.aibench.gui.dialogs.ReferenceGenomeOperationParamsWindow.NAME;
import static org.sing_group.rnaseq.aibench.gui.util.PortConfiguration.EXTRAS_GENOME_FA_FILES;
import static org.sing_group.rnaseq.core.persistence.entities.DefaultBowtie2ReferenceGenomeIndex.directoryContainsBowtie2Indexes;

import java.io.File;
import java.io.IOException;

import org.sing_group.rnaseq.aibench.operations.util.OperationsUtils;
import org.sing_group.rnaseq.api.persistence.entities.ReferenceGenomeIndex;
import org.sing_group.rnaseq.core.controller.DefaultAppController;
import org.sing_group.rnaseq.core.persistence.DefaultReferenceGenomeIndexDatabaseManager;
import org.sing_group.rnaseq.core.persistence.entities.DefaultBowtie2ReferenceGenomeIndex;

import es.uvigo.ei.aibench.core.operation.annotation.Direction;
import es.uvigo.ei.aibench.core.operation.annotation.Operation;
import es.uvigo.ei.aibench.core.operation.annotation.Port;
import es.uvigo.ei.aibench.core.operation.annotation.Progress;
import es.uvigo.ei.aibench.workbench.Workbench;

@Operation(
	name = "Import Bowtie2 index",
	description = "Imports a reference genome indexed using Bowtie2."
)
public class Bowtie2ImportIndex {
	private String name;
	private File file;
	private File indexDir;

	@Port(
		direction = Direction.INPUT,
		name = "Index folder",
		description = "The folder that contains the Bowtie2 genome index.",
		allowNull = false,
		order = 1,
		extras = "selectionMode=directories",
		advanced = false,
		validateMethod = "validateIndexDirectory"
	)
	public void setIndexDirectory(File indexDir) {
		this.indexDir = indexDir;
	}
	
	public void validateIndexDirectory(File indexDir) {
		if (!directoryContainsBowtie2Indexes(indexDir)) {
			throw new IllegalArgumentException(
				"Index directory must contain the Bowtie2 indexes files");
		}
	}	

	@Port(
		direction = Direction.INPUT,
		name = NAME,
		description = "The name for the genome index in order to identify it later.",
		allowNull = false,
		order = 2,
		extras = "required",
		validateMethod = "validateName"
	)
	public void setName(String name) {
		this.name = name;
	}

	public void validateName(String name) {
		OperationsUtils.validateName(name);
	}

	@Port(
		direction = Direction.INPUT,
		name = GENOME,
		description = "The reference genome with which the index was built.",
		allowNull = true,
		order = 3,
		extras = EXTRAS_GENOME_FA_FILES,
		advanced = true
	)
	public void setFile(File file) {
		this.file = file;

		this.runOperation();
	}

	private void runOperation() {
		try {
			DefaultAppController.getInstance()
				.getReferenceGenomeDatabaseManager()
				.addIndex(createReferenceGenome()
			);
			DefaultReferenceGenomeIndexDatabaseManager.getInstance().persistDatabase();
			invokeLater(this::succeed);
		} catch (IOException e) {
			Workbench.getInstance().error(e, e.getMessage());
		}
	}

	private ReferenceGenomeIndex createReferenceGenome() {
		return new DefaultBowtie2ReferenceGenomeIndex(this.name, this.file,
			this.indexDir);
	}

	private void succeed() {
		Workbench.getInstance().info("Bowtie2 index successfully imported.");
	}

	@Progress(
		progressDialogTitle = "Progress",
		workingLabel = "Importing Bowtie2 index",
		preferredHeight = 200,
		preferredWidth = 300
	)
	public void progress() {};
}
