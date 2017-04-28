package org.sing_group.rnaseq.core.io.ballgown;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Test;
import org.sing_group.rnaseq.api.entities.ballgown.BallgownGene;
import org.sing_group.rnaseq.api.entities.ballgown.BallgownGenes;
import org.sing_group.rnaseq.core.entities.ballgown.DefaultBallgownGene;

public class BallgownGenesCsvFileLoaderTest {
	public static final File GENES_FILE = new File(
		"src/test/resources/data/ballgown/genes.tsv");

	public static final BallgownGene FIRST_GENE = new DefaultBallgownGene(
		"MSTRG.1003", "MIR6858",
		0.254115255461757, 0.0462757988333933, 0.530356494283635
	);

	@Test
	public void testLoadGenesFile() throws IOException {
		BallgownGenes genes = BallgownGenesCsvFileLoader.loadFile(GENES_FILE);
		assertEquals(2, genes.size());
		assertEquals(FIRST_GENE, genes.get(0));
	}
}
