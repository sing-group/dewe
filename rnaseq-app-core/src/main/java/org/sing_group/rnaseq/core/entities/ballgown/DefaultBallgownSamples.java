package org.sing_group.rnaseq.core.entities.ballgown;

import java.util.Collection;
import java.util.LinkedList;

import org.sing_group.rnaseq.api.entities.ballgown.BallgownSample;
import org.sing_group.rnaseq.api.entities.ballgown.BallgownSamples;

/**
 * The default {@code BallgownSamples} implementation.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class DefaultBallgownSamples extends LinkedList<BallgownSample>
	implements BallgownSamples {
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a list containing the elements of the specified collection.
	 * 
	 * @param samples the collection whose samples are to be placed into this 
	 * 		  list
	 */
	public DefaultBallgownSamples(Collection<BallgownSample> samples) {
		super(samples);
	}
}
