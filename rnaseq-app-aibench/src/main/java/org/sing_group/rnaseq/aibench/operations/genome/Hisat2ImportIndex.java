package org.sing_group.rnaseq.aibench.operations.genome;

import static org.sing_group.rnaseq.core.persistence.entities.DefaultHisat2ReferenceGenomeIndex.directoryContainsHisat2Indexes;
import static javax.swing.SwingUtilities.invokeLater;
import static org.sing_group.rnaseq.aibench.gui.dialogs.ReferenceGenomeOperationParamsWindow.NAME;
import static org.sing_group.rnaseq.aibench.gui.dialogs.ReferenceGenomeOperationParamsWindow.GENOME;
import static org.sing_group.rnaseq.aibench.gui.util.PortConfiguration.EXTRAS_GENOME_FA_FILES;

import java.io.File;
import java.io.IOException;

import org.sing_group.rnaseq.aibench.operations.util.OperationsUtils;
import org.sing_group.rnaseq.api.persistence.entities.ReferenceGenomeIndex;
import org.sing_group.rnaseq.core.controller.DefaultAppController;
import org.sing_group.rnaseq.core.persistence.DefaultReferenceGenomeIndexDatabaseManager;
import org.sing_group.rnaseq.core.persistence.entities.DefaultHisat2ReferenceGenomeIndex;

import es.uvigo.ei.aibench.core.operation.annotation.Direction;
import es.uvigo.ei.aibench.core.operation.annotation.Operation;
import es.uvigo.ei.aibench.core.operation.annotation.Port;
import es.uvigo.ei.aibench.core.operation.annotation.Progress;
import es.uvigo.ei.aibench.workbench.Workbench;

@Operation(
	name = "Import HISAT2 index",
	description = "Imports a reference genome indexed using HISAT2."
)
public class Hisat2ImportIndex {
	private String name;
	private File file;
	private File indexDir;
	
	@Port(
		direction = Direction.INPUT,
		name = "Index folder",
		description = "The directory that contains the HISAT2 genome index.",
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
		if(!directoryContainsHisat2Indexes(indexDir)) {
			throw new IllegalArgumentException(
				"Index directory must contain the HISAT2 indexes files");
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
				.addIndex(createReferengeGenome()
			);
			DefaultReferenceGenomeIndexDatabaseManager.getInstance().persistDatabase();
			invokeLater(this::succeed);
		} catch (IOException e) {
			Workbench.getInstance().error(e, e.getMessage());
		}
	}

	private ReferenceGenomeIndex createReferengeGenome() {
		return new DefaultHisat2ReferenceGenomeIndex(this.name, this.file,
			this.indexDir);
	}

	private void succeed() {
		Workbench.getInstance().info("HISAT2 index successfully imported.");
	}

	@Progress(
		progressDialogTitle = "Progress",
		workingLabel = "Importing HISAT2 index",
		preferredHeight = 200,
		preferredWidth = 300
	)
	public void progress() {};
}
