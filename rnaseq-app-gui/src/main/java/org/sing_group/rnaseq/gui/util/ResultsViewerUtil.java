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
