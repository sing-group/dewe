package org.sing_group.rnaseq.gui.edger;

import static java.awt.Frame.MAXIMIZED_BOTH;
import static org.sing_group.gc4s.demo.DemoUtils.setNimbusKeepAlternateRowColor;
import static org.sing_group.gc4s.demo.DemoUtils.setNimbusLookAndFeel;
import static org.sing_group.gc4s.demo.DemoUtils.showComponent;
import static org.sing_group.rnaseq.core.io.edger.EdgeRGenesTxtFileLoader.loadFile;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

import javax.swing.JScrollPane;

import org.sing_group.rnaseq.gui.edger.EdgeRGenesTable;

public class EdgeRGenesTableTest {
	public static final File GENES_FILE = new File(
		"src/test/resources/data/edger/DE_genes.txt");
	
	public static void main(String[] args) throws IOException, ParseException {
		setNimbusKeepAlternateRowColor();
		setNimbusLookAndFeel();
		EdgeRGenesTable table = getTable();
		showComponent(new JScrollPane(table), MAXIMIZED_BOTH);
	}

	private static EdgeRGenesTable getTable() throws IOException {
		return new EdgeRGenesTable(loadFile(GENES_FILE));
	}
}
