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
package org.sing_group.rnaseq.gui.edger.results;

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
import org.sing_group.rnaseq.api.controller.EdgeRWorkingDirectoryController;
import org.sing_group.rnaseq.api.entities.edger.EdgeRGenes;
import org.sing_group.rnaseq.api.environment.execution.ExecutionException;
import org.sing_group.rnaseq.api.environment.execution.parameters.ImageConfigurationParameter;
import org.sing_group.rnaseq.core.controller.DefaultEdgeRWorkingDirectoryController;
import org.sing_group.rnaseq.core.entities.edgeR.DefaultEdgeRGenes;
import org.sing_group.rnaseq.gui.components.configuration.FigureConfigurationDialog;
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
	private EdgeRGenesTable significantGenesTable;

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
		this.add(getToolbar(), BorderLayout.NORTH);
		this.add(getTablesTabbedPane(), BorderLayout.CENTER);
	}

	private JComponent getToolbar() {
		JToolBar toolbar = new JToolBar(JToolBar.HORIZONTAL);
		toolbar.setFloatable(false);
		toolbar.setBorder(new EmptyBorder(5, 10, 5, 10));

		toolbar.add(new JButton(getGenerateDEPvalDistFigure()));
		toolbar.add(new JButton(getGenerateDEValsDistFigure()));
		toolbar.add(new JButton(getGenerateVolcanoFigure()));
		toolbar.add(Box.createHorizontalGlue());

		return toolbar;
	}

	private Action getGenerateDEPvalDistFigure() {
		return new ExtendedAbstractAction(
			"DE p-values distribution",
			Icons.ICON_IMAGE_24,
			this::generateDEPvalDistributionFigure
		);
	}

	private void generateDEPvalDistributionFigure() {
		FigureConfigurationDialog dialog = new FigureConfigurationDialog(
			getDialogParent());
		dialog.setVisible(true);

		if (!dialog.isCanceled()) {
			generateDEPvalDistributionFigure(dialog.getImageConfiguration());
		}
	}

	private void generateDEPvalDistributionFigure(
		ImageConfigurationParameter imageConfiguration) {
		generateFigure(imageConfiguration, t -> {
			try {
				this.workingDirectoryController.createDEpValuesDistributionFigure(t);
			} catch (ExecutionException | InterruptedException e) {
				showFigureError();
			}
		});
	}

	private Action getGenerateDEValsDistFigure() {
		return new ExtendedAbstractAction(
			"DE fold changes values distribution",
			Icons.ICON_IMAGE_24,
			this::generateDEValsDistributionFigure
		);
	}

	private void generateDEValsDistributionFigure() {
		FigureConfigurationDialog dialog = new FigureConfigurationDialog(
			getDialogParent());
		dialog.setVisible(true);

		if (!dialog.isCanceled()) {
			generateDEValsDistributionFigure(dialog.getImageConfiguration());
		}
	}

	private void generateDEValsDistributionFigure(
		ImageConfigurationParameter imageConfiguration) {
		generateFigure(imageConfiguration, t -> {
			try {
				this.workingDirectoryController.createDEfoldChangeValuesDistributionFigure(t);
			} catch (ExecutionException | InterruptedException e) {
				showFigureError();
			}
		});
	}

	private Action getGenerateVolcanoFigure() {
		return new ExtendedAbstractAction(
			"Volcano plot",
			Icons.ICON_IMAGE_24,
			this::generateVolcanoFigure
		);
	}

	private void generateVolcanoFigure() {
		FigureConfigurationDialog dialog = new FigureConfigurationDialog(
			getDialogParent());
		dialog.setVisible(true);

		if (!dialog.isCanceled()) {
			generateVolcanoFigure(dialog.getImageConfiguration());
		}
	}

	private void generateVolcanoFigure(
		ImageConfigurationParameter imageConfiguration) {
		generateFigure(imageConfiguration, t -> {
			try {
				this.workingDirectoryController.createVolcanoFigure(t);
			} catch (ExecutionException | InterruptedException e) {
				showFigureError();
			}
		});
	}

	private void generateFigure(ImageConfigurationParameter imageConfiguration,
		Consumer<ImageConfigurationParameter> method
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

	private JComponent getTablesTabbedPane() {
		if(tablesTabbedPane == null) {
			tablesTabbedPane = new JTabbedPane();
			tablesTabbedPane.add("DE Genes", new JScrollPane(getGenesTable()));
			tablesTabbedPane.add("DE Genes (p < 0.05)", new JScrollPane(getSignificantGenesTable()));
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

	private JComponent getSignificantGenesTable() {
		if (significantGenesTable == null) {
			significantGenesTable = new EdgeRGenesTable(getSignificantGenes());
		}
		return significantGenesTable;
	}

	private EdgeRGenes getSignificantGenes() {
		return workingDirectoryController.getSignificantGenes()
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

	private Window getDialogParent() {
		return SwingUtilities.getWindowAncestor(this);
	}

}
