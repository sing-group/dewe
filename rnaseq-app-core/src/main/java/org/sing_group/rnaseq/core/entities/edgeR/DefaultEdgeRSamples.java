package org.sing_group.rnaseq.core.entities.edgeR;

import java.util.List;

import org.sing_group.rnaseq.api.entities.edger.EdgeRSample;
import org.sing_group.rnaseq.api.entities.edger.EdgeRSamples;
import org.sing_group.rnaseq.core.entities.DefaultFileBasedSamples;

public class DefaultEdgeRSamples extends DefaultFileBasedSamples<EdgeRSample> implements EdgeRSamples {
	private static final long serialVersionUID = 1L;

	public DefaultEdgeRSamples(List<EdgeRSample> samples) {
		super(samples);
	}
}
