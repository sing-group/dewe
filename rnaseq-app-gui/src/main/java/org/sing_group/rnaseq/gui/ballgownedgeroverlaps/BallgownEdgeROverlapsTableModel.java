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
package org.sing_group.rnaseq.gui.ballgownedgeroverlaps;

import javax.swing.table.DefaultTableModel;

import org.sing_group.rnaseq.api.entities.ballgownedgeroverlaps.BallgownEdgeROverlap;
import org.sing_group.rnaseq.api.entities.ballgownedgeroverlaps.BallgownEdgeROverlaps;

/**
 * A {@code DefaultTableModel} for displaying {@code BallgownEdgeROverlaps}.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 * @see BallgownEdgeROverlaps
 */
public class BallgownEdgeROverlapsTableModel  extends DefaultTableModel {
	private static final long serialVersionUID = 1L;

	private static final int COLUMN_COUNT = 5;

	private BallgownEdgeROverlaps overlaps;

	/**
	 * Creates a new {@code BallgownEdgeROverlapsTableModel} for the specified
	 * {@code overlaps} list.
	 *
	 * @param genes the {@code overlaps} list
	 */
	public BallgownEdgeROverlapsTableModel(BallgownEdgeROverlaps overlaps) {
		this.overlaps = overlaps;
	}

	@Override
	public int getRowCount() {
		return this.overlaps == null ? 0 : this.overlaps.size();
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
			return "Ballgown log2 fold change";
		case 2:
			return "Ballgown p-Value";
		case 3:
			return "EdgeR log2 fold change";
		case 4:
			return "EdgeR p-Value";
		}
		throw new IllegalStateException();
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch (columnIndex) {
		case 0:
			return String.class;
		case 1:
			return Double.class;
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
		BallgownEdgeROverlap overlap = this.overlaps.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return overlap.getGene();
		case 1:
			return overlap.getBallgownLogFoldChange();
		case 2:
			return overlap.getBallgownPval();
		case 3:
			return overlap.getEdgeRLogFoldChange();
		case 4:
			return overlap.getEdgeRPval();
		}
		throw new IllegalStateException();
	}
}