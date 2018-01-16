/*
 * #%L
 * DEWE GUI
 * %%
 * Copyright (C) 2016 - 2018 Hugo López-Fernández, Aitor Blanco-García, Florentino Fdez-Riverola,
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

import org.sing_group.gc4s.dialog.wizard.WizardStep;
import org.sing_group.gc4s.input.filechooser.JFileChooserPanel;
import org.sing_group.gc4s.input.filechooser.JFileChooserPanelBuilder;
import org.sing_group.gc4s.input.filechooser.SelectionMode;
import org.sing_group.gc4s.ui.CenteredJPanel;
import org.sing_group.rnaseq.gui.util.CommonFileChooser;

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

	/**
	 * Sets the selected file.
	 *
	 * @param file the selected file
	 */
	public void setSelectedFile(File file) {
		this.fileChooser.setSelectedFile(file);
	}
}
