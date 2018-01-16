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
package org.sing_group.rnaseq.core.io.alignment;

import static java.nio.file.Files.createTempFile;
import static java.nio.file.Files.readAllLines;
import static java.util.Arrays.asList;
import static junit.framework.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.Test;
import org.sing_group.rnaseq.api.entities.alignment.SampleAlignmentStatistics;
import org.sing_group.rnaseq.core.entities.alignment.DefaultAlignmentStatistics;
import org.sing_group.rnaseq.core.entities.alignment.DefaultSampleAlignmentStatistics;


public class SamplesAlignmentStatisticsCsvWriterTest {
	public static final File CSV_FILE = new File(
		"src/test/resources/data/alignment/samples.csv");	

	private static final List<SampleAlignmentStatistics> SAMPLES = asList(
		new DefaultSampleAlignmentStatistics("SAMPLE_A",
			new DefaultAlignmentStatistics(95d, 80d, 10d)),
		new DefaultSampleAlignmentStatistics("SAMPLE_B",
			new DefaultAlignmentStatistics(90d, 75d, 5d))
	);

	@Test
	public void samplesAlignmentStatisticsCsvWriterTest() throws IOException {
		File tempFile = createTempFile("rna-seq-samples", ".csv").toFile();
		SamplesAlignmentStatisticsCsvWriter.write(SAMPLES, tempFile);
		assertEquals(
			readAllLines(CSV_FILE.toPath()), 
			readAllLines(tempFile.toPath())
		);
	}
}
