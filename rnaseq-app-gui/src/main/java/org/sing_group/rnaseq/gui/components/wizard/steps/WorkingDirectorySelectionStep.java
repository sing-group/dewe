package org.sing_group.rnaseq.gui.components.wizard.steps;

import org.sing_group.gc4s.filechooser.JFileChooserPanel.SelectionMode;

public class WorkingDirectorySelectionStep extends FileSelectionStep {

	@Override
	protected String getSelectionFileLabelText() {
		return "Select a working directory: ";
	}

	@Override
	public String getStepTitle() {
		return "Working directory selection";
	}

	@Override
	protected SelectionMode getSelectionMode() {
		return SelectionMode.DIRECTORIES;
	}
}
