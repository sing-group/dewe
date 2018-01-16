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
package org.sing_group.rnaseq.core.io.ballgown;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.junit.Test;
import org.sing_group.rnaseq.api.entities.ballgown.BallgownTranscript;
import org.sing_group.rnaseq.api.entities.ballgown.BallgownTranscripts;
import org.sing_group.rnaseq.core.entities.ballgown.DefaultBallgownTranscript;

public class BallgownTranscriptsCsvFileLoaderTest {
	public static final File TRANSCRIPTS_FILE = new File(
		"src/test/resources/data/ballgown/transcripts.tsv");

	public static final BallgownTranscript FIRST_TRANSCRIPT =
		new DefaultBallgownTranscript(
			"MSTRG.11", "ASMTL-AS1", "NR_026711", 43,
			2.81422105567809, 0.0133774817092107, 0.589156624121647
		);

	@Test
	public void testLoadGenesFile() throws IOException {
		BallgownTranscripts transcripts = BallgownTranscriptsCsvFileLoader
			.loadFile(TRANSCRIPTS_FILE);
		assertEquals(2, transcripts.size());
		assertEquals(FIRST_TRANSCRIPT, transcripts.get(0));
	}
}
