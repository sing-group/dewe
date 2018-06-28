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
package org.sing_group.rnaseq.gui.components.wizard;

import java.awt.Component;
import java.awt.Window;
import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import org.sing_group.gc4s.dialog.wizard.WizardStep;
import org.sing_group.rnaseq.api.controller.WorkflowController;
import org.sing_group.rnaseq.api.entities.FastqReadsSamples;
import org.sing_group.rnaseq.api.persistence.entities.Bowtie2ReferenceGenomeIndex;
import org.sing_group.rnaseq.api.persistence.entities.DifferentialExpressionWorkflowConfiguration;
import org.sing_group.rnaseq.gui.components.wizard.steps.Bowtie2ReferenceGenomeIndexSelectionStep;
import org.sing_group.rnaseq.gui.components.wizard.steps.BowtieStringTieAndRDifferentialExpressionCommandLineParametersStep;
import org.sing_group.rnaseq.gui.components.wizard.steps.BowtieStringTieAndRDifferentialExpressionWizardPresentationStep;
import org.sing_group.rnaseq.gui.components.wizard.steps.BowtieStringTieAndRDifferentialExpressionWizardStepProvider;
import org.sing_group.rnaseq.gui.components.wizard.steps.DefaultBowtieStringTieAndRDifferentialExpressionWizardStepProvider;
import org.sing_group.rnaseq.gui.components.wizard.steps.ExperimentalConditionsStep;
import org.sing_group.rnaseq.gui.components.wizard.steps.ReferenceAnnotationFileSelectionStep;
import org.sing_group.rnaseq.gui.components.wizard.steps.SampleReadsSelectionStep;
import org.sing_group.rnaseq.gui.components.wizard.steps.WizardSummaryStep;
import org.sing_group.rnaseq.gui.components.wizard.steps.WorkingDirectorySelectionStep;

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
	private ExperimentalConditionsStep experimentalConditionsStep;
	private SampleReadsSelectionStep samplesSelectionStep;
	private ReferenceAnnotationFileSelectionStep referenceAnnotationFileSelectionStep;
	private WorkingDirectorySelectionStep workingDirectorySelectionStep;
	private BowtieStringTieAndRDifferentialExpressionCommandLineParametersStep commandLineApplicationsParametersStep;

	private DifferentialExpressionWorkflowConfiguration workflowConfiguration;

	/**
	 * Creates a new
	 * {@code BowtieStringTieAndRDifferentialExpressionWizard}
	 * using the specified workflow configuration.
	 *
	 * @param parent the parent component of the wizard dialog
	 * @param configuration the {@code DifferentialExpressionWorkflowConfiguration}
	 *
	 * @return a new {@code BowtieStringTieAndRDifferentialExpressionWizard}
	 */
	public static BowtieStringTieAndRDifferentialExpressionWizard getWizard(
		Window parent,
		DifferentialExpressionWorkflowConfiguration configuration
	) {
		return new BowtieStringTieAndRDifferentialExpressionWizard(
			parent,	TITLE, getWizardSteps(), configuration);
	}

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
		return new BowtieStringTieAndRDifferentialExpressionWizard(parent,
			TITLE, getWizardSteps());
	}

	protected BowtieStringTieAndRDifferentialExpressionWizard(Window parent,
		String wizardTitle, List<WizardStep> steps
	) {
		this(parent, wizardTitle, steps, null);
	}

	protected BowtieStringTieAndRDifferentialExpressionWizard(Window parent,
		String wizardTitle, List<WizardStep> steps,
		DifferentialExpressionWorkflowConfiguration workflowConfiguration
	) {
		super(parent, wizardTitle, steps);
		this.workflowConfiguration = workflowConfiguration;
		this.init();
	}

	private void init() {
		genomeSelectionStep =
			(Bowtie2ReferenceGenomeIndexSelectionStep) getSteps().get(1);

		experimentalConditionsStep = (ExperimentalConditionsStep) getSteps()
			.get(2);

		samplesSelectionStep =
			(SampleReadsSelectionStep) getSteps().get(3);

		referenceAnnotationFileSelectionStep =
			(ReferenceAnnotationFileSelectionStep) getSteps().get(4);

		workingDirectorySelectionStep =
			(WorkingDirectorySelectionStep) getSteps().get(5);

		commandLineApplicationsParametersStep =
			(BowtieStringTieAndRDifferentialExpressionCommandLineParametersStep) getSteps().get(6);


		((WizardSummaryStep) getSteps().get(7)).setWizardSummaryProvider(this);

		this.setWorkflowConfiguration();
	}

	private void setWorkflowConfiguration() {
		if (this.workflowConfiguration != null) {
			if (this.workflowConfiguration.getReferenceGenome()
				.isValidIndex()
			) {
				this.genomeSelectionStep.setSelectedReferenceGenomeIndex(
					(Bowtie2ReferenceGenomeIndex) this.workflowConfiguration
						.getReferenceGenome(),
					true);
			} else {
				JOptionPane.showMessageDialog(getParentForDialog(),
					"The imported reference genome index is not valid (maybe "
					+ "its files has been removed or renamed). You will need "
					+ "to import or build one later.",
					"Invalid reference genome index", JOptionPane.WARNING_MESSAGE);
			}

			this.experimentalConditionsStep
				.setExperimentalConditionsAndSamples(this.workflowConfiguration
					.getExperimentalConditionsAndSamples());

			this.referenceAnnotationFileSelectionStep.setSelectedFile(
				this.workflowConfiguration.getReferenceAnnotationFile());

			this.workingDirectorySelectionStep.setSelectedFile(
				this.workflowConfiguration.getWorkingDirectory());

			this.commandLineApplicationsParametersStep.setParametersMap(
				this.workflowConfiguration.getCommandLineApplicationsParameters());
		}
	}

	private Component getParentForDialog() {
		return SwingUtilities.getRootPane(this);
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
		wizardSteps.add(new BowtieStringTieAndRDifferentialExpressionCommandLineParametersStep());
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

	@Override
	public Map<WorkflowController.Parameters, String> getCommandLineApplicationsParameters() {
		return this.commandLineApplicationsParametersStep.getParametersMap();
	}
}
