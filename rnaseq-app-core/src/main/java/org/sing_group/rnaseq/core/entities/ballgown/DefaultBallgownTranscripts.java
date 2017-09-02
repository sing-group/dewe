/*
 * #%L
 * DEWE Core
 * %%
 * Copyright (C) 2016 - 2017 Hugo López-Fernández, Aitor Blanco-García, Florentino Fdez-Riverola, 
 * 			Borja Sánchez, and Anália Lourenço
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */
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
