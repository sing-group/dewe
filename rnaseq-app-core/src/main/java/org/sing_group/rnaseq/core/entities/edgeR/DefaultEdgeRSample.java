package org.sing_group.rnaseq.core.entities.edgeR;

import java.io.File;

import org.sing_group.rnaseq.api.entities.edger.EdgeRSample;
import org.sing_group.rnaseq.core.entities.DefaultFileBasedSample;

/**
 * The default {@code EdgeRSample} implementation.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class DefaultEdgeRSample extends DefaultFileBasedSample
	implements EdgeRSample {
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new {@code DefaultEdgeRSample} instance.
	 * 
	 * @param name the sample name
	 * @param type the sample type
	 * @param file the file where the sample is located
	 */
	public DefaultEdgeRSample(String name, String type, File file) {
		super(name, type, file);
	}
}
