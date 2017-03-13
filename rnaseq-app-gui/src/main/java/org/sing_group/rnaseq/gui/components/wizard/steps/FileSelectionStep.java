package org.sing_group.rnaseq.gui.components.wizard.steps;

import java.awt.Component;
import java.io.File;
import java.util.Collections;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.filechooser.FileFilter;

import org.sing_group.rnaseq.gui.util.CommonFileChooser;

import es.uvigo.ei.sing.hlfernandez.filechooser.JFileChooserPanel;
import es.uvigo.ei.sing.hlfernandez.filechooser.JFileChooserPanel.SelectionMode;
import es.uvigo.ei.sing.hlfernandez.filechooser.JFileChooserPanelBuilder;
import es.uvigo.ei.sing.hlfernandez.ui.CenteredJPanel;
import es.uvigo.ei.sing.hlfernandez.wizard.WizardStep;

public abstract class FileSelectionStep extends WizardStep {

	private JPanel stepComponent;
	private JFileChooserPanel fileChooser;

	@Override
	public JComponent getStepComponent() {
		if(stepComponent == null) {
			this.stepComponent = new JPanel();
			this.stepComponent.setLayout(
				new BoxLayout(this.stepComponent, BoxLayout.Y_AXIS));
			this.stepComponent.add(getLabel());
			this.stepComponent.add(getFileChooserPanel());
		}
		return new CenteredJPanel(this.stepComponent);
	}

	private Component getFileChooserPanel() {
		this.fileChooser = JFileChooserPanelBuilder
			.createOpenJFileChooserPanel()
				.withFileChooserSelectionMode(getSelectionMode())
				.withFileChooser(
					CommonFileChooser.getInstance().getSingleFilechooser()
				)
				.withFileFilters(getFileFilters())
			.build();
		this.fileChooser.addFileChooserListener(this::fileChanged);
		return this.fileChooser;
	}

	protected List<FileFilter> getFileFilters() {
		return Collections.emptyList();
	}

	protected abstract SelectionMode getSelectionMode();

	protected void fileChanged(ChangeEvent e) {
		notifyWizardStepStatus();
	}

	private Component getLabel() {
		return new JLabel(getSelectionFileLabelText());
	}

	protected abstract String getSelectionFileLabelText();

	@Override
	public boolean isStepCompleted() {
		return isValidFile();
	}

	protected boolean isValidFile() {
		return 	this.fileChooser.getSelectedFile() != null;
	}

	@Override
	public void stepEntered() {
	}

	public File getSelectedFile() {
		return this.fileChooser.getSelectedFile();
	}
}
