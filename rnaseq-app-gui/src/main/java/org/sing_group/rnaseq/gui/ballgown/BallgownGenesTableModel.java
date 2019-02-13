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
package org.sing_group.rnaseq.gui.ballgown;

import javax.swing.table.DefaultTableModel;

import org.sing_group.rnaseq.api.entities.ballgown.BallgownGene;
import org.sing_group.rnaseq.api.entities.ballgown.BallgownGenes;

/**
 * A {@code DefaultTableModel} for displaying {@code BallgownGenes}.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 * @see BallgownGenes
 */
public class BallgownGenesTableModel extends DefaultTableModel {
	private static final long serialVersionUID = 1L;

	private static final int COLUMN_COUNT = 5;

	private BallgownGenes genes;

	/**
	 * Creates a new {@code BallgownGenesTableModel} for the specified
	 * {@code genes} list.
	 *
	 * @param genes the {@code BallgownGenes} list
	 */
	public BallgownGenesTableModel(BallgownGenes genes) {
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
			return "ID";
		case 1:
			return "Gene name";
		case 2:
			return "Fold change";
		case 3:
			return "p-Value";
		case 4:
			return "q-Value";
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
		case 4:
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
		BallgownGene gene = this.genes.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return gene.getId();
		case 1:
			return gene.getGeneName();
		case 2:
			return gene.getFoldChange();
		case 3:
			return gene.getPval();
		case 4:
			return gene.getQval();
		}
		throw new IllegalStateException();
	}
}
