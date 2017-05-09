package org.sing_group.rnaseq.gui.components;

import java.io.File;

import javax.swing.table.DefaultTableModel;

import org.sing_group.rnaseq.api.persistence.ReferenceGenomeIndexDatabaseManager;
import org.sing_group.rnaseq.api.persistence.entities.ReferenceGenomeIndex;
import org.sing_group.rnaseq.api.persistence.entities.event.ReferenceGenomeIndexDatabaseListener;

/**
 * A {@code DefaultTableModel} extension that shows the reference genome indexes
 * in a {@code ReferenceGenomeIndexDatabaseManager}.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class ReferenceGenomeIndexDatabaseTableModel 
	extends DefaultTableModel
	implements ReferenceGenomeIndexDatabaseListener {
	private static final long serialVersionUID = 1L;
	private static final File EMPTY_FILE = new File("") {
		private static final long serialVersionUID = 1L;
		
		public String getAbsolutePath() {
			return "";
		}
	};
	private static final int COLUMN_COUNT = 5;
	private ReferenceGenomeIndexDatabaseManager dbManager;

	/**
	 * Creates a new {@code ReferenceGenomeIndexDatabaseTableModel} with the
	 * specified database manager to obtain the reference genome indexes.
	 * 
	 * @param databaseManager a {@code ReferenceGenomeIndexDatabaseManager}
	 */
	public ReferenceGenomeIndexDatabaseTableModel(
		ReferenceGenomeIndexDatabaseManager databaseManager
	) {
		this.dbManager = databaseManager;
		this.dbManager.addReferenceGenomeIndexDatabaseListener(this);
	}

	@Override
	public int getRowCount() {
		return this.dbManager == null ? 0 : this.dbManager.listIndexes().size();
	}

	@Override
	public int getColumnCount() {
		return COLUMN_COUNT;
	}

	@Override
	public String getColumnName(int columnIndex) {
		switch (columnIndex) {
		case 0:
			return "Name";
		case 1:
			return "Reference genome";
		case 2:
			return "Index";
		case 3:
			return "Valid";
		case 4:
			return "Type";
		}
		throw new IllegalStateException();
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch (columnIndex) {
		case 0:
			return String.class;
		case 1:
			return String.class;
		case 2:
			return String.class;
		case 3:
			return Boolean.class;
		case 4:
			return String.class;
		}
		throw new IllegalStateException();
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		ReferenceGenomeIndex genome = getReferenceGenomeAt(rowIndex);
		switch (columnIndex) {
		case 0:
			return genome.getName();
		case 1:
			return genome.getReferenceGenome().orElse(EMPTY_FILE).getAbsolutePath();
		case 2:
			return genome.getReferenceGenomeIndex();
		case 3:
			return genome.isValidIndex();
		case 4:
			return genome.getType();
		}
		throw new IllegalStateException();
	}

	/**
	 * Returns the {@code ReferenceGenomeIndex} at the specified model
	 * {@code rowIndex}.
	 * 
	 * @param rowIndex a model row index
	 * @return the {@code ReferenceGenomeIndex} at the specified model
	 *         {@code rowIndex}
	 */
	public ReferenceGenomeIndex getReferenceGenomeAt(int rowIndex) {
		return this.dbManager.listIndexes().get(rowIndex);
	}

	@Override
	public void referenceGenomeIndexAdded() {
		this.fireTableDataChanged();
	}

	@Override
	public void referenceGenomeIndexRemoved() {
		this.fireTableDataChanged();
	}
}
