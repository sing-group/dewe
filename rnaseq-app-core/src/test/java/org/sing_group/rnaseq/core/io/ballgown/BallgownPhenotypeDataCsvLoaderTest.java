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

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class BallgownPhenotypeDataCsvLoaderTest {
	public static final File FILE = new File(
		"src/test/resources/data/ballgown/phenotype-data.csv");

	private static final List<String> SAMPLES = Arrays.asList(
		"ERR188245_chrX", "ERR188428_chrX", "ERR188337_chrX",
		"ERR188401_chrX", "ERR188257_chrX",	"ERR188383_chrX",
		"ERR204916_chrX", "ERR188234_chrX", "ERR188273_chrX",
		"ERR188454_chrX", "ERR188104_chrX", "ERR188044_chrX"
	);

	@Test
	public void testLoadGenesFile() throws IOException {
		List<String> samples = BallgownPhenotypeDataCsvLoader
			.loadSampleNames(FILE);
		assertEquals(SAMPLES, samples);
	}
}
