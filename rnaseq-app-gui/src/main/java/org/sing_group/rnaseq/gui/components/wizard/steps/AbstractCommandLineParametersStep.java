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

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import org.sing_group.gc4s.dialog.wizard.WizardStep;
import org.sing_group.rnaseq.gui.util.UISettings;

/**
 * An abstract {@code WizardStep} to create command-line parameters steps that
 * provides the generic warning message.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public abstract class AbstractCommandLineParametersStep extends WizardStep {
	private static final String WARNING = "Warning: this parameter "
		+ "configuration is intended for advanced users. If you are not "
		+ "familiarized with command-line parameters of the underlying "
		+ "applications of the workflow, please, skip this configuration step."
		+ "Otherwise, check carefully the parameters introduced since they can"
		+ "lead to unexpected failures.";

	private JPanel stepComponent;

	@Override
	public JPanel getStepComponent() {
		if (stepComponent == null) {
			stepComponent = new JPanel(new BorderLayout());
			stepComponent.add(getWarningArea(), BorderLayout.NORTH);
		}
		return stepComponent;
	}

	private JComponent getWarningArea() {
		JTextArea toret = new JTextArea(WARNING);
		toret.setTabSize(2);
		toret.setEditable(false);
		toret.setLineWrap(true);
		toret.setOpaque(false);
		toret.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
		toret.setBackground(UISettings.COLOR_WARNING);
		return toret;
	}

	@Override
	public String getStepTitle() {
		return "Parameter configuration";
	}

	@Override
	public void stepEntered() {

	}
}
