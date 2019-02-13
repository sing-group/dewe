/*
 * #%L
 * DEWE Core
 * %%
 * Copyright (C) 2016 - 2019 Hugo López-Fernández, Aitor Blanco-García, Florentino Fdez-Riverola, 
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
package org.sing_group.rnaseq.core.operations.ballgown;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.sing_group.rnaseq.core.operations.ballgown.BallgownGenesOperations.getTopGeneNames;

import java.util.List;

import org.junit.Test;
import org.sing_group.rnaseq.api.entities.ballgown.BallgownGenes;
import org.sing_group.rnaseq.core.entities.ballgown.DefaultBallgownGene;
import org.sing_group.rnaseq.core.entities.ballgown.DefaultBallgownGenes;
import org.sing_group.rnaseq.core.operations.ballgown.BallgownGenesOperations.GenesType;

public class BallgownGenesOperationsTest {

	public static BallgownGenes GENES = new DefaultBallgownGenes(asList(
		new DefaultBallgownGene("1", "Gene1", 10d, 0d, 0d),
		new DefaultBallgownGene("2", "Gene2", 8d, 0d, 0d),
		new DefaultBallgownGene("3", "Gene3", 6d, 0d, 0d),
		new DefaultBallgownGene("4", "Gene4", 4d, 0d, 0d),
		new DefaultBallgownGene("5", "Gene5", 2d, 0d, 0d),
		new DefaultBallgownGene("6", "Gene6", 1d, 0d, 0d),
		new DefaultBallgownGene("7", "Gene7", 0.5d, 0d, 0d),
		new DefaultBallgownGene("8", "Gene8", 0.25d, 0d, 0d)
	));

	@Test
	public void testGetMostOverexpressedGenes() {
		List<String> overExpressedGenes = getTopGeneNames(GENES, 2, GenesType.OVEREXPRESSED);
		assertEquals(asList("Gene1", "Gene2"), overExpressedGenes);
	}

	@Test
	public void testGetMostUnderexpressedGenes() {
		List<String> overExpressedGenes = getTopGeneNames(GENES, 2, GenesType.UNDEREXPRESSED);
		assertEquals(asList("Gene8", "Gene7"), overExpressedGenes);
	}
}
