package org.sing_group.rnaseq.gui.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.swing.Action;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.event.PopupMenuEvent;

import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.decorator.ColorHighlighter;
import org.jdesktop.swingx.decorator.ComponentAdapter;
import org.jdesktop.swingx.decorator.HighlightPredicate;
import org.sing_group.gc4s.event.PopupMenuAdapter;
import org.sing_group.gc4s.utilities.ExtendedAbstractAction;
import org.sing_group.rnaseq.api.persistence.entities.ReferenceGenomeIndex;
import org.sing_group.rnaseq.core.persistence.DefaultReferenceGenomeIndexDatabaseManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This component displays a table with the reference genome indexes obtained
 * through a {@code DefaultReferenceGenomeIndexDatabaseManager}.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class ReferenceGenomeIndexDatabaseViewer extends JPanel {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = 
		LoggerFactory.getLogger(ReferenceGenomeIndexDatabaseViewer.class);
	private static final Color INVALID_GENOME = Color.decode("#ff5148");
	private JXTable table;
	private ExtendedAbstractAction removeRowsAction;
	private ReferenceGenomeIndexDatabaseTableModel tableModel;
	private DefaultReferenceGenomeIndexDatabaseManager databaseManager;

	/**
	 * Creates a new {@code ReferenceGenomeIndexDatabaseViewer}.
	 */
	public ReferenceGenomeIndexDatabaseViewer() {
		this.setLayout(new BorderLayout());
		this.table = new JXTable(0, 0);
		this.table.setComponentPopupMenu(getTablePopupMenu());
		this.table.getTableHeader().setReorderingAllowed(false);
		this.addTableHighlighters();
		this.add(new JScrollPane(this.table), BorderLayout.CENTER);
	}

	/**
	 * Sets the {@code ReferenceGenomeIndexDatabaseManager} that must be used
	 * to retrieve the reference genome indexes.
	 * 
	 * @param databaseManager a {@code ReferenceGenomeIndexDatabaseManager} 
	 */
	public void setReferenceGenomeDatabaseManager(
		DefaultReferenceGenomeIndexDatabaseManager databaseManager
	) {
		this.databaseManager = databaseManager;
		this.tableModel = new ReferenceGenomeIndexDatabaseTableModel(databaseManager);
		this.table.setModel(this.tableModel);
		this.updateUI();
	}

	private JPopupMenu getTablePopupMenu() {
		JPopupMenu menu = new JPopupMenu();
		menu.add(getRemoveSelectedRowsAction());
		menu.addPopupMenuListener(new PopupMenuAdapter() {

			@Override
			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
				removeRowsAction.setEnabled(table.getSelectedRowCount() > 0);
			}
		});

		return menu;
	}

	private Action getRemoveSelectedRowsAction() {
		removeRowsAction = new ExtendedAbstractAction(
			"Remove selected genomes", this::removeSelectedRows);
		removeRowsAction.setEnabled(false);

		return removeRowsAction;
	}

	private void removeSelectedRows() {
		if (table.getSelectedRowCount() > 0 && this.tableModel != null
			&& this.databaseManager != null
		) {
			removeSelectedReferenceGenomes();
			tableModel.fireTableDataChanged();
		}
	}

	private void removeSelectedReferenceGenomes() {
		List<ReferenceGenomeIndex> selectedGenomes =
			IntStream.of(table.getSelectedRows()).boxed()
			.map(table::convertRowIndexToModel)
			.map(i -> tableModel.getReferenceGenomeAt(i))
			.collect(Collectors.toList());

		selectedGenomes.forEach(databaseManager::removeIndex);
		try {
			databaseManager.persistDatabase();
		} catch (IOException e) {
			LOGGER.error("Error saving genome database " + e.getMessage());
		}
	}

	private void addTableHighlighters() {
		final HighlightPredicate invalidHighLight = new HighlightPredicate() {
			@Override
			public boolean isHighlighted(Component renderer,
				ComponentAdapter adapter
			) {
				int rowModel = adapter.convertRowIndexToModel(adapter.row);
				return testItem(tableModel.getReferenceGenomeAt(rowModel));
			}

			public boolean testItem(ReferenceGenomeIndex r) {
				return !r.isValidIndex();
			}
		};

		ColorHighlighter highlighter =
			new ColorHighlighter(invalidHighLight, INVALID_GENOME, null);

		this.table.addHighlighter(highlighter);
	}
}
