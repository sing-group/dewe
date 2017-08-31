package org.sing_group.rnaseq.core.entities.edgeR;

import java.util.Collection;

import org.sing_group.rnaseq.api.entities.edger.EdgeRSample;
import org.sing_group.rnaseq.api.entities.edger.EdgeRSamples;
import org.sing_group.rnaseq.core.entities.DefaultFileBasedSamples;

/**
 * The default {@code EdgeRSamples} implementation.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class DefaultEdgeRSamples extends DefaultFileBasedSamples<EdgeRSample>
	implements EdgeRSamples {
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a list containing the elements of the specified collection.
	 * 
	 * @param samples the collection whose samples are to be placed into this 
	 * 		  list
	 */
	public DefaultEdgeRSamples(Collection<EdgeRSample> samples) {
		super(samples);
	}
}
