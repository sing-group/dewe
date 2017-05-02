package org.sing_group.rnaseq.gui.components;

import javax.swing.table.DefaultTableModel;

import org.sing_group.rnaseq.api.persistence.entities.ReferenceGenome;
import org.sing_group.rnaseq.api.persistence.entities.event.ReferenceGenomeDatabaseListener;
import org.sing_group.rnaseq.core.persistence.DefaultReferenceGenomeDatabaseManager;

public class ReferenceGenomeDatabaseTableModel
		extends DefaultTableModel
		implements ReferenceGenomeDatabaseListener {
	private static final long serialVersionUID = 1L;
	private static final int COLUMN_COUNT = 5;
	private DefaultReferenceGenomeDatabaseManager dbManager;

	public ReferenceGenomeDatabaseTableModel(
		DefaultReferenceGenomeDatabaseManager databaseManager
	) {
		this.dbManager = databaseManager;
		this.dbManager.addReferenceGenomeDatabaseListener(this);
	}

	@Override
	public int getRowCount() {
		return this.dbManager == null ? 0 : this.dbManager.listReferenceGenomes().size();
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
		ReferenceGenome genome = getReferenceGenomeAt(rowIndex);
		switch (columnIndex) {
		case 0:
			return genome.getName();
		case 1:
			return genome.getReferenceGenome().getAbsolutePath();
		case 2:
			return genome.getReferenceGenomeIndex().orElse("");
		case 3:
			return genome.isValid();
		case 4:
			return genome.getType();
		}
		throw new IllegalStateException();
	}

	public ReferenceGenome getReferenceGenomeAt(int rowIndex) {
		return this.dbManager.listReferenceGenomes().get(rowIndex);
	}

	@Override
	public void referenceGenomeAdded() {
		this.fireTableDataChanged();
	}

	@Override
	public void referenceGenomeRemoved() {
		this.fireTableDataChanged();
	}
}
