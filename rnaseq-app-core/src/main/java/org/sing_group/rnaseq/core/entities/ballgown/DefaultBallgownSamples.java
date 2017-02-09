package org.sing_group.rnaseq.core.entities.ballgown;

import java.util.LinkedList;
import java.util.List;

import org.sing_group.rnaseq.api.entities.ballgown.BallgownSample;
import org.sing_group.rnaseq.api.entities.ballgown.BallgownSamples;

public class DefaultBallgownSamples 
	extends LinkedList<BallgownSample>
	implements BallgownSamples 
{
	private static final long serialVersionUID = 1L;

	public DefaultBallgownSamples(List<BallgownSample> samples) {
		super(samples);
	}
}
