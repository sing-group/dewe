package org.sing_group.rnaseq.gui.ballgown.results;

import java.awt.Window;

/**
 * A dialog that allows users to configure the FKPM transcripts figure.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class FkpmTranscriptsFigureConfigurationDialog
	extends FigureConfigurationDialog {
	private static final long serialVersionUID = 1L;

	protected FkpmTranscriptsFigureConfigurationDialog(Window parent) {
		super(parent);
	}

	@Override
	protected String getDialogTitle() {
		return "FKPM transcripts figure configuration";
	}
}
