package org.sing_group.rnaseq.core.io.edger;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.junit.Test;
import org.sing_group.rnaseq.api.entities.edger.EdgeRGene;
import org.sing_group.rnaseq.api.entities.edger.EdgeRGenes;
import org.sing_group.rnaseq.core.entities.edgeR.DefaultEdgeRGene;

public class EdgeRGenesTxtFileLoaderTest {
	public static final File GENES_FILE = new File(
		"src/test/resources/data/edger/DE_genes.txt");

	public static final EdgeRGene FIRST_GENE = new DefaultEdgeRGene(
		"TSIX", "TSIX", 0d, 5.16492326084871d
	);

	@Test
	public void testLoadGenesFile() throws IOException {
		EdgeRGenes genes = EdgeRGenesTxtFileLoader.loadFile(GENES_FILE);
		assertEquals(3200, genes.size());
		assertEquals(FIRST_GENE, genes.get(0));
	}
}
