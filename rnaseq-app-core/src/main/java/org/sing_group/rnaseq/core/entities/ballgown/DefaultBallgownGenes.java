/*
 * #%L
 * DEWE Core
 * %%
 * Copyright (C) 2016 - 2018 Hugo López-Fernández, Aitor Blanco-García, Florentino Fdez-Riverola, 
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
