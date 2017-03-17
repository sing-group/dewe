package org.sing_group.rnaseq.gui.components;

import javax.swing.table.DefaultTableModel;

import org.sing_group.rnaseq.api.persistence.entities.ReferenceGenome;
import org.sing_group.rnaseq.api.persistence.entities.event.ReferenceGenomeDatabaseListener;
import org.sing_group.rnaseq.core.persistence.DefaultReferenceGenomeDatabaseManager;

public class ReferenceGenomeDatabaseTableModel 
		extends DefaultTableModel
		implements ReferenceGenomeDatabaseListener {
	private static final long serialVersionUID = 1L;
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
		return 4;
	}

	@Override
	public String getColumnName(int columnIndex) {
		switch (columnIndex) {
		case 0:
			return "Reference genome";
		case 1:
			return "Index";
		case 2:
			return "Valid";
		case 3:
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
			return Boolean.class;
		case 3:
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
			return genome.getReferenceGenome().getAbsolutePath();
		case 1:
			return genome.getReferenceGenomeIndex().orElse("");
		case 2:
			return genome.isValid();
		case 3:
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
