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
package org.sing_group.rnaseq.core.io.alignment;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.sing_group.rnaseq.api.entities.alignment.AlignmentStatistics;
import org.sing_group.rnaseq.core.io.alignment.DefaultAlignmentLogParser;

public class DefaultAlignmentLogParserTest {
	public static final File LOG_FILE = new File(
		"src/test/resources/data/alignment/alignment.sam.txt");

	public static final String[] LOG_LINES = new String[] {
		"1321477 reads; of these:",
		"  1321477 (100.00%) were paired; of these:",
		"    112783 (8.53%) aligned concordantly 0 times",
		"    998414 (75.55%) aligned concordantly exactly 1 time",
		"    210280 (15.91%) aligned concordantly >1 times",
		"    ----",
		"    112783 pairs aligned concordantly 0 times; of these:",
		"      4592 (4.07%) aligned discordantly 1 time",
		"    ----",
		"    108191 pairs aligned 0 times concordantly or discordantly; of these:",
		"      216382 mates make up the pairs; of these:",
		"        109337 (50.53%) aligned 0 times",
		"        86913 (40.17%) aligned exactly 1 time",
		"        20132 (9.30%) aligned >1 times",
		"95.86% overall alignment rate",
	};

	private static final Double OVERALL_ALIGNMENT_RATE = 95.86d;
	private static final Double UNIQUELY_MAPPED_READS = 75.55d;
	private static final Double MULTIMAPPED_READS = 15.91d;

	DefaultAlignmentLogParser parser;

	@Before
	public void setUpTest() {
		parser = new DefaultAlignmentLogParser();
	}

	@Test
	public void parseLogLinesTest() {
		parser.parseLogLines(LOG_LINES);
		assertCorrectStatistics(parser.getAlignmentStatistics());
	}

	@Test
	public void parseLogFileTest() throws IOException {
		parser.parseLogFile(LOG_FILE);
		assertCorrectStatistics(parser.getAlignmentStatistics());
	}

	private void assertCorrectStatistics(AlignmentStatistics statistics) {
		assertEquals(OVERALL_ALIGNMENT_RATE,
			statistics.getOverallAlignmentRate(), 0.0d);
		assertEquals(UNIQUELY_MAPPED_READS,
			statistics.getUniquelyAlignedReads(), 0.0d);
		assertEquals(MULTIMAPPED_READS, statistics.getMultimappedReads(), 0.0d);
	}
}
