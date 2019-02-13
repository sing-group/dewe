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
