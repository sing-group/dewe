package org.sing_group.rnaseq.gui.components.wizard.steps;

import java.awt.Component;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;

import org.sing_group.rnaseq.gui.util.CommonFileChooser;

import es.uvigo.ei.sing.hlfernandez.filechooser.JFileChooserPanel;
import es.uvigo.ei.sing.hlfernandez.filechooser.JFileChooserPanel.Mode;
import es.uvigo.ei.sing.hlfernandez.filechooser.JFileChooserPanel.SelectionMode;
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
		this.fileChooser = new JFileChooserPanel(
			Mode.OPEN, getSelectionMode(),
			CommonFileChooser.getInstance().getSingleFilechooser()
		);
		this.fileChooser.addFileChooserListener(this::fileChanged);
		return this.fileChooser;
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

	private boolean isValidFile() {
		return 	this.fileChooser.getSelectedFile() != null;
	}

	@Override
	public void stepEntered() {
	}

	public File getSelectedFile() {
		return this.fileChooser.getSelectedFile();
	}
}
