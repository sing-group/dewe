package org.sing_group.rnaseq.gui.components.wizard;

import java.awt.Window;
import java.io.File;
import java.util.LinkedList;
import java.util.List;

import org.sing_group.rnaseq.api.entities.FastqReadsSamples;
import org.sing_group.rnaseq.api.persistence.entities.Bowtie2ReferenceGenome;
import org.sing_group.rnaseq.core.persistence.DefaultReferenceGenomeDatabaseManager;
import org.sing_group.rnaseq.gui.components.wizard.steps.Bowtie2ReferenceGenomeSelectionStep;
import org.sing_group.rnaseq.gui.components.wizard.steps.BowtieStringTieAndRDifferentialExpressionWizardPresentationStep;
import org.sing_group.rnaseq.gui.components.wizard.steps.ExperimentalConditionsStep;
import org.sing_group.rnaseq.gui.components.wizard.steps.ReferenceAnnotationFileSelectionStep;
import org.sing_group.rnaseq.gui.components.wizard.steps.SampleReadsSelectionStep;
import org.sing_group.rnaseq.gui.components.wizard.steps.WizardSummaryProvider;
import org.sing_group.rnaseq.gui.components.wizard.steps.WizardSummaryStep;
import org.sing_group.rnaseq.gui.components.wizard.steps.WorkingDirectorySelectionStep;

import es.uvigo.ei.sing.hlfernandez.wizard.Wizard;
import es.uvigo.ei.sing.hlfernandez.wizard.WizardStep;

public class BowtieStringTieAndRDifferentialExpressionWizard extends Wizard
		implements WizardSummaryProvider {
	private static final long serialVersionUID = 1L;

	private static final String NEW_LINE = "\n";
	protected static final String TITLE = "Differential expression analysis";

	private Bowtie2ReferenceGenomeSelectionStep genomeSelectionStep;
	private SampleReadsSelectionStep samplesSelectionStep;
	private ReferenceAnnotationFileSelectionStep referenceAnnotationFileSelectionStep;
	private WorkingDirectorySelectionStep workingDirectorySelectionStep;

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
			(Bowtie2ReferenceGenomeSelectionStep) getSteps().get(1);

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
		wizardSteps.add(new BowtieStringTieAndRDifferentialExpressionWizardPresentationStep());
		wizardSteps.add(new Bowtie2ReferenceGenomeSelectionStep(DefaultReferenceGenomeDatabaseManager.getInstance()));
		ExperimentalConditionsStep experimentalConditionsStep = new ExperimentalConditionsStep(2, 2);
		wizardSteps.add(experimentalConditionsStep);
		wizardSteps.add(new SampleReadsSelectionStep(experimentalConditionsStep, 2, 4));
		wizardSteps.add(new ReferenceAnnotationFileSelectionStep());
		wizardSteps.add(new WorkingDirectorySelectionStep());
		wizardSteps.add(new WizardSummaryStep());
		return wizardSteps;
	}

	public Bowtie2ReferenceGenome getReferenceGenome() {
		return (Bowtie2ReferenceGenome) genomeSelectionStep.getSelectedReferenceGenome();
	}
	
	public FastqReadsSamples getSamples() {
		return samplesSelectionStep.getSamples();
	}
	
	public File getReferenceAnnotationFile() {
		return referenceAnnotationFileSelectionStep.getSelectedFile();
	}
	
	public File getWorkingDirectory() {
		return workingDirectorySelectionStep.getSelectedFile();
	}
	
	@Override
	public String getSummary() {
		StringBuilder sb = new StringBuilder();
		sb
			.append("Workflow configuration:")
			.append(NEW_LINE)
			.append("  · Reference genome: ")
			.append(this.getReferenceGenome().getReferenceGenome().getAbsolutePath())
			.append(NEW_LINE)
			.append("  · Reference annotation file: ")
			.append(this.getReferenceAnnotationFile().getAbsolutePath())
			.append(NEW_LINE)
			.append("  · Working directory: ")
			.append(this.getWorkingDirectory().getAbsolutePath())
			.append(NEW_LINE)
			.append(NEW_LINE)
			.append("Experiment samples: ")
			.append(NEW_LINE);
		this.getSamples().forEach(s -> {
			sb
				.append("  · Sample name: ")
				.append(s.getName())
				.append(" [Condition: ")
				.append(s.getCondition() + "]")
				.append(NEW_LINE);
		});
		sb
			.append(NEW_LINE)
			.append(NEW_LINE)
			.append("Click the finish button to run the workflow.");
		return sb.toString();
	}
}
