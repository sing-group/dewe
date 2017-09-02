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
import java.util.LinkedList;
import java.util.List;

import org.sing_group.rnaseq.api.entities.FastqReadsSamples;
import org.sing_group.rnaseq.api.persistence.entities.Bowtie2ReferenceGenomeIndex;
import org.sing_group.rnaseq.gui.components.wizard.steps.Bowtie2ReferenceGenomeIndexSelectionStep;
import org.sing_group.rnaseq.gui.components.wizard.steps.BowtieStringTieAndRDifferentialExpressionWizardPresentationStep;
import org.sing_group.rnaseq.gui.components.wizard.steps.BowtieStringTieAndRDifferentialExpressionWizardStepProvider;
import org.sing_group.rnaseq.gui.components.wizard.steps.DefaultBowtieStringTieAndRDifferentialExpressionWizardStepProvider;
import org.sing_group.rnaseq.gui.components.wizard.steps.ExperimentalConditionsStep;
import org.sing_group.rnaseq.gui.components.wizard.steps.ReferenceAnnotationFileSelectionStep;
import org.sing_group.rnaseq.gui.components.wizard.steps.SampleReadsSelectionStep;
import org.sing_group.rnaseq.gui.components.wizard.steps.WizardSummaryStep;
import org.sing_group.rnaseq.gui.components.wizard.steps.WorkingDirectorySelectionStep;

import org.sing_group.gc4s.wizard.WizardStep;

/**
 * This class extends {@code AbstractDifferentialExpressionWizard} to provide a
 * wizard that allows user configuring a differential expression workflow using
 * Bowtie2, StringTie and R packages.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class BowtieStringTieAndRDifferentialExpressionWizard
	extends AbstractDifferentialExpressionWizard {
	private static final long serialVersionUID = 1L;

	protected static final String TITLE = "Differential expression analysis";

	private Bowtie2ReferenceGenomeIndexSelectionStep genomeSelectionStep;
	private SampleReadsSelectionStep samplesSelectionStep;
	private ReferenceAnnotationFileSelectionStep referenceAnnotationFileSelectionStep;
	private WorkingDirectorySelectionStep workingDirectorySelectionStep;

	/**
	 * Creates a new
	 * {@code BowtieStringTieAndRDifferentialExpressionWizard} with the
	 * specified parent window dialog.
	 *
	 * @param parent the parent window dialog
	 * @return a new {@code BowtieStringTieAndRDifferentialExpressionWizard} 
	 */
	public static BowtieStringTieAndRDifferentialExpressionWizard getWizard(
		Window parent
	) {
		return new BowtieStringTieAndRDifferentialExpressionWizard(
			parent,	TITLE, getWizardSteps());
	}

	protected BowtieStringTieAndRDifferentialExpressionWizard(Window parent,
		String wizardTitle, List<WizardStep> steps
	) {
		super(parent, wizardTitle, steps);
		this.init();
	}

	private void init() {
		genomeSelectionStep =
			(Bowtie2ReferenceGenomeIndexSelectionStep) getSteps().get(1);

		samplesSelectionStep =
			(SampleReadsSelectionStep) getSteps().get(3);

		referenceAnnotationFileSelectionStep =
			(ReferenceAnnotationFileSelectionStep) getSteps().get(4);

		workingDirectorySelectionStep =
			(WorkingDirectorySelectionStep) getSteps().get(5);

		((WizardSummaryStep) getSteps().get(6)).setWizardSummaryProvider(this);
	}

	protected static List<WizardStep> getWizardSteps() {
		return getWizardSteps(
			new DefaultBowtieStringTieAndRDifferentialExpressionWizardStepProvider());
	}

	protected static List<WizardStep> getWizardSteps(
		BowtieStringTieAndRDifferentialExpressionWizardStepProvider stepProvider
	) {
		List<WizardStep> wizardSteps = new LinkedList<>();
		wizardSteps.add(
			new BowtieStringTieAndRDifferentialExpressionWizardPresentationStep());
		wizardSteps.add(stepProvider.getBowtie2ReferenceGenomeSelectionStep());
		ExperimentalConditionsStep experimentalConditionsStep =
			new ExperimentalConditionsStep(2, 2);
		wizardSteps.add(experimentalConditionsStep);
		wizardSteps.add(
			new SampleReadsSelectionStep(experimentalConditionsStep, 2, 4));
		wizardSteps.add(new ReferenceAnnotationFileSelectionStep());
		wizardSteps.add(new WorkingDirectorySelectionStep());
		wizardSteps.add(new WizardSummaryStep());

		return wizardSteps;
	}

	@Override
	public Bowtie2ReferenceGenomeIndex getReferenceGenome() {
		return (Bowtie2ReferenceGenomeIndex) genomeSelectionStep
			.getSelectedReferenceGenomeIndex();
	}

	@Override
	public FastqReadsSamples getSamples() {
		return samplesSelectionStep.getSamples();
	}

	@Override
	public File getReferenceAnnotationFile() {
		return referenceAnnotationFileSelectionStep.getSelectedFile();
	}

	@Override
	public File getWorkingDirectory() {
		return workingDirectorySelectionStep.getSelectedFile();
	}
}
