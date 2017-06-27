package org.sing_group.rnaseq.gui.ballgown;

import java.awt.Window;

/**
 * A dialog that allows the user to configure the settings to export a
 * transcripts table.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class ExportFilteredTranscriptsTableDialog extends ExportTableDialog {
	private static final long serialVersionUID = 1L;
	private static final String DESCRIPTION =
		"This dialog allows you to export the filtered transcripts table and "
		+ "save only those transcripts under the specified p-value.";

	public ExportFilteredTranscriptsTableDialog(Window parent) {
		super(parent);
	}

	@Override
	protected String getDescription() {
		return DESCRIPTION;
	}
}
