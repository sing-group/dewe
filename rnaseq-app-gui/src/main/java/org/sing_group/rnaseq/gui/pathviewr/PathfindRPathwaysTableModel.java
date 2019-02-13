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
package org.sing_group.rnaseq.gui.pathviewr;

import javax.swing.table.DefaultTableModel;

import org.sing_group.rnaseq.api.entities.edger.EdgeRGenes;
import org.sing_group.rnaseq.api.entities.pathfindr.PathfindRPathway;
import org.sing_group.rnaseq.api.entities.pathfindr.PathfindRPathways;

/**
 * A {@code DefaultTableModel} for displaying {@code PathfindRPathways}.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 * @see EdgeRGenes
 */
public class PathfindRPathwaysTableModel  extends DefaultTableModel {
	private static final long serialVersionUID = 1L;

	private static final int COLUMN_COUNT = 10;

	private PathfindRPathways pathways;

	/**
	 * Creates a new {@code PathfindRPathwaysTableModel} for the specified
	 * {@code pathways} list.
	 *
	 * @param genes the {@code pathways} list
	 */
	public PathfindRPathwaysTableModel(PathfindRPathways pathways) {
		this.pathways = pathways;
	}

	@Override
	public int getRowCount() {
		return this.pathways == null ? 0 : this.pathways.size();
	}

	@Override
	public int getColumnCount() {
		return COLUMN_COUNT;
	}

	@Override
	public String getColumnName(int columnIndex) {
		switch (columnIndex) {
		case 0:
			return "Pathway";
		case 1:
			return "Pathway name";
		case 2:
			return "Fold enrichment";
		case 3:
			return "Occurrence";
		case 4:
			return "Lowest p-Value";
		case 5:
			return "Highest p-Value";
		case 6:
			return "Down-regulated genes";
		case 7:
			return "Up-regulated genes";
		case 8:
			return "Cluster";
		case 9:
			return "Status";
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
			return Integer.class;
		case 4:
			return Double.class;
		case 5:
			return Double.class;
		case 6:
			return String.class;
		case 7:
			return String.class;
		case 8:
			return Integer.class;
		case 9:
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
		PathfindRPathway pathway = this.pathways.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return pathway.getPathway();
		case 1:
			return pathway.getPathwayName();
		case 2:
			return pathway.getLogFoldChange();
		case 3:
			return pathway.getOccurrence();
		case 4:
			return pathway.getLowPval();
		case 5:
			return pathway.getHighPval();
		case 6:
			return pathway.getDownGenes();
		case 7:
			return pathway.getUpGenes();
		case 8:
			return pathway.getCluster();
		case 9:
			return pathway.getStatus();
		}
		throw new IllegalStateException();
	}
}