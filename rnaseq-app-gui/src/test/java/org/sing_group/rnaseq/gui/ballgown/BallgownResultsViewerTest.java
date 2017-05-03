package org.sing_group.rnaseq.gui.ballgown;

import static org.sing_group.gc4s.demo.DemoUtils.setNimbusKeepAlternateRowColor;
import static org.sing_group.gc4s.demo.DemoUtils.setNimbusLookAndFeel;
import static org.sing_group.gc4s.demo.DemoUtils.showComponent;
import static java.awt.Frame.MAXIMIZED_BOTH;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

import org.sing_group.rnaseq.gui.ballgown.results.BallgownResultsViewer;

public class BallgownResultsViewerTest {
	public static final File WORKING_DIR = new File(
		"src/test/resources/data/ballgown/analysis");
	
	public static void main(String[] args) throws IOException, ParseException {
		setNimbusKeepAlternateRowColor();
		setNimbusLookAndFeel();
		showComponent(new BallgownResultsViewer(WORKING_DIR), MAXIMIZED_BOTH);
	}
}
