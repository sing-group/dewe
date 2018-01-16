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
package org.sing_group.rnaseq.aibench.operations.genome;

import static javax.swing.SwingUtilities.invokeLater;
import static org.sing_group.rnaseq.aibench.gui.dialogs.ReferenceGenomeOperationParamsWindow.GENOME;
import static org.sing_group.rnaseq.aibench.gui.dialogs.ReferenceGenomeOperationParamsWindow.NAME;
import static org.sing_group.rnaseq.aibench.gui.util.PortConfiguration.EXTRAS_GENOME_FA_FILES;
import static org.sing_group.rnaseq.core.util.FileUtils.removeExtension;

import java.io.File;
import java.io.IOException;

import org.sing_group.rnaseq.aibench.operations.util.OperationsUtils;
import org.sing_group.rnaseq.api.environment.execution.ExecutionException;
import org.sing_group.rnaseq.core.controller.DefaultAppController;
import org.sing_group.rnaseq.core.persistence.DefaultReferenceGenomeIndexDatabaseManager;
import org.sing_group.rnaseq.core.persistence.entities.DefaultBowtie2ReferenceGenomeIndex;

import es.uvigo.ei.aibench.core.operation.annotation.Direction;
import es.uvigo.ei.aibench.core.operation.annotation.Operation;
import es.uvigo.ei.aibench.core.operation.annotation.Port;
import es.uvigo.ei.aibench.core.operation.annotation.Progress;
import es.uvigo.ei.aibench.workbench.Workbench;

@Operation(
	name = "Build Bowtie2 index",
	description = "Builds a genome index using Bowtie2."
)
public class Bowtie2BuildIndex {
	private String name;
	private File file;
	private File outputDir;

	@Port(
		direction = Direction.INPUT,
		name = GENOME,
		description = "The reference genome file for which the index will be created.",
		allowNull = false,
		order = 1,
		extras = EXTRAS_GENOME_FA_FILES
	)
	public void setFile(File file) {
		this.file = file;
	}

	@Port(
		direction = Direction.INPUT,
		name = NAME,
		description = "The name for the genome index in order to identify it later.",
		allowNull = false,
		order = 2,
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
		name = "Output folder",
		description = "The folder where the index will be built.",
		allowNull = true,
		order = 3,
		extras = "selectionMode=directories",
		advanced = true
	)
	public void setOutputFolder(File outputDir) {
		this.outputDir = outputDir == null ? this.file.getParentFile() : outputDir;

		this.runOperation();
	}

	private void runOperation() {
		try {
			createIndex();
			addIndexToDatabase();
			invokeLater(this::succeed);
		} catch (ExecutionException e) {
			Workbench.getInstance().error(e, e.getMessage());
		} catch (InterruptedException e) {
			Workbench.getInstance().error(e, e.getMessage());
		} catch (IOException e) {
			Workbench.getInstance().error(e, e.getMessage());
		}
	}

	private void createIndex() throws ExecutionException, InterruptedException {
		DefaultAppController.getInstance().getBowtie2Controller()
			.buildIndex(this.file, this.outputDir, getIndexName());
	}

	private String getIndexName() {
		return removeExtension(file) + "index";
	}

	private void addIndexToDatabase() throws IOException {
		DefaultAppController.getInstance()
		.getReferenceGenomeDatabaseManager().addIndex(
			new DefaultBowtie2ReferenceGenomeIndex(this.name, this.file,
				new File(outputDir, getIndexName()).getAbsolutePath())
		);

		DefaultReferenceGenomeIndexDatabaseManager.getInstance().persistDatabase();
	}

	private void succeed() {
		Workbench.getInstance().info("Bowtie2 index successfully created.");
	}

	@Progress(
		progressDialogTitle = "Progress",
		workingLabel = "Creating Bowtie2 index",
		preferredHeight = 200,
		preferredWidth = 300
	)
	public void progress() {};
}
