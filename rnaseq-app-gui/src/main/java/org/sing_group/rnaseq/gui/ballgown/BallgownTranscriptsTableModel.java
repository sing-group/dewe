/*
 * #%L
 * DEWE GUI
 * %%
 * Copyright (C) 2016 - 2017 Hugo López-Fernández, Aitor Blanco-García, Florentino Fdez-Riverola, 
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

import org.sing_group.rnaseq.api.entities.ballgown.BallgownTranscript;
import org.sing_group.rnaseq.api.entities.ballgown.BallgownTranscripts;

/**
 * A {@code DefaultTableModel} for displaying {@code BallgownTranscripts}.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 * @see BallgownTranscripts
 */
public class BallgownTranscriptsTableModel extends DefaultTableModel {
	private static final long serialVersionUID = 1L;

	private static final int COLUMN_COUNT = 6;

	private BallgownTranscripts transcripts;

	/**
	 * Creates a new {@code BallgownTranscriptsTableModel} for the specified
	 * {@code transcripts} list.
	 *
	 * @param transcripts the {@code BallgownTranscripts} list
	 */
	public BallgownTranscriptsTableModel(BallgownTranscripts transcripts) {
		this.transcripts = transcripts;
	}

	@Override
	public int getRowCount() {
		return this.transcripts == null ? 0 : this.transcripts.size();
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
			return "Gene names";
		case 2:
			return "Transcript names";
		case 3:
			return "Fold change";
		case 4:
			return "p-Value";
		case 5:
			return "q-Value";
		}
		throw new IllegalStateException();
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch (columnIndex) {
		case 0:
			return Integer.class;
		case 1:
			return String.class;
		case 2:
			return String.class;
		case 3:
			return Double.class;
		case 4:
			return Double.class;
		case 5:
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
		BallgownTranscript transcript = this.transcripts.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return transcript.getTranscriptId();
		case 1:
			return transcript.getGeneNames();
		case 2:
			return transcript.getTranscriptNames();
		case 3:
			return transcript.getFoldChange();
		case 4:
			return transcript.getPval();
		case 5:
			return transcript.getQval();
		}
		throw new IllegalStateException();
	}
}
