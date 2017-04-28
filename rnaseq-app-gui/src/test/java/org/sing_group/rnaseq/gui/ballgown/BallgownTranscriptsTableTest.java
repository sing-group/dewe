package org.sing_group.rnaseq.gui.ballgown;

import static es.uvigo.ei.sing.hlfernandez.demo.DemoUtils.setNimbusKeepAlternateRowColor;
import static es.uvigo.ei.sing.hlfernandez.demo.DemoUtils.setNimbusLookAndFeel;
import static es.uvigo.ei.sing.hlfernandez.demo.DemoUtils.showComponent;
import static java.awt.Frame.MAXIMIZED_BOTH;
import static org.sing_group.rnaseq.core.io.ballgown.BallgownTranscriptsCsvFileLoader.loadFile;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

import javax.swing.JScrollPane;

public class BallgownTranscriptsTableTest {
	public static final File TRANSCRIPTS_FILE = new File(
		"src/test/resources/data/ballgown/transcripts.tsv");
	
	public static void main(String[] args) throws IOException, ParseException {
		setNimbusKeepAlternateRowColor();
		setNimbusLookAndFeel();
		BallgownTranscriptsTable table = getTable();
		showComponent(new JScrollPane(table), MAXIMIZED_BOTH);
	}

	private static BallgownTranscriptsTable getTable() throws IOException {
		return new BallgownTranscriptsTable(loadFile(TRANSCRIPTS_FILE));
	}
}
