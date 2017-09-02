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
package org.sing_group.rnaseq.gui.edger;

import static org.sing_group.jsparklines_factory.JSparklinesBarChartTableCellRendererFactory.createMaxMinValuesBarChartRenderer;
import static org.sing_group.jsparklines_factory.builders.JSparklinesBarChartTableCellRendererBuilderFactory.newMaximumBarChartRenderer;
import static org.sing_group.rnaseq.gui.edger.EdgeRTableSettings.MAXIMUM_FOLD_CHANGE;
import static org.sing_group.rnaseq.gui.edger.EdgeRTableSettings.ROW_COUNT_SORT_LIMIT;

import org.jdesktop.swingx.JXTable;
import org.sing_group.rnaseq.api.entities.edger.EdgeRGenes;
import org.sing_group.rnaseq.gui.ballgown.ProbabilityValueCellRenderer;

/**
 * A table for displaying a {@code EdgeRGenes} list using a
 * {@code EdgeRGenesTableModel}. Please, note that the table is
 * automatically ordered by p-value if row count is less than
 * {@link EdgeRTableSettings#ROW_COUNT_SORT_LIMIT}. This limit also affects
 * to the fold change sparklines creation: if row count is greater than the
 * limit then a fixed maximum fold change value is used
 * ({@link EdgeRTableSettings#MAXIMUM_FOLD_CHANGE}), otherwise the maximum
 * value is set to the actual maximum value.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 * @see EdgeRGenes
 * @see EdgeRGenesTableModel
 * @see EdgeRTableSettings
 */
public class EdgeRGenesTable extends JXTable {
	private static final long serialVersionUID = 1L;

	private final ProbabilityValueCellRenderer renderer =
		new ProbabilityValueCellRenderer();

	/**
	 * Creates a new {@code EdgeRGenesTable} for the specified {@code genes}
	 * list.
	 *
	 * @param genes the {@code EdgeRGenes} list.
	 */
	public EdgeRGenesTable(EdgeRGenes genes) {
		super(new EdgeRGenesTableModel(genes));

		this.init();
	}

	private void init() {
		this.updateSparklinesRenderers();
	}

	private void updateSparklinesRenderers() {
		this.getColumnModel().getColumn(2).setCellRenderer(renderer);
		if(rowCountUnderThreshold()) {
			createMaxMinValuesBarChartRenderer(this, 3).showNumberAndChart(true, 40);
			this.getRowSorter().toggleSortOrder(2);
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
