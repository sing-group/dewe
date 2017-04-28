package org.sing_group.rnaseq.gui.ballgown;

import static org.sing_group.jsparklines_factory.JSparklinesBarChartTableCellRendererFactory.createMaxValueBarChartRenderer;

import org.jdesktop.swingx.JXTable;
import org.sing_group.rnaseq.api.entities.ballgown.BallgownTranscripts;

/**
 * A table for displaying a {@code BallgownTranscripts} list using a
 * {@code BallgownTranscriptsTableModel}.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 * @see BallgownTranscripts
 * @see BallgownTranscriptsTableModel
 */
public class BallgownTranscriptsTable extends JXTable {
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
		this.getRowSorter().toggleSortOrder(4);
		this.getColumnModel().getColumn(4).setCellRenderer(renderer);
		this.getColumnModel().getColumn(5).setCellRenderer(renderer);
		this.getTableHeader().setReorderingAllowed(false);
		this.updateSparklinesRenderers();
	}

	private void updateSparklinesRenderers() {
		createMaxValueBarChartRenderer(this, 3).showNumberAndChart(true, 40);
	}
}
