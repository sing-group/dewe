package org.sing_group.rnaseq.gui.edger;

import javax.swing.table.DefaultTableModel;

import org.sing_group.rnaseq.api.entities.edger.EdgeRGene;
import org.sing_group.rnaseq.api.entities.edger.EdgeRGenes;

/**
 * A {@code DefaultTableModel} for displaying {@code EdgeRGenes}.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 * @see EdgeRGenes
 */
public class EdgeRGenesTableModel  extends DefaultTableModel {
	private static final long serialVersionUID = 1L;

	private static final int COLUMN_COUNT = 4;

	private EdgeRGenes genes;

	/**
	 * Creates a new {@code EdgeRGenesTableModel} for the specified
	 * {@code genes} list.
	 *
	 * @param genes the {@code genes} list
	 */
	public EdgeRGenesTableModel(EdgeRGenes genes) {
		this.genes = genes;
	}

	@Override
	public int getRowCount() {
		return this.genes == null ? 0 : this.genes.size();
	}

	@Override
	public int getColumnCount() {
		return COLUMN_COUNT;
	}

	@Override
	public String getColumnName(int columnIndex) {
		switch (columnIndex) {
		case 0:
			return "Gene";
		case 1:
			return "Gene name";
		case 2:
			return "p-Value";
		case 3:
			return "Log Fold change";
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
			return Double.class;
		case 3:
			return Double.class;
		}
		throw new IllegalStateException();
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		EdgeRGene gene = this.genes.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return gene.getGene();
		case 1:
			return gene.getGeneName();
		case 2:
			return gene.getPval();
		case 3:
			return gene.getLogFoldChange();
		}
		throw new IllegalStateException();
	}
}