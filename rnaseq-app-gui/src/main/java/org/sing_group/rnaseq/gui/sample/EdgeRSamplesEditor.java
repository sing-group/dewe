package org.sing_group.rnaseq.gui.sample;

import org.sing_group.rnaseq.api.entities.edger.EdgeRSample;
import org.sing_group.rnaseq.api.entities.edger.EdgeRSamples;
import org.sing_group.rnaseq.core.entities.edgeR.DefaultEdgeRSamples;

/**
 * A {@code FileBasedSamplesEditor} implementation to the introduction of a
 * {@code EdgeRSamples} list of {@code EdgeRSample}s.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class EdgeRSamplesEditor
	extends ExperimentalConditionsSamplesSelection<EdgeRSamples, EdgeRSample> {
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