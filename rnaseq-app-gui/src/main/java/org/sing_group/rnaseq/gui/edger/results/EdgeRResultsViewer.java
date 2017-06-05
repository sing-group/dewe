package org.sing_group.rnaseq.gui.edger.results;

import java.awt.BorderLayout;
import java.io.File;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import org.sing_group.rnaseq.api.controller.EdgeRWorkingDirectoryController;
import org.sing_group.rnaseq.api.entities.edger.EdgeRGenes;
import org.sing_group.rnaseq.core.controller.DefaultEdgeRWorkingDirectoryController;
import org.sing_group.rnaseq.core.entities.edgeR.DefaultEdgeRGenes;
import org.sing_group.rnaseq.gui.edger.EdgeRGenesTable;

/**
 * A component that shows the genes table in an edgeR working directory.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class EdgeRResultsViewer extends JPanel {
	private static final long serialVersionUID = 1L;

	private EdgeRWorkingDirectoryController workingDirectoryController;
	private JTabbedPane tablesTabbedPane;
	private EdgeRGenesTable genesTable;

	/**
	 * Creates a new {@code EdgeRResultsViewer} for viewing tables in the
	 * specified edgeR {@code workingDirectory}.
	 *
	 * @param workingDirectory the edgeRworking directory
	 */
	public EdgeRResultsViewer(File workingDirectory) {
		super(new BorderLayout());

		this.workingDirectoryController =
			new DefaultEdgeRWorkingDirectoryController(workingDirectory);
		this.init();
	}

	private void init() {
		this.add(getTablesTabbedPane(), BorderLayout.CENTER);
	}

	private JComponent getTablesTabbedPane() {
		if(tablesTabbedPane == null) {
			tablesTabbedPane = new JTabbedPane();
			tablesTabbedPane.add("DE Genes", new JScrollPane(getGenesTable()));
		}
		return tablesTabbedPane;
	}

	private JComponent getGenesTable() {
		if (genesTable == null) {
			genesTable = new EdgeRGenesTable(getGenes());
		}
		return genesTable;
	}

	private EdgeRGenes getGenes() {
		return workingDirectoryController.getGenes()
					.orElse(new DefaultEdgeRGenes());
	}
}
