package org.sing_group.rnaseq.gui.components.wizard.steps;

import java.util.Arrays;
import java.util.List;

import javax.swing.filechooser.FileFilter;

import es.uvigo.ei.sing.hlfernandez.filechooser.ExtensionFileFilter;
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

	@Override
	protected List<FileFilter> getFileFilters() {
		return Arrays.asList(new ExtensionFileFilter(".*\\.gtf",
			"Gene transfer format (GTF) files"));
	}

	@Override
	protected boolean isValidFile() {
		return 	super.isValidFile() && 
				getSelectedFile().getName().endsWith(".gtf");
	}
	
}
