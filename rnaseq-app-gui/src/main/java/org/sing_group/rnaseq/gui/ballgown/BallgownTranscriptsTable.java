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

import static org.sing_group.jsparklines_factory.JSparklinesBarChartTableCellRendererFactory.createMaxValueBarChartRenderer;
import static org.sing_group.jsparklines_factory.builders.JSparklinesBarChartTableCellRendererBuilderFactory.newMaximumBarChartRenderer;
import static org.sing_group.rnaseq.gui.ballgown.BallgownTableSettings.MAXIMUM_FOLD_CHANGE;
import static org.sing_group.rnaseq.gui.ballgown.BallgownTableSettings.ROW_COUNT_SORT_LIMIT;

import org.sing_group.gc4s.table.ExtendedJXTable;
import org.sing_group.rnaseq.api.entities.ballgown.BallgownTranscripts;

/**
 * A table for displaying a {@code BallgownTranscripts} list using a
 * {@code BallgownTranscriptsTableModel}. Please, note that the table is
 * automatically ordered by p-value if row count is less than
 * {@link BallgownTableSettings#ROW_COUNT_SORT_LIMIT}. This limit also affects
 * to the fold change sparklines creation: if row count is greater than the
 * limit then a fixed maximum fold change value is used
 * ({@link BallgownTableSettings#MAXIMUM_FOLD_CHANGE}), otherwise the maximum
 * value is set to the actual maximum value.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 * @see BallgownTranscripts
 * @see BallgownTranscriptsTableModel
 */
public class BallgownTranscriptsTable extends ExtendedJXTable {
	private static final long serialVersionUID = 1L;

	private final ProbabilityValueCellRenderer renderer =
		new ProbabilityValueCellRenderer();

	/**
	 * Creates a new {@code BallgownTranscriptsTable} for the specified
	 * {@code transcripts} list.
	 *
	 * @param transcripts the {@code BallgownTranscripts} list.
	 */
	public BallgownTranscriptsTable(BallgownTranscripts transcripts) {
		super(new BallgownTranscriptsTableModel(transcripts));

		this.init();
	}

	private void init() {
		if (rowCountUnderThreshold()) {
			this.getRowSorter().toggleSortOrder(4);
		}
		this.getColumnModel().getColumn(4).setCellRenderer(renderer);
		this.getColumnModel().getColumn(5).setCellRenderer(renderer);
		this.getTableHeader().setReorderingAllowed(false);
		this.setColumnControlVisible(true);
		this.setColumVisibilityActionsEnabled(false);
		this.updateSparklinesRenderers();
	}

	private void updateSparklinesRenderers() {
		if(rowCountUnderThreshold()) {
			createMaxValueBarChartRenderer(this, 3).showNumberAndChart(true, 40);
		} else {
			newMaximumBarChartRenderer(this, 3)
				.withMaxValue(MAXIMUM_FOLD_CHANGE)
			.build().showNumberAndChart(true, 40);
		}
	}

	private boolean rowCountUnderThreshold() {
		return this.getModel().getRowCount() < ROW_COUNT_SORT_LIMIT;
	}
}
