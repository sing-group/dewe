package org.sing_group.rnaseq.gui.components.wizard.steps;

import es.uvigo.ei.sing.hlfernandez.filechooser.JFileChooserPanel.SelectionMode;

public class ReferenceAnnotationFileSelectionStep extends FileSelectionStep {

	@Override
	protected String getSelectionFileLabelText() {
		return "Select a reference annotation file (.gtf): ";
	}

	@Override
	public String getStepTitle() {
		return "Reference annotation file selection";
	}

	@Override
	protected SelectionMode getSelectionMode() {
		return SelectionMode.FILES;
	}
}
