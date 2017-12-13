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
package org.sing_group.rnaseq.gui.components.wizard;

import java.awt.Window;
import java.io.File;
import java.util.List;

import org.sing_group.gc4s.dialog.wizard.Wizard;
import org.sing_group.gc4s.dialog.wizard.WizardStep;
import org.sing_group.rnaseq.api.entities.FastqReadsSamples;
import org.sing_group.rnaseq.api.persistence.entities.ReferenceGenomeIndex;
import org.sing_group.rnaseq.core.controller.helper.AbstractDifferentialExpressionWorkflow;
import org.sing_group.rnaseq.gui.components.wizard.steps.WizardSummaryProvider;

/**
 * An abstract extension of {@code Wizard} that implements
 * {@code WizardSummaryProvider} to provide a summary of the differential
 * expression workflow configuration.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public abstract class AbstractDifferentialExpressionWizard extends Wizard
	implements WizardSummaryProvider {
	private static final long serialVersionUID = 1L;
	private static final String NEW_LINE = "\n";

	/**
	 * Creates a new {@code AbstractDifferentialExpressionWizard} with the
	 * specified parameters.
	 *
	 * @param parent the parent window dialog
	 * @param wizardTitle the title of the wizard dialog
	 * @param steps the list of {@code WizardStep}
	 */
	public AbstractDifferentialExpressionWizard(Window parent,
		String wizardTitle, List<WizardStep> steps
	) {
		super(parent, wizardTitle, steps);
	}

	@Override
	public String getSummary() {
		StringBuilder sb = new StringBuilder();
		sb.append(AbstractDifferentialExpressionWorkflow.getSummary(
			getReferenceGenome(), getReferenceAnnotationFile(),
			getWorkingDirectory(), getSamples()));
		sb
			.append(NEW_LINE)
			.append(NEW_LINE)
			.append("Click the finish button to run the workflow.");
		return sb.toString();
	}

	/**
	 * Returns the selected {code ReferenceGenome}.
	 *
	 * @return the selected {code ReferenceGenome}
	 */
	public abstract ReferenceGenomeIndex getReferenceGenome();

	/**
	 * Returns the selected {code FastqReadsSamples}.
	 *
	 * @return the selected {code FastqReadsSamples}
	 */
	public abstract FastqReadsSamples getSamples();

	/**
	 * Returns the selected reference annotation file.
	 *
	 * @return the selected reference annotation file
	 */
	public abstract File getReferenceAnnotationFile();

	/**
	 * Returns the selected working directory.
	 *
	 * @return the selected working directory
	 */
	public abstract File getWorkingDirectory();
}
