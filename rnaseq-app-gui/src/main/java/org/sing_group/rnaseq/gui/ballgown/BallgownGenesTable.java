package org.sing_group.rnaseq.gui.ballgown;

import static org.sing_group.jsparklines_factory.JSparklinesBarChartTableCellRendererFactory.createMaxValueBarChartRenderer;
import static org.sing_group.jsparklines_factory.builders.JSparklinesBarChartTableCellRendererBuilderFactory.newMaximumBarChartRenderer;
import static org.sing_group.rnaseq.gui.ballgown.BallgownTableSettings.MAXIMUM_FOLD_CHANGE;
import static org.sing_group.rnaseq.gui.ballgown.BallgownTableSettings.ROW_COUNT_SORT_LIMIT;

import org.jdesktop.swingx.JXTable;
import org.sing_group.rnaseq.api.entities.ballgown.BallgownGenes;

/**
 * A table for displaying a {@code BallgownGenes} list using a
 * {@code BallgownGenesTableModel}. Please, note that the table is
 * automatically ordered by p-value if row count is less than
 * {@link BallgownTableSettings#ROW_COUNT_SORT_LIMIT}. This limit also affects
 * to the fold change sparklines creation: if row count is greater than the
 * limit then a fixed maximum fold change value is used
 * ({@link BallgownTableSettings#MAXIMUM_FOLD_CHANGE}), otherwise the maximum
 * value is set to the actual maximum value.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 * @see BallgownGenes
 * @see BallgownGenesTableModel
 * @see BallgownTableSettings
 */
public class BallgownGenesTable extends JXTable {
	private static final long serialVersionUID = 1L;

	private final ProbabilityValueCellRenderer renderer =
		new ProbabilityValueCellRenderer();

	/**
	 * Creates a new {@code BallgownGenesTable} for the specified {@code genes}
	 * list.
	 *
	 * @param genes the {@code BallgownGenes} list.
	 */
	public BallgownGenesTable(BallgownGenes genes) {
		super(new BallgownGenesTableModel(genes));

		this.init();
	}

	private void init() {
		if (rowCountUnderThreshold()) {
			this.getRowSorter().toggleSortOrder(3);
		}
		this.getColumnModel().getColumn(3).setCellRenderer(renderer);
		this.getColumnModel().getColumn(4).setCellRenderer(renderer);
		this.getTableHeader().setReorderingAllowed(false);
		this.updateSparklinesRenderers();
	}

	private void updateSparklinesRenderers() {
		if(rowCountUnderThreshold()) {
			createMaxValueBarChartRenderer(this, 2).showNumberAndChart(true, 40);
		} else {
			newMaximumBarChartRenderer(this, 2)
				.withMaxValue(MAXIMUM_FOLD_CHANGE)
			.build().showNumberAndChart(true, 40);
		}
	}

	private boolean rowCountUnderThreshold() {
		return this.getModel().getRowCount() < ROW_COUNT_SORT_LIMIT;
	}
}
