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
