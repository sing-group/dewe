package org.sing_group.rnaseq.gui.ballgown.results;

import static java.lang.String.valueOf;
import static java.util.stream.Collectors.toList;

import java.awt.BorderLayout;
import java.awt.Window;
import java.io.File;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.event.PopupMenuEvent;

import org.sing_group.rnaseq.api.controller.BallgownWorkingDirectoryController;
import org.sing_group.rnaseq.api.entities.ballgown.BallgownGenes;
import org.sing_group.rnaseq.api.entities.ballgown.BallgownTranscripts;
import org.sing_group.rnaseq.api.environment.execution.ExecutionException;
import org.sing_group.rnaseq.api.environment.execution.parameters.ImageConfigurationParameter;
import org.sing_group.rnaseq.core.controller.DefaultBallgownWorkingDirectoryController;
import org.sing_group.rnaseq.core.entities.ballgown.DefaultBallgownGenes;
import org.sing_group.rnaseq.core.entities.ballgown.DefaultBallgownTranscripts;
import org.sing_group.rnaseq.gui.ballgown.BallgownGenesTable;
import org.sing_group.rnaseq.gui.ballgown.BallgownTranscriptsTable;

import es.uvigo.ei.sing.hlfernandez.dialog.JProgressDialog;
import es.uvigo.ei.sing.hlfernandez.event.PopupMenuAdapter;
import es.uvigo.ei.sing.hlfernandez.utilities.ExtendedAbstractAction;

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
	private BallgownTranscriptsTable transcriptsTable;
	private BallgownTranscriptsTable filteredTranscriptsTable;
	private ExtendedAbstractAction transcriptsExpressionLevelsFigureAction;
	private ExtendedAbstractAction filteredTranscriptsExpressionLevelsFigureAction;
	private ExtendedAbstractAction transcriptsFigureAction;
	private ExtendedAbstractAction filteredTranscriptsFigureAction;

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
	}

	private void init() {
		this.add(getTablesTabbedPane(), BorderLayout.CENTER);
	}

	private JComponent getTablesTabbedPane() {
		if(tablesTabbedPane == null) {
			tablesTabbedPane = new JTabbedPane();
			tablesTabbedPane.add("Genes", new JScrollPane(getGenesTable()));
			tablesTabbedPane.add("Filtered genes",
				new JScrollPane(getFilteredGenesTable()));
			tablesTabbedPane.add("Transcripts",
				new JScrollPane(getTranscriptsTable()));
			tablesTabbedPane.add("Filtered transcripts",
				new JScrollPane(getFilteredTranscriptsTable()));
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
		}
		return filteredGenesTable;
	}


	private BallgownGenes getFilteredGenes() {
		return workingDirectoryController.getFilteredGenes()
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
			filteredTranscriptsTable = new BallgownTranscriptsTable(getFilteredTranscripts());
			filteredTranscriptsTable.setComponentPopupMenu(
				createTablePopupMenu(
					filteredTranscriptsTable,
					getFilteredTranscriptsFigureAction(),
					getFilteredTranscriptsExpressionLevelsFigureAction()
				)
			);
		}
		return filteredTranscriptsTable;
	}

	private BallgownTranscripts getFilteredTranscripts() {
		return workingDirectoryController.getFilteredTranscripts()
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
}
