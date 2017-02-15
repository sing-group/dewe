package org.sing_group.rnaseq.core.entities.edgeR;

import java.io.File;

import org.sing_group.rnaseq.api.entities.edger.EdgeRSample;
import org.sing_group.rnaseq.core.entities.DefaultFileBasedSample;

public class DefaultEdgeRSample extends DefaultFileBasedSample implements EdgeRSample {
	private static final long serialVersionUID = 1L;

	public DefaultEdgeRSample(String name, String type, File file) {
		super(name, type, file);
	}
}
