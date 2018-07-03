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
package org.sing_group.rnaseq.gui.ballgown.results;

import static java.lang.String.valueOf;
import static java.util.stream.Collectors.toList;
import static javax.swing.JOptionPane.showMessageDialog;
import static org.sing_group.rnaseq.gui.util.ResultsViewerUtil.missingFilesMessage;

import java.awt.BorderLayout;
import java.awt.Window;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import javax.swing.Action;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.event.PopupMenuEvent;

import org.sing_group.gc4s.dialog.JProgressDialog;
import org.sing_group.gc4s.dialog.WorkingDialog;
import org.sing_group.gc4s.event.PopupMenuAdapter;
import org.sing_group.gc4s.ui.icons.Icons;
import org.sing_group.gc4s.ui.menu.HamburgerMenu;
import org.sing_group.gc4s.ui.menu.HamburgerMenu.Size;
import org.sing_group.gc4s.utilities.ExtendedAbstractAction;
import org.sing_group.rnaseq.api.controller.BallgownWorkingDirectoryController;
import org.sing_group.rnaseq.api.entities.ballgown.BallgownGenes;
import org.sing_group.rnaseq.api.entities.ballgown.BallgownTranscripts;
import org.sing_group.rnaseq.api.environment.execution.ExecutionException;
import org.sing_group.rnaseq.api.environment.execution.parameters.ImageConfigurationParameter;
import org.sing_group.rnaseq.core.controller.DefaultBallgownWorkingDirectoryController;
import org.sing_group.rnaseq.core.entities.ballgown.DefaultBallgownGenes;
import org.sing_group.rnaseq.core.entities.ballgown.DefaultBallgownTranscripts;
import org.sing_group.rnaseq.core.io.ballgown.BallgownGenesCsvFileLoader;
import org.sing_group.rnaseq.core.io.ballgown.BallgownTranscriptsCsvFileLoader;
import org.sing_group.rnaseq.gui.ballgown.BallgownGenesTable;
import org.sing_group.rnaseq.gui.ballgown.BallgownTranscriptsTable;
import org.sing_group.rnaseq.gui.ballgown.ExportFilteredGenesTableDialog;
import org.sing_group.rnaseq.gui.ballgown.ExportFilteredTranscriptsTableDialog;
import org.sing_group.rnaseq.gui.ballgown.ExportTableDialog;
import org.sing_group.rnaseq.gui.components.configuration.FigureConfigurationDialog;
import org.sing_group.rnaseq.gui.util.CommonFileChooser;

