package org.sing_group.rnaseq.gui.ballgown;

import static org.sing_group.gc4s.demo.DemoUtils.setNimbusKeepAlternateRowColor;
import static org.sing_group.gc4s.demo.DemoUtils.setNimbusLookAndFeel;
import static org.sing_group.gc4s.demo.DemoUtils.showComponent;
import static java.awt.Frame.MAXIMIZED_BOTH;
import static org.sing_group.rnaseq.core.io.ballgown.BallgownGenesCsvFileLoader.loadFile;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

import javax.swing.JScrollPane;

public class BallgownGenesTableTest {
	public static final File GENES_FILE = new File(
		"src/test/resources/data/ballgown/genes.tsv");
	
	public static void main(String[] args) throws IOException, ParseException {
		setNimbusKeepAlternateRowColor();
		setNimbusLookAndFeel();
		BallgownGenesTable table = getTable();
		showComponent(new JScrollPane(table), MAXIMIZED_BOTH);
	}

	private static BallgownGenesTable getTable() throws IOException {
		return new BallgownGenesTable(loadFile(GENES_FILE));
	}
}
