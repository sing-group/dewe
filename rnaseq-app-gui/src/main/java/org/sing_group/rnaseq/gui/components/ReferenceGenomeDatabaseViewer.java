package org.sing_group.rnaseq.gui.components;

import java.awt.BorderLayout;
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
import org.sing_group.rnaseq.api.persistence.entities.ReferenceGenome;
import org.sing_group.rnaseq.core.persistence.DefaultReferenceGenomeDatabaseManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.uvigo.ei.sing.hlfernandez.event.PopupMenuAdapter;
import es.uvigo.ei.sing.hlfernandez.utilities.ExtendedAbstractAction;

public class ReferenceGenomeDatabaseViewer extends JPanel {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = 
		LoggerFactory.getLogger(ReferenceGenomeDatabaseViewer.class);
	private JXTable table;
	private ExtendedAbstractAction removeRowsAction;
	private ReferenceGenomeDatabaseTableModel tableModel;
	private DefaultReferenceGenomeDatabaseManager databaseManager;

	public ReferenceGenomeDatabaseViewer() {
		this.setLayout(new BorderLayout());
		this.table = new JXTable(0, 0);
		this.table.setComponentPopupMenu(getTablePopupMenu());
		this.add(new JScrollPane(this.table), BorderLayout.CENTER);
	}

	public void setReferenceGenomeDatabaseManager(DefaultReferenceGenomeDatabaseManager database) {
		this.databaseManager = database;
		this.tableModel = new ReferenceGenomeDatabaseTableModel(database);
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
		List<ReferenceGenome> selectedGenomes =
			IntStream.of(table.getSelectedRows()).boxed()
			.map(table::convertRowIndexToModel)
			.map(i -> tableModel.getReferenceGenomeAt(i))
			.collect(Collectors.toList());

		selectedGenomes.forEach(databaseManager::removeReferenceGenome);
		try {
			databaseManager.persistDatabase();
		} catch (IOException e) {
			LOGGER.error("Error saving genome database " + e.getMessage());
		}
	}
}
