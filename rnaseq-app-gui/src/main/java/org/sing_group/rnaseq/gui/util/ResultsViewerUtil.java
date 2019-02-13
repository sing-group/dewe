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
package org.sing_group.rnaseq.gui.util;

import static java.util.stream.Collectors.joining;

import java.util.List;

/**
 * This class provides utility functions for results visualization classes.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class ResultsViewerUtil {

	/**
	 * Generates an HTML message containing the list of missing files specified
	 * as parameter along with an informative text.
	 *
	 * @param missingFiles the names of the missing files.
	 * @return an HTML formated string message
	 */
	public static final String missingFilesMessage(List<String> missingFiles) {
		return "<html>The following file(s) can't be found in the selected "
			+ "working directory:" + toHtmlList(missingFiles) + "<p>Due to "
				+ "this reason, its associated views will appear empty."
				+ "</p></html>";
	}

	private static String toHtmlList(List<String> elements) {
		return elements.stream().collect(
			joining("</li><li>", "<ul><li>", "</li></ul")
		);
	}
}
