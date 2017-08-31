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

import org.sing_group.gc4s.filechooser.JFileChooserPanel;
import org.sing_group.gc4s.filechooser.JFileChooserPanel.SelectionMode;
import org.sing_group.gc4s.filechooser.JFileChooserPanelBuilder;
import org.sing_group.gc4s.ui.CenteredJPanel;
import org.sing_group.gc4s.wizard.WizardStep;

/**
 * An abstract {@code WizardStep} implementation that allows the selection of a
 * file.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public abstract class FileSelectionStep extends WizardStep {
	private JPanel stepComponent;
	private JFileChooserPanel fileChooser;

	@Override
	public JComponent getStepComponent() {
		if(stepComponent == null) {
			this.stepComponent = new CenteredJPanel(getSelectionComponent());
			StepUtils.configureStepComponent(this.stepComponent);
		}
		return this.stepComponent;
	}

	private Component getSelectionComponent() {
		JPanel selectionComponent = new JPanel();
		selectionComponent.setOpaque(false);
		selectionComponent.setLayout(
			new BoxLayout(selectionComponent, BoxLayout.Y_AXIS));
		selectionComponent.add(getLabel());
		selectionComponent.add(getFileChooserPanel());
		return selectionComponent;
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
		this.fileChooser.setOpaque(false);
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

	/**
	 * Returns the file selected by the user.
	 * 
	 * @return the file selected by the user
	 */
	public File getSelectedFile() {
		return this.fileChooser.getSelectedFile();
	}
}