/**
 * A component that shows the genes and transcripts tables in a Ballgown working
 * directory.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class BallgownResultsViewer extends JPanel {
	private static final long serialVersionUID = 1L;

	private BallgownWorkingDirectoryController workingDirectoryController;
	private JTabbedPane tablesTabbedPane;
	private BallgownGenesTable genesTable;
	private BallgownGenesTable filteredGenesTable;
	private BallgownGenesTable significantFilteredGenesTable;
	private BallgownTranscriptsTable transcriptsTable;
	private BallgownTranscriptsTable filteredTranscriptsTable;
	private BallgownTranscriptsTable significantFilteredTranscriptsTable;
	private ExtendedAbstractAction transcriptsExpressionLevelsFigureAction;
	private ExtendedAbstractAction filteredTranscriptsExpressionLevelsFigureAction;
	private ExtendedAbstractAction significantFilteredTranscriptsExpressionLevelsFigureAction;
	private ExtendedAbstractAction transcriptsFigureAction;
	private ExtendedAbstractAction filteredTranscriptsFigureAction;
	private ExtendedAbstractAction significantFilteredTranscriptsFigureAction;

	/**
	 * Creates a new {@code BallgownResultsViewer} for viewing tables in the
	 * specified Ballgown {@code workingDirectory}.
	 *
	 * @param workingDirectory the Ballgown working directory
	 */
	public BallgownResultsViewer(File workingDirectory) {
		super(new BorderLayout());

		this.workingDirectoryController =
			new DefaultBallgownWorkingDirectoryController(workingDirectory);
		this.init();
		SwingUtilities.invokeLater(this::checkMissingWorkingDirectoryFiles);
	}

	private void init() {
		this.add(getToolbar(), BorderLayout.NORTH);
		this.add(getTablesTabbedPane(), BorderLayout.CENTER);
	}

	private JComponent getToolbar() {
		HamburgerMenu figuresMenu = new HamburgerMenu(Size.SIZE16);
		figuresMenu.add(getGenerateFkpmAcrossSamplesFigure());
		figuresMenu.add(getGenerateGenesPvalDistFigure());
		figuresMenu.add(getGenerateTranscriptsPvalDistFigure());
		figuresMenu.add(getGenerateFoldChangesDistributionFigure());
		figuresMenu.add(getGenerateVolcanoFigure());
		figuresMenu.add(getGenerateFpkmConditionsCorrelationFigure());
		figuresMenu.add(getGenerateFpkmConditionsDensityFigure());
		figuresMenu.add(getGeneratePcaFigure());
		figuresMenu.add(getGenerateHeatmapFigure());

		JToolBar toolbar = new JToolBar(JToolBar.HORIZONTAL);
		toolbar.setFloatable(false);
		toolbar.setBorder(new EmptyBorder(5, 10, 5, 10));

		toolbar.add(new JButton(getOpenGenesTableAction()));
		toolbar.add(new JButton(getOpenTranscriptsTableAction()));
		toolbar.add(Box.createHorizontalGlue());
		toolbar.add(figuresMenu);

		return toolbar;
	}

	private Action getGenerateFkpmAcrossSamplesFigure() {
		return new ExtendedAbstractAction(
			"FKPM across samples",
			Icons.ICON_IMAGE_24,
			this::generateFkpmAcrossSamplesFigure
		);
	}

	private void generateFkpmAcrossSamplesFigure() {
		FigureConfigurationDialog dialog = new FigureConfigurationDialog(
			getDialogParent());
		dialog.setVisible(true);

		if (!dialog.isCanceled()) {
			generateFkpmAcrossSamplesFigure(dialog.getImageConfiguration());
		}
	}

	private void generateFkpmAcrossSamplesFigure(
		ImageConfigurationParameter imageConfiguration) {
		generateFigure(imageConfiguration, t -> {
			try {
				this.workingDirectoryController
					.createFpkmDistributionAcrossSamplesFigure(t);
			} catch (ExecutionException | InterruptedException e) {
				showFigureError();
			}
		});
	}

	private void showFigureError() {
		JOptionPane.showMessageDialog(this,
			"An error occurred generating the figure."
			+ "Please, check the error log", "Error",
			JOptionPane.ERROR_MESSAGE);
	}

	private Action getGenerateGenesPvalDistFigure() {
		return new ExtendedAbstractAction(
			"DE genes p-values distribution",
			Icons.ICON_IMAGE_24,
			this::generateGenesPvalDistributionFigure
		);
	}

	private void generateGenesPvalDistributionFigure() {
		FigureConfigurationDialog dialog = new FigureConfigurationDialog(
			getDialogParent());
		dialog.setVisible(true);

		if (!dialog.isCanceled()) {
			generateGenesPvalDistributionFigure(dialog.getImageConfiguration());
		}
	}

	private void generateGenesPvalDistributionFigure(
		ImageConfigurationParameter imageConfiguration) {
		generateFigure(imageConfiguration, t -> {
			try {
				this.workingDirectoryController.createGenesDEpValuesFigure(t);
			} catch (ExecutionException | InterruptedException e) {
				showFigureError();
			}
		});
	}

	private Action getGenerateFoldChangesDistributionFigure() {
		return new ExtendedAbstractAction(
			"DE fold changes values distribution",
			Icons.ICON_IMAGE_24,
			this::generateFoldChangesDistributionFigure
		);
	}

	private void generateFoldChangesDistributionFigure() {
		FigureConfigurationDialog dialog = new FigureConfigurationDialog(
			getDialogParent());
		dialog.setVisible(true);

		if (!dialog.isCanceled()) {
			generateFoldChangesDistributionFigure(
				dialog.getImageConfiguration());
		}
	}

	private void generateFoldChangesDistributionFigure(
		ImageConfigurationParameter imageConfiguration) {
		generateFigure(imageConfiguration, t -> {
			try {
				this.workingDirectoryController
					.createDEfoldChangeValuesDistributionFigure(imageConfiguration);
			} catch (ExecutionException | InterruptedException e) {
				showFigureError();
			}
		});
	}

	private Action getGenerateTranscriptsPvalDistFigure() {
		return new ExtendedAbstractAction(
			"DE transcripts p-values distribution",
			Icons.ICON_IMAGE_24,
			this::generateTranscriptsPvalDistributionFigure
		);
	}

	private void generateTranscriptsPvalDistributionFigure() {
		FigureConfigurationDialog dialog = new FigureConfigurationDialog(
			getDialogParent());
		dialog.setVisible(true);

		if (!dialog.isCanceled()) {
			generateTranscriptsPvalDistributionFigure(
				dialog.getImageConfiguration());
		}
	}

	private void generateTranscriptsPvalDistributionFigure(
		ImageConfigurationParameter imageConfiguration) {
		generateFigure(imageConfiguration, t -> {
			try {
				this.workingDirectoryController.createTranscriptsDEpValuesFigure(t);
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
			generateVolcanoFigure(
				dialog.getImageConfiguration());
		}
	}

	private void generateVolcanoFigure(
		ImageConfigurationParameter imageConfiguration) {
		generateFigure(imageConfiguration, t -> {
			try {
				this.workingDirectoryController.createVolcanoFigure(imageConfiguration);
			} catch (ExecutionException | InterruptedException e) {
				showFigureError();
			}
		});
	}

	private Action getGenerateFpkmConditionsCorrelationFigure() {
		return new ExtendedAbstractAction(
			"FPKMs conditions correlation",
			Icons.ICON_IMAGE_24,
			this::generateFpkmConditionsCorrelationFigure
		);
	}

	private void generateFpkmConditionsCorrelationFigure() {
		FigureConfigurationDialog dialog = new FigureConfigurationDialog(
			getDialogParent());
		dialog.setVisible(true);

		if (!dialog.isCanceled()) {
			generateFpkmConditionsCorrelationFigure(
				dialog.getImageConfiguration());
		}
	}

	private void generateFpkmConditionsCorrelationFigure(
		ImageConfigurationParameter imageConfiguration) {
		generateFigure(imageConfiguration, t -> {
			try {
				this.workingDirectoryController.createFpkmConditionsCorrelationFigure(t);
			} catch (ExecutionException | InterruptedException e) {
				showFigureError();
			}
		});
	}

	private Action getGenerateFpkmConditionsDensityFigure() {
		return new ExtendedAbstractAction(
			"FPKMs conditions density",
			Icons.ICON_IMAGE_24,
			this::generateFpkmConditionsDensityFigure
		);
	}

	private void generateFpkmConditionsDensityFigure() {
		FigureConfigurationDialog dialog = new FigureConfigurationDialog(
			getDialogParent());
		dialog.setVisible(true);

		if (!dialog.isCanceled()) {
			generateFpkmConditionsDensityFigure(
				dialog.getImageConfiguration());
		}
	}

	private void generateFpkmConditionsDensityFigure(
		ImageConfigurationParameter imageConfiguration) {
		generateFigure(imageConfiguration, t -> {
			try {
				this.workingDirectoryController.createFpkmConditionsDensityFigure(t);
			} catch (ExecutionException | InterruptedException e) {
				showFigureError();
			}
		});
	}

	private Action getGeneratePcaFigure() {
		return new ExtendedAbstractAction(
			"Principal Component Analysis",
			Icons.ICON_IMAGE_24,
			this::generatePcaFigure
		);
	}

	private void generatePcaFigure() {
		FigureConfigurationDialog dialog = new FigureConfigurationDialog(
			getDialogParent());
		dialog.setVisible(true);

		if (!dialog.isCanceled()) {
			generatePcaFigure(
				dialog.getImageConfiguration());
		}
	}

	private void generatePcaFigure(
		ImageConfigurationParameter imageConfiguration) {
		generateFigure(imageConfiguration, t -> {
			try {
				this.workingDirectoryController.createPcaFigure(t);
			} catch (ExecutionException | InterruptedException e) {
				showFigureError();
			}
		});
	}

	private Action getGenerateHeatmapFigure() {
		return new ExtendedAbstractAction(
			"Heatmap",
			Icons.ICON_IMAGE_24,
			this::generateHeatmaFigure
		);
	}

	private void generateHeatmaFigure() {
		HeatmapFigureConfigurationDialog dialog = new HeatmapFigureConfigurationDialog(
			getDialogParent());
		dialog.setVisible(true);

		if (!dialog.isCanceled()) {
			int numClusters = dialog.getClustersNumber();
			generateHeatmaFigure(dialog.getImageConfiguration(), numClusters);
		}
	}

	private void generateHeatmaFigure(
		ImageConfigurationParameter imageConfiguration, int numClusters) {
		generateFigure(imageConfiguration, t -> {
			try {
				this.workingDirectoryController.createHeatmapFigure(t, numClusters);
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

	private Action getOpenGenesTableAction() {
		return new ExtendedAbstractAction(
			"Load genes",
			Icons.ICON_TABLE_24,
			this::openGenesTable
		);
	}

	private void openGenesTable() {
		Optional<File> genesFile = selectTsvFile();
		if(genesFile.isPresent()) {
			loadAndShowGenesFile(genesFile.get());
		}
	}

	private Action getOpenTranscriptsTableAction() {
		return new ExtendedAbstractAction(
			"Load transcripts",
			Icons.ICON_TABLE_24,
			this::openTranscriptsTable
		);
	}

	private void openTranscriptsTable() {
		Optional<File> transcriptsFile = selectTsvFile();
		if(transcriptsFile.isPresent()) {
			loadAndShowTranscriptsFile(transcriptsFile.get());
		}
	}

	private Optional<File> selectTsvFile() {
		JFileChooser fileChooser = CommonFileChooser.getInstance()
			.getSingleFilechooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		int returnVal = fileChooser.showOpenDialog(getDialogParent());
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			return Optional.of(fileChooser.getSelectedFile());
		} else {
			return Optional.empty();
		}
	}

	private JComponent getTablesTabbedPane() {
		if(tablesTabbedPane == null) {
			tablesTabbedPane = new JTabbedPane();
			tablesTabbedPane.add("Genes", new JScrollPane(getGenesTable()));
			tablesTabbedPane.add("Filtered genes",
				new JScrollPane(getFilteredGenesTable()));
			tablesTabbedPane.add("Significant filtered genes",
				new JScrollPane(getSignificantFilteredGenesTable()));
			tablesTabbedPane.add("Transcripts",
				new JScrollPane(getTranscriptsTable()));
			tablesTabbedPane.add("Filtered transcripts",
				new JScrollPane(getFilteredTranscriptsTable()));
			tablesTabbedPane.add("Significant filtered transcripts",
				new JScrollPane(getSignificantFilteredTranscriptsTable()));
		}
		return tablesTabbedPane;
	}

	private JComponent getGenesTable() {
		if (genesTable == null) {
			genesTable = new BallgownGenesTable(getGenes());
		}
		return genesTable;
	}

	private BallgownGenes getGenes() {
		return workingDirectoryController.getGenes()
					.orElse(new DefaultBallgownGenes());
	}

	private JComponent getFilteredGenesTable() {
		if (filteredGenesTable == null) {
			filteredGenesTable = new BallgownGenesTable(getFilteredGenes());
			this.filteredGenesTable.addAction(
				new ExtendedAbstractAction("Filter and export genes",
				this::exportFilteredGenesTable)
			);
		}
		return filteredGenesTable;
	}

	private BallgownGenes getFilteredGenes() {
		return workingDirectoryController.getFilteredGenes()
			.orElse(new DefaultBallgownGenes());
	}

	private void exportFilteredGenesTable() {
		ExportTableDialog dialog = new ExportFilteredGenesTableDialog(
			getDialogParent());
		dialog.setVisible(true);

		if(!dialog.isCanceled()) {
			WorkingDialog progressDialog = new WorkingDialog(getDialogParent(),
				"Exporting table", "Exporting table");
			Thread dialogThread = new Thread(() -> {
				progressDialog.setVisible(true);

				exportFilteredGenesTable(dialog.getPvalue());

				progressDialog.finished("Finished");
				progressDialog.dispose();
			});
			dialogThread.start();
		}
	}

	private void exportFilteredGenesTable(double pvalue) {
		try {
			File genesFile = this.workingDirectoryController
				.exportFilteredGenesTable(pvalue);
			loadAndShowGenesFile(genesFile);
		} catch (ExecutionException | InterruptedException e) {
			JOptionPane.showMessageDialog(this,
				"There was an error writing the genes table. "
				+ "Please, check the error log", "Output error",
				JOptionPane.ERROR_MESSAGE);
		}
	}

	private void loadAndShowGenesFile(File genesFile) {
		try {
			BallgownGenes genes = BallgownGenesCsvFileLoader.loadFile(genesFile);
			tablesTabbedPane.add(genesFile.getName(),
				new JScrollPane(new BallgownGenesTable(genes)));
			tablesTabbedPane.setSelectedIndex(tablesTabbedPane.getTabCount()-1);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this,
				"I/O error loading genes file" +
					genesFile.getAbsolutePath(), "I/O error",
				JOptionPane.ERROR_MESSAGE);
		}
	}

	private JComponent getSignificantFilteredGenesTable() {
		if (significantFilteredGenesTable == null) {
			significantFilteredGenesTable = new BallgownGenesTable(
				getSignificantFilteredGenes());
		}
		return significantFilteredGenesTable;
	}

	private BallgownGenes getSignificantFilteredGenes() {
		return workingDirectoryController.getSignificantFilteredGenes()
					.orElse(new DefaultBallgownGenes());
	}

	private JComponent getTranscriptsTable() {
		if (transcriptsTable == null) {
			transcriptsTable = new BallgownTranscriptsTable(getTranscripts());
			transcriptsTable.setComponentPopupMenu(
				createTablePopupMenu(
					transcriptsTable,
					getTranscriptsFigureAction(),
					getTranscriptsExpressionLevelsFigureAction()
				)
			);
		}
		return transcriptsTable;
	}

	private BallgownTranscripts getTranscripts() {
		return workingDirectoryController.getTranscripts()
					.orElse(new DefaultBallgownTranscripts());
	}

	private JComponent getFilteredTranscriptsTable() {
		if (filteredTranscriptsTable == null) {
			filteredTranscriptsTable = new BallgownTranscriptsTable(
				getFilteredTranscripts());
			filteredTranscriptsTable.setComponentPopupMenu(
				createTablePopupMenu(
					filteredTranscriptsTable,
					getFilteredTranscriptsFigureAction(),
					getFilteredTranscriptsExpressionLevelsFigureAction()
				)
			);
			filteredTranscriptsTable.addAction(
				new ExtendedAbstractAction("Filter and export transcripts",
				this::exportFilteredTranscriptsTable)
			);
		}
		return filteredTranscriptsTable;
	}


	private BallgownTranscripts getFilteredTranscripts() {
		return workingDirectoryController.getFilteredTranscripts()
					.orElse(new DefaultBallgownTranscripts());
	}

	private void exportFilteredTranscriptsTable() {
		ExportTableDialog dialog = new ExportFilteredTranscriptsTableDialog(
			getDialogParent());
		dialog.setVisible(true);

		if(!dialog.isCanceled()) {
			WorkingDialog progressDialog = new WorkingDialog(getDialogParent(),
				"Exporting table", "Exporting table");
			Thread dialogThread = new Thread(() -> {
				progressDialog.setVisible(true);

				exportFilteredTranscriptsTable(dialog.getPvalue());

				progressDialog.finished("Finished");
				progressDialog.dispose();
			});
			dialogThread.start();
		}
	}

	private void exportFilteredTranscriptsTable(double pvalue) {
		try {
			File transcriptsFile = this.workingDirectoryController
				.exportFilteredTranscriptsTable(pvalue);
			loadAndShowTranscriptsFile(transcriptsFile);
		} catch (ExecutionException | InterruptedException e) {
			JOptionPane.showMessageDialog(this,
				"There was an error writing the transcripts table. "
				+ "Please, check the error log", "Output error",
				JOptionPane.ERROR_MESSAGE);
		}
	}

	private void loadAndShowTranscriptsFile(File transcriptsFile) {
		try {
			BallgownTranscripts transcripts = BallgownTranscriptsCsvFileLoader
				.loadFile(transcriptsFile);
			tablesTabbedPane.add(transcriptsFile.getName(),
				new JScrollPane(new BallgownTranscriptsTable(transcripts)));
			tablesTabbedPane.setSelectedIndex(tablesTabbedPane.getTabCount()-1);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this,
				"I/O error loading transcripts file" +
				transcriptsFile.getAbsolutePath(), "I/O error",
				JOptionPane.ERROR_MESSAGE);
		}
	}

	private JComponent getSignificantFilteredTranscriptsTable() {
		if (significantFilteredTranscriptsTable == null) {
			significantFilteredTranscriptsTable = new BallgownTranscriptsTable(
				getSignificantFilteredTranscripts());
			significantFilteredTranscriptsTable.setComponentPopupMenu(
				createTablePopupMenu(
					significantFilteredTranscriptsTable,
					getSignificantFilteredTranscriptsFigureAction(),
					getSignificantFilteredTranscriptsExpressionLevelsFigureAction()
				)
			);
		}
		return significantFilteredTranscriptsTable;
	}

	private BallgownTranscripts getSignificantFilteredTranscripts() {
		return workingDirectoryController.getSignificantFilteredTranscripts()
			.orElse(new DefaultBallgownTranscripts());
	}

	/**
	 * Creates a popup menu for the specified {@code table} that contains the
	 * specified {@code actions}. Moreover, it adds a popup menu listener that
	 * only shows the actions when at least one row is selected in the specified
	 * {@code table}.
	 *
	 * @param table the table for which the popup menu must be created
	 * @param actions the list of {@code Action} in the menu
	 *
	 * @return the popuplated {@code JPopupMenu}
	 */
	protected static JPopupMenu createTablePopupMenu(JTable table,
		Action... actions
	) {
		JPopupMenu menu = new JPopupMenu();
		for (Action action : actions) {
			menu.add(action);
			menu.addPopupMenuListener(new PopupMenuAdapter() {

				@Override
				public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
					action.setEnabled(table.getSelectedRowCount() > 0);
				}
			});
		}

		return menu;
	}

	private Action getTranscriptsFigureAction() {
		transcriptsFigureAction = new ExtendedAbstractAction(
			"Create FKPM distribution figure", () -> {
				createFkpmTranscriptsFigure(transcriptsTable);
			});
		transcriptsFigureAction.setEnabled(false);

		return transcriptsFigureAction;
	}

	private Action getFilteredTranscriptsFigureAction() {
		filteredTranscriptsFigureAction = new ExtendedAbstractAction(
			"Create FKPM distribution figure", () -> {
				createFkpmTranscriptsFigure(filteredTranscriptsTable);
			});
		filteredTranscriptsFigureAction.setEnabled(false);

		return filteredTranscriptsFigureAction;
	}

	private Action getSignificantFilteredTranscriptsFigureAction() {
		significantFilteredTranscriptsFigureAction = new ExtendedAbstractAction(
			"Create FKPM distribution figure", () -> {
				createFkpmTranscriptsFigure(significantFilteredTranscriptsTable);
			});
		significantFilteredTranscriptsFigureAction.setEnabled(false);

		return significantFilteredTranscriptsFigureAction;
	}

	private Action getTranscriptsExpressionLevelsFigureAction() {
		transcriptsExpressionLevelsFigureAction = new ExtendedAbstractAction(
			"Create expression levels figure", () -> {
				createGenesExpressionLevelsFigure(transcriptsTable);
			});
		transcriptsExpressionLevelsFigureAction.setEnabled(false);

		return transcriptsExpressionLevelsFigureAction;
	}

	private Action getFilteredTranscriptsExpressionLevelsFigureAction() {
		filteredTranscriptsExpressionLevelsFigureAction = new ExtendedAbstractAction(
			"Create expression levels figure", () -> {
				createGenesExpressionLevelsFigure(filteredTranscriptsTable);
			});
		filteredTranscriptsExpressionLevelsFigureAction.setEnabled(false);

		return filteredTranscriptsExpressionLevelsFigureAction;
	}

	private Action getSignificantFilteredTranscriptsExpressionLevelsFigureAction() {
		significantFilteredTranscriptsExpressionLevelsFigureAction =
			new ExtendedAbstractAction(
				"Create expression levels figure", () -> {
					createGenesExpressionLevelsFigure(significantFilteredTranscriptsTable);
				});
		significantFilteredTranscriptsExpressionLevelsFigureAction.setEnabled(false);

		return significantFilteredTranscriptsExpressionLevelsFigureAction;
	}

	private void createFkpmTranscriptsFigure(JTable transcriptsTable) {
		FkpmTranscriptsFigureConfigurationDialog dialog =
			new FkpmTranscriptsFigureConfigurationDialog(getDialogParent());

		dialog.setVisible(true);
		if (!dialog.isCanceled()) {
			createFkpmTranscriptsFigure(
				getSelectedTranscriptIds(transcriptsTable),
				dialog.getImageConfiguration()
			);
		}
	}

	private void createFkpmTranscriptsFigure(List<String> transcriptsIds,
		ImageConfigurationParameter imageConfiguration
	) {
		JProgressDialog dialog = new JProgressDialog(getDialogParent(),
			"Transcripts figures generation", transcriptsIds,
			"Processing transcript: "
		);
		Thread dialogThread = new Thread(() -> {
			dialog.setVisible(true);

			transcriptsIds.stream().forEach(transcriptId -> {
				try {
					this.workingDirectoryController
						.createFpkmDistributionFigureForTranscript(transcriptId,
							imageConfiguration);
				} catch (ExecutionException | InterruptedException e) {
					e.printStackTrace();
				}
				dialog.nextTask();
			});

			dialog.finished("Finished");
			dialog.dispose();
		});
		dialogThread.start();
	}

	private void createGenesExpressionLevelsFigure(JTable transcriptsTable) {
		ExpressionLevelsFigureConfigurationDialog dialog =
			new ExpressionLevelsFigureConfigurationDialog(
				getDialogParent(), getSamples()
			);

		dialog.setVisible(true);
		if (!dialog.isCanceled()) {
			createTranscriptsExpressionLevelsFigure(
				getSelectedTranscriptIds(transcriptsTable),
				dialog.getImageConfiguration(),
				dialog.getSelectedSamples()
			);
		}
	}

	private List<String> getSamples() {
		return workingDirectoryController.getSampleNames()
					.orElse(Collections.emptyList());
	}

	private void createTranscriptsExpressionLevelsFigure(
		List<String> transcriptsIds,
		ImageConfigurationParameter imageConfigurationParameter,
		List<String> samples
	) {
		JProgressDialog dialog = new JProgressDialog(getDialogParent(),
			"Transcripts figures generation",
			createTasks(transcriptsIds, samples),
			"Processing transcript: "
		);
		Thread dialogThread = new Thread(() -> {
			dialog.setVisible(true);

			transcriptsIds.stream().forEach(transcriptId -> {
				try {
					for (String sample : samples) {
						this.workingDirectoryController
							.createExpressionLevelsFigure(transcriptId, sample,
								imageConfigurationParameter);
						dialog.nextTask();
					}
				} catch (ExecutionException | InterruptedException e) {
					e.printStackTrace();
				}
			});

			dialog.finished("Finished");
			dialog.dispose();
		});
		dialogThread.start();
	}

	private List<String> createTasks(List<String> transcriptsIds,
		List<String> samples
	) {
		List<String> tasks = new LinkedList<>();
		for (String transcript : transcriptsIds) {
			for (String sample : samples) {
				tasks.add("Sample: " + sample + " | Transcript: " + transcript);
			}
		}
		return tasks;
	}

	private Window getDialogParent() {
		return SwingUtilities.getWindowAncestor(this);
	}

	private static List<String> getSelectedTranscriptIds(
		JTable transcriptsTable
	) {
		return getSelectedModelRows(transcriptsTable).stream()
			.map(row -> valueOf(transcriptsTable.getModel().getValueAt(row, 0)))
			.collect(toList());
	}

	private static List<Integer> getSelectedModelRows(JTable transcriptsTable) {
		List<Integer> selectedRows = new LinkedList<>();
		for (int row : transcriptsTable.getSelectedRows()) {
			row = transcriptsTable.convertRowIndexToModel(row);
			selectedRows.add(row);
		}
		return selectedRows;
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
