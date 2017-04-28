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
