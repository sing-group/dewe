/*
 * #%L
 * DEWE GUI
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
package org.sing_group.rnaseq.gui.ballgownedgeroverlaps.results;

import static javax.swing.JOptionPane.showMessageDialog;
import static org.sing_group.rnaseq.gui.util.ResultsViewerUtil.missingFilesMessage;

import java.awt.BorderLayout;
import java.awt.Window;
import java.io.File;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.Action;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import org.sing_group.gc4s.dialog.WorkingDialog;
import org.sing_group.gc4s.ui.icons.Icons;
import org.sing_group.gc4s.utilities.ExtendedAbstractAction;
import org.sing_group.rnaseq.api.controller.BallgownEdgeROverlapsWorkingDirectoryController;
import org.sing_group.rnaseq.api.entities.ballgownedgeroverlaps.BallgownEdgeROverlaps;
import org.sing_group.rnaseq.api.environment.execution.ExecutionException;
import org.sing_group.rnaseq.api.environment.execution.parameters.VennDiagramConfigurationParameter;
import org.sing_group.rnaseq.core.controller.DefaultBallgownEdgeROverlapsWorkingDirectoryController;
import org.sing_group.rnaseq.core.entities.ballgownedgeroverlaps.DefaultBallgownEdgeROverlaps;
import org.sing_group.rnaseq.gui.ballgownedgeroverlaps.BallgownEdgeROverlapsTable;
import org.sing_group.rnaseq.gui.components.configuration.VennDiagramConfigurationDialog;

/**
 * A component that shows the genes table in a Ballgown and edgeR DE
 * overlaps working directory.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class BallgownEdgeROverlapsViewer extends JPanel {
	private static final long serialVersionUID = 1L;

	private BallgownEdgeROverlapsWorkingDirectoryController workingDirectoryController;
	private JTabbedPane tablesTabbedPane;
	private BallgownEdgeROverlapsTable overlapsTable;

	/**
	 * Creates a new {@code PathfindRResultsViewer} for viewing tables in the
	 * specified pathfindR {@code workingDirectory}.
	 *
	 * @param workingDirectory the edgeRworking directory
	 */
	public BallgownEdgeROverlapsViewer(File workingDirectory) {
		super(new BorderLayout());

		this.workingDirectoryController =
			new DefaultBallgownEdgeROverlapsWorkingDirectoryController(workingDirectory);
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
		toolbar.add(new JButton(getGenerateVennDiagram()));
		toolbar.add(Box.createHorizontalGlue());

		return toolbar;
	}


	private JComponent getTablesTabbedPane() {
		if(tablesTabbedPane == null) {
			tablesTabbedPane = new JTabbedPane();
			tablesTabbedPane.add("Ballgown and edgeR DE Overlaps", new JScrollPane(getGenesTable()));
		}
		return tablesTabbedPane;
	}

	private JComponent getGenesTable() {
		if (overlapsTable == null) {
			overlapsTable = new BallgownEdgeROverlapsTable(getOverlaps());
		}
		return overlapsTable;
	}

	private BallgownEdgeROverlaps getOverlaps() {
		return workingDirectoryController.getOverlaps()
					.orElse(new DefaultBallgownEdgeROverlaps());
	}

	private void checkMissingWorkingDirectoryFiles() {
		List<String> missingFiles = this.workingDirectoryController
			.getMissingWorkingDirectoryFiles();
		if (!missingFiles.isEmpty()) {
			showMessageDialog(this, missingFilesMessage(missingFiles),
				"Warning", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	private Action getGenerateVennDiagram() {
		return new ExtendedAbstractAction(
			"Venn diagram",
			Icons.ICON_IMAGE_24,
			this::generateOverlapsFigure
		);
	}

	private void generateOverlapsFigure() {
		VennDiagramConfigurationDialog dialog = new VennDiagramConfigurationDialog(
			getDialogParent());
		dialog.setVisible(true);

		if (!dialog.isCanceled()) {
			createVennDiagram(dialog.getImageConfiguration());
		}
	}

	private void createVennDiagram(
			VennDiagramConfigurationParameter imageConfiguration) {
		generateFigure(imageConfiguration, t -> {
			try {
				this.workingDirectoryController.createVennDiagram(t);
			} catch (ExecutionException | InterruptedException e) {
				showFigureError();
			}
		});
	}

	private void generateFigure(VennDiagramConfigurationParameter imageConfiguration,
		Consumer<VennDiagramConfigurationParameter> method
	) {
		WorkingDialog dialog = new WorkingDialog(getDialogParent(),
			"Generating figure", "Generating figure");
		Thread dialogThread = new Thread(() -> {
			dialog.setVisible(true);

			method.accept(imageConfiguration);

			dialog.finished("Finished");
			dialog.dispose();
		});
		dialogThread.start();
	}

	private void showFigureError() {
		JOptionPane.showMessageDialog(this,
			"An error occurred generating the figure."
			+ "Please, check the error log", "Error",
			JOptionPane.ERROR_MESSAGE);
	}

	private Window getDialogParent() {
		return SwingUtilities.getWindowAncestor(this);
	}

}
