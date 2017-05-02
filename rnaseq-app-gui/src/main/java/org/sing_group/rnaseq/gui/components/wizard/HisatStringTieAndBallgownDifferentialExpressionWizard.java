package org.sing_group.rnaseq.gui.components.wizard;

import java.awt.Window;
import java.io.File;
import java.util.LinkedList;
import java.util.List;

import org.sing_group.rnaseq.api.entities.FastqReadsSamples;
import org.sing_group.rnaseq.api.persistence.entities.Hisat2ReferenceGenome;
import org.sing_group.rnaseq.core.persistence.DefaultReferenceGenomeDatabaseManager;
import org.sing_group.rnaseq.gui.components.wizard.steps.ExperimentalConditionsStep;
import org.sing_group.rnaseq.gui.components.wizard.steps.Hisat2ReferenceGenomeSelectionStep;
import org.sing_group.rnaseq.gui.components.wizard.steps.HisatStringTieAndBallgownDifferentialExpressionWizardPresentationStep;
import org.sing_group.rnaseq.gui.components.wizard.steps.ReferenceAnnotationFileSelectionStep;
import org.sing_group.rnaseq.gui.components.wizard.steps.SampleReadsSelectionStep;
import org.sing_group.rnaseq.gui.components.wizard.steps.WizardSummaryStep;
import org.sing_group.rnaseq.gui.components.wizard.steps.WorkingDirectorySelectionStep;

import es.uvigo.ei.sing.hlfernandez.wizard.WizardStep;

/**
 * This class extends {@code AbstractDifferentialExpressionWizard} to provide a
 * wizard that allows user configuring a differential expression workflow using
 * HISAT2, StringTie and Ballgown.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class HisatStringTieAndBallgownDifferentialExpressionWizard
	extends AbstractDifferentialExpressionWizard {
	private static final long serialVersionUID = 1L;
	protected static final String TITLE = "Differential expression analysis";

	private Hisat2ReferenceGenomeSelectionStep genomeSelectionStep;
	private SampleReadsSelectionStep samplesSelectionStep;
	private ReferenceAnnotationFileSelectionStep referenceAnnotationFileSelectionStep;
	private WorkingDirectorySelectionStep workingDirectorySelectionStep;

	/**
	 * Creates a new
	 * {@code HisatStringTieAndBallgownDifferentialExpressionWizard} with the
	 * specified parent window dialog.
	 *
	 * @param parent the parent window dialog
	 */
	public static HisatStringTieAndBallgownDifferentialExpressionWizard getWizard(
		Window parent
	) {
		return new HisatStringTieAndBallgownDifferentialExpressionWizard(
			parent,	TITLE, getWizardSteps());
	}

	protected HisatStringTieAndBallgownDifferentialExpressionWizard(Window parent,
		String wizardTitle, List<WizardStep> steps
	) {
		super(parent, wizardTitle, steps);
		this.init();
	}

	private void init() {
		genomeSelectionStep =
			(Hisat2ReferenceGenomeSelectionStep) getSteps().get(1);

		samplesSelectionStep =
			(SampleReadsSelectionStep) getSteps().get(3);

		referenceAnnotationFileSelectionStep =
			(ReferenceAnnotationFileSelectionStep) getSteps().get(4);

		workingDirectorySelectionStep =
			(WorkingDirectorySelectionStep) getSteps().get(5);

		((WizardSummaryStep) getSteps().get(6)).setWizardSummaryProvider(this);
	}

	protected static List<WizardStep> getWizardSteps() {
		List<WizardStep> wizardSteps = new LinkedList<>();
		wizardSteps.add(
			new HisatStringTieAndBallgownDifferentialExpressionWizardPresentationStep());
		wizardSteps.add(new Hisat2ReferenceGenomeSelectionStep(
			DefaultReferenceGenomeDatabaseManager.getInstance()));
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
	public Hisat2ReferenceGenome getReferenceGenome() {
		return (Hisat2ReferenceGenome) genomeSelectionStep
			.getSelectedReferenceGenome();
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
