package org.sing_group.rnaseq.aibench.views;

import org.sing_group.rnaseq.aibench.datatypes.EdgeRWorkingDirectory;
import org.sing_group.rnaseq.gui.edger.results.EdgeRResultsViewer;

/**
 * An AIBench view for the {@code EdgeRWorkingDirectory} that extends the
 * {@code EdgeRResultsViewer} component.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class EdgeRWorkingDirectoryView extends EdgeRResultsViewer {
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new {@code EdgeRWorkingDirectoryView} for the specified
	 * {@code edgeRWorkingDirectory}.
	 *
	 * @param edgeRWorkingDirectory the {@code EdgeRWorkingDirectory} to
	 *        view.
	 */
	public EdgeRWorkingDirectoryView(
		EdgeRWorkingDirectory edgeRWorkingDirectory
	) {
		super(edgeRWorkingDirectory.getWorkingDirectory());
	}
}
