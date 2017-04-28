package org.sing_group.rnaseq.aibench.views;

import org.sing_group.rnaseq.aibench.datatypes.BallgownWorkingDirectory;
import org.sing_group.rnaseq.gui.ballgown.results.BallgownResultsViewer;

/**
 * An AIBench view for the {@code BallgownWorkingDirectory} that extends the
 * {@code BallgownResultsViewer} component.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class BallgownWorkingDirectoryView extends BallgownResultsViewer {
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new {@code BallgownWorkingDirectoryView} for the specified
	 * {@code ballgownWorkingDirectory}.
	 * 
	 * @param ballgownWorkingDirectory the {@code BallgownWorkingDirectory} to 
	 *        view.
	 */
	public BallgownWorkingDirectoryView(
		BallgownWorkingDirectory ballgownWorkingDirectory) {
		super(ballgownWorkingDirectory.getWorkingDirectory());
	}
}
