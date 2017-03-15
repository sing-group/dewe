package org.sing_group.rnaseq.gui.components.wizard.steps;

import static org.sing_group.rnaseq.gui.components.wizard.steps.StepUtils.configureStepComponent;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import es.uvigo.ei.sing.hlfernandez.wizard.WizardStep;

public class BowtieStringTieAndRDifferentialExpressionWizardPresentationStep 
	extends WizardStep {

	public static final String TEXT = "<html>"
		+ "<p>This wizard will guide you through the steps to configure the "
		+ "differential expression pipeline using bowtie2, samtools, StringTie"
		+ ", Ballgown and EdgeR.</p>"

		+ "<p>This pipeline is able to compare two conditions with at least "
		+ "two samples each.</p>"

		+ "<p>The pipeline performs the following steps:</p><ol>"
		+ "<li>Reads alignment using bowtie2.</li>"
		+ "<li>Conversion of the aligned reads into bam files using samtools.</li>"
		+ "<li>Transcript assemblng using StringTie.</li>"
		+ "<li>And, finally, the differential expression analysis using two "
		+ "R libraries: ballgown and edgeR.</li>"
		+ "</ol>"

		+ "Once you have finished this wizard, the pipeline execution starts "
		+ "and you will be able to monitor the exection progress."

		+ "</html>";

	private JComponent stepComponent;

	@Override
	public boolean isStepCompleted() {
		return true;
	}

	@Override
	public String getStepTitle() {
		return "Differential expression analysis pipeline";
	}

	@Override
	public JComponent getStepComponent() {
		this.stepComponent = getWelcomePanel();
		configureStepComponent(stepComponent);
		return this.stepComponent;
	}

	private JComponent getWelcomePanel() {
		JTextPane textArea = new JTextPane();
		textArea.setContentType("text/html");
		textArea.setEditable(false);
		textArea.setOpaque(false);
		textArea.setFont(textArea.getFont().deriveFont(13f));
		textArea.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
		textArea.setText(TEXT);
		return new JScrollPane(textArea);
	}

	@Override
	public void stepEntered() {
	}
}
