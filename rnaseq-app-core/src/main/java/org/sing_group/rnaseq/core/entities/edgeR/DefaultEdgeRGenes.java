package org.sing_group.rnaseq.core.entities.edgeR;

import java.util.Collection;
import java.util.LinkedList;

import org.sing_group.rnaseq.api.entities.edger.EdgeRGene;
import org.sing_group.rnaseq.api.entities.edger.EdgeRGenes;

/**
 * The default {@code EdgeRGenes} implementation.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 * @see EdgeRGenes
 */
public class DefaultEdgeRGenes extends LinkedList<EdgeRGene>
	implements EdgeRGenes {
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new {@code DefaultEdgeRGenes} with the specified initial
	 * {@code genes}.
	 *
	 * @param genes a collection of {@code EdgeRGene}s
	 */
	public DefaultEdgeRGenes(Collection<EdgeRGene> genes) {
		super(genes);
	}

	/**
	 * Creates an empty {@code DefaultEdgeRGenes}.
	 */
	public DefaultEdgeRGenes() {
		super();
	}
}
