package org.sing_group.rnaseq.gui.ballgown;

import static org.sing_group.jsparklines_factory.JSparklinesBarChartTableCellRendererFactory.createMaxValueBarChartRenderer;

import org.jdesktop.swingx.JXTable;
import org.sing_group.rnaseq.api.entities.ballgown.BallgownGenes;

/**
 * A table for displaying a {@code BallgownGenes} list using a
 * {@code BallgownGenesTableModel}.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 * @see BallgownGenes
 * @see BallgownGenesTableModel
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
		this.getRowSorter().toggleSortOrder(3);
		this.getColumnModel().getColumn(3).setCellRenderer(renderer);
		this.getColumnModel().getColumn(4).setCellRenderer(renderer);
		this.getTableHeader().setReorderingAllowed(false);
		this.updateSparklinesRenderers();
	}

	private void updateSparklinesRenderers() {
		createMaxValueBarChartRenderer(this, 2).showNumberAndChart(true, 40);
	}
}
