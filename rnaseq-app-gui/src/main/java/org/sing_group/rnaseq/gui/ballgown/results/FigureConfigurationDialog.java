/*
 * #%L
 * DEWE GUI
 * %%
 * Copyright (C) 2016 - 2017 Hugo López-Fernández, Aitor Blanco-García, Florentino Fdez-Riverola, 
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
package org.sing_group.rnaseq.gui.ballgown.results;

import java.awt.Window;
import java.beans.PropertyChangeEvent;

import javax.swing.JPanel;

import org.sing_group.rnaseq.api.environment.execution.parameters.ImageConfigurationParameter;
import org.sing_group.rnaseq.gui.components.configuration.ImageConfigurationPanel;

import org.sing_group.gc4s.dialog.AbstractInputJDialog;

/**
 * A dialog that allows users to configure a Ballgown figure.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class FigureConfigurationDialog
	extends AbstractInputJDialog {
	private static final long serialVersionUID = 1L;

	private ImageConfigurationPanel imageConfigurationPanel;

	protected FigureConfigurationDialog(Window parent) {
		super(parent);
	}

	@Override
	protected String getDialogTitle() {
		return "Figure configuration";
	}

	@Override
	protected String getDescription() {
		return "This dialog allows you to configure the figure settings.";
	}

	@Override
	protected JPanel getInputComponentsPane() {
		return getImageConfigurationPanel();
	}

	private JPanel getImageConfigurationPanel() {
		this.imageConfigurationPanel = new ImageConfigurationPanel();
		this.imageConfigurationPanel.addPropertyChangeListener(
			ImageConfigurationPanel.PROPERTY_CONFIGURATION,
			this::imageConfigurationChanged);

		return this.imageConfigurationPanel;
	}

	protected void imageConfigurationChanged(PropertyChangeEvent evt) {
		checkOkButton();
	}

	private void checkOkButton() {
		okButton.setEnabled(isValidImageConfiguration());
	}

	private boolean isValidImageConfiguration() {
		return this.imageConfigurationPanel.isValidImageConfiguration();
	}

	/**
	 * Returns the selected {@code ImageConfigurationParameter}.
	 *
	 * @return the selected {@code ImageConfigurationParameter}
	 */
	public ImageConfigurationParameter getImageConfiguration() {
		return imageConfigurationPanel.getImageConfiguration();
	}

	@Override
	public void setVisible(boolean b) {
		checkOkButton();
		this.pack();
		super.setVisible(b);
	}
}
