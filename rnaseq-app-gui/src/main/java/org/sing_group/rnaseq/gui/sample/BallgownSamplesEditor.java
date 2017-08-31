package org.sing_group.rnaseq.gui.sample;

import org.sing_group.rnaseq.api.entities.ballgown.BallgownSample;
import org.sing_group.rnaseq.api.entities.ballgown.BallgownSamples;
import org.sing_group.rnaseq.core.entities.ballgown.DefaultBallgownSamples;

/**
 * A {@code FileBasedSamplesEditor} implementation to the introduction of a
 * {@code BallgownSamples} list of {@code BallgownSample}s.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class BallgownSamplesEditor extends
	ExperimentalConditionsSamplesSelection<BallgownSamples, BallgownSample> {
	private static final long serialVersionUID = 1L;

	@Override
	protected FileBasedSampleEditor<BallgownSample> getFileBasedSampleEditor() {
		return new BallgownSampleEditor();
	}

	@Override
	public BallgownSamples getSamples() {
		return new DefaultBallgownSamples(getSamplesList());
	}
}