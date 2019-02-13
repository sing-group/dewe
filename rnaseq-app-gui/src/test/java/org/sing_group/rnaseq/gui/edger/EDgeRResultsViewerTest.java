/*
 * #%L
 * DEWE GUI
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
package org.sing_group.rnaseq.gui.edger;

import static java.awt.Frame.MAXIMIZED_BOTH;
import static org.sing_group.gc4s.visualization.VisualizationUtils.setNimbusKeepAlternateRowColor;
import static org.sing_group.gc4s.visualization.VisualizationUtils.setNimbusLookAndFeel;
import static org.sing_group.gc4s.visualization.VisualizationUtils.showComponent;

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
