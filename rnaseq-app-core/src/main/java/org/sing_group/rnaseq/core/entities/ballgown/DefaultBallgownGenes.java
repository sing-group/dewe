package org.sing_group.rnaseq.core.entities.ballgown;

import java.util.Collection;
import java.util.LinkedList;

import org.sing_group.rnaseq.api.entities.ballgown.BallgownGene;
import org.sing_group.rnaseq.api.entities.ballgown.BallgownGenes;

/**
 * The default {@code BallgownGenes} implementation.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 * @see BallgownGenes
 */
public class DefaultBallgownGenes extends LinkedList<BallgownGene>
	implements BallgownGenes {
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new {@code DefaultBallgownGenes} with the specified
	 * initial {@code genes}.
	 *
	 * @param genes a collection of {@code BallgownGene}s
	 */
	public DefaultBallgownGenes(Collection<BallgownGene> genes) {
		super(genes);
	}

	/**
	 * Creates an empty {@code DefaultBallgownGenes}.
	 */
	public DefaultBallgownGenes() {
		super();
	}
}
