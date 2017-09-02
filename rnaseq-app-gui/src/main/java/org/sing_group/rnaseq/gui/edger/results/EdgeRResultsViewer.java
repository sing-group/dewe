/*
 * #%L
 * DEWE GUI
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
package org.sing_group.rnaseq.gui.edger.results;

import static javax.swing.JOptionPane.showMessageDialog;
import static org.sing_group.rnaseq.gui.util.ResultsViewerUtil.missingFilesMessage;

import java.awt.BorderLayout;
import java.io.File;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

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
		SwingUtilities.invokeLater(this::checkMissingWorkingDirectoryFiles);
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

	private void checkMissingWorkingDirectoryFiles() {
		List<String> missingFiles = this.workingDirectoryController
			.getMissingWorkingDirectoryFiles();
		if (!missingFiles.isEmpty()) {
			showMessageDialog(this, missingFilesMessage(missingFiles),
				"Warning", JOptionPane.WARNING_MESSAGE);
		}
	}
}
