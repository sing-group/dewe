/*
 * #%L
 * DEWE GUI
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
package org.sing_group.rnaseq.gui.pathviewr.results;

import static javax.swing.JOptionPane.showMessageDialog;
import static org.sing_group.rnaseq.gui.util.ResultsViewerUtil.missingFilesMessage;

import java.awt.BorderLayout;
import java.io.File;
import java.util.List;

import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import org.sing_group.rnaseq.api.controller.PathfindRWorkingDirectoryController;
import org.sing_group.rnaseq.api.entities.pathfindr.PathfindRPathways;
import org.sing_group.rnaseq.core.controller.DefaultPathfindRWorkingDirectoryController;
import org.sing_group.rnaseq.core.entities.pathfindr.DefaultPathfindRPathways;
import org.sing_group.rnaseq.gui.pathviewr.PathfindRPathwaysTable;

/**
 * A component that shows the genes table in an pathfindR working directory.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class PathfindRResultsViewer extends JPanel {
	private static final long serialVersionUID = 1L;

	private PathfindRWorkingDirectoryController workingDirectoryController;
	private JTabbedPane tablesTabbedPane;
	private PathfindRPathwaysTable pathwaysTable;

	/**
	 * Creates a new {@code PathfindRResultsViewer} for viewing tables in the
	 * specified pathfindR {@code workingDirectory}.
	 *
	 * @param workingDirectory the edgeRworking directory
	 */
	public PathfindRResultsViewer(File workingDirectory) {
		super(new BorderLayout());

		this.workingDirectoryController =
			new DefaultPathfindRWorkingDirectoryController(workingDirectory);
		this.init();
		SwingUtilities.invokeLater(this::checkMissingWorkingDirectoryFiles);
	}

	private void init() {
		this.add(getToolbar(), BorderLayout.NORTH);
		this.add(getTablesTabbedPane(), BorderLayout.CENTER);
	}

	private JComponent getToolbar() {
		JToolBar toolbar = new JToolBar(JToolBar.HORIZONTAL);
		toolbar.setFloatable(false);
		toolbar.setBorder(new EmptyBorder(5, 10, 5, 10));
		toolbar.add(Box.createHorizontalGlue());

		return toolbar;
	}


	private JComponent getTablesTabbedPane() {
		if(tablesTabbedPane == null) {
			tablesTabbedPane = new JTabbedPane();
			tablesTabbedPane.add("Enriched Pathways", new JScrollPane(getGenesTable()));
		}
		return tablesTabbedPane;
	}

	private JComponent getGenesTable() {
		if (pathwaysTable == null) {
			pathwaysTable = new PathfindRPathwaysTable(getPathways());
		}
		return pathwaysTable;
	}

	private PathfindRPathways getPathways() {
		return workingDirectoryController.getPathways()
					.orElse(new DefaultPathfindRPathways());
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
