package org.sing_group.rnaseq.gui.sample;

import org.sing_group.rnaseq.api.entities.edger.EdgeRSample;
import org.sing_group.rnaseq.api.entities.edger.EdgeRSamples;
import org.sing_group.rnaseq.core.entities.edgeR.DefaultEdgeRSamples;

public class EdgeRSamplesEditor extends FileBasedSamplesEditor<EdgeRSamples, EdgeRSample> {
	private static final long serialVersionUID = 1L;

	@Override
	protected FileBasedSampleEditor<EdgeRSample> getFileBasedSampleEditor() {
		return new EdgeRSampleEditor();
	}

	@Override
	public EdgeRSamples getSamples() {
		return new DefaultEdgeRSamples(getSamplesList());
	}
}