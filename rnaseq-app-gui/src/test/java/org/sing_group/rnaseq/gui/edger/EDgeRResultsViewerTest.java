package org.sing_group.rnaseq.gui.edger;

import static java.awt.Frame.MAXIMIZED_BOTH;
import static org.sing_group.gc4s.demo.DemoUtils.setNimbusKeepAlternateRowColor;
import static org.sing_group.gc4s.demo.DemoUtils.setNimbusLookAndFeel;
import static org.sing_group.gc4s.demo.DemoUtils.showComponent;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

import org.sing_group.rnaseq.gui.edger.results.EdgeRResultsViewer;

public class EDgeRResultsViewerTest {
	public static final File WORKING_DIR = new File(
		"src/test/resources/data/edger");
	
	public static void main(String[] args) throws IOException, ParseException {
		setNimbusKeepAlternateRowColor();
		setNimbusLookAndFeel();
		showComponent(new EdgeRResultsViewer(WORKING_DIR), MAXIMIZED_BOTH);
	}
}
