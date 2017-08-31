package org.sing_group.rnaseq.gui.sample;

import org.sing_group.rnaseq.api.entities.edger.EdgeRSample;
import org.sing_group.rnaseq.core.entities.edgeR.DefaultEdgeRSample;

import org.sing_group.gc4s.filechooser.JFileChooserPanel.SelectionMode;

/**
 * A {@code FileBasedSampleEditor} implementation to the introduction of an
 * {@code EdgeRSample}.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class EdgeRSampleEditor extends FileBasedSampleEditor<EdgeRSample> {
	private static final long serialVersionUID = 1L;
	
	@Override
	public EdgeRSample getSample() {
		return new DefaultEdgeRSample(
			getSampleName(), getSampleType(), getSelectedFile()
		);
	}

	@Override
	protected SelectionMode getSelectionMode() {
		return SelectionMode.FILES;
	}
}
