package org.sing_group.rnaseq.gui.sample;

import org.sing_group.rnaseq.api.entities.ballgown.BallgownSample;
import org.sing_group.rnaseq.core.entities.ballgown.DefaultBallgownSample;

import org.sing_group.gc4s.filechooser.JFileChooserPanel.SelectionMode;

public class BallgownSampleEditor extends FileBasedSampleEditor<BallgownSample> {
	private static final long serialVersionUID = 1L;
	
	@Override
	public BallgownSample getSample() {
		return new DefaultBallgownSample(
			getSampleName(), getSampleType(), getSelectedFile()
		);
	}

	@Override
	protected SelectionMode getSelectionMode() {
		return SelectionMode.DIRECTORIES;
	}
}
