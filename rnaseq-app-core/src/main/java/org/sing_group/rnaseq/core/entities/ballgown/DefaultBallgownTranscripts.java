package org.sing_group.rnaseq.core.entities.ballgown;

import java.util.Collection;
import java.util.LinkedList;

import org.sing_group.rnaseq.api.entities.ballgown.BallgownTranscript;
import org.sing_group.rnaseq.api.entities.ballgown.BallgownTranscripts;

/**
 * The default {@code BallgownTranscripts} implementation.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 * @see BallgownTranscripts
 */
public class DefaultBallgownTranscripts extends LinkedList<BallgownTranscript>
	implements BallgownTranscripts {
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new {@code DefaultBallgownTranscripts} with the specified
	 * initial {@code transcripts}.
	 *
	 * @param transcripts a collection of {@code BallgownTranscript}s
	 */
	public DefaultBallgownTranscripts(
		Collection<BallgownTranscript> transcripts
	) {
		super(transcripts);
	}

	/**
	 * Creates an empty {@code DefaultBallgownTranscripts}.
	 */
	public DefaultBallgownTranscripts() {
		super();
	}
}
